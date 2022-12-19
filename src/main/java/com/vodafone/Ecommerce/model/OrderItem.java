//package com.vodafone.Ecommerce.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "orderItem")
//public class OrderItem {
//    @Getter
//    @Setter
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    @Getter
//    @Setter
//    @ManyToOne(fetch = FetchType.LAZY)
//    Order order;
//
//    @Getter
//    @Setter
//    @ManyToOne(fetch = FetchType.EAGER)
//    Product product;
//
//    @Getter
//    @Column
//    int quantity;
//
//    @Getter
//    @Setter
//    double total;
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//        this.total = quantity*product.getPrice();
//    }
//}
