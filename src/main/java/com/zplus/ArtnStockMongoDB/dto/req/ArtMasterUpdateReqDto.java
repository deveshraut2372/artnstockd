package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
public class ArtMasterUpdateReqDto {

    private String artId;
    private String artName;
    private String status;
    private String description;


}
