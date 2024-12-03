package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.PaymentInformation;
import lombok.Data;

import java.util.List;

@Data
public class SingleOrderMasterRequest {

    private String userId;
    private String productId;
    private String type;
//    private String cartArtFrameId;
//    private String cartProductId;
//    //change
//    private String cartAdminArtProductId;
    private String cartId;
    private PaymentInformation paymentInformation;
    private String orderPaymentStatus;
    private String shippingMethodId;

}
