package com.zone.quartz_module.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone.quartz_module.common.BaseResult;
import com.zone.quartz_module.common.Constant;
import com.zone.quartz_module.common.Page;
import com.zone.quartz_module.common.ResLanguage;
import com.zone.quartz_module.exception.CustomException;
import com.zone.quartz_module.mapper.TaskLogMapper;
import com.zone.quartz_module.mapper.TaskMapper;
import com.zone.quartz_module.param.DeviceParam;
import com.zone.quartz_module.param.FindDevLogBean;
import com.zone.quartz_module.param.FindTask;
import com.zone.quartz_module.pojo.*;
import com.zone.quartz_module.quartz.job.DynamicJob;
import com.zone.quartz_module.service.TaskService;
import com.zone.quartz_module.utils.CommonUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {
    public static Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    TaskLogMapper taskLogMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Resource
    private Scheduler scheduler;

    @Resource
    private HttpAPIService httpAPIService;

    @Value("${module_net.find_user_by_ids}")
    public String url_findUserByIds;
    @Value("${module_net.finddevs_by_key}")
    public String url_finddevsByKey;

    @Value("${module_net.get_cron}")
    public String get_cron;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 添加任务（龙哥）
     *
     * @param task_detail
     * @param language
     * @return
     * @throws Exception
     */
    @Override
    public BaseResult addTask(Task_Detail task_detail, String language) throws Exception {
        String a = objectMapper.writeValueAsString(task_detail);
        log.info(a);
        // 1.向mon_task插入数据
        taskMapper.addTask(task_detail);
        Long taskId = task_detail.getId();
        if (taskId == null) {
            throw new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE);
        }

        //向mon_task_detail插入数据
        List<Detail> details = task_detail.getDetails();
        if (details != null) {
            for (Detail detail : details) {
                detail.setTask_id(taskId);
                taskMapper.addTaskDetail(detail);
            }
        }
        return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), task_detail);
    }

    /**
     * 根据id修改任务以及detail
     *
     * @param task_detail
     * @param language
     * @return
     * @throws Exception
     */
    @Override
    public BaseResult updateTask(Task_Detail task_detail, String language) throws Exception {
        Integer res = taskMapper.deleteDetailByTaskId(task_detail.getId());
        if (res == null || res < 1) {
            throw new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE);
        }
        for (int i = 0; i < task_detail.getDetails().size(); i++) {
            task_detail.getDetails().get(i).setTask_id(task_detail.getId());
            taskMapper.addTaskDetail(task_detail.getDetails().get(i));
        }
        return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), task_detail);
    }

    @Override
    public BaseResult findDevLog(FindDevLogBean findDevLogBean,Long user_id,List<Long> org_ids) throws Exception {
        String data1 = objectMapper.writeValueAsString(findDevLogBean);
        log.info("接口请求参数bean：" + data1);
        Map reqMap = new HashMap();

        if(org_ids.size()==0){
            org_ids=null;
        }

        if (findDevLogBean.getName() != null) {
            reqMap.put("devName", findDevLogBean.getName());
        } else {
            reqMap.put("devName", "");
        }
        if (findDevLogBean.getSno() != null) {
            reqMap.put("devSno", findDevLogBean.getSno());
        } else {
            reqMap.put("devSno", "");
        }
        String path;
        if (findDevLogBean.getLanguage() != null  && !findDevLogBean.getLanguage().equals("")) {
            path =  url_finddevsByKey +"/"+ findDevLogBean.getLanguage();
        } else {
            path = url_finddevsByKey+"/english";
        }
        HttpResult httpResult = null;
        httpResult = httpAPIService.doPost(path, reqMap);
        if (httpResult == null || httpResult.getCode() != 200 || httpResult.getBody() == null) {
            throw new CustomException(ResLanguage.getRes_system_err_mes(findDevLogBean.getLanguage()), ResLanguage.RES_SYSTEM_ERR_CODE);
        }
        String body = httpResult.getBody();
        ModuleResponseBean moduleResponseBean = objectMapper.readValue(body, ModuleResponseBean.class);
        if (moduleResponseBean.getCode() != 200) {
            throw new CustomException(moduleResponseBean.getMessage(), moduleResponseBean.getCode());
        } else {
            List<ModuleResponseDataBean> moduleResponseDataBeans = moduleResponseBean.getData().getDevices();
            String data2 = objectMapper.writeValueAsString(moduleResponseDataBeans);
            log.info("module接口1请求结果devicesBean：" + data2);
            List<Integer> list = new ArrayList<>();
            for (ModuleResponseDataBean mbean : moduleResponseDataBeans) {
                list.add(mbean.getId());
            }
            if (list == null || list.size() == 0) {
                return new BaseResult(ResLanguage.RES_SUCCESS_CODE, findDevLogBean.getLanguage(), null);
            }
            findDevLogBean.setObject_ids(list);
            Page page = new Page();
            Integer pageNumber = findDevLogBean.getPageNumber();
            Integer pageSize = findDevLogBean.getPageSize();
            findDevLogBean.setUser_id(user_id);
            findDevLogBean.setOrg_ids(org_ids);
            Long total = taskLogMapper.findDevLogCount(findDevLogBean);
            if (pageNumber == null || pageNumber < 1) {
                page.setTotal(total);
                if (pageSize != null && pageSize > 0) {
                    page.setPageSize(pageSize);
                }
            } else {
                page.setStartNumber(((pageNumber - 1) * page.getPageSize()));
                page.setTotal(total);
                if (pageSize != null && pageSize > 0) {
                    page.setPageSize(pageSize);
                }
            }
            findDevLogBean.setPage(page);
            String data22 = objectMapper.writeValueAsString(findDevLogBean);
            log.info("查询task_log列表的参数findDevLogBean：" + data22);
            List<TaskLog> taskLogs = taskLogMapper.findDevLog(findDevLogBean);
            String data3 = objectMapper.writeValueAsString(taskLogs);
            log.info("查询task_log列表返回的Bean：" + data3);
            for (int i = 0; i < taskLogs.size(); i++) {
                TaskLog taskLog = taskLogs.get(i);
                Detail detail = taskLogMapper.getTaskDetailByoId(taskLog.getTask_detail_id());
                if (detail == null || detail.getParam_id() == null || detail.getParam_id().equals("")) {
                    continue;
                }
                String data4 = objectMapper.writeValueAsString(detail);
                log.info("查询每个task_log返回的对应的DetailBean：" + data4);
                String[] split = detail.getParam_id().split(",");
                List<Integer> paramids = new ArrayList<>();
                for (int j = 0; j < split.length; j++) {
                    paramids.add(Integer.parseInt(split[j]));
                }
                String data5 = objectMapper.writeValueAsString(paramids);
                log.info("module2请求参数paramids：" + data5);
                Map reqMap2 = new HashMap();
                reqMap2.put("paramIds", paramids);
                httpResult = httpAPIService.doPost(path, reqMap2);
                if (httpResult == null || httpResult.getCode() != 200 || httpResult.getBody() == null) {
                    throw new CustomException(ResLanguage.getRes_system_err_mes(findDevLogBean.getLanguage()), ResLanguage.RES_SYSTEM_ERR_CODE);
                }
                String body2 = httpResult.getBody();
                ModuleResponseBean moduleResponseBean2 = objectMapper.readValue(body2, ModuleResponseBean.class);
                if (moduleResponseBean2.getCode() != 200) {
                    throw new CustomException(moduleResponseBean2.getMessage(), moduleResponseBean2.getCode());
                } else {
                    String data6 = objectMapper.writeValueAsString(moduleResponseBean2);
                    log.info("module2请求结果：" + data6);
                    List<DeviceParam> deviceParams = moduleResponseBean2.getData().getDeviceParams();
                    taskLog.setDeviceParams(deviceParams);
                    taskLog.setData(detail.getData());
                    for (ModuleResponseDataBean mbean : moduleResponseDataBeans) {
                        if (mbean.getId() == detail.getObject_id().intValue()) {
                            taskLog.setDevName(mbean.getName());
                            taskLog.setSno(mbean.getSno());
                        }
                    }
                }
            }
            Map map = new HashMap();
            map.put("task_logs", taskLogs);
            map.put("page", page);
            return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(findDevLogBean.getLanguage()), map);
        }
    }

    @Override
    public BaseResult appfindDevLog(FindDevLogBean findDevLogBean, Long user_id, List<Long> org_ids) throws Exception {
        String data1 = objectMapper.writeValueAsString(findDevLogBean);
        log.info("接口请求参数bean：" + data1);
        Map reqMap = new HashMap();

        if (org_ids.size() == 0) {
            org_ids = null;
        }

        if (findDevLogBean.getName() != null) {
            reqMap.put("devName", findDevLogBean.getName());
        } else {
            reqMap.put("devName", "");
        }
        if (findDevLogBean.getSno() != null) {
            reqMap.put("devSno", findDevLogBean.getSno());
        } else {
            reqMap.put("devSno", "");
        }
        String path;
        if (findDevLogBean.getLanguage() != null && !findDevLogBean.getLanguage().equals("")) {
            path = url_finddevsByKey + "/" + findDevLogBean.getLanguage();
        } else {
            path = url_finddevsByKey + "/english";
        }
        HttpResult httpResult = null;
        httpResult = httpAPIService.doPost(path, reqMap);
        if (httpResult == null || httpResult.getCode() != 200 || httpResult.getBody() == null) {
            throw new CustomException(ResLanguage.getRes_system_err_mes(findDevLogBean.getLanguage()), ResLanguage.RES_SYSTEM_ERR_CODE);
        }
        String body = httpResult.getBody();
        ModuleResponseBean moduleResponseBean = objectMapper.readValue(body, ModuleResponseBean.class);
        if (moduleResponseBean.getCode() != 200) {
            throw new CustomException(moduleResponseBean.getMessage(), moduleResponseBean.getCode());
        } else {
            List<ModuleResponseDataBean> moduleResponseDataBeans = moduleResponseBean.getData().getDevices();
            String data2 = objectMapper.writeValueAsString(moduleResponseDataBeans);
            log.info("module接口1请求结果devicesBean：" + data2);
            List<Integer> list = new ArrayList<>();
            for (ModuleResponseDataBean mbean : moduleResponseDataBeans) {
                list.add(mbean.getId());
            }
            if (list == null || list.size() == 0) {
                return new BaseResult(ResLanguage.RES_SUCCESS_CODE, findDevLogBean.getLanguage(), null);
            }
            findDevLogBean.setObject_ids(list);
            Page page = new Page();
            Integer pageNumber = findDevLogBean.getPageNumber();
            Integer pageSize = findDevLogBean.getPageSize();
            findDevLogBean.setUser_id(user_id);
            findDevLogBean.setOrg_ids(org_ids);
            Long total = taskLogMapper.findDevLogCount(findDevLogBean);
            if (pageNumber == null || pageNumber < 1) {
                page.setTotal(total);
                if (pageSize != null && pageSize > 0) {
                    page.setPageSize(pageSize);
                }
            } else {
                page.setStartNumber(((pageNumber - 1) * page.getPageSize()));
                page.setTotal(total);
                if (pageSize != null && pageSize > 0) {
                    page.setPageSize(pageSize);
                }
            }
            findDevLogBean.setPage(page);
            String data22 = objectMapper.writeValueAsString(findDevLogBean);
            log.info("查询task_log列表的参数findDevLogBean：" + data22);
            List<TaskLog> taskLogs = taskLogMapper.findDevLog(findDevLogBean);
            String data3 = objectMapper.writeValueAsString(taskLogs);
            log.info("查询task_log列表返回的Bean：" + data3);
            for (int i = 0; i < taskLogs.size(); i++) {
                TaskLog taskLog = taskLogs.get(i);
                Detail detail = taskLogMapper.getTaskDetailByoId(taskLog.getTask_detail_id());
                if (detail == null || detail.getParam_id() == null || detail.getParam_id().equals("")) {
                    continue;
                }
                String data4 = objectMapper.writeValueAsString(detail);
                log.info("查询每个task_log返回的对应的DetailBean：" + data4);
                String[] split = detail.getParam_id().split(",");
                List<Integer> paramids = new ArrayList<>();
                for (int j = 0; j < split.length; j++) {
                    paramids.add(Integer.parseInt(split[j]));
                }
                String data5 = objectMapper.writeValueAsString(paramids);
                log.info("module2请求参数paramids：" + data5);
                Map reqMap2 = new HashMap();
                reqMap2.put("paramIds", paramids);
                httpResult = httpAPIService.doPost(path, reqMap2);
                if (httpResult == null || httpResult.getCode() != 200 || httpResult.getBody() == null) {
                    throw new CustomException(ResLanguage.getRes_system_err_mes(findDevLogBean.getLanguage()), ResLanguage.RES_SYSTEM_ERR_CODE);
                }
                String body2 = httpResult.getBody();
                ModuleResponseBean moduleResponseBean2 = objectMapper.readValue(body2, ModuleResponseBean.class);
                if (moduleResponseBean2.getCode() != 200) {
                    throw new CustomException(moduleResponseBean2.getMessage(), moduleResponseBean2.getCode());
                } else {
                    String data6 = objectMapper.writeValueAsString(moduleResponseBean2);
                    log.info("module2请求结果：" + data6);
                    List<DeviceParam> deviceParams = moduleResponseBean2.getData().getDeviceParams();
                    taskLog.setDeviceParams(deviceParams);
                    taskLog.setData(detail.getData());
                    for (ModuleResponseDataBean mbean : moduleResponseDataBeans) {
                        if (mbean.getId() == detail.getObject_id().intValue()) {
                            taskLog.setDevName(mbean.getName());
                            taskLog.setSno(mbean.getSno());
                        }
                    }
                }
            }
            Map map = new HashMap();
            map.put("task_logs", taskLogs);
            map.put("page", page);
            return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(findDevLogBean.getLanguage()), map);
        }
    }

    /**
     * 添加定时任务
     *
     * @param schedule_task_detail
     * @param language
     * @return
     * @throws Exception
     */
    @Override
    public BaseResult addScheduleTask(Schedule_Task_Detail schedule_task_detail, String language) throws Exception {
        // 1.秒（0~59）
        // 2.分钟（0~59）
        // 3.小时（0~23）
        // 4.天（月）（0~31，但是你需要考虑你月的天数）
        // 5.月（0~11）
        // 6.天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
        // 7.年份（1970－2099）
//		https://blog.csdn.net/caiwenfeng_for_23/article/details/17004213

        // 1.向mon_schedule插入数据
        schedule_task_detail.setCreate_time(new Date().getTime() / 1000);
        taskMapper.addTaskSchedule(schedule_task_detail);
        if (schedule_task_detail.getId() == null) {
            throw new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE);
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (schedule_task_detail.getSeconds() != null) {
            stringBuffer.append(schedule_task_detail.getSeconds());
            stringBuffer.append(" ");
        }
        if (schedule_task_detail.getMinutes() != null) {
            stringBuffer.append(schedule_task_detail.getMinutes());
            stringBuffer.append(" ");
        }
        if (schedule_task_detail.getHours() != null) {
            stringBuffer.append(schedule_task_detail.getHours());
            stringBuffer.append(" ");
        }
        if (schedule_task_detail.getDayofMonth() != null) {
            stringBuffer.append(schedule_task_detail.getDayofMonth());
            stringBuffer.append(" ");
        }
        if (schedule_task_detail.getMonth() != null) {
            stringBuffer.append(schedule_task_detail.getMonth());
            stringBuffer.append(" ");
        }
        if (schedule_task_detail.getDayofWeek() != null) {
            stringBuffer.append(schedule_task_detail.getDayofWeek());
            stringBuffer.append(" ");
        }
        if (schedule_task_detail.getYear() != null) {
            stringBuffer.append(schedule_task_detail.getYear());
            stringBuffer.append(" ");
        }
        String strCronExpression = stringBuffer.toString().replaceAll("\\s+$", "");

        // 2.向mon_task插入数据
        taskMapper.addTask(schedule_task_detail.getTask_detail());
        Long taskId = schedule_task_detail.getTask_detail().getId();
        schedule_task_detail.setTask_id(taskId);
        if (taskId == null) {
            throw new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE);
        }

        //3.根据taskId更新向mon_schedule数据
        Integer res = taskMapper.updateScheduleTaskId(schedule_task_detail.getId(), schedule_task_detail.getTask_id());
        if (res == null || res < 1) {
            throw new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE);
        }

        //4.向mon_task_detail插入数据
        if (schedule_task_detail.getTask_detail().getDetails() == null || schedule_task_detail.getTask_detail().getDetails().size() < 0) {
            return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), schedule_task_detail);
        }
        List<QuartzDataBean> quartzDataBeans = new ArrayList<>();
        for (int j = 0; j < schedule_task_detail.getTask_detail().getDetails().size(); j++) {
            schedule_task_detail.getTask_detail().getDetails().get(j).setTask_id(taskId);
            Detail detail = schedule_task_detail.getTask_detail().getDetails().get(j);
            taskMapper.addTaskDetail(detail);
            QuartzDataBean quartzDataBean = new QuartzDataBean();
            quartzDataBean.setAct("task_data");
            quartzDataBean.setD_id(detail.getId());
            quartzDataBean.setType("task");
            quartzDataBean.setDevice_id(detail.getObject_id());
            quartzDataBean.setT_id(taskId);
            quartzDataBean.setSort(detail.getSort());
            quartzDataBean.setTask_type(detail.getTask_type());

            List<Float> arr_data1 = new ArrayList<>();
            if (detail.getData() != null && !detail.getData().equals("")) {
                String[] strData = detail.getData().split(",");
                for (int i = 0; i < strData.length; i++) {
                    try {
                        arr_data1.add(Float.parseFloat(strData[i]));
                    } catch (Exception e) {
                        throw new CustomException(ResLanguage.getRes_param_err_mes(language, "detail.data"), ResLanguage.RES_PARAM_ERR_CODE);
                    }
                }
            }
            quartzDataBean.setData(arr_data1);

            List<Long> arr_data2 = new ArrayList<>();
            if (detail.getParam_id() != null && !detail.getParam_id().equals("")) {
                String[] strData = detail.getParam_id().split(",");
                for (int i = 0; i < strData.length; i++) {
                    try {
                        arr_data2.add(Long.parseLong(strData[i]));
                    } catch (Exception e) {
                        throw new CustomException(ResLanguage.getRes_param_err_mes(language, "detail.param"), ResLanguage.RES_PARAM_ERR_CODE);
                    }
                }
            }
            quartzDataBean.setParam_ids(arr_data2);

            quartzDataBeans.add(quartzDataBean);
        }

        String jobParam = objectMapper.writeValueAsString(quartzDataBeans);
        if(schedule_task_detail.getStatus()!=0) {
            return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), schedule_task_detail);
        }


        // 添加任务到quartz
        try {
            ScheduleJob job = new ScheduleJob();
            job.setParam(jobParam);// 到时数据以json的格式封装在这里
            job.setCreateTime(new Date());
            job.setId(taskId.intValue());
            job.setCronExpression(strCronExpression);
            job.setJobName(taskId + "");
            job.setJobGroup(Constant.JOB_GROUP_NAME);
            job.setRemark("");
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 不存在，则创建
            if (null == trigger) {
                JobDetail jobDetail = JobBuilder.newJob(DynamicJob.class)
                        .withIdentity(job.getJobName(), job.getJobGroup()).build();
                jobDetail.getJobDataMap().put(Constant.SCHEDULE_JOB_DATA_KEY, job);
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                // withIdentity中写jobName和groupName
                trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
                        .withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
                return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), schedule_task_detail);
            } else {
                try {
                    throw new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE);
                } catch (CustomException e) {
                    // TODO: handle exception
                    throw e;
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public BaseResult addScheduleTasks(List<Schedule_Task_Detail> schedule_task_details, String language) throws Exception {
        for (Schedule_Task_Detail schedule_task_detail : schedule_task_details) {
            addScheduleTask(schedule_task_detail, language);
        }
        return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), schedule_task_details);
    }

    /**
     * 查找任务
     */
    public BaseResult selectTasks(Integer pageNumber, Integer pageSize, String keyWord, Integer taskStatus, String language, Long user_id, List<Long> org_ids, Integer status, Integer type)
            throws Exception {

        Page page = new Page();
        if (org_ids != null && org_ids.size() < 1) {
            org_ids = null;
        }
        if (org_ids != null && org_ids.size() > 0) {
            user_id = null;
        }
        FindTask findTask = new FindTask();
        findTask.setUser_id(user_id);
        findTask.setOrg_ids(org_ids);
        findTask.setName(keyWord);
        if (pageNumber == null || pageNumber < 1) {
            if (pageSize != null && pageSize > 0) {
                page.setPageSize(pageSize);
            }
            findTask.setStatus(taskStatus);
            findTask.setPage(page);
            Long total = taskMapper.findScheduleCount(findTask);
            page.setTotal(total);
        } else {
            page.setStartNumber(((pageNumber - 1) * page.getPageSize()));
            if (pageSize != null && pageSize > 0) {
                page.setPageSize(pageSize);
            }
            findTask.setStatus(taskStatus);
            findTask.setPage(page);
            findTask.setType(type);
            Long total = taskMapper.findScheduleCount(findTask);
            page.setTotal(total);
        }
        //1.查询定时器列表
        List<Schedule_Task_Detail> list = taskMapper.findSchedule(findTask);
        for (int i = 0; i < list.size(); i++) {
            Schedule_Task_Detail schedule = list.get(i);
            StringBuffer stringBuffer = new StringBuffer();
            if (schedule.getSeconds() != null) {
                stringBuffer.append(schedule.getSeconds());
                stringBuffer.append("+");
            }
            if (schedule.getMinutes() != null) {
                stringBuffer.append(schedule.getMinutes());
                stringBuffer.append("+");
            }
            if (schedule.getHours() != null) {
                stringBuffer.append(schedule.getHours());
                stringBuffer.append("+");
            }
            if (schedule.getDayofMonth() != null) {
                stringBuffer.append(schedule.getDayofMonth());
                stringBuffer.append("+");
            }
            if (schedule.getMonth() != null) {
                stringBuffer.append(schedule.getMonth());
                stringBuffer.append("+");
            }
            if (schedule.getDayofWeek() != null) {
                stringBuffer.append(schedule.getDayofWeek());
                stringBuffer.append("+");
            }
            if (schedule.getYear() != null) {
                stringBuffer.append(schedule.getYear());
                stringBuffer.append("+");
            }
            String expStr = stringBuffer.toString();
            String expression = expStr.substring(0, expStr.length() - 1);
            Map map = new HashMap();
            map.put("locale", language);
            map.put("expression", expression);
            String baseUrtl = get_cron;
            StringBuffer urlBuffer = new StringBuffer(baseUrtl);
            urlBuffer.append("?").append("expression=").append(expression).append("&locale=").append(CommonUtils.changeLanguage(language));
            String cronExpression = httpAPIService.doGet(urlBuffer.toString());
            Map map1 = objectMapper.readValue(cronExpression, Map.class);
            schedule.setCronexpression((String) map1.get("description"));
            //遍历定时器，查询每个定时器对应的任务
            Schedule schedule1 = taskMapper.selectScheduleById(schedule.getId());
            Task_Detail task_detail = taskMapper.findTaskbyId(schedule1.getTask_id());
            if (task_detail != null) {
                List<Detail> details = taskMapper.selectTaskDetailByTaskId(task_detail.getId());

                String path;
                if (language != null) {
                    path =  url_finddevsByKey +"/"+ language;
                } else {
                    path = url_finddevsByKey+"/english";
                }
                for(int k=0;k<details.size();k++){

                    Map reqMap3 = new HashMap();
                    List<Long> deviceids = new ArrayList<>();
                    deviceids.add(details.get(k).getObject_id());
                    reqMap3.put("deviceIds", deviceids);
                    HttpResult httpResult2 = httpAPIService.doPost(path, reqMap3);
                    if (httpResult2 == null || httpResult2.getCode() != 200 || httpResult2.getBody() == null) {
                        log.info("http_req_err1:/devmodule/api/find_devs_params_by_key");
                        throw new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE);
                    }
                    String body3 = httpResult2.getBody();
                    ModuleResponseBean moduleResponseBean3 = objectMapper.readValue(body3, ModuleResponseBean.class);
                    if (moduleResponseBean3.getCode() != 200) {
                        log.info("http_req_err2:/devmodule/api/find_devs_params_by_key");
                        throw new CustomException(moduleResponseBean3.getMessage(), moduleResponseBean3.getCode());
                    } else {
                        String data6 = objectMapper.writeValueAsString(moduleResponseBean3);
                        log.info("module2请求结果：" + data6);
                        List<ModuleResponseDataBean> moduleResponseDataBeans = moduleResponseBean3.getData().getDevices();
                        if(moduleResponseDataBeans!=null && moduleResponseDataBeans.size()>0){
                            details.get(k).setDeviceName(moduleResponseDataBeans.get(0).getName());
                        }
                    }

                    String[] split = details.get(k).getParam_id().split(",");
                    List<Integer> paramids = new ArrayList<>();
                    for (int j = 0; j < split.length; j++) {
                        paramids.add(Integer.parseInt(split[j]));
                    }
                    String data5 = objectMapper.writeValueAsString(paramids);
                    log.info("module2请求参数paramids：" + data5);
                    Map reqMap2 = new HashMap();
                    reqMap2.put("paramIds", paramids);

                    HttpResult httpResult  = httpAPIService.doPost(path, reqMap2);
                    if (httpResult == null || httpResult.getCode() != 200 || httpResult.getBody() == null) {
                        log.info("http_req_err3:/devmodule/api/find_devs_params_by_key");
                        throw new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE);
                    }
                    String body2 = httpResult.getBody();
                    ModuleResponseBean moduleResponseBean2 = objectMapper.readValue(body2, ModuleResponseBean.class);
                    if (moduleResponseBean2.getCode() != 200) {
                        log.info("http_req_err4:/devmodule/api/find_devs_params_by_key");
                        throw new CustomException(moduleResponseBean2.getMessage(), moduleResponseBean2.getCode());
                    } else {
                        String data6 = objectMapper.writeValueAsString(moduleResponseBean2);
                        log.info("module2请求结果：" + data6);
                        List<DeviceParam> deviceParams = moduleResponseBean2.getData().getDeviceParams();
                        details.get(k).setDeviceParams(deviceParams);
                    }
                }

                task_detail.setDetails(details);
                list.get(i).setTask_detail(task_detail);
            }

