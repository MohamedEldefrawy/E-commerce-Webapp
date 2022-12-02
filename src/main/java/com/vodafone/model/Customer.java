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
    @OneToMany(mappedBy = "customer" ,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Order> orders;


}
