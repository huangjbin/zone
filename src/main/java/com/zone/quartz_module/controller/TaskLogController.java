package com.zone.quartz_module.controller;


import com.zone.quartz_module.common.BaseResult;
import com.zone.quartz_module.common.ResLanguage;
import com.zone.quartz_module.param.InsertTaskLogBean;
import com.zone.quartz_module.param.UpdateStatusBean;
import com.zone.quartz_module.param.UpdateTaskLog;
import com.zone.quartz_module.pojo.TaskLog;
import com.zone.quartz_module.service.TaskLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 任务日志
 * 
 * @author huangjunbin
 *
 */
@Controller
@RequestMapping(value = "/taskLog")
public class TaskLogController {

	public static Logger log = LoggerFactory.getLogger(TaskLogController.class);

	@Autowired
	private TaskLogService taskLogService;

	/**
	 * 查询日志
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findTaskLog", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult findTaskLog(@RequestBody Map map) throws Exception {

		Integer pageNumber = (Integer)map.get("pageNumber");
		Integer pageSize = (Integer)map.get("pageSize");
		String keyWord = (String)map.get("keyWord");
		Integer result = (Integer)map.get("result");
		String language = (String)map.get("language");
		BaseResult baseResult = taskLogService.selectTaskLogs(pageNumber, pageSize, keyWord, result, language);
		return baseResult;
	}

	@RequestMapping(value = "/module/updateTaskLogStatus", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public BaseResult updateTaskLogStatus(@Valid @RequestBody UpdateStatusBean updateStatusBean, BindingResult result,HttpServletRequest request) throws Exception {
		String language = updateStatusBean.getLanguage();
		if (result.hasErrors()){
			List<ObjectError> errorList = result.getAllErrors();
			for(ObjectError error : errorList){
				return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(language, error.getDefaultMessage()), null);
			}
		}
		List<UpdateTaskLog> taskLogIds = updateStatusBean.getTaskLogIds();
		taskLogService.updateTaskLog(taskLogIds);
		return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), null);
	}

	@RequestMapping(value = "/module/insertTaskLog", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public BaseResult insertTaskLog(@Valid @RequestBody InsertTaskLogBean bean, BindingResult result, HttpServletRequest request) throws Exception {
		if (result.hasErrors()){
			List<ObjectError> errorList = result.getAllErrors();
			for(ObjectError error : errorList){
				return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(bean.getLanguage(), error.getDefaultMessage()), null);
			}
		}
		List<TaskLog> taskLogs = bean.getTaskLogs();
		String language = bean.getLanguage();
		taskLogs = taskLogService.addTaskLog(taskLogs);
		return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), taskLogs);
	}


}
