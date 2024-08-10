package com.example.ordersservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    private OrderStatus orderStatus;
    private LocalDate orderDate;
    private double amount;

    @OneToMany
    private List<Product> productsList;

    public enum OrderStatus {
        NEW, PAYED, SHIPPED, COMPLETED, CANCELLED
    }

}
