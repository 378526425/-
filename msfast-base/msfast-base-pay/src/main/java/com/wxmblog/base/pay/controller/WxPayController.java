package com.wxmblog.base.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.wxmblog.base.common.annotation.AuthIgnore;
import com.wxmblog.base.common.constant.ParamTypeConstants;
import com.wxmblog.base.common.utils.SpringUtils;
import com.wxmblog.base.common.utils.ViolationUtils;
import com.wxmblog.base.common.web.domain.R;
import com.wxmblog.base.pay.service.IWxPayService;
import com.wxmblog.base.pay.utils.ReflexUtils;
import com.wxmblog.base.pay.common.rest.request.OrderSubmitRequest;
import com.wxmblog.base.pay.service.MsfWxPayService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/wxPay")
@Api(tags = "微信支付")
public class WxPayController {

    @Autowired
    MsfWxPayService msfWxPayService;

    @PostMapping("/wxApplet")
    @ApiOperation(value = "小程序支付")
    @ApiOperationSort(1)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestBody, name = "body", value = "{\"code\":\"code 必填\"}", required = true)
    })
    public R<Map<String, String>> wxAppletPay(@RequestBody @ApiIgnore String viewmodelJson) throws Exception {

        IWxPayService IAuthorityService = SpringUtils.getBean(IWxPayService.class);

        Class<? extends OrderSubmitRequest> clsViewModel = ReflexUtils.getOrderSubmitRequest(IAuthorityService);

        OrderSubmitRequest viewModel = JSONObject.parseObject(viewmodelJson, clsViewModel);

        //数据校验
        ViolationUtils.violation(viewModel);
        return R.ok(msfWxPayService.wxAppletPay(viewModel));
    }

    //微信小程序回调
    @PostMapping("/wxApplet/notifyUrl")
    @AuthIgnore
    public String wxNotifyUrl(HttpServletRequest request, HttpServletResponse response) {

        return msfWxPayService.wxPayNotifyUrl(request, response, "applet");
    }

    @PostMapping("/wxPublic")
    @ApiOperation(value = "公众号支付")
    @ApiOperationSort(2)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestBody, name = "body", value = "{\"code\":\"code 必填\"}", required = true)
    })
    public R<Map<String, String>> wxPublic(@RequestBody @ApiIgnore String viewmodelJson) throws Exception {

        IWxPayService IAuthorityService = SpringUtils.getBean(IWxPayService.class);

        Class<? extends OrderSubmitRequest> clsViewModel = ReflexUtils.getOrderSubmitRequest(IAuthorityService);

        OrderSubmitRequest viewModel = JSONObject.parseObject(viewmodelJson, clsViewModel);

        //数据校验
        ViolationUtils.violation(viewModel);
        return R.ok(msfWxPayService.wxPublic(viewModel));
    }

    //微信公众号回调
    @PostMapping("/wxPublic/notifyUrl")
    @AuthIgnore
    public String wxPublicNotifyUrl(HttpServletRequest request, HttpServletResponse response) {

        return msfWxPayService.wxPayNotifyUrl(request, response, "public");
    }
}
