package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.LimitedEditionDao;
import com.zplus.ArtnStockMongoDB.dto.req.LimitedEditionReqDto;
import com.zplus.ArtnStockMongoDB.model.LimitedEditionMaster;
import com.zplus.ArtnStockMongoDB.service.LimitedEditionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LimitedEditionServiceImpl implements LimitedEditionService {

    @Autowired
    private LimitedEditionDao limitedEditionDao;

    @Override
    public Boolean createLimitedEditionMaster(LimitedEditionReqDto limitedEditionReqDto) {
        LimitedEditionMaster limitedEditionMaster = new LimitedEditionMaster();

        BeanUtils.copyProperties(limitedEditionReqDto, limitedEditionMaster);
        try {
            limitedEditionDao.save(limitedEditionMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateLimitedEditionMaster(LimitedEditionReqDto limitedEditionReqDto) {
        LimitedEditionMaster limitedEditionMaster = new LimitedEditionMaster();

        limitedEditionMaster.setLimitedEditionId(limitedEditionReqDto.getLimitedEditionId());
        BeanUtils.copyProperties(limitedEditionReqDto, limitedEditionMaster);
        try {
            limitedEditionDao.save(limitedEditionMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllLimitedEditionMaster() {
        return  limitedEditionDao.findAll();
    }

    @Override
    public LimitedEditionMaster editLimitedEditionMaster(String limitedEditionId) {
        LimitedEditionMaster limitedEditionMaster = new LimitedEditionMaster();
        try {
            Optional<LimitedEditionMaster> limitedEditionMaster1 = limitedEditionDao.findById(limitedEditionId);
            limitedEditionMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, limitedEditionMaster));
            return limitedEditionMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return limitedEditionMaster;
        }
    }

    @Override
    public List getActiveLimitedEditionMaster() {
        List list = limitedEditionDao.findAllByStatus("Active");
        return list;    }

    @Override
    public LimitedEditionMaster getTypeWiseList(String type) {
        LimitedEditionMaster limitedEditionMaster=limitedEditionDao.findAllByType(type);
        return limitedEditionMaster;
    }
}
