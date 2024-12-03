package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.MainCategoryDao;
import com.zplus.ArtnStockMongoDB.dto.req.MainCategoryReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.UpdateMainCategoryReqDto;
import com.zplus.ArtnStockMongoDB.model.MainCategoryMaster;
import com.zplus.ArtnStockMongoDB.service.MainCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MainCategoryServiceImpl implements MainCategoryService {

    @Autowired
    private MainCategoryDao mainCategoryDao;

    @Override
    public Boolean createMainCategoryMaster(MainCategoryReqDto mainCategoryReqDto) {
        MainCategoryMaster mainCategoryMaster = new MainCategoryMaster();

        BeanUtils.copyProperties(mainCategoryReqDto, mainCategoryMaster);
        try {
            mainCategoryDao.save(mainCategoryMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateMainCategoryMaster(UpdateMainCategoryReqDto updateMainCategoryReqDto) {
        MainCategoryMaster mainCategoryMaster = new MainCategoryMaster();

        mainCategoryMaster.setMainCategoryId(updateMainCategoryReqDto.getMainCategoryId());
//        BeanUtils.copyProperties(updateMainCategoryReqDto, mainCategoryMaster);

        mainCategoryMaster.setMainCategoryName(updateMainCategoryReqDto.getMainCategoryName());
        mainCategoryMaster.setLogo(updateMainCategoryReqDto.getLogo());
        mainCategoryMaster.setStatus(updateMainCategoryReqDto.getStatus());
        mainCategoryMaster.setMainCategoryId(updateMainCategoryReqDto.getMainCategoryId());

        try {
            mainCategoryDao.save(mainCategoryMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllMainCategoryMaster() {
        return mainCategoryDao.findAll();

    }

    @Override
    public MainCategoryMaster editMainCategoryMaster(String mainCategoryId) {
        MainCategoryMaster mainCategoryMaster = new MainCategoryMaster();
        try {
            Optional<MainCategoryMaster> mainCategoryMaster1 = mainCategoryDao.findById(mainCategoryId);
            mainCategoryMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, mainCategoryMaster));
            return mainCategoryMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return mainCategoryMaster;
        }
    }

    @Override
    public List getActiveMainCategory() {
        List<MainCategoryMaster> categoryMasterList = mainCategoryDao.findAllByStatus("Active");
        return categoryMasterList;

    }
}
