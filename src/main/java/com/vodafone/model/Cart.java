package com.vodafone.model;

import lombok.*;

import javax.persistence.*;

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
    @OneToOne
    @JoinColumn(name = "customer_id")
    Customer customer;
    @ManyToMany(cascade = CascadeType.REMOVE)
    List<CartItem> items;
}
