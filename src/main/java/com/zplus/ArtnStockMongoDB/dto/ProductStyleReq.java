package com.zplus.ArtnStockMongoDB.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStyleReq
{
    private String productStyleId;

    private String styleName;

//    private String styleLogo;

    private String styleImage;

    private String productSubSubCategoryId;

    private String productId;

    private Integer index;


}
