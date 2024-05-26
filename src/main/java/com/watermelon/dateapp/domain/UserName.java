package com.watermelon.dateapp.domain;

import jakarta.persistence.Embeddable;
import org.antlr.v4.runtime.misc.NotNull;

@Embeddable
public class UserName {
    private static String pattern = "/^[a-z ,.'-]+$/i";
    private String value;
}
