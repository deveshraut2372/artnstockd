package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.*;
import com.zplus.ArtnStockMongoDB.dto.req.ReleaseMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.req.ReleaseSortReq;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.ReleaseMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReleaseMasterServiceImpl implements ReleaseMasterService {

    @Autowired
    private ReleaseMasterDao releaseMasterDao;

    @Autowired
    private ReleaseFileMasterDao releaseFileMasterDao;

    @Autowired
    private ArtDetailsMasterDao artDetailsMasterDao;


    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    private ArtMasterDao artMasterDao;


    @Autowired
    private UserDao userDao;



    @Override
    public Boolean createReleaseMaster(ReleaseMasterRequest releaseMasterRequest) {
        ReleaseMaster releaseMaster = new ReleaseMaster();
        releaseMaster.setDate(new Date());

        BeanUtils.copyProperties(releaseMasterRequest, releaseMaster);
        releaseMaster.setReleaseStatus("InReview");
        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(releaseMasterRequest.getUserId());
        releaseMaster.setUserMaster(userMaster);
        AddDetailsMaster addDetailsMaster=new AddDetailsMaster();
        addDetailsMaster.setArtDetailsId(releaseMasterRequest.getArtDetailsId());
        releaseMaster.setAddDetailsMaster(addDetailsMaster);

        try {
            releaseMasterDao.save(releaseMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Boolean updateReleaseMaster(ReleaseMasterRequest releaseMasterRequest) {

        System.out.println("  Release Req "+releaseMasterRequest.toString());
        ReleaseMaster releaseMaster = new ReleaseMaster();
        releaseMaster=releaseMasterDao.findById(releaseMasterRequest.getReleaseId()).get();

        releaseMaster.setReleaseId(releaseMasterRequest.getReleaseId());
        BeanUtils.copyProperties(releaseMasterRequest, releaseMaster);
        releaseMaster.setReleaseStatus(releaseMasterRequest.getReleaseStatus());
        releaseMaster.setType(releaseMasterRequest.getType());

        UserMaster userMaster = new UserMaster();
        userMaster=userDao.findById(releaseMasterRequest.getUserId()).get();
        releaseMaster.setUserMaster(userMaster);

        AddDetailsMaster addDetailsMaster=new AddDetailsMaster();
        addDetailsMaster=artDetailsMasterDao.findById(releaseMasterRequest.getArtDetailsId()).get();
        releaseMaster.setAddDetailsMaster(addDetailsMaster);

        System.out.println("  Release master "+releaseMaster.toString());
        try {
            releaseMasterDao.save(releaseMaster);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllReleaseMaster() {
        return releaseMasterDao.findAll();
    }

    @Override
    public ReleaseMaster editReleaseMaster(String releaseId) {
        ReleaseMaster releaseMaster = new ReleaseMaster();
        try {
            Optional<ReleaseMaster> releaseMaster1 = releaseMasterDao.findById(releaseId);
            releaseMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, releaseMaster));
            return releaseMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return releaseMaster;
        }
    }

    @Override
    public List<ReleaseMaster> typeWiseReleaseMaster(String type) {
        List<ReleaseMaster> list = releaseMasterDao.findByType(type);
        return list;
    }

    @Override
    public List<ReleaseMaster> getUserIdWiseReleaseMaster(String userId) {
        List<ReleaseMaster> list = releaseMasterDao.findByUserMaster_UserId(userId);
        return list;
    }

    @Override
    public List<ReleaseMaster> getReleasesByArtDetailsId(String artDetailsId) {
        List<ReleaseMaster> releaseMasterList=new ArrayList<>();
        releaseMasterList=releaseMasterDao.findAllByAddDetailsMaster_ArtDetailsId(artDetailsId);
        return releaseMasterList;
    }

    @Override
    public Boolean deleteByRelasesId(String releaseId) {
        System.out.println("  releasesId ="+releaseId);
      try {
          releaseMasterDao.deleteById(releaseId);
          return true;
      }catch (Exception e)
      {
          e.printStackTrace();
          return false;
      }
    }

    @Override
    public Boolean deleteReleasesByArtDetailsId(String artDetailsId) {
        List<ReleaseMaster> releaseMasterList=new ArrayList<>();
        releaseMasterList=releaseMasterDao.findAllByAddDetailsMaster_ArtDetailsId(artDetailsId);
        try
        {
            releaseMasterDao.deleteAll(releaseMasterList);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ReleaseMaster> getReleasesByUserIdAndType(String userId, String type) {
        List<ReleaseMaster> releaseMasterList=new ArrayList<>();
        releaseMasterList=releaseMasterDao.findByUserMaster_UserIdAndType(userId,type);
        return releaseMasterList;
    }

    @Override
    public List<ReleaseMaster> getReleasesByFileName(String fileName) {
        List<ReleaseMaster> releaseMasterList=new ArrayList<>();
        releaseMasterList=releaseMasterDao.getReleasesByFileName(fileName);
        return releaseMasterList;
    }

    @Override
    public List<ReleaseMaster> getReleasesByFileNameAndSortType(String fileName, String sortType,String userId)
    {
        System.out.println("  file name = null");
        List<ReleaseMaster> releaseMasterList = new ArrayList<>();
        List<ReleaseMaster> releaseMasterList1=new ArrayList<>();

        Query query=new Query();

        if (fileName == null) {
            System.out.println(" file name = null");
            if (sortType != null) {
                System.out.println("  sort Type  = " + sortType);
                switch (sortType) {
                    case "Newest":
                        System.out.println("  Newsets is called ");
                        releaseMasterList = releaseMasterDao.findAllByUserMaster_UserId(userId);
                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(20, releaseMasterList.size()));
                        break;
                    case "Oldest":
                        System.out.println("  oldest is called ");
                        releaseMasterList = releaseMasterDao.findAllByUserMaster_UserId(userId);
                        Collections.reverse(releaseMasterList);
                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(20, releaseMasterList.size()));
                        break;
                    case "MostPopular":
                        releaseMasterList=releaseMasterDao.findAllByUserMaster_UserId(userId);
                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(20, releaseMasterList.size()));
                        break;
                    case "Featured":
                        releaseMasterList=releaseMasterDao.findAllByUserMaster_UserId(userId);
                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(20, releaseMasterList.size()));
                        break;
                    case "AtoZ":
                        System.out.println("  AtoZ is called ");
                        query.addCriteria(Criteria.where("userMaster.userId").is(userId));
                        query.with(Sort.by(Sort.Direction.ASC, "fileName"));
                        releaseMasterList= mongoTemplate.find(query, ReleaseMaster.class);
                        System.out.println("  size  ="+releaseMasterList.size());
                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(20, releaseMasterList.size()));
                        break;
                    case "ZtoA":
                        System.out.println("  ZtoA is called ");
                        query.addCriteria(Criteria.where("userMaster.userId").is(userId));
                        query.with(Sort.by(Sort.Direction.DESC, "fileName"));
                        releaseMasterList= mongoTemplate.find(query, ReleaseMaster.class);
                        System.out.println("  size  ="+releaseMasterList.size());
//                            releaseMasterList = releaseMasterDao.findAll(Sort.by(Sort.Direction.DESC, "fileName"));
                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(20, releaseMasterList.size()));
                        break;
                    default:
                        releaseMasterList = releaseMasterDao.findAll();
                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(20, releaseMasterList.size()));
                        break;
                }
            } else {
                releaseMasterList = releaseMasterDao.findAll();
                releaseMasterList1 = releaseMasterList.subList(0, Math.min(20, releaseMasterList.size()));
            }
        } else {
            releaseMasterList = releaseMasterDao.getReleasesByFileName(fileName);
            releaseMasterList1 = releaseMasterList.subList(0, Math.min(20, releaseMasterList.size()));
        }
        return releaseMasterList1;
    }


    // A simple cache to store results based on request parameters
    private Map<String, List<ReleaseMaster>> cache = new HashMap<>();

    @Override
    public List<ReleaseMaster> gerReleasesBySortTypeAndUserId(ReleaseSortReq releaseSortReq)
    {
        System.out.println("  File Name ="+releaseSortReq.getFileName());
        System.out.println("  sort Type ="+releaseSortReq.getSortType());
        System.out.println("  User id ="+releaseSortReq.getUserId());

        System.out.println("  size cache every  ="+cache.size());
        if (cache.containsKey(releaseSortReq.toString())) {
            System.out.println("  Default call ");
           UpdategetReleasesBySortTypeAndUserId(releaseSortReq);
            return cache.get(releaseSortReq.toString());
        }

        List<ReleaseMaster> releaseMasterList=new ArrayList<>();

        UserMaster userMaster=userDao.getId(releaseSortReq.getUserId());

        Query query=new Query();
        System.out.println("hhh  called aaa");
        if(releaseSortReq.getType()!=null && releaseSortReq.getType().equalsIgnoreCase("All"))
        {
            query.addCriteria(Criteria.where("userMaster").is(userMaster));
            System.out.println("hhh  called firt return ");
            releaseMasterList=mongoTemplate.find(query,ReleaseMaster.class);
            cache.put(releaseSortReq.toString(),releaseMasterList);
            System.out.println("  size casch 1 "+cache.size());
            return releaseMasterList;
        }

        System.out.println("hhh add criateria ");
        query.addCriteria(Criteria.where("userMaster").is(userMaster).and("type").is(releaseSortReq.getType()));

        if(releaseSortReq.getFileName()!=null)
        {
            query.addCriteria(Criteria.where("fileName").regex(releaseSortReq.getFileName()).and("releaseStatus").is("Approved"));
        }
        System.out.println("hhh  aaaaaaa");
        releaseMasterList=mongoTemplate.find(query,ReleaseMaster.class);

        System.out.println("  File Name ="+releaseSortReq.getFileName());
        System.out.println("  sort Type ="+releaseSortReq.getSortType());
        System.out.println("  User id ="+releaseSortReq.getUserId());

        System.out.println("hhh ");
        List<ReleaseMaster> releaseMasterList1=new ArrayList<>();

            if (releaseSortReq.getSortType() != null)
            {
                System.out.println(" inside if  ");

                System.out.println("  sort Type  = " + releaseSortReq.getSortType());
                switch (releaseSortReq.getSortType()) {
                    case "Newest":
                        System.out.println("  Newsets is called ");
                        releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                        releaseMasterList1=releaseMasterList;
                        System.out.println("hhh newest");
                        break;
                    case "Oldest":
                        System.out.println("  oldest is called ");
                        releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
                        Collections.reverse(releaseMasterList);
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                        releaseMasterList1=releaseMasterList;
                        System.out.println("hhh oldest");
                        break;
                    case "MostPopular":
                        releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                        releaseMasterList1=releaseMasterList;
                        System.out.println("hhh MostPopular");
                        break;

                    case "Featured":
                        List<ArtMaster> artMasterList=new ArrayList<>();
//                        UserMaster userMaster=userDao.getId(releaseSortReq.getUserId());

//                        Query query1=new Query();
//                        query1.addCriteria(Criteria.where("userMaster").is(userMaster));
//                        query1.addCriteria(Criteria.where("typeOfContent").in("Featured Art"));
//                        List<ArtMaster> artMasters=mongoTemplate.find(query,ArtMaster.class);
//                        List<ReleaseMaster> finalReleaseMasterList = releaseMasterList;
//                        artMasters.stream().forEach(artMaster ->{
//                            finalReleaseMasterList.addAll(artMaster.getReleaseMasterList());
//
//                        });
//                        releaseMasterList=artMasters.stream().map(ArtMaster::getReleaseMasterList).flatMap(List::stream).collect(Collectors.toList());

                        releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));

//                        List<AddDetailsMaster> addDetailsMasterList=new ArrayList<>();
//                        addDetailsMasterList=artDetailsMasterDao.findAll();

//                        releaseMasterList1=finalReleaseMasterList;
                        releaseMasterList1=releaseMasterList;

                        System.out.println("hhh Featured");
                        break;
                    case "AtoZ":
                        System.out.println("  AtoZ is called ");
                        query.addCriteria(Criteria.where("userMaster.userId").is(releaseSortReq.getUserId()));
                        query.with(Sort.by(Sort.Direction.ASC, "fileName"));
                        releaseMasterList= mongoTemplate.find(query, ReleaseMaster.class);
                        System.out.println("  size  ="+releaseMasterList.size());
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                        releaseMasterList1=releaseMasterList;
                        System.out.println("hhh AtoZ");
                        break;
                    case "ZtoA":
                        System.out.println("  ZtoA is called ");
                        query.addCriteria(Criteria.where("userMaster").is(userMaster));
                        query.with(Sort.by(Sort.Direction.DESC, "fileName"));
                        releaseMasterList= mongoTemplate.find(query, ReleaseMaster.class);
                        System.out.println("  size  ="+releaseMasterList.size());
//                            releaseMasterList = releaseMasterDao.findAll(Sort.by(Sort.Direction.DESC, "fileName"));
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                        releaseMasterList1=releaseMasterList;
                        System.out.println("hhh ztoA");
                        break;
                    default:
                        releaseMasterList = releaseMasterDao.findAll();
                        releaseMasterList1=releaseMasterList;
                        System.out.println("hhh defaulr");
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                        break;
                }
            } else {
//                releaseMasterList = releaseMasterDao.findAll();
                releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                System.out.println("hhh  else ");
            }
        System.out.println("hhh last");
        System.out.println("  size casch 2 "+cache.size());
        cache.put(releaseSortReq.toString(),releaseMasterList1);
        return releaseMasterList1;
    }

    ///

    Map<String,List<ReleaseMaster>>  releasesMap=new HashMap<>();


    @Override
    public List<ReleaseMaster> getReleasesBySortTypeAndUserId(ReleaseSortReq releaseSortReq)
    {
        System.out.println("  File Name ="+releaseSortReq.getFileName());
        System.out.println("  sort Type ="+releaseSortReq.getSortType());
        System.out.println("  User id ="+releaseSortReq.getUserId());

        List<ReleaseMaster> releaseMasterList=new ArrayList<>();
        UserMaster userMaster=userDao.getId(releaseSortReq.getUserId());

        Query query=new Query();
        System.out.println("hhh  called aaa");

        query.addCriteria(Criteria.where("userMaster.$id").is(releaseSortReq.getUserId()));
        query.fields().include("releaseMasterList").exclude("_id");


        if(releaseSortReq.getType()!=null && releaseSortReq.getType().equalsIgnoreCase("All"))
        {
            List<ArtMaster> artMasters = mongoTemplate.find(query, ArtMaster.class);

            List<ReleaseMaster> releaseMasterList1=artMasters.stream()
                    .flatMap(artMaster -> artMaster.getReleaseMasterList().stream()).collect(Collectors.toList());

            releasesMap.put(releaseSortReq.toString(),releaseMasterList1);
            return  artMasters.stream()
                    .flatMap(artMaster -> artMaster.getReleaseMasterList().stream()).collect(Collectors.toList());
        }

        query.addCriteria(Criteria.where("type").is(releaseSortReq.getType()));

        if(releaseSortReq.getFileName()!=null)
        {
            query.addCriteria(Criteria.where("fileName").regex(releaseSortReq.getFileName()).and("releaseStatus").is("Approved"));
        }

        List<ArtMaster> artMasters =mongoTemplate.find(query,ArtMaster.class);

        releaseMasterList= artMasters.stream()
                .flatMap(artMaster -> artMaster.getReleaseMasterList().stream()).collect(Collectors.toList());

        System.out.println("hhh ");
        List<ReleaseMaster> releaseMasterList1=new ArrayList<>();

        if (releaseSortReq.getSortType() != null)
        {
            System.out.println(" inside if  ");

            System.out.println("  sort Type  = " + releaseSortReq.getSortType());
            switch (releaseSortReq.getSortType()) {

                case "Newest":
                    System.out.println("  Newsets is called ");
                    releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
                    Collections.reverse(releaseMasterList);
                    releaseMasterList1=releaseMasterList;
                    break;

                case "Oldest":
                    System.out.println("  oldest is called ");
                    releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
                    releaseMasterList1=releaseMasterList;
                    System.out.println("hhh oldest");
                    break;

                case "MostPopular":
                    releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
                    releaseMasterList1=releaseMasterList;
                    break;

                case "Featured":
                    List<ArtMaster> artMasterList=new ArrayList<>();
                    releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
                    releaseMasterList1=releaseMasterList;
                    break;
                case "AtoZ":
                    System.out.println("  AtoZ is called ");
                    releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
                    releaseMasterList.sort(Comparator.comparing(ReleaseMaster::getFileName));
                    releaseMasterList1=releaseMasterList;
                    break;
                case "ZtoA":
                    System.out.println("  ZtoA is called ");
                    releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
                    releaseMasterList.sort(Comparator.comparing(ReleaseMaster::getFileName));
                    Collections.reverse(releaseMasterList);
                    releaseMasterList1=releaseMasterList;
                    break;
                default:
                    releaseMasterList = releaseMasterDao.findAll();
                    releaseMasterList1=releaseMasterList;
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                    break;
            }
        } else {
//                releaseMasterList = releaseMasterDao.findAll();
            releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
            System.out.println("hhh  else ");
        }
        System.out.println("hhh last");
        System.out.println("  size casch 2 "+cache.size());
        cache.put(releaseSortReq.toString(),releaseMasterList1);

        return releaseMasterList1;
    }

    public List<ReleaseMaster> gettReleasesBySortTypeAndUserId(ReleaseSortReq releaseSortReq)
    {
        System.out.println("  File Name ="+releaseSortReq.getFileName());
        System.out.println("  User id ="+releaseSortReq.getUserId());
        System.out.println("  sort Type ="+releaseSortReq.getSortType());

        List<ReleaseMaster> releaseMasterList=new ArrayList<>();

        Query query=new Query();

        UserMaster userMaster=userDao.getId(releaseSortReq.getUserId());
        query.addCriteria(Criteria.where("userMaster").is(userMaster));

        List<ArtMaster> artMasterList=new ArrayList<>();

//        List<ReleaseMaster> releaseMasterList=new ArrayList<>();
//        UserMaster userMaster=userDao.getId(releaseSortReq.getUserId());
//
//        Query query=new Query();
//        System.out.println("hhh  called aaa");
//
//        query.addCriteria(Criteria.where("userMaster.$id").is(releaseSortReq.getUserId()));
//        query.addCriteria(Criteria.where("status").is("Approved"));
//        query.fields().include("releaseMasterList").exclude("_id");
//
//        if(releaseSortReq.getType()!=null || releaseSortReq.getType()!="") {
//            query.addCriteria(Criteria.where("type").is(releaseSortReq.getType()));
//        }
//
//
//        if(releaseSortReq.getType()!=null && releaseSortReq.getType().equalsIgnoreCase("All"))
//        {
//            List<ArtMaster> artMasters = mongoTemplate.find(query, ArtMaster.class);
//            return  artMasters.stream()
//                    .flatMap(artMaster -> artMaster.getReleaseMasterList().stream()).collect(Collectors.toList());
//        }
//
//
//        if(releaseSortReq.getFileName()!=null)
//        {
//            query.addCriteria(Criteria.where("fileName").regex(releaseSortReq.getFileName()).and("releaseStatus").is("Approved"));
//        }
//
//        List<ArtMaster> artMasters =mongoTemplate.find(query,ArtMaster.class);
//
//        releaseMasterList= artMasters.stream()
//                .flatMap(artMaster -> artMaster.getReleaseMasterList().stream()).collect(Collectors.toList());
//
//        System.out.println("hhh ");
//        List<ReleaseMaster> releaseMasterList1=new ArrayList<>();
//
//        if (releaseSortReq.getSortType() != null)
//        {
//            System.out.println(" inside if  ");
//
//            System.out.println("  sort Type  = " + releaseSortReq.getSortType());
//            switch (releaseSortReq.getSortType()) {
//
//                case "Newest":
//                    System.out.println("  Newsets is called ");
//                    releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
//                    releaseMasterList1=releaseMasterList;
//                    break;
//
//                case "Oldest":
//                    System.out.println("  oldest is called ");
//                    releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
//                    Collections.reverse(releaseMasterList);
//                    releaseMasterList1=releaseMasterList;
//                    System.out.println("hhh oldest");
//                    break;
//
//                case "MostPopular":
//                    releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
//                    releaseMasterList1=releaseMasterList;
//                    break;
//
//                case "Featured":
//                    List<ArtMaster> artMasterList=new ArrayList<>();
//                    releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
//                    releaseMasterList1=releaseMasterList;
//                    break;
//                case "AtoZ":
//                    System.out.println("  AtoZ is called ");
//                    releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
////                    releaseMasterList.sort(Comparator.comparing(ReleaseMaster::getFileName));
//                    releaseMasterList1=releaseMasterList;
//                    break;
//                case "ZtoA":
//                    System.out.println("  ZtoA is called ");
//                    releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
////                    releaseMasterList.sort(Comparator.comparing(ReleaseMaster::getFileName));
//                    Collections.reverse(releaseMasterList);
//                    releaseMasterList1=releaseMasterList;
//                    break;
//                default:
//                    releaseMasterList = releaseMasterDao.findAll();
//                    releaseMasterList1=releaseMasterList;
////                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
//                    break;
//            }
//        } else {
////                releaseMasterList = releaseMasterDao.findAll();
//            releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
//            System.out.println("hhh  else ");
//        }
//        System.out.println("hhh last");
//        System.out.println("  size casch 2 "+cache.size());
//        cache.put(releaseSortReq.toString(),releaseMasterList1);
//        return releaseMasterList1;

        return null;
    }
    ///


    @Override
    public int getReleasesCountByUserIdAndType(String userId, String type) {
        long cnt=0;
         UserMaster userMaster=new UserMaster();
         userMaster=userDao.findById(userId).get();

         Query query=new Query();
        query.addCriteria(Criteria.where("userMaster").is(userMaster));
        query.addCriteria(Criteria.where("type").is(type));
        query.addCriteria(Criteria.where("releaseStatus").is("Approved"));
         cnt =mongoTemplate.count(query,ReleaseMaster.class);
        return (int) cnt;
    }

    @Override
    public List<ReleaseMaster> getReleasesByArtId(String artId) {

        List<ReleaseMaster> releaseMasterList=new ArrayList<>();

                ArtMaster artMaster=new ArtMaster();
                artMaster=artMasterDao.getArtId(artId);

                Query query=new Query();
                query.addCriteria(Criteria.where("addDetailsMaster").is(artMaster.getAddDetailsMaster()));
                query.addCriteria(Criteria.where("releaseStatus").is("Approved"));
        releaseMasterList=mongoTemplate.find(query,ReleaseMaster.class);
        return releaseMasterList;
    }




