package com.vodafone.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    private String name;
    private String description;
    private String image;
    private String category;
    @ColumnDefault("0")
    private float rate;
    private double price;
    @ColumnDefault("10")
    private int inStock;
    private boolean isDeleted;

}
