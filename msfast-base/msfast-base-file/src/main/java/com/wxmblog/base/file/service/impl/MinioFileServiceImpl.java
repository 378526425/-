package com.wxmblog.base.file.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import com.wxmblog.base.common.constant.ConfigConstants;
import com.wxmblog.base.common.enums.BaseExceptionEnum;
import com.wxmblog.base.common.exception.JrsfException;
import com.wxmblog.base.common.utils.SpringBeanUtils;
import com.wxmblog.base.common.utils.TokenUtils;
import com.wxmblog.base.file.exception.FileExceptionEnum;
import com.wxmblog.base.file.service.IMsfFileService;
import com.wxmblog.base.file.service.MsfFileService;
import com.wxmblog.base.file.utils.FileUploadUtils;
import com.wxmblog.base.file.config.MinioConfig;
import com.wxmblog.base.file.service.IFileService;
import com.wxmblog.base.file.utils.FileUtils;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * Minio 文件存储
 *
 * @author ruoyi
 */
@Primary
@Service
public class MinioFileServiceImpl implements IFileService {

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient client;

    @Autowired
    private MsfFileService fileService;


    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile file) throws Exception {


        MultipartFile multipartFile = toThumbnailsFile(file);

        String filePath = FileUploadUtils.extractFilename(multipartFile);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(filePath)
                .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                .contentType(multipartFile.getContentType())
                .build();
        client.putObject(args);
        String url = fileService.getPrePath() + filePath;
        //保存文件 此时文件为临时文件 会被定期删除
        fileService.saveFile(url, filePath, FileUtils.getName(multipartFile.getOriginalFilename()));
        return url;
    }

    private MultipartFile toThumbnailsFile(MultipartFile file) {
        MultipartFile multipartFile = file;
        if (ConfigConstants.CONDENSE() && FileUtils.isPicture(file.getOriginalFilename())) {
            String tempPath = ConfigConstants.FILE_STATIC_PATH() + File.separator + "temp" + File.separator + UUID.fastUUID().toString().replaceAll("-", "") + file.getOriginalFilename();
            File tempFile = new File(tempPath);
            mkdirs(tempPath);
            //文件大于0.5m
            if (file.getSize() > (1024 * 1024) * 0.5) {
                try {
                    Thumbnails.of(file.getInputStream())
                            // 图片缩放率，不能和size()一起使用
                            .scale(ConfigConstants.CONDENSE_SCALE())
                            // 缩略图保存目录,该目录需存在，否则报错
                            .toFile(tempPath);
                    // 获取输入流
                    FileInputStream input = new FileInputStream(tempFile);
                    // 转为 MultipartFile
                    multipartFile = new MockMultipartFile("file", tempFile.getName(), file.getContentType(), input);
                    FileUtils.deleteFile(tempPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return multipartFile;
    }

    /**
     * @param file
     * @Description: 上传的文件不会被删除
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2023/4/11 上午10:15
     */
    @Override
    public String lastingUpload(MultipartFile file) throws Exception {
        return saveFile(file, false);
    }

    /**
     * @param filename
     * @param response
     * @Description: 文件下载
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/9/9 上午11:08
     */
    @Override
    public void download(String filename, HttpServletResponse response) throws Exception {

        if (Boolean.TRUE.equals(ConfigConstants.DOWNLOAD_LOGIN())) {
            Integer ownerId = TokenUtils.getOwnerId();
            if (ownerId == null) {
                throw new JrsfException(BaseExceptionEnum.NO_LOGIN_EXCEPTION);
            }
        }

        IMsfFileService iMsfFileService = SpringBeanUtils.getBean(IMsfFileService.class);
        if (iMsfFileService != null) {
            iMsfFileService.before(filename);
        }

        InputStream in = null;
        try {
            //获取对象信息
            StatObjectResponse stat = client.statObject(
                    StatObjectArgs.builder().bucket(minioConfig.getBucketName()).object(filename).build());
            response.setContentType(stat.contentType());
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename, "utf-8"));
            //文件下载
            in = client.getObject(GetObjectArgs.builder().bucket(minioConfig.getBucketName()).object(filename).build());
            IOUtils.copy(in, response.getOutputStream());
        } catch (ErrorResponseException e) {
            throw new JrsfException(FileExceptionEnum.File_NOT_Exists_Exception);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }

        iMsfFileService.after(filename);
    }

    /**
     * @param filePath
     * @Description: 删除文件
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/9/9 下午3:39
     */
    @Override
    public void deleteFile(String filePath) throws Exception {
        client.removeObject(
                RemoveObjectArgs.builder().bucket(minioConfig.getBucketName()).object(filePath).build());
    }

    @Override
    public String staticUpload(MultipartFile file, String path) {
        String staticPath = FileUtils.getPath(path) + File.separator + file.getOriginalFilename();
        String uploadpath = ConfigConstants.FILE_STATIC_PATH() + File.separator + staticPath;

        File fileOld = new File(uploadpath);
        if (fileOld.exists()) {
            throw new JrsfException(FileExceptionEnum.File_Exists_Exception);
        }
        mkdirs(uploadpath);
        //上传
        try {
            file.transferTo(new File(uploadpath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staticPath.replaceAll("\\\\", "/");
    }

    @Override
    public Boolean staticDelete(String path) {
        return FileUtils.deleteFile(ConfigConstants.FILE_STATIC_PATH() + File.separator + FileUtils.getPath(path));
    }

    @Override
    public String toBase64(MultipartFile file) {
        MultipartFile multipartFile = toThumbnailsFile(file);
        String base64 = null;
        try {
            base64 = Base64.encode(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    private void mkdirs(String uploadpath) {
        File file1 = new File(uploadpath);
        File parent = file1.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
    }

    private String saveFile(MultipartFile file, Boolean isDelete) throws Exception {
        MultipartFile multipartFile = file;
        if (ConfigConstants.CONDENSE() && FileUtils.isPicture(file.getOriginalFilename())) {
            String tempPath = ConfigConstants.FILE_STATIC_PATH() + File.separator + "temp" + File.separator + UUID.fastUUID().toString().replaceAll("-", "") + file.getOriginalFilename();
            File tempFile = new File(tempPath);
            mkdirs(tempPath);
            //文件大于0.5m
            if (file.getSize() > (1024 * 1024) * 0.5) {
                Thumbnails.of(file.getInputStream())
                        // 图片缩放率，不能和size()一起使用
                        .scale(ConfigConstants.CONDENSE_SCALE())
                        // 缩略图保存目录,该目录需存在，否则报错
                        .toFile(tempPath);
                // 获取输入流
                FileInputStream input = new FileInputStream(tempFile);
                // 转为 MultipartFile
                multipartFile = new MockMultipartFile("file", tempFile.getName(), file.getContentType(), input);
                FileUtils.deleteFile(tempPath);
            }
        }

        String filePath = FileUploadUtils.extractFilename(multipartFile);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(filePath)
                .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                .contentType(multipartFile.getContentType())
                .build();
        client.putObject(args);
        String url = fileService.getPrePath() + filePath;
        if (isDelete == null || Boolean.TRUE.equals(isDelete)) {
            //保存文件 此时文件为临时文件 会被定期删除
            fileService.saveFile(url, filePath, FileUtils.getName(multipartFile.getOriginalFilename()));
        }

        return url;
    }
}
