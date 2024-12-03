package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ShippingMethodRequest {

    private String shippingMethodId;
    private String shippingMethodName;
    private Double shippingMethodPrice;
    private String dayToReceive;
}
