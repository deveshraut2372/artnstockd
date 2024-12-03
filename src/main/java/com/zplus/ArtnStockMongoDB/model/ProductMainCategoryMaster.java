package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "product_main_category_master")
public class ProductMainCategoryMaster {

    @Id
    private String productMainCategoryId;
    private String productMainCategoryName;
    private String image;
    private String status;
}
