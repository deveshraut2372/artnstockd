package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishListAddReq {

    private  String id;

    private String deleteId;

    private String type;
}
