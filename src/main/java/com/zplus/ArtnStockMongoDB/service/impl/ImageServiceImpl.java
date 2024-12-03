package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.DateMasterDao;
import com.zplus.ArtnStockMongoDB.dao.DraftMasterDao;
import com.zplus.ArtnStockMongoDB.dao.ImageMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.DraftDeleteImageReq;
import com.zplus.ArtnStockMongoDB.dto.req.DraftUpdateImageReq;
import com.zplus.ArtnStockMongoDB.dto.req.ImageMasterIds;
import com.zplus.ArtnStockMongoDB.dto.req.UpdateImageReq;
import com.zplus.ArtnStockMongoDB.model.DraftMaster;
import com.zplus.ArtnStockMongoDB.model.ImageMaster;
import com.zplus.ArtnStockMongoDB.model.ImageOrientation;
import com.zplus.ArtnStockMongoDB.service.ImageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {


    @Autowired
    private ImageMasterDao imageMasterDao;

    @Autowired
    private DraftMasterDao draftMasterDao;


    @Override
    public Boolean updateImageMaster(DraftUpdateImageReq draftUpdateImageReq) {

        try {
        System.out.println("  status ="+ draftUpdateImageReq.getStatus());
        System.out.println("  active status ="+ draftUpdateImageReq.getActiveStatus());

        DraftMaster draftMaster=new DraftMaster();
        draftMaster=draftMasterDao.findById(draftUpdateImageReq.getDraftId()).get();
            System.out.println("  draft Master image ="+draftMaster.getImageMaster().stream().map(draftMaster1-> draftMaster1.getImageId().toString()+" status  "+draftMaster1.getStatus().toString()+" Active status "+draftMaster1.getActiveStatus().toString()));
        List<ImageMaster> imageMasterList11 = draftMaster.getImageMaster();
        List<ImageMaster> imageMasterList12 = draftUpdateImageReq.getImageMasters();

        // Compare and update the status
        List<ImageMaster> imageMasterModify = imageMasterList11.stream()
                .map(imageMaster11 -> {
                    if (imageMasterList12.stream().anyMatch(imageMaster12 -> imageMaster12.getImageId().equalsIgnoreCase(imageMaster11.getImageId()))) {
                        imageMaster11.setStatus(draftUpdateImageReq.getStatus());
                        imageMaster11.setActiveStatus(draftUpdateImageReq.getActiveStatus());
                        imageMaster11.setDate(new Date());
                        System.out.println("  imageMaster ="+imageMaster11.toString());
                        // Update the person's status in the database here
                        imageMasterDao.save(imageMaster11);
                    }
                    return imageMaster11;
                })
                .collect(Collectors.toList());

        draftMaster.setImageMaster(imageMasterModify);
            System.out.println(" After change  draft Master image ="+draftMaster.getImageMaster().stream().map(draftMaster1-> draftMaster1.getImageId().toString()+" status  "+draftMaster1.getStatus().toString()+" Active status "+draftMaster1.getActiveStatus().toString()));

           DraftMaster draftMaster2= draftMasterDao.save(draftMaster);

            System.out.println(" After change  draft Master image ="+draftMaster2.getImageMaster().stream().map(draftMaster1-> draftMaster1.getImageId().toString()+" status  "+draftMaster1.getStatus().toString()+" Active status "+draftMaster1.getActiveStatus().toString()));

            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

//
//        List<ImageMaster> imageMasterList=new ArrayList<>();


//            for (ImageMaster imageMaster1 : draftUpdateImageReq.getImageMasters()) {
//                draftMaster.getImageMaster().stream().map(imageMaster -> {
//                  if(imageMaster.getImageId().equalsIgnoreCase(imageMaster1.getImageId()))
//                  {
//                      imageMaster.setStatus(draftUpdateImageReq.getStatus());
//                      imageMaster.setActiveStatus(draftUpdateImageReq.getActiveStatus());
//                  }
//                  return imageMaster;
//              }).collect(Collectors.toList());
//
////                      filter(imageMaster -> imageMaster.getImageId().equalsIgnoreCase(imageMaster.getImageId())).
//
////                ImageMaster imageMaster = new ImageMaster();
//                imageMaster1.setStatus(draftUpdateImageReq.getStatus());
//                imageMaster1.setActiveStatus(draftUpdateImageReq.getActiveStatus());
//               ImageMaster imageMaster2= imageMasterDao.save(imageMaster1);
//            }



    }

    @Override
    public Boolean deleteImages(DraftDeleteImageReq draftDeleteImageReq) {

        System.out.println(" draftDeleteImageReq = "+draftDeleteImageReq.toString());

        try {
            DraftMaster draftMaster = new DraftMaster();
            draftMaster = draftMasterDao.findById(draftDeleteImageReq.getDraftId()).orElse(null);

            List<ImageMaster> imageMasterList11 = draftMaster.getImageMaster();
            List<ImageMasterIds> imageMasterList12 = draftDeleteImageReq.getImageMasterIds();

            Set<String> imageMasterDeleteList=imageMasterList12.stream().map(ImageMasterIds::getImageId).collect(Collectors.toSet());

            imageMasterList11.removeIf(imageMaster -> imageMasterDeleteList.contains(imageMaster.getImageId()));

            System.out.println("  imageMasterList11 size ="+imageMasterList11.size());

            List<ImageMaster> imageMasterModify=new ArrayList<>();

            List<ImageMaster> imageMasterList13=new ArrayList<>();

            System.out.println("  imageMasterList11 size =hhh "+imageMasterList13.size());

            imageMasterList13=imageMasterList11;

//            for (ImageMaster imageMaster : imageMasterList11) {
//                for (ImageMasterIds imageMasterIds : imageMasterList12) {
//                    if(!imageMasterIds.getImageId().equalsIgnoreCase(imageMaster.getImageId()))
//                    {
//                        imageMasterList13.add(imageMaster);
//                        break;
//                    }
//                }
//            }

            System.out.println("  imageMasterList11 size =hhh "+imageMasterList13.size());
            draftMaster.setImageMaster(imageMasterList13);


//            // Compare and update the status
//            List<ImageMaster> imageMasterModify = imageMasterList11.stream()
//                    .map(imageMaster11 -> {
//                        if (imageMasterList12.stream().anyMatch(imageMaster12 -> imageMaster12.getImageId().equalsIgnoreCase(imageMaster11.getImageId()))) {
//                            imageMasterList11.remove(imageMaster11);
////                            imageMasterDao.deleteById(imageMaster11.getImageId());
//                        }
//                        return imageMaster11;
//                    })
//                    .collect(Collectors.toList());

//            draftMaster.setImageMaster(imageMasterList11);
            DraftMaster draftMaster2 = draftMasterDao.save(draftMaster);

            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateImg(ImageMaster imageMaster) {

        System.out.println("  Image Master Request ="+imageMaster.toString());

        ImageMaster imageMaster1=new ImageMaster();
//        BeanUtils.copyProperties(imageMaster,imageMaster1);
        imageMaster1=imageMasterDao.findById(imageMaster.getImageId()).get();

        ImageOrientation imageOrientation=new ImageOrientation();
        imageOrientation.setSquareUrl(imageMaster.getImageOrientation().getSquareUrl());
        imageOrientation.setVerticalUrl(imageMaster.getImageOrientation().getVerticalUrl());
        imageOrientation.setHorizontalUrl(imageMaster.getImageOrientation().getHorizontalUrl());
        imageOrientation.setHorizontalBCUrl(imageMaster.getImageOrientation().getHorizontalBCUrl());
        imageOrientation.setSquareBCUrl(imageMaster.getImageOrientation().getSquareBCUrl());
        imageOrientation.setVerticalBCUrl(imageMaster.getImageOrientation().getVerticalBCUrl());
        imageMaster1.setImageOrientation(imageOrientation);
        imageMaster1.setXAxis(imageMaster.getXAxis());
        imageMaster1.setYAxis(imageMaster.getYAxis());
        imageMaster1.setZoom(imageMaster.getZoom());

        try
        {
         ImageMaster imageMaster2=   imageMasterDao.save(imageMaster1);
            System.out.println(" updated image ="+imageMaster2.toString());
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
