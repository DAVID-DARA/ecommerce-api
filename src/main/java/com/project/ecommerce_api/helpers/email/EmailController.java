package com.project.ecommerce_api.helpers.email;

import com.project.ecommerce_api.helpers.rabbitmq.RabbitMQProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")

public class EmailController {

    private final RabbitMQProducer rabbitMQProducer;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        rabbitMQProducer.sendEmail(emailRequest);
        return ResponseEntity.ok("Email request sent to queue.");
    }
}
