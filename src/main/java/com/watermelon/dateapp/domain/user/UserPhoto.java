package com.watermelon.dateapp.domain.user;

import com.watermelon.dateapp.global.common.BaseEntity;
import jakarta.persistence.*;

import static jakarta.persistence.FetchType.*;

@Entity
public class UserPhoto extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private UploadPhoto PhotoFile;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
