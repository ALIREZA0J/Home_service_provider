package com.hsp.home_service_provider.model;

import com.github.mfathi91.time.PersianDate;
import com.hsp.home_service_provider.model.enums.SpecialistStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder(setterPrefix = "set")
@Table(name = "specialist_tbl")
public class Specialist extends Person{

    @Column(name = "credit")
    private Long credit;

    @Column(name = "socre")
    private Double score;

    @Column(name = "specialist_status")
    @Enumerated(value = EnumType.STRING)
    private SpecialistStatus specialistStatus;

    @ManyToMany(mappedBy = "specialists",fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<SubService> subServices;

    @OneToOne(mappedBy = "specialist",cascade = {CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Avatar avatar;

    @OneToMany(mappedBy = "specialist",
            cascade = {CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Offer> offers;

    @Override
    public String toString() {
        return "Specialist{" +
                " Id- " + super.getId() +
                " | full name: " + super.getFirstName() + " " + super.getLastName() +
                " | gmail: " + super.getGmail() +
                " | registration date : " + PersianDate.fromGregorian(super.getRegistrationDate()) +
                " | credit = " + credit +
                " | specialistStatus=" + specialistStatus +
                " } " ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialist that = (Specialist) o;
        return Objects.equals(credit, that.credit) && Objects.equals(score, that.score) && specialistStatus == that.specialistStatus && Objects.equals(subServices, that.subServices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(credit, score, specialistStatus, subServices);
    }
}
