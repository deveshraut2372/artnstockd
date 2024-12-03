package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.ProductColorRepository;
import com.zplus.ArtnStockMongoDB.dto.req.ProductColorReq;
import com.zplus.ArtnStockMongoDB.dto.res.ProductColorRes;
import com.zplus.ArtnStockMongoDB.model.ProductColor;
import com.zplus.ArtnStockMongoDB.service.ProductColorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProductColorServiceImpl implements ProductColorService {


    @Autowired
    private ProductColorRepository productColorRepository;


    @Override
    public Boolean createProductColor(ProductColorReq productColorReq) {
        ProductColor productColor = new ProductColor();
        BeanUtils.copyProperties(productColorReq, productColor);
        try {
            productColorRepository.save(productColor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateProductColor(ProductColorReq productColorReq) {
        ProductColor productColor = new ProductColor();
        BeanUtils.copyProperties(productColorReq, productColor);
        productColor.setProductColorId(productColorReq.getProductColorId());
        try {
            productColorRepository.save(productColor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllProductColors() {
        return productColorRepository.findAll();
    }

    @Override
    public ProductColor getByProductColorId(String productColorId) {
        return productColorRepository.findById(productColorId).get();
    }

    @Override
    public Boolean deleteByProductColorId(String productColorId) {
        try {
            productColorRepository.deleteById(productColorId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ProductColor> getByProductColorByProductStyleId(String productStyleId) {
      List<ProductColor> productColorList=new ArrayList<>();
      productColorList=productColorRepository.findAllByProductStyleId(productStyleId);
      productColorList.stream().sorted(Comparator.comparingInt(ProductColor::getIndex)).collect(Collectors.toSet());
        return productColorList;
    }

    @Override
    public ProductColorRes getByProductColorsByProductStyleId(String productStyleId) {

        ProductColorRes productColorRes=new ProductColorRes();
        List<ProductColor> productColorList=new ArrayList<>();
        productColorList=productColorRepository.findAllByProductStyleId(productStyleId);

        Set<ProductColor> standardColors =new HashSet<>();

        List<ProductColor> slist=productColorList.stream().filter(productColor -> productColor.getColorType().equalsIgnoreCase("Standard")).collect(Collectors.toList());
//        standardColors.stream().sorted(Comparator.comparingInt(ProductColor::getIndex)).collect(Collectors.toCollection(LinkedHashSet::new));
        slist.sort(Comparator.comparingInt(ProductColor::getIndex));
//        slist.sorted(Comparator.comparingInt(ProductColor::getIndex));
        standardColors.addAll(slist);
        productColorRes.setStandaredColors(standardColors);

        Set<ProductColor> neonColors=new HashSet<>();
        List<ProductColor> nlist= productColorList.stream().filter(productColor -> productColor.getColorType().equalsIgnoreCase("Neon")).collect(Collectors.toList());
        slist.sort(Comparator.comparingInt(ProductColor::getIndex));
//        nlist.stream().sorted(Comparator.comparingInt(ProductColor::getIndex));
//        neonColors.stream().sorted(Comparator.comparingInt(ProductColor::getIndex)).collect(Collectors.toCollection(LinkedHashSet::new));
        neonColors.addAll(nlist);
        productColorRes.setNeonColors(neonColors);

        return productColorRes;
    }
}
