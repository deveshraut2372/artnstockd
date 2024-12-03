package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "order_master")
@ToString
public class OrderMaster {

    @Id
    private String orderId;
    private String orderUniqueNo;
    private String orderStatus;
    private String orderPaymentStatus;
    private Integer totalQty;
    private Date orderDate;
    private Double totalAmount; /*total of cart amount*/
    private Double tax=12.0;/*default value set 12% */
    private Double coupon;
    private String promoCode;
    private String giftCode;
    private Double estimatedTotal;/*total of order amount include tax ,estimate shipping ,cart amount*/
    private Double estimatedShipping=0.0;
    private Double promoCodeAmount;
    private Double giftCodeAmount;
    private Double couponDiscount;
    private Double taxAmount;
    private Double finalAmount;


    private Integer totalCount;
//    private ShippingMethod shippingMethod;
    private PaymentInformation paymentInformation;
    private ShippingMethod shippingMethod;

    @DBRef
    private UserMaster userMaster;
    @Field("CartArtFrameMaster")
    private List<CartArtFrameMaster> cartArtFrameMaster;
    @Field("CartProductMaster")
    private List<CartProductMaster> cartProductMaster;

    @Field("CartAdminArtProductMaster")
    private List<CartAdminArtProductMaster> cartAdminArtProductMaster;


    private SizeAndPrice sizeAndPrice;


}
