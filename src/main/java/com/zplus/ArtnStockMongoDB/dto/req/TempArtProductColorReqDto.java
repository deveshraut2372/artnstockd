package com.zplus.ArtnStockMongoDB.dto.req;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TempArtProductColorReqDto {

    private String productColorId;

    private String colorCode;

    private String colorType;

    private Integer quantity;

    private Double colorPrice;

    private String colorName;

    private String productImage;

    private String productStyleId;

    private String productId;

}
