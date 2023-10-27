package com.wxmblog.base.auth.controller;

import com.wxmblog.base.auth.service.MsfConfigService;
import com.wxmblog.base.auth.common.enums.ConfigAccessEnum;
import com.wxmblog.base.auth.entity.MsfConfigEntity;
import com.wxmblog.base.common.annotation.AuthIgnore;
import com.wxmblog.base.common.enums.BaseExceptionEnum;
import com.wxmblog.base.common.exception.JrsfException;
import com.wxmblog.base.common.web.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-10-10 15:45:08
 */
@RestController
@RequestMapping("msfast/sysconfig")
@Api(tags = "系统配置")
public class MsfConfigController {

    @Autowired
    private MsfConfigService msfConfigService;

    /**
     * 信息
     */
    @GetMapping("/value")
    @ApiOperation(value = "查询系统配置值")
    @ApiOperationSort(value = 1)
    @AuthIgnore
    public R<String> value(@RequestParam String code) {
        MsfConfigEntity msfConfigEntity = msfConfigService.getConfigByCode(code);
        if (msfConfigEntity != null) {

            if (ConfigAccessEnum.INNER.equals(msfConfigEntity.getAccess())) {
                throw new JrsfException(BaseExceptionEnum.NO_PERMISSION_EXCEPTION);
            }
            return R.ok(msfConfigEntity.getValue());
        }
        return R.ok();
    }
}
