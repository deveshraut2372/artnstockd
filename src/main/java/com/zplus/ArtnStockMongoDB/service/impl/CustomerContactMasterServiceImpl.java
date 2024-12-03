package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.CustomerContactMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.CustomerContactMasterReq;
import com.zplus.ArtnStockMongoDB.model.CustomerContactMaster;
import com.zplus.ArtnStockMongoDB.service.CustomerContactMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerContactMasterServiceImpl implements CustomerContactMasterService {

    @Autowired
    private CustomerContactMasterDao customerContactMasterDao;

    @Override
    public Boolean createCustomerContactMaster(CustomerContactMasterReq customerContactMasterReq) {
        Boolean flag = false;
        CustomerContactMaster customerContactMaster = new CustomerContactMaster();
        BeanUtils.copyProperties(customerContactMasterReq,customerContactMaster);
        try {
            CustomerContactMaster cm = customerContactMasterDao.save(customerContactMaster);
            System.out.println("customerContactMaster" + cm.getCustomerContactId());
            flag= true;
        } catch (Exception e) {
            e.printStackTrace();
            flag= false;
        }
        return flag;
    }
    @Override
    public Boolean updateCustomerContactMaster(CustomerContactMasterReq customerContactMasterReq) {
        Boolean flag = false;
        CustomerContactMaster customerContactMaster = new CustomerContactMaster();
        customerContactMaster.setCustomerContactId(customerContactMasterReq.getCustomerContactId());
        BeanUtils.copyProperties(customerContactMasterReq,customerContactMaster);
        try {
            CustomerContactMaster cm = customerContactMasterDao.save(customerContactMaster);
            System.out.println("customerContactMaster" + cm.getCustomerContactId());
            flag= true;
        } catch (Exception e) {
            e.printStackTrace();
            flag= false;
        }
        return flag;
    }

    @Override
    public CustomerContactMaster editCustomerContactMaster(String customerContactId) {
        CustomerContactMaster customerContactMaster=new CustomerContactMaster();
        try {
            Optional<CustomerContactMaster> customerContactMaster1=customerContactMasterDao.findById(customerContactId);
            customerContactMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, customerContactMaster));
            return customerContactMaster;
        }
        catch (Exception e) {
            e.printStackTrace();
            return customerContactMaster;
        }
    }

    @Override
    public List getCustomerContactMasterList() {
        List list=customerContactMasterDao.findAll();
        return list;
    }
}
