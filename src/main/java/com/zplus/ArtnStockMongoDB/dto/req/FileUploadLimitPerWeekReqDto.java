package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class FileUploadLimitPerWeekReqDto {

    private String fileUploadLimitPerWeekId;

    private Double art;

    private Double photos;

    private Double footage;

    private Double music;

    private Double templates;

    private Double approvalPercentageFrom;

    private Double approvalPercentageTo;

    private Double approvalPercentage;
}
