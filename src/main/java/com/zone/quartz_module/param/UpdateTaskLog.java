package com.zone.quartz_module.param;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class UpdateTaskLog {
    @Valid
    @NotNull(message="taskLogId")
    private Long taskLogId;
    @Valid
    @NotNull(message="result")
    private Integer result;

    public Long getTaskLogId() {
        return taskLogId;
    }

    public void setTaskLogId(Long taskLogId) {
        this.taskLogId = taskLogId;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
