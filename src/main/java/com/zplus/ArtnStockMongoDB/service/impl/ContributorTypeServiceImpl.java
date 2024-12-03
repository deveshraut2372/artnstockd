package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.ContributorTypeDao;
import com.zplus.ArtnStockMongoDB.dto.req.ContributorTypeReq;
import com.zplus.ArtnStockMongoDB.model.ContributorTypeMaster;
import com.zplus.ArtnStockMongoDB.model.CountryMaster;
import com.zplus.ArtnStockMongoDB.service.ContributorTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContributorTypeServiceImpl implements ContributorTypeService {

    @Autowired
    private ContributorTypeDao contributorTypeDao;


    @Override
    public Boolean createContributorTypeMaster(ContributorTypeReq contributorTypeReq) {
       ContributorTypeMaster contributorTypeMaster=new ContributorTypeMaster();
        BeanUtils.copyProperties(contributorTypeReq, contributorTypeMaster);
        contributorTypeMaster.setStatus("Active");
        try {
            contributorTypeDao.save(contributorTypeMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateContributorTypeMaster(ContributorTypeReq contributorTypeReq) {
        ContributorTypeMaster contributorTypeMaster=new ContributorTypeMaster();
        BeanUtils.copyProperties(contributorTypeReq, contributorTypeMaster);
        contributorTypeMaster.setStatus("Active");
        contributorTypeMaster.setContributorTypeId(contributorTypeReq.getContributorTypeId());
        try {
            contributorTypeDao.save(contributorTypeMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllContributorTypeMaster() {
        return contributorTypeDao.findAll();
    }

    @Override
    public ContributorTypeMaster editCountryMaster(String contributorTypeId) {

        ContributorTypeMaster contributorTypeMaster=new ContributorTypeMaster();
        contributorTypeMaster=contributorTypeDao.findById(contributorTypeId).get();

        return contributorTypeMaster;
    }

    @Override
    public List getActiveContributorTypeMaster() {
        return contributorTypeDao.findAllByStatus("Active");
    }

    @Override
    public Boolean deleteByContributorTypeId(String contributorTypeId) {
        try {
            contributorTypeDao.deleteById(contributorTypeId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
