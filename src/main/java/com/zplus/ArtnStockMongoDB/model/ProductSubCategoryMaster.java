package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "product_sub_category_master")
public class ProductSubCategoryMaster {

    @Id
    private String productSubCategoryId;
    private String productSubCategoryName;
    private String image;
    private String status;
    private String logoImage;
    private String type;

    @DBRef
    private ProductMainCategoryMaster productMainCategoryMaster;
    private Integer index;

//    @DBRef
        private ProductSubSubCategory productSubSubCategory;


}
