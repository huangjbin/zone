package com.zone.quartz_module.pojo;

import com.zone.quartz_module.param.DeviceParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TaskLog {

	private Long id;//	NOT NULL	关键字
	@Valid
	@NotNull(message="task_id")
	private Long task_id;//		任务编号
	@Valid
	@NotNull(message="task_detail_id")
	private Long task_detail_id;//		任务明细编号
	@Valid
	@NotNull(message="msg")
	private String msg;//		JSON	命令的消息体
	private Long run_time;//		执行时间	
	private Long end_time;//		结束时间
	@Valid
	@NotNull(message="result")
	private Integer result;//		执行结果	0：成功，1：失败
	private String remark;//		备注	错误代码等

	private String devName;
	private List<DeviceParam> deviceParams;
	private String sno;
	private String data;


	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public List<DeviceParam> getDeviceParams() {
		return deviceParams;
	}

	public void setDeviceParams(List<DeviceParam> deviceParams) {
		this.deviceParams = deviceParams;
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

	public Long getTask_detail_id() {
		return task_detail_id;
	}

	public void setTask_detail_id(Long task_detail_id) {
		this.task_detail_id = task_detail_id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getRun_time() {
		return run_time;
	}

	public void setRun_time(Long run_time) {
		this.run_time = run_time;
	}

	public Long getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Long end_time) {
		this.end_time = end_time;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "TaskLog [id=" + id + ", task_id=" + task_id + ", task_detail_id=" + task_detail_id + ", msg=" + msg
				+ ", run_time=" + run_time + ", end_time=" + end_time + ", result=" + result + ", remark=" + remark
				+ "]";
	}
	
	

}
