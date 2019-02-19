package com.zone.quartz_module.service;


import com.zone.quartz_module.common.BaseResult;
import com.zone.quartz_module.param.UpdateTaskLog;
import com.zone.quartz_module.pojo.TaskLog;

import java.util.List;

public interface TaskLogService {
	
	/**
	 * 查询任务日志
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	BaseResult selectTaskLogs(Integer pageNumber, Integer pageSize, String keyWord, Integer result, String language) throws Exception;

	
	/**
	 * 添加日志
	 * @param taskLogs
	 * @return
	 */
	List<TaskLog> addTaskLog(List<TaskLog> taskLogs);
	
	/**
	 * 更新日志状态
	 * @param taskLogId
	 * @param result
	 */
	Integer updateTaskLog(List<UpdateTaskLog> taskLogId) throws Exception;
   
}
