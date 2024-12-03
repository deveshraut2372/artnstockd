package com.zplus.ArtnStockMongoDB.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zplus.ArtnStockMongoDB.dto.req.CartFrameReq;
import com.zplus.ArtnStockMongoDB.dto.req.CartMatReq;
import com.zplus.ArtnStockMongoDB.model.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

@Getter@Setter
public class CartProductAndArtFrameRes {
    private String cartProductId;
    private String cartId;
    private String productName;
    private Double discount;
    private String size;
    private String cartProductNo;
    private Integer quantity;
    private Double rate;
    private Double amount;
    private String stockStatus;
    private String description;
    private String status;
    private String cartProductUniqueNo;
    private Date updatedDate;
    private String userId;
    private String artProductId;

    private String cartArtFrameId;
    private String artFrameName;//*art frame name and frame name combination*//*
    private Date date;
    private String cartArtFrameUniqueNo;
    private String newOrderUniqueNo;
    private String expectedDeliveryDate;
    private Double totalAmount;
    private CartFrameReq frameMaster;
    private CartMatReq matMasterTop;
    private CartMatReq matMasterBottom;
    private String imgUrl;
    private PrintingMaterialMaster printingMaterialMaster;
    //    private PrintingSizeMaster printingSizeMaster;
    private OrientationMaster orientationMaster;
    private String artId;

}