//            Map reqMap = new HashMap();
//            List<Integer> userIds = new ArrayList<>();
//            userIds.add(schedule.getUser_id().intValue());
//            reqMap.put("userIds", userIds);
//            HttpResult httpResult = null;
//            try {
//                httpResult = httpAPIService.doPost(Constant.BASE_URL + "/user/module/findUserByIds", reqMap);
//                if (httpResult == null || httpResult.getCode() != 200 || httpResult.getBody() == null) {
//                    log.info(ResLanguage.getRes_system_err_mes(language) + "errCode：" + httpResult.getCode());
//                    throw new CustomException(ResLanguage.getSystemModule_err_mes(language), ResLanguage.RES_SYS_MODULE_ERR_CODE);
//                }
//                String body = httpResult.getBody();
//                SystemUserResponseBean systemResponseBean = objectMapper.readValue(body, SystemUserResponseBean.class);
//                if (systemResponseBean.getCode() != 200) {
//                    throw new CustomException((String) systemResponseBean.getMessage(), systemResponseBean.getCode());
//                }else{
//                    if(systemResponseBean.getData()!=null && systemResponseBean.getData().size()>0){
//                        User user = systemResponseBean.getData().get(0);
//                        if(user!=null){
//                            list.get(i).setUserName(user.getName());
//                        }else{
//                            log.info("");
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new CustomException(ResLanguage.getSystemModule_err_mes(language), ResLanguage.RES_SYS_MODULE_ERR_CODE);
//            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("schedules", list);
        map.put("page", page);
        return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), map);
    }

    /**
     * 查询任务（id）
     */
    public BaseResult findTasksbyId(Long id, String language) throws Exception {
        TaskScheduleDetail task = taskMapper.findScheduleTaskbyId(id);
        if (task == null) {
            throw new CustomException(ResLanguage.getRes_nofound_mes(language), ResLanguage.RES_NOFOUND_ERR_CODE);
        }
        task.setDetails(taskMapper.selectTaskDetailByTaskId(task.getId()));

        Schedule schedule = taskMapper.selectScheduleByTaskId(task.getId());
        if (schedule != null) {
            task.setSchedule(schedule);
            StringBuffer stringBuffer = new StringBuffer();
            if (schedule.getSeconds() != null) {
                stringBuffer.append(schedule.getSeconds());
                stringBuffer.append("+");
            }
            if (schedule.getMinutes() != null) {
                stringBuffer.append(schedule.getMinutes());
                stringBuffer.append("+");
            }
            if (schedule.getHours() != null) {
                stringBuffer.append(schedule.getHours());
                stringBuffer.append("+");
            }
            if (schedule.getDayofMonth() != null) {
                stringBuffer.append(schedule.getDayofMonth());
                stringBuffer.append("+");
            }
            if (schedule.getMonth() != null) {
                stringBuffer.append(schedule.getMonth());
                stringBuffer.append("+");
            }
            if (schedule.getDayofWeek() != null) {
                stringBuffer.append(schedule.getDayofWeek());
                stringBuffer.append("+");
            }
            if (schedule.getYear() != null) {
                stringBuffer.append(schedule.getYear());
                stringBuffer.append("+");
            }
            String expStr = stringBuffer.toString();
            String expression = expStr.substring(0, expStr.length() - 1);
            Map map = new HashMap();
            map.put("locale", language);
            map.put("expression", expression);
            String baseUrtl = get_cron;
            StringBuffer urlBuffer = new StringBuffer(baseUrtl);
            urlBuffer.append("?").append("expression=").append(expression).append("&locale=").append(CommonUtils.changeLanguage(language));
            String cronExpression = httpAPIService.doGet(urlBuffer.toString());
            Map map1 = objectMapper.readValue(cronExpression, Map.class);
            task.getSchedule().setCronexpression((String) map1.get("description"));
        }
        return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), task);
    }

    /**
     * 查找定时器
     *
     * @param scheduleId
     * @param language
     * @return
     * @throws Exception
     */
    public BaseResult findScheduleTaskbyId(Long scheduleId, String language) throws Exception {

        Schedule_Task_Detail schedule = taskMapper.selectScheduleTDByScheduleId(scheduleId);
        if (schedule == null) {
            throw new CustomException(ResLanguage.getRes_nofound_mes(language), ResLanguage.RES_NOFOUND_ERR_CODE);
        }
        Task_Detail task = taskMapper.findTaskbyId(schedule.getTask_id());
        if (task == null) {
            throw new CustomException(ResLanguage.getRes_nofound_mes(language), ResLanguage.RES_NOFOUND_ERR_CODE);
        }
        task.setDetails(taskMapper.selectTaskDetailByTaskId(task.getId()));
        if (schedule != null) {
            StringBuffer stringBuffer = new StringBuffer();
            if (schedule.getSeconds() != null) {
                stringBuffer.append(schedule.getSeconds());
                stringBuffer.append("+");
            }
            if (schedule.getMinutes() != null) {
                stringBuffer.append(schedule.getMinutes());
                stringBuffer.append("+");
            }
            if (schedule.getHours() != null) {
                stringBuffer.append(schedule.getHours());
                stringBuffer.append("+");
            }
            if (schedule.getDayofMonth() != null) {
                stringBuffer.append(schedule.getDayofMonth());
                stringBuffer.append("+");
            }
            if (schedule.getMonth() != null) {
                stringBuffer.append(schedule.getMonth());
                stringBuffer.append("+");
            }
            if (schedule.getDayofWeek() != null) {
                stringBuffer.append(schedule.getDayofWeek());
                stringBuffer.append("+");
            }
            if (schedule.getYear() != null) {
                stringBuffer.append(schedule.getYear());
                stringBuffer.append("+");
            }
            String expStr = stringBuffer.toString();
            String expression = expStr.substring(0, expStr.length() - 1);
            Map map = new HashMap();
            map.put("locale", language);
            map.put("expression", expression);
            String baseUrtl = get_cron;
            StringBuffer urlBuffer = new StringBuffer(baseUrtl);
            urlBuffer.append("?").append("expression=").append(expression).append("&locale=").append(CommonUtils.changeLanguage(language));
            String cronExpression = httpAPIService.doGet(urlBuffer.toString());
            Map map1 = objectMapper.readValue(cronExpression, Map.class);
            schedule.setCronexpression((String) map1.get("description"));
            schedule.setTask_detail(task);


        }
        return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), schedule);
    }

    /**
     * 查询任务组
     *
     * @param ids
     * @param language
     * @return
     * @throws Exception
     */
    @Override
    public BaseResult findTasksbyIds(List<Integer> ids, String language) throws Exception {
        List<Task_Detail> list = new ArrayList<>();
        for (Integer id : ids) {
            Task_Detail task = taskMapper.findTaskbyId(id.longValue());
            if (task == null) {
                throw new CustomException(ResLanguage.getRes_nofound_mes(language), ResLanguage.RES_NOFOUND_ERR_CODE);
            }
            task.setDetails(taskMapper.selectTaskDetailByTaskId(task.getId()));
            list.add(task);
        }

        return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), list);
    }

    @Override
    public BaseResult findTasksCount(String language) throws Exception {
        Integer count = taskMapper.getScheduleCount();
        return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), count);
    }

    /**
     * 修改任务
     */
    public BaseResult updateScheduleTask(Schedule_Task_Detail schedule_task_detail, String language) throws Exception {
        // 1
        if (schedule_task_detail == null || schedule_task_detail.getId() == null) {
            throw new CustomException(ResLanguage.getRes_param_no_mes(language, "schedule_id"), ResLanguage.RES_PARAM_NO_CODE);
        }
        if (schedule_task_detail.getTask_detail() == null || schedule_task_detail.getTask_detail().getId() == null) {
            throw new CustomException(ResLanguage.getRes_param_no_mes(language, "task_id"), ResLanguage.RES_PARAM_NO_CODE);
        }
        schedule_task_detail.setTask_id(schedule_task_detail.getTask_detail().getId());

        taskMapper.updateTaskSchedule(schedule_task_detail);
        Schedule schedule = taskMapper.selectScheduleById(schedule_task_detail.getId());
        StringBuffer stringBuffer = new StringBuffer();
        if (schedule.getSeconds() != null) {
            stringBuffer.append(schedule.getSeconds());
            stringBuffer.append(" ");
        }
        if (schedule.getMinutes() != null) {
            stringBuffer.append(schedule.getMinutes());
            stringBuffer.append(" ");
        }
        if (schedule.getHours() != null) {
            stringBuffer.append(schedule.getHours());
            stringBuffer.append(" ");
        }
        if (schedule.getDayofMonth() != null) {
            stringBuffer.append(schedule.getDayofMonth());
            stringBuffer.append(" ");
        }
        if (schedule.getMonth() != null) {
            stringBuffer.append(schedule.getMonth());
            stringBuffer.append(" ");
        }
        if (schedule.getDayofWeek() != null) {
            stringBuffer.append(schedule.getDayofWeek());
            stringBuffer.append(" ");
        }
        if (schedule.getYear() != null) {
            stringBuffer.append(schedule.getYear());
            stringBuffer.append(" ");
        }
        String strCronExpression = stringBuffer.toString().replaceAll("\\s+$", "");

        // 2
        taskMapper.updateTask(schedule_task_detail.getTask_detail());
        Long task_id = schedule_task_detail.getTask_detail().getId();

        //3
        taskMapper.deleteDetailByTaskId(task_id);

        //4
        if (schedule_task_detail.getTask_detail().getDetails() == null || schedule_task_detail.getTask_detail().getDetails().size() < 0) {
            return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), schedule_task_detail);
        }
        List<QuartzDataBean> quartzDataBeans = new ArrayList<>();
        for (int j = 0; j < schedule_task_detail.getTask_detail().getDetails().size(); j++) {
            schedule_task_detail.getTask_detail().getDetails().get(j).setTask_id(task_id);
            Detail detail = schedule_task_detail.getTask_detail().getDetails().get(j);
            detail.setTask_type(2);
            taskMapper.addTaskDetail(detail);
            QuartzDataBean quartzDataBean = new QuartzDataBean();
            quartzDataBean.setAct("task_data");
            quartzDataBean.setD_id(detail.getId());
            quartzDataBean.setType("task");
            quartzDataBean.setDevice_id(detail.getObject_id());
            quartzDataBean.setT_id(task_id);
            quartzDataBean.setSort(detail.getSort());
            quartzDataBean.setTask_type(detail.getTask_type());

            List<Float> arr_data1 = new ArrayList<>();
            if (detail.getData() != null && !detail.getData().equals("")) {
                String[] strData = detail.getData().split(",");
                for (int i = 0; i < strData.length; i++) {
                    arr_data1.add(Float.parseFloat(strData[i]));
                }
            }
            quartzDataBean.setData(arr_data1);

            List<Long> arr_data2 = new ArrayList<>();
            if (detail.getParam_id() != null && !detail.getParam_id().equals("")) {
                String[] strData = detail.getParam_id().split(",");
                for (int i = 0; i < strData.length; i++) {
                    arr_data2.add(Long.parseLong(strData[i]));
                }
            }
            quartzDataBean.setParam_ids(arr_data2);

            quartzDataBeans.add(quartzDataBean);
        }

        String jobParam = objectMapper.writeValueAsString(quartzDataBeans);

        try {
            ScheduleJob job = new ScheduleJob();
            job.setParam(jobParam);// 到时数据以json的格式封装在这里
            job.setCreateTime(new Date());
            job.setId(schedule_task_detail.getTask_detail().getId().intValue());
            job.setCronExpression(strCronExpression);
            job.setJobName(schedule_task_detail.getTask_detail().getId() + "");
            job.setJobGroup(Constant.JOB_GROUP_NAME);
            job.setRemark("");
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 不存在
            if (null == trigger) {
                throw new CustomException(ResLanguage.getRes_notask_mes(language), ResLanguage.RES_TASK_NO_CODE);
            } else {
//				 Trigger已存在，那么更新相应的定时设置
                JobDetail jobDetail = JobBuilder.newJob(DynamicJob.class)
                        .withIdentity(job.getJobName(), job.getJobGroup()).build();
                jobDetail.getJobDataMap().put(Constant.SCHEDULE_JOB_DATA_KEY, job);
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

//				 按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

//				 按新的trigger重新设置job执行
                deleteJob(schedule_task_detail.getTask_detail().getId().intValue(), language);
                scheduler.scheduleJob(jobDetail, trigger);
                return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), schedule_task_detail);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 设置任务状态
     */
    public BaseResult updateTaskScheduleStatusbyId(List<Integer> ids, Integer status, String language) {
        for (Integer sid : ids) {
            Integer result = taskMapper.updateTaskScheduleStatusbyId(sid.longValue(), status);
            if (result != null && result > 0) {
                Schedule schedule = taskMapper.selectScheduleById(sid.longValue());
                Long id = schedule.getTask_id();
                if (status == 0) {
                    //运行
                    resumeJob(id.intValue(), language);
                }
                if (status == 1) {
                    //暂停
                    pauseJob(id.intValue(), language);
                }
                if (status == 2) {
                    //删除
                    deleteJob(id.intValue(), language);
                }

            } else {
                throw new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE);
            }
        }
        return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), null);
    }

    @Override
    public BaseResult findUsersByTaskIds(List<Integer> ids, String language) throws Exception {
        List<Integer> list = taskMapper.selectuIdBytaskId(ids);
        List<Integer> uIds = new ArrayList<>();
        for (Integer id : list) {
            if (!uIds.contains(id)) {
                uIds.add(id);
            }
        }
        Map reqMap = new HashMap();
        reqMap.put("language", language);
        reqMap.put("userIds", uIds);
        HttpResult httpResult = null;
        try {
            httpResult = httpAPIService.doPost(url_findUserByIds , reqMap);
            if (httpResult == null || httpResult.getCode() != 200 || httpResult.getBody() == null) {
                throw new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE);
            }
            String body = httpResult.getBody();
            TaskResponseBean taskResponseBean = objectMapper.readValue(body, TaskResponseBean.class);
            if (taskResponseBean.getCode() != 200) {
                throw new CustomException(taskResponseBean.getMessage(), taskResponseBean.getCode());
            } else {
                return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), taskResponseBean.getData());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE);
        }
    }

    @Override
    public BaseResult findTaskIdByUTId(List<Integer> ids, Integer userId, String language) throws Exception {
        List<Integer> task_ids = taskMapper.selectTIdByUtaskId(ids, userId);
        return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(language), task_ids);
    }


    /**
     * 暂停定时任务
     *
     * @param jobId
     */
    public void pauseJob(int jobId, String language) {
        try {
            TriggerKey triggerkey = TriggerKey.triggerKey(jobId + "", Constant.JOB_GROUP_NAME);
            scheduler.pauseTrigger(triggerkey);
        } catch (Exception e) {
            throw new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE);
        }
    }

    /**
     * 恢复一个定时任务
     *
     * @param jobId
     */
    public void resumeJob(int jobId, String language) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobId + "", Constant.JOB_GROUP_NAME);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            scheduler.resumeTrigger(triggerKey);
        } catch (Exception e) {
            throw new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE);
        }
    }

    /**
     * 删除任务
     *
     * @param jobId
     * @param language
     */
    public void deleteJob(int jobId, String language) {
        try {
            //删除一个定时任务
            TriggerKey triggerKey = TriggerKey.triggerKey(jobId + "", Constant.JOB_GROUP_NAME);
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobId + "", Constant.JOB_GROUP_NAME));// 删除任务
        } catch (Exception e) {
            throw new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE);
        }
    }

    /**
     * 立即执行一个定时任务
     *
     * @param jobId
     */
    public void runOnce(int jobId) {
//		try {
//			ScheduleJob scheduleJob = getScheduleJobByPrimaryKey(jobId);
//			JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
//			scheduler.triggerJob(jobKey);
//		} catch (Exception e) {
//			System.out.println("CatchException:恢复定时任务失败" + e);
//		}
    }

    /**
     * 更新时间表达式
     *
     * @param id
     * @param cronExpression
     */
    public void updateCron(int id, String cronExpression) {
//		ScheduleJob scheduleJob = getScheduleJobByPrimaryKey(id);
//		scheduleJob.setCronExpression(cronExpression);
//		updateJobCronExpressionById(scheduleJob);
//		try {
//			TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
//			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
//			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
//			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
//			scheduler.rescheduleJob(triggerKey, trigger);
//		} catch (Exception e) {
//			System.out.println("CatchException:更新时间表达式失败:" + e);
//		}

    }

}
