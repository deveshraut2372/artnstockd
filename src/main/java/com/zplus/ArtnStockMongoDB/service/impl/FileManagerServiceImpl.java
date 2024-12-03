package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.Advice.Exception.NoValueFoundException;
import com.zplus.ArtnStockMongoDB.dao.*;
import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.FileManagerMasterRes;
import com.zplus.ArtnStockMongoDB.dto.res.FileManagerRes;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.FileManagerService;
import org.apache.xmlbeans.impl.schema.XQuerySchemaTypeSystem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class FileManagerServiceImpl implements FileManagerService {

    @Autowired
    private FileManagerDao fileManagerDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ArtMasterDao artMasterDao;

    @Autowired
    private ArtProductMasterDao artProductMasterDao;

    @Autowired
    private AdminArtProductMasterDao adminArtProductMasterDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Boolean createFileManagerMaster(FileManagerReqDto fileManagerReqDto) {

        System.out.println("  hi ");
        List<ArtMaster> artMasterList = new ArrayList<>();
        List<ArtProductMaster> artProductMasterList=new ArrayList<>();
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();


        FileManagerMaster fileManagerMaster = new FileManagerMaster();
        fileManagerMaster.setCoverImage(fileManagerReqDto.getCoverImage());
        UserMaster userMaster1 = userDao.getId(fileManagerReqDto.getUserId());
        userMaster1.setUserId(fileManagerReqDto.getUserId());
        fileManagerMaster.setUserMaster(userMaster1);
        fileManagerMaster.setUpdatedDate(new Date());

        System.out.println("art.." + fileManagerReqDto.getArtId());
        if (fileManagerReqDto.getArtId() != null && !fileManagerReqDto.getArtId().isEmpty()) {
            for (String id : fileManagerReqDto.getArtId()) {
                System.out.println("art111.." + id);

                ArtMaster artMaster = artMasterDao.getArtId(id);
                artMasterList.add(artMaster);
            }
        }

        if (fileManagerReqDto.getArtProductId() != null && !fileManagerReqDto.getArtProductId().isEmpty()) {
            for (String id : fileManagerReqDto.getArtProductId()) {
                System.out.println("artProduct 111.." + id);

                ArtProductMaster artProductMaster = artProductMasterDao.findByArtProductId(id).get();
                artProductMasterList.add(artProductMaster);
            }
        }


        if(fileManagerReqDto.getAdminArtProductId()!=null && !fileManagerReqDto.getAdminArtProductId().isEmpty())
        {
           for(String id:fileManagerReqDto.getAdminArtProductId())
           {
               System.out.println("  adminArtProduct 111.."+id);
               AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.findByAdminArtProductId(id).get();
               adminArtProductMasterList.add(adminArtProductMaster);
           }
        }

        fileManagerMaster.setArtMaster(artMasterList);
        fileManagerMaster.setArtProductMaster(artProductMasterList);
        fileManagerMaster.setAdminArtProductMaster(adminArtProductMasterList);
        fileManagerMaster.setUserType(userMaster1.getUserRole().get(0));
        fileManagerMaster.setCount(fileManagerReqDto.getArtId().size()+fileManagerReqDto.getArtProductId().size()+fileManagerReqDto.getAdminArtProductId().size());
        BeanUtils.copyProperties(fileManagerReqDto, fileManagerMaster);
        try {
            fileManagerDao.save(fileManagerMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateFileManagerMaster(FileManagerReqDto fileManagerReqDto) {
        FileManagerMaster fileManagerMaster = new FileManagerMaster();
        List<ArtMaster> artMasterList = new ArrayList<>();
        List<ArtProductMaster> artProductMasterList=new ArrayList<>();
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();

        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(fileManagerReqDto.getUserId());
        fileManagerMaster.setUserMaster(userMaster);
        fileManagerMaster.setCoverImage(fileManagerReqDto.getCoverImage());
        for (String id : fileManagerReqDto.getArtId()) {
            ArtMaster artMaster = artMasterDao.getArtId(id);
            artMaster.setArtId(id);
            artMasterList.add(artMaster);
        }

        if (fileManagerReqDto.getArtProductId() != null && !fileManagerReqDto.getArtProductId().isEmpty()) {
            for (String id : fileManagerReqDto.getArtProductId()) {
                System.out.println("artProduct 111.." + id);

                ArtProductMaster artProductMaster = artProductMasterDao.findByArtProductId(id).get();
                artProductMasterList.add(artProductMaster);
            }
        }

        if(fileManagerReqDto.getAdminArtProductId()!=null && !fileManagerReqDto.getAdminArtProductId().isEmpty())
        {
            for(String id:fileManagerReqDto.getAdminArtProductId())
            {
                System.out.println("  adminArtProduct 111.."+id);
                AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.findByAdminArtProductId(id).get();
                adminArtProductMasterList.add(adminArtProductMaster);
            }
        }

        fileManagerMaster.setArtMaster(artMasterList);
        fileManagerMaster.setArtProductMaster(artProductMasterList);
        fileManagerMaster.setAdminArtProductMaster(adminArtProductMasterList);
        fileManagerMaster.setFileManagerId(fileManagerReqDto.getFileManagerId());
        BeanUtils.copyProperties(fileManagerReqDto, fileManagerMaster);
        try {
            fileManagerDao.save(fileManagerMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllFileManagerMaster() {
        return fileManagerDao.findAll();
    }

    @Override
    public FileManagerMaster editFileManagerMaster(String fileManagerId) {
        FileManagerMaster fileManagerMaster = new FileManagerMaster();
        try {
            Optional<FileManagerMaster> fileManagerMaster1 = fileManagerDao.findById(fileManagerId);
            fileManagerMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, fileManagerMaster));
            return fileManagerMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return fileManagerMaster;
        }
    }

    @Override
    public List getActiveFileManagerMaster() {
        return fileManagerDao.findAllByStatus("Active");
    }

    @Override
    public List<FileManagerMasterRes> getUserIdWiseFileManagerList(String userId) {
        List<FileManagerMaster> list = fileManagerDao.findByUserMasterUserId(userId);

        return list.parallelStream().map(fileManagerMaster -> {
            // Create the FileManagerMasterRes object
            FileManagerMasterRes fileManagerMasterRes = new FileManagerMasterRes();

            // Copy properties from FileManagerMaster to FileManagerMasterRes
            BeanUtils.copyProperties(fileManagerMaster, fileManagerMasterRes);

            // Combine the lists into one
            List<Object> combinedList = Stream.concat(
                    (fileManagerMaster.getArtMaster() != null ? fileManagerMaster.getArtMaster().stream() : Stream.empty()),
                    (fileManagerMaster.getAdminArtProductMaster() != null ? fileManagerMaster.getAdminArtProductMaster().stream() : Stream.empty())
            ).collect(Collectors.toList());

            System.out.println(" combinedList   "+combinedList.size());

            // Set the combined list
            fileManagerMasterRes.setList(combinedList);

            return fileManagerMasterRes;
        }).collect(Collectors.toList());


    }

    @Override
    public List<ArtMaster> getFileManagerIdWiseArtList(String fileManagerId) {
        FileManagerMaster fileManager = fileManagerDao.findById(fileManagerId).orElse(null);

        if (fileManager != null) {
            List<ArtMaster> artMasters = fileManager.getArtMaster();
            List<String> artMasterIds = extractArtMasterIds(artMasters);

            if (artMasterIds != null && !artMasterIds.isEmpty()) {
                return artMasterDao.findByArtIdIn(artMasterIds);
            }
        }

        return Collections.emptyList();


//        List<String> artMasterIds = new ArrayList<>();
//        List<ArtMaster> artMasters = fileManager.getArtMaster();
//
//        if (fileManager != null) {
//            for (ArtMaster artMaster : fileManager.getArtMaster()) {
//                artMasterIds.add(artMaster.getArtId());
//                if (artMasterIds != null && !artMasterIds.isEmpty()) {
//                    return artMasterDao.findByArtIdIn(artMasterIds);
//                }
//            }
//        }
//            return Collections.emptyList();
    }

    @Override
    public List<FileManagerRes> getUserIdWiseData(String userId) {
        List<FileManagerRes> managerRes = new ArrayList<>();
        List<FileManagerMaster> list = fileManagerDao.findByUserMaster_UserId(userId);
        for (FileManagerMaster fileManagerMaster : list) {
            FileManagerRes fileManagerRes = new FileManagerRes();
            fileManagerRes.setFileManagerId(fileManagerMaster.getFileManagerId());
            fileManagerRes.setTitle(fileManagerMaster.getTitle());
            managerRes.add(fileManagerRes);
        }
        return managerRes;
    }

    @Override
    public Boolean fileManagerIdWiseAddArt(AddArtRequestDto addArtRequestDto) {
        ArtMaster artMaster = artMasterDao.getArtId(addArtRequestDto.getArtId());
        Boolean flag = false;
        Optional<FileManagerMaster> fileManagerMasterOptional = fileManagerDao.findByFileManagerId(addArtRequestDto.getFileManagerId());

        if (fileManagerMasterOptional.isPresent()) {
            FileManagerMaster fileManagerMaster = fileManagerMasterOptional.get();
            List<ArtMaster> artList = fileManagerMaster.getArtMaster(); // Assuming you have a List<ArtMaster> property

            artList.add(artMaster);
            fileManagerMaster.setCount(artList.size());// Add the artMaster object to the list
            FileManagerMaster fileManagerMaster1 = fileManagerDao.save(fileManagerMaster);
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }

    @Override
    public Boolean fileManagerIdWiseAddAdminArtProduct(AddAdminArtProductReq addAdminArtProductReq)
    {

        System.out.println(" addAdminArtProductReq  ="+addAdminArtProductReq.toString());
        try
        {
            AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.findByAdminArtProductId(addAdminArtProductReq.getAdminArtProductId()).get();
            FileManagerMaster fileManagerMaster=fileManagerDao.findByFileManagerId(addAdminArtProductReq.getFileManagerId()).get();
            List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
            if(!fileManagerMaster.getAdminArtProductMaster().isEmpty()){
                adminArtProductMasterList=fileManagerMaster.getAdminArtProductMaster();
            }
            adminArtProductMasterList.add(adminArtProductMaster);
            fileManagerMaster.setCount(fileManagerMaster.getCount()+1);
            fileManagerMaster.setAdminArtProductMaster(adminArtProductMasterList);
            fileManagerDao.save(fileManagerMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean fileManagerIdWiseAddArtAndAdminProduct(AddArtAndAdminProductRequestDto addArtAndAdminProductRequestDto)
    {
        FileManagerMaster fileManagerMaster=new FileManagerMaster();
        try
        {
            fileManagerMaster=fileManagerDao.findByFileManagerId(addArtAndAdminProductRequestDto.getFileManagerId()).get();
            if(fileManagerMaster==null)
            {
                throw new NullPointerException();
            }

            List<ArtMaster> artMasterList=new ArrayList<>();
//            List<ArtProductMaster> artProductMasterList=fileManagerMaster.getArtProductMaster();
            List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();

            if(!addArtAndAdminProductRequestDto.getArtIds().isEmpty())
            {
                artMasterList=fileManagerMaster.getArtMaster();
                for (String artId : addArtAndAdminProductRequestDto.getArtIds()) {
                    ArtMaster artMaster=artMasterDao.getArtId(artId);
                    artMasterList.add(artMaster);
                    fileManagerMaster.setArtMaster(artMasterList);
                    fileManagerMaster.setCount(fileManagerMaster.getCount()+1);
                }
            }

            if(!addArtAndAdminProductRequestDto.getAdminArtProductIds().isEmpty())
            {
                adminArtProductMasterList=fileManagerMaster.getAdminArtProductMaster();
                for (String adminArtProductId : addArtAndAdminProductRequestDto.getAdminArtProductIds()) {
                    AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.getAdminArtProductMaster(adminArtProductId);
                    adminArtProductMasterList.add(adminArtProductMaster);
                    fileManagerMaster.setAdminArtProductMaster(adminArtProductMasterList);
                    fileManagerMaster.setCount(fileManagerMaster.getCount()+1);
                }
            }
            fileManagerDao.save(fileManagerMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean fileManagerIdWiseAddArtAndAdminProduct1(AddArtAndAdminProductRequest addArtAndAdminProductRequest) {
        FileManagerMaster fileManagerMaster=new FileManagerMaster();
        try
        {
            System.out.println("  called ");
            fileManagerMaster=fileManagerDao.findByFileManagerId(addArtAndAdminProductRequest.getFileManagerId()).get();

            if(fileManagerMaster==null)
            {
                throw new NullPointerException();
            }

            List<ArtMaster> artMasterList=new ArrayList<>();
//            List<ArtProductMaster> artProductMasterList=fileManagerMaster.getArtProductMaster();
            List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();

            artMasterList=fileManagerMaster.getArtMaster();
            adminArtProductMasterList=fileManagerMaster.getAdminArtProductMaster();

//            List<ArtMaster> finalArtMasterList = artMasterList;
//            FileManagerMaster finalFileManagerMaster = fileManagerMaster;
//            List<AdminArtProductMaster> finalAdminArtProductMasterList = adminArtProductMasterList;
//            FileManagerMaster finalFileManagerMaster1 = fileManagerMaster;

//            Thread thread=new Thread(()->
//            {
                System.out.println("  Thread is called ");
                for (AddArtAndProductReq addArtAndProductReq : addArtAndAdminProductRequest.getAddArtAndProductReqList()) {
                 switch(addArtAndProductReq.getType())
                 {
                     case "art":
                         System.out.println("  art ");
                         ArtMaster artMaster=artMasterDao.getArtId(addArtAndProductReq.getId());
                         artMaster.setFileManagerId(fileManagerMaster.getFileManagerId());
                         artMasterDao.save(artMaster);
                         artMasterList.add(artMaster);
                         fileManagerMaster.setArtMaster(artMasterList);
                         fileManagerMaster.setCount(fileManagerMaster.getCount()+1);
                         fileManagerDao.save(fileManagerMaster);
                         break;
                     case "product":
                         System.out.println("  product ");
                         AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.getAdminArtProductMaster(addArtAndProductReq.getId());
                         adminArtProductMaster.setFileManagerId(fileManagerMaster.getFileManagerId());
                         adminArtProductMasterDao.save(adminArtProductMaster);
                         adminArtProductMasterList.add(adminArtProductMaster);
                         fileManagerMaster.setAdminArtProductMaster(adminArtProductMasterList);
                         fileManagerMaster.setCount(fileManagerMaster.getCount()+1);
                         fileManagerDao.save(fileManagerMaster);
                         break;
                 }
                }
//            });

//            if(!addArtAndAdminProductRequestDto.getArtIds().isEmpty())
//            {
//                artMasterList=fileManagerMaster.getArtMaster();
//                for (String artId : addArtAndAdminProductRequestDto.getArtIds()) {
//                    ArtMaster artMaster=artMasterDao.getArtId(artId);
//                    artMasterList.add(artMaster);
//                    fileManagerMaster.setArtMaster(artMasterList);
//                    fileManagerMaster.setCount(fileManagerMaster.getCount()+1);
//                }
//            }

//            if(!addArtAndAdminProductRequestDto.getAdminArtProductIds().isEmpty())
//            {
//                adminArtProductMasterList=fileManagerMaster.getAdminArtProductMaster();
//                for (String adminArtProductId : addArtAndAdminProductRequestDto.getAdminArtProductIds()) {
//                    AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.getAdminArtProductMaster(adminArtProductId);
//                    adminArtProductMasterList.add(adminArtProductMaster);
//                    fileManagerMaster.setAdminArtProductMaster(adminArtProductMasterList);
//                    fileManagerMaster.setCount(fileManagerMaster.getCount()+1);
//                }
//            }
//            fileManagerDao.save(fileManagerMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List UserIdwiseArtAndProduct(String userId) {

        List<Object> responseList=new ArrayList();

        FileManagerMaster fileManagerMaster=new FileManagerMaster();

        UserMaster userMaster=new UserMaster();
        userMaster=userDao.findById(userId).get();

        List<ArtMaster> artMasterList=new ArrayList<>();
        artMasterList=artMasterDao.findAllByStatusAndUserMaster("Approved",userMaster);

        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
        adminArtProductMasterList=adminArtProductMasterDao.findAllByStatusAndUserMaster("Approved",userMaster);
        responseList.addAll(artMasterList);
        responseList.addAll(adminArtProductMasterList);

        return responseList;
    }

    @Override
    public List<FileManagerMasterRes> getFileManagerByUserIdAndSort(FileManagerSortRequest fileManagerSortRequest) {
//        FileManagerMasterRes fileManagerMasterRes=new FileManagerMasterRes();

        List<FileManagerMaster> fileManagerMasterList=new ArrayList<>();

        UserMaster userMaster=new UserMaster();
        userMaster=userDao.findById(fileManagerSortRequest.getUserId()).get();
        Query query=new Query();
        query.addCriteria(Criteria.where("userMaster").is(userMaster));
        query.addCriteria(Criteria.where("category").is(fileManagerSortRequest.getCategory()));

        List<FileManagerMaster> list=new ArrayList();

        switch (fileManagerSortRequest.getOrder())
        {
            case "Newest":
                list=mongoTemplate.find(query,FileManagerMaster.class);
                fileManagerMasterList.addAll(list);
                break;
            case "Oldest":
                list=mongoTemplate.find(query,FileManagerMaster.class);
                Collections.reverse(list);
                fileManagerMasterList.addAll(list);
                break;
            case "A-Z":
                list=mongoTemplate.find(query,FileManagerMaster.class);
                list.sort(Comparator.comparing(FileManagerMaster::getTitle));
                fileManagerMasterList.addAll(list);
                break;
            case "Z-A":
                list=mongoTemplate.find(query,FileManagerMaster.class);
                list.sort(Comparator.comparing(FileManagerMaster::getTitle).reversed());
//                Collections.reverse(list);
                fileManagerMasterList.addAll(list);
                break;
            default:
                list=mongoTemplate.find(query,FileManagerMaster.class);
                fileManagerMasterList.addAll(list);
                break;
        }

//        return fileManagerMasterList;

        return fileManagerMasterList.parallelStream().map(fileManagerMaster -> {
            // Create the FileManagerMasterRes object
            FileManagerMasterRes fileManagerMasterRes = new FileManagerMasterRes();

            // Copy properties from FileManagerMaster to FileManagerMasterRes
            BeanUtils.copyProperties(fileManagerMaster, fileManagerMasterRes);

            // Combine the lists into one
            List<Object> combinedList = Stream.concat(
                    (fileManagerMaster.getArtMaster() != null ? fileManagerMaster.getArtMaster().stream() : Stream.empty()),
                    (fileManagerMaster.getAdminArtProductMaster() != null ? fileManagerMaster.getAdminArtProductMaster().stream() : Stream.empty())
            ).collect(Collectors.toList());

            combinedList.sort((o1, o2) -> {
                Date date1 = getSubmittedDate(o1);
                Date date2 = getSubmittedDate(o2);
                return date1.compareTo(date2);
            });

            System.out.println(" combinedList   "+combinedList.size());

            // Set the combined list
            fileManagerMasterRes.setList(combinedList);

            return fileManagerMasterRes;
        }).collect(Collectors.toList());

    }

    private static Date getSubmittedDate(Object obj) {
        if (obj instanceof ArtMaster) {
            return ((ArtMaster) obj).getSubmittedDate();
        } else if (obj instanceof AdminArtProductMaster) {
            return ((AdminArtProductMaster) obj).getSubmittedDate();
        }
        return null;
    }

    @Override
    public Boolean DeletefileManagerIdWiseAddArtAndAdminProduct(AddArtAndAdminProductRequestDto addArtAndAdminProductRequestDto) {

        FileManagerMaster fileManagerMaster=new FileManagerMaster();

        Query query=new Query();
        query.addCriteria(Criteria.where("fileManagerId").is(addArtAndAdminProductRequestDto.getFileManagerId()));

        try {

            fileManagerMaster = mongoTemplate.findOne(query, FileManagerMaster.class);
            List<ArtMaster> artMasterList = fileManagerMaster.getArtMaster();
            List<AdminArtProductMaster> adminArtProductMasterList = fileManagerMaster.getAdminArtProductMaster();

            if (!addArtAndAdminProductRequestDto.getArtIds().isEmpty()) {
                System.out.println("  12 size ="+artMasterList.size());
                for (String artId : addArtAndAdminProductRequestDto.getArtIds()) {
                    System.out.println("  art id ="+artId);
                    for (ArtMaster artMaster : artMasterList) {
                        System.out.println("  art Id ="+artMaster.getArtId());
                    }

                    artMasterList.removeIf(artMaster -> artId.equals(artMaster.getArtId()));
                    ArtMaster artMaster=artMasterDao.findById(artId).get();
                    artMaster.setFileManagerId(null);
                    artMasterDao.save(artMaster);
                }
                System.out.println("  remove size ="+artMasterList.size());
                fileManagerMaster.setArtMaster(artMasterList);
            }

            if (!addArtAndAdminProductRequestDto.getAdminArtProductIds().isEmpty()) {
                System.out.println("  13 size ="+adminArtProductMasterList.size());
                for (String adminArtProductId : addArtAndAdminProductRequestDto.getAdminArtProductIds()) {
                    adminArtProductMasterList.removeIf(adminArtProductMaster -> adminArtProductId.equals(adminArtProductMaster.getAdminArtProductId()));
                    AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.findById(adminArtProductId).get();
                    adminArtProductMaster.setFileManagerId(null);
                    adminArtProductMasterDao.save(adminArtProductMaster);
                    //                    AdminArtProductMaster adminArtProductMaster = new AdminArtProductMaster();
//                    adminArtProductMaster = adminArtProductMasterDao.getAdminArtProductMaster(adminArtProductId);

//                    if (adminArtProductMasterList.contains(adminArtProductMaster)) {
//                        adminArtProductMasterList.remove(adminArtProductMaster);
//                        System.out.println("  removed ");
//                    }

                }
                System.out.println("  remove size ="+adminArtProductMasterList.size());
                fileManagerMaster.setAdminArtProductMaster(adminArtProductMasterList);
            }

//            fileManagerMaster.setAdminArtProductMaster(adminArtProductMasterList);
//            fileManagerMaster.setArtMaster(artMasterList);
//            System.out.println("   adminArtProductMasterList ="+adminArtProductMasterList.isEmpty());
            System.out.println("   artMasterList ="+artMasterList.isEmpty());
            fileManagerDao.save(fileManagerMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean CheckFilePresentOrNot(CheckFileReq checkFileReq) {
        Boolean flag=false;
        UserMaster userMaster=new UserMaster();

        userMaster=userDao.findById(checkFileReq.getUserId()).get();
        List<FileManagerMaster> list=new ArrayList();

        if(checkFileReq.getType().equalsIgnoreCase("art")) {
            System.out.println(" art ");
            list=fileManagerDao.findAllByUserMaster_UserIdAndArtMaster_ArtId(checkFileReq.getUserId(),checkFileReq.getId());
        }
        if(checkFileReq.getType().equalsIgnoreCase("product")) {
            list = fileManagerDao.findAllByUserMaster_UserIdAndAdminArtProductMaster_AdminArtProductId(checkFileReq.getUserId(),checkFileReq.getId());
        }

        System.out.println("Size ="+list.size());

        if(list.isEmpty())
        {
            System.out.println("  flag = "+false);
            return false;
        }else {
            System.out.println("  flag = "+true);
            return true;
        }

//        return false;
    }

    @Override
    public List<ArtMaster> getFileManagerIdWiseArtList1(String fileManagerId, String sortType) {
        return null;
    }

    @Override
    public List getFileManagersListByUserIdAndSort(FileManagerListSortRequest fileManagerListSortRequest) {

//        System.out.println(" fileManagerListSortRequest = "+fileManagerListSortRequest.toString());
//        System.out.println(" searchKey ="+fileManagerListSortRequest.getSearchKey());

        FileManagerMasterRes fileManagerMasterRes=new FileManagerMasterRes();

//        UserMaster userMaster=new UserMaster();
//        userMaster=userDao.findById(fileManagerListSortRequest.getUserId()).get();

        FileManagerMaster fileManagerMaster=new FileManagerMaster();
        fileManagerMaster=fileManagerDao.findByFileManagerId(fileManagerListSortRequest.getFileManagerId()).get();

//        System.out.println(" artMaster list "+fileManagerMaster.getArtMaster().size());
//        System.out.println(" artProductMaster  list "+fileManagerMaster.getArtMaster().size());

        if(fileManagerMaster==null)
        {
            throw new NoValueFoundException("400","  No Value Found ");
        }

        BeanUtils.copyProperties(fileManagerMaster,fileManagerMasterRes);

        List list=new ArrayList<>();

        List list1=new ArrayList<>();
        List list2=new ArrayList();

//        if(!fileManagerMaster.getArtMaster().isEmpty()) {
//            list.addAll(fileManagerMaster.getArtMaster());
//        }

//        if(!fileManagerMaster.getAdminArtProductMaster().isEmpty()) {
//            list.addAll(fileManagerMaster.getAdminArtProductMaster());
//        }

        Query query=new Query();
        query.addCriteria(Criteria.where("fileManagerId").is(fileManagerListSortRequest.getFileManagerId()));

        Query query1=new Query();
        query1.addCriteria(Criteria.where("fileManagerId").is(fileManagerListSortRequest.getFileManagerId()));

        if(fileManagerListSortRequest.getSearchKey()!=null || !fileManagerListSortRequest.getSearchKey().isEmpty()){
           query.addCriteria(new Criteria().orOperator(
                   Criteria.where("artName").regex(fileManagerListSortRequest.getSearchKey()),
                   Criteria.where("arProductNo").regex(fileManagerListSortRequest.getSearchKey()),
                   Criteria.where("keywords").regex(fileManagerListSortRequest.getSearchKey())
           ));

            query1.addCriteria(new Criteria().orOperator(
                    Criteria.where("adminArtProductName").regex(fileManagerListSortRequest.getSearchKey()),
                    Criteria.where("artProductUniqueNo").regex(fileManagerListSortRequest.getSearchKey())
//                    Criteria.where("artMaster.keywords").regex(fileManagerListSortRequest.getSearchKey())
            ));
        }


        switch (fileManagerListSortRequest.getOrder())
        {
            case "Newest":
                fileManagerMaster=mongoTemplate.findOne(query,FileManagerMaster.class);

                    list1=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(list1);

                    list2=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    list.addAll(list2);

        list.sort((o1, o2) -> {
            Date date1 = getSubmittedDate(o1);
            Date date2 = getSubmittedDate(o2);
            return date1.compareTo(date2);
        });
                break;
            case "Oldest":
                fileManagerMaster=mongoTemplate.findOne(query,FileManagerMaster.class);
                    list1=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(list1);
                    list2=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    list.addAll(list2);


//        list.sort((o1, o2) -> {
//            Date date1 = getSubmittedDate(o1);
//            Date date2 = getSubmittedDate(o2);
//            return date1.compareTo(date2);
//        });

                Collections.reverse(list);
                break;
            case "A-Z":
                fileManagerMaster=mongoTemplate.findOne(query,FileManagerMaster.class);
//                if( fileManagerMaster.getArtMaster().size()>0) {
//                    fileManagerMaster.getArtMaster().sort(Comparator.comparing(ArtMaster::getArtName));
//                    list.addAll(fileManagerMaster.getArtMaster());
                    list1=mongoTemplate.find(query,ArtMaster.class);
                    list1.sort(Comparator.comparing(ArtMaster::getArtName));
                    list.addAll(list1);
//                }
//                if( fileManagerMaster.getAdminArtProductMaster().size()>0) {
//                    fileManagerMaster.getAdminArtProductMaster().sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
//                    list.addAll(fileManagerMaster.getAdminArtProductMaster());
                    list2=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    list2.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
                    list.addAll(list2);
//                }
                break;
            case "Z-A":
                fileManagerMaster=mongoTemplate.findOne(query,FileManagerMaster.class);
//                if( fileManagerMaster.getArtMaster().size()>0) {
//                    fileManagerMaster.getArtMaster().sort(Comparator.comparing(ArtMaster::getArtName).reversed());
//                    list.addAll(fileManagerMaster.getArtMaster());
                    list1=mongoTemplate.find(query,ArtMaster.class);
                    list1.sort(Comparator.comparing(ArtMaster::getArtName));
                    Collections.reverse(list1);
                    list.addAll(list1);
//                }
//                if( fileManagerMaster.getAdminArtProductMaster().size()>0) {
//                    fileManagerMaster.getAdminArtProductMaster().sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName).reversed());
//                    list.addAll(fileManagerMaster.getAdminArtProductMaster());
                    list2=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    list2.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
                    Collections.reverse(list2);
                    list.addAll(list2);

//                }
                break;

            case "Exclusive":
                query.addCriteria(Criteria.where("typeOfContent").is("Exclusive Art"));
                fileManagerMaster=mongoTemplate.findOne(query,FileManagerMaster.class);
//                if( fileManagerMaster.getArtMaster().size()>0) {
//                    fileManagerMaster.getArtMaster().sort(Comparator.comparing(ArtMaster::getArtName).reversed());
//                    list.addAll(fileManagerMaster.getArtMaster());
                list1=mongoTemplate.find(query,ArtMaster.class);
                list1.sort(Comparator.comparing(ArtMaster::getArtName));
                list.addAll(list1);
//                }
//                if( fileManagerMaster.getAdminArtProductMaster().size()>0) {
//                    fileManagerMaster.getAdminArtProductMaster().sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName).reversed());
//                    list.addAll(fileManagerMaster.getAdminArtProductMaster());
                list2=mongoTemplate.find(query1,AdminArtProductMaster.class);
                list2.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
                list.addAll(list2);

//                }
                break;
            case "Featured":
                query.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
                fileManagerMaster=mongoTemplate.findOne(query,FileManagerMaster.class);
//                if( fileManagerMaster.getArtMaster().size()>0) {
//                    fileManagerMaster.getArtMaster().sort(Comparator.comparing(ArtMaster::getArtName).reversed());
//                    list.addAll(fileManagerMaster.getArtMaster());
                list1=mongoTemplate.find(query,ArtMaster.class);
                list.addAll(list1);
//                }
//                if( fileManagerMaster.getAdminArtProductMaster().size()>0) {
//                    fileManagerMaster.getAdminArtProductMaster().sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName).reversed());
//                    list.addAll(fileManagerMaster.getAdminArtProductMaster());
                list2=mongoTemplate.find(query1,AdminArtProductMaster.class);
                list.addAll(list2);

//                }
                break;

            default:
                if( fileManagerMaster.getArtMaster().size()>0) {
//                    list.addAll(fileManagerMaster.getArtMaster());
                    list1=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(list1);
                }
                if( fileManagerMaster.getAdminArtProductMaster().size()>0) {
//                    list.addAll(fileManagerMaster.getAdminArtProductMaster());
                    list2=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    list.addAll(list2);
                }
                break;
        }


        return list;
    }

    @Override
    public int getCountByCategoryWise(FileManagerCountReq fileManagerCountReq) {
        int cnt = 0;
        Query query=new Query();

        UserMaster userMaster=new UserMaster();
        userMaster=userDao.findById(fileManagerCountReq.getUserId()).get();

        query.addCriteria(Criteria.where("userMaster").is(userMaster));
        query.addCriteria(Criteria.where("category").is(fileManagerCountReq.getCategory()));

        List<FileManagerMaster> fileManagerMasterList=new ArrayList<>();
        fileManagerMasterList=mongoTemplate.find(query,FileManagerMaster.class);

        int s=0;
        for (FileManagerMaster fileManagerMaster : fileManagerMasterList) {
            cnt=cnt+fileManagerMaster.getArtMaster().size();
            cnt=cnt+fileManagerMaster.getAdminArtProductMaster().size();
        }

        return cnt;
    }

    @Override
    public Boolean updateCoverImage(UpdateCoverImageReq updateCoverImageReq) {

        FileManagerMaster fileManagerMaster=new FileManagerMaster();
        fileManagerMaster=fileManagerDao.findById(updateCoverImageReq.getFileManagerId()).get();
        fileManagerMaster.setCoverImage(updateCoverImageReq.getCoverImage());

        try
        {
            fileManagerDao.save(fileManagerMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }







    /*
      @Override
    public Boolean fileManagerIdWiseAddArtAndAdminProduct(AddArtAndAdminProductRequestDto addArtAndAdminProductRequestDto)
    {
        FileManagerMaster fileManagerMaster=new FileManagerMaster();
        try
        {
            fileManagerMaster=fileManagerDao.findByFileManagerId(addArtAndAdminProductRequestDto.getFileManagerId()).get();
            if(fileManagerMaster==null)
            {
                throw new NullPointerException();
            }
            List<ArtMaster> artMasterList=new ArrayList<>();
//            List<ArtProductMaster> artProductMasterList=fileManagerMaster.getArtProductMaster();
            List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();

            if(addArtAndAdminProductRequestDto.getArtId()!=null)
            {
                artMasterList=fileManagerMaster.getArtMaster();
                ArtMaster artMaster=artMasterDao.getArtId(addArtAndAdminProductRequestDto.getArtId());
                artMasterList.add(artMaster);
                fileManagerMaster.setArtMaster(artMasterList);
                fileManagerMaster.setCount(fileManagerMaster.getCount()+1);
            }

            if(addArtAndAdminProductRequestDto.getAdminArtProductId()!=null)
            {
                adminArtProductMasterList=fileManagerMaster.getAdminArtProductMaster();
                AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.getAdminArtProductMaster(addArtAndAdminProductRequestDto.getAdminArtProductId());
                adminArtProductMasterList.add(adminArtProductMaster);
                fileManagerMaster.setAdminArtProductMaster(adminArtProductMasterList);
                fileManagerMaster.setCount(fileManagerMaster.getCount()+1);
            }

            fileManagerDao.save(fileManagerMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
     */

    @Override
    public Boolean updateIdWiseTitle(UpdateFileManagerTitleReqDto updateFileManagerTitleReqDto) {
        Boolean flag = false;
//        Optional<FileManagerMaster> fileManagerMasterOptional = fileManagerDao.findByFileManagerId(updateFileManagerTitleReqDto.getFileManagerId());
//        if (fileManagerMasterOptional.isPresent()) {
//            FileManagerMaster fileManagerMaster = fileManagerMasterOptional.get();
//
//            fileManagerMaster=fileManagerDao.findById(updateFileManagerTitleReqDto.getFileManagerId()).get()
//            fileManagerMaster.setTitle(updateFileManagerTitleReqDto.getTitle());
//            fileManagerDao.save(fileManagerMaster);
//            flag = true;
//        } else {
//            flag = false;
//        }
//        return flag;

        try
        {
            FileManagerMaster fileManagerMaster=new FileManagerMaster();
            fileManagerMaster= fileManagerDao.findById(updateFileManagerTitleReqDto.getFileManagerId()).get();
            fileManagerMaster.setTitle(updateFileManagerTitleReqDto.getTitle());
            fileManagerDao.save(fileManagerMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteFileManager(String fileManagerId) {
        Boolean flag = false;
        try {
            fileManagerDao.deleteById(fileManagerId);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public List FileManagerFilter(FilterReqDto filterReqDto) {
        List filterList = new ArrayList<>();

        if (filterReqDto.getType().equals("All") && filterReqDto.getText().equals("All")) {
            List<FileManagerMaster> fileManagerMasterList = fileManagerDao.findAll();
            System.out.println("fileManagerMasterList: " + fileManagerMasterList.size());
            if (filterReqDto.getUserId() == null) {
                return fileManagerMasterList;
            }
            if (filterReqDto.getUserId() != null && !filterReqDto.getUserId().isEmpty()) {
                filterList = fileManagerMasterList.stream()
                        .filter(artMaster -> artMaster.getUserMaster() != null && artMaster.getUserMaster().getUserId().equals(filterReqDto.getUserId()))
                        .collect(Collectors.toList());
            }
        } else if (filterReqDto.getType().equals("File")) {
            List<FileManagerMaster> list = fileManagerDao.findByTitle(filterReqDto.getText());
            System.out.println("fileManagerMasterList 1: " + list.size());

            if (filterReqDto.getUserId() == null) {
                return list;
            }
            if (!filterReqDto.getUserId().isEmpty()) {
                filterList = list.stream()
                        .filter(artMaster -> artMaster.getUserMaster() != null && artMaster.getUserMaster().getUserId().equals(filterReqDto.getUserId()))
                        .collect(Collectors.toList());
            }
            System.out.println("filterList 2: " + filterList.size());
        } else if (filterReqDto.getType().equals("All")) {
            String searchText = filterReqDto.getText();
            List<FileManagerMaster> fileManagerList = fileManagerDao.findByArtMaster_ArtNameContaining(searchText);
            System.out.println("fileManagerList!!!: " + fileManagerList.size());
            List<ArtMaster> filteredArtMasterList = new ArrayList<>();
            if (filterReqDto.getUserId() == null || filterReqDto.getUserId().isEmpty()) {
                for (FileManagerMaster fileManagerMaster : fileManagerList) {
                    List<ArtMaster> artMasterList = fileManagerMaster.getArtMaster();
                    for (ArtMaster artMaster : artMasterList) {
                        if (artMaster.getArtName().contains(searchText)) {
                            filteredArtMasterList.add(artMaster);
                            System.out.println("rrrr...." + filteredArtMasterList.size());
                        }
                    }
                    filterList = filteredArtMasterList;
                }
            } else if (filterReqDto.getUserId() != null && !filterReqDto.getUserId().isEmpty()) {
                for (FileManagerMaster fileManagerMaster : fileManagerList) {
                    List<ArtMaster> artMasterList = fileManagerMaster.getArtMaster();
                    for (ArtMaster artMaster : artMasterList) {
                        if (artMaster.getArtName().contains(searchText)) {
                            filteredArtMasterList.add(artMaster);
                            System.out.println("sss...." + filteredArtMasterList.size());
                        }
                    }
                    filterList = filteredArtMasterList;
                }
            }
        }
        return filterList;
    }
//    @Override
//    public List<ArtMaster> getList(String fileManagerId) {
//        FileManagerMaster fileManager = fileManagerDao.findById(fileManagerId).orElse(null);
//
//        if (fileManager != null) {
//            List<ArtMaster> artMasters = fileManager.getArtMaster();
//            List<String> artMasterIds = extractArtMasterIds(artMasters);
//
//            if (artMasterIds != null && !artMasterIds.isEmpty()) {
//                List<ArtMaster> artMasterList = artMasterDao.findByArtIdIn(artMasterIds);
//
//                return artMasterList;
//            }
//        }
//
//        return Collections.emptyList();
//    }

    List<String> extractArtMasterIds(List<ArtMaster> artMasters) {
        List<String> artMasterIds = new ArrayList<>();

        for (ArtMaster artMaster : artMasters) {
            artMasterIds.add(artMaster.getArtId());
        }

        return artMasterIds;
    }
}
