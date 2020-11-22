package com.atguigu.crowd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ali.pay")
public class AlipayConfig {
   private String appId;
   private String merchantPrivateKey;
   private String alipayPublicKey;
   private String notifyUrl;
   private String returnUrl;
   private String signType;
   private String charset;
   private String gatewayUrl;

    public AlipayConfig( ) {
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public void setMerchantPrivateKey(String merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public AlipayConfig(String appId, String merchantPrivateKey, String alipayPublicKey, String notifyUrl, String returnUrl, String signType, String charset, String gatewayUrl) {
        this.appId = appId;
        this.merchantPrivateKey = merchantPrivateKey;
        this.alipayPublicKey = alipayPublicKey;
        this.notifyUrl = notifyUrl;
        this.returnUrl = returnUrl;
        this.signType = signType;
        this.charset = charset;
        this.gatewayUrl = gatewayUrl;
    }
}
