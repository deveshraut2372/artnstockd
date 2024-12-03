package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Data;

@Data
public class CartUpdateReq {

    private  String id;

    private String deleteId;

    private String type;
}
