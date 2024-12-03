package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyNowRes {

    private String cartId;
    private Date cartDate;
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

//    private CartProductMaster cartProductMaster;

//    private CartAdminArtProductMaster cartAdminArtProductMaster;

//    private List<CartArtFrameMaster> cartArtFrameMaster;

    private List list;


    public BuyNowRes(String cartId, Date cartDate, Integer totalQty, Double totalAmount, Double tax, Double taxAmount, Double estimateAmount, String promoCode, String giftCode, Double estimateShipping, String description, String status, Double promoCodeAmount, Double giftCodeAmount, Integer totalCount, Double finalAmount, String codeType, String style) {
        this.cartId = cartId;
        this.cartDate = cartDate;
        this.totalQty = totalQty;
        TotalAmount = totalAmount;
        this.tax = tax;
        this.taxAmount = taxAmount;
        this.estimateAmount = estimateAmount;
        this.promoCode = promoCode;
        this.giftCode = giftCode;
        this.estimateShipping = estimateShipping;
        this.description = description;
        this.status = status;
        this.promoCodeAmount = promoCodeAmount;
        this.giftCodeAmount = giftCodeAmount;
        this.totalCount = totalCount;
        this.finalAmount = finalAmount;
        this.codeType = codeType;
        this.style = style;
    }
}
