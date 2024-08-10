package com.example.paymentservice.kafka;

import com.example.paymentservice.dto.Order;
import com.example.paymentservice.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentConsumer {


    private final PaymentService paymentService;

    private final static Logger logger = LoggerFactory.getLogger(PaymentConsumer.class);

    @KafkaListener(topics = "new_orders", groupId = "payment_group")
    public void consume(String message) {
        logger.info("Received new order for payment {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Order order = objectMapper.readValue(message, Order.class);
            paymentService.processPayment(order);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }
}
