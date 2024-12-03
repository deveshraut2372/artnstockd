package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CommonMasterReqDto {

    private String status;
    private Integer qty;
    private Double commonPrice;
    private String productId;
    private String artId;
}
