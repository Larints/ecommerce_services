package com.example.notificationservice.kafka;

import com.example.notificationservice.dto.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationProducer {

    private static final String TOPIC = "notified_orders";

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final static Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

    public void sendMessage(Order orderDTO) {
        logger.info("Sending notification to user: {}", orderDTO);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String message = objectMapper.writeValueAsString(orderDTO);
            kafkaTemplate.send(TOPIC, message);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }
}
