package com.example.ordersservice.kafka;

import com.example.ordersservice.dto.OrderDTO;
import com.example.ordersservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderConsumer {

    private final OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(topics = "payed_orders", groupId = "orders_group")
    public void consumePayedOrder(String message) {
        logger.info("Received payed order message: {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            OrderDTO order = objectMapper.readValue(message, OrderDTO.class);
            orderService.updateOrderStatus(order.getOrderId(), order.getOrderStatus());
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }

    @KafkaListener(topics = "shipped_orders", groupId = "orders_group")
    public void consumeShippedOrder(String message) {
        logger.info("Received shipping order message: {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            OrderDTO order = objectMapper.readValue(message, OrderDTO.class);
            orderService.updateOrderStatus(order.getOrderId(), order.getOrderStatus());
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }

    @KafkaListener(topics = "notified_orders", groupId = "orders_group")
    public void consumeNotifiedOrder(String message) {
        logger.info("Received notify: {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            OrderDTO order = objectMapper.readValue(message, OrderDTO.class);
            orderService.updateOrderStatus(order.getOrderId(), order.getOrderStatus());
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }
}
