package com.zplus.ArtnStockMongoDB.dto.req;


import com.zplus.ArtnStockMongoDB.model.SizeAndPrice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyNowReqd {

    private String cartId;

    private String userId;

    private String cartProductId;

    private String cartArtFrameId;

    private Integer Qty;

    private String cartAdminArtProductId;

    //new  changes
    private String type;

    private String size;

    private SizeAndPrice sizeAndPrice;

}
