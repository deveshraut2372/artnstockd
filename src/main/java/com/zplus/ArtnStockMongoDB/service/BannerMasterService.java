package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.BannerReqDto;
import com.zplus.ArtnStockMongoDB.model.BannerMaster;

import java.util.List;

public interface BannerMasterService {
    Boolean createBannerMaster(BannerReqDto bannerReqDto);

    Boolean updateBannerMaster(BannerReqDto bannerReqDto);

    List getAllBannerMaster();

    BannerMaster editBannerMaster(String bannerId);

    List getActiveBannerMaster();

    List getBannerTypeWiseList(String bannerType);

}
