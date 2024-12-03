package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.CartMaster;
import com.zplus.ArtnStockMongoDB.model.SizeAndPrice;
import com.zplus.ArtnStockMongoDB.service.CartMasterService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  BuyNowReq {

    private String cartId;

    private String userId;

    private String cartProductId;

    private String cartArtFrameId;

    private Integer Qty;

    private String cartAdminArtProductId;

    //new  changes
//    private String type;
//
//    private String size;
//
//    private SizeAndPrice sizeAndPrice;

}
