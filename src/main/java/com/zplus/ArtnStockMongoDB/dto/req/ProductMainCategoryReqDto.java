package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProductMainCategoryReqDto {
    private String productMainCategoryId;
    private String productMainCategoryName;
    private String image;
    private String status;
}
