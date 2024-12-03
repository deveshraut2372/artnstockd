package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.CartArtFrameMaster;
import com.zplus.ArtnStockMongoDB.model.CartProductMaster;
import com.zplus.ArtnStockMongoDB.model.ShippingMethod;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter@Setter@NoArgsConstructor@Data
public class CartResDTo {
    private String cartId;
    private Date cartDate;
    private Integer totalQty;
    private Double TotalAmount;
    private Double discount;
    private Double tax=12.0;    // Temporary default value set 12%
    private Double taxAmount;
    private Double estimateAmount;
    private String promoCode;
    private String giftCode;
    private Double estimateShipping =5.0;// Temporary default value set to 5.0;
    private String description;
    private String status;
    private Double promoCodeAmount=0.0;
    private Double giftCodeAmount=0.0;
    private Integer totalCount;

    private ShippingMethod shippingMethod;
    private String userId;
    private List<CartProductMaster> cartProductMaster;
    private List<CartArtFrameMaster> cartArtFrameMaster;
}
