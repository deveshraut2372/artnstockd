package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.*;

import java.util.List;

public class ArtProductRequest {

    private String productMainCategoryId;

    private String productSubCategoryId;

    private String productSubSubCategoryId;

    //
    private ProductSubSubCategory productSubSubCategory;

    private ProductStyle productStyle;

    private ProductColor productColor;

    private List<ProductColor> productColorList;

    private List<SizeAndPrice> sizeAndPriceList;
    //


    private String productSubSubCategoryName;

    private String productSubCategoryName;

    private String productMainCategoryName;

    //
    private String productStyleId;

    private String productColorId;

    private String SizeAndPriceId;
}