//     update cash memory
    @Async
    public void UpdategetReleasesBySortTypeAndUserId(ReleaseSortReq releaseSortReq)
    {

        System.out.println("  size of catch ="+cache.size());

        System.out.println("  Default call  asynchronise ");
    System.out.println("  File Name ="+releaseSortReq.getFileName());
    System.out.println("  sort Type ="+releaseSortReq.getSortType());
    System.out.println("  User id ="+releaseSortReq.getUserId());
    List<ReleaseMaster> releaseMasterList=new ArrayList<>();

    UserMaster userMaster=userDao.getId(releaseSortReq.getUserId());

    Query query=new Query();
    if(releaseSortReq.getType()!=null && releaseSortReq.getType().equalsIgnoreCase("All"))
    {
        query.addCriteria(Criteria.where("userMaster").is(userMaster));
        System.out.println("hhh  called firt return ");
        releaseMasterList=mongoTemplate.find(query,ReleaseMaster.class);
        System.out.println("  Default call  asynchronise  add 1 ");
        cache.put(releaseSortReq.toString(),releaseMasterList);
//        return releaseMasterList;
    }
    query.addCriteria(Criteria.where("userMaster").is(userMaster).and("type").is(releaseSortReq.getType()));

    if(releaseSortReq.getFileName()!=null)
    {
        query.addCriteria(Criteria.where("fileName").regex(releaseSortReq.getFileName()).and("releaseStatus").is("Approved"));
    }
    System.out.println("hhh  aaaaaaa");
    releaseMasterList=mongoTemplate.find(query,ReleaseMaster.class);

    List<ReleaseMaster> releaseMasterList1=new ArrayList<>();

    if (releaseSortReq.getSortType() != null)
    {
        System.out.println("  sort Type  = " + releaseSortReq.getSortType());
        switch (releaseSortReq.getSortType()) {
            case "Newest":
                System.out.println("  Newsets is called ");
                releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                releaseMasterList1=releaseMasterList;
                break;
            case "Oldest":
                System.out.println("  oldest is called ");
                releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
                Collections.reverse(releaseMasterList);
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                releaseMasterList1=releaseMasterList;
                break;
            case "MostPopular":
                releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                releaseMasterList1=releaseMasterList;
                break;

            case "Featured":
                List<ArtMaster> artMasterList=new ArrayList<>();
//                        UserMaster userMaster=userDao.getId(releaseSortReq.getUserId());

//                        Query query1=new Query();
//                        query1.addCriteria(Criteria.where("userMaster").is(userMaster));
//                        query1.addCriteria(Criteria.where("typeOfContent").in("Featured Art"));
//                        List<ArtMaster> artMasters=mongoTemplate.find(query,ArtMaster.class);
//                        List<ReleaseMaster> finalReleaseMasterList = releaseMasterList;
//                        artMasters.stream().forEach(artMaster ->{
//                            finalReleaseMasterList.addAll(artMaster.getReleaseMasterList());
//
//                        });
//                        releaseMasterList=artMasters.stream().map(ArtMaster::getReleaseMasterList).flatMap(List::stream).collect(Collectors.toList());

                releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));

//                        List<AddDetailsMaster> addDetailsMasterList=new ArrayList<>();
//                        addDetailsMasterList=artDetailsMasterDao.findAll();

//                        releaseMasterList1=finalReleaseMasterList;
                releaseMasterList1=releaseMasterList;
                break;
            case "AtoZ":
                System.out.println("  AtoZ is called ");
                query.addCriteria(Criteria.where("userMaster.userId").is(releaseSortReq.getUserId()));
                query.with(Sort.by(Sort.Direction.ASC, "fileName"));
                releaseMasterList= mongoTemplate.find(query, ReleaseMaster.class);
                System.out.println("  size  ="+releaseMasterList.size());
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                releaseMasterList1=releaseMasterList;
                break;
            case "ZtoA":
                System.out.println("  ZtoA is called ");
                query.addCriteria(Criteria.where("userMaster").is(userMaster));
                query.with(Sort.by(Sort.Direction.DESC, "fileName"));
                releaseMasterList= mongoTemplate.find(query, ReleaseMaster.class);
                System.out.println("  size  ="+releaseMasterList.size());
//                            releaseMasterList = releaseMasterDao.findAll(Sort.by(Sort.Direction.DESC, "fileName"));
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                releaseMasterList1=releaseMasterList;
                break;
            default:
                releaseMasterList = releaseMasterDao.findAll();
                releaseMasterList1=releaseMasterList;
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                break;
        }
    } else {
//                releaseMasterList = releaseMasterDao.findAll();
        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
    }
    cache.put(releaseSortReq.toString(),releaseMasterList1);
        System.out.println("  Default call  asynchronise  add 2 ");
