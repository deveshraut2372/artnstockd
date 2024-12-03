package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.*;
import com.zplus.ArtnStockMongoDB.dto.req.AddDetailsGetReq;
import com.zplus.ArtnStockMongoDB.dto.req.AddDetailsMasterReq;
import com.zplus.ArtnStockMongoDB.dto.req.DeleteAddDetailsreq;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.AddDetailsMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AddDetailsMasterServiceImpl implements AddDetailsMasterService {

    @Autowired
    private ArtDetailsMasterDao artDetailsMasterDao;

    @Autowired
    private StyleMasterDao styleMasterDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private MediumMasterDao mediumMasterDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ImageMasterDao imageMasterDao;

    @Autowired
    private  DraftMasterDao draftMasterDao;



    @Override
    public Boolean createArtDetails(AddDetailsMasterReq addDetailsMasterReq) {

        AddDetailsMaster addDetailsMaster =new AddDetailsMaster();
        StyleMaster styleMaster=new StyleMaster();
        SubjectMaster subjectMaster=new SubjectMaster();
        UserMaster userMaster=new UserMaster();
        ImageMaster imageMaster=new ImageMaster();
        DraftMaster draftMaster=new DraftMaster();
        MediumMaster mediumMaster=new MediumMaster();
        try {
//            styleMaster = styleMasterDao.findById(addDetailsMasterReq.getStyleId()).orElse(null);
//            subjectMaster = subjectDao.findById(addDetailsMasterReq.getSubjectId()).orElse(null);

            if(addDetailsMasterReq.getStyleId()==null)
            {
                styleMaster=null;
            }else {
                styleMaster = styleMasterDao.findById(addDetailsMasterReq.getStyleId()).orElse(null);
            }
            if(addDetailsMasterReq.getSubjectId()==null)
            {
                subjectMaster=null;
            }else {
                subjectMaster = subjectDao.findById(addDetailsMasterReq.getSubjectId()).orElse(null);
            }

//            if(addDetailsMasterReq.getMediumId()==null)
//            {
//                mediumMaster=null;
//            }else {
//                mediumMaster = mediumMasterDao.findById(addDetailsMasterReq.getMediumId()).orElse(null);
//            }

            userMaster = userDao.getId(addDetailsMasterReq.getUserId());
            imageMaster = imageMasterDao.getImage(addDetailsMasterReq.getImageId());
            draftMaster=draftMasterDao.findByUserMaster_UserId(addDetailsMasterReq.getUserId());

            // new change
            imageMaster.setImageOrientation(addDetailsMasterReq.getImageOrientation());
            imageMaster.setZoom(addDetailsMasterReq.getZoom());
            imageMaster.setXAxis(addDetailsMasterReq.getXAxis());
            imageMaster.setYAxis(addDetailsMasterReq.getYAxis());
            imageMaster.setPreviews(addDetailsMasterReq.getPreviews());
            imageMaster.setDate(new Date());
            imageMasterDao.save(imageMaster);
            //
            BeanUtils.copyProperties(addDetailsMasterReq, addDetailsMaster);
            addDetailsMaster.setImageMaster(imageMaster);
            addDetailsMaster.setUserMaster(userMaster);
            addDetailsMaster.setStyleMaster(styleMaster);
            addDetailsMaster.setSubjectMaster(subjectMaster);
            addDetailsMaster.setMediumMaster(mediumMaster);
            addDetailsMaster.setPreviews(addDetailsMasterReq.getPreviews());
            //            artDetailsMaster.setDraftMaster(draftMaster);
            artDetailsMasterDao.save(addDetailsMaster);

            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateArtDetails(AddDetailsMasterReq addDetailsMasterReq) {

        AddDetailsMaster addDetailsMaster =new AddDetailsMaster();
        StyleMaster styleMaster=new StyleMaster();
        SubjectMaster subjectMaster=new SubjectMaster();
        MediumMaster  mediumMaster=new MediumMaster();

        UserMaster userMaster=new UserMaster();
        ImageMaster imageMaster=new ImageMaster();
        DraftMaster draftMaster=new DraftMaster();

        try {

            if(addDetailsMasterReq.getStyleId()==null)
            {
                styleMaster=null;
            }else {
                styleMaster = styleMasterDao.findById(addDetailsMasterReq.getStyleId()).orElse(null);
            }
            if(addDetailsMasterReq.getSubjectId()==null)
            {
                subjectMaster=null;
            }else {
                subjectMaster = subjectDao.findById(addDetailsMasterReq.getSubjectId()).orElse(null);
            }

            if(addDetailsMasterReq.getMediumId()==null)
            {
                mediumMaster=null;
            }else {
                mediumMaster = mediumMasterDao.findById(addDetailsMasterReq.getMediumId()).orElse(null);
            }

            userMaster = userDao.getId(addDetailsMasterReq.getUserId());
            imageMaster = imageMasterDao.getImage(addDetailsMasterReq.getImageId());
            draftMaster=draftMasterDao.findByUserMaster_UserId(addDetailsMasterReq.getUserId());

            // new change
            imageMaster.setImageOrientation(addDetailsMasterReq.getImageOrientation());
            imageMaster.setZoom(addDetailsMasterReq.getZoom());
            imageMaster.setXAxis(addDetailsMasterReq.getXAxis());
            imageMaster.setYAxis(addDetailsMasterReq.getYAxis());
            imageMaster.setDate(imageMaster.getDate());
           ImageMaster imageMaster1= imageMasterDao.save(imageMaster);
            //

            BeanUtils.copyProperties(addDetailsMasterReq, addDetailsMaster);
            addDetailsMaster.setImageMaster(imageMaster);
            addDetailsMaster.setUserMaster(userMaster);
            addDetailsMaster.setStyleMaster(styleMaster);
            addDetailsMaster.setSubjectMaster(subjectMaster);
            addDetailsMaster.setMediumMaster(mediumMaster);
            addDetailsMaster.setArtDetailsId(addDetailsMasterReq.getArtDetailsId());
//            artDetailsMaster.setDraftMaster(draftMaster);
            artDetailsMasterDao.save(addDetailsMaster);


            // changes dfrat also update
            List<ImageMaster> imageMasters=draftMaster.getImageMaster();
            imageMasters.removeIf(image -> image.getImageId().equalsIgnoreCase(addDetailsMasterReq.getImageId()));
            imageMasters.add(imageMaster1);
            draftMaster.setImageMaster(imageMasters);
            draftMaster.setDate(draftMaster.getDate());
            draftMasterDao.save(draftMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public AddDetailsMaster getArtDetails(AddDetailsGetReq addDetailsGetReq) {

        System.out.println(" addDetailsGetReq = "+addDetailsGetReq.toString());
        AddDetailsMaster addDetailsMaster =new AddDetailsMaster();
        DraftMaster draftMaster=new DraftMaster();
        draftMaster=draftMasterDao.findByUserMaster_UserId(addDetailsGetReq.getUserId());
        UserMaster userMaster=new UserMaster();
        userMaster=userDao.getId(addDetailsGetReq.getUserId());

        ImageMaster imageMaster=imageMasterDao.getImage(addDetailsGetReq.getImageId());

        addDetailsMaster =artDetailsMasterDao.findByImageMaster(imageMaster);
        return addDetailsMaster;
    }

    @Override
    public Boolean checkExistOrNot(AddDetailsGetReq addDetailsGetReq) {

        AddDetailsMaster addDetailsMaster =new AddDetailsMaster();
        DraftMaster draftMaster=new DraftMaster();
        draftMaster=draftMasterDao.findByUserMaster_UserId(addDetailsGetReq.getUserId());
        UserMaster userMaster=new UserMaster();
        userMaster=userDao.getId(addDetailsGetReq.getUserId());

        if(imageMasterDao.existsById(addDetailsGetReq.getImageId()))
        {
            ImageMaster imageMaster=imageMasterDao.getImage(addDetailsGetReq.getImageId());

            if(artDetailsMasterDao.existsByImageMaster_ImageId(addDetailsGetReq.getImageId()))
            {
                return true;
            }else {
                System.out.println("  artdetails image is not found ");
                return false;
            }
        }else {
            System.out.println("  image is not found ");
            return false;
        }

//        65d8af04d605b8786dde97ad

//        System.out.println(" imageMaster ="+imageMaster.toString());
//        addDetailsMaster =artDetailsMasterDao.findByImageMaster(imageMaster);
//        if(addDetailsMaster==null)
//        {
//            return false;
//        }else {
//            return true;
//        }


    }

    @Override
    public List<AddDetailsMaster> getAllAddDetails() {
        List<AddDetailsMaster> addDetailsMasterList=new ArrayList<>();
        addDetailsMasterList=artDetailsMasterDao.findAll();
        return addDetailsMasterList;
    }

    @Override
    public Boolean deleteAddDetails(DeleteAddDetailsreq deleteAddDetailsreq) {

        DraftMaster draftMaster=new DraftMaster();


        return null;
    }
}
