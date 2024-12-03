package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Data;

@Data
public class SortRequest {
    private String userId;
    private String type;
    private String order;
    private String searchKey;

}
