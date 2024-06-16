package com.hsp.home_service_provider.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder(setterPrefix = "set")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[A-Za-z\\s]+$",message = "First name most consist of letter only.")
    @Size(min = 3,max = 60,message = "At least three characters must be written for the name")
    @Column(name = "first_name")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z\\s]+$",message = "First name most consist of letter only.")
    @Size(min = 3,max = 60,message = "At least three characters must be written for the name")
    @Column(name = "last_name")
    private String lastName;

    @Email(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    @Column(name = "gmail",unique = true,nullable = false)
    private String gmail;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])"+"(?=.*[0-9])(?=.*[@#!%&*])[A-Za-z0-9@#!%&*]{8,}$"
            ,message = "Password should contain At least one (small word, capital letter, number , @ # ! % & *) and " +
            "it must be at least eight characters.")
    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "registration_date")
    private LocalDate registrationDate;


}
