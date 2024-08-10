package com.example.shippingservice.kafka;


import com.example.shippingservice.dto.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShippingProducer {

    private static final String TOPIC = "sent_orders";

    private static final Logger logger = LoggerFactory.getLogger(ShippingProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Order order) {
        logger.info("Sending shipment order information to notification service {}", order);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String message = objectMapper.writeValueAsString(order);
            kafkaTemplate.send(TOPIC, message);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }
}
