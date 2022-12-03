package com.vodafone.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table ( name = "customers")
public class Customer extends User {
    /*@OneToOne(mappedBy = "customer", fetch = FetchType.EAGER)
    private Cart cart = new Cart(this,new ArrayList<>());*/
    //Inverted cutomer-cart relationship to make customer the owner
    @OneToOne
    @JoinColumn(name = "cartId")
    private Cart cart;
    @Column (nullable = false)
    private UserStatus userStatus;
    @OneToMany(mappedBy = "customer" ,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Order> orders;

    public Customer(UserStatus userStatus) {
        this.userStatus = userStatus;
        cart = new Cart(this,new ArrayList<>());
    }
}
