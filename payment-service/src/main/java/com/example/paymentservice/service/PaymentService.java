package com.example.paymentservice.service;

import com.example.paymentservice.dto.Order;
import com.example.paymentservice.kafka.PaymentProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentProducer paymentProducer;

    public void processPayment(Order order) {
        // Логика обработки платежа
        order.setOrderStatus("PAYED");
        paymentProducer.sendMessage(order);
    }
}
