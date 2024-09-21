package com.wxmblog.base.role.authority;

import com.wxmblog.base.auth.authority.service.IAdminAuthorityService;
import com.wxmblog.base.auth.common.rest.request.LoginRequest;
import com.wxmblog.base.common.constant.ConfigConstants;
import com.wxmblog.base.common.entity.LoginUser;
import com.wxmblog.base.common.enums.BaseUserTypeEnum;
import com.wxmblog.base.common.utils.SpringBeanUtils;
import com.wxmblog.base.role.common.rest.response.LoginResponse;
import com.wxmblog.base.role.service.IAdminLoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-13 10:41
 **/
@Service
public class AdminAuthority extends IAdminAuthorityService {

    @Override
    public LoginUser adminLogin(LoginRequest loginRequest) {

        if (ConfigConstants.ROLE_ADMIN_USER_NAME().equals(loginRequest.getUsername()) && ConfigConstants.ROLE_ADMIN_PASSWORD().equals(loginRequest.getPassword())) {
            LoginUser loginUser = new LoginUser();
            loginUser.setId(0);
            loginUser.setUserType(BaseUserTypeEnum.SUPER_ADMIN);
            LoginResponse loginResponse = new LoginResponse();
            BeanUtils.copyProperties(loginUser, loginResponse);
            loginUser.setInfo(loginResponse);
            return loginUser;
        } else if (ConfigConstants.ROLE_TOURIST_USER_NAME().equals(loginRequest.getUsername()) && ConfigConstants.ROLE_TOURIST_PASSWORD().equals(loginRequest.getPassword())) {

            LoginUser loginUser = new LoginUser();
            loginUser.setId(0);
            loginUser.setUserType(BaseUserTypeEnum.TOURIST);
            LoginResponse loginResponse = new LoginResponse();
            BeanUtils.copyProperties(loginUser, loginResponse);
            loginUser.setInfo(loginResponse);
            return loginUser;
        } else {
            IAdminLoginService adminLoginService = SpringBeanUtils.getBean(IAdminLoginService.class);
            if (adminLoginService != null) {
                return adminLoginService.adminLogin(loginRequest);
            }
        }

        return null;
    }

    @Override
    public void logout() {

        IAdminLoginService adminLoginService = SpringBeanUtils.getBean(IAdminLoginService.class);
        if (adminLoginService != null) {
            adminLoginService.logout();
        }
    }
}
