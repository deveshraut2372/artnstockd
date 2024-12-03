package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "file_manager_master")
public class FileManagerMaster {

    @Id
    private String fileManagerId;

    private String title;

    private Date updatedDate=new Date();

    private Integer count;

    private String status;

    private String userType;

    private String category;

    @DBRef
    private UserMaster userMaster;

    @Field("ArtMaster")
    private List<ArtMaster> artMaster;

    @Field("ArtProductMaster")
    private List<ArtProductMaster> artProductMaster;

    @Field("AdminArtProductMaster")
    private List<AdminArtProductMaster> adminArtProductMaster;


    private String coverImage;


//    @DBRef
//    private MainCategoryMaster mainCategoryMaster;

}
