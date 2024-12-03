package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class FilterReqDto {
    private String userId;
    private String Type;
    private String text;
}
