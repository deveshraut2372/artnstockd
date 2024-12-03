package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ImageOrientation;

import lombok.Data;

@Data
public class UpdateImageReq {

    private String imageId;

    private ImageOrientation imageOrientation;

    private String userId;
}
