package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class CartArtFrameMasterRequest {

    private String artId;
    private String cartArtFrameId;
    private Integer quantity;
    private String description;
    private String stockStatus;
    private String userId;
    private CartFrameReq frameMaster;
    private CartMatReq matMasterTop;
    private CartMatReq matMasterBottom;
    private PrintingMaterialMaster printingMaterialMaster;
  //  private PrintingSizeMaster printingSizeMaster;
    private OrientationMaster orientationMaster;
    private ShippingMethod shippingMethod;
    private String imgUrl;
    private String shippingMethodId;
    private Double totalAmount;




}
