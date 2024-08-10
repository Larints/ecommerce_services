package com.example.notificationservice.kafka;

import com.example.notificationservice.dto.Order;
import com.example.notificationservice.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    private final static Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

    @KafkaListener(topics = "sent_orders", groupId = "notification_group")
    public void consumeShippedOrder(String message) {
        logger.info("Received shipment order information: {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Order order = objectMapper.readValue(message, Order.class);
            notificationService.sendNotification(order);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }
}
