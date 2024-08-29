package com.watermelon.dateapp.domain.user;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

import static lombok.AccessLevel.*;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
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
