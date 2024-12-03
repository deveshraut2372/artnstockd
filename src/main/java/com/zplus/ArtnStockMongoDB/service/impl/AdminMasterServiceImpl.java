package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.AdminMasterDao;
import com.zplus.ArtnStockMongoDB.dao.UserDao;
import com.zplus.ArtnStockMongoDB.dto.req.AdminMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterFilterReqDto;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.AdminMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminMasterServiceImpl implements AdminMasterService {

    @Autowired
    private AdminMasterDao adminMasterDao;


    @Autowired
    private UserDao userDao;

    @Override
    public Boolean createAdminMaster(AdminMasterRequest adminMasterRequest) {
        AdminMaster adminMaster = new AdminMaster();
        BeanUtils.copyProperties(adminMasterRequest, adminMaster);
        try {
            adminMasterDao.save(adminMaster);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateAdminMaster(AdminMasterRequest adminMasterRequest) {
        AdminMaster adminMaster = new AdminMaster();
        adminMaster.setAdminId(adminMasterRequest.getAdminId());
        BeanUtils.copyProperties(adminMasterRequest, adminMaster);
        try {
            adminMasterDao.save(adminMaster);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List getAllAdminMaster() {
        return adminMasterDao.findAll();
    }

    @Override
    public AdminMaster editAdminMaster(String adminId) {
        AdminMaster adminMaster = new AdminMaster();
        try {
            Optional<AdminMaster> adminMaster1 = adminMasterDao.findById(adminId);
            adminMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, adminMaster));
            return adminMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return adminMaster;
        }
    }

    @Async
    @Override
    public Boolean changeTermsAndCondition() {

        try
        {
            List<UserMaster> userMasterList=new ArrayList<>();
            userMasterList=userDao.findAll();
            userMasterList.forEach(userMaster -> userMaster.setTermsAndConditions(true));
            userDao.saveAll(userMasterList);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


}
