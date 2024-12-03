package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.AdminArtProductMaster;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter@Setter
public class CollectionMasterReqDto {
    private String collectionId;

    private String title;

    private Date updatedDate;

    private Integer count;

    private String status;

    private String userType;

    private String category;

    private String userId;

    private List<String> artId;

    private List<String> artProductId;

    private List<String> adminArtProductId;
}
