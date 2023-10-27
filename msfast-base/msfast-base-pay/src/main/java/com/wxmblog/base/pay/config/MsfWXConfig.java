package com.wxmblog.base.pay.config;

import com.github.wxpay.sdk.WXPayConfig;
import com.wxmblog.base.common.constant.ConfigConstants;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MsfWXConfig implements WXPayConfig {
    private byte[] certData;

    public MsfWXConfig() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("apiclient_cert.p12");
        InputStream certStream = classPathResource.getInputStream();
        this.certData = IOUtils.toByteArray(certStream);
        certStream.read(this.certData);
        certStream.close();
    }

    @Override
    public String getAppID() {
        return ConfigConstants.WX_APPLET_APPID();
    }

    @Override
    public String getMchID() {
        return ConfigConstants.PAY_WX_APPLET_MCHID();
    }

    @Override
    public String getKey() {
        return ConfigConstants.PAY_WX_APPLET_KEY();
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
