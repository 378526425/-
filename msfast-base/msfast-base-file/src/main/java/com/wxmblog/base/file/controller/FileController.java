package com.wxmblog.base.file.controller;

import com.wxmblog.base.common.annotation.AuthIgnore;
import com.wxmblog.base.common.constant.ParamTypeConstants;
import com.wxmblog.base.common.web.domain.R;
import com.wxmblog.base.file.rest.response.FileResponse;
import com.wxmblog.base.file.service.IFileService;
import com.wxmblog.base.file.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件请求处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("file")
@Api(tags = "文件系统")
@Slf4j
public class FileController {

    @Autowired
    private IFileService sysFileService;


    /**
     * 文件上传请求
     */
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    @AuthIgnore
    public R<FileResponse> upload(@RequestPart MultipartFile file) throws Exception {

        try {
            // 上传并返回访问地址
            String url = sysFileService.uploadFile(file);
            FileResponse sysFile = new FileResponse();
            sysFile.setName(FileUtils.getName(url));
            sysFile.setUrl(url);
            return R.ok(sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw e;
        }
    }

    /**
     * 下载文件
     *
     * @param filename
     * @param response
     */
    @GetMapping("/download")
    @ApiOperation(value = "文件下载")
    @AuthIgnore
    public void download(@RequestParam String filename, HttpServletResponse response) throws Exception {

        try {
            this.sysFileService.download(filename, response);
        } catch (Exception e) {
            log.error("下载文件失败", e);
            throw e;
        }
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "文件删除")
    public R delete(@RequestParam String filename) throws Exception {

        try {
            this.sysFileService.deleteFile(filename);
            return R.ok();
        } catch (Exception e) {
            log.error("文件删除失败", e);
            throw e;
        }
    }

    /**
     * 静态资源上传 todo 线上需要屏蔽该接口或增加权限校验
     */
    @PostMapping("/static/upload")
    @ApiOperation(value = "静态资源上传")
    @AuthIgnore
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "path", value = "images/user", required = true)
    })
    public R<FileResponse> staticUpload(@RequestPart MultipartFile file, @ApiIgnore @RequestParam String path) {
        // 上传并返回访问地址
        String url = sysFileService.staticUpload(file, path);
        FileResponse sysFile = new FileResponse();
        sysFile.setName(FileUtils.getName(url));
        sysFile.setUrl(url);
        return R.ok(sysFile);
    }

    /**
     * 静态资源上传 todo 线上需要屏蔽该接口或增加权限校验
     */
    @DeleteMapping("/static/delete")
    @ApiOperation(value = "静态资源删除")
    @AuthIgnore
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "path", value = "images/user/logo.jpg", required = true)
    })
    public R<Boolean> staticDelete(@ApiIgnore @RequestParam String path) {
        return R.ok(sysFileService.staticDelete(path));
    }

    /**
     * 文件上传请求
     */
    @PostMapping("/lasting/upload")
    @ApiOperation(value = "文件上传-持久化")
    public R<FileResponse> lastingUpload(@RequestPart MultipartFile file) throws Exception {

        try {
            // 上传并返回访问地址
            String url = sysFileService.lastingUpload(file);
            FileResponse sysFile = new FileResponse();
            sysFile.setName(FileUtils.getName(url));
            sysFile.setUrl(url);
            return R.ok(sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw e;
        }
    }
    /**
     * 文件转base64
     */
    @PostMapping("/toBase64")
    @ApiOperation(value = "文件转Base64")
    @AuthIgnore
    public R<String> toBase64(@RequestPart MultipartFile file) throws Exception {
        return R.ok(sysFileService.toBase64(file));
    }
}
