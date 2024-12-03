package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.ContributorImageUploadDao;
import com.zplus.ArtnStockMongoDB.dto.req.ContributorImageFlagChangeReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ContributorImageStatusChangeReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ContributorImageUploadReq;
import com.zplus.ArtnStockMongoDB.dto.res.UserIdWiseContributorResDto;
import com.zplus.ArtnStockMongoDB.model.ContributorImageUploadMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import com.zplus.ArtnStockMongoDB.service.ContributorImageUploadService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContributorImageUploadServiceImpl implements ContributorImageUploadService {
    @Autowired
    private ContributorImageUploadDao contributorImageUploadDao;



    @Override
    public Boolean updateContributorImageUpload(ContributorImageUploadReq contributorImageUploadReq) {
        ContributorImageUploadMaster contributorImageUploadMaster=new ContributorImageUploadMaster();
        contributorImageUploadMaster.setStatus("Active");
        BeanUtils.copyProperties(contributorImageUploadReq, contributorImageUploadMaster);
        try {
            contributorImageUploadDao.save(contributorImageUploadMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean createContributorImageUpload(List<ContributorImageUploadReq> contributorImageList) {
            Boolean flag=false;

        ContributorImageUploadMaster contributorImageUploadMaster1=new ContributorImageUploadMaster();
        UserMaster userMaster=new UserMaster();
        try {
            for(ContributorImageUploadReq contributorImageUploadReq:contributorImageList){
                ContributorImageUploadMaster contributorImageUploadMaster=new ContributorImageUploadMaster();
                contributorImageUploadMaster.setImageURL(contributorImageUploadReq.getImageURL());
                contributorImageUploadMaster.setImageFlag(contributorImageUploadReq.getImageFlag());
                userMaster.setUserId(contributorImageUploadReq.getUserId());
                contributorImageUploadMaster.setUserMaster(userMaster);
                contributorImageUploadMaster1=contributorImageUploadDao.save(contributorImageUploadMaster);
                System.out.println("contributorImageUploadMaster1"+contributorImageUploadMaster1.toString());
            }
            flag =true;
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }
        return flag;



    }

    @Override
    public List getFlagTrueContributorImageUpload(String userId) {
        List<UserIdWiseContributorResDto> list1=new ArrayList<>();
       List<ContributorImageUploadMaster> contributorImageUploadMaster=contributorImageUploadDao.findAllByUserMasterUserIdIncludeImageURLAndImageFlagAndStatusAndIdFields(userId);
        System.out.println("contributorImageUploadMaster"+contributorImageUploadMaster.size());
       return contributorImageUploadMaster;


    }

    @Override
    public List<ContributorImageUploadMaster> getUserIdWiseContributorMaster(String userId) {
        List<ContributorImageUploadMaster> inventoryList = contributorImageUploadDao.findByUserMasterUserIdIncludeIdAndImageURLAndImageFlagAndStatusFields(userId);
        System.out.println("inventoryList..."+inventoryList.size());

        return inventoryList;
    }

    @Override
    public Boolean changeImageFlag(ContributorImageFlagChangeReqDto contributorImageFlagChangeReqDto) {
       Boolean flag=false;
        Optional<ContributorImageUploadMaster>  contributorImageUploadMaster = contributorImageUploadDao.findByContributorImageUploadId(contributorImageFlagChangeReqDto.getContributorImageUploadId());
        if (contributorImageUploadMaster.isPresent()) {
            ContributorImageUploadMaster contributorImageUploadMaster1 = contributorImageUploadMaster.get();
            contributorImageUploadMaster1.setImageFlag(contributorImageFlagChangeReqDto.getImageFlag());
            contributorImageUploadDao.save(contributorImageUploadMaster1);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    @Override
    public Boolean ContributorMasterChangeStatus(ContributorImageStatusChangeReqDto contributorImageStatusChangeReqDto) {
        Boolean flag=false;
        Optional<ContributorImageUploadMaster>  contributorImageUploadMaster = contributorImageUploadDao.findByContributorImageUploadId(contributorImageStatusChangeReqDto.getContributorImageUploadId());
        if (contributorImageUploadMaster.isPresent()) {
            ContributorImageUploadMaster contributorImageUploadMaster1 = contributorImageUploadMaster.get();
            contributorImageUploadMaster1.setStatus(contributorImageStatusChangeReqDto.getStatus());
            contributorImageUploadDao.save(contributorImageUploadMaster1);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    @Override
    public List<ContributorImageUploadMaster> getStatusWiseContributorMaster(String status) {
        List<ContributorImageUploadMaster> list = contributorImageUploadDao.getMaster(status);
        System.out.println("inventoryList..."+list.size());

        return list;    }


    @Override
    public List getAllContributorImageUpload() {

        List<ContributorImageUploadMaster> contributorImageUploadMasters = contributorImageUploadDao.findAll();
        return contributorImageUploadMasters;

    }

    @Override
    public ContributorImageUploadMaster editContributorImageUploadr(String contributorImageUploadId) {
        ContributorImageUploadMaster contributorImageUploadMaster=new ContributorImageUploadMaster();
        try {
            Optional<ContributorImageUploadMaster> contributorImageUploadMaster1=contributorImageUploadDao.findById(contributorImageUploadId);
            contributorImageUploadMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, contributorImageUploadMaster));
            return contributorImageUploadMaster;
        }
        catch (Exception e) {
            e.printStackTrace();
            return contributorImageUploadMaster;
        }
    }



}

