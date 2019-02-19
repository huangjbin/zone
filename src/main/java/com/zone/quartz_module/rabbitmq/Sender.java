//package com.zone.quartz_module.rabbitmq;
//
//import com.zone.quartz_module.common.Constant;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Sender {
//
//    @Autowired
//    private AmqpTemplate rabbitTemplate;
//
//    public void send(String context) {
//        this.rabbitTemplate.convertAndSend(Constant.TASK_XC,context);
////        this.rabbitTemplate.send(Constant.TASK_XC, Constant.JSON_TASK_KEY, new Message(context.getBytes(),null));
//    }
//}
