package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter@NoArgsConstructor
public class ContributorImageUploadReq {
    private String contributorImageUploadId;

    private String imageURL;

    private Boolean imageFlag;

    private String userId;

}
