package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Orientation {

    private List<Horizontal> horizontal;
    private List<Vertical> vertical;
    private List<Square> square;
    private List<Custom> custom;
}
