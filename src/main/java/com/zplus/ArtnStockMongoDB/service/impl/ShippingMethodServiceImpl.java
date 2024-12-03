package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.ShippingMethodDao;
import com.zplus.ArtnStockMongoDB.dto.req.ShippingMethodRequest;
import com.zplus.ArtnStockMongoDB.model.ShippingMethod;
import com.zplus.ArtnStockMongoDB.model.StyleMaster;
import com.zplus.ArtnStockMongoDB.service.ShippingMethodService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingMethodServiceImpl implements ShippingMethodService {

    @Autowired
    private ShippingMethodDao shippingMethodDao;

    @Override
    public Boolean createShippingMethod(ShippingMethodRequest shippingMethodRequest) {
        ShippingMethod shippingMethod = new ShippingMethod();
        BeanUtils.copyProperties(shippingMethodRequest, shippingMethod);
        try {
            shippingMethodDao.save(shippingMethod);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public Boolean updateShippingMethod(ShippingMethodRequest shippingMethodRequest) {
        ShippingMethod shippingMethod = new ShippingMethod();
        shippingMethod.setShippingMethodId(shippingMethodRequest.getShippingMethodId());
        BeanUtils.copyProperties(shippingMethodRequest, shippingMethod);
        try {
            shippingMethodDao.save(shippingMethod);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllShippingMethod() {
        return shippingMethodDao.findAll();
    }

    @Override
    public ShippingMethod editShippingMethod(String shippingMethodId) {
        ShippingMethod shippingMethod = new ShippingMethod();
        try {
            Optional<ShippingMethod> shippingMethodOptional = shippingMethodDao.findById(shippingMethodId);
            shippingMethodOptional.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, shippingMethod));
            return shippingMethod;
        } catch (Exception e) {
            e.printStackTrace();
            return shippingMethod;
        }
    }
}
