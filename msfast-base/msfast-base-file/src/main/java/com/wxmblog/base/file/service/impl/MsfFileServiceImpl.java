package com.wxmblog.base.file.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ClassUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxmblog.base.common.constant.ConfigConstants;
import com.wxmblog.base.common.utils.ThreadUtil;
import com.wxmblog.base.file.service.MsfFileService;
import com.wxmblog.base.file.annotation.FileListSave;
import com.wxmblog.base.file.annotation.FileSave;
import com.wxmblog.base.file.common.enums.FileStatusEnum;
import com.wxmblog.base.file.config.MinioConfig;
import com.wxmblog.base.file.dao.MsfFileDao;
import com.wxmblog.base.file.entity.MsfFileEntity;
import com.wxmblog.base.file.exception.TempFileNoExistException;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("msfFileService")
public class MsfFileServiceImpl extends ServiceImpl<MsfFileDao, MsfFileEntity> implements MsfFileService {

    @Autowired
    private MinioClient client;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    @Transactional
    @Async
    public void saveFile(String url, String filePath, String fileName) {
        MsfFileEntity msfFileEntity = new MsfFileEntity();
        msfFileEntity.setUrl(url);
        msfFileEntity.setOriginal(true);
        msfFileEntity.setFileName(fileName);
        msfFileEntity.setStatus(FileStatusEnum.TEMP);
        this.save(msfFileEntity);
        delayDeleteFile(filePath, url);
    }

    @Async
    void delayDeleteFile(String filePath, String url) {
        ThreadUtil.getInstance().scheduledThreadPool.schedule(() -> {
            Wrapper<MsfFileEntity> wrapper = new QueryWrapper<MsfFileEntity>().lambda()
                    .eq(MsfFileEntity::getUrl, url)
                    .eq(MsfFileEntity::getStatus, FileStatusEnum.TEMP);
            Long count = count(wrapper);
            if (count > 0) {
                deleteTempFile(filePath, url);
            }
        }, ConfigConstants.FILE_TEMP_TIME(), TimeUnit.MINUTES);
    }

