package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UpdateCommonMasterReqDto {
    private String commonId;
    private String status;
    private Integer qty;
    private String productId;
    private String artId;
}
