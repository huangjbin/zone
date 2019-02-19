package com.zone.quartz_module.pojo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
@Valid
public class Task_Detail extends Task{
    @NotNull(message="details")
    private List<Detail> details;

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }
}
