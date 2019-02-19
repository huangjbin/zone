package com.zone.quartz_module.param;

import com.zone.quartz_module.pojo.TaskLog;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class InsertTaskLogBean {
    @Valid
    @NotNull(message="taskLog")
    private List<TaskLog> taskLogs;
    private String language;

    public List<TaskLog> getTaskLogs() {
        return taskLogs;
    }

    public void setTaskLogs(List<TaskLog> taskLogs) {
        this.taskLogs = taskLogs;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
