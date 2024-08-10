package com.example.shippingservice.kafka;


import com.example.shippingservice.dto.Order;
import com.example.shippingservice.service.ShippingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShippingConsumer {


    private final ShippingService shippingService;

    private final static Logger logger = LoggerFactory.getLogger(ShippingConsumer.class);

    @KafkaListener(topics = "payed_orders", groupId = "shipping_group")
    public void consume(String message) {
        logger.info("received payment order information: {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Order order = objectMapper.readValue(message, Order.class);
            shippingService.processShipping(order);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }
}
