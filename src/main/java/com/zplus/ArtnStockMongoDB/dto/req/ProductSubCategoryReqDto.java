package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ProductSubSubCategory;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProductSubCategoryReqDto {
    private String productSubCategoryId;
    private String productSubCategoryName;
    private String image;
    private String status;
    private String logoImage;
    private String type;
    private String productMainCategoryId;
    private Integer index;

    //new One Added for add some data about product style ,color, sizeandPrice
    private ProductSubSubCategory productSubSubCategory;

//    private String productSubSubCategoryId;

}
