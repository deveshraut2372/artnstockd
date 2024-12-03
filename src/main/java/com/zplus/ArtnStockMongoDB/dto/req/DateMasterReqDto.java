package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class DateMasterReqDto {
    private String dateId;
    private Date date;
    private Integer count;
    private String userId;
}
