package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class OrderStatusUpdateArtProductRequest {
    private String orderId;
    private String cartProductId;
    private String orderStatus;
}
