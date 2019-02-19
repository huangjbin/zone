package com.zone.quartz_module.param;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ParamBean {
    @Valid
    @NotNull(message="token")
    private String token;
    @Valid
    @NotNull(message="appTypeId")
    private Integer appTypeId;
    private String language;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getAppTypeId() {
        return appTypeId;
    }

    public void setAppTypeId(Integer appTypeId) {
        this.appTypeId = appTypeId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "ParamBean{" +
                "token='" + token + '\'' +
                ", appTypeId=" + appTypeId +
                ", language='" + language + '\'' +
                '}';
    }
}
