package com.zplus.ArtnStockMongoDB.model;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cartmaster")
public class CartMaster {

    @Id
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

    private Double productsValue;

    @DBRef
    @JsonIgnore
    private UserMaster userMaster;
    //    @DBRef
//    private CartProductMaster cartProductMaster;
    @Field("CartProductMaster")
    private List<CartProductMaster> cartProductMaster=new ArrayList<>();
    @Field("CartArtFrameMaster")
    private List<CartArtFrameMaster> cartArtFrameMaster=new ArrayList<>();
//
    @Field("CartAdminArtProductMaster")
    private List<CartAdminArtProductMaster> cartAdminArtProductMaster=new ArrayList<>();


    //  chamge combo Master

    public CartMaster(CartMaster c) {
        this.cartId = c.getCartId();
        this.cartDate =c.getCartDate();
        this.totalQty = c.getTotalQty();
        TotalAmount = c.getTotalAmount();
//        this.discount =c.getDiscount();
        this.tax = c.getTax();
        this.taxAmount =c.getTaxAmount();
        this.estimateAmount = c.getEstimateAmount();
        this.promoCode = c.getPromoCode();
        this.giftCode = c.getGiftCode();
        this.estimateShipping = c.getEstimateShipping();
        this.description = c.getDescription();
        this.status = c.getStatus();
        this.promoCodeAmount = c.getPromoCodeAmount();
        this.giftCodeAmount = c.getGiftCodeAmount();
        this.totalCount = c.getTotalCount();
        this.finalAmount = c.getFinalAmount();
        this.shippingMethod = c.getShippingMethod();
        this.userMaster = c.getUserMaster();
        this.cartProductMaster = c.getCartProductMaster();
        this.cartArtFrameMaster = c.getCartArtFrameMaster();
    }

    public CartMaster(String cartId, Date cartDate, Integer totalQty, Double totalAmount, Double tax, Double taxAmount, Double estimateAmount, String promoCode, String giftCode, Double estimateShipping, String description, String status, Double promoCodeAmount, Double giftCodeAmount, Integer totalCount, Double finalAmount, String codeType, String style, ShippingMethod shippingMethod, UserMaster userMaster) {
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
        this.shippingMethod = shippingMethod;
        this.userMaster = userMaster;
    }
}
