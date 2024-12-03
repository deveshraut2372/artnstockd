package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ArtProductMaster;
import com.zplus.ArtnStockMongoDB.model.SizeAndPrice;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CartMasterReqDto {
    private String cartId;
    private String cartDate;
    private Integer totalQty;
    private Double TotalAmount;
    private Double discount;
    private Double FinalAmount;
    private Integer qty;
//    private Double tax;
//    private String promoCode;
//    private String giftCode;
//    private Double estimateShipping;
    private String description;

    private String userId;
    private String artProductId;
    private String frameArtId;


}
