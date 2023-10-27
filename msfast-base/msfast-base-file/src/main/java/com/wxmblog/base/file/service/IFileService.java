package com.wxmblog.base.file.service;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 文件上传接口
 *
 * @author ruoyi
 */
public interface IFileService {
    /**
     * 文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    String uploadFile(MultipartFile file) throws Exception;

    /**
     * @Description: 上传的文件不会被删除
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2023/4/11 上午10:15
     */
    String lastingUpload(MultipartFile file) throws Exception;

    /**
     * @Description: 文件下载
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/9/9 上午11:08
     */
    void download(String filename, HttpServletResponse response) throws Exception;

    /**
     * @Description: 删除文件
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/9/9 下午3:39
     */
    void deleteFile(String filePath) throws Exception;

    /*
     * @Author wanglei
     * @Description  静态文件上传
     * @Date 14:40 2022/12/15
     * @Param
     * @return
     **/
    String staticUpload(MultipartFile file, String path);

    /*
     * @Author
     * @Description  删除静态文件
     * @Date 15:49 2022/12/15
     * @Param
     * @return
     **/
    Boolean staticDelete(String path);

    /*
     * @Author wanglei
     * @Description  文件转base64
     * @Date 16:40 2023/4/9
     * @Param
     * @return
     **/
    String toBase64(MultipartFile file);
}
