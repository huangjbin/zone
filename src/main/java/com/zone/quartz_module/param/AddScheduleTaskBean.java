package com.zone.quartz_module.param;

import com.zone.quartz_module.pojo.Schedule_Task_Detail;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
@Valid
public class AddScheduleTaskBean{
    private String language;

    @NotNull(message="schedule_task_details")
    private List<Schedule_Task_Detail> schedule_task_details;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Schedule_Task_Detail> getSchedule_task_details() {
        return schedule_task_details;
    }

    public void setSchedule_task_details(List<Schedule_Task_Detail> schedule_task_details) {
        this.schedule_task_details = schedule_task_details;
    }
}
