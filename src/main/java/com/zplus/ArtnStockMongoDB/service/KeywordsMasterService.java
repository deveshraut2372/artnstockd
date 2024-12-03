package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.KeywordsMasterReq;

import java.util.List;
import java.util.Set;

public interface KeywordsMasterService {
    Boolean addKeywords(KeywordsMasterReq keywordsMasterReq);

    Set getKeywords();

    Set<String> getKeywordsByNumber(Integer number);

    Boolean deleteByKeyword(String keyword);
}
