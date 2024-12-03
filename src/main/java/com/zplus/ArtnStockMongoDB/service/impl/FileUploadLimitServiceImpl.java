package com.zplus.ArtnStockMongoDB.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.zplus.ArtnStockMongoDB.dao.ArtMasterDao;
import com.zplus.ArtnStockMongoDB.dao.FileUploadLimitRepository;
import com.zplus.ArtnStockMongoDB.dao.UserDao;
import com.zplus.ArtnStockMongoDB.dto.res.ApprovedRatioRes;
import com.zplus.ArtnStockMongoDB.model.AdminArtProductMaster;
import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.FileUploadLimitMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import com.zplus.ArtnStockMongoDB.service.FileUploadLimitService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileUploadLimitServiceImpl implements FileUploadLimitService {

    @Autowired
    FileUploadLimitRepository fileUploadLimitRepository;

    @Autowired
    UserDao userDao;

    @Autowired
    private ArtMasterDao artMasterDao;



    @Override
    public Boolean CalculateApprovedPercentage() {

        try {
            List<UserMaster> contributorList = new ArrayList<>();
            contributorList = userDao.findAll().stream().filter(userMaster -> userMaster.getUserRole().contains("contributor")).collect(Collectors.toList());
            System.out.println("  contributorList size =" + contributorList.size());
            contributorList.stream().forEach(userMaster -> {

                List<ArtMaster> artApprovedMasterList = new ArrayList<>();
                List<ArtMaster> artMasterList = new ArrayList<>();

                artApprovedMasterList = artMasterDao.findAllByStatusAndUserMaster("Approved", userMaster);
                artMasterList = artMasterDao.findByUserMasterUserId(userMaster.getUserId());


                System.out.println(" artApprovedMasterList size " + artApprovedMasterList.size());
                System.out.println(" total art size " + artMasterList.size());

                Integer totalCount = artMasterList.size();

                System.out.println("  User Master =="+userMaster.getUserId());
                FileUploadLimitMaster fileUploadLimitMaster = new FileUploadLimitMaster();
                fileUploadLimitMaster = fileUploadLimitRepository.findByUserMaster(userMaster);
                if (fileUploadLimitMaster == null) {
                    FileUploadLimitMaster fileUploadLimitMaster2=new FileUploadLimitMaster();
                    fileUploadLimitMaster2.setUserMaster(userMaster);
                    FileUploadLimitMaster fileUploadLimitMaster1 = fileUploadLimitRepository.save(fileUploadLimitMaster2);
                    BeanUtils.copyProperties(fileUploadLimitMaster1,fileUploadLimitMaster);
                }

                if (artApprovedMasterList.size() != 0) {
                    Double approvedPercentage = (double) ((artApprovedMasterList.size()) * 100 / totalCount);
                    System.out.println("  approved percentage =" + approvedPercentage + "  user name =" + userMaster.getUserFirstName());
                    fileUploadLimitMaster.setUserMaster(userMaster);
                    fileUploadLimitMaster.setArtCount(totalCount);
                    fileUploadLimitMaster = fileUploadLimitRepository.findByUserMaster(userMaster);
                    if (approvedPercentage > 0 && approvedPercentage <= 20) {
                        fileUploadLimitMaster.setLevel(1);
                        fileUploadLimitMaster.setUserMaster(userMaster);
                        fileUploadLimitMaster.setArtCount(totalCount);
                        fileUploadLimitMaster.setApprovedPercentage(approvedPercentage);
                        fileUploadLimitRepository.save(fileUploadLimitMaster);

                    } else if (approvedPercentage > 20 && approvedPercentage <= 40) {
                        fileUploadLimitMaster.setLevel(2);
                        fileUploadLimitMaster.setArtCount(totalCount);
                        fileUploadLimitMaster.setUserMaster(userMaster);
                        fileUploadLimitMaster.setApprovedPercentage(approvedPercentage);
                        fileUploadLimitRepository.save(fileUploadLimitMaster);
                    } else if (approvedPercentage > 40 && approvedPercentage <= 60) {
                        fileUploadLimitMaster.setLevel(3);
                        fileUploadLimitMaster.setArtCount(totalCount);
                        fileUploadLimitMaster.setUserMaster(userMaster);
                        fileUploadLimitMaster.setApprovedPercentage(approvedPercentage);
                        fileUploadLimitRepository.save(fileUploadLimitMaster);
                    } else if (approvedPercentage > 60 && approvedPercentage <= 80) {
                        fileUploadLimitMaster.setLevel(4);
                        fileUploadLimitMaster.setArtCount(totalCount);
                        fileUploadLimitMaster.setUserMaster(userMaster);
                        fileUploadLimitMaster.setApprovedPercentage(approvedPercentage);
                        fileUploadLimitRepository.save(fileUploadLimitMaster);
                    } else if (approvedPercentage > 80 && approvedPercentage <= 100) {
                        fileUploadLimitMaster.setLevel(5);
                        fileUploadLimitMaster.setArtCount(totalCount);
                        fileUploadLimitMaster.setUserMaster(userMaster);
                        fileUploadLimitMaster.setApprovedPercentage(approvedPercentage);
                        fileUploadLimitRepository.save(fileUploadLimitMaster);
                    }
                } else {
                    fileUploadLimitMaster.setLevel(1);
                    fileUploadLimitMaster.setArtCount(totalCount);
                    fileUploadLimitMaster.setUserMaster(userMaster);
                    fileUploadLimitMaster.setApprovedPercentage(0.0);
                    fileUploadLimitRepository.save(fileUploadLimitMaster);
                    System.out.println(" user name =" + userMaster.getUserFirstName());
                }
            });
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FileUploadLimitMaster> getAllFileUploadLimitMaster() {
        return fileUploadLimitRepository.findAll();
    }

    @Override
    public ApprovedRatioRes getUserIdWiseRatio(String userId) {

        FileUploadLimitMaster fileUploadLimitMaster=fileUploadLimitRepository.findByUserMaster_UserId(userId);
        ApprovedRatioRes approvedRatioRes=new ApprovedRatioRes();
        approvedRatioRes.setArtCount(fileUploadLimitMaster.getArtCount());
        
        switch (fileUploadLimitMaster.getLevel())
        {
            case 1:
                approvedRatioRes.setUploadedCount(50);
                break;
            case 2:
                approvedRatioRes.setUploadedCount(100);
                break;
            case 3:
                approvedRatioRes.setUploadedCount(150);
                break;
            case 4:
                approvedRatioRes.setUploadedCount(200);
                break;
            case 5:
                approvedRatioRes.setUploadedCount(250);
                break;
        }
        return approvedRatioRes;

    }

    @Override
    public FileUploadLimitMaster createFileUploadLimit( String userId  )
    {
        FileUploadLimitMaster fileUploadLimitMaster=new FileUploadLimitMaster();
        UserMaster userMaster=userDao.findById(userId).get();
        fileUploadLimitMaster.setUserMaster(userMaster);
        fileUploadLimitMaster.setLevel(1);
        fileUploadLimitMaster.setApprovedPercentage(0.0);
        FileUploadLimitMaster fileUploadLimitMaster1=fileUploadLimitRepository.save(fileUploadLimitMaster);
        return fileUploadLimitMaster1;
    }
}
