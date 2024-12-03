package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;
import java.util.List;

@Data
public class TempArtProductReq {

    private String tempArtProductId;

    private String tempArtProductName;

    private Date date;

    private String status;

    private String image;

//    private List<SizeAndPrice> sizeAndPrices;

    private Integer qty;

//    private String artProductUniqueNo;

    private double CanvasX;
    private double CanvasY;
    private int canvasSize;

    //    Relation

    private String userId;

//    @DBRef
//    private ArtMaster artMaster;

    private String artId;


    private String productId;


//    private List<ColorImagesModel> images;

//    private  String  approveStatus;

    private String artDetailsId;

/////////////////////////////////////////////////////////////////////////

    private String productMainCategoryId;

    private String productSubCategoryId;

    private String productSubSubCategoryId;

    private String productStyleId;

    private List<String> productColorId;

//    private List<String> productSubSubCategoryIds;
//
//    private List<String> productStyleIds;
//
//    private List<ArtProductColorReq> artProductColorReqList;

}
