package com.vodafone.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cartItem")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private  int quantity;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    private Cart cart;
}
