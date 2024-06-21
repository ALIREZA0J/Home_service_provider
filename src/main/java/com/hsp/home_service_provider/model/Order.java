package com.hsp.home_service_provider.model;

import com.hsp.home_service_provider.model.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_tbl")
@Builder(setterPrefix = "set")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "porposed_price")
    private Long proposedPrice;

    @Column(name = "date_of_work")
    private LocalDate dateOfWork;

    @Column(name = "time_of_work")
    private LocalTime timeOfWork;

    @Column(name = "order_status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "address")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "sub_service_id")
    private SubService subService;

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    private List<Offer> offers;

    @Override
    public String toString() {
        return "Order { Id-" + id +
                " | description: " + description +
                " | proposed price:" + proposedPrice +
                " | date of work: " + dateOfWork +
                " | time of work: " + timeOfWork +
                " | order status: " + orderStatus +
                " | address: " + address +
                " | sub service of order: " + subService.getName() + " }" ;
    }
}
