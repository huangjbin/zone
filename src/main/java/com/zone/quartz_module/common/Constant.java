package com.zone.quartz_module.common;

import org.springframework.stereotype.Component;

/**
 * 常量
 * 
 * @author admin
 * @date 2017-11-25 19:06
 */
@Component
public class Constant {
	/**
	 * 分页条显示页数
	 */
	public static final int PAGENUMBER = 5;
	/**
	 * 定时任务启动状态
	 */
	public static final int STATUS_RUNNING = 0;
	/**
	 * 定时任务暂停状态
	 */
	public static final int STATUS_NOT_RUNNING = 1;

	public static final String SCHEDULE_JOB_DATA_KEY = "scheduleJob_data_key";
	public static final String JOB_GROUP_NAME = "job_group_name";

	//JSON_TASK
	public static final String TASK_XC = "TASK_XC";//交换机 ==> TASK_XC
	public static final String JSON_TASK_KEY = "JSON_TASK_KEY";//路邮键 ==> JSON_TASK_KEY，
	public static final String JSON_TASK_Q = "JSON_TASK_Q";//队列 ==> JSON_TASK_Q

	//JSON_RULE_RES
	public static final String PTS_XC = "PTS_XC";//交换机 ==> PTS_XC
	public static final String JSON_TASK_RES_KEY = "JSON_TASK_RES_KEY";//路邮键 ==> JSON_TASK_RES_KEY
	public static final String JSON_TASK_RES_Q = "JSON_TASK_RES_Q";//队列 ==> JSON_TASK_RES_Q

}
