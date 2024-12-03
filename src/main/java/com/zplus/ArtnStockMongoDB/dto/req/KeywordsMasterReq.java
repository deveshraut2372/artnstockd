package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class KeywordsMasterReq {

    private String keywordId;
    private List<String> keywords;
}
