package com.vodafone.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "orderItem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    Product product;

    @Column
    @Getter
    @Setter
    int quantity;


}
