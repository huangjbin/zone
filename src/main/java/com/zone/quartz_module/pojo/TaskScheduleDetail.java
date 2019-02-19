package com.zone.quartz_module.pojo;

import java.util.List;

public class TaskScheduleDetail extends Task{
	
	private Schedule schedule;
	private List<Detail> details;
	private Long total;


	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public List<Detail> getDetails() {
		return details;
	}
	public void setDetails(List<Detail> details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return super.toString()+"---"+"TaskScheduleDetail [schedule=" + schedule + ", details=" + details + "]";
	}




}
