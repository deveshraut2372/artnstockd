package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderFilterReq {

    private String userId;

    private Date date;

}
