package com.example.notificationservice.service;


import com.example.notificationservice.dto.Order;
import com.example.notificationservice.kafka.NotificationProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationProducer notificationProducer;

    public void sendNotification(Order order) {
        order.setOrderStatus("COMPLETED");
        notificationProducer.sendMessage(order);
    }

}
