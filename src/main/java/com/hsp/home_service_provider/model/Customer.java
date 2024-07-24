package com.hsp.home_service_provider.model;


import com.hsp.home_service_provider.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
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

    @PrePersist
    private void defaultValues(){
        if (this.credit == null) this.credit = 0L;
        if (super.registrationDate == null) super.registrationDate = LocalDate.now();
        if (super.role == null) super.role = Role.ROLE_CUSTOMER;
        if (super.isActive == null) super.isActive = false;
        if (super.locked == null) super.locked = false;
    }
}
