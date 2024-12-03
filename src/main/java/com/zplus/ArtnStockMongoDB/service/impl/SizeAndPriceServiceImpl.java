package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.ProductColorRepository;
import com.zplus.ArtnStockMongoDB.dao.SizeAndPriceRepository;
import com.zplus.ArtnStockMongoDB.dto.req.SizeAndPriceReq;
import com.zplus.ArtnStockMongoDB.model.BannerMaster;
import com.zplus.ArtnStockMongoDB.model.ProductColor;
import com.zplus.ArtnStockMongoDB.model.SizeAndPrice;
import com.zplus.ArtnStockMongoDB.service.SizeAndPriceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SizeAndPriceServiceImpl implements SizeAndPriceService {


    @Autowired
    private SizeAndPriceRepository sizeAndPriceRepository;

    @Autowired
    private ProductColorRepository productColorRepository;

    @Override
    public Boolean createSizeAndPrice(SizeAndPriceReq sizeAndPriceReq) {
        SizeAndPrice sizeAndPrice = new SizeAndPrice();
        BeanUtils.copyProperties(sizeAndPriceReq, sizeAndPrice);

        ProductColor productColor=new ProductColor();
        productColor=productColorRepository.findById(sizeAndPriceReq.getProductColorId()).get();

        Double basePrice=sizeAndPrice.getBasePrice()+productColor.getColorPrice();
        Double markup=basePrice*sizeAndPriceReq.getMarkup()/100;

        sizeAndPrice.setSellPrice(basePrice+markup);
        sizeAndPrice.setBasePrice(basePrice);

        try {
            sizeAndPriceRepository.save(sizeAndPrice);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateSizeAndPrice(SizeAndPriceReq sizeAndPriceReq) {
        SizeAndPrice sizeAndPrice = new SizeAndPrice();
        BeanUtils.copyProperties(sizeAndPriceReq, sizeAndPrice);

        ProductColor productColor=new ProductColor();
        productColor=productColorRepository.findById(sizeAndPriceReq.getProductColorId()).get();

        Double basePrice=sizeAndPrice.getBasePrice()+productColor.getColorPrice();
        Double markup=basePrice*sizeAndPriceReq.getMarkup()/100;

        sizeAndPrice.setSellPrice(basePrice+markup);
        sizeAndPrice.setBasePrice(basePrice);
        sizeAndPrice.setSizeAndPriceId(sizeAndPriceReq.getSizeAndPriceId());

        try {
            sizeAndPriceRepository.save(sizeAndPrice);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<SizeAndPrice> getAllSizes() {
        return  sizeAndPriceRepository.findAll();
    }

    @Override
    public SizeAndPrice getBySizeAndPriceId(String sizeAndPriceId) {
        return sizeAndPriceRepository.findById(sizeAndPriceId).get();
    }

    @Override
    public Boolean deleteBySizeAndPriceId(String sizeAndPriceId) {
        try {
            sizeAndPriceRepository.deleteById(sizeAndPriceId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<SizeAndPrice> getSizeAndPricesByProductColorId(String productColorId) {
        List<SizeAndPrice> sizeAndPriceList=new ArrayList<>();
        sizeAndPriceList=sizeAndPriceRepository.findAllByProductColorId(productColorId);
        return sizeAndPriceList;
    }


}
