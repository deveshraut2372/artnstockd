package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.ShippingMethod;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter@Setter
public class CartMasterResponseDto {

    private String cartId;
    private Date cartDate;
    private Integer totalQty;
    private Double TotalAmount;
    private Double tax = 12.0;
    private Double taxAmount;
    private Double estimateAmount;
    private String promoCode;
    private String giftCode;
    private Double estimateShipping = 0.0;
    private String description;
    private String status;
    private Double promoCodeAmount = 0.0;
    private Double giftCodeAmount = 0.0;
    private Integer totalCount;
    private Double finalAmount;
    private String codeType;
    private String style;
    private ShippingMethod shippingMethod;
    private String userId;
    List<CartProductAndArtFrameRes> list=new ArrayList<>();

}
