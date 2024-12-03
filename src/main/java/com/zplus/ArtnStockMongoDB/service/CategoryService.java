package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.CategoryMasterReq;
import com.zplus.ArtnStockMongoDB.model.CategoryMaster;

import java.util.List;

public interface CategoryService {

    public Boolean createCategoryMaster(CategoryMasterReq categoryMasterReq);

    public Boolean updateCategoryMaster(CategoryMasterReq categoryMasterReq);

    public List<CategoryMaster> getAll();
    public CategoryMaster getByCategoryId(String categoryId);
    public List<CategoryMaster> getAllByStatus(String status);

    Boolean deleteByCategoryId(String categoryId);
}
