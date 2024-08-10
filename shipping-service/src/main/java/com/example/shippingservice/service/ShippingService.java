package com.example.shippingservice.service;


import com.example.shippingservice.dto.Order;
import com.example.shippingservice.kafka.ShippingProducer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShippingService {


    private final ShippingProducer shippingProducer;

    public void processShipping(Order order) {
        // Логика отгрузки
        order.setOrderStatus("SHIPPED");
        shippingProducer.sendMessage(order);
    }
}
