package com.vodafone.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerId")
    @Setter
    @Getter
    private Customer customer;

    @Column
    @Getter
    @Setter
    private Date date;

    @OneToMany(mappedBy = "order" ,cascade = CascadeType.ALL)
    @Getter
    @Setter
    Set<OrderItem> orderItems = new HashSet<>();

    public Order(Customer customer, Date date, Set<OrderItem> orderItems) {
        this.customer = customer;
        this.date = date;
        this.orderItems = orderItems;
    }

    public Order(Customer customer, Date date) {
        this.customer = customer;
        this.date = date;
    }
}
