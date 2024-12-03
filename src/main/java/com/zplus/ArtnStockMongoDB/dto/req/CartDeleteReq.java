package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Data;

@Data
public class CartDeleteReq {

    private  String id;

    private String deleteId;

    private String type;

}
