package com.watermelon.dateapp.controller;

import com.watermelon.dateapp.domain.UserName;
import lombok.Getter;

@Getter
public class UserDto {
    private Long id;
    private UserName userName;

    public UserDto(Long id, String userName) {
        this.id = id;
        this.userName = new UserName(userName);
    }
    public String getUserName() {
        return userName.getValue();
    }
}
