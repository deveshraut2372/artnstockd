package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.SizeAndPrice;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductColorReqDto {

    private String productColorId;

    private String colorCode;

    private String colorType;

    private Integer quantity;

    private Double colorPrice;

    private String colorName;

    private String productImage;

    private String productStyleId;

    private String productId;

    private List<SizeAndPrice> sizeAndPriceList;


}
