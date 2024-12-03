package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
@Document(collection = "banner_master")
public class BannerMaster {

    @Id
    private String bannerId;

    private String bannerLeftTitle;

    private String bannerLeftDescription;

    private String bannerType;

    private String ImagePath;

    private String status;

    private Date startDate;

    private Date endDate;

    private String ButtonDescription;
    private String bannerLeftDesc;
    private String copyRightText;
    private String offerNum;
    private String offerText;
    private String offerDesc;
    private String offerBtnDesc;
    private String rightBottomImg;
    private String mobileViewImg;
}

