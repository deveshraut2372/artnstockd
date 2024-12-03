package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.dto.res.PrintLocations;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProductSubSubCategoryReq {

    private String productSubSubCategoryId;

    private String productSubSubCategoryName;

    private String status;

    private LocalDate date;

//    private List<ProductStyle> productStyleList;

//    private List<PrintLocations> printLocationsList;

    private String productSubCategoryId;

    private String productId;

    private String productMainCategoryId;

    private Integer index;

}
