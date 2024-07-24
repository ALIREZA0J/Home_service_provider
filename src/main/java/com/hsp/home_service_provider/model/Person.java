package com.hsp.home_service_provider.model;


import com.hsp.home_service_provider.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder(setterPrefix = "set")
public class Person implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[A-Za-z\\s]+$",message = "First name most consist of letter only.")
    @Size(min = 3,max = 60,message = "At least three characters must be written for the name")
    @Column(name = "first_name")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z\\s]+$",message = "Last name most consist of letter only.")
    @Size(min = 3,max = 60,message = "At least three characters must be written for the name")
    @Column(name = "last_name")
    private String lastName;

    @Email(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    @Column(name = "gmail",unique = true,nullable = false)
    private String gmail;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
            ,message = "Password should contain At least one (small word, capital letter, number , @ # ! % & *) and " +
            "it must be at least eight characters.")
    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "registration_date")
    protected LocalDate registrationDate;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    protected Role role;

    @Column(name = "locked")
    protected Boolean locked;
    @Column(name = "is_active")
    protected Boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) && Objects.equals(gmail, person.gmail) &&
                Objects.equals(password, person.password) && Objects.equals(registrationDate, person.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, gmail, password, registrationDate);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getRole().name()));
    }

    @Override
    public String getUsername() {
        return getGmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
