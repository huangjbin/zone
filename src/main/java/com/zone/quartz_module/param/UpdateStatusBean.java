package com.zone.quartz_module.param;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class UpdateStatusBean {
    @Valid
    @NotNull(message="taskLogIds")
    private List<UpdateTaskLog> taskLogIds;
    private String language;

    public List<UpdateTaskLog> getTaskLogIds() {
        return taskLogIds;
    }

    public void setTaskLogIds(List<UpdateTaskLog> taskLogIds) {
        this.taskLogIds = taskLogIds;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
