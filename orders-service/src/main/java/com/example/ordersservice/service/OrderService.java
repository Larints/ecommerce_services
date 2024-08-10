package com.example.ordersservice.service;

import com.example.ordersservice.dto.OrderDTO;
import com.example.ordersservice.entity.Order;
import com.example.ordersservice.entity.Product;
import com.example.ordersservice.entity.User;
import com.example.ordersservice.kafka.OrderProducer;
import com.example.ordersservice.repository.OrderRepository;
import com.example.ordersservice.repository.ProductRepository;
import com.example.ordersservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderProducer orderProducer;

    private final UserRepository userRepository;

    // Создание нового заказа
    public Order createOrder(Order order) {

        User user = order.getUser();
        userRepository.save(user);

        // Сохраняем продукты перед добавлением в заказ
        List<Product> savedProducts = new ArrayList<>();
        for (Product product : order.getProductsList()) {
            savedProducts.add(productRepository.save(product));
        }

        // Обновляем список продуктов в заказе
        order.setProductsList(savedProducts);
        order.setOrderStatus(Order.OrderStatus.NEW);

        // Добавляем заказ в список заказов пользователя
        user.addOrder(order);

        // Сохраняем заказ
        Order savedOrder = orderRepository.save(order);

        // Создаем DTO и отправляем сообщение в Kafka
        OrderDTO orderDTO = OrderDTO.builder()
                .orderId(savedOrder.getId())
                .orderStatus(savedOrder.getOrderStatus().name())
                .amount(savedOrder.getAmount())
                .userEmail(savedOrder.getUser().getUserEmail())
                .build();

        orderProducer.sendMessage(orderDTO);

        return savedOrder;

    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(Order.OrderStatus.valueOf(status));
        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

}
