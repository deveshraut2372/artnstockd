package com.zplus.ArtnStockMongoDB.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zplus.ArtnStockMongoDB.dto.req.CartFrameReq;
import com.zplus.ArtnStockMongoDB.dto.req.CartMatReq;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Getter
@Setter
@Data
@Document(collection = "order_art_frame_master")
@ToString
public class OrderArtFrameMaster {

    @Id
    private String orderArtFrameId;
    private String status;
    private String artFrameName;//*art frame name and frame name combination*//*
    private Date date;
    private Double rate;
    private Double amount;
    private Integer quantity;
    private String description;
    private String stockStatus;
    private String cartArtFrameUniqueNo;
    private String newOrderUniqueNo;
    private String expectedDeliveryDate;
    private Date updatedDate;
    private String type;
    private Double totalAmount;
    @DBRef
    private UserMaster userMaster;
    private CartFrameReq frameMaster;
    private CartMatReq matMasterTop;
    private CartMatReq matMasterBottom;
    private String imgUrl;
    private PrintingMaterialMaster printingMaterialMaster;
    //    private PrintingSizeMaster printingSizeMaster;
    private OrientationMaster orientationMaster;
    @DBRef
    @JsonIgnore
    private CartMaster cartMaster;
    @DBRef
    private ArtMaster artMaster;

    @Override
    public String toString() {
        return "CartArtFrameMaster{" +
                "cartArtFrameId='" + orderArtFrameId + '\'' +
                ", status='" + status + '\'' +
                ", artFrameName='" + artFrameName + '\'' +
                ", date=" + date +
                ", rate=" + rate +
                ", amount=" + amount +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", stockStatus='" + stockStatus + '\'' +
                ", cartArtFrameUniqueNo='" + cartArtFrameUniqueNo + '\'' +
                ", newOrderUniqueNo='" + newOrderUniqueNo + '\'' +
                ", expectedDeliveryDate='" + expectedDeliveryDate + '\'' +
                ", updatedDate=" + updatedDate +
                ", type='" + type + '\'' +
                ", totalAmount=" + totalAmount +
                ", userMaster=" + userMaster +
                ", frameMaster=" + frameMaster +
                ", matMasterTop=" + matMasterTop +
                ", matMasterBottom=" + matMasterBottom +
                ", imgUrl='" + imgUrl + '\'' +
                ", printingMaterialMaster=" + printingMaterialMaster +
                ", orientationMaster=" + orientationMaster +
                ", artMaster=" + artMaster +
                '}';
    }
}
