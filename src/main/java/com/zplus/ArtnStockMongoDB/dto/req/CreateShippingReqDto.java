package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ShippingAddress;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateShippingReqDto {

    private String userId;
    private ShippingAddress shippingAddress;


}
