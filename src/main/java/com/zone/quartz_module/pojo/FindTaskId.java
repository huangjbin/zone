package com.zone.quartz_module.pojo;

import java.util.List;

public class FindTaskId {
    private Integer user_id;
    private List<Integer> task_ids;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public List<Integer> getTask_ids() {
        return task_ids;
    }

    public void setTask_ids(List<Integer> task_ids) {
        this.task_ids = task_ids;
    }
}
