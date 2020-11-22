package com.atguigu.crowd.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "show.message")
public class ShowMessageConfigProperties {
    private String host;
    private String path;
    private String method;
    private String phoneNum;
    private String appcode;
    private String skin;
    public ShowMessageConfigProperties(){}

    public ShowMessageConfigProperties(String host, String path, String method, String phoneNum, String appcode, String skin) {
        this.host = host;
        this.path = path;
        this.method = method;
        this.phoneNum = phoneNum;
        this.appcode = appcode;
        this.skin = skin;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }
}
