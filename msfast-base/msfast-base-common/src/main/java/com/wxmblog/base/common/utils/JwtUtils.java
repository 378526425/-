package com.wxmblog.base.common.utils;

import com.wxmblog.base.common.constant.ConfigConstants;
import com.wxmblog.base.common.constant.SecurityConstants;
import com.wxmblog.base.common.text.Convert;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Jwt工具类
 *
 * @author
 */
public class JwtUtils {

    public static String secret = ConfigConstants.SECRET();

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims parseToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * 根据令牌获取用户标识
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static String getUserKey(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.USER_KEY);
    }

    /**
     * 根据令牌获取用户标识
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static String getUserKey(Claims claims) {
        return getValue(claims, SecurityConstants.USER_KEY);
    }

    /**
     * 根据令牌获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static String getUserId(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_USER_ID);
    }

    /**
     * 根据身份信息获取用户ID
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static String getUserId(Claims claims) {
        return getValue(claims, SecurityConstants.DETAILS_USER_ID);
    }

    /**
     * 根据令牌获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUserName(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_USERNAME);
    }

    /**
     * 根据身份信息获取用户名
     *
     * @param claims 身份信息
     * @return 用户名
     */
    public static String getUserName(Claims claims) {
        return getValue(claims, SecurityConstants.DETAILS_USERNAME);
    }

    /**
     * 根据身份信息获取键值
     *
     * @param claims 身份信息
     * @param key    键
     * @return 值
     */
    public static String getValue(Claims claims, String key) {
        return Convert.toStr(claims.get(key), "");
    }

    /**
     * @Description: 用户token
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/6/24 下午2:53
     */
    public static String getUserRedisToken(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.REDIS_TOKEN);
    }

    /**
     * @Description: 此刻在线用户token
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/6/24 下午2:53
     */
    public static String getOnlineUSerToken(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.MANY_ONLINE_USER_TOKEN);
    }
}
