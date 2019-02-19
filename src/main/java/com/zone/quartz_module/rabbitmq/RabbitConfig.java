package com.zone.quartz_module.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.zone.quartz_module.common.Constant;
import com.zone.quartz_module.service.impl.SeviceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Map;


@Configuration
@EnableAutoConfiguration
public class RabbitConfig {

    public static Logger log = LoggerFactory.getLogger(RabbitConfig.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String vhost;


//    public static final String EXCHANGE   = "spring-boot-exchange";
//    public static final String ROUTINGKEY = "spring-boot-routingKey";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(vhost);
        connectionFactory.setPublisherConfirms(true); //必须要设置
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     *
     *
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(Constant.PTS_XC);
    }

    @Bean
    public Queue queue() {
        return new Queue(Constant.JSON_TASK_RES_Q, true); //队列持久

    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(Constant.JSON_TASK_RES_KEY);
    }

    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(queue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(new ChannelAwareMessageListener() {

            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                System.out.println("receive msg : " + new String(body));

                        try {
            Map<String,Object> map = objectMapper.readValue(new String(body), Map.class);
            Integer log_id = (Integer) map.get("log_id");
            Integer status = (Integer) map.get("status");
            SeviceUtils.updateTaskLog(log_id.longValue(), status);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
            }
        });
        return container;
    }


}
