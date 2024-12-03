package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class OrderStatusUpdateRequest {
    private String orderId;
    private String cartArtFrameId;
    private String orderStatus;
}
