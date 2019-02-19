package com.zone.quartz_module.pojo;

import javax.validation.constraints.NotNull;

public class Schedule {
	private Long id;//	NOT NULL	关键字
	private Long task_id=0l;//	NOT NULL	任务ID
	private String seconds;//		秒
	private String minutes;//		分
	private String hours;//		时
	private String dayofMonth;//		天
	private String month;//		月
	private String dayofWeek;//		星期
	private String year;//		年
	private Long create_time;//		创建时间
	private String remark;//		备注
	private Integer status;//
	@NotNull(message="name")
	private String name;//
	@NotNull(message="user_id")
	private Long user_id;
	@NotNull(message="org_id")
	private Long org_id;
	private Integer type;
	private String userName;


	private String cronexpression;


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Long getTask_id() {
		return task_id;
	}

	public void setTask_id(Long task_id) {
		this.task_id = task_id;
	}

	public String getSeconds() {
		return seconds;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getDayofMonth() {
		return dayofMonth;
	}

	public void setDayofMonth(String dayofMonth) {
		this.dayofMonth = dayofMonth;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDayofWeek() {
		return dayofWeek;
	}

	public void setDayofWeek(String dayofWeek) {
		this.dayofWeek = dayofWeek;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCronexpression() {
		return cronexpression;
	}

	public void setCronexpression(String cronexpression) {
		this.cronexpression = cronexpression;
	}


	@Override
	public String toString() {
		return "Schedule{" +
				"id=" + id +
				", task_id=" + task_id +
				", seconds='" + seconds + '\'' +
				", minutes='" + minutes + '\'' +
				", hours='" + hours + '\'' +
				", dayofMonth='" + dayofMonth + '\'' +
				", month='" + month + '\'' +
				", dayofWeek='" + dayofWeek + '\'' +
				", year='" + year + '\'' +
				", create_time=" + create_time +
				", remark='" + remark + '\'' +
				", status=" + status +
				", name='" + name + '\'' +
				", cronexpression='" + cronexpression + '\'' +
				'}';
	}
}
