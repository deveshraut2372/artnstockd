package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.RecentlySearchRequest;
import com.zplus.ArtnStockMongoDB.model.RecentlySearchMaster;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface RecentlySearchMasterService {
    Boolean createRecentlySearchMaster(RecentlySearchRequest recentlySearchRequest);

    List getAllRecentlySearchMaster();

    List getUserIdWiseRecentlyKeywordSearch(String userId);

    RecentlySearchMaster getByRecentlySearchId(String recentlySearchId);

    Boolean deleteRecentlySearch(String userId);
}
