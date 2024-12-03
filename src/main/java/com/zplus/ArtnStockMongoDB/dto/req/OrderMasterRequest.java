package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.PaymentInformation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter@Setter
public class OrderMasterRequest {
    private String userId;
    private List<String> cartArtFrameId=new ArrayList<>();
    private List<String> cartProductId=new ArrayList<>();
    //change
    private List<String> cartAdminArtProductId=new ArrayList<>();
    private PaymentInformation paymentInformation;
    private String orderPaymentStatus;
    private String shippingMethodId;

}



