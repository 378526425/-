package com.wxmblog.base.auth.authority.service;

public interface TokenValidService {

    Boolean hasPermission(Object handler);
}
