package com.example.ordersservice.kafka;

import com.example.ordersservice.dto.OrderDTO;
import com.example.ordersservice.entity.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderProducer {

    private static final String TOPIC = "new_orders"; // Имя темы в Kafka

    private static final Logger logger = LoggerFactory.getLogger(OrderProducer.class);


    private final KafkaTemplate<String, String> kafkaTemplate; // KafkaTemplate используется для отправки сообщений

    public void sendMessage(OrderDTO order) {
        logger.info("Sending order to payment service: {}", order);
        ObjectMapper objectMapper = new ObjectMapper(); // Создаем экземпляр ObjectMapper для преобразования объекта в JSON
        try {
            String message = objectMapper.writeValueAsString(order); // Преобразуем объект Order в строку JSON
            kafkaTemplate.send(TOPIC, message); // Отправляем JSON в тему Kafka
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());; // Обрабатываем исключение, если преобразование не удалось
        }
    }
}
