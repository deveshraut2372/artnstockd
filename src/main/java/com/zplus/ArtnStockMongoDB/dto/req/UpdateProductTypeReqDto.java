package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UpdateProductTypeReqDto {

    private String productTypeId;
    private String productTypeName;
    private String image;
    private String status;

}
