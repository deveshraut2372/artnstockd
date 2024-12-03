package com.zplus.ArtnStockMongoDB.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorImagesModel {

    private String color;
    private String image;
    private String colorCode;
    private String productColorId;


}
