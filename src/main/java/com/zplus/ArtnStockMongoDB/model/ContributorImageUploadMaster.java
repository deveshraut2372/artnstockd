package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "contributor_image_upload")
public class ContributorImageUploadMaster
{
    @Id
    private String contributorImageUploadId;

    private String imageURL;

    private Boolean imageFlag;

    private String status;



//Realtion
    @DBRef
    private UserMaster userMaster;

}
