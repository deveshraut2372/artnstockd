package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionSortRequest {
    private String userId;
    private String category;
    private String order;
    private String searchKey;

}
