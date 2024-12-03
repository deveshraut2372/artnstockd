package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UpdateMainCategoryReqDto {

    private String mainCategoryId;

    private String mainCategoryName;

    private String status;
    private String logo;

}
