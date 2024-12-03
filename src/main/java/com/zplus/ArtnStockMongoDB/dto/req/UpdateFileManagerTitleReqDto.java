package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UpdateFileManagerTitleReqDto {

    private String fileManagerId;
    private String title;
}
