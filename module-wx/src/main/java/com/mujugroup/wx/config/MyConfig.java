package com.mujugroup.wx.config;

import com.github.wxpay.sdk.WXPayConfig;
import java.io.InputStream;

public class MyConfig implements WXPayConfig {
    public static final String FAIL = "FAIL";
    //public static final String ERROR = "ERROR";
    public static final String SUCCESS = "SUCCESS";
    public static final String REFUND_NOT_ENOUGH_MONEY = "NOTENOUGH";
    public static final String REFUND_SOURCE_RECHARGE_FUNDS = "REFUND_SOURCE_RECHARGE_FUNDS";
    public static final String REFUND_SOURCE_UNSETTLED_FUNDS = "REFUND_SOURCE_UNSETTLED_FUNDS";

    //private byte[] certData= new byte[1024];

    @Override
    public String getAppID() {
        return "wx9e8bd69c244bf42e";
    }


    @Override
    public String getMchID() {
        return "1508426431";
    }

    @Override
    public String getKey() {
        return "ilfxJVSYE2s8b3W0JB6KtM5Ia9pkRcGc";
    }

    @Override
    public InputStream getCertStream() {
        return getClass().getClassLoader().getResourceAsStream("key/cert.p12");
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    /**
     * 密钥
     */
    public String getSecret() {
        return "a391f8df148e1b319ab566a1820d6dc9";
    }

}
