package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddCollArtAndAdminProductRequest {
    private String collectionId;
    private List<AddArtAndProductReq> addArtAndProductReqList;
}
