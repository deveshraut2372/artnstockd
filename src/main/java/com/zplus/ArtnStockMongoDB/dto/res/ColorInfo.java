package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.*;

@Getter@Setter
public class ColorInfo {
    private String color;
    private Double percentage;

    public ColorInfo(String color, Double percentage) {
        this.color = color;
        this.percentage = percentage;
    }

    public ColorInfo() {

    }
}

