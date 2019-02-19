package com.zone.quartz_module.pojo;

import java.util.List;

public class SystemUserResponseBean {

    private Integer code;//	[number]	是		展开
    private String message;//	[string]	是		展开
    private List<User> data;//	[object]	是

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
