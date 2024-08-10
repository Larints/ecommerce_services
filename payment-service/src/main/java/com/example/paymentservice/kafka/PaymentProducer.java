package com.example.paymentservice.kafka;

import com.example.paymentservice.dto.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentProducer {

    private static final String TOPIC = "payed_orders";


    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PaymentProducer.class);

    public void sendMessage(Order order) {
        logger.info("sending to shipment order information {}", order);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String message = objectMapper.writeValueAsString(order);
            kafkaTemplate.send(TOPIC, message);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }
}
