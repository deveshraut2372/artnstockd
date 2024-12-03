package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ShippingMethod;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UpdateCartShippingMethodReq {
    private String cartId;
    private ShippingMethod shippingMethod;
}
