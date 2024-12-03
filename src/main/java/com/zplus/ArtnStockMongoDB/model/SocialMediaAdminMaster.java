package com.zplus.ArtnStockMongoDB.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
@Document(collection = "SocialMediaAdmin_Master")
public class SocialMediaAdminMaster {

    @Id
    private String socialMediaAdminId;

    @Field
    private String logo;

    @Field
    private String name;

    @Field
    private String link;


    @Field
    private String status;


    private Date date;

}
