package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.TopBarMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.TopBarMasterReq;
import com.zplus.ArtnStockMongoDB.model.TopBarMaster;
import com.zplus.ArtnStockMongoDB.service.TopBarService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TopBarServiceImpl implements TopBarService {

    @Autowired
    private TopBarMasterDao topBarMasterDao;

    @Override
    public Boolean createTopBar(TopBarMasterReq topBarMasterReq) {

        TopBarMaster topBarMaster=new TopBarMaster();

        BeanUtils.copyProperties(topBarMasterReq,topBarMaster);
        topBarMaster.setDate(new Date());
        topBarMaster.setStatus("Active");
        try
        {
            topBarMasterDao.save(topBarMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateTopBar(TopBarMasterReq topBarMasterReq) {
        TopBarMaster topBarMaster=new TopBarMaster();
        Optional<TopBarMaster> topBarMaster1=topBarMasterDao.findById(topBarMasterReq.getTopBarId());
        topBarMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster,topBarMaster));
        topBarMaster.setLink1(topBarMasterReq.getLink1());
        topBarMaster.setLink2(topBarMasterReq.getLink2());
        topBarMaster.setLink3(topBarMasterReq.getLink3());
        topBarMaster.setDate(new Date());

        try
        {
            topBarMasterDao.save(topBarMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<TopBarMaster> getAllTopBars() {
        return topBarMasterDao.findAll();
    }

    @Override
    public TopBarMaster editBytopBarId(String topBarId) {
        TopBarMaster topBarMaster=new TopBarMaster();
        Optional<TopBarMaster> topBarMaster1=topBarMasterDao.findById(topBarId);
        topBarMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster,topBarMaster));
        return topBarMaster;
    }

    @Override
    public Boolean DeleteBytopBarId(String topBarId) {
        try
        {
            topBarMasterDao.deleteById(topBarId);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
