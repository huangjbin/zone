package com.zone.quartz_module.pojo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
@Valid
public class Schedule_Task_Detail extends Schedule {
    @NotNull(message="task_detail")
    private Task_Detail task_detail;

    public Task_Detail getTask_detail() {
        return task_detail;
    }

    public void setTask_detail(Task_Detail task_detail) {
        this.task_detail = task_detail;
    }
}
