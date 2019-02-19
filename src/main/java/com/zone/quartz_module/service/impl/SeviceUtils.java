package com.zone.quartz_module.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone.quartz_module.param.UpdateTaskLog;
import com.zone.quartz_module.pojo.QuartzDataBean;
import com.zone.quartz_module.pojo.TaskLog;
import com.zone.quartz_module.rabbitmq.Send;
import com.zone.quartz_module.service.TaskLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class SeviceUtils {
	
	@Autowired
	private TaskLogService taskLogService;
	
	public static SeviceUtils seviceUtils;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	Send sender;


	
	public static Long addTaskLog(TaskLog tLog){
		List<TaskLog> list = new ArrayList<>();
		list.add(tLog);
		List<TaskLog> taskLogs =  seviceUtils.taskLogService.addTaskLog(list);
		if(taskLogs.size()>0){
			return taskLogs.get(0).getId();
		}else{
			return null;
		}
	}

	public static List<QuartzDataBean> changeStr2TaskDataBean(String param) throws IOException {
		JavaType javaType = seviceUtils.objectMapper.getTypeFactory().constructParametricType(ArrayList.class, QuartzDataBean.class);
		List<QuartzDataBean> quartzDataBeans =  (List<QuartzDataBean>)seviceUtils.objectMapper.readValue(param, javaType);
		return quartzDataBeans;
	}

	public static String change_map(Map map) throws JsonProcessingException {
		String result = seviceUtils.objectMapper.writeValueAsString(map);
		return result;
	}

	public static void sendMap2MQ(Map map) throws JsonProcessingException {
		seviceUtils.sender.sendMsg(seviceUtils.objectMapper.writeValueAsString(map));
//		seviceUtils.objectMapper.writeValueAsString(map)
	}
	
	public static void updateTaskLog(Long taskLogId,Integer result) throws Exception{
		List<UpdateTaskLog> list = new ArrayList<>();
		UpdateTaskLog updateTaskLog = new UpdateTaskLog();
		updateTaskLog.setResult(result);
		updateTaskLog.setTaskLogId(taskLogId);
		list.add(updateTaskLog);
		seviceUtils.taskLogService.updateTaskLog(list);
	}

    // 在方法上加上注解@PostConstruct，这样方法就会在Bean初始化之后被Spring容器执行（注：Bean初始化包括，实例化Bean，并装配Bean的属性（依赖注入））。
    @PostConstruct
    public void init() {
    	seviceUtils = this;
    }
	
}










