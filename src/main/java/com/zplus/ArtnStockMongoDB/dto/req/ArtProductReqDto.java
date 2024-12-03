package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class ArtProductReqDto {
    private String artProductName;
    private String userId;
//    private String image;
    private String artId;
    private String productId;
    private double CanvasX;
    private double CanvasY;
    private int canvasSize;
    private List<ColorImagesModel> images;

    private String artDetailsId;


    // new Added
    private String productMainCategoryId;

    private String productSubCategoryId;

    private String productSubSubCategoryId;

//    private ArtProductSubSubCategoryReqDto artProductSubSubCategoryReqDto;

    //
//    private ProductSubSubCategory productSubSubCategory;x
//    private ProductStyle productStyle;
//    private ProductColor productColor;
//    private List<ProductColor> productColorList;
//    private List<SizeAndPrice> sizeAndPriceList;
    //

    //
    private ProductMainCategoryMaster productMainCategoryMaster;

    private ProductSubCategoryMaster productSubCategoryMaster;

    private List<ProductSubSubCategory>  productSubSubCategoryList;

    private List<ProductStyle> ProductStyleList;

    private List<ProductColor> ProductColorList;

    private List<SizeAndPrice> SizeAndPriceList;

}
