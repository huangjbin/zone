package com.zone.quartz_module.pojo;

import java.io.Serializable;

public class TaskDataBean implements Serializable {
	private String act;
	private Long t_id;
	private Integer type;
	private String d_id;
	private String d_task_type;
	private String d_notice_user_type;
	private String str_device_id;
	private String param_ids;
	private String log_id;
	private String str_data;
	private String str_sort;
	
	
	public String getStr_sort() {
		return str_sort;
	}
	public void setStr_sort(String str_sort) {
		this.str_sort = str_sort;
	}
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}

	public String getD_task_type() {
		return d_task_type;
	}

	public void setD_task_type(String d_task_type) {
		this.d_task_type = d_task_type;
	}

	public String getD_notice_user_type() {
		return d_notice_user_type;
	}

	public void setD_notice_user_type(String d_notice_user_type) {
		this.d_notice_user_type = d_notice_user_type;
	}

	public Long getT_id() {
		return t_id;
	}
	public void setT_id(Long t_id) {
		this.t_id = t_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getD_id() {
		return d_id;
	}
	public void setD_id(String d_id) {
		this.d_id = d_id;
	}
	public String getParam_ids() {
		return param_ids;
	}
	public void setParam_ids(String param_ids) {
		this.param_ids = param_ids;
	}
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	
	public String getStr_device_id() {
		return str_device_id;
	}
	public void setStr_device_id(String str_device_id) {
		this.str_device_id = str_device_id;
	}
	public String getStr_data() {
		return str_data;
	}
	public void setStr_data(String str_data) {
		this.str_data = str_data;
	}
	
	
	

}
