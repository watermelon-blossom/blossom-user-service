package com.watermelon.dateapp.domain.user;

import com.watermelon.dateapp.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
public class UserLocation extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @OneToMany(mappedBy = "location")
    private List<User> user = new ArrayList<>();

    @Column(name = "location", unique = true)
    private String locationName;
}
