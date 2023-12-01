package com.wxmblog.community.controller;

import com.wxmblog.base.auth.common.annotation.AdminRequest;
import com.wxmblog.base.auth.common.constant.AuthConstants;
import com.wxmblog.base.common.web.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangxiaomu
 * @date 2023/11/28 17:48
 */
@RestController
@Api(tags = "后台接口")
@RequestMapping("admin")
@AdminRequest
public class AdminController {

    @ApiOperation("get请求")
    @ApiOperationSort(value = 1)
    @GetMapping("/get")
    public R<Void> get() {
        return R.ok();
    }

    @ApiOperation("post请求")
    @ApiOperationSort(value = 1)
    @PostMapping("/post")
    public R<Void> post() {
        return R.ok();
    }

    @ApiOperation("put请求")
    @ApiOperationSort(value = 1)
    @PutMapping("/put")
    public R<Void> put() {
        return R.ok();
    }

    @ApiOperation("delete请求")
    @ApiOperationSort(value = 1)
    @DeleteMapping("/delete")
    public R<Void> delete() {
        return R.ok();
    }
}
