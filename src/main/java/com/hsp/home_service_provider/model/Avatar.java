package com.hsp.home_service_provider.model;

import com.hsp.home_service_provider.model.enums.AvatarStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "avatar_tbl")
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_name")
    private String name;

    @Column(name = "image_type")
    private String type;

    @Lob
    @Column(name = "avatar_data")
    private byte[] avatarData;

    @Column(name = "avatar_status")
    private AvatarStatus avatarStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_id")
    private Specialist specialist;

}
