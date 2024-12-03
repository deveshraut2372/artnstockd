package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.ShippingAddress;
import com.zplus.ArtnStockMongoDB.model.ShippingMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class OrderCartProductResDto {
    private String artProductName;
    private Double amount;
    private String imgUrl;
    private String userId;
    private String status;
    private String artDescription;
    private Date orderDate;
    private String cartProductNo;
    private String contributorFirstName;
    private String contributorLastName;
    private String contributorDisplayName;
    private ShippingAddress shippingAddress;
    private String userFirstName;
    private String userLastName;
    private String userDisplayName;
    private String emailAddress;
    private String orderId;
    private ShippingMethod shippingMethod;
    private Integer totalQty;
    private Double totalAmount;
}
