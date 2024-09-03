package com.watermelon.dateapp.global.converter;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ", ";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return String.join(SPLIT_CHAR, stringList);
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        return StringUtils.hasText(string) ? Arrays.asList(string.split(SPLIT_CHAR)) : null ;
    }
}