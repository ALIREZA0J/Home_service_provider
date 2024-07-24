package com.hsp.home_service_provider.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "set")
@Entity
@Table(name = "ConfirmationToken_tbl")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    private Specialist specialist;

    @ManyToOne
    private Customer customer;

    @PrePersist
    private void defaultValues(){
        if (this.token == null) this.token = UUID.randomUUID().toString();
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
        if (this.expiresAt == null) this.expiresAt = LocalDateTime.now().plusMinutes(10);
    }
}
