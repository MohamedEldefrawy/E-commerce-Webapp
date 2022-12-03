package com.vodafone.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    /*@OneToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    Customer customer;*/
    @OneToOne(mappedBy = "cart")
    private Customer customer;
    @OneToMany
    List<CartItem> items;

    public Cart(Customer customer, List<CartItem> items) {
        this.customer = customer;
        this.items = items;
    }
}
