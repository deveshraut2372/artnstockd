package com.zplus.ArtnStockMongoDB.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArrayListToStringConverter implements Converter<List, String> {

    @Override
    public String convert(List source) {
        if (source != null && !source.isEmpty()) {
            return source.get(0).toString(); // Assuming you want to convert the first element of the list to a String
        }
        return null;
    }
}
