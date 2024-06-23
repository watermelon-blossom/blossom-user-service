package com.watermelon.dateapp.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@Getter
public class UserName {
    private static String pattern = "^[a-zA-Z가-힣 ,.'-]+$";
    private String value;

    public UserName(String value) {
        Pattern compile = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        if (!compile.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid user name");
        }
        this.value = value;
    }
}
