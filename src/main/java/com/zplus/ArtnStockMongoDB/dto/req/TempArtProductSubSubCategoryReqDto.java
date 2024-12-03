package com.zplus.ArtnStockMongoDB.dto.req;


import com.zplus.ArtnStockMongoDB.model.ProductSubSubCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TempArtProductSubSubCategoryReqDto
{

    private String productSubSubCategoryId;

    private String productSubSubCategoryName;

    private String status;

    private LocalDate date;

//    private List<PrintLocations> printLocationsList;

    private String productSubCategoryId;

    private String productMainCategoryId;

    private String productId;
}
