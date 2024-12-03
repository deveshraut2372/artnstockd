package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.OrientationMaster;
import com.zplus.ArtnStockMongoDB.model.ShippingAddress;
import com.zplus.ArtnStockMongoDB.model.ShippingMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrderResDto {
    private String artFrameName;
    private Double amount;
    private String imgUrl;
    private String userId;
    private String status;
    private String artDescription;
    private Date orderDate;
    private String contributorFirstName;
    private String contributorLastName;
    private String contributorDisplayName;
    private String cartArtFrameUniqueNo;
    private ShippingAddress shippingAddress;
    private String userFirstName;
    private String userLastName;
    private String userDisplayName;
    private String emailAddress;
    private OrientationMaster orientationMaster;
    private String orderId;
    private ShippingMethod shippingMethod;
    private Integer totalQty;
    private Double totalAmount;



    public OrderResDto() {

    }
}
