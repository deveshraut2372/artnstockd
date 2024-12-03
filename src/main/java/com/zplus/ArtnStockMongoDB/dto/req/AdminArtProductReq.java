package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminArtProductReq {

//    private ArtProductMasterReq artProductMaster;
//    private AdminArtProductRequest adminArtProductRequest;

    private CartAdminArtProductRequest adminArtProductRequest;

    private String adminArtProductName;

    private String description;
    private Integer quantity;
    private String stockStatus;
    private String size;
    private String userId;
    private String shippingMethodId;
    private String style;
    private String adminArtProductId;

    //changes
//    private String cartAdminArtProductId;
    //

      // new one for calculation
//     private String productSubSubCategoryId;
//    private String productSubSubCategoryName;
//     private String productStyleId;
//    private String styleName;
//     private String productColorId;
//    private String colorName;
//     private String SizeAndPriceId;
//    private String sizeName;



}
