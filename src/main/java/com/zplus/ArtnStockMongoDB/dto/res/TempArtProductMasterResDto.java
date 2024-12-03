package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TempArtProductMasterResDto {


    private ProductMainCategoryMaster productMainCategoryMaster;

    private ProductSubCategoryMaster productSubCategoryMaster;

    private Set<ProductSubSubCategory> productSubSubCategorySet;

    private Set<ProductStyle> productStyleSet;

//    private Set<ProductColor> productColorSet;

    private Set<ProductColor> standardColorSet;

    private Set<ProductColor> neonColorSet;


    private Set<SizeAndPrice> sizeAndPriceSet;

    private  Double basePrice;

    private  Double markup;


}
