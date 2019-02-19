package com.zone.quartz_module.pojo;

import java.io.Serializable;
import java.util.Date;


/**
 * 定时任务实体类
 * @author admin
 * @date 2017-11-25 下午 19:06
 */
public class ScheduleJob implements Serializable{
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 任务名
     */
    private String jobName;
    /**
     * 任务组
     */
    private String jobGroup;
    /**
     * 要执行的方法的名称
     */
    private String methodName;
    /**
     * 要执行的方法所在的class路径
     */
    private String beanClass;

    /**
     * 时间表达式
     */
    private String cronExpression;
    /**
     * 参数
     */
    private String param;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    
    private int status;
    
    

    public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public ScheduleJob() {
    }

    

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }
    
   

	public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Override
	public String toString() {
		return "ScheduleJob [id=" + id + ", jobName=" + jobName + ", jobGroup=" + jobGroup + ", methodName="
				+ methodName + ", beanClass=" + beanClass + ", cronExpression=" + cronExpression + ", param=" + param
				+ ", remark=" + remark + ", createTime=" + createTime + "]";
	}

	

    
}