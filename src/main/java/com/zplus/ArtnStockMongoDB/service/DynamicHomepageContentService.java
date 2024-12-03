package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.DynamicHomepageContentReqDto;
import com.zplus.ArtnStockMongoDB.model.DynamicHomepageContentMaster;

import java.util.List;

public interface DynamicHomepageContentService {
    Boolean createDynamicHomepageContentMaster(DynamicHomepageContentReqDto dynamicHomepageContentReqDto);

    Boolean updateDynamicHomepageContentMaster(DynamicHomepageContentReqDto dynamicHomepageContentReqDto);

    List getAllDynamicHomepageContentMaster();

    DynamicHomepageContentMaster editDynamicHomepageContentMaster(String dynamicHomepageContentId);

    DynamicHomepageContentMaster getTypeWiseList(String type);
}
