package com.hsp.home_service_provider.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "admin_tbl")
@SuperBuilder(setterPrefix = "set")
public class Admin extends Person{

    public Admin() {
    }
}
