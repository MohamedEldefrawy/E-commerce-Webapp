package com.vodafone.Ecommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends User {

    private int loginAttempts;

    private String code;

    /*@OneToOne(mappedBy = "customer", fetch = FetchType.EAGER)
    private Cart cart = new Cart(this,new ArrayList<>());*/
    //Inverted cutomer-cart relationship to make customer the owner

    @OneToOne
    @JoinColumn(name = "cartId")
    private Cart cart;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Order> orders;

    public Customer() {
        this.userStatus = UserStatus.DEACTIVATED;
        this.loginAttempts = 3;
        cart = new Cart(this, new ArrayList<>());
    }
}
