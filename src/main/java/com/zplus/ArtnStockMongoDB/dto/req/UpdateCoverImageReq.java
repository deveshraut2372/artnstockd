package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCoverImageReq {

    private String fileManagerId;

    private String coverImage;

}
