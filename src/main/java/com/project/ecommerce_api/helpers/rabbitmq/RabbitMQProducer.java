package com.project.ecommerce_api.helpers.rabbitmq;

import com.project.ecommerce_api.helpers.email.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE_NAME = "email_exchange";
    private static final String ROUTING_KEY = "emailRoutingKey";
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQProducer.class);

    public void sendEmail(EmailRequest emailRequest) {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, emailRequest);
            logger.info("Sent email message to RabbitMQ.");
    }
}
