package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionOrderReq {
    private String collectionId;
    private String order;
    private String searchKey;

}
