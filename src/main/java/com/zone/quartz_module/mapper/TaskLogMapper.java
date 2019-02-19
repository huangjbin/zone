package com.zone.quartz_module.mapper;

import com.zone.quartz_module.param.FindDevLogBean;
import com.zone.quartz_module.param.FindTaskLog;
import com.zone.quartz_module.pojo.Detail;
import com.zone.quartz_module.pojo.TaskLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
@Mapper
public interface TaskLogMapper {


    /**
     * 查询任务日志
     * @param taskLog
     * @return
     */
    List<TaskLog> selectTaskLogs(FindTaskLog taskLog);

    List<TaskLog> findDevLog(FindDevLogBean devLogBean);
    
    /**
     * 查看日志总数
     * @return
     */
    long totalTaskLog();

    Long findDevLogCount(FindDevLogBean findDevLogBean);

    
    /**
     * 添加日志
     * @param taskLog
     * @return
     */
    Long addTaskLog(TaskLog taskLog);
    
    int updateTaskLog(Map map);
    
    Long totalTaskLogByTaskId(@Param("task_id") Long task_id);

    Detail getTaskDetailByoId(@Param("id") Long id);
}