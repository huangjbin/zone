package com.zone.quartz_module.pojo;

import com.zone.quartz_module.param.DeviceParam;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Detail {

	private Long id;
	@NotNull(message="device_id")
	private Long object_id;//		设备编号
	@NotNull(message="param_id")
	private String param_id;//		参数ID
	@NotNull(message="data")
	private String data;//		数据
	@NotNull(message="sort")
	private Integer sort;//		顺序
	private Long task_id;//	NOT NULL	任务ID
	private String remark;//		备注
	@NotNull(message="task_type")
	private Integer task_type;//_type
	private Integer notice_user_type;//通知设备时该字段为空，通知人时，该字段非空，主要包括：1、微信2、短信3、邮件

	List<DeviceParam> deviceParams;
	private String deviceName;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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

	public String getParam_id() {
		return param_id;
	}

	public void setParam_id(String param_id) {
		this.param_id = param_id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public Long getObject_id() {
		return object_id;
	}

	public void setObject_id(Long object_id) {
		this.object_id = object_id;
	}

	public Integer getNotice_user_type() {
		return notice_user_type;
	}

	public void setNotice_user_type(Integer notice_user_type) {
		this.notice_user_type = notice_user_type;
	}

	public Integer getTask_type() {
		return task_type;
	}

	public void setTask_type(Integer task_type) {
		this.task_type = task_type;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Detail{" +
				"id=" + id +
				", task_id=" + task_id +
				", object_id=" + object_id +
				", param_id='" + param_id + '\'' +
				", data='" + data + '\'' +
				", sort=" + sort +
				", remark='" + remark + '\'' +
				", notice_user_type=" + notice_user_type +
				", task_type=" + task_type +
				'}';
	}
}
