package com.hsp.home_service_provider.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(setterPrefix = "set")
@Table(name = "customer_tbl")
public class Customer extends Person{

    @Column(name = "credit")
    private Long credit;

    @OneToMany(mappedBy = "customer" , cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Order> orders;

    @OneToMany(mappedBy = "customer" , cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Address> addresses;

    @OneToMany(mappedBy = "customer" , cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Comment> comments;
}
