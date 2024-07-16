package com.watermelon.dateapp.domain.user;

import com.watermelon.dateapp.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
public class UserLocation extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @OneToOne(mappedBy = "location")
    private User user;

    @Column(name = "location")
    private String locationName;

    public static UserLocation of(User user, String location) {
        UserLocation userLocation = new UserLocation();
        userLocation.user = user;
        userLocation.locationName = location;
        return userLocation;
    }

    public void updateLocation(String location) {
        this.locationName = location;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
