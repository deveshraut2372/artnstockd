package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.FrameMatMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.FrameMatMasterReqDto;
import com.zplus.ArtnStockMongoDB.dto.res.FrameMatMasterResDto;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.FrameMatMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FrameMatMasterServiceImpl implements FrameMatMasterService {

    @Autowired
    private FrameMatMasterDao frameMatMasterDao;

    @Override
    public Boolean createFrameMatMaster(FrameMatMasterReqDto frameMatMasterReqDto) {
        FrameMatMaster frameMatMaster = new FrameMatMaster();

        UserMaster userMaster=new UserMaster();
        userMaster.setUserId(frameMatMasterReqDto.getUserId());
        frameMatMaster.setUserMaster(userMaster);

        ArtMaster artMaster=new ArtMaster();
        artMaster.setArtId(frameMatMasterReqDto.getArtId());
        frameMatMaster.setArtMaster(artMaster);

        BeanUtils.copyProperties(frameMatMasterReqDto, frameMatMaster);
        try {
            frameMatMasterDao.save(frameMatMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Boolean updateFrameMatMaster(FrameMatMasterReqDto frameMatMasterReqDto) {
        FrameMatMaster frameMatMaster = new FrameMatMaster();

        UserMaster userMaster=new UserMaster();
        userMaster.setUserId(frameMatMasterReqDto.getUserId());
        frameMatMaster.setUserMaster(userMaster);

        ArtMaster artMaster=new ArtMaster();
        artMaster.setArtId(frameMatMasterReqDto.getArtId());
        frameMatMaster.setArtMaster(artMaster);

        frameMatMaster.setFrameMatId(frameMatMasterReqDto.getFrameMatId());
        BeanUtils.copyProperties(frameMatMasterReqDto, frameMatMaster);
        try {
            frameMatMasterDao.save(frameMatMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FrameMatMaster> getAllFrameMatMaster() {

       List<FrameMatMaster> list=frameMatMasterDao.findAll();
        System.out.println("list.."+list.toString());
       return list;
    }

    @Override
    public FrameMatMaster editFrameMatMaster(String frameMatId) {
        FrameMatMaster frameMatMaster = new FrameMatMaster();
        try {
            Optional<FrameMatMaster> frameMatMaster1 = frameMatMasterDao.findById(frameMatId);
            frameMatMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, frameMatMaster));
            return frameMatMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return frameMatMaster;
        }
    }

    @Override
    public List getActiveFrameMatMaster() {
        return frameMatMasterDao.findByStatus("Active");
    }
}
