package com.zone.quartz_module.service.impl;

import com.zone.quartz_module.common.BaseResult;
import com.zone.quartz_module.common.Page;
import com.zone.quartz_module.common.ResLanguage;
import com.zone.quartz_module.mapper.TaskLogMapper;
import com.zone.quartz_module.param.FindTaskLog;
import com.zone.quartz_module.param.UpdateTaskLog;
import com.zone.quartz_module.pojo.TaskLog;
import com.zone.quartz_module.service.TaskLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TaskLogServiceImpl implements TaskLogService {
	public static Logger log = LoggerFactory.getLogger(TaskLogServiceImpl.class);

	@Autowired
    TaskLogMapper taskLogMapper;

	/**
	 * 查询任务日志
	 */
	public BaseResult selectTaskLogs(Integer pageNumber, Integer pageSize, String keyWord, Integer result, String language) throws Exception {
		Page page = new Page();
		Long total = taskLogMapper.totalTaskLog();
		FindTaskLog findTask = new FindTaskLog();
		if(pageNumber==null && pageNumber<1){
			page.setTotal(total);
			if(pageSize!=null && pageSize>0){
				page.setPageSize(pageSize);
			}
			findTask.setMsg(keyWord);
			findTask.setResult(result);
			findTask.setPage(page);
		}else{
			page.setStartNumber((int) ((pageNumber - 1) * page.getPageSize()));
			page.setTotal(total);
			if(pageSize!=null && pageSize>0){
				page.setPageSize(pageSize);
			}
			findTask.setMsg(keyWord);
			findTask.setResult(result);
			findTask.setPage(page);
		}
		List<TaskLog> list = taskLogMapper.selectTaskLogs(findTask);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("task_log", list);
		map.put("page", page);
		return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), map);
	}

	/**
	 * 添加日志
	 */
	public List<TaskLog> addTaskLog(List<TaskLog> taskLogs) {
		for(int i=0;i<taskLogs.size();i++){
			taskLogs.get(i).setRun_time(new Date().getTime() / 1000);
			TaskLog taskLog = taskLogs.get(i);
			taskLogMapper.addTaskLog(taskLog);
			if(taskLog.getId()!=null){
				taskLogs.get(i).setId(taskLog.getId());
			}
		}

		return taskLogs;
	}

	/**
	 * 更新日志状态
	 */
	public Integer updateTaskLog(List<UpdateTaskLog> taskLogIds) throws Exception{
		for(UpdateTaskLog updateTaskLog : taskLogIds){
			Long time = new Date().getTime() / 1000;
			Map map = new HashMap();
			map.put("mResult",updateTaskLog.getResult());
			map.put("mEndTime",time);
			map.put("Id",updateTaskLog.getTaskLogId());
			taskLogMapper.updateTaskLog(map);
		}
		return taskLogIds.size();
	}
}
