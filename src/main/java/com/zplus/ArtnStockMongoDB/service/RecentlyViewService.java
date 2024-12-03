package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.RecentlyViewRequest;
import com.zplus.ArtnStockMongoDB.model.RecentlyViewMaster;

import java.text.ParseException;
import java.util.List;

public interface RecentlyViewService {
  Boolean createRecentlyViewMaster(RecentlyViewRequest recentlyViewRequest);

  List<RecentlyViewMaster> getAllRecentlyViewMaster() ;

  List getUserIdWiseRecentlyViewMaster(String userId);

  RecentlyViewMaster getByRecentlyViewId(String recentlyViewId);

  Boolean deleteByUserId(String userId);
}
