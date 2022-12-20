package com.vodafone.model;


import lombok.*;
import javax.persistence.*;
import com.vodafone.model.Product;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cartItem")
public class CartItem {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;


    @Getter
    private  int quantity;

    @Setter
    @Getter
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Setter
    @Getter
    @ManyToOne
    private Cart cart;

    @Setter
    @Getter
    private double total;

    public CartItem(int quantity, Product product, Cart cart) {
        this.quantity = quantity;
        this.product = product;
        this.cart = cart;
        this.total=quantity*product.getPrice();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.total = quantity*product.getPrice();
    }
}
