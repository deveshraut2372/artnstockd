package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.CategoryMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.BannerReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.CategoryMasterReq;
import com.zplus.ArtnStockMongoDB.model.BannerMaster;
import com.zplus.ArtnStockMongoDB.model.CategoryMaster;
import com.zplus.ArtnStockMongoDB.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryMasterDao categoryMasterDao;

    @Override
    public Boolean createCategoryMaster(CategoryMasterReq categoryMasterReq) {
        CategoryMaster categoryMaster = new CategoryMaster();
        BeanUtils.copyProperties(categoryMasterReq, categoryMaster);
        categoryMaster.setStatus("Active");
        categoryMaster.setLocalDate(LocalDate.now());
        try {
            categoryMasterDao.save(categoryMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateCategoryMaster(CategoryMasterReq categoryMasterReq) {
        CategoryMaster categoryMaster = new CategoryMaster();
        categoryMaster=categoryMasterDao.findById(categoryMaster.getCategoryId()).get();
        BeanUtils.copyProperties(categoryMasterReq, categoryMaster);
        categoryMaster.setCategoryId(categoryMasterReq.getCategoryId());
        categoryMaster.setStatus(categoryMasterReq.getStatus());
        categoryMaster.setLocalDate(LocalDate.now());
        try {
            categoryMasterDao.save(categoryMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CategoryMaster> getAll()
    {
        List<CategoryMaster> categoryMasterList=new ArrayList<>();
        categoryMasterList=categoryMasterDao.findAll();
        return categoryMasterList;
    }

    public CategoryMaster getByCategoryId(String categoryId)
    {
        CategoryMaster categoryMaster=new CategoryMaster();
        categoryMaster=categoryMasterDao.findById(categoryId).get();
        return categoryMaster;
    }

    public List<CategoryMaster> getAllByStatus(String status)
    {
        List<CategoryMaster> categoryMasterList=new ArrayList<>();
        categoryMasterList=categoryMasterDao.findAllByStatus(status);
        return categoryMasterList;
    }

    @Override
    public Boolean deleteByCategoryId(String categoryId) {
        try {
            categoryMasterDao.deleteById(categoryId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
