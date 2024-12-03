package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.configuration.UniqueNumber;
import com.zplus.ArtnStockMongoDB.dao.FrameDao;
import com.zplus.ArtnStockMongoDB.dto.req.FrameReqDto;
import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.FrameMaster;
import com.zplus.ArtnStockMongoDB.model.ProductMaster;
import com.zplus.ArtnStockMongoDB.service.FrameService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class FrameServiceImpl implements FrameService {

    @Autowired
    public FrameDao frameDao;

    @Override
    public Boolean createFrameMaster(FrameReqDto frameReqDto) {
        FrameMaster frameMaster1=new FrameMaster();
        Boolean flag=false;
        FrameMaster frameMaster = new FrameMaster();
//        frameMaster.setOrientationList(frameReqDto.getOrientation());
        frameMaster.setFrameMaterial(frameReqDto.getFrameMaterial());
        BeanUtils.copyProperties(frameReqDto,frameMaster);
        try {
          frameMaster1=frameDao.save(frameMaster);
            System.out.println("frameMaster1...."+frameMaster1.toString());
            flag= true;
        } catch (Exception e) {
            e.printStackTrace();
            flag= false;
        }
        String frameProductNo = UniqueNumber.generateUniqueNumber();
        System.out.println("frameProductNo: " + frameProductNo);
        try {
            Optional<FrameMaster> frameMasterOptional = frameDao.findByFrameId(frameMaster1.getFrameId());
            if (frameMasterOptional.isPresent()) {
                FrameMaster master = frameMasterOptional.get();
                master.setFrameProductNo(frameProductNo);
                FrameMaster frameMaster2 = frameDao.save(master);
                System.out.println("frameProductNo: " + frameMaster2.getFrameProductNo());
                flag = true;
            } else {
                flag = false;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public Boolean updateFrameMaster(FrameReqDto frameReqDto) {
        FrameMaster frameMaster = new FrameMaster();
        frameMaster.setFrameId(frameReqDto.getFrameId());
        BeanUtils.copyProperties(frameReqDto, frameMaster);
        try {
            frameDao.save(frameMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }    }

    @Override
    public List getAllFrameMaster() {
        return frameDao.findAll();
    }

    @Override
    public FrameMaster editProductTypeMaster(String frameId) {
        FrameMaster frameMaster = new FrameMaster();
        try {
            Optional<FrameMaster> frameMaster1 = frameDao.findById(frameId);
            frameMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, frameMaster));
            return frameMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return frameMaster;
        }
    }

    @Override
    public List getActiveFrameMaster() {
        List frameMasterList = frameDao.findByStatus();
        return frameMasterList;
    }
}