    @Override
    @Async
    @Transactional
    @Retryable(value = TempFileNoExistException.class)
    public void changeTempFile(Object object) {
        if (object != null) {
            Field[] fieldMap = object.getClass().getDeclaredFields();
            for (Field field : fieldMap) {
                field.setAccessible(true);
                //有该注解表示这个字段是文件 需要将临时文件设置为持久化避免被删除
                FileSave fileSave = field.getAnnotation(FileSave.class);
                if (fileSave != null) {
                    try {
                        Object urlObject = field.get(object);
                        if (urlObject != null && urlObject instanceof String) {
                            getFileListByText(urlObject.toString()).forEach(model -> {
                                changeTempUrl(model);
                            });
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                FileListSave fileListSave = field.getAnnotation(FileListSave.class);
                if (fileListSave != null) {
                    try {
                        Object urlObject = field.get(object);
                        if (urlObject != null && urlObject instanceof List) {
                            ((List) urlObject).forEach(model -> {
                                if (model instanceof String) {
                                    changeTempUrl(model.toString());
                                }
                            });
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    @Async
    @Transactional
    @Retryable(value = TempFileNoExistException.class)
    public void changeTempFile(String url) {
        changeTempUrl(url);
    }

    @Override
    @Async
    @Transactional
    public void deleteTempFile(String filePath, String url) {

        Wrapper<MsfFileEntity> wrapper = new QueryWrapper<MsfFileEntity>().lambda()
                .eq(MsfFileEntity::getUrl, url)
                .eq(MsfFileEntity::getStatus, FileStatusEnum.TEMP);
        Long tempCount = this.getBaseMapper().selectCount(wrapper);
        if (tempCount > 0) {
            deleteFileByPath(filePath);
            this.remove(wrapper);
        }
    }

    /**
     * @param filePath
     * @Description: 删除文件 物理删除
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/9/9 下午3:39
     */
    @Override
    public void deleteFileByPath(String filePath) {
        try {
            client.removeObject(
                    RemoveObjectArgs.builder().bucket(minioConfig.getBucketName()).object(filePath).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void deleteFileByUrl(String url) {
        if (StringUtils.isNotBlank(url)) {
            String pre = getPrePath();
            int index = url.indexOf(pre);
            if (index >= 0) {
                String path = url.substring(pre.length());
                deleteFileByPath(path);
                Wrapper<MsfFileEntity> wrapper = new QueryWrapper<MsfFileEntity>().lambda()
                        .eq(MsfFileEntity::getUrl, url);
                this.remove(wrapper);
            }

        }
    }

    @Override
    public String getPrePath() {

        return (StringUtils.isNotBlank(minioConfig.getUrl()) ? minioConfig.getUrl() : minioConfig.getEndpoint()) + "/" + minioConfig.getBucketName() + "/";
    }

    @Override
    public void deleteImg(List<String> oldImg, List<String> imgList) {
        //todo 删除图片
        if (CollectionUtil.isNotEmpty(oldImg)) {

            oldImg.forEach(model -> {
                Long count = imgList.stream().filter(p -> StringUtils.isNotBlank(p) && p.equals(model)).count();
                if (count == 0) {
                    deleteFileByUrl(model);
                }
            });
        }
    }

    @Override
    public void deleteSaveFile(Object object, Integer ownerId) {

        Field[] Fields = ClassUtil.getDeclaredFields(object.getClass());
        for (Field field : Fields) {

            Field fieldId = ClassUtil.getDeclaredField(object.getClass(), "id");
            //图片地址
            FileSave fileSave = field.getAnnotation(FileSave.class);
            if (fileSave != null && StringUtils.isNotBlank(fileSave.table()) && StringUtils.isNotBlank(fileSave.field()) && fileSave.updateDelete()) {

                if (fieldId != null || fileSave.tokenId()) {
                    deleteSaveFile(fileSave.tokenId(), ownerId, fieldId, object, fileSave.field(), fileSave.table(), field);
                }
            }
            FileListSave fileListSave = field.getAnnotation(FileListSave.class);
            if (fileListSave != null && StringUtils.isNotBlank(fileListSave.table()) && StringUtils.isNotBlank(fileListSave.field()) && fileListSave.updateDelete()) {

                if (fieldId != null || fileListSave.tokenId()) {
                    deleteSaveFile(fileListSave.tokenId(), ownerId, fieldId, object, fileListSave.field(), fileListSave.table(), field);
                }
            }
        }
    }

    void deleteSaveFile(boolean tokenId, Integer ownerId, Field fieldId, Object object, String fieldName, String tableName, Field field) {
        Integer fieldIdValue = null;
        if (tokenId) {
            fieldIdValue = ownerId;
        } else {
            try {
                fieldId.setAccessible(true);
                Object objectId = fieldId.get(object);
                if (objectId != null) {
                    fieldIdValue = (Integer) objectId;
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (fieldIdValue != null) {
            String urlSql = "select tb." + fieldName + " from " + tableName + " tb where  tb.id=:id";
            HashMap<String, Object> param = new HashMap<>();
            param.put("id", fieldIdValue);
            List<String> urlList = namedParameterJdbcTemplate.queryForList(urlSql, param, String.class);
            field.setAccessible(true);
            try {

                Object urlObject = field.get(object);
                List<String> oldFileList = getFileListByText(urlList.get(0));
                if (urlObject != null && urlObject instanceof String) {

                    String newUrl = urlObject.toString();
                    if (CollectionUtil.isNotEmpty(urlList)) {
                        List<String> newFileList = getFileListByText(newUrl);
                        deleteImg(oldFileList, newFileList);
                    }
                } else if (urlObject != null && urlObject instanceof List) {
                    List<String> newFileList = (List) urlObject;
                    deleteImg(oldFileList, newFileList);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getFileNameByUrl(String url) {
        if (StringUtils.isNotBlank(url)) {
            return url.substring(url.indexOf(minioConfig.getBucketName()) + minioConfig.getBucketName().length() + 1);
        }
        return null;
    }

    @Override
    public void deleteFileByRichText(String richText) {

        getFileListByText(richText).forEach(model -> {
            deleteFileByUrl(model);
        });
    }

    @Override
    public String imageToBase64(String imgUrl) {
        String suffix = imgUrl.substring(imgUrl.lastIndexOf(".") + 1);

        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try {
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();
            outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            return encode(outStream.toByteArray(), suffix);
        } catch (Exception e) {

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrl != null) {
                httpUrl.disconnect();
            }
        }
        return imgUrl;
    }

    public static String encode(byte[] image, String suffix) {
        Base64.Encoder ENCODE_64 = Base64.getEncoder();
        String encodedToStr = ENCODE_64.encodeToString(image);
        encodedToStr = "data:image/" + suffix + ";base64," + encodedToStr;
        return replaceEnter(encodedToStr);
    }

    public static String replaceEnter(String str) {
        String reg = "[\n-\r]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    public List<String> getFileListByText(String richText) {

        List<String> imgList = new ArrayList<>();

        //提取图片地址
        String regex = "(((https|http)?:)?//?[^'\"<>]+?\\.(jpg|jpeg|gif|png|JPG|JPEG|GIF|PNG))";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(richText);
        while (m.find()) {
            //获取数字子串
            String num = m.group(1);
            if (num.contains(minioConfig.getBucketName())) {
                imgList.add(num);
            }
        }

        return imgList;
    }

    private void changeTempUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return;
        }
        Wrapper<MsfFileEntity> wrapper = new UpdateWrapper<MsfFileEntity>().lambda()
                .eq(MsfFileEntity::getUrl, url)
                .set(MsfFileEntity::getStatus, FileStatusEnum.SAVED);
        Boolean result = this.update(wrapper);
        if (result == false) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new TempFileNoExistException();
        }
    }

}
