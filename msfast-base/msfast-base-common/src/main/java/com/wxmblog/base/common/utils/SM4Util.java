package com.wxmblog.base.common.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.wxmblog.base.common.constant.ConfigConstants;

/**
 * @program: baban
 * @description:
 * @author: Mr.Wang
 * @create: 2022-09-15 10:37
 **/

public class SM4Util {
    static byte[] keys = ConfigConstants.SM4_KEY().getBytes();

    public static String encryptHex(String content) {
        SymmetricCrypto sm4 = new SymmetricCrypto(SymmetricAlgorithm.DESede, keys);
        return sm4.encryptHex(content);
    }

    public static String decryptStr(String encryptHex) {
        SymmetricCrypto sm4 = new SymmetricCrypto(SymmetricAlgorithm.DESede, keys);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        return decryptStr;
    }

    public static String encryptHex(String content,String key) {
        SymmetricCrypto sm4 = new SymmetricCrypto(SymmetricAlgorithm.DESede, key.getBytes());
        return sm4.encryptHex(content);
    }

    public static String decryptStr(String encryptHex,String key) {
        SymmetricCrypto sm4 = new SymmetricCrypto(SymmetricAlgorithm.DESede, key.getBytes());
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        return decryptStr;
    }
}
