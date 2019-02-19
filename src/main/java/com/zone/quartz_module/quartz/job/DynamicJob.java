package com.zone.quartz_module.quartz.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zone.quartz_module.common.Constant;
import com.zone.quartz_module.pojo.QuartzDataBean;
import com.zone.quartz_module.pojo.ScheduleJob;
import com.zone.quartz_module.pojo.TaskLog;
import com.zone.quartz_module.service.impl.SeviceUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by EalenXie on 2018/6/4 14:29
 * :@DisallowConcurrentExecution : 此标记用在实现Job的类上面,意思是不允许并发执行.
 * :注意org.quartz.threadPool.threadCount线程池中线程的数量至少要多个,否则@DisallowConcurrentExecution不生效
 * :假如Job的设置时间间隔为3秒,但Job执行时间是5秒,设置@DisallowConcurrentExecution以后程序会等任务执行完毕以后再去执行,否则会在3秒时再启用新的线程执行
 */
@DisallowConcurrentExecution
@Component
public class DynamicJob implements Job {
    private Logger logger = LoggerFactory.getLogger(DynamicJob.class);

    /**
     * 核心方法,Quartz Job真正的执行逻辑.
     * @param executorContext executorContext JobExecutionContext中封装有Quartz运行所需要的所有信息
     * @throws JobExecutionException execute()方法只允许抛出JobExecutionException异常
     */
    @Override
    public void execute(JobExecutionContext executorContext) {
        ScheduleJob scheduleJob = (ScheduleJob) executorContext.getMergedJobDataMap().get(Constant.SCHEDULE_JOB_DATA_KEY);

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        logger.info("----------" + dateString + "---------");
        logger.info("scheduleJob:" + scheduleJob.toString());
        String param = scheduleJob.getParam();
        try {
            List<QuartzDataBean> list = SeviceUtils.changeStr2TaskDataBean(param);
            Collections.sort(list, new Comparator<QuartzDataBean>() {
                public int compare(QuartzDataBean o1, QuartzDataBean o2) {
                    if (o1.getSort() < o2.getSort() ) {
                        return -1;
                    }
                    if (o1.getSort()  == o2.getSort() ) {
                        return 0;
                    }
                    return 1;
                }
            });
            for(QuartzDataBean quartzDataBean :list){
                if(quartzDataBean.getTask_type()==2){//1、通知人，2、通知设备
                    logger.info("通知设备:" + quartzDataBean.toString());
                    sendDevice(quartzDataBean);
                }
                if(quartzDataBean.getTask_type()==1){
                    logger.info("通知人:" + quartzDataBean.toString());
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void sendDevice(QuartzDataBean quartzDataBean) throws JsonProcessingException {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("act", quartzDataBean.getAct());
        map.put("d_id", quartzDataBean.getD_id());
        map.put("param_ids", quartzDataBean.getParam_ids());
        map.put("data", quartzDataBean.getData());
        map.put("device_id", quartzDataBean.getDevice_id());
        map.put("type", quartzDataBean.getType());
        map.put("t_id", quartzDataBean.getT_id());
        logger.info("send to MQ:---"+SeviceUtils.change_map(map));
        TaskLog tLog = new TaskLog();
        tLog.setMsg(SeviceUtils.change_map(map));
        tLog.setResult(9);
        tLog.setRun_time(new Date().getTime() / 1000);
        tLog.setTask_detail_id(quartzDataBean.getD_id());
        tLog.setTask_id(quartzDataBean.getT_id());
        tLog.setEnd_time(new Date().getTime() / 1000);
        SeviceUtils.addTaskLog(tLog);

        if(tLog.getId()!=null){
            map.put("log_id", tLog.getId());
        SeviceUtils.sendMap2MQ(map);
        }
    }

}
