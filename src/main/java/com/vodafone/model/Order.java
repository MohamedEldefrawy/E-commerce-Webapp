package com.vodafone.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;

@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerId")
    @Setter
    private Customer customer;

    @Column
    @Getter
    @Setter
    private Date date;

    @OneToMany(cascade = CascadeType.ALL)
    @Getter
    Set<OrderItem> orderItems = new HashSet<>();

    public Order(Customer customer, Date date, Set<OrderItem> orderItems) {
        this.customer = customer;
        this.date = date;
        this.orderItems = orderItems;
    }

}
