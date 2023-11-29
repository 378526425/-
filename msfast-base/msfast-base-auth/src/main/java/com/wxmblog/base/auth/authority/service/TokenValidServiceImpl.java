package com.wxmblog.base.auth.authority.service;

import com.wxmblog.base.auth.common.annotation.AdminRequest;
import com.wxmblog.base.common.entity.LoginUser;
import com.wxmblog.base.common.enums.BaseUserTypeEnum;
import com.wxmblog.base.common.utils.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.HandlerMethod;

/**
 * @program: wxm-fast
 * @description: 业务权限相关校验
 * @author: Mr.Wang
 * @create: 2022-06-20 09:57
 **/

@Service
public class TokenValidServiceImpl implements TokenValidService {

    @Override
    public Boolean hasPermission(Object handler) {

        //管理员才能访问的接口
        AdminRequest superAdminController = ((HandlerMethod) handler).getMethod().getDeclaringClass().getAnnotation(AdminRequest.class);
        if (superAdminController != null) {
            LoginUser loginUser = TokenUtils.info(Object.class);
            if (loginUser == null || loginUser.getUserType() == null) {
                return false;
            }
            GetMapping getMapping = ((HandlerMethod) handler).getMethodAnnotation(GetMapping.class);
            if (getMapping == null && !BaseUserTypeEnum.SUPER_ADMIN.equals(loginUser.getUserType())) {
                //游客只能访问get请求
                return false;
            }
        }

        return true;
    }
}
