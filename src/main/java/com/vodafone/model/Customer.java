package com.vodafone.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table ( name = "customers")
public class Customer extends User {
    @OneToOne
    private Cart cart;
    @Column (nullable = false)
    private UserStatus userStatus;
    @OneToMany
    private List<Order> orders;


}
