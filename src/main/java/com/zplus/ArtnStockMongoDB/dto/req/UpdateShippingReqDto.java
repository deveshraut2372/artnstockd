package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ShippingAddress;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class UpdateShippingReqDto {

    private String userId;
    private ShippingAddress shippingAddress;
    List<ShippingAddress> shippingAddressList;



}
