package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.DiscountMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.DiscountMasterReq;
import com.zplus.ArtnStockMongoDB.model.DiscountMaster;
import com.zplus.ArtnStockMongoDB.service.DiscountMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class DiscountMasterServiceImpl implements DiscountMasterService {


    @Autowired
    private DiscountMasterDao discountMasterDao;


    @Override
    public Boolean createDiscountMaster(DiscountMasterReq discountMasterReq) {

        DiscountMaster discountMaster=new DiscountMaster();
        BeanUtils.copyProperties(discountMasterReq,discountMaster);
        discountMaster.setDate(new Date());
        try
        {
            discountMasterDao.save(discountMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateDiscountMaster(DiscountMasterReq discountMasterReq) {
        DiscountMaster discountMaster=new DiscountMaster();
        BeanUtils.copyProperties(discountMasterReq,discountMaster);
        discountMaster.setDiscountId(discountMasterReq.getDiscountId());
        discountMaster.setDate(new Date());
        try
        {
            discountMasterDao.save(discountMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<DiscountMaster> getAllDiscounts() {
        return discountMasterDao.findAll();
    }

    @Override
    public DiscountMaster editByDiscountId(String discountId) {
        return null;
    }

    @Override
    public Boolean deleteByDiscountId(String discountId) {
        try
        {
            discountMasterDao.deleteById(discountId);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


}
