package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.configuration.GiftAndPromoCode;
import com.zplus.ArtnStockMongoDB.dao.GiftCodeDao;
import com.zplus.ArtnStockMongoDB.dto.req.GiftCodeRequest;
import com.zplus.ArtnStockMongoDB.model.GiftCodeMaster;
import com.zplus.ArtnStockMongoDB.model.PromoCodeMaster;
import com.zplus.ArtnStockMongoDB.service.GiftCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCodeServiceImpl implements GiftCodeService {

    @Autowired
    private GiftCodeDao giftCodeDao;

    @Override
    public Boolean createGiftCodeMaster(GiftCodeRequest giftCodeRequest) {
        Boolean flag=false;
        GiftCodeMaster giftCodeMaster=new GiftCodeMaster();
        giftCodeMaster.setGiftCode(GiftAndPromoCode.generatePromoCode());
        giftCodeMaster.setStatus("Active");
        BeanUtils.copyProperties(giftCodeRequest,giftCodeMaster);
        try {
            giftCodeDao.save(giftCodeMaster);
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }

    @Override
    public Boolean updateGiftCodeMaster(GiftCodeRequest giftCodeRequest) {

        Boolean flag=false;
        GiftCodeMaster giftCodeMaster=new GiftCodeMaster();
        GiftCodeMaster giftCodeMaster1=giftCodeDao.findById(giftCodeRequest.getGiftCodeId()).get();
        giftCodeMaster.setGiftCodeId(giftCodeRequest.getGiftCodeId());
        BeanUtils.copyProperties(giftCodeRequest,giftCodeMaster);
        giftCodeMaster.setGiftCode(giftCodeMaster1.getGiftCode());
        try {
            giftCodeDao.save(giftCodeMaster);
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }

    @Override
    public List getAllGiftCodeMaster() {
        List list=giftCodeDao.findAll();
        return list;
    }

    @Override
    public List getActiveGiftCodeMaster() {
        List list=giftCodeDao.findByStatus("Active");
        return list;
    }

    @Override
    public GiftCodeMaster editGiftCodeMaster(String giftCodeId) {
        GiftCodeMaster giftCodeMaster = new GiftCodeMaster();
        try {
            Optional<GiftCodeMaster> promoCodeMaster1 = giftCodeDao.findById(giftCodeId);
            promoCodeMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, giftCodeMaster));
            return giftCodeMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return giftCodeMaster;
        }
    }

    @Override
    public List getGiftCodeMasterByStatus(String status) {

        List list=new ArrayList();
        list=giftCodeDao.findAllByStatus(status);
        return list;
    }

    @Override
    public Boolean deleteGiftCodeByGiftCodeId(String giftCodeId) {

        try {
            giftCodeDao.deleteById(giftCodeId);
         return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
