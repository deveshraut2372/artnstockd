package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.ProductMainCategoryDao;
import com.zplus.ArtnStockMongoDB.dto.req.ProductMainCategoryReqDto;
import com.zplus.ArtnStockMongoDB.model.ProductMainCategoryMaster;
import com.zplus.ArtnStockMongoDB.service.ProductMainCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductMainCategoryServiceImpl implements ProductMainCategoryService {

    @Autowired
    private ProductMainCategoryDao productMainCategoryDao;

    @Override
    public Boolean createProductMainCategory(ProductMainCategoryReqDto productMainCategoryReqDto) {
        ProductMainCategoryMaster productMainCategoryMaster = new ProductMainCategoryMaster();
        BeanUtils.copyProperties(productMainCategoryReqDto, productMainCategoryMaster);
        productMainCategoryMaster.setStatus("Active");
        try {
            productMainCategoryDao.save(productMainCategoryMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateProductMainCategory(ProductMainCategoryReqDto productMainCategoryReqDto) {
        ProductMainCategoryMaster productMainCategoryMaster = new ProductMainCategoryMaster();
        productMainCategoryMaster.setProductMainCategoryId(productMainCategoryReqDto.getProductMainCategoryId());
        BeanUtils.copyProperties(productMainCategoryReqDto, productMainCategoryMaster);
        try {
            productMainCategoryDao.save(productMainCategoryMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllProductMainCategory() {
        return productMainCategoryDao.findAll();
    }

    @Override
    public ProductMainCategoryMaster editProductMainCategory(String productMainCategoryId) {
        ProductMainCategoryMaster productMainCategoryMaster = new ProductMainCategoryMaster();
        try {
            Optional<ProductMainCategoryMaster> productMainCategoryMaster1 = productMainCategoryDao.findById(productMainCategoryId);
            productMainCategoryMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, productMainCategoryMaster));
            return productMainCategoryMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return productMainCategoryMaster;
        }
    }

    @Override
    public List getActiveProductMainCategory() {
        return productMainCategoryDao.findAllByStatus("Active");
    }
}
