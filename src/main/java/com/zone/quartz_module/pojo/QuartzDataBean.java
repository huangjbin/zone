package com.zone.quartz_module.pojo;

import java.util.List;

public class QuartzDataBean {
    private String act;
    private Long t_id;
    private String type;
    private Long d_id;
    private Long device_id;
    private List<Long> param_ids;
    private List<Float> data;
    private Long log_id;
    private Integer sort;
    private Integer task_type;


    public Integer getTask_type() {
        return task_type;
    }

    public void setTask_type(Integer task_type) {
        this.task_type = task_type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public Long getT_id() {
        return t_id;
    }

    public void setT_id(Long t_id) {
        this.t_id = t_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getD_id() {
        return d_id;
    }

    public void setD_id(Long d_id) {
        this.d_id = d_id;
    }

    public Long getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Long device_id) {
        this.device_id = device_id;
    }

    public List<Long> getParam_ids() {
        return param_ids;
    }

    public void setParam_ids(List<Long> param_ids) {
        this.param_ids = param_ids;
    }

    public List<Float> getData() {
        return data;
    }

    public void setData(List<Float> data) {
        this.data = data;
    }

    public Long getLog_id() {
        return log_id;
    }

    public void setLog_id(Long log_id) {
        this.log_id = log_id;
    }

    @Override
    public String toString() {
        return "QuartzDataBean{" +
                "act='" + act + '\'' +
                ", t_id=" + t_id +
                ", type='" + type + '\'' +
                ", d_id=" + d_id +
                ", device_id=" + device_id +
                ", param_ids=" + param_ids +
                ", data=" + data +
                ", log_id=" + log_id +
                '}';
    }
}
//{
//        "act": "task_data",
//        "t_id": "2739473952",
//        "type": "task",
//        "d_id": "227492749",
//        "device_id": "53623636,
//        "param_ids": ["227492749", "227492749"],
//        "log_id": 789456,
//        "data": [byte，byte]
//        }