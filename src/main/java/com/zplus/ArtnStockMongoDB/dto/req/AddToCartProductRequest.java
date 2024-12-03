package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ArtProductMaster;
import lombok.Getter;
import lombok.Setter;
@Getter@Setter
public class AddToCartProductRequest {
//    private String cartId;
    private ArtProductMasterReq artProductMaster;
    private String productName;
    private String description;
    private Integer quantity;
    private String stockStatus;
    private String size;
    private String userId;
    private String shippingMethodId;
    private String style;
//    private String adminArtProductId;
}
