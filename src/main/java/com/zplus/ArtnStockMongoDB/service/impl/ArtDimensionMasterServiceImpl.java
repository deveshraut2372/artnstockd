package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.ArtDimensionDao;
import com.zplus.ArtnStockMongoDB.dto.req.ArtDimensionMasterReqDto;
import com.zplus.ArtnStockMongoDB.model.ArtDimensionMaster;
import com.zplus.ArtnStockMongoDB.service.ArtDimensionMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtDimensionMasterServiceImpl implements ArtDimensionMasterService {

    @Autowired
    private ArtDimensionDao artDimensionDao;

    @Override
    public Boolean createArtDimensionMasterMaster(ArtDimensionMasterReqDto artDimensionMasterReqDto) {
        ArtDimensionMaster artDimensionMaster = new ArtDimensionMaster();

        artDimensionMaster.setHeightInInches(artDimensionMaster.getHeightInInches());
        BeanUtils.copyProperties(artDimensionMasterReqDto, artDimensionMaster);
        try {
            ArtDimensionMaster artDimensionMaster1=artDimensionDao.save(artDimensionMaster);
            System.out.println("artDimensionMaster1"+artDimensionMaster1.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateArtDimensionMasterMaster(ArtDimensionMasterReqDto artDimensionMasterReqDto) {
        ArtDimensionMaster artDimensionMaster = new ArtDimensionMaster();

        artDimensionMaster.setArtDimensionId(artDimensionMasterReqDto.getArtDimensionId());
        BeanUtils.copyProperties(artDimensionMasterReqDto, artDimensionMaster);
        try {
            artDimensionDao.save(artDimensionMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllArtDimensionMaster() {
        return  artDimensionDao.findAll();
    }

    @Override
    public ArtDimensionMaster editArtDimensionMaster(String artDimensionId) {
        ArtDimensionMaster artDimensionMaster = new ArtDimensionMaster();
        try {
            Optional<ArtDimensionMaster> artDimensionMaster1 = artDimensionDao.findById(artDimensionId);
            artDimensionMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, artDimensionMaster));
            return artDimensionMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return artDimensionMaster;
        }
    }

    @Override
    public List getActiveArtDimensionMaster() {
        List list = artDimensionDao.findAllByStatus("Active");
        return list;
    }
}
