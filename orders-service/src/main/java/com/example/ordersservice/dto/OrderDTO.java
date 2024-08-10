package com.example.ordersservice.dto;

import com.example.ordersservice.entity.Order;
import com.example.ordersservice.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String userEmail;
    private String orderStatus;
    private double amount;
}
