package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AddToCartArtFrameRequest {
    private String cartArtFrameId;
    private String color;
    private String frameType;
    private String frameSize;
    private String imageSize;
    private String topMatWidth;
    private String bottomMatWidth;
    private String frameWidth;
    private String finishSize;
    private String description;
    private Integer qty;
    private String artId;
    private String frameId;
    private String userId;
    private String cartId;
}
