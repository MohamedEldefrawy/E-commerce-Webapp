package com.vodafone.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    private String image;
    private String category;
    private float rate;
    private double price;
    private int inStock;
}
