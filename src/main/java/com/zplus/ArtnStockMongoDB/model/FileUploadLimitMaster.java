package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@ToString
@Document(collection = "File_Upload_Limit_Master")
public class FileUploadLimitMaster {

    @Id
    private String userFileUploadId;
    @Field
    private Integer artCount=0;
    @Field
    private Integer photosCount=0;
    @Field
    private Integer footageCount=0;
    @Field
    private Integer musicCount=0;
    @Field
    private Integer templatesCounts=0;
    @Field
    private Integer level=1;

    @Field
    private Double approvedPercentage;


    @DBRef
    private UserMaster userMaster;

}
