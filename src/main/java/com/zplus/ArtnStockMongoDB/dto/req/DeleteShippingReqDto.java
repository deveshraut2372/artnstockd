package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteShippingReqDto {

    private String userId;

    private String shippingAddressId;

}
