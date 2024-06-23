package com.watermelon.dateapp.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false))
    private UserName userName;

    @OneToMany(mappedBy = "user")
    private List<UserTendency> userTendency = new ArrayList<>();

    public User(String userName) {
        this.userName = new UserName(userName);
    }

    public User() {

    }

    public String getUserName() {
        return this.userName.getValue();
    }

    public void updateUserName(String userName) {
        this.userName = new UserName(userName);
    }
}
