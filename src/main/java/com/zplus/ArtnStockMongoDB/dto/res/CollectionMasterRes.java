package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.AdminArtProductMaster;
import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.ArtProductMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
public class CollectionMasterRes {

    private String collectionId;

    private String title;

    private Date updatedDate;

    private Integer count;

    private String status;

    private String userType;

    private String category;

    private UserMaster userMaster;
    private List<ArtMaster> artMaster;
    private List<ArtProductMaster> artProductMaster;
    private List<AdminArtProductMaster> adminArtProductMaster;

    private List list;

    private String coverImage;




}
