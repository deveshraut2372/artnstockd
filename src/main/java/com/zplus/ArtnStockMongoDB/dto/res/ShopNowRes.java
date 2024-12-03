package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopNowRes {

    private Date Date;
    private Integer totalQty;
    private Double TotalAmount;
    //    private Double discount;
    private Double tax = 12.0;    // Temporary default value set 12%
    private Double taxAmount;
    private Double estimateAmount;
    private String promoCode;
    private String giftCode;
    private Double estimateShipping = 0.0;// Temporary default value set to 5.0;
    private String description;
    private String status;
    private Double promoCodeAmount = 0.0;
    private Double giftCodeAmount = 0.0;
    private Integer totalCount;
    private Double finalAmount;
    private String codeType;
    private String style;
    private ShippingMethod shippingMethod;

    private UserMaster userMaster;

    private CartProductMaster cartProductMaster;

    private CartArtFrameMaster cartArtFrameMaster;
}
