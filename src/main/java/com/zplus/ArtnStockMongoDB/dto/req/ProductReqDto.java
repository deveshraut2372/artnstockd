package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ProductDetails;
import com.zplus.ArtnStockMongoDB.model.ProductSubSubCategory;
import com.zplus.ArtnStockMongoDB.model.SizeAndPrice;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter@Setter
@ToString
public class ProductReqDto {

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
    private String productSubCategoryId;
    private String imageId;

    private Integer index;

//    private List<String> categoryIds;

    private ProductSubSubCategory productSubSubCategory;




}
