package com.zplus.ArtnStockMongoDB.service.impl;

import com.amazonaws.services.dynamodbv2.model.CreateGlobalSecondaryIndexAction;
import com.zplus.ArtnStockMongoDB.Advice.Exception.NoValueFoundException;
import com.zplus.ArtnStockMongoDB.dao.*;
import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.*;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.CollectionMasterService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@Service
public class CollectionMasterServiceImpl implements CollectionMasterService {

    @Autowired
    private CollectionMasterDao collectionMasterDao;

    @Autowired
    private ArtMasterDao artMasterDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FileManagerServiceImpl managerService;
    @Autowired
    private ArtProductMasterDao artProductMasterDao;

    @Autowired
    private AdminArtProductMasterDao adminArtProductMasterDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CollectionArtDao collectionArtDao;


    @Autowired
    private  CollectionAdminArtProductDao collectionAdminArtProductDao;


    @Override
    public Boolean createCollectionMaster(CollectionMasterReqDto collectionMasterReqDto) {
        List<ArtMaster> artMasterList = new ArrayList<>();
        List<ArtProductMaster> artProductMasterList = new ArrayList<>();
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();


        int count=0;
        CollectionMaster collectionMaster = new CollectionMaster();
        UserMaster userMaster1 = userDao.getId(collectionMasterReqDto.getUserId());
        userMaster1.setUserId(collectionMasterReqDto.getUserId());
        collectionMaster.setUserMaster(userMaster1);

        CollectionMaster collectionMaster1=collectionMasterDao.save(collectionMaster);
        BeanUtils.copyProperties(collectionMaster1,collectionMaster);

        System.out.println("art.." + collectionMasterReqDto.getArtId());
        if (collectionMasterReqDto.getArtId() != null && !collectionMasterReqDto.getArtId().isEmpty()) {
            for (String id : collectionMasterReqDto.getArtId()) {
                System.out.println("art111.." + id);

                ArtMaster artMaster = artMasterDao.getArtId(id);
                artMaster.setCollectionId(collectionMaster1.getCollectionId());
                artMasterDao.save(artMaster);
                artMasterList.add(artMaster);
            }
            collectionMaster.setCount(collectionMasterReqDto.getArtId().size());
            count=count+collectionMasterReqDto.getArtId().size();

        }
        if (collectionMasterReqDto.getArtProductId() != null && !collectionMasterReqDto.getArtProductId().isEmpty()) {
            for (String id : collectionMasterReqDto.getArtProductId()) {
                System.out.println("id.." + id);

                ArtProductMaster artProductMaster = artProductMasterDao.getArtProductMaster(id);
                artProductMaster.setCollectionId(collectionMaster1.getCollectionId());
                artProductMasterDao.save(artProductMaster);
                artProductMasterList.add(artProductMaster);
            }
            collectionMaster.setCount(collectionMasterReqDto.getArtProductId().size());
            count=count+collectionMasterReqDto.getArtProductId().size();
        }

        if(collectionMasterReqDto.getAdminArtProductId()!=null && !collectionMasterReqDto.getAdminArtProductId().isEmpty())
        {
          for(String id:collectionMasterReqDto.getAdminArtProductId())
          {
              System.out.println("  id .."+id);
              AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.findByAdminArtProductId(id).get();
              adminArtProductMaster.setCollectionId(collectionMaster1.getCollectionId());
              adminArtProductMasterDao.save(adminArtProductMaster);
              adminArtProductMasterList.add(adminArtProductMaster);
          }
          collectionMaster.setCount(collectionMaster.getCount()+adminArtProductMasterList.size());
            count=count+collectionMasterReqDto.getAdminArtProductId().size();
        }

        collectionMaster.setArtMaster(artMasterList);
        collectionMaster.setArtProductMaster(artProductMasterList);
        collectionMaster.setAdminArtProductMaster(adminArtProductMasterList);
        BeanUtils.copyProperties(collectionMasterReqDto, collectionMaster);
        collectionMaster.setCount(count);
        collectionMaster.setUserType(userMaster1.getUserRole().stream().findFirst().get());
        collectionMaster.setStatus("Active");
//        collectionMaster.setList(new ArrayList());

        try {
            collectionMasterDao.save(collectionMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateCollectionMaster(CollectionMasterReqDto collectionMasterReqDto) {
        CollectionMaster collectionMaster = new CollectionMaster();
        List<ArtMaster> artMasterList = new ArrayList<>();
        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(collectionMasterReqDto.getUserId());
        collectionMaster.setUserMaster(userMaster);
        for (String id : collectionMasterReqDto.getArtId()) {
            ArtMaster artMaster = artMasterDao.getArtId(id);
            artMaster.setArtId(id);
            artMasterList.add(artMaster);
        }


        collectionMaster.setArtMaster(artMasterList);

        collectionMaster.setCollectionId(collectionMasterReqDto.getCollectionId());
        BeanUtils.copyProperties(collectionMasterReqDto, collectionMaster);
        try {
            collectionMasterDao.save(collectionMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllCollectionMaster() {
        return collectionMasterDao.findAll();
    }

    @Override
    public CollectionMaster editCollectionMaster(String collectionId) {
        CollectionMaster collectionMaster = new CollectionMaster();
        try {
            Optional<CollectionMaster> collectionMaster1 = collectionMasterDao.findById(collectionId);
            collectionMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, collectionMaster));
            return collectionMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return collectionMaster;
        }
    }

    @Override
    public List getActiveCollectionMaster() {
        return collectionMasterDao.findAllByStatus("Active");
    }

    @Override
    public List<CollectionMasterRes> getUserIdWiseCollectionMasterList(String userId) {

        List<CollectionMaster> collectionMasterList=new ArrayList();
        collectionMasterList= collectionMasterDao.findByUserMasterUserId(userId);



        return collectionMasterList.parallelStream().map(collectionMaster -> {
            // Create the FileManagerMasterRes object
            CollectionMasterRes collectionMasterRes = new CollectionMasterRes();

            // Copy properties from FileManagerMaster to FileManagerMasterRes
            BeanUtils.copyProperties(collectionMaster, collectionMasterRes);

            List<CollectionArtMaster> collectionArtMasters=new ArrayList<>();
            List<CollectionAdminArtProductMaster> collectionAdminArtProductMasterArrayList=new ArrayList<>();

            Query query=new Query();
            query.addCriteria(Criteria.where("collectionId").is(collectionMaster.getCollectionId()));
            collectionArtMasters=mongoTemplate.find(query,CollectionArtMaster.class);
            collectionAdminArtProductMasterArrayList=mongoTemplate.find(query,CollectionAdminArtProductMaster.class);

            List<Object> combinedList = new ArrayList<>();
            combinedList.addAll(collectionArtMasters);
            combinedList.addAll(collectionAdminArtProductMasterArrayList);

            // Combine the lists into one
//            List<Object> combinedList = Stream.concat(
//                    (collectionMaster.getArtMaster() != null ? collectionMaster.getArtMaster().stream() : Stream.empty()),
//                    (collectionMaster.getAdminArtProductMaster() != null ? collectionMaster.getAdminArtProductMaster().stream() : Stream.empty())
//            ).collect(Collectors.toList());


//            // Combine the lists into one
//            List<Object> combinedList = Stream.concat(
//                    (collectionMaster.getArtMaster() != null ? collectionMaster.getArtMaster().stream() : Stream.empty()),
//                    (collectionMaster.getAdminArtProductMaster() != null ? collectionMaster.getAdminArtProductMaster().stream() : Stream.empty())
//            ).collect(Collectors.toList());

            System.out.println(" combinedList   "+combinedList.size());

            // Set the combined list
            collectionMasterRes.setList(combinedList);

            return collectionMasterRes;
        }).collect(Collectors.toList());


//        return collectionMasterResList;
    }

    @Override
    public List<ArtMaster> getCollectionIdWiseArtList(String collectionId) {
        CollectionMaster collectionMaster = collectionMasterDao.findById(collectionId).orElse(null);

        if (collectionMaster != null) {
            List<ArtMaster> artMasters = collectionMaster.getArtMaster();
            List<String> artMasterIds = managerService.extractArtMasterIds(artMasters);
            if (artMasterIds != null && !artMasterIds.isEmpty()) {
                return artMasterDao.findByArtIdIn(artMasterIds);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<CollectionRes> getUserIdWiseCollectionData(String userId) {
        List<CollectionRes> managerRes = new ArrayList<>();
        List<CollectionMaster> list = collectionMasterDao.findByUserMasterUserId(userId);
        for (CollectionMaster collectionMaster : list) {
            CollectionRes collectionRes = new CollectionRes();
            collectionRes.setCollectionId(collectionMaster.getCollectionId());
            collectionRes.setTitle(collectionMaster.getTitle());
            managerRes.add(collectionRes);
        }
        return managerRes;
    }

    @Override
    public Boolean CollectionIdWiseAddArt(AddArtInCollectionRequestDto addArtInCollectionRequestDto) {
        ArtMaster artMaster = artMasterDao.getArtId(addArtInCollectionRequestDto.getArtId());
        ArtProductMaster artProductMaster = artProductMasterDao.getArtProductMaster(addArtInCollectionRequestDto.getArtProductId());
//        AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.getAdminArtProductMaster(addArtInCollectionRequestDto.getAdminArtProductId());

        Boolean flag = false;

        try {
        Optional<CollectionMaster> collectionMasterOptional = collectionMasterDao.findByCollectionId(addArtInCollectionRequestDto.getCollectionId());
        if (collectionMasterOptional.isPresent()) {
            if (addArtInCollectionRequestDto.getArtId() != null && !addArtInCollectionRequestDto.getArtId().isEmpty()) {
                CollectionMaster collectionMaster = collectionMasterOptional.get();
                List<ArtMaster> artList = collectionMaster.getArtMaster(); // Assuming you have a List<ArtMaster> property
                artList.add(artMaster);
                collectionMaster.setCount(artList.size());
                CollectionMaster cm = collectionMasterDao.save(collectionMaster);
            }

//            if(addArtInCollectionRequestDto.getAdminArtProductId()!=null && !addArtInCollectionRequestDto.getAdminArtProductId().isEmpty())
//            {
//                CollectionMaster collectionMaster = collectionMasterOptional.get();
//                List<ArtMaster> artList = collectionMaster.getArtMaster(); // Assuming you have a List<ArtMaster> property
//                artList.add(artMaster);
//                collectionMaster.setCount(artList.size());
//                CollectionMaster cm = collectionMasterDao.save(collectionMaster);
//            }

            }else if (addArtInCollectionRequestDto.getArtProductId() !=null && !addArtInCollectionRequestDto.getArtProductId().isEmpty()) {
                CollectionMaster collectionMaster = collectionMasterOptional.get();
                List<ArtProductMaster> artProductMasterList = collectionMaster.getArtProductMaster(); // Assuming you have a List<ArtMaster> property

                artProductMasterList.add(artProductMaster);
                collectionMaster.setCount(artProductMasterList.size());
                CollectionMaster cm = collectionMasterDao.save(collectionMaster);
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public Boolean updateIdWiseCollectionTitle(UpdateCollectionTitleReqDto updateCollectionTitleReqDto) {
        Boolean flag = false;
//        Optional<CollectionMaster> collectionMasterOptional = collectionMasterDao.findByCollectionId(updateCollectionTitleReqDto.getCollectionId());
//        if (collectionMasterOptional.isPresent()) {
//            CollectionMaster collectionMaster = collectionMasterOptional.get();
//            collectionMaster.setTitle(updateCollectionTitleReqDto.getTitle());
//            collectionMasterDao.save(collectionMaster);
//            flag = true;
//        } else {
//            flag = false;
//        }
//        return flag;

        try
        {
            if(updateCollectionTitleReqDto.getCollectionId()==null)
            {
                throw new NoValueFoundException(" 400","  Null Value Exception ");
            }
            CollectionMaster collectionMaster=new CollectionMaster();
            collectionMaster=collectionMasterDao.findById(updateCollectionTitleReqDto.getCollectionId()).get();
            collectionMaster.setTitle(updateCollectionTitleReqDto.getTitle());
            collectionMasterDao.save(collectionMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteCollection(String collectionId) {
        Boolean flag = false;
        try {
            collectionMasterDao.deleteById(collectionId);

//            List<CollectionArtMaster> collectionArtMasterList=new ArrayList<>();
//            List<CollectionAdminArtProductMaster> collectionAdminArtProductMasterList=new ArrayList<>();
//
//            collectionArtMasterList=collectionArtDao.findAllByCollectionId(collectionId);
//            collectionAdminArtProductMasterList=collectionAdminArtProductDao.findAllByCollectionId(collectionId);
//
//            collectionArtDao.deleteAll(collectionArtMasterList);
//            collectionAdminArtProductDao.deleteAll(collectionAdminArtProductMasterList);

            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public List CollectionFilter(FilterReqDto filterReqDto) {
        List filterList = new ArrayList<>();

        if (filterReqDto.getType().equals("All") && filterReqDto.getText().equals("All")) {
            List<CollectionMaster> collectionMasterList = collectionMasterDao.findAll();
            System.out.println("collectionMasterList: " + collectionMasterList.size());
            if (filterReqDto.getUserId() == null) {
                return collectionMasterList;
            }
            if (filterReqDto.getUserId() != null && !filterReqDto.getUserId().isEmpty()) {
                filterList = collectionMasterList.stream()
                        .filter(artMaster -> artMaster.getUserMaster() != null && artMaster.getUserMaster().getUserId().equals(filterReqDto.getUserId()))
                        .collect(Collectors.toList());
            }
        } else if (filterReqDto.getType().equals("File")) {
            List<CollectionMaster> list = collectionMasterDao.findByTitle(filterReqDto.getText());
            System.out.println("collectionMasterList 1: " + list.size());

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
            List<CollectionMaster> collectionMasterList = collectionMasterDao.findByArtMaster_ArtNameContaining(searchText);
            System.out.println("collectionMasterList!!!: " + collectionMasterList.size());
            List<ArtMaster> filteredArtMasterList = new ArrayList<>();
            if (filterReqDto.getUserId() == null || filterReqDto.getUserId().isEmpty()) {
                for (CollectionMaster collectionMaster : collectionMasterList) {
                    List<ArtMaster> artMasterList = collectionMaster.getArtMaster();
                    for (ArtMaster artMaster : artMasterList) {
                        if (artMaster.getArtName().contains(searchText)) {
                            filteredArtMasterList.add(artMaster);
                            System.out.println("rrrr...." + filteredArtMasterList.size());
                        }
                    }
                    filterList = filteredArtMasterList;
                }
            } else if (filterReqDto.getUserId() != null && !filterReqDto.getUserId().isEmpty()) {
                for (CollectionMaster collectionMaster : collectionMasterList) {
                    List<ArtMaster> artMasterList = collectionMaster.getArtMaster();
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

    @Override
    public Boolean addArtOrProductInCollection(AddArtOrProductCollectionRequestDto addArtOrProductCollectionRequestDto) {

        try
        {
            CollectionMaster collectionMaster=new CollectionMaster();
            if(addArtOrProductCollectionRequestDto.getCollectionId()==null)
            {
                return false;
            }
            collectionMaster=collectionMasterDao.getCollection(addArtOrProductCollectionRequestDto.getCollectionId());

            if(addArtOrProductCollectionRequestDto.getArtId()!=null) {
                ArtMaster artMaster = artMasterDao.getArtId(addArtOrProductCollectionRequestDto.getArtId());
                artMaster.setCollectionId(collectionMaster.getCollectionId());
                artMasterDao.save(artMaster);
                List<ArtMaster> artMasterList=collectionMaster.getArtMaster();
                artMasterList.add(artMaster);
                collectionMaster.setArtMaster(artMasterList);
                collectionMaster.setCount(collectionMaster.getCount()+1);
            }
            if(addArtOrProductCollectionRequestDto.getArtProductId()!=null) {
                ArtProductMaster artProductMaster = artProductMasterDao.getArtProductMaster(addArtOrProductCollectionRequestDto.getArtProductId());
                artProductMaster.setCollectionId(collectionMaster.getCollectionId());
                artProductMasterDao.save(artProductMaster);
                List<ArtProductMaster> artProductMasterList=collectionMaster.getArtProductMaster();
                artProductMasterList.add(artProductMaster);
                collectionMaster.setArtProductMaster(artProductMasterList);
                collectionMaster.setCount(collectionMaster.getCount()+1);
            }

            if(addArtOrProductCollectionRequestDto.getAdminArtProductId()!=null) {
                AdminArtProductMaster adminArtProductMaster = adminArtProductMasterDao.getAdminArtProductMaster(addArtOrProductCollectionRequestDto.getAdminArtProductId());
                adminArtProductMaster.setCollectionId(collectionMaster.getCollectionId());
                adminArtProductMasterDao.save(adminArtProductMaster);
                List<AdminArtProductMaster> adminArtProductMasterList=collectionMaster.getAdminArtProductMaster();
                adminArtProductMasterList.add(adminArtProductMaster);
                collectionMaster.setAdminArtProductMaster(adminArtProductMasterList);
                collectionMaster.setCount(collectionMaster.getCount()+1);
            }

            collectionMaster.setUpdatedDate(new Date());
            collectionMasterDao.save(collectionMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean CollectionIdWiseAddArtAndAdminProduct(AddCollectionArtAndAdminProductRequestDto addCollectionArtAndAdminProductRequestDto) {

       CollectionMaster collectionMaster=new CollectionMaster();

        try
        {
            collectionMaster=collectionMasterDao.findByCollectionId(addCollectionArtAndAdminProductRequestDto.getCollectionId()).get();
            if(collectionMaster==null)
            {
                throw new NullPointerException();
            }

            List<ArtMaster> artMasterList=new ArrayList<>();
//            List<ArtProductMaster> artProductMasterList=fileManagerMaster.getArtProductMaster();
            List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();

            if(!addCollectionArtAndAdminProductRequestDto.getArtIds().isEmpty())
            {
                artMasterList=collectionMaster.getArtMaster();
                for (String artId : addCollectionArtAndAdminProductRequestDto.getArtIds()) {
                    ArtMaster artMaster=artMasterDao.getArtId(artId);
                    artMaster.setCollectionId(collectionMaster.getCollectionId());
                    artMasterDao.save(artMaster);
                    artMasterList.add(artMaster);
                    collectionMaster.setArtMaster(artMasterList);
                    collectionMaster.setCount(collectionMaster.getCount()+1);
                }
            }

            if(!addCollectionArtAndAdminProductRequestDto.getAdminArtProductIds().isEmpty())
            {
                adminArtProductMasterList=collectionMaster.getAdminArtProductMaster();
                for (String adminArtProductId : addCollectionArtAndAdminProductRequestDto.getAdminArtProductIds()) {
                    AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.getAdminArtProductMaster(adminArtProductId);
                    adminArtProductMaster.setCollectionId(collectionMaster.getCollectionId());
                    adminArtProductMasterDao.save(adminArtProductMaster);

                    adminArtProductMasterList.add(adminArtProductMaster);
                    collectionMaster.setAdminArtProductMaster(adminArtProductMasterList);
                    collectionMaster.setCount(collectionMaster.getCount()+1);
                }
            }
            collectionMasterDao.save(collectionMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<CollectionMaster> getCollectionsByUserIdAndSort(CollectionSortRequest collectionSortRequest) {
//        FileManagerMasterRes fileManagerMasterRes=new FileManagerMasterRes();

        List<CollectionMaster> collectionMasterList=new ArrayList<>();

        UserMaster userMaster=new UserMaster();
        userMaster=userDao.findById(collectionSortRequest.getUserId()).get();

        Query query=new Query();
        query.addCriteria(Criteria.where("userMaster").is(userMaster));

        if(collectionSortRequest.getCategory().equalsIgnoreCase("All") ) {

        }else if(collectionSortRequest.getCategory()!=null || collectionSortRequest.getCategory()!="")
        {
            query.addCriteria(Criteria.where("category").is(collectionSortRequest.getCategory()));
        }

        if(collectionSortRequest.getSearchKey()!=null || !collectionSortRequest.getSearchKey().equalsIgnoreCase(""))
        {
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("artMaster.artName").regex(collectionSortRequest.getSearchKey()),
                    Criteria.where("artMaster.keywords").regex(collectionSortRequest.getSearchKey()),
                    Criteria.where("artMaster.arProductNo").regex(collectionSortRequest.getSearchKey()),
                    Criteria.where("adminArtProductMaster.adminArtProductName").regex(collectionSortRequest.getSearchKey(), "i"),
                    Criteria.where("adminArtProductMaster.artProductNo").regex(collectionSortRequest.getSearchKey(), "i"),
                    Criteria.where("adminArtProductMaster.artMasterDetails.keywords").regex(collectionSortRequest.getSearchKey(), "i")
            ));
        }


        List<CollectionMaster> list=new ArrayList();

        switch (collectionSortRequest.getOrder())
        {
            case "Newest":
                list=mongoTemplate.find(query,CollectionMaster.class);
                collectionMasterList.addAll(list);
                System.out.println(" list size ="+list.size());
                break;
            case "Oldest":
                list=mongoTemplate.find(query,CollectionMaster.class);
                Collections.reverse(list);
                collectionMasterList.addAll(list);
                System.out.println("  list size ="+list.size());
                break;
            case "A-Z":
                list=mongoTemplate.find(query,CollectionMaster.class);
                list.sort(Comparator.comparing(CollectionMaster::getTitle));
                collectionMasterList.addAll(list);
                System.out.println("  list size ="+list.size());
                break;
            case "Z-A":
                list=mongoTemplate.find(query,CollectionMaster.class);
                list.sort(Comparator.comparing(CollectionMaster::getTitle).reversed());
//                Collections.reverse(list);
                collectionMasterList.addAll(list);
                System.out.println("  list size ="+list.size());
                break;
            default:
                list=mongoTemplate.find(query,CollectionMaster.class);
                collectionMasterList.addAll(list);
                System.out.println("  list size ="+list.size());
                break;
        }
        return collectionMasterList;
    }

    @Override
    public List getCollectionByListSort(CollectionOrderReq collectionOrderReq) {

        List list=new ArrayList();
        List<CollectionMaster> collectionMasterList=new ArrayList<>();

        CollectionMaster collectionMaster=new CollectionMaster();
        collectionMaster=collectionMasterDao.findByCollectionId(collectionOrderReq.getCollectionId()).get();

        if(collectionMaster==null)
        {
            throw new NoValueFoundException("400","  No Value Found ");
        }

        List list1=new ArrayList();
        List list2=new ArrayList();

        Query query=new Query();
        query.addCriteria(Criteria.where("collectionId").is(collectionOrderReq.getCollectionId()));

        Query query1=new Query();
        query1.addCriteria(Criteria.where("collectionId").is(collectionOrderReq.getCollectionId()));


        if(collectionOrderReq.getSearchKey()!=null || !collectionOrderReq.getSearchKey().isEmpty()) {

            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("artName").regex(collectionOrderReq.getSearchKey()),
                    Criteria.where("arProductNo").regex(collectionOrderReq.getSearchKey()),
                    Criteria.where("keywords").regex(collectionOrderReq.getSearchKey())
            ));

            query1.addCriteria(new Criteria().orOperator(
                    Criteria.where("adminArtProductName").regex(collectionOrderReq.getSearchKey()),
                    Criteria.where("artProductUniqueNo").regex(collectionOrderReq.getSearchKey()),
                    Criteria.where("keywords").regex(collectionOrderReq.getSearchKey())
            ));
        }

        switch (collectionOrderReq.getOrder())
        {
            case "Newest":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                collectionMasterList.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                System.out.println("  list2 size ="+list2.size());
                collectionMasterList.addAll(list2);

//                list=mongoTemplate.find(query,CollectionMaster.class);
//                collectionMasterList.addAll(list);

//                collectionMasterList.sort((o1, o2) -> {
//            Date date1 = getSubmittedDate(o1);
//            Date date2 = getSubmittedDate(o2);
//            return date1.compareTo(date2);
//        });
                break;
            case "Oldest":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                collectionMasterList.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                System.out.println("  list2 size ="+list2.size());
                collectionMasterList.addAll(list2);
//                list=mongoTemplate.find(query,CollectionMaster.class);
//                Collections.reverse(list);
//                collectionMasterList.addAll(list);

//                collectionMasterList.sort((o1, o2) -> {
//                    Date date1 = getSubmittedDate(o1);
//                    Date date2 = getSubmittedDate(o2);
//                    return date1.compareTo(date2);
//                });

                Collections.reverse(collectionMasterList);
                break;
            case "A-Z":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                list1.sort(Comparator.comparing(CollectionArtMaster::getArtName));
                System.out.println("  list1 size ="+list1.size());
//                list1.sort(Comparator.comparing(ArtMaster::getArtName));
                collectionMasterList.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                list2.sort(Comparator.comparing(CollectionAdminArtProductMaster::getAdminArtProductName));
                System.out.println("  list2 size ="+list2.size());
//                list2.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
                collectionMasterList.addAll(list2);

//                list=mongoTemplate.find(query,CollectionMaster.class);
//                list.sort(Comparator.comparing(CollectionMaster::getTitle));
//                collectionMasterList.addAll(list);


//                collectionMasterList.sort(Comparator.comparing(CollectionMaster::getTitle));
                break;
            case "Z-A":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                list1.sort(Comparator.comparing(CollectionArtMaster::getArtName).reversed());
                System.out.println("  list1 size ="+list1.size());
                collectionMasterList.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                list2.sort(Comparator.comparing(CollectionAdminArtProductMaster::getAdminArtProductName).reversed());
                System.out.println("  list2 size ="+list2.size());
                collectionMasterList.addAll(list2);
//                list=mongoTemplate.find(query,CollectionMaster.class);
//                list.sort(Comparator.comparing(CollectionMaster::getTitle).reversed());
////                Collections.reverse(list);
//                collectionMasterList.addAll(list);
                break;

            case "Exclusive":
                query.addCriteria(Criteria.where("typeOfContent").is("Exclusive Art"));
                collectionMaster=mongoTemplate.findOne(query,CollectionMaster.class);
//                if( fileManagerMaster.getArtMaster().size()>0) {
//                    fileManagerMaster.getArtMaster().sort(Comparator.comparing(ArtMaster::getArtName).reversed());
//                    list.addAll(fileManagerMaster.getArtMaster());
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                list1.sort(Comparator.comparing(CollectionArtMaster::getArtName));
                collectionMasterList.addAll(list1);
//                }
//                if( fileManagerMaster.getAdminArtProductMaster().size()>0) {
//                    fileManagerMaster.getAdminArtProductMaster().sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName).reversed());
//                    list.addAll(fileManagerMaster.getAdminArtProductMaster());
                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                System.out.println("  list2 size ="+list2.size());
                list2.sort(Comparator.comparing(CollectionAdminArtProductMaster::getAdminArtProductName));
                collectionMasterList.addAll(list2);

//                }
                break;
            case "Featured":
                query.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
                collectionMaster=mongoTemplate.findOne(query,CollectionMaster.class);
//                if( fileManagerMaster.getArtMaster().size()>0) {
//                    fileManagerMaster.getArtMaster().sort(Comparator.comparing(ArtMaster::getArtName).reversed());
//                    list.addAll(fileManagerMaster.getArtMaster());
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                collectionMasterList.addAll(list1);
//                }
//                if( fileManagerMaster.getAdminArtProductMaster().size()>0) {
//                    fileManagerMaster.getAdminArtProductMaster().sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName).reversed());
//                    list.addAll(fileManagerMaster.getAdminArtProductMaster());
                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                System.out.println("  list2 size ="+list2.size());
                collectionMasterList.addAll(list2);
//                }
                break;

            default:
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                collectionMasterList.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                System.out.println("  list1 size ="+list2.size());
                collectionMasterList.addAll(list2);
//                list=mongoTemplate.find(query,CollectionMaster.class);
//                collectionMasterList.addAll(list);
                break;
        }
        return collectionMasterList;
    }

    @Override
    public Boolean deleteCollectionIdWiseAddArtAndAdminProduct(CollectionAddArtAndAdminProductRequestDto collectionAddArtAndAdminProductRequestDto) {

        CollectionMaster collectionMaster=new CollectionMaster();
        try
        {
            collectionMaster=collectionMasterDao.findById(collectionAddArtAndAdminProductRequestDto.getCollectionId()).get();

            List<ArtMaster> artMasterList=new ArrayList<>();
            artMasterList=collectionMaster.getArtMaster();

            List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
            adminArtProductMasterList= collectionMaster.getAdminArtProductMaster();

//            //new

            //new
            List<CollectionArtMaster> collectionArtMasterList=new ArrayList<>();
            List<CollectionAdminArtProductMaster> collectionAdminArtProductMasterArrayList=new ArrayList<>();
            //

            Query queryca=new Query();
            queryca.addCriteria(Criteria.where("collectionId").is(collectionAddArtAndAdminProductRequestDto.getCollectionId()));
            collectionArtMasterList=mongoTemplate.find(queryca,CollectionArtMaster.class);
            collectionAdminArtProductMasterArrayList=mongoTemplate.find(queryca,CollectionAdminArtProductMaster.class);

            if(!collectionArtMasterList.isEmpty())
            {
                for (String artId : collectionAddArtAndAdminProductRequestDto.getArtIds()) {
                    CollectionArtMaster collectionArtMaster=new CollectionArtMaster();
                    Query query=new Query();
                    query.addCriteria(Criteria.where("collectionId").is(collectionAddArtAndAdminProductRequestDto.getCollectionId()));
                    query.addCriteria(Criteria.where("artId").is(artId));

                    List<CollectionArtMaster> collectionArtMasters=new ArrayList<>();
                    collectionArtMasters=mongoTemplate.find(query,CollectionArtMaster.class);

                    collectionArtDao.deleteAll(collectionArtMasters);

                    List holeList=new ArrayList();
                    List listca=mongoTemplate.find(queryca,CollectionArtMaster.class);
                    List listcp=mongoTemplate.find(queryca,CollectionAdminArtProductMaster.class);
                    holeList.addAll(listca);
                    holeList.addAll(listcp);

                    collectionMaster.setList(holeList);
                    collectionMaster.setCount(collectionMaster.getCount()-1);
                    collectionMasterDao.save(collectionMaster);
                }
                collectionMasterDao.save(collectionMaster);
            }

            if(!adminArtProductMasterList.isEmpty())
            {
                for (String adminArtProductId : collectionAddArtAndAdminProductRequestDto.getAdminArtProductIds()) {

                    CollectionAdminArtProductMaster collectionAdminArtProductMaster=new CollectionAdminArtProductMaster();

                    Query query=new Query();
                    query.addCriteria(Criteria.where("collectionId").is(collectionAddArtAndAdminProductRequestDto.getCollectionId()));
                    query.addCriteria(Criteria.where("adminArtProductId").is(adminArtProductId));

                    List<CollectionAdminArtProductMaster> collectionAdminArtProductMasters=new ArrayList<>();
                    collectionAdminArtProductMasters=mongoTemplate.find(query,CollectionAdminArtProductMaster.class);
                    collectionAdminArtProductDao.deleteAll(collectionAdminArtProductMasters);
                    collectionMaster.setCount(collectionMaster.getCount()-1);
                    collectionMasterDao.save(collectionMaster);

                    List holeList=new ArrayList();
                    List listca=mongoTemplate.find(queryca,CollectionArtMaster.class);
                    List listcp=mongoTemplate.find(queryca,CollectionAdminArtProductMaster.class);
                    holeList.addAll(listca);
                    holeList.addAll(listcp);
                    collectionMaster.setList(holeList);
                }
                collectionMasterDao.save(collectionMaster);
            }

            //

//            if(!artMasterList.isEmpty())
//            {
//                for (String artId : collectionAddArtAndAdminProductRequestDto.getArtIds()) {
//
//                    ArtMaster artMaster1=new ArtMaster();
//                    artMaster1=artMasterDao.findByArtId(artId).get();
//
//                    System.out.println(" containe ="+artMasterList.contains(artMaster1));
//
//                   artMasterList.removeIf(artMaster -> artId.equals(artMaster.getArtId()));
//                    ArtMaster artMaster=new ArtMaster();
//                    artMaster=artMasterDao.findById(artId).get();
//                    artMaster.setCollectionId(null);
//                    artMasterDao.save(artMaster);
//                }
//                collectionMaster.setArtMaster(artMasterList);
//                collectionMasterDao.save(collectionMaster);
//            }
//
//            if(!adminArtProductMasterList.isEmpty())
//            {
//                for (String adminArtProductId : collectionAddArtAndAdminProductRequestDto.getAdminArtProductIds()) {
//                    adminArtProductMasterList.removeIf(adminArtProductMaster -> adminArtProductId.equals(adminArtProductMaster.getAdminArtProductId()));
//                    AdminArtProductMaster adminArtProductMaster=new AdminArtProductMaster();
//                    adminArtProductMaster=adminArtProductMasterDao.findById(adminArtProductId).get();
//                    adminArtProductMaster.setCollectionId(null);
//                    adminArtProductMasterDao.save(adminArtProductMaster);
//                }
//                collectionMaster.setAdminArtProductMaster(adminArtProductMasterList);
//                collectionMasterDao.save(collectionMaster);
//            }
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateCollectionCoverImage(UpdateCollectionCoverImageReq updateCollectionCoverImageReq) {

        try
        {
            CollectionMaster collectionMaster=new CollectionMaster();
            collectionMaster=collectionMasterDao.findById(updateCollectionCoverImageReq.getCollectionId()).get();
            collectionMaster.setCoverImage(updateCollectionCoverImageReq.getCoverImage());
            collectionMasterDao.save(collectionMaster);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean CollectionIdWiseAddArtAndAdminProduct1(AddCollArtAndAdminProductRequest addCollArtAndAdminProductRequest) {
//        FileManagerMaster fileManagerMaster=new FileManagerMaster();
        CollectionMaster collectionMaster=new CollectionMaster();
        try
        {
            System.out.println("  called ");
            collectionMaster=collectionMasterDao.findById(addCollArtAndAdminProductRequest.getCollectionId()).get();

            if(collectionMaster==null)
            {
                throw new NullPointerException();
            }

            List<ArtMaster> artMasterList=new ArrayList<>();
            List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();

            //new
            List<CollectionArtMaster> collectionArtMasterList=new ArrayList<>();
            List<CollectionAdminArtProductMaster> collectionAdminArtProductMasterArrayList=new ArrayList<>();
            //

            Query queryca=new Query();
            queryca.addCriteria(Criteria.where("collectionId").is(addCollArtAndAdminProductRequest.getCollectionId()));
            collectionArtMasterList=mongoTemplate.find(queryca,CollectionArtMaster.class);
            collectionAdminArtProductMasterArrayList=mongoTemplate.find(queryca,CollectionAdminArtProductMaster.class);

            artMasterList=collectionMaster.getArtMaster();
            adminArtProductMasterList=collectionMaster.getAdminArtProductMaster();

            List holeList=new ArrayList();

            System.out.println("  Thread is called ");
            for (AddArtAndProductReq addArtAndProductReq : addCollArtAndAdminProductRequest.getAddArtAndProductReqList()) {
                switch(addArtAndProductReq.getType())
                {
                    case "art":
                        System.out.println("  art ");
                        ArtMaster artMaster=artMasterDao.getArtId(addArtAndProductReq.getId());
                        artMaster.setCollectionId(collectionMaster.getCollectionId());
                        artMasterDao.save(artMaster);
                        artMasterList.add(artMaster);

                        CollectionArtMaster collectionArtMaster=new CollectionArtMaster();
                        BeanUtils.copyProperties(artMaster,collectionArtMaster);
                        collectionArtMaster.setCollectionId(collectionMaster.getCollectionId());
                        collectionArtMaster.setCategory(collectionMaster.getCategory());
                        collectionArtDao.save(collectionArtMaster);

                        System.out.println("  artmaster list ="+artMasterList.size());
                        collectionMaster.setArtMaster(artMasterList);

//                        collectionMaster.setList(collectionMaster.getList());
                        collectionMaster.setCount(collectionMaster.getCount()+1);
                        collectionMasterDao.save(collectionMaster);
                        break;
                    case "product":
                        System.out.println("  product ");
                        AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.getAdminArtProductMaster(addArtAndProductReq.getId());
                        adminArtProductMaster.setCollectionId(collectionMaster.getCollectionId());
                        adminArtProductMasterDao.save(adminArtProductMaster);

                        CollectionAdminArtProductMaster collectionAdminArtProductMaster=new CollectionAdminArtProductMaster();
                        BeanUtils.copyProperties(adminArtProductMaster,collectionAdminArtProductMaster);
                        collectionAdminArtProductMaster.setCollectionId(collectionMaster.getCollectionId());
                        collectionAdminArtProductMaster.setCategory(collectionMaster.getCategory());
                        collectionAdminArtProductDao.save(collectionAdminArtProductMaster);

                        adminArtProductMasterList.add(adminArtProductMaster);
//                        collectionMaster.getAdminArtProductMaster().addAll(adminArtProductMasterList);
//                        collectionMaster.setList(collectionMaster.getList());
                        collectionMaster.setAdminArtProductMaster(adminArtProductMasterList);
                        collectionMaster.setCount(collectionMaster.getCount()+1);

                        collectionMasterDao.save(collectionMaster);
                        break;
                }
            }

            CollectionMaster collectionMaster1=new CollectionMaster();
            collectionMaster1=collectionMasterDao.findById(addCollArtAndAdminProductRequest.getCollectionId()).get();

            List list1=new ArrayList();
            List list2=new ArrayList();
            list1=mongoTemplate.find(queryca,CollectionArtMaster.class);
            list2=mongoTemplate.find(queryca,CollectionAdminArtProductMaster.class);
            holeList.addAll(list1);
            holeList.addAll(list2);
            collectionMaster1.setList(holeList);
            collectionMasterDao.save(collectionMaster1);

            collectionMasterDao.save(collectionMaster);

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


    /*
     @Override
    public Boolean CollectionIdWiseAddArtAndAdminProduct1(AddCollArtAndAdminProductRequest addCollArtAndAdminProductRequest) {
//        FileManagerMaster fileManagerMaster=new FileManagerMaster();
        CollectionMaster collectionMaster=new CollectionMaster();
        try
        {
            System.out.println("  called ");
            collectionMaster=collectionMasterDao.findById(addCollArtAndAdminProductRequest.getCollectionId()).get();

            if(collectionMaster==null)
            {
                throw new NullPointerException();
            }

            List<ArtMaster> artMasterList=new ArrayList<>();
//            List<ArtProductMaster> artProductMasterList=fileManagerMaster.getArtProductMaster();
            List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();

            artMasterList=collectionMaster.getArtMaster();
            adminArtProductMasterList=collectionMaster.getAdminArtProductMaster();

//            List<ArtMaster> finalArtMasterList = artMasterList;
//            FileManagerMaster finalFileManagerMaster = fileManagerMaster;
//            List<AdminArtProductMaster> finalAdminArtProductMasterList = adminArtProductMasterList;
//            FileManagerMaster finalFileManagerMaster1 = fileManagerMaster;

//            Thread thread=new Thread(()->
//            {
            System.out.println("  Thread is called ");
            for (AddArtAndProductReq addArtAndProductReq : addCollArtAndAdminProductRequest.getAddArtAndProductReqList()) {
                switch(addArtAndProductReq.getType())
                {
                    case "art":
                        System.out.println("  art ");
                        ArtMaster artMaster=artMasterDao.getArtId(addArtAndProductReq.getId());
                        artMaster.setCollectionId(collectionMaster.getCollectionId());
                        artMasterDao.save(artMaster);
                        artMasterList.add(artMaster);
                        System.out.println("  artmaster list ="+artMasterList.size());
                        collectionMaster.setArtMaster(artMasterList);
//                        collectionMaster.getList().addAll(artMasterList);
//                        collectionMaster.setList(collectionMaster.getList());
                        collectionMaster.setCount(collectionMaster.getCount()+1);
                        collectionMasterDao.save(collectionMaster);
                        break;
                    case "product":
                        System.out.println("  product ");
                        AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.getAdminArtProductMaster(addArtAndProductReq.getId());
                        adminArtProductMaster.setCollectionId(collectionMaster.getCollectionId());
                        adminArtProductMasterDao.save(adminArtProductMaster);
                        adminArtProductMasterList.add(adminArtProductMaster);
//                        collectionMaster.getAdminArtProductMaster().addAll(adminArtProductMasterList);
//                        collectionMaster.setList(collectionMaster.getList());
                        collectionMaster.setAdminArtProductMaster(adminArtProductMasterList);
                        collectionMaster.setCount(collectionMaster.getCount()+1);
                        collectionMasterDao.save(collectionMaster);
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
     */


/*
  public List getAllCollectionsArtAndAdmiAProducts( CollectionSortReq collectionSortReq) {

        List list=new ArrayList();

        List<CollectionMaster> collectionMasterList = new ArrayList<>();

        UserMaster userMaster = new UserMaster();
        userMaster = userDao.getUserMaster(collectionSortReq.getUserId());

        Query query = new Query();
        query.addCriteria(Criteria.where("userMaster").is(userMaster));

        List<Criteria> criteriaList=new ArrayList<>();
        criteriaList.add(Criteria.where("userMaster").is(userMaster));



        if (collectionSortReq.getCategory() != null) {
            query.addCriteria(Criteria.where("category").is(collectionSortReq.getCategory()));
            criteriaList.add(Criteria.where("category").is(collectionSortReq.getCategory()));
        }

        if (collectionSortReq.getSearchKey() != null || !collectionSortReq.getSearchKey().equalsIgnoreCase("")) {
            query.addCriteria(new Criteria().orOperator(
                Criteria.where("artMaster.artName").regex(collectionSortReq.getSearchKey()),
                Criteria.where("artMaster.arProductNo").regex(collectionSortReq.getSearchKey()),
                Criteria.where("artMaster.keywords").regex(collectionSortReq.getSearchKey()),
                Criteria.where("adminArtProductMaster.adminArtProductName").regex(collectionSortReq.getSearchKey()),
                Criteria.where("adminArtProductMaster.artProductUniqueNo").regex(collectionSortReq.getSearchKey()),
                Criteria.where("adminArtProductMaster.keywords").regex(collectionSortReq.getSearchKey())
        ));
        }



        if(collectionSortReq.getOrder().equalsIgnoreCase("Exclusive"))
        {
            query.addCriteria(Criteria.where("typeOfContent").is("Exclusive Art"));
            criteriaList.add(Criteria.where("typeOfContent").is("Exclusive Art"));
        }

        if(collectionSortReq.getOrder().equalsIgnoreCase("Featured"))
        {
            query.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
            criteriaList.add(Criteria.where("typeOfContent").is("Featured Art"));
        }


        collectionMasterList = mongoTemplate.find(query, CollectionMaster.class);
        System.out.println(" list size =" + collectionMasterList.size());

        List list1=new ArrayList();

        List list2=new ArrayList();

        if (!collectionMasterList.isEmpty()) {
            List finalList2 = list2;
            List finalList1 = list1;
            collectionMasterList.stream().parallel().forEach(collectionMaster -> {

                if (collectionMaster.getArtMaster() != null) {
                    finalList1.addAll(collectionMaster.getArtMaster());
                }

                if (collectionMaster.getAdminArtProductMaster() != null) {
                    finalList2.addAll(collectionMaster.getAdminArtProductMaster());
                }
            });
        }






        switch (collectionSortReq.getOrder())
        {
            case "Newest":
//                list1=mongoTemplate.find(query,ArtMaster.class);
//                System.out.println("  list1 size ="+list1.size());
                list.addAll(list1);
                System.out.println("  list1 size ="+list1.size());

//                list2=mongoTemplate.find(query1,AdminArtProductMaster.class);
//                System.out.println("  list2 size ="+list2.size());
                list2.addAll(list2);
                System.out.println("  list2 size ="+list2.size());

//                list=mongoTemplate.find(query,CollectionMaster.class);
//                collectionMasterList.addAll(list);

//                collectionMasterList.sort((o1, o2) -> {
//            Date date1 = getSubmittedDate(o1);
//            Date date2 = getSubmittedDate(o2);
//            return date1.compareTo(date2);
//        });
                break;
            case "Oldest":
//                list1=mongoTemplate.find(query,ArtMaster.class);
//                System.out.println("  list1 size ="+list1.size());
                list.addAll(list1);
//                collectionMasterList.addAll(list1);

//                list2=mongoTemplate.find(query1,AdminArtProductMaster.class);
//                System.out.println("  list2 size ="+list2.size());
                list.addAll(list2);
//                collectionMasterList.addAll(list2);
//                list=mongoTemplate.find(query,CollectionMaster.class);
//                Collections.reverse(list);
//                collectionMasterList.addAll(list);

                collectionMasterList.sort((o1, o2) -> {
                    Date date1 = getSubmittedDate(o1);
                    Date date2 = getSubmittedDate(o2);
                    return date1.compareTo(date2);
                });
                Collections.reverse(collectionMasterList);
                break;
            case "A-Z":
//                list1=mongoTemplate.find(query,ArtMaster.class);
                list1.sort(Comparator.comparing(ArtMaster::getArtName));
//                System.out.println("  list1 size ="+list1.size());
//                collectionMasterList.addAll(list1);
                list.addAll(list1);

//                list2=mongoTemplate.find(query1,AdminArtProductMaster.class);
                list2.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
//                System.out.println("  list2 size ="+list2.size());
//                collectionMasterList.addAll(list2);
                list.addAll(list2);
//                list=mongoTemplate.find(query,CollectionMaster.class);
//                list.sort(Comparator.comparing(CollectionMaster::getTitle));
//                collectionMasterList.addAll(list);

                collectionMasterList.sort(Comparator.comparing(CollectionMaster::getTitle));
                break;
            case "Z-A":
//                list1=mongoTemplate.find(query,ArtMaster.class);
                list1.sort(Comparator.comparing(ArtMaster::getArtName).reversed());
//                System.out.println("  list1 size ="+list1.size());
//                collectionMasterList.addAll(list1);
                list.addAll(list1);

//                list2=mongoTemplate.find(query1,AdminArtProductMaster.class);
                list2.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName).reversed());
//                System.out.println("  list2 size ="+list2.size());
//                collectionMasterList.addAll(list2);
                list.addAll(list2);
//                list=mongoTemplate.find(query,CollectionMaster.class);
//                list.sort(Comparator.comparing(CollectionMaster::getTitle).reversed());
////                Collections.reverse(list);
//                collectionMasterList.addAll(list);
                break;

            case "Exclusive":
//                query.addCriteria(Criteria.where("typeOfContent").is("Exclusive Art"));
//                collectionMaster=mongoTemplate.findOne(query,CollectionMaster.class);
//                if( fileManagerMaster.getArtMaster().size()>0) {
//                    fileManagerMaster.getArtMaster().sort(Comparator.comparing(ArtMaster::getArtName).reversed());
//                    list.addAll(fileManagerMaster.getArtMaster());
//                list1=mongoTemplate.find(query,ArtMaster.class);
//                System.out.println("  list1 size ="+list1.size());
//                list1.sort(Comparator.comparing(ArtMaster::getArtName));
//                collectionMasterList.addAll(list1);
//                }
//                if( fileManagerMaster.getAdminArtProductMaster().size()>0) {
//                    fileManagerMaster.getAdminArtProductMaster().sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName).reversed());
//                    list.addAll(fileManagerMaster.getAdminArtProductMaster());
//                list2=mongoTemplate.find(query1,AdminArtProductMaster.class);
//                System.out.println("  list2 size ="+list2.size());
//                list2.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
//                collectionMasterList.addAll(list2);

                list.addAll(list1);
                list.addAll(list2);

//                }
                break;
            case "Featured":
//                query.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
//                collectionMaster=mongoTemplate.findOne(query,CollectionMaster.class);
////                if( fileManagerMaster.getArtMaster().size()>0) {
////                    fileManagerMaster.getArtMaster().sort(Comparator.comparing(ArtMaster::getArtName).reversed());
////                    list.addAll(fileManagerMaster.getArtMaster());
//                list1=mongoTemplate.find(query,ArtMaster.class);
//                System.out.println("  list1 size ="+list1.size());
//                collectionMasterList.addAll(list1);
////                }
////                if( fileManagerMaster.getAdminArtProductMaster().size()>0) {
////                    fileManagerMaster.getAdminArtProductMaster().sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName).reversed());
////                    list.addAll(fileManagerMaster.getAdminArtProductMaster());
//                list2=mongoTemplate.find(query1,AdminArtProductMaster.class);
//                System.out.println("  list2 size ="+list2.size());
//                collectionMasterList.addAll(list2);
////                }

                list.addAll(list1);
                list.addAll(list2);
                break;
            default:
                list.addAll(list1);
                list.addAll(list2);
                System.out.println("  list size ="+list.size());

//                list1=mongoTemplate.find(query,ArtMaster.class);
//                System.out.println("  list1 size ="+list1.size());
//                collectionMasterList.addAll(list1);
//
//                list2=mongoTemplate.find(query1,AdminArtProductMaster.class);
//                System.out.println("  list1 size ="+list2.size());
//                collectionMasterList.addAll(list2);
//                list=mongoTemplate.find(query,CollectionMaster.class);
//                collectionMasterList.addAll(list);
                break;
        }

        return list;
    }
 */
    @Override
    public List getAllCollectionsArtAndAdmiAProducts( CollectionSortReq collectionSortReq) {

        UserMaster userMaster = new UserMaster();
        userMaster = userDao.findById(collectionSortReq.getUserId()).get();

        System.out.println("  user Id ="+userMaster.getUserId());
        List list=new ArrayList();
//        List<CollectionMaster> collectionMasterList=new ArrayList<>();

//        CollectionMaster collectionMaster=new CollectionMaster();
//        collectionMaster=collectionMasterDao.findByCollectionId(collectionSortReq.getCollectionId()).get();

//        if(collectionMaster==null)
//        {
//            throw new NoValueFoundException("400","  No Value Found ");
//        }

        List list1=new ArrayList();
        List list2=new ArrayList();

        Query query=new Query();
        query.addCriteria(Criteria.where("userMaster").is(userMaster));

        Query query1=new Query();
        query1.addCriteria(Criteria.where("userMaster").is(userMaster));

//        if(collectionSortReq.getCategory()!=null || !collectionSortReq.getCategory().equalsIgnoreCase("")){
//            query.addCriteria(Criteria.where("category").is(collectionSortReq.getCategory()));
//            query1.addCriteria(Criteria.where("category").is(collectionSortReq.getCategory()));
//        }


        if(collectionSortReq.getSearchKey()!=null || !collectionSortReq.getSearchKey().equalsIgnoreCase("")){

            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("artName").regex(collectionSortReq.getSearchKey()),
                    Criteria.where("arProductNo").regex(collectionSortReq.getSearchKey()),
                    Criteria.where("keywords").regex(collectionSortReq.getSearchKey())
            ));

            query1.addCriteria(new Criteria().orOperator(
                    Criteria.where("adminArtProductName").regex(collectionSortReq.getSearchKey()),
                    Criteria.where("artProductUniqueNo").regex(collectionSortReq.getSearchKey()),
                    Criteria.where("keywords").regex(collectionSortReq.getSearchKey())
            ));
        }

        switch (collectionSortReq.getOrder())
        {
            case "Newest":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                System.out.println("  list2 size ="+list2.size());
                list.addAll(list2);

//                list=mongoTemplate.find(query,CollectionMaster.class);
//                collectionMasterList.addAll(list);

//                collectionMasterList.sort((o1, o2) -> {
//            Date date1 = getSubmittedDate(o1);
//            Date date2 = getSubmittedDate(o2);
//            return date1.compareTo(date2);
//        });
                break;
            case "Oldest":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                Collections.reverse(list1);
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                System.out.println("  list2 size ="+list2.size());
                Collections.reverse(list2);
                list.addAll(list2);
//                list=mongoTemplate.find(query,CollectionMaster.class);
//                Collections.reverse(list);
//                collectionMasterList.addAll(list);

//                collectionMasterList.sort((o1, o2) -> {
//                    Date date1 = getSubmittedDate(o1);
//                    Date date2 = getSubmittedDate(o2);
//                    return date1.compareTo(date2);
//                });

//                Collections.reverse(collectionMasterList);
                break;
            case "A-Z":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                list1.sort(Comparator.comparing(CollectionArtMaster::getArtName));
                System.out.println("  list1 size ="+list1.size());
//                list1.sort(Comparator.comparing(ArtMaster::getArtName));
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                list2.sort(Comparator.comparing(CollectionAdminArtProductMaster::getAdminArtProductName));
                System.out.println("  list2 size ="+list2.size());
//                list2.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
                list.addAll(list2);

//                list=mongoTemplate.find(query,CollectionMaster.class);
//                list.sort(Comparator.comparing(CollectionMaster::getTitle));
//                collectionMasterList.addAll(list);


//                collectionMasterList.sort(Comparator.comparing(CollectionMaster::getTitle));
                break;
            case "Z-A":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                list1.sort(Comparator.comparing(CollectionArtMaster::getArtName).reversed());
                System.out.println("  list1 size ="+list1.size());
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                list2.sort(Comparator.comparing(CollectionAdminArtProductMaster::getAdminArtProductName).reversed());
                System.out.println("  list2 size ="+list2.size());
                list.addAll(list2);
//                list=mongoTemplate.find(query,CollectionMaster.class);
//                list.sort(Comparator.comparing(CollectionMaster::getTitle).reversed());
////                Collections.reverse(list);
//                collectionMasterList.addAll(list);
                break;

            case "Exclusive":
                query.addCriteria(Criteria.where("typeOfContent").is("Exclusive Art"));
//                collectionMaster=mongoTemplate.findOne(query,CollectionMaster.class);
//                if( fileManagerMaster.getArtMaster().size()>0) {
//                    fileManagerMaster.getArtMaster().sort(Comparator.comparing(ArtMaster::getArtName).reversed());
//                    list.addAll(fileManagerMaster.getArtMaster());
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                list1.sort(Comparator.comparing(CollectionArtMaster::getArtName));
                list.addAll(list1);
//                }
//                if( fileManagerMaster.getAdminArtProductMaster().size()>0) {
//                    fileManagerMaster.getAdminArtProductMaster().sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName).reversed());
//                    list.addAll(fileManagerMaster.getAdminArtProductMaster());


//                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
//                System.out.println("  list2 size ="+list2.size());
//                list2.sort(Comparator.comparing(CollectionAdminArtProductMaster::getAdminArtProductName));
//                collectionMasterList.addAll(list2);

//                }
                break;
            case "Featured":
                query.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
//                collectionMaster=mongoTemplate.findOne(query,CollectionMaster.class);
//                if( fileManagerMaster.getArtMaster().size()>0) {
//                    fileManagerMaster.getArtMaster().sort(Comparator.comparing(ArtMaster::getArtName).reversed());
//                    list.addAll(fileManagerMaster.getArtMaster());
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                list.addAll(list1);
//                }
//                if( fileManagerMaster.getAdminArtProductMaster().size()>0) {
//                    fileManagerMaster.getAdminArtProductMaster().sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName).reversed());
//                    list.addAll(fileManagerMaster.getAdminArtProductMaster());

//                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
//                System.out.println("  list2 size ="+list2.size());
//                collectionMasterList.addAll(list2);
//                }
                break;

            default:
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                System.out.println("  list1 size ="+list2.size());
                list.addAll(list2);
//                list=mongoTemplate.find(query,CollectionMaster.class);
//                collectionMasterList.addAll(list);
                break;
        }
        return list;
    }

    @Override
    public List getCollectionListByUserIdAndSort(String collectionId) {
        List<CollectionMaster> collectionMasterList=new ArrayList();
//        collectionMasterList= collectionMasterDao.findByUserMasterUserId(userId);
        Query query=new Query();
        query.addCriteria(Criteria.where("collectionId").is(collectionId));

        collectionMasterList=mongoTemplate.find(query,CollectionMaster.class);

        List<CollectionArtMaster> collectionArtMasters=new ArrayList<>();
        collectionArtMasters=mongoTemplate.find(query,CollectionArtMaster.class);

        List<CollectionAdminArtProductMaster> collectionAdminArtProductMasters=new ArrayList<>();
        collectionAdminArtProductMasters=mongoTemplate.find(query,CollectionAdminArtProductMaster.class);

        List<Object> combineList=new ArrayList<>();
        combineList.addAll(collectionArtMasters);
        combineList.addAll(collectionAdminArtProductMasters);

        return collectionMasterList.parallelStream().map(collectionMaster -> {
            // Create the FileManagerMasterRes object
            CollectionMasterRes collectionMasterRes = new CollectionMasterRes();

            // Copy properties from FileManagerMaster to FileManagerMasterRes
            BeanUtils.copyProperties(collectionMaster, collectionMasterRes);

            // Combine the lists into one
            List<Object> combinedList = Stream.concat(
                    (collectionMaster.getArtMaster() != null ? collectionMaster.getArtMaster().stream() : Stream.empty()),
                    (collectionMaster.getAdminArtProductMaster() != null ? collectionMaster.getAdminArtProductMaster().stream() : Stream.empty())
            ).collect(Collectors.toList());

            System.out.println(" combinedList   "+combinedList.size());



            // Set the combined list
            collectionMasterRes.setList(combinedList);

            return collectionMasterRes;
        }).collect(Collectors.toList());
    }

    @Override
    public List getAllCollectionsArtAndAdminProducts(CollectionSortReq collectionSortReq) {

        UserMaster userMaster = new UserMaster();
        userMaster = userDao.findById(collectionSortReq.getUserId()).get();

        System.out.println("  user Id ="+userMaster.getUserId());
        List list=new ArrayList();
//        List<CollectionMaster> collectionMasterList=new ArrayList<>();

//        CollectionMaster collectionMaster=new CollectionMaster();
//        collectionMaster=collectionMasterDao.findByCollectionId(collectionSortReq.getCollectionId()).get();

//        if(collectionMaster==null)
//        {
//            throw new NoValueFoundException("400","  No Value Found ");
//        }

        List list1=new ArrayList();
        List list2=new ArrayList();

        Query query=new Query();
        query.addCriteria(Criteria.where("userMaster").is(userMaster));

        Query query1=new Query();
        query1.addCriteria(Criteria.where("userMaster").is(userMaster));

//        if(collectionSortReq.getCategory()!=null || !collectionSortReq.getCategory().equalsIgnoreCase("")){
//            query.addCriteria(Criteria.where("category").is(collectionSortReq.getCategory()));
//            query1.addCriteria(Criteria.where("category").is(collectionSortReq.getCategory()));
//        }


        if(collectionSortReq.getSearchKey()!=null || !collectionSortReq.getSearchKey().equalsIgnoreCase("")){

            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("artName").regex(collectionSortReq.getSearchKey()),
                    Criteria.where("arProductNo").regex(collectionSortReq.getSearchKey()),
                    Criteria.where("keywords").regex(collectionSortReq.getSearchKey())
            ));

            query1.addCriteria(new Criteria().orOperator(
                    Criteria.where("adminArtProductName").regex(collectionSortReq.getSearchKey()),
                    Criteria.where("artProductUniqueNo").regex(collectionSortReq.getSearchKey()),
                    Criteria.where("keywords").regex(collectionSortReq.getSearchKey())
            ));
        }

        switch (collectionSortReq.getOrder())
        {
            case "Newest":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                System.out.println("  list2 size ="+list2.size());
                list.addAll(list2);

//                list=mongoTemplate.find(query,CollectionMaster.class);
//                collectionMasterList.addAll(list);

//                collectionMasterList.sort((o1, o2) -> {
//            Date date1 = getSubmittedDate(o1);
//            Date date2 = getSubmittedDate(o2);
//            return date1.compareTo(date2);
//        });
                break;
            case "Oldest":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                Collections.reverse(list1);
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                System.out.println("  list2 size ="+list2.size());
                Collections.reverse(list2);
                list.addAll(list2);
//                list=mongoTemplate.find(query,CollectionMaster.class);
//                Collections.reverse(list);
//                collectionMasterList.addAll(list);

//                collectionMasterList.sort((o1, o2) -> {
//                    Date date1 = getSubmittedDate(o1);
//                    Date date2 = getSubmittedDate(o2);
//                    return date1.compareTo(date2);
//                });

//                Collections.reverse(collectionMasterList);
                break;
            case "A-Z":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                list1.sort(Comparator.comparing(CollectionArtMaster::getArtName));
                System.out.println("  list1 size ="+list1.size());
//                list1.sort(Comparator.comparing(ArtMaster::getArtName));
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                list2.sort(Comparator.comparing(CollectionAdminArtProductMaster::getAdminArtProductName));
                System.out.println("  list2 size ="+list2.size());
//                list2.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
                list.addAll(list2);

//                list=mongoTemplate.find(query,CollectionMaster.class);
//                list.sort(Comparator.comparing(CollectionMaster::getTitle));
//                collectionMasterList.addAll(list);


//                collectionMasterList.sort(Comparator.comparing(CollectionMaster::getTitle));
                break;
            case "Z-A":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                list1.sort(Comparator.comparing(CollectionArtMaster::getArtName).reversed());
                System.out.println("  list1 size ="+list1.size());
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                list2.sort(Comparator.comparing(CollectionAdminArtProductMaster::getAdminArtProductName).reversed());
                System.out.println("  list2 size ="+list2.size());
                list.addAll(list2);
//                list=mongoTemplate.find(query,CollectionMaster.class);
//                list.sort(Comparator.comparing(CollectionMaster::getTitle).reversed());
////                Collections.reverse(list);
//                collectionMasterList.addAll(list);
                break;

            case "Exclusive":
                query.addCriteria(Criteria.where("typeOfContent").is("Exclusive Art"));
//                collectionMaster=mongoTemplate.findOne(query,CollectionMaster.class);
//                if( fileManagerMaster.getArtMaster().size()>0) {
//                    fileManagerMaster.getArtMaster().sort(Comparator.comparing(ArtMaster::getArtName).reversed());
//                    list.addAll(fileManagerMaster.getArtMaster());
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                list1.sort(Comparator.comparing(CollectionArtMaster::getArtName));
                list.addAll(list1);
//                }
//                if( fileManagerMaster.getAdminArtProductMaster().size()>0) {
//                    fileManagerMaster.getAdminArtProductMaster().sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName).reversed());
//                    list.addAll(fileManagerMaster.getAdminArtProductMaster());


//                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
//                System.out.println("  list2 size ="+list2.size());
//                list2.sort(Comparator.comparing(CollectionAdminArtProductMaster::getAdminArtProductName));
//                collectionMasterList.addAll(list2);

//                }
                break;
            case "Featured":
                query.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
//                collectionMaster=mongoTemplate.findOne(query,CollectionMaster.class);
//                if( fileManagerMaster.getArtMaster().size()>0) {
//                    fileManagerMaster.getArtMaster().sort(Comparator.comparing(ArtMaster::getArtName).reversed());
//                    list.addAll(fileManagerMaster.getArtMaster());
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                list.addAll(list1);
//                }
//                if( fileManagerMaster.getAdminArtProductMaster().size()>0) {
//                    fileManagerMaster.getAdminArtProductMaster().sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName).reversed());
//                    list.addAll(fileManagerMaster.getAdminArtProductMaster());

//                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
//                System.out.println("  list2 size ="+list2.size());
//                collectionMasterList.addAll(list2);
//                }
                break;

            default:
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                System.out.println("  list1 size ="+list1.size());
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                System.out.println("  list1 size ="+list2.size());
                list.addAll(list2);
//                list=mongoTemplate.find(query,CollectionMaster.class);
//                collectionMasterList.addAll(list);
                break;
        }
        return list;
    }

    @Override
    public List<CollectionMasterRes> getCollectionsByUserIdAndSor(CollectionSortRequest collectionSortRequest) {

        List<CollectionMasterRes> collectionMasterResList=new ArrayList<>();

        UserMaster userMaster=new UserMaster();
        userMaster=userDao.findById(collectionSortRequest.getUserId()).get();

//        List<CollectionMaster> collectionMasterList=new ArrayList<>();
//        collectionMasterList=collectionMasterDao.findAllByUserMaster(userMaster);


        Query query=new Query();
        query.addCriteria(Criteria.where("userMaster").is(userMaster));
        query.addCriteria(Criteria.where("category").is(collectionSortRequest.getCategory()));

        query.addCriteria(new Criteria().orOperator(
                Criteria.where("artMaster.artName").regex(collectionSortRequest.getSearchKey(), "i"),
                Criteria.where("artMaster.arProductNo").regex(collectionSortRequest.getSearchKey(), "i"),
                Criteria.where("artMaster.keywords").regex(collectionSortRequest.getSearchKey(), "i"),
                Criteria.where("adminArtProductMaster.adminArtProductName").regex(collectionSortRequest.getSearchKey(), "i"),
                Criteria.where("adminArtProductMaster.artProductUniqueNo").regex(collectionSortRequest.getSearchKey(), "i"),
                Criteria.where("adminArtProductMaster.keywords").regex(collectionSortRequest.getSearchKey(), "i")
        ));

        List<CollectionArtMaster> collectionArtMasters=new ArrayList<>();
        List<CollectionAdminArtProductMaster> collectionAdminArtProductMasterList=new ArrayList<>();


        return null;
    }

    @Override
    public List getCollectionResponseByCollectionIdWise(CollectionOrderReq collectionOrderReq) {

        List list=new ArrayList();

        CollectionMasterRes collectionMasterRes=new CollectionMasterRes();

        CollectionMaster collectionMaster=new CollectionMaster();
        collectionMaster=collectionMasterDao.findByCollectionId(collectionOrderReq.getCollectionId()).get();

        BeanUtils.copyProperties(collectionMaster,collectionMasterRes);

        if(collectionMaster == null) {
         throw new NoValueFoundException("400"," CollectionId Should be not Null");
        }


        List list1=new ArrayList();
        List list2=new ArrayList();

        Query query=new Query();
        query.addCriteria(Criteria.where("collectionId").is(collectionOrderReq.getCollectionId()));

        Query query1=new Query();
        query1.addCriteria(Criteria.where("collectionId").is(collectionOrderReq.getCollectionId()));

//        if(collectionSortReq.getCategory()!=null || !collectionSortReq.getCategory().equalsIgnoreCase("")){
//            query.addCriteria(Criteria.where("category").is(collectionSortReq.getCategory()));
//            query1.addCriteria(Criteria.where("category").is(collectionSortReq.getCategory()));
//        }


        if(collectionOrderReq.getSearchKey()!=null || !collectionOrderReq.getSearchKey().equalsIgnoreCase("")){

            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("artName").regex(collectionOrderReq.getSearchKey()),
                    Criteria.where("arProductNo").regex(collectionOrderReq.getSearchKey()),
                    Criteria.where("keywords").regex(collectionOrderReq.getSearchKey())
            ));

            query1.addCriteria(new Criteria().orOperator(
                    Criteria.where("adminArtProductName").regex(collectionOrderReq.getSearchKey()),
                    Criteria.where("artProductUniqueNo").regex(collectionOrderReq.getSearchKey()),
                    Criteria.where("keywords").regex(collectionOrderReq.getSearchKey())
            ));
        }

        switch (collectionOrderReq.getOrder())
        {
            case "Newest":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                list.addAll(list2);
                break;
            case "Oldest":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                Collections.reverse(list1);
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                Collections.reverse(list2);
                list.addAll(list2);
                break;
            case "A-Z":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                list1.sort(Comparator.comparing(CollectionArtMaster::getArtName));
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                list2.sort(Comparator.comparing(CollectionAdminArtProductMaster::getAdminArtProductName));
                list.addAll(list2);
            break;
            case "Z-A":
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                list1.sort(Comparator.comparing(CollectionArtMaster::getArtName).reversed());
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                list2.sort(Comparator.comparing(CollectionAdminArtProductMaster::getAdminArtProductName).reversed());
                list.addAll(list2);
                break;

            case "Exclusive":
                query.addCriteria(Criteria.where("typeOfContent").is("Exclusive Art"));
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                list1.sort(Comparator.comparing(CollectionArtMaster::getArtName));
                list.addAll(list1);

                break;
            case "Featured":
                query.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                list.addAll(list1);
                break;

            default:
                list1=mongoTemplate.find(query,CollectionArtMaster.class);
                list.addAll(list1);

                list2=mongoTemplate.find(query1,CollectionAdminArtProductMaster.class);
                list.addAll(list2);
                break;
        }

        System.out.println("  List 1 size ="+list1.size());
        System.out.println("  List 2 size ="+list2.size());
        System.out.println("  List   size ="+list.size());

        return list;
    }

    @Override
    public CollectionCountRes CollectionCountRes(String userId) {

        CollectionCountRes collectionCountRes=new CollectionCountRes();
        int count1=0;
        int count2=0;

        UserMaster userMaster=new UserMaster();
        userMaster=userDao.findByUserId(userId).get();


        List<CollectionMaster> collectionMasterList=new ArrayList<>();
        collectionMasterList=collectionMasterDao.findAllByUserMaster(userMaster);
        count1=collectionMasterList.size();

        System.out.println("  count 1 ="+count1);
        Query query=new Query();
        query.addCriteria(Criteria.where("userMaster").is(userMaster));

        List<CollectionArtMaster> collectionArtMasters=new ArrayList<>();
        collectionArtMasters=mongoTemplate.find(query,CollectionArtMaster.class);
        count2=count2+collectionArtMasters.size();

        List<CollectionAdminArtProductMaster> collectonAdminArtProductList=new ArrayList<>();
        collectonAdminArtProductList=mongoTemplate.find(query,CollectionAdminArtProductMaster.class);
        count2=count2+collectonAdminArtProductList.size();
        System.out.println("  count 2 ="+count2);
        collectionCountRes.setCollectionCounts(count1);
        collectionCountRes.setCollectionsFileCounts(count2);

        return collectionCountRes;
    }


    private static Date getSubmittedDate(Object obj) {
        if (obj instanceof ArtMaster) {
            return ((ArtMaster) obj).getSubmittedDate();
        } else if (obj instanceof AdminArtProductMaster) {
            return ((AdminArtProductMaster) obj).getSubmittedDate();
        }
        return null;
    }



}
