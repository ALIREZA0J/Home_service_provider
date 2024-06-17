package com.hsp.home_service_provider.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder(setterPrefix = "set")
@Table(name = "sub_service_tbl")
public class SubService  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[A-Za-z\\s]+$",message = "Name most consist of letter only.")
    @Size(min = 5,max = 60,message = "At least five characters must be written for the name")
    @Column(name = "sub_service_name",nullable = false,unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Min(value = 300_000,message = "The basic price of a sub-service should be at least 300_000.")
    @Column(name = "base_price")
    private Long basePrice;


    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<Specialist> specialists;

    @ManyToOne
    @JoinColumn(name = "main_service_id")
    private MainService mainService;

    @OneToMany(mappedBy = "subService",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Order> orders;


    @Override
    public String toString() {
        return "Sub Service { Id- " + id +
                " | name= " + name +
                " | description= " + description +
                " | basePrice= " + basePrice + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubService that = (SubService) o;
        return Objects.equals(name, that.name) && Objects.equals(basePrice, that.basePrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, basePrice);
    }
}
