//package com.zone.quartz_module.rabbitmq;
//
//import com.zone.quartz_module.common.Constant;
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class TopicRabbitConfig {
//
////    final static String message = Constant.JSON_TASK_RES_Q;
//
//    @Bean
//    public Queue queueMessage() {
//        return new Queue("JSON_TASK_RES_KEY",true);
//    }
//
//    @Bean
//    DirectExchange exchange() {
//        return new DirectExchange(Constant.PTS_XC);
//    }
//
//    @Bean
//    Binding bindingExchangeMessage(Queue queueMessage, DirectExchange exchange) {
//        return BindingBuilder.bind(queueMessage).to(exchange).with(Constant.JSON_TASK_RES_KEY);
//    }
//}
