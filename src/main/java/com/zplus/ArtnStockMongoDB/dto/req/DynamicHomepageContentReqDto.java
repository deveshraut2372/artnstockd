package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class DynamicHomepageContentReqDto {

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
}
