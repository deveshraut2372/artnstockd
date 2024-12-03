package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class WishListReqDto {

    private String wishListId;

    private Date wishListDate;
    private String wishListStatus;
    private String id;
    private String artId;
//    private String productId;
    private String artProductId;
    private String adminArtProductId;
}
