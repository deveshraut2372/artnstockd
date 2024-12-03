package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RecentlySearchRequest {

    private String recentlySearchId;
    private String userId;
    private String text;

}
