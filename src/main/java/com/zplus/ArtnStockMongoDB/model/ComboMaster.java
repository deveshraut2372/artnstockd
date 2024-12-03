package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@Document(collection = "combo_master")
public class ComboMaster {

    @Id
    private String comboId;
    private Integer count;
    private Double price;
    private String image;
    private String comboNo;
    private Double stock;
    private Double stockQuantity;
    private String title;
    private String description;
    private String status;

    //    @DBRef
    @Field("artProductMasterList")
        private List<ArtProductMaster> artProductMaster;

    @Field("adminArtProductMaster")
    private  List<AdminArtProductMaster> adminArtProductMaster;


    //changes
    private String size;

}
