package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
public class UserIdWiseContributorResDto {
    private String contributorImageUploadId;

    private String imageURL;

    private Boolean imageFlag;

    private String status;
}
