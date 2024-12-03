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
@Document(collection = "collection_master")
@ToString
public class CollectionMaster {

    @Id
    private String collectionId;

    private String title;

    private Date updatedDate;

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

    private List list;

    private String coverImage;

}
