package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.MainCategoryReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.UpdateMainCategoryReqDto;
import com.zplus.ArtnStockMongoDB.model.MainCategoryMaster;

import java.util.List;

public interface MainCategoryService {
    Boolean createMainCategoryMaster(MainCategoryReqDto mainCategoryReqDto);

    Boolean updateMainCategoryMaster(UpdateMainCategoryReqDto updateMainCategoryReqDto);

    List getAllMainCategoryMaster();

    MainCategoryMaster editMainCategoryMaster(String mainCategoryId);

    List getActiveMainCategory();
}
