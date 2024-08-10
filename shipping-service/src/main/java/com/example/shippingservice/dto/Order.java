package com.example.shippingservice.dto;

import lombok.Data;


@Data
public class Order {

    private Long orderId;
    private String userEmail;
    private String orderStatus;
    private double amount;

}
