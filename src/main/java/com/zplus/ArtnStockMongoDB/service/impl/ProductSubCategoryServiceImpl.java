package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.ProductMainCategoryDao;
import com.zplus.ArtnStockMongoDB.dao.ProductSubCategoryDao;
import com.zplus.ArtnStockMongoDB.dao.ProductSubSubCategoryDao;
import com.zplus.ArtnStockMongoDB.dto.req.ProductSubCategoryReqDto;
import com.zplus.ArtnStockMongoDB.model.ProductMainCategoryMaster;
import com.zplus.ArtnStockMongoDB.model.ProductSubCategoryMaster;
import com.zplus.ArtnStockMongoDB.model.ProductSubSubCategory;
import com.zplus.ArtnStockMongoDB.service.ProductSubCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductSubCategoryServiceImpl implements ProductSubCategoryService {

    @Autowired
    private ProductSubCategoryDao productSubCategoryDao;

    @Autowired
    private ProductMainCategoryDao productMainCategoryDao;

    @Autowired
    private ProductSubSubCategoryDao productSubSubCategoryDao;



    @Override
    public Boolean createProductSubCategory(ProductSubCategoryReqDto productSubCategoryReqDto) {
        ProductSubCategoryMaster productSubCategoryMaster = new ProductSubCategoryMaster();
        ProductMainCategoryMaster productMainCategoryMaster=new ProductMainCategoryMaster();
        productMainCategoryMaster.setProductMainCategoryId(productSubCategoryReqDto.getProductMainCategoryId());
        productSubCategoryMaster.setProductMainCategoryMaster(productMainCategoryMaster);

        BeanUtils.copyProperties(productSubCategoryReqDto, productSubCategoryMaster);

        //  add a productSubSubCategoryMaster in productSubcategory
//        ProductSubSubCategory productSubSubCategory=new ProductSubSubCategory();
//        if(productSubCategoryReqDto.getProductSubCategoryId()!=null)
//        {
//            productSubSubCategory=productSubSubCategoryDao.findById(productSubCategoryReqDto.getProductSubSubCategoryId()).get();
//        }

        productSubCategoryMaster.setProductSubSubCategory(productSubCategoryReqDto.getProductSubSubCategory());


        try {
            productSubCategoryDao.save(productSubCategoryMaster);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public Boolean updateProductSubCategory(ProductSubCategoryReqDto productSubCategoryReqDto) {
        ProductSubCategoryMaster productSubCategoryMaster = new ProductSubCategoryMaster();

        ProductMainCategoryMaster productMainCategoryMaster=new ProductMainCategoryMaster();
        productMainCategoryMaster.setProductMainCategoryId(productSubCategoryReqDto.getProductMainCategoryId());
        productSubCategoryMaster.setProductMainCategoryMaster(productMainCategoryMaster);

        productSubCategoryMaster.setProductSubCategoryId(productSubCategoryReqDto.getProductSubCategoryId());
        BeanUtils.copyProperties(productSubCategoryReqDto, productSubCategoryMaster);
        productSubCategoryMaster.setProductSubCategoryId(productSubCategoryReqDto.getProductSubCategoryId());


        //  add a productSubSubCategoryMaster in productSubcategory
//        ProductSubSubCategory productSubSubCategory=new ProductSubSubCategory();
//        if(productSubCategoryReqDto.getProductSubCategoryId()!=null)
//        {
//            productSubSubCategory=productSubSubCategoryDao.findById(productSubCategoryReqDto.getProductSubSubCategoryId()).get();
//        }
        productSubCategoryMaster.setProductSubSubCategory(productSubCategoryReqDto.getProductSubSubCategory());



        try {
            System.out.println(" productSubCategoryMaster =  "+productSubCategoryMaster.toString());
            productSubCategoryDao.save(productSubCategoryMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllProductSubCategory() {
        return productSubCategoryDao.findAll();
    }

    @Override
    public ProductSubCategoryMaster editProductSubCategory(String productSubCategoryId) {
        ProductSubCategoryMaster productSubCategoryMaster = new ProductSubCategoryMaster();
        try {
            Optional<ProductSubCategoryMaster> productSubCategoryMaster1 = productSubCategoryDao.findById(productSubCategoryId);
            productSubCategoryMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, productSubCategoryMaster));
            return productSubCategoryMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return productSubCategoryMaster;
        }
    }

    @Override
    public List getActiveProductSubCategory() {
        return productSubCategoryDao.findAllByStatus("Active");
    }

    @Override
    public List<ProductSubCategoryMaster> getProductMainCategoryIdWiseProductSubCategory(String productMainCategoryId) {
        List<ProductSubCategoryMaster> list=productSubCategoryDao.findByProductMainCategoryMaster_ProductMainCategoryIdAndStatus(productMainCategoryId,"Active");
        list.stream().sorted(Comparator.comparing(ProductSubCategoryMaster::getIndex)).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<ProductSubCategoryMaster> getTypeWiseList(String type) {
        List<ProductSubCategoryMaster> list=new ArrayList<>();
        list=productSubCategoryDao.findByType(type);
//        list.sort((productSubCategoryMaster1, productSubCategoryMaster2)
//                -> productSubCategoryMaster1.getIndex().compareTo(
//                productSubCategoryMaster1.getIndex()));
        list.sort(Comparator.comparing(ProductSubCategoryMaster::getIndex));
        return list;
    }
}
