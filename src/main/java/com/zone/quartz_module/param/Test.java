package com.zone.quartz_module.param;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
@Valid
public class Test {
    private String test1;
    @NotNull(message="test2")
    private Integer test2;

    public String getTest1() {
        return test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public Integer getTest2() {
        return test2;
    }

    public void setTest2(Integer test2) {
        this.test2 = test2;
    }
}
