package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.ProductMasterDao;
import com.zplus.ArtnStockMongoDB.dao.ProductSubCategoryDao;
import com.zplus.ArtnStockMongoDB.dao.ProductSubSubCategoryDao;
import com.zplus.ArtnStockMongoDB.dto.req.ProductSubSubCategoryReq;
import com.zplus.ArtnStockMongoDB.model.ProductMaster;
import com.zplus.ArtnStockMongoDB.model.ProductSubCategoryMaster;
import com.zplus.ArtnStockMongoDB.model.ProductSubSubCategory;
import com.zplus.ArtnStockMongoDB.service.ProductSubSubCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductSubSubCategoryServiceImpl implements ProductSubSubCategoryService {

    @Autowired
    private ProductSubSubCategoryDao productSubSubCategoryDao;

    @Autowired
    private ProductSubCategoryDao productSubCategoryDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    
    @Autowired
    private ProductMasterDao productMasterDao;
    


    public Map createProductSubSubCategory(ProductSubSubCategoryReq productSubSubCategoryReq)
    {
        Map message=new HashMap();

        try {
            ProductSubSubCategory productSubSubCategory = new ProductSubSubCategory();
            BeanUtils.copyProperties(productSubSubCategoryReq, productSubSubCategory);

            ProductSubCategoryMaster productSubCategoryMaster = new ProductSubCategoryMaster();
            productSubCategoryMaster = productSubCategoryDao.findById(productSubSubCategoryReq.getProductSubCategoryId()).get();

            System.out.println(" sub category of product ="+productSubCategoryMaster.getProductSubCategoryId());
//            productSubSubCategory.setProductSubCategoryMaster(productSubCategoryMaster);

            productSubSubCategory.setProductSubCategoryId(productSubSubCategoryReq.getProductSubCategoryId());

            if(productSubCategoryMaster==null) {
                ProductMaster productMaster = new ProductMaster();
                List<ProductMaster> productMasterList = new ArrayList<>();
                productMasterList = productMasterDao.findByProductSubCategoryMaster_ProductSubCategoryId(productSubCategoryMaster.getProductSubCategoryId());
                productMaster = productMasterList.stream().findFirst().get();
                productSubSubCategory.setProductId(productMaster.getProductId());
                productSubSubCategory.setProductMainCategoryId(productSubCategoryMaster.getProductMainCategoryMaster().getProductMainCategoryId());
                productSubSubCategory.setProductSubCategoryId(productSubCategoryMaster.getProductSubCategoryId());
            }

            productSubSubCategoryDao.save(productSubSubCategory);

            message.put("flag","true");
            message.put("responseCode","200");
            message.put("message","Created Succedfully ");
            return message;
        }catch (Exception e)
        {
            e.printStackTrace();
            message.put("flag","false");
            message.put("responseCode","400");
            message.put("message"," Error "+e.getMessage());
            return message;
        }
    }

    public Map updateProductSubSubCategory(ProductSubSubCategoryReq productSubSubCategoryReq)
    {
        Map message=new HashMap();

        try {
            ProductSubSubCategory productSubSubCategory = new ProductSubSubCategory();
            BeanUtils.copyProperties(productSubSubCategoryReq, productSubSubCategory);

            ProductSubCategoryMaster productSubCategoryMaster = new ProductSubCategoryMaster();
            productSubCategoryMaster = productSubCategoryDao.findById(productSubCategoryMaster.getProductSubCategoryId()).get();

            System.out.println(" sub category of product ="+productSubCategoryMaster.getProductSubCategoryId());
//            productSubSubCategory.setProductSubCategoryMaster(productSubCategoryMaster);
//            productSubSubCategory.setProductSubSubCategoryId(productSubSubCategoryReq.getProductSubSubCategoryId());

            productSubSubCategoryDao.save(productSubSubCategory);

//            productSubSubCategory.setProductSubSubCategoryId(productSubSubCategoryReq.getProductSubSubCategoryId());

            message.put("flag","true");
            message.put("responseCode","200");
            message.put("message","Update Succedfully ");
            return message;
        }catch (Exception e)
        {
            e.printStackTrace();
            message.put("flag","false");
            message.put("responseCode","400");
            message.put("message"," Error "+e.getMessage());
            return message;
        }
    }


    public  List<ProductSubSubCategory> getAllByProductSubCategoryId(String productSubCategoryId)
    {
        List<ProductSubSubCategory> productSubSubCategoryList=new LinkedList<>();

        ProductSubCategoryMaster productSubCategoryMaster=new ProductSubCategoryMaster();
        productSubCategoryMaster=productSubCategoryDao.findById(productSubCategoryId).get();

        Query query=new Query();
        query.addCriteria(Criteria.where("productSubCategoryId").is(productSubCategoryMaster.getProductSubCategoryId()));
        productSubSubCategoryList=mongoTemplate.find(query, ProductSubSubCategory.class);
        productSubSubCategoryList.stream().sorted(Comparator.comparingInt(ProductSubSubCategory::getIndex)).collect(Collectors.toList());
        return productSubSubCategoryList;
    }


    public List<ProductSubSubCategory> getAll()
    {
        List<ProductSubSubCategory> productSubSubCategoryList=new LinkedList<>();
        productSubSubCategoryList=productSubSubCategoryDao.findAll();
        return productSubSubCategoryList;
    }

    @Override
    public List<ProductSubSubCategory> getByProductSubCategoryId(String productSubCategoryId) {
        List<ProductSubSubCategory> productSubSubCategoryList=new LinkedList<>();
        productSubSubCategoryList=productSubSubCategoryDao.findAllByProductSubCategoryId(productSubCategoryId);
        return productSubSubCategoryList;
    }


}
