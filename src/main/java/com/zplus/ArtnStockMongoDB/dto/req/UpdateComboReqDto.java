package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class UpdateComboReqDto {

    private String comboId;
    private Integer count;
    private Double price;
    private String image;
    private String comboNo;
    private Double stock;
    private Double stockQuantity;
    private String title;
    private String description;
    private String status;
    private List<String> artProductId;

    private List<String> adminArtProductId;

}
