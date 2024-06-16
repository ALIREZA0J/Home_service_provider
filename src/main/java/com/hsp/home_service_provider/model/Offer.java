package com.hsp.home_service_provider.model;


import com.github.mfathi91.time.PersianDate;
import com.hsp.home_service_provider.model.enums.OfferStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(setterPrefix = "set")
@Entity
@Table(name = "offer_tbl")

public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;
    @Column(name = "time_to_register_offer")
    private LocalDateTime timeToRegisterOffer;
    @Column(name = "offer_price")
    private Long offerPrice;

    @Column(name = "days_of_work")
    private Integer daysOfWork;

    @Column(name = "duration_of_the_work")
    private LocalTime durationOfWork;

    @Column(name = "offer_status")
    @Enumerated(value = EnumType.STRING)
    private OfferStatus offerStatus;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(mappedBy = "offer" , cascade = CascadeType.ALL)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private Specialist specialist;


    @Override
    public String toString() {
        return "Offer{ Id- " + id +
                " |specialist: " + specialist.getFirstName() + " " + specialist.getLastName() +
                " |time to register offer: " + PersianDate.fromGregorian(timeToRegisterOffer.toLocalDate()) +
                " - " + timeToRegisterOffer.toLocalTime() +
                " |offer price: " + offerPrice +
                " |number of working days: " + daysOfWork +
                " |duration of work: " + durationOfWork +
                " |offer Status: " + offerStatus +
                "} ";
    }
}
