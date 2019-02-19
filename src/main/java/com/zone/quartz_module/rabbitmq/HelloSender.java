//package com.zone.quartz_module.rabbitmq;
//
//import com.zone.quartz_module.common.Constant;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class HelloSender {
//
//	@Autowired
//	private AmqpTemplate rabbitTemplate;
//
//	public void send() {
//		String context = "hello " + new Date();
//		System.out.println("Sender : " + context);
//		this.rabbitTemplate.convertAndSend("hello", context);
//	}
//
//	public void sendMsg(String content) {
//		rabbitTemplate.convertAndSend(Constant.TASK_XC, Constant.JSON_TASK_KEY, content);
//	}
//
//}