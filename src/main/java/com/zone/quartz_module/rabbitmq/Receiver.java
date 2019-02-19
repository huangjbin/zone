//package com.zone.quartz_module.rabbitmq;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.zone.quartz_module.service.impl.SeviceUtils;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//
//@Component
//@RabbitListener
//public class Receiver {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @RabbitHandler
//    public void process(String mes) {
//        try {
//            Map<String,Object> map = objectMapper.readValue(mes, Map.class);
//            Integer log_id = (Integer) map.get("log_id");
//            Integer status = (Integer) map.get("status");
//            SeviceUtils.updateTaskLog(log_id, status);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//}
