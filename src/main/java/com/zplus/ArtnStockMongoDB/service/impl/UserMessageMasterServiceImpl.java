package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.UserDao;
import com.zplus.ArtnStockMongoDB.dao.UserMessageDao;
import com.zplus.ArtnStockMongoDB.dao.UserMessageMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.UserMessageMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.res.MainResDto;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import com.zplus.ArtnStockMongoDB.model.UserMessageMaster;
import com.zplus.ArtnStockMongoDB.service.UserMessageMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserMessageMasterServiceImpl implements UserMessageMasterService {
    @Autowired
    private UserMessageDao userMessageDao;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserDao userDao;
    @Override
    public MainResDto create(UserMessageMasterRequest userMessageMasterRequest) {
        MainResDto mainResDto = new MainResDto();
        UserMessageMaster userMessageMaster = new UserMessageMaster();
        Optional<UserMaster> userMaster = this.userDao.findByUserId(userMessageMasterRequest.getUserMessageId());
        if (userMaster.isPresent()){
            BeanUtils.copyProperties(userMessageMasterRequest,userMessageMaster);
            userMessageMaster.setUserMaster(userMaster.get());
            this.userMessageDao.save(userMessageMaster);
            mainResDto.setMessage("User Message Master created.");
            mainResDto.setResponseCode(HttpStatus.OK.value());
            mainResDto.setFlag(true);
        }else {
            mainResDto.setMessage("Something went wrong");
            mainResDto.setResponseCode(HttpStatus.BAD_REQUEST.value());
            mainResDto.setFlag(false);
        }
        return mainResDto;
    }
    @Override
    public MainResDto update(UserMessageMasterRequest userMessageMasterRequest) {
        MainResDto mainResDto = new MainResDto();
        UserMessageMaster userMessageMaster = new UserMessageMaster();
        Optional<UserMaster> userMaster = this.userDao.findByUserId(userMessageMasterRequest.getUserId());
        if (userMaster.isPresent()){
            BeanUtils.copyProperties(userMessageMasterRequest,userMessageMaster);
            userMessageMaster.setUserMaster(userMaster.get());
            this.userMessageDao.save(userMessageMaster);
            mainResDto.setMessage("User Message Master updated successfully");
            mainResDto.setResponseCode(HttpStatus.OK.value());
            mainResDto.setFlag(true);
        }else {
            mainResDto.setMessage("Something went wrong.");
            mainResDto.setResponseCode(HttpStatus.BAD_REQUEST.value());
            mainResDto.setFlag(false);
        }
        return mainResDto;
    }
    @Override
    public UserMessageMaster getById(String userMessageId) {
        Optional<UserMessageMaster> userMessageMaster = this.userMessageDao.findById(userMessageId);
        return userMessageMaster.get();
    }
    @Override
    public List<UserMessageMaster> getAll() {
        List<UserMessageMaster> userMessageMasters = this.userMessageDao.findAll();
        return userMessageMasters;
    }
    @Override
    public MainResDto deleteById(String userMessageId) {
        MainResDto mainResDto = new MainResDto();
        Optional<UserMessageMaster> userMessageMaster = this.userMessageDao.findById(userMessageId);
        if (userMessageMaster.isPresent()){
            this.userMessageDao.deleteById(userMessageMaster.get().getUserMessageId());
            mainResDto.setMessage("User message master deleted successfully.");
            mainResDto.setResponseCode(HttpStatus.OK.value());
            mainResDto.setFlag(true);
        }else {
            mainResDto.setMessage("Something went wrong.");
            mainResDto.setResponseCode(HttpStatus.OK.value());
            mainResDto.setFlag(false);
        }
        return mainResDto;
    }

    @Override
    public UserMessageMaster getUserIdWiseMessage(String userId) {

        UserMaster userMaster=new UserMaster();
        userMaster=userDao.findById(userId).get();

        Query query=new Query();
        query.addCriteria(Criteria.where("userMaster").is(userMaster));

        UserMessageMaster userMessageMaster=new UserMessageMaster();
        userMessageMaster=mongoTemplate.findOne(query,UserMessageMaster.class);
        return userMessageMaster;
    }
}