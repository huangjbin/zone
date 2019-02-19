package com.zone.quartz_module.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone.quartz_module.common.BaseResult;
import com.zone.quartz_module.common.ResLanguage;
import com.zone.quartz_module.param.AddScheduleTaskBean;
import com.zone.quartz_module.param.FindDevLogBean;
import com.zone.quartz_module.pojo.Schedule_Task_Detail;
import com.zone.quartz_module.pojo.Task_Detail;
import com.zone.quartz_module.service.TaskService;
import com.zone.quartz_module.utils.JsonXMLUtils;
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
 * 任务
 * <p>
 * <p>
 * https://github.com/ityouknow/spring-boot-examples
 *
 * @author huangjunbin
 */
@Controller
@RequestMapping(value = "/task")
public class TaskController {

    public Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TaskService taskService;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 添加定时任务
     *
     * @param models
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addScheduleTask", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public BaseResult addScheduleTask(@Valid @RequestBody Map<String, Object> models, BindingResult result, HttpServletRequest request) throws Exception {
        String addScheduleTask_req_data = objectMapper.writeValueAsString(models);
        log.info("addScheduleTask_req_data:"+addScheduleTask_req_data);
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), error.getDefaultMessage()), null);
            }
        }

        Long userId = (Long) request.getAttribute("userId");
        Long orgId = (Long) request.getAttribute("orgId");

        Schedule_Task_Detail schedule_task_detail = JsonXMLUtils.map2obj((Map<String, Object>) models.get("schedule_task_detail"), Schedule_Task_Detail.class);
        schedule_task_detail.setUser_id(userId);
        schedule_task_detail.setOrg_id(orgId);
        schedule_task_detail.setType(0);
        schedule_task_detail.getTask_detail().setUser_id(userId);
        schedule_task_detail.getTask_detail().setOrg_id(orgId);
        if (schedule_task_detail.getName() == null) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), "name"), null);
        }
        if (schedule_task_detail.getOrg_id() == null) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), "schedule_org_id"), null);
        }
        if (schedule_task_detail.getUser_id() == null) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), "schedule_user_id"), null);
        }
        if (schedule_task_detail.getTask_detail().getName() == null) {
            schedule_task_detail.getTask_detail().setName("");
        }
        return taskService.addScheduleTask(schedule_task_detail, (String) models.get("language"));
    }

    /**
     * 添加定时任务s
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "module/addScheduleTasks", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public BaseResult addScheduleTasks(@Valid @RequestBody AddScheduleTaskBean bean, BindingResult result, HttpServletRequest request) throws Exception {
        if (result.hasErrors()) {//不起作用？？？？？？
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(bean.getLanguage(), error.getDefaultMessage()), null);
            }
        }
        String a = objectMapper.writeValueAsString(bean);
        List<Schedule_Task_Detail> schedule_task_details = bean.getSchedule_task_details();

        if (schedule_task_details == null || schedule_task_details.size() == 0) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(bean.getLanguage(), "schedule_task_details"), null);
        } else {
            for (Schedule_Task_Detail schedule_task_detail : schedule_task_details) {
                if (schedule_task_detail.getTask_detail() == null) {
                    return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(bean.getLanguage(), "task_detail"), null);
                }
            }
        }

        for (int i = 0; i < schedule_task_details.size(); i++) {
//			schedule_task_details.get(i).setUser_id(-1);
//			schedule_task_details.get(i).setOrg_id(-1);
//		if(schedule_task_details.get(i).getType()==null){
//			return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), "type"), null);
//		}
            schedule_task_details.get(i).setType(1);
//			schedule_task_details.get(i).getTask_detail().setUser_id(userId);
//			schedule_task_details.get(i).getTask_detail().setOrg_id(orgId);
            if (schedule_task_details.get(i).getName() == null) {
                return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(bean.getLanguage(), "name"), null);
            }
//		if(schedule_task_details.get(i).getOrg_id()==null){
//			return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), "schedule_org_id"), null);
//		}
//		if(schedule_task_details.get(i).getUser_id()==null){
//			return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), "schedule_user_id"), null);
//		}
            if (schedule_task_details.get(i).getTask_detail().getName() == null) {
                schedule_task_details.get(i).getTask_detail().setName("");
            }
        }
        return taskService.addScheduleTasks(schedule_task_details, bean.getLanguage());
    }


    @RequestMapping(value = "module/addScheduleTask", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public BaseResult maddScheduleTask(@Valid @RequestBody Map<String, Object> models, BindingResult result, HttpServletRequest request) throws Exception {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), error.getDefaultMessage()), null);
            }
        }


        Long userId = (Long) request.getAttribute("userId");
        Long orgId = (Long) request.getAttribute("orgId");

        Schedule_Task_Detail schedule_task_detail = JsonXMLUtils.map2obj((Map<String, Object>) models.get("schedule_task_detail"), Schedule_Task_Detail.class);
        if (schedule_task_detail.getType() == null) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), "type"), null);
        }
        schedule_task_detail.setUser_id(userId);
        schedule_task_detail.setOrg_id(orgId);
        schedule_task_detail.getTask_detail().setUser_id(userId);
        schedule_task_detail.getTask_detail().setOrg_id(orgId);
        if (schedule_task_detail.getName() == null) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), "name"), null);
        }
        if (schedule_task_detail.getOrg_id() == null) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), "schedule_org_id"), null);
        }
        if (schedule_task_detail.getUser_id() == null) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), "schedule_user_id"), null);
        }
        if (schedule_task_detail.getTask_detail().getName() == null) {
            schedule_task_detail.getTask_detail().setName("");
        }
        return taskService.addScheduleTask(schedule_task_detail, (String) models.get("language"));
    }


    /**
     * 查询任务
     *
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findTasks", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public BaseResult findTasks(@RequestBody Map<String, Object> map, HttpServletRequest request) throws Exception {
        Long user_id = (Long) request.getAttribute("userId");
        List<Long> org_ids = (List<Long>) request.getAttribute("dataScopeOrgIds");
        Integer pageNumber = (Integer) map.get("pageNumber");
        Integer pageSize = (Integer) map.get("pageSize");
        String keyWord = (String) map.get("keyWord");
        Integer taskStatus = (Integer) map.get("taskStatus");
        String language = (String) map.get("language");
        BaseResult baseResult = taskService.selectTasks(pageNumber, pageSize, keyWord, taskStatus, language, user_id, org_ids,0,0);
        return baseResult;
    }

    /**
     * 根据id查询任务（详情）
     *
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findTaskbyId", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult findTaskbyId(@RequestBody Map<String, Object> map) throws Exception {
        Integer a = (Integer) map.get("id");
//		Long id = (Long) map.get("id");
        String language = (String) map.get("language");
        if (!map.containsKey("id")) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(language, "id"), null);
        }
        BaseResult result = taskService.findTasksbyId(a.longValue(), language);
        return result;
    }

    @RequestMapping(value = "module/findScheduleTaskbyId", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult mfindTaskbyId(@RequestBody Map<String, Object> map) throws Exception {
        Integer a = (Integer) map.get("id");
        String language = (String) map.get("language");
        if (!map.containsKey("id")) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(language, "id"), null);
        }
        BaseResult result = taskService.findScheduleTaskbyId(a.longValue(), language);
        return result;
    }

    /**
     * 根据id设置定时器状态（0运行，1暂停，2删除）
     *
     * @param models
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateTaskScheduleStatusbyId", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public BaseResult updateTaskScheduleStatusbyId(@RequestBody Map models) throws Exception {
        List<Integer> ids = (List<Integer>) models.get("ids");
        Integer status = (Integer) models.get("status");
        String language = (String) models.get("language");
        if (!models.containsKey("ids")) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(language, "ids"), null);
        }
        BaseResult baseResult = taskService.updateTaskScheduleStatusbyId(ids, status, language);
        return baseResult;
    }

    @RequestMapping(value = "/module/updateTaskScheduleStatusbyId", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public BaseResult mupdateTaskScheduleStatusbyId(@RequestBody Map models) throws Exception {
        List<Integer> ids = (List<Integer>) models.get("ids");
        Integer status = (Integer) models.get("status");
        String language = (String) models.get("language");
        if (!models.containsKey("ids")) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(language, "ids"), null);
        }
        BaseResult baseResult = taskService.updateTaskScheduleStatusbyId(ids, status, language);
        return baseResult;
    }

    @RequestMapping(value = "/module/findUsersByTaskIds", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public BaseResult findUsersByTaskIds(@RequestBody Map models) throws Exception {
        List<Integer> taskIds = (List<Integer>) models.get("taskIds");
        String language = (String) models.get("language");
        if (!models.containsKey("taskIds")) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(language, "taskIds"), null);
        }
        BaseResult baseResult = taskService.findUsersByTaskIds(taskIds, language);
        return baseResult;
    }

    @RequestMapping(value = "/module/findTaskIdByUTId", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public BaseResult findTaskIdByUTId(@RequestBody Map models) throws Exception {
        List<Integer> taskIds = (List<Integer>) models.get("taskIds");
        Integer user_id = (Integer) models.get("user_id");
        String language = (String) models.get("language");
        if (!models.containsKey("taskIds")) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(language, "taskIds"), null);
        }
        if (!models.containsKey("user_id")) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(language, "user_id"), null);
        }
        BaseResult baseResult = taskService.findTaskIdByUTId(taskIds, user_id, language);
        return baseResult;
    }

    /**
     * 根据id修改定时器
     *
     * @param models
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateScheduleTask")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public BaseResult updateScheduleTask(@RequestBody Map<String, Object> models) throws Exception {
        Schedule_Task_Detail schedule_task_detail = JsonXMLUtils.map2obj((Map<String, Object>) models.get("schedule_task_detail"), Schedule_Task_Detail.class);
        String a = objectMapper.writeValueAsString(schedule_task_detail);
        log.info(a);
        return taskService.updateScheduleTask(schedule_task_detail, (String) models.get("language"));
    }

//****************************************************************************************************************************************

    /**
     * 根据id查询任务（详情）
     *
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/module/findTaskbyId", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult moduleFindTaskbyId(@RequestBody Map<String, Object> map) throws Exception {
        List<Integer> ids = (List<Integer>) map.get("ids");
        String language = (String) map.get("language");
        if (!map.containsKey("ids")) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(language, "ids"), null);
        }
        BaseResult result = taskService.findTasksbyIds(ids, language);
        return result;
    }


    /**
     *
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/module/findTasksCount", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult moduleFindTasksCount(@RequestBody Map<String, Object> map) throws Exception {
        String language = (String) map.get("language");
        BaseResult result = taskService.findTasksCount(language);
        return result;
    }


    /**
     * 添加任务
     *
     * @param models
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/module/addTask", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public BaseResult moduleAddTask(@Valid @RequestBody Map<String, Object> models, BindingResult result) throws Exception {
        if (result.hasErrors()) {//不起作用？？？？？？
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), error.getDefaultMessage()), null);
            }
        }
        Task_Detail task_detail = JsonXMLUtils.map2obj((Map<String, Object>) models.get("task_detail"), Task_Detail.class);
        if (task_detail.getOrg_id() == null) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), "org_id"), null);
        }
        if (task_detail.getUser_id() == null) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), "user_id"), null);
        }
        if (task_detail.getStatus() == null) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), "status"), null);
        }
        if (task_detail.getName() == null) {
            task_detail.setName("");
        }
        return taskService.addTask(task_detail, (String) models.get("language"));
    }

    /**
     * 根据id修改任务以及detail
     *
     * @param models
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/module/updateTask")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public BaseResult moduleUpdateTask(@RequestBody Map<String, Object> models) throws Exception {
        Task_Detail task_detail = JsonXMLUtils.map2obj((Map<String, Object>) models.get("task_detail"), Task_Detail.class);
        if (task_detail.getId() == null) {
            return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes((String) models.get("language"), "id"), null);
        }
        return taskService.updateTask(task_detail, (String) models.get("language"));
    }

    @RequestMapping(value = "/test")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public BaseResult test(@RequestBody Map<String, Object> models) throws Exception {
//		Map reqMap = new HashMap();
//		reqMap.put("language","zh_cn");
//		reqMap.put("appTypeId",1);
//		reqMap.put("token","token****");
//		reqMap.put("action","");
//		HttpResult httpResult = httpAPIService.doPost(Constant.BASE_URL+"/auth/findAuth",reqMap);
//		if(httpResult==null || httpResult.getCode()!=200 || httpResult.getBody()==null){
//			throw new CustomException(ResLanguage.getRes_system_err_mes("zh_cn"),ResLanguage.RES_SYSTEM_ERR_CODE);
//		}
//		String body = httpResult.getBody();
//		Map map = objectMapper.readValue(body,Map.class);
//		if((Integer)map.get("code")!=200){
//			return new BaseResult((Integer)map.get("code"), (String)map.get("message"), map.get("data"));
//		}else{
//			return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(""), map);
//		}

        return new BaseResult(ResLanguage.RES_SUCCESS_CODE, ResLanguage.getRes_success_mes(""), models);
    }

    @RequestMapping(value = "/findDevLog", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public BaseResult findDevLog(@Valid @RequestBody FindDevLogBean bean, BindingResult result, HttpServletRequest request) throws Exception {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(bean.getLanguage(), error.getDefaultMessage()), null);
            }
        }
        Long user_id = (Long) request.getAttribute("userId");
        List<Long> org_ids = (List<Long>) request.getAttribute("dataScopeOrgIds");
        return taskService.findDevLog(bean,user_id,org_ids);
    }

    @RequestMapping(value = "/app/findDevLog", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public BaseResult appFindDevLog(@Valid @RequestBody FindDevLogBean bean, BindingResult result, HttpServletRequest request) throws Exception {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                return new BaseResult(ResLanguage.RES_PARAM_NO_CODE, ResLanguage.getRes_param_no_mes(bean.getLanguage(), error.getDefaultMessage()), null);
            }
        }
        Long user_id = (Long) request.getAttribute("userId");
        List<Long> org_ids = (List<Long>) request.getAttribute("dataScopeOrgIds");
        return taskService.appfindDevLog(bean,user_id,org_ids);
    }

}
