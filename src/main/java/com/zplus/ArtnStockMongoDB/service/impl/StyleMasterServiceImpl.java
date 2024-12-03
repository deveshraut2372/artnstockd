package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.RecentlyViewDao;
import com.zplus.ArtnStockMongoDB.dao.StyleMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.StyleReqDto;
import com.zplus.ArtnStockMongoDB.model.StyleMaster;
import com.zplus.ArtnStockMongoDB.service.StyleMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StyleMasterServiceImpl implements StyleMasterService {
    @Autowired
    private StyleMasterDao styleMasterDao;

    @Autowired
    private RecentlyViewDao recentlyViewDao;

    @Override
    public Boolean createStyleMaster(StyleReqDto styleReqDto) {
        StyleMaster styleMaster = new StyleMaster();
        styleMaster.setStatus("Active");
        BeanUtils.copyProperties(styleReqDto, styleMaster);
        try {
            styleMasterDao.save(styleMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateStyleMaster(StyleReqDto styleReqDto) {
        StyleMaster styleMaster = new StyleMaster();
        BeanUtils.copyProperties(styleReqDto, styleMaster);
        try {
            styleMasterDao.save(styleMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllStyleMaster() {
        List<StyleMaster> styleMasters = styleMasterDao.findAll();
        return styleMasters;
    }

    @Override
    public StyleMaster editStyleMaster(String styleId) {
        StyleMaster styleMaster = new StyleMaster();
        try {
            Optional<StyleMaster> styleMasterOptional = styleMasterDao.findById(styleId);
            styleMasterOptional.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, styleMaster));
            return styleMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return styleMaster;
        }
    }
    @Override
    public List getActiveStyleMaster() {
        return styleMasterDao.findAllByStatus("Active");
    }
    @Override
    public List getArtDropdownTrue() {
        List<StyleMaster> styleMasterList = styleMasterDao.findByArtDropdown(true);
        System.out.println("styleMasterList" + styleMasterList.size());
        return styleMasterList;
    }

    @Override
    public Boolean deleteByStyleId(String styleId) {
        try {
            styleMasterDao.deleteById(styleId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}




