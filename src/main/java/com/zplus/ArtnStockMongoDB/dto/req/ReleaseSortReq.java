package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReleaseSortReq {

    private  String fileName;

    private String sortType;

    private String userId;

    private Integer limit;

    private  String type;

}
