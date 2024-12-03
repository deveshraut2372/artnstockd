package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCollectionCoverImageReq {

    private String collectionId;

    private String coverImage;
}
