package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class ProductResDto {
    private String productId;

    private String productName;
    private String description;
    private String image;
    private String style;
    private Double price;
    private List<String> size;
    private List<String> colour;
    private String productNo;
    private Double stock;
    private String stockStatus;
    private String status;
    private String productTypeId;

}
