package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.ShippingMethodRequest;
import com.zplus.ArtnStockMongoDB.model.ShippingMethod;

import java.util.List;

public interface ShippingMethodService {
    Boolean createShippingMethod(ShippingMethodRequest shippingMethodRequest);

    Boolean updateShippingMethod(ShippingMethodRequest shippingMethodRequest);

    List getAllShippingMethod();

    ShippingMethod editShippingMethod(String shippingMethodId);
}
