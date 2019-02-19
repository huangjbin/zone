package com.zone.quartz_module.pojo;

public class ModuleResponseBean {

    private Integer code;//	[number]	是		展开
    private String message;//	[string]	是		展开
    private Devices data;//	[object]	是

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

    public Devices getData() {
        return data;
    }

    public void setData(Devices data) {
        this.data = data;
    }
}
