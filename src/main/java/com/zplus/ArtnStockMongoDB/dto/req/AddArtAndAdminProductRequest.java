package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class AddArtAndAdminProductRequest {
    private String fileManagerId;
    private List<AddArtAndProductReq> addArtAndProductReqList;
}
