package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@ToString
@Document(collection = "dynamic_homepage_content_master")

public class DynamicHomepageContentMaster {

    @Id
    private String dynamicHomepageContentId;
    private String gridTitle;
    private String gridDesc;
    private String popularProductTitle;
    private String popularProductDesc;
    private String smallLogo;
    private String bigLogo;
    private String comboTextImg;
    private String signInLeftImg;
    private String signInBackground;
    private String signInLeftMain;
    private String signInLeftDesc;
    private String signInLeftBtn;
    private String signInBottomText;
    private String type;
    private String royaltyHeading;
    private String royaltyDesc;


}
