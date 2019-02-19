package com.zone.quartz_module.mapper;

import com.zone.quartz_module.param.FindTask;
import com.zone.quartz_module.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TaskMapper {
	/**
	 * 修改任务详情
	 * @param taskDetailParam
	 * @return
	 */
	Integer updateTaskDetail(Detail taskDetailParam);

	/**
	 * 添加定时器
	 * @param schedule
	 * @return
	 */
	Long addTaskSchedule(Schedule schedule);

	/**
	 * 添加任务
	 * @param task
	 * @return
	 */
	Long addTask(Task task);

	/**
	 * 添加任务明细
	 * @param detail
	 * @return
	 */
	Long addTaskDetail(Detail detail);

	/**
	 * 查询定时器总数
	 * @return
	 */
	Long findScheduleCount(FindTask findTask);

	Integer getScheduleCount();

	/**
	 * 查询任务（id）
	 * @param id
	 */
	TaskScheduleDetail findScheduleTaskbyId(Long id);
	Task_Detail findTaskbyId(Long id);

	/**
	 * 根据多个多个条件查询定时器列表
	 * @param findTask
	 * @return
	 */
	List<Schedule_Task_Detail> findSchedule(FindTask findTask);

	/**
	 * 根据任务id查找任务定时
	 * @param task_id
	 * @return
	 */
	Schedule selectScheduleByTaskId(Long task_id);

	Schedule_Task_Detail selectScheduleTDByScheduleId(Long task_id);

	/**
	 * 根据任务id查找任务明细
	 * @param task_id
	 * @return
	 */
	List<Detail> selectTaskDetailByTaskId(Long task_id);

	/**
	 * 删除任务
	 * @param id
	 * @return
	 */
	Integer updateTaskScheduleStatusbyId(@Param("id") Long id, @Param("status") Integer status);

	/**
	 * 修改任务定时器
	 * @param schedule_task_detail
	 * @return
	 */
	Integer updateTaskSchedule(Schedule_Task_Detail schedule_task_detail);

	/**
	 * 修改任务
	 * @param task_detail
	 * @return
	 */
	Integer updateTask(Task_Detail task_detail);

	/**
	 * 添加任务后更新定时器的task_id
	 * @param id
	 * @param task_id
	 * @return
	 */
	Integer updateScheduleTaskId(@Param("id") Long id, @Param("task_id") Long task_id);

	/**
	 * 根据task_id删除derail
	 * @param task_id
	 * @return
	 */
	Integer deleteDetailByTaskId(Long task_id);

	/**
	 * 根据id查找定时器
	 * @param scheduleId
	 * @return
	 */
	Schedule selectScheduleById(Long scheduleId);

	List<Integer> selectuIdBytaskId(@Param("task_ids")List<Integer> task_ids);

	List<Integer> selectTIdByUtaskId(@Param("task_ids") List<Integer> task_ids, @Param("user_id") Integer user_id);

}