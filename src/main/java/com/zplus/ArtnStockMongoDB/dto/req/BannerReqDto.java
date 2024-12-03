package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class BannerReqDto {

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
