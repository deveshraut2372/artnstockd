package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.AccountSettingDao;
import com.zplus.ArtnStockMongoDB.dao.CountryMasterDao;
import com.zplus.ArtnStockMongoDB.dao.UserDao;
import com.zplus.ArtnStockMongoDB.dto.req.AccountSettingReq;
import com.zplus.ArtnStockMongoDB.model.AccountSettingMaster;
import com.zplus.ArtnStockMongoDB.model.BannerMaster;
import com.zplus.ArtnStockMongoDB.model.CountryMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import com.zplus.ArtnStockMongoDB.service.AccountSettingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountSettingServiceImpl implements AccountSettingService {

    @Autowired
    private AccountSettingDao accountSettingDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private CountryMasterDao countryMasterDao;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public Boolean createAccountSettingMaster(AccountSettingReq accountSettingReq) {
        AccountSettingMaster accountSettingMaster1 = new AccountSettingMaster();
        BeanUtils.copyProperties(accountSettingReq, accountSettingMaster1);

        UserMaster userMaster= userDao.findByUserId(accountSettingReq.getUserId()).get();
        accountSettingMaster1.setUserMaster(userMaster);

        CountryMaster countryMaster=countryMasterDao.findById(accountSettingReq.getCountryId()).get();
        accountSettingMaster1.setCountryMaster(countryMaster);

        try {
            accountSettingDao.save(accountSettingMaster1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateAccountSettingMaster(AccountSettingReq accountSettingReq ) {
        AccountSettingMaster accountSettingMaster1 = new AccountSettingMaster();
        BeanUtils.copyProperties(accountSettingReq, accountSettingMaster1);
        accountSettingMaster1.setAccountSettingId(accountSettingReq.getAccountSettingId());

        UserMaster userMaster= userDao.findByUserId(accountSettingReq.getUserId()).get();
        accountSettingMaster1.setUserMaster(userMaster);

        CountryMaster countryMaster=countryMasterDao.findById(accountSettingReq.getCountryId()).get();
        accountSettingMaster1.setCountryMaster(countryMaster);

        try {
            accountSettingDao.save(accountSettingMaster1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public AccountSettingMaster editAccountSettingMaster(String accountSettingId) {
        return accountSettingDao.findById(accountSettingId).get();
    }

    @Override
    public List<AccountSettingMaster> getAccountSettingMasters(String userId) {
        List<AccountSettingMaster> accountSettingMasterList=new ArrayList<>();
        accountSettingMasterList=accountSettingDao.findAllByUserMaster_UserId(userId);
        return accountSettingMasterList;
    }
}
