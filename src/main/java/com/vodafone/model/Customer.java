package com.vodafone.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter @AllArgsConstructor
@Entity
@Table ( name = "customers")
public class Customer extends User {


    private String code;

    /*@OneToOne(mappedBy = "customer", fetch = FetchType.EAGER)
    private Cart cart = new Cart(this,new ArrayList<>());*/
    //Inverted cutomer-cart relationship to make customer the owner

    @OneToOne
    @JoinColumn(name = "cartId")
    private Cart cart;
    @OneToMany(mappedBy = "customer" ,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Order> orders;

    public Customer() {
        this.userStatus = UserStatus.DEACTIVATED;
        cart = new Cart(this,new ArrayList<>());
    }
}
