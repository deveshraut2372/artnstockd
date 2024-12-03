package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.configuration.UniqueNumber;
import com.zplus.ArtnStockMongoDB.dao.CategoryMasterDao;
import com.zplus.ArtnStockMongoDB.dao.ImageMasterDao;
import com.zplus.ArtnStockMongoDB.dao.ProductMasterDao;
import com.zplus.ArtnStockMongoDB.dao.UserDao;
import com.zplus.ArtnStockMongoDB.dto.req.ProductReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.UpdateProductReqDto;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.ProductMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductMasterServiceImpl implements ProductMasterService {

    @Autowired
    private ProductMasterDao productMasterDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CategoryMasterDao categoryMasterDao;

@Autowired
    private ImageMasterDao imageMasterDao;
    @Override
    public Boolean createProductMaster(ProductReqDto productReqDto) {
        Boolean flag=false;
        ProductMaster productMaster=new ProductMaster();
        ProductMaster productMaster1=new ProductMaster();
        ImageMaster imageMaster = imageMasterDao.getImage(productReqDto.getImageId());

        imageMaster.setImageId(productReqDto.getImageId());
        productMaster.setImageMaster(imageMaster);
        ProductSubCategoryMaster productSubCategoryMaster=new ProductSubCategoryMaster();
        productSubCategoryMaster.setProductSubCategoryId(productReqDto.getProductSubCategoryId());
        productMaster.setProductSubCategoryMaster(productSubCategoryMaster);
        productMaster.setIndex(productReqDto.getIndex());

        BeanUtils.copyProperties(productReqDto,productMaster);

//        List<CategoryMaster> categoryMasterList=new ArrayList<>();
//        for (String categoryId : productReqDto.getCategoryIds()) {
//            CategoryMaster categoryMaster=new CategoryMaster();
//            categoryMaster=categoryMasterDao.findById(categoryId).get();
//            categoryMasterList.add(categoryMaster);
//        }
//        productMaster.setCategoryMasterList(categoryMasterList);

        try {
            productMaster.setType("product");
            productMaster1= productMasterDao.save(productMaster);
            flag=true;
        }catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }

        String productNo = UniqueNumber.generateUniqueNumber();
        System.out.println("productNo: " + productNo);
        try {
            Optional<ProductMaster> optionalProductMaster = productMasterDao.findByProductId(productMaster1.getProductId());
            if (optionalProductMaster.isPresent()) {
                ProductMaster master = optionalProductMaster.get();
                master.setProductNo(productNo);
                ProductMaster productMaster2 = productMasterDao.save(master);
                System.out.println("ProductNo111: " + productMaster2.getProductNo());
                flag = true;
            } else {
                flag = false;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
        }
         return flag;
    }

    @Override
    public Boolean updateProductMaster(UpdateProductReqDto updateProductReqDto) {
        ProductMaster productMaster = new ProductMaster();
        ProductSubCategoryMaster productSubCategoryMaster=new ProductSubCategoryMaster();
        productSubCategoryMaster.setProductSubCategoryId(updateProductReqDto.getProductSubCategoryId());
        productMaster.setProductSubCategoryMaster(productSubCategoryMaster);
        productMaster.setIndex(updateProductReqDto.getIndex());
        updateProductReqDto.setProductId(productMaster.getProductId());

     try {
            productMasterDao.save(productMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllProductMaster() {
        List<ProductMaster> productList = productMasterDao.findAll();
        return productList;
    }

    @Override
    public ProductMaster editProductMaster(String productId) {
        ProductMaster productMaster=new ProductMaster();
        try {
            Optional<ProductMaster> productMaster1=productMasterDao.findById(productId);
            productMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, productMaster));
            return productMaster;
        }
        catch (Exception e) {
            e.printStackTrace();
            return productMaster;
        }
    }

    @Override
    public List getActiveProductMaster() {
        return productMasterDao.findAllByStatus("Active");
    }

    @Override
    public List<ProductMaster> getProductSubCategoryIdWiseProductMasterList(String productSubCategoryId) {
        List<ProductMaster> list=productMasterDao.findByProductSubCategoryMaster_ProductSubCategoryId(productSubCategoryId);
        return list;
    }



//    @Override
//    public List getPopularStatusWiseList() {
//        List<ProductMaster> productMasterList=productMasterDao.findAll();
////        List sortedList = productMasterList.stream().sorted().collect(Collectors.toList());
////        System.out.println("sortedList" +sortedList.size());
//
//        final Function<ProductMaster, Integer> byPopularStatus = productMaster -> productMaster.getPopularStatus();
//        List<ProductMaster> sortedlist =   productMasterList.stream().sorted(Comparator.comparing(byPopularStatus))
//                .collect(Collectors.toList());
//
//         Collections.reverse(sortedlist);
//         sortedlist.stream().limit(10);
////        System.out.println("aaaaa"+sortedlist);
//
//        return sortedlist;
//    }
}

