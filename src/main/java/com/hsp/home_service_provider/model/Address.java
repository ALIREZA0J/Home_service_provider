package com.hsp.home_service_provider.model;

import com.hsp.home_service_provider.model.enums.City;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address_tbl")
@Builder(setterPrefix = "set")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    @Enumerated
    private City city;

    @Column(name = "street_name")
    private String street;

    @Column(name = "alley_name")
    private String alley;

    @Column(name = "plaque_namber")
    private Integer plaque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "address",cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Order> orders;

    @Override
    public String toString() {
        return "Address{" +
                " Id- " + id +
                " | city=" + city +
                " | street= " + street +
                " | alley= " + alley +
                " | plaque= " + plaque +
                "} " ;
    }
}
