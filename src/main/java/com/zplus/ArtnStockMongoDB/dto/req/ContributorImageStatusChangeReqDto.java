package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ContributorImageStatusChangeReqDto {

    private String contributorImageUploadId;
    private String status;
}
