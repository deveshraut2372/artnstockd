package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "product_master")
public class ProductMaster {

    @Id
    private String productId;
    private String productName;
    private String description;
    private List<String> style;
    private List<ProductDetails> productDetails;
    private List<SizeAndPrice> sizeAndPrices;
    private String productNo;
    private Double stock;
    private String stockStatus;
    private String status;
    private ImageMaster imageMaster;
    private Integer index;

    @DBRef
    private ProductSubCategoryMaster productSubCategoryMaster;

//    private List<CategoryMaster> categoryMasterList;

//    private Double price;
//    private String image;
//    private Integer popularStatus;
//    private List<String> colour;

    private String type="product";
}
