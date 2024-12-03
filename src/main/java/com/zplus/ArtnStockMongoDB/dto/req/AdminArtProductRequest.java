package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminArtProductRequest {

    private String adminArtProductId;
    private String adminArtProductName;
    private Date date;
    private String status;

//    private String image;

    private SizeAndPrice sizeAndPrices;
//    private List<SizeAndPrice> sizeAndPrices;
    private Integer qty;
    private String artProductUniqueNo;
    private double CanvasX;
    private double CanvasY;
    private int canvasSize;
    private String userId;
//    private UserMaster userMaster;
    private String artId;
//    private ArtMaster artMaster;
    private String productId;
//    private ProductMaster productMaster;

//    private ColorImagesModel images;
    private List<ColorImagesModel> images;
    private String artProductId;

// /////////////// new content added
//    private ProductSubSubCategory productSubSubCategory;
//    private String productSubCategoryId;
//    private String productMainCategoryId;

    ////////////////////////


//    private  ArtProductMaster artProductMaster;

//    private String cartId;
//    private CartMaster cartMaster;

//    private String artProductId;
//    private String artProductName;
//    private String artId;
//    private Date date;
//    private String status;
//    private String productId;
//    private double CanvasX;
//    private double CanvasY;
//    private int canvasSize;
//    private Integer qty;
//    private String artProductUniqueNo;
//    private SizeAndPrice sizeAndPrices;
//    private ColorImagesModel images;
//    private String adminArtProductId;



    ///
//    private String adminArtProductId;
//    private String artProductName;
//    private Date date;
//    private String status;
//    private String image;
//    private List<SizeAndPrice> sizeAndPrices;
//    private Integer qty;
//    private String artProductUniqueNo;
//    private double CanvasX;
//    private double CanvasY;
//    private int canvasSize;
//    private String userId;
//    private String artId;
//    private String productId;
//    private List<ColorImagesModel> images;
//    private String artProductId;


}
