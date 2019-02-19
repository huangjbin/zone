package com.zone.quartz_module.pojo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
public class Task {

	private Long id;//	NOT NULL	关键字
	@NotNull(message="name")
	private String name;//	NOT NULL	任务名称
	private Long create_time;//		创建时间	
	private String remark;//		备注
	@NotNull(message="status")
	private Integer status;
	private Long user_id;
	private Long org_id;
	private String action;


	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Long getCreate_time() {
		return create_time;
	}



	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Task{" +
				"id=" + id +
				", name='" + name + '\'' +
				", create_time=" + create_time +
				", remark='" + remark + '\'' +
				", status=" + status +
				", user_id=" + user_id +
				", org_id=" + org_id +
				'}';
	}
}
