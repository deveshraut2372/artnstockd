package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContributorImageFlagChangeReqDto {
    private String contributorImageUploadId;
    private Boolean imageFlag;
}
