package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.FileUploadLimitPerWeekDao;
import com.zplus.ArtnStockMongoDB.dto.req.FileUploadLimitPerWeekReqDto;
import com.zplus.ArtnStockMongoDB.model.FileUploadLimitPerWeekMaster;
import com.zplus.ArtnStockMongoDB.service.FileUploadLimitPerWeekService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FileUploadLimitPerWeekServiceImpl implements FileUploadLimitPerWeekService {

    @Autowired
    private FileUploadLimitPerWeekDao fileUploadLimitPerWeekDao;

    @Override
    public Boolean saveFileUploadLimitPerWeekMaster(FileUploadLimitPerWeekReqDto fileUploadLimitPerWeekReqDto) {
        FileUploadLimitPerWeekMaster fileUploadLimitPerWeekMaster = new FileUploadLimitPerWeekMaster();

        BeanUtils.copyProperties(fileUploadLimitPerWeekReqDto, fileUploadLimitPerWeekMaster);
        try {
            fileUploadLimitPerWeekDao.save(fileUploadLimitPerWeekMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateFrequentlyAskedQuestions(FileUploadLimitPerWeekReqDto fileUploadLimitPerWeekReqDto) {
        FileUploadLimitPerWeekMaster fileUploadLimitPerWeekMaster = new FileUploadLimitPerWeekMaster();

        fileUploadLimitPerWeekMaster.setFileUploadLimitPerWeekId(fileUploadLimitPerWeekReqDto.getFileUploadLimitPerWeekId());
        BeanUtils.copyProperties(fileUploadLimitPerWeekReqDto, fileUploadLimitPerWeekMaster);
        try {
            fileUploadLimitPerWeekDao.save(fileUploadLimitPerWeekMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FileUploadLimitPerWeekMaster> getAllListFileUploadLimitPerWeekMaster() {
        return fileUploadLimitPerWeekDao.findAll();
    }

    @Override
    public FileUploadLimitPerWeekMaster getByFileUploadLimitPerWeekId(String fileUploadLimitPerWeekId) {
        FileUploadLimitPerWeekMaster fileUploadLimitPerWeekMaster = new FileUploadLimitPerWeekMaster();
        try {
            Optional<FileUploadLimitPerWeekMaster> fileUploadLimitPerWeekMaster1 = fileUploadLimitPerWeekDao.findById(fileUploadLimitPerWeekId);
            fileUploadLimitPerWeekMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, fileUploadLimitPerWeekMaster));
            return fileUploadLimitPerWeekMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return fileUploadLimitPerWeekMaster;
        }
    }
}
