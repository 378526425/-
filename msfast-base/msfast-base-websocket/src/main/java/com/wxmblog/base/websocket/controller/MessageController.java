package com.wxmblog.base.websocket.controller;

import com.wxmblog.base.common.constant.ParamTypeConstants;
import com.wxmblog.base.common.utils.PageResult;
import com.wxmblog.base.common.web.domain.R;
import com.wxmblog.base.websocket.common.rest.response.MessageInfoResponse;
import com.wxmblog.base.websocket.common.rest.response.MessageListResponse;
import com.wxmblog.base.websocket.service.MsFastMessageService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-13 17:20
 **/

@RestController
@RequestMapping("message")
@Api(tags = "消息通讯")
@Slf4j
public class MessageController {

    @Autowired
    MsFastMessageService msFastMessageService;


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页数", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "页码", defaultValue = "10")
    })
    @ApiOperation("对话框详情")
    @ApiOperationSort(value = 1)
    @GetMapping("/info/{sendUserId}")
    public R<PageResult<MessageInfoResponse>> messageInfoList(
            @PathVariable Integer sendUserId,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(msFastMessageService.getMessageInfoRange(sendUserId, pageIndex, pageSize));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageIndex", value = "页数", defaultValue = "1"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "pageSize", value = "页码", defaultValue = "10")
    })
    @ApiOperation("消息列表")
    @ApiOperationSort(value = 2)
    @GetMapping("/list")
    public R<PageResult<MessageListResponse>> messageInfoList(
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return R.ok(msFastMessageService.getMessageListRange(pageIndex, pageSize));
    }

    @ApiOperation("删除消息")
    @ApiOperationSort(value = 3)
    @DeleteMapping("/list/delete/{sendUserId}")
    public R<Void> deleteList(@PathVariable Integer sendUserId) {
        msFastMessageService.deleteList(sendUserId);
        return R.ok();
    }

    @ApiOperation("消息未读总数")
    @ApiOperationSort(value = 4)
    @GetMapping("/un/read")
    public R<Integer> unRead() {
        return R.ok(msFastMessageService.unRead());
    }


}
