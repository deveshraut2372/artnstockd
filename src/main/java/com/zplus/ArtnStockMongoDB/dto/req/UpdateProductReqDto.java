package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ProductDetails;
import com.zplus.ArtnStockMongoDB.model.SizeAndPrice;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class UpdateProductReqDto {

    private String productId;
    private String productName;
    private String description;
    private String style;
    private List<ProductDetails> productDetails;
    private List<SizeAndPrice> sizeAndPrices;
    private String productNo;
    private Double stock;
    private String stockStatus;
    private String status;
    private String productSubCategoryId;
    private String hexCode;
    private Integer index;

}
