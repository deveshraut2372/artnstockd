package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Data;

@Data
public class MediumMasterReq {

    private String mediumId;

    private Boolean artDropDown;

    private String mediumImage;

    private String mediumDescription;

    private String mediumType;

    private String mediumStatus;

}
