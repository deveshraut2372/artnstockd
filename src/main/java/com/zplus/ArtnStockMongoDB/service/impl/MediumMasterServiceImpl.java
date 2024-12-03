package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.MediumMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.MediumMasterReq;
import com.zplus.ArtnStockMongoDB.model.BannerMaster;
import com.zplus.ArtnStockMongoDB.model.MediumMaster;
import com.zplus.ArtnStockMongoDB.service.MediumMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MediumMasterServiceImpl implements MediumMasterService {

    @Autowired
    private MediumMasterDao mediumMasterDao;


    @Override
    public Boolean createMediumMaster(MediumMasterReq mediumMasterReq) {
        MediumMaster mediumMaster = new MediumMaster();
        BeanUtils.copyProperties(mediumMasterReq, mediumMaster);
        mediumMaster.setMediumStatus("Active");
        try {
            mediumMasterDao.save(mediumMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateMediumMaster(MediumMasterReq mediumMasterReq) {
        MediumMaster mediumMaster = new MediumMaster();
        BeanUtils.copyProperties(mediumMasterReq, mediumMaster);
        mediumMaster.setMediumId(mediumMasterReq.getMediumId());
        try {
            mediumMasterDao.save(mediumMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllMediumMaster() {
        return mediumMasterDao.findAll();
    }

    @Override
    public MediumMaster editmediumMaster(String mediumId) {
        return mediumMasterDao.findById(mediumId).get();
    }

    @Override
    public List getActiveMediumMaster() {
        return null;
    }

    @Override
    public Boolean deleteMediumMaster(String mediumId) {
        try {
            mediumMasterDao.deleteById(mediumId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getMediumMasterByStatus(String status) {
        List list=new ArrayList();
        list=mediumMasterDao.findAllByMediumStatus(status);
        return list;
    }

}
