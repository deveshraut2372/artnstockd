package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.ArtDetailsMasterDao;
import com.zplus.ArtnStockMongoDB.dao.DraftMasterDao;
import com.zplus.ArtnStockMongoDB.dao.ImageMasterDao;
import com.zplus.ArtnStockMongoDB.dao.UserDao;
import com.zplus.ArtnStockMongoDB.dto.req.DeleteImageReq;
import com.zplus.ArtnStockMongoDB.dto.req.DraftMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.res.PreviewRes;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.DraftMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DraftMasterMasterServiceImpl implements DraftMasterService {

    @Autowired
    private DraftMasterDao draftMasterDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ImageMasterDao imageMasterDao;

    @Autowired
    private ArtDetailsMasterDao artDetailsMasterDao;


//    @Override
//    public Boolean createDraftMaster(DraftReq draftReq) {
//        Boolean flag = false;
//        UserMaster userMaster = userDao.getUserMaster(draftReq.getUserId());
//
//        Draft draft = draftDao.findByUserMaster_UserId(userMaster.getUserId());
//
//        if (draft == null) {
//            draft = new Draft();
//            draft.setDate(LocalDate.now());
//            draft.setUserMaster(userMaster);
//        } else {
//            draft.setDate(LocalDate.now());
//        }
//
//        List<ImageMaster> imageMasters = draft.getImageMaster();
//        if (imageMasters == null) {
//            imageMasters = new ArrayList<>();
//        }
//
//        if (draftReq.getImageId() != null && !draftReq.getImageId().isEmpty()) {
//            for (String id : draftReq.getImageId()) {
//                ImageMaster imageMaster = imageMasterDao.getImage(id);
//                if (imageMaster != null && !imageMasters.contains(imageMaster)) {
//                    imageMasters.add(imageMaster);
//                }
//            }
//        }
//        try {
//            draft.setImageMaster(imageMasters);
//            draftDao.save(draft);
//            flag = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            flag = false;
//        }
//
//        return flag;
//    }

    @Override
    public Boolean createDraftMaster(DraftMasterRequest draftMasterRequest) {
        Boolean flag=false;
        UserMaster userMaster = userDao.getUserMaster(draftMasterRequest.getUserId());
        DraftMaster draftMaster = draftMasterDao.findByUserMaster_UserId(userMaster.getUserId());

        if (draftMaster == null) {
            draftMaster = new DraftMaster();
            draftMaster.setDate(LocalDate.now());
            draftMaster.setUserMaster(userMaster);



        } else {
            draftMaster.setDate(LocalDate.now());
        }

        List<ImageMaster> imageMasters = draftMaster.getImageMaster();
        if (imageMasters == null) {
            imageMasters = new ArrayList<>();
        }

        if (draftMasterRequest.getImageId() != null && !draftMasterRequest.getImageId().isEmpty()) {
            for (String id : draftMasterRequest.getImageId()) {
                ImageMaster imageMaster = imageMasterDao.getImage(id);
                imageMaster.setDate(new Date());
                imageMaster.setStatus("submit");
                String value="";
                AddDetailsMaster addDetailsMaster=new AddDetailsMaster();
                addDetailsMaster.setImageMaster(imageMaster);
                addDetailsMaster.setUserMaster(userMaster);
                addDetailsMaster.setSubjectMaster(null);
                addDetailsMaster.setStyleMaster(null);
                addDetailsMaster.setMediumMaster(null);
                addDetailsMaster.setArtName(value);
//                addDetailsMaster.setArtMedium(value);
                addDetailsMaster.setMediumMaster(null);
                addDetailsMaster.setCommercialUser(new ArrayList<String>());
                addDetailsMaster.setTypeOfContent(new ArrayList<String>());
                addDetailsMaster.setReferenceFile(new ArrayList<String>());
                addDetailsMaster.setDescription(value);
                addDetailsMaster.setKeywords(new ArrayList<String>());
//                addDetailsMaster.setArtMedium(value);
                addDetailsMaster.setImage(value);
                addDetailsMaster.setReleases(new ArrayList<String>());
                addDetailsMaster.setPrice(0.0);
                addDetailsMaster.setNotes(value);

                Map<String,Object> preview=new HashMap<>();

                preview.put("horizontalPreview",new PreviewRes(1.0,0.0,0.0));
                preview.put("verticalPreview",new PreviewRes(1.0,0.0,0.0));
                preview.put("squarePreview",new PreviewRes(1.0,0.0,0.0));
                addDetailsMaster.setPreviews(preview);

                artDetailsMasterDao.save(addDetailsMaster);

                if (imageMaster != null && !containsImageId(imageMasters, id)) {
                    imageMasters.add(imageMaster);
                }
            }
        }

        try {

            draftMaster.setImageMaster(imageMasters);
            draftMasterDao.save(draftMaster);

            flag=true;
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }

    private boolean containsImageId(List<ImageMaster> imageMasters, String imageId) {
        for (ImageMaster imageMaster : imageMasters) {
            if (imageMaster.getImageId().equals(imageId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean updateDraftMaster(DraftMasterRequest draftMasterRequest) {
        Boolean flag = false;
        DraftMaster draftMaster = new DraftMaster();

        draftMaster.setDraftId(draftMasterRequest.getDraftId());
        BeanUtils.copyProperties(draftMasterRequest, draftMaster);
        try {
            draftMasterDao.save(draftMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public DraftMaster editDraftMaster(String draftId) {
        DraftMaster draftMaster = new DraftMaster();
        try {
            Optional<DraftMaster> draft1 = draftMasterDao.findById(draftId);
            draft1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, draftMaster));
            return draftMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return draftMaster;
        }
    }

    @Override
    public List getDraftMasterList() {
        return draftMasterDao.findAll();
    }

    @Override
    public List getContributorWiseDraftMasterList(String userId) {
        List list = new ArrayList();
        DraftMaster draftMaster = draftMasterDao.findByUserMasterUserId(userId);
        list.add(draftMaster);
        return list;
    }

    @Override
    public DraftMaster getDraftMasterByStatusAndActiveStatus(String status, Boolean activeStatus, String userId)
    {
        System.out.println("  status ="+status+"   id ="+userId+"  activeStatus = "+activeStatus);

        DraftMaster draftMaster=new DraftMaster();
        draftMaster=draftMasterDao.findByUserMaster_UserId(userId);

        List<ImageMaster> imageMasterList=new ArrayList<>();
        imageMasterList=draftMaster.getImageMaster();

        List<ImageMaster> imageMasterList1=new ArrayList<ImageMaster>();

        imageMasterList1=draftMaster.getImageMaster().stream().filter(imageMaster -> imageMaster.getStatus().equalsIgnoreCase(status) && imageMaster.getActiveStatus()==activeStatus ).collect(Collectors.toList());
        imageMasterList1.sort(Comparator.comparing(ImageMaster::getDate));

        System.out.println(" imageMasterList1 ="+imageMasterList1.toString());

//        imageMasterList1=draftMaster.getImageMaster().stream().sorted(Comparator.comparing(ImageMaster::getDate).reversed()).collect(Collectors.toList());

//        imageMasterList1=imageMasterList.stream().sorted(Comparator.comparing(ImageMaster::getDate).reversed()).collect(Collectors.toList());
//        imageMasterList1.stream().forEach(imageMaster -> System.out.println("  dates |"+imageMaster.getDate()));
        draftMaster.setImageMaster(imageMasterList1);
        return draftMaster;
    }


    @Override
    public Integer getDraftMasterByStatusAndActiveStatusCount(String status, Boolean activeStatus, String userId) {
        System.out.println("  status ="+status+"   id ="+userId+"  activeStatus = "+activeStatus);

        DraftMaster draftMaster=new DraftMaster();
        draftMaster=draftMasterDao.findByUserMaster_UserId(userId);

        List<ImageMaster> imageMasterList=new ArrayList<>();
        imageMasterList=draftMaster.getImageMaster();

        List<ImageMaster> imageMasterList1=new ArrayList<ImageMaster>();

        imageMasterList1=draftMaster.getImageMaster().parallelStream().filter(imageMaster -> imageMaster.getStatus().equalsIgnoreCase(status) && imageMaster.getActiveStatus()==activeStatus ).collect(Collectors.toList());
        System.out.println("imageMasterList1 ="+imageMasterList1.toString());


        List<ImageMaster> imageMasterList2=new ArrayList<>();
        if(!activeStatus)
        {
            imageMasterList2=draftMaster.getImageMaster().parallelStream().filter(imageMaster -> imageMaster.getStatus().equalsIgnoreCase(status) && imageMaster.getActiveStatus()==true ).collect(Collectors.toList());
        }

        System.out.println(" imageMasterList1.size() =="+imageMasterList1.size());
//        imageMasterList1=draftMaster.getImageMaster().stream().sorted(Comparator.comparing(ImageMaster::getDate).reversed()).collect(Collectors.toList());

//        imageMasterList1=imageMasterList.stream().sorted(Comparator.comparing(ImageMaster::getDate).reversed()).collect(Collectors.toList());
//        imageMasterList1.stream().forEach(imageMaster -> System.out.println("  dates |"+imageMaster.getDate()));

        int cnt =imageMasterList1.size()+imageMasterList2.size();

        return cnt;
//        draftMaster.setImageMaster(imageMasterList1);
//        return draftMaster;
    }


    @Override
    public DraftMaster getDraftMasterByStatus(String status, String userId) {

        System.out.println("  status ="+status+"   id ="+userId);
        DraftMaster draftMaster=new DraftMaster();
        draftMaster=draftMasterDao.findByUserMaster_UserId(userId);

        List<ImageMaster> imageMasters=new ArrayList<>();
        imageMasters=draftMaster.getImageMaster();

        List<ImageMaster> imageMasterList=new ArrayList<>();

        imageMasterList= imageMasters.stream().filter(imageMaster -> imageMaster.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());

        System.out.println("image MAssters  ="+imageMasterList.toString());

        imageMasterList.sort(Comparator.comparing(ImageMaster::getDate));
        //
//        imageMasterList=draftMaster.getImageMaster().stream().sorted(Comparator.comparing(ImageMaster::getDate).reversed()).collect(Collectors.toList());

        draftMaster.setImageMaster(imageMasterList);

        return draftMaster;
    }

    @Override
    public Boolean deleteDraftByDraftId(String draftId) {
        try
        {
            draftMasterDao.deleteById(draftId);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteImage(DeleteImageReq deleteImageReq) {

        try {
            DraftMaster draftMaster = new DraftMaster();
            draftMaster = draftMasterDao.findById(deleteImageReq.getDraftId()).get();

            List<ImageMaster> imageMasterList = new ArrayList<>();
            imageMasterList = draftMaster.getImageMaster();
            imageMasterList.stream().map(imageMaster -> {
                if (imageMaster.getImageId().equalsIgnoreCase(deleteImageReq.getImageId())) {
                    imageMaster.setStatus("delete");
                    imageMasterDao.save(imageMaster);
                    return imageMaster;
                } else {
                    return imageMaster;
                }
            }).collect(Collectors.toList());

            draftMaster.setImageMaster(imageMasterList);
            draftMasterDao.save(draftMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
