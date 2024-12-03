package com.zplus.ArtnStockMongoDB.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection="social_Media_Master")
public class SocialMediaMaster {

    @Id
    private String linkId;

    @Field
    private String instagramLink;

    @Field
    private String linkedInLink;

    @Field
    private String twitterLink;

    @Field
    private String facebookLink;

    @Field
    private String status;

    @Field
    private Date date;

}
