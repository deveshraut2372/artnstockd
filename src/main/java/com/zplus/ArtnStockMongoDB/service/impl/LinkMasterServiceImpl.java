package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.SocialMediaAdminDao;
import com.zplus.ArtnStockMongoDB.dto.req.SocialMediaMasterReq;
import com.zplus.ArtnStockMongoDB.model.SocialMediaAdminMaster;
import com.zplus.ArtnStockMongoDB.service.SocialMediaMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
class SocialMediaMasterServiceImpl implements SocialMediaMasterService {



    @Autowired
    private SocialMediaAdminDao socialMediaAdminDao;


    @Override
    public Boolean createLinkMaster(SocialMediaMasterReq socialMediaMasterReq) {

        SocialMediaAdminMaster socialMediaAdminMaster=new SocialMediaAdminMaster();
        BeanUtils.copyProperties(socialMediaMasterReq,socialMediaAdminMaster);
        socialMediaAdminMaster.setDate(new Date());
        socialMediaAdminMaster.setStatus("Active");
        try {
            socialMediaAdminDao.save(socialMediaAdminMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateLinkMaster(SocialMediaMasterReq socialMediaMasterReq) {
        SocialMediaAdminMaster socialMediaAdminMaster=new SocialMediaAdminMaster();
        BeanUtils.copyProperties(socialMediaMasterReq,socialMediaAdminMaster);
        socialMediaAdminMaster.setDate(new Date());
        socialMediaAdminMaster.setStatus(socialMediaMasterReq.getStatus());
        socialMediaAdminMaster.setSocialMediaAdminId(socialMediaMasterReq.getSocialMediaAdminId());
        try {
            socialMediaAdminDao.save(socialMediaAdminMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<SocialMediaAdminMaster> getActiveSocialMedia() {
        return socialMediaAdminDao.findAllByStatus("Active");
    }

    @Override
    public List<SocialMediaAdminMaster> getAllSocialMedia() {
        return socialMediaAdminDao.findAll();
    }

    @Override
    public Boolean deleteSocialMedia(String socialMediaAdminId) {
        try{
            socialMediaAdminDao.deleteById(socialMediaAdminId);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
