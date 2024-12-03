package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Data;

@Data
public class FileManagerListSortRequest {
    private String order;
    private String fileManagerId;
    private String searchKey;

}
