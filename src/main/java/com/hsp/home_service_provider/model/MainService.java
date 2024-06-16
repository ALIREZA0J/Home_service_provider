package com.hsp.home_service_provider.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "main_service_tbl")
@Builder(setterPrefix = "set")
public class MainService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "service_name",nullable = false,unique = true)
    private String serviceName;

    @OneToMany(mappedBy = "mainService" ,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE})
    private List<SubService> subService;

    @Override
    public String toString() {
        return "MainService{" + "Id- " + id +
                " | serviceName: " + serviceName +
                " }";
    }
}
