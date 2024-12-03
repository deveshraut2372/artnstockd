package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMarkupContributerReqDto {
    private String userId;
    private Double markupPer;
}
