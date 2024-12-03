package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.management.relation.Relation;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString@NoArgsConstructor
@Document(collection = "art_product_master")
public class ArtProductMaster {

    @Id
    private String artProductId;

    private String artProductName;

    private Date date;

    private String status;

    private String image;

//    private List<SizeAndPrice> sizeAndPrices;

    private Integer qty;

    private String artProductUniqueNo;

    private double CanvasX;
    private double CanvasY;
    private int canvasSize;

    //    Relation
    @DBRef
    private UserMaster userMaster;

    @DBRef
    private ArtMaster artMaster;

    @DBRef
    private ProductMaster productMaster;

//    @DBRef
//    private ProductSubCategoryMaster productSubCategoryMaster;

//    private List<ColorImagesModel> images;

//    @DBRef
//    private AddDetailsMaster addDetailsMaster;

//    private  String  approveStatus;

    private ColorImagesModel colorImage;


    ///
    private Date submittedDate;

    private Date reviewData;

    private Date approveDate;

    private Date updatedDate;

    private String collectionId;

////////////////////////////////////////
    private ProductMainCategoryMaster productMainCategoryMaster;

    private ProductSubCategoryMaster productSubCategoryMaster;

    private ProductSubSubCategory productSubSubCategory;

    private ProductStyle productStyle;

    private ProductColor productColor;

    private List<SizeAndPrice> sizeAndPrices;

}
