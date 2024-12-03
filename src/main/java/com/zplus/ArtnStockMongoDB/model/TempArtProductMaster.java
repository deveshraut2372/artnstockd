package com.zplus.ArtnStockMongoDB.model;

import com.zplus.ArtnStockMongoDB.dto.req.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "TempArtProductMaster")
@Getter
@Setter
public class TempArtProductMaster {

    @Id
    private String tempArtProductId;

    private String tempArtProductName;

    private Date date;

    private String status;

    private String image;

    private List<SizeAndPrice> sizeAndPrices;

    private Integer qty;

    private String artProductUniqueNo;

    private double CanvasX;
    private double CanvasY;
    private int canvasSize;

    //    Relation
    @DBRef
    private UserMaster userMaster;

//    @DBRef
    private ArtMaster artMaster;

    @DBRef
    private ProductMaster productMaster;

    private List<ColorImagesModel> images;

//    private  String  approveStatus;

    @DBRef
    private AddDetailsMaster addDetailsMaster;

    /////////////////////////////////

    private ProductMainCategoryMaster productMainCategoryMaster;

    private ProductSubCategoryMaster productSubCategoryMaster;

    private ProductSubSubCategory productSubSubCategory;

//    private List<ProductSubSubCategory>  productSubSubCategoryList;

    private Set<ProductStyle> ProductStyleList;

    private Set<ProductColor> ProductColorList;

    private Set<SizeAndPrice> SizeAndPriceList;



}
