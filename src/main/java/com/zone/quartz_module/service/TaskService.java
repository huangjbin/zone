package com.zone.quartz_module.service;

import com.zone.quartz_module.common.BaseResult;
import com.zone.quartz_module.param.FindDevLogBean;
import com.zone.quartz_module.pojo.Schedule_Task_Detail;
import com.zone.quartz_module.pojo.Task_Detail;

import java.util.List;


public interface TaskService {
	
	/**
	 * 查找任务
	 * @param pageNumber
	 * @param pageSize
	 * @param keyWord
	 * @param taskStatus
	 * @param language
	 * @return
	 * @throws Exception
	 */
	BaseResult selectTasks(Integer pageNumber, Integer pageSize, String keyWord, Integer taskStatus, String language,Long user_id,List<Long> org_ids,Integer status,Integer type) throws Exception;

	/**
	 * 添加任务
	 * @param task_detail
	 * @param language
	 * @return
	 * @throws Exception
	 */
	BaseResult addTask(Task_Detail task_detail,String language) throws Exception;

	/**
	 * 添加定时任务
	 * @param schedule_task_detail
	 * @param language
	 * @return
	 * @throws Exception
	 */
	BaseResult addScheduleTask(Schedule_Task_Detail schedule_task_detail, String language) throws Exception;
	BaseResult addScheduleTasks(List<Schedule_Task_Detail> schedule_task_details, String language) throws Exception;
	
	/**
	 * 通过id查询任务
	 * @param id
	 * @return
	 */
	BaseResult findTasksbyId(Long id, String language) throws Exception;
	/**
	 * 通过id查询定时器
	 * @param id
	 * @return
	 */
	BaseResult findScheduleTaskbyId(Long id, String language) throws Exception;

	BaseResult findTasksbyIds(List<Integer> ids, String language) throws Exception;

	BaseResult findTasksCount(String language) throws Exception;
	
	/**
	 * 设置任务状态
	 * @param ids
	 * @return
	 */
	BaseResult updateTaskScheduleStatusbyId(List<Integer> ids, Integer status, String language) throws Exception;

	BaseResult findUsersByTaskIds(List<Integer> ids, String language) throws Exception;
	BaseResult findTaskIdByUTId(List<Integer> ids,Integer userId, String language) throws Exception;

	/**
	 * 修改定时器
	 * @param schedule_task_detail
	 * @param language
	 * @return
	 * @throws Exception
	 */
	BaseResult updateScheduleTask(Schedule_Task_Detail schedule_task_detail, String language) throws Exception;

	/**
	 * 根据id修改任务以及detail
	 * @param task_detail
	 * @param language
	 * @return
	 * @throws Exception
	 */
	BaseResult updateTask(Task_Detail task_detail, String language) throws Exception;

	BaseResult findDevLog(FindDevLogBean bean,Long user_id,List<Long> org_ids) throws Exception;
	BaseResult appfindDevLog(FindDevLogBean bean,Long user_id,List<Long> org_ids) throws Exception;

}
