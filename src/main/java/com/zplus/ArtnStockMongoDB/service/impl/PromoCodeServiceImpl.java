package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.configuration.GiftAndPromoCode;
import com.zplus.ArtnStockMongoDB.dao.PromoCodeDao;
import com.zplus.ArtnStockMongoDB.dto.req.PromoCodeRequest;
import com.zplus.ArtnStockMongoDB.model.AdminMaster;
import com.zplus.ArtnStockMongoDB.model.PromoCodeMaster;
import com.zplus.ArtnStockMongoDB.service.PromoCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromoCodeServiceImpl implements PromoCodeService {

    @Autowired
    private PromoCodeDao promoCodeDao;

    @Override
    public Boolean createPromoCodeMaster(PromoCodeRequest promoCodeRequest) {
        Boolean flag=false;
        PromoCodeMaster promoCodeMaster=new PromoCodeMaster();
        promoCodeMaster.setPromoCode(GiftAndPromoCode.generatePromoCode());
        promoCodeMaster.setStatus("Active");
        BeanUtils.copyProperties(promoCodeRequest,promoCodeMaster);
        try {
            promoCodeDao.save(promoCodeMaster);
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }

    @Override
    public Boolean updatePromoCodeMaster(PromoCodeRequest promoCodeRequest) {
        Boolean flag=false;
        PromoCodeMaster promoCodeMaster=new PromoCodeMaster();
        promoCodeMaster.setPromoCodeId(promoCodeRequest.getPromoCodeId());
        PromoCodeMaster promoCodeMaster1=new PromoCodeMaster();
        promoCodeMaster1=promoCodeDao.findById(promoCodeRequest.getPromoCodeId()).get();
        BeanUtils.copyProperties(promoCodeRequest,promoCodeMaster);
        promoCodeMaster.setPromoCode(promoCodeMaster1.getPromoCode());

        try {
            promoCodeDao.save(promoCodeMaster);
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }

    @Override
    public List getAllPromoCodeMaster() {
        List list=promoCodeDao.findAll();
        return list;
    }

    @Override
    public PromoCodeMaster editPromoCodeMaster(String promoCodeId) {
        PromoCodeMaster promoCodeMaster = new PromoCodeMaster();
        try {
            Optional<PromoCodeMaster> promoCodeMaster1 = promoCodeDao.findById(promoCodeId);
            promoCodeMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, promoCodeMaster));
            return promoCodeMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return promoCodeMaster;
        }
    }

    @Override
    public List getActivePromoCodeMaster() {
        List list=promoCodeDao.findByStatus("Active");
        return list;
    }

    @Override
    public Boolean deletePromoCodeMaster(String promoCodeId) {
        try {
            promoCodeDao.deleteById(promoCodeId);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
