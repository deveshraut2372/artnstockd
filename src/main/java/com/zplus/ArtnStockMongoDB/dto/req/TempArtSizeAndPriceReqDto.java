package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TempArtSizeAndPriceReqDto {

    private String SizeAndPriceId;
    private String size;
    private Double basePrice;
    private Double sellPrice;
    private String sizeName;

    //change new
    private Double markup;

    private String productColorId;

    private String productId;
}