//    return releaseMasterList1;
}




/*

    @Override
    public List<ReleaseMaster> gerReleasesBySortTypeAndUserId(ReleaseSortReq releaseSortReq)
    {
        System.out.println("  File Name ="+releaseSortReq.getFileName());
        System.out.println("  sort Type ="+releaseSortReq.getSortType());
        System.out.println("  User id ="+releaseSortReq.getUserId());

        List<ReleaseMaster> releaseMasterList=new ArrayList<>();

        UserMaster userMaster=userDao.getId(releaseSortReq.getUserId());


        Query query=new Query();
        System.out.println("hhh  called aaa");
        if(releaseSortReq.getType()!=null && releaseSortReq.getType().equalsIgnoreCase("All"))
        {
            query.addCriteria(Criteria.where("userMaster").is(userMaster));
            System.out.println("hhh  called firt return ");
            return mongoTemplate.find(query,ReleaseMaster.class);
        }

        System.out.println("hhh add criateria ");
        query.addCriteria(Criteria.where("userMaster").is(userMaster).and("type").is(releaseSortReq.getType()));

        if(releaseSortReq.getFileName()!=null)
        {
            query.addCriteria(Criteria.where("fileName").regex(releaseSortReq.getFileName()).and("releaseStatus").is("Approved"));
        }
        System.out.println("hhh  aaaaaaa");
        releaseMasterList=mongoTemplate.find(query,ReleaseMaster.class);

        System.out.println("  File Name ="+releaseSortReq.getFileName());
        System.out.println("  sort Type ="+releaseSortReq.getSortType());
        System.out.println("  User id ="+releaseSortReq.getUserId());

        System.out.println("hhh ");
        List<ReleaseMaster> releaseMasterList1=new ArrayList<>();

            if (releaseSortReq.getSortType() != null)
            {
                System.out.println(" inside if  ");

                System.out.println("  sort Type  = " + releaseSortReq.getSortType());
                switch (releaseSortReq.getSortType()) {
                    case "Newest":
                        System.out.println("  Newsets is called ");
                        releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                        releaseMasterList1=releaseMasterList;
                        System.out.println("hhh newest");
                        break;
                    case "Oldest":
                        System.out.println("  oldest is called ");
                        releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
                        Collections.reverse(releaseMasterList);
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                        releaseMasterList1=releaseMasterList;
                        System.out.println("hhh oldest");
                        break;
                    case "MostPopular":
                        releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                        releaseMasterList1=releaseMasterList;
                        System.out.println("hhh MostPopular");
                        break;

                    case "Featured":
                        List<ArtMaster> artMasterList=new ArrayList<>();
//                        UserMaster userMaster=userDao.getId(releaseSortReq.getUserId());

//                        Query query1=new Query();
//                        query1.addCriteria(Criteria.where("userMaster").is(userMaster));
//                        query1.addCriteria(Criteria.where("typeOfContent").in("Featured Art"));
//                        List<ArtMaster> artMasters=mongoTemplate.find(query,ArtMaster.class);
//                        List<ReleaseMaster> finalReleaseMasterList = releaseMasterList;
//                        artMasters.stream().forEach(artMaster ->{
//                            finalReleaseMasterList.addAll(artMaster.getReleaseMasterList());
//
//                        });
//                        releaseMasterList=artMasters.stream().map(ArtMaster::getReleaseMasterList).flatMap(List::stream).collect(Collectors.toList());

                        releaseMasterList = releaseMasterDao.findAllByUserMaster_UserIdAndReleaseStatus(releaseSortReq.getUserId(),"Approved");
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));

//                        List<AddDetailsMaster> addDetailsMasterList=new ArrayList<>();
//                        addDetailsMasterList=artDetailsMasterDao.findAll();

//                        releaseMasterList1=finalReleaseMasterList;
                        releaseMasterList1=releaseMasterList;

                        System.out.println("hhh Featured");
                        break;
                    case "AtoZ":
                        System.out.println("  AtoZ is called ");
                        query.addCriteria(Criteria.where("userMaster.userId").is(releaseSortReq.getUserId()));
                        query.with(Sort.by(Sort.Direction.ASC, "fileName"));
                        releaseMasterList= mongoTemplate.find(query, ReleaseMaster.class);
                        System.out.println("  size  ="+releaseMasterList.size());
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                        releaseMasterList1=releaseMasterList;
                        System.out.println("hhh AtoZ");
                        break;
                    case "ZtoA":
                        System.out.println("  ZtoA is called ");
                        query.addCriteria(Criteria.where("userMaster").is(userMaster));
                        query.with(Sort.by(Sort.Direction.DESC, "fileName"));
                        releaseMasterList= mongoTemplate.find(query, ReleaseMaster.class);
                        System.out.println("  size  ="+releaseMasterList.size());
//                            releaseMasterList = releaseMasterDao.findAll(Sort.by(Sort.Direction.DESC, "fileName"));
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                        releaseMasterList1=releaseMasterList;
                        System.out.println("hhh ztoA");
                        break;
                    default:
                        releaseMasterList = releaseMasterDao.findAll();
                        releaseMasterList1=releaseMasterList;
                        System.out.println("hhh defaulr");
//                        releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                        break;
                }
            } else {
//                releaseMasterList = releaseMasterDao.findAll();
                releaseMasterList1 = releaseMasterList.subList(0, Math.min(releaseSortReq.getLimit(), releaseMasterList.size()));
                System.out.println("hhh  else ");
            }
        System.out.println("hhh last");
        return releaseMasterList1;
    }

 */

}
