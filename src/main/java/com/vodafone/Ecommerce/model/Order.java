package com.vodafone.Ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @Column
    @Getter
    @Setter
    private float total;

    @OneToMany(mappedBy = "order" ,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
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
