package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.ProductStyleRepository;
import com.zplus.ArtnStockMongoDB.dto.ProductStyleReq;
import com.zplus.ArtnStockMongoDB.model.ProductStyle;
import com.zplus.ArtnStockMongoDB.service.ProductStyleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductStyleServiceImpl implements ProductStyleService {

    @Autowired
     ProductStyleRepository productStyleRepository;


    @Override
    public Boolean createProductStyle(ProductStyleReq productStyleReq) {
        ProductStyle productStyle=new ProductStyle();
        BeanUtils.copyProperties(productStyleReq, productStyle);
        try {
            productStyleRepository.save(productStyle);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateProductStyle(ProductStyleReq productStyleReq) {
        ProductStyle productStyle=new ProductStyle();
        BeanUtils.copyProperties(productStyleReq, productStyle);
        try {
            productStyle.setProductStyleId(productStyleReq.getProductStyleId());
            productStyleRepository.save(productStyle);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllProductStyles() {
        return productStyleRepository.findAll();
    }

    @Override
    public ProductStyle getByProductStyleId(String productStyleId) {
        return productStyleRepository.findById(productStyleId).get();
    }

    @Override
    public Boolean deleteByProductStyleId(String productStyleId) {
        try {
            productStyleRepository.deleteById(productStyleId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ProductStyle> getAllStylesByProductSubSubCategoryId(String productSubSubCategoryId) {
        List<ProductStyle> productStyleList=new ArrayList<>();
        productStyleList=productStyleRepository.findAllByProductSubSubCategoryId(productSubSubCategoryId);
        productStyleList.stream().sorted(Comparator.comparingInt(ProductStyle::getIndex)).collect(Collectors.toList());
        return productStyleList;
    }
}
