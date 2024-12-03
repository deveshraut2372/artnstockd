package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.BannerDao;
import com.zplus.ArtnStockMongoDB.dto.req.BannerReqDto;
import com.zplus.ArtnStockMongoDB.model.BannerMaster;
import com.zplus.ArtnStockMongoDB.service.BannerMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BannerMasterServiceImpl implements BannerMasterService {

    @Autowired
    private BannerDao bannerDao;

    @Override
    public Boolean createBannerMaster(BannerReqDto bannerReqDto) {
        BannerMaster bannerMaster = new BannerMaster();
        BeanUtils.copyProperties(bannerReqDto, bannerMaster);
        try {
            bannerDao.save(bannerMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateBannerMaster(BannerReqDto bannerReqDto) {
        BannerMaster bannerMaster = new BannerMaster();
        bannerMaster.setBannerId(bannerReqDto.getBannerId());
        BeanUtils.copyProperties(bannerReqDto, bannerMaster);
        try {
            bannerDao.save(bannerMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllBannerMaster() {
        List<BannerMaster> bannerMasterList = bannerDao.findAll();
        return bannerMasterList;
    }

    @Override
    public BannerMaster editBannerMaster(String bannerId) {
        BannerMaster bannerMaster=new BannerMaster();
        try {
            Optional<BannerMaster> bannerMaster1=bannerDao.findById(bannerId);
            bannerMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, bannerMaster));
            return bannerMaster;
        }
        catch (Exception e) {
            e.printStackTrace();
            return bannerMaster;
        }
    }
    @Override
    public List getActiveBannerMaster() {
        return bannerDao.findAllByStatus("Active");

    }
    @Override
    public List getBannerTypeWiseList(String bannerType) {
        return bannerDao.findByBannerType(bannerType);
    }


}
