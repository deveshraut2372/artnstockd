package com.zplus.ArtnStockMongoDB.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zplus.ArtnStockMongoDB.dao.*;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterFilterReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterUpdateReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.SortRequest;
import com.zplus.ArtnStockMongoDB.dto.res.ArtMasterRes;
import com.zplus.ArtnStockMongoDB.dto.res.MainResDto;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.ArtMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArtMasterServiceImpl implements ArtMasterService {
    private static final int N = 0;

    @Autowired
    private ContributorArtMarkupDao contributorArtMarkupDao;

    @Autowired
    private ArtMasterDao artMasterDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private KeywordCountMasterDao keywordCountMasterDao;

    @Autowired
    private UserMasterServiceImpl userMasterService;

    @Autowired
    private UserDao userDao;
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private StyleMasterDao styleMasterDao;
    @Autowired
    private ImageMasterDao imageMasterDao;
    @Autowired
    private AdminMasterDao adminMasterDao;
    @Autowired
    private OrientationMasterDao orientationMasterDao;

    @Autowired
    private FileUploadLimitRepository fileUploadLimitRepository;

    @Autowired
    private ReleaseMasterDao releaseMasterDao;

    @Autowired
    private ArtDetailsMasterDao artDetailsMasterDao;

    @Autowired
    private AdminArtProductMasterDao adminArtProductMasterDao;

    @Autowired
    private KeywordsMasterDao keywordsMasterDao;


    @Autowired
    private UserMessageDao userMessageDao;


    @Autowired
    private MediumMasterDao mediumMasterDao;



    //  create Art Master
    @Override
    public ArtMasterRes createArtMaster(ArtMasterReqDto artMasterReqDto) {
        ArtMasterRes artMasterRes=new ArtMasterRes();

        Boolean flag = false;
        ArtMaster artMaster1 = new ArtMaster();
        ArtMaster artMaster2 = new ArtMaster();


        MainResDto mainResDto=new MainResDto();

        // new code
        Boolean cflag=checkUserArts(artMasterReqDto.getUserId());
        if(cflag)
        {

        }else {
        mainResDto.setFlag(false);
        mainResDto.setResponseCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        mainResDto.setMessage(" your Art approved percentage is very low you should be fill up a form for upload your art !");
//        return  mainResDto;
        }

        SubjectMaster subjectMaster = subjectDao.getSubject(artMasterReqDto.getSubjectId());
        System.out.println("subjectMaster...." + subjectMaster.toString());
        StyleMaster styleMaster = styleMasterDao.getStyle(artMasterReqDto.getStyleId());
        System.out.println("styleMaster..." + styleMaster);

        MediumMaster mediumMaster=new MediumMaster();
        mediumMaster=mediumMasterDao.findById(artMasterReqDto.getArtMedium()).get();


        ImageMaster imageMaster = imageMasterDao.getImage(artMasterReqDto.getImageId());
        System.out.println("imageMaster..." + imageMaster.toString());
        UserMaster userMaster = userDao.getUserMaster(artMasterReqDto.getUserId());
        System.out.println("userMaster..." + userMaster.getUserId());
        AddDetailsMaster addDetailsMaster=artDetailsMasterDao.getArtById(artMasterReqDto.getArtDetailsId());
        System.out.println("userMaster..." + addDetailsMaster.getArtDetailsId());

        imageMaster.setImageId(artMasterReqDto.getImageId());
        //new change
        imageMaster.setImageOrientation(artMasterReqDto.getImageOrientation());
        artMaster1.setImageMaster(imageMaster);
        imageMasterDao.save(imageMaster);



        userMaster.setUserId(artMasterReqDto.getUserId());
        artMaster1.setUserMaster(userMaster);
        artMaster1.setSubjectMaster(subjectMaster);
        artMaster1.setStyleMaster(styleMaster);
        artMaster1.setMediumMaster(mediumMaster);
        artMaster1.setAddDetailsMaster(addDetailsMaster);
        artMaster1.setStatus("InReview");

        List<ReleaseMaster> releaseMasterList=new ArrayList<>();
        Query query=new Query();
        Criteria criteria=Criteria.where("addDetailsMaster.artDetailsId").is(addDetailsMaster.getArtDetailsId());
//        Criteria criteria1=Criteria.where("releaseStatus").is("Approved");
        query.addCriteria(criteria);
//        query.addCriteria(criteria1);
        releaseMasterList=mongoTemplate.find(query,ReleaseMaster.class);
        System.out.println("  release size ="+releaseMasterList.size());
        artMaster1.setReleaseMasterList(releaseMasterList);

        List<AdminMaster> list = adminMasterDao.findAll();
        for (AdminMaster adminMaster : list) {
            double amt = artMasterReqDto.getPrice() * adminMaster.getArtPercentage();
            //
//            System.out.println("amt..." + amt);
//            double amount = artMasterReqDto.getPrice() - amt;
            //
            amt=amt/100;
            System.out.println("amt..." + amt);
            double amount = artMasterReqDto.getPrice() + amt;
            System.out.println("amount..." + amount);
            DecimalFormat df = new DecimalFormat("0.00");
            amount = Double.valueOf(df.format(amount));

            artMaster1.setFinalArtPrice(amount);
        }

        BeanUtils.copyProperties(artMasterReqDto, artMaster1);
        artMaster1.setFestiveOffer(false);

        try {
            artMaster1.setType("art");
            artMaster1.setSubmittedDate(new Date());
            artMaster1.setUpdatedDate(new Date());
            artMaster1.setReviewData(new Date());
            artMaster2 = artMasterDao.save(artMaster1);
            increseFileManagerLimit(userMaster);

            System.out.println("artMaster2......." + artMaster2.getUserMaster().getUserId());

            flag = true;

            Boolean flag1 = userMasterService.getData(artMasterReqDto.getUserId());
            System.out.println("flag1......." + flag1);

            if (flag) {
                for (String s : artMaster2.getKeywords()) {
                    KeywordCountMaster keywordCountMaster = keywordCountMasterDao.findByKeyword(s);
                    if (keywordCountMaster != null) {
                        Integer cnt = keywordCountMaster.getCount() + 1;

                        Optional<KeywordCountMaster> optionalKeywordCountMaster = keywordCountMasterDao.findByKeywordCountId(keywordCountMaster.getKeywordCountId());
                        if (optionalKeywordCountMaster.isPresent()) {
                            KeywordCountMaster keywordCountMaster1 = optionalKeywordCountMaster.get();
                            keywordCountMaster1.setCount(cnt);
                            keywordCountMasterDao.save(keywordCountMaster1);
                            System.out.println("keywordCountMaster2" + keywordCountMaster1.toString());
                            flag = true;
                        }
                    } else {
                        KeywordCountMaster keywordCountMaster1 = new KeywordCountMaster();
                        keywordCountMaster1.setKeyword(s);
                        keywordCountMaster1.setCount(1);
                        keywordCountMasterDao.save(keywordCountMaster1);
                        System.out.println("kkkkkk" + keywordCountMaster1);
                        flag = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }

        List<OrientationMaster> temp = new ArrayList();
        ContributorArtMarkupMaster contributorArtMarkupMaster = new ContributorArtMarkupMaster();
        List<OrientationMaster> orientationMasterList = orientationMasterDao.findAll();

        for (OrientationMaster orientationMaster : orientationMasterList) {

            OrientationMaster cm = new OrientationMaster();

            System.out.println("price " + artMaster2.getPrice());
            System.out.println("per " + orientationMaster.getContributorMarkupPercentage());

            double contPrice = artMaster2.getPrice() * orientationMaster.getContributorMarkupPercentage() / 100;
            double marginAmount = contPrice * orientationMaster.getArtMarginPercentage() / 100;
            double subTotalExpenses = orientationMaster.getArtExpensesOne() + orientationMaster.getArtExpensesTwo() + orientationMaster.getArtExpensesThree();
            double marginPer = subTotalExpenses * orientationMaster.getMarginPercentage() / 100;
            double totalExpenses = subTotalExpenses + marginPer;
            double basePrice = marginAmount + totalExpenses;
            double sellPrice = contPrice + marginAmount + totalExpenses;

            cm.setPrice(artMaster2.getPrice());
            cm.setContributorCalculatedPrice(contPrice);
            cm.setMarginAmount(marginAmount);
            cm.setSubTotalExpenses(subTotalExpenses);
            cm.setTotalExpenses(totalExpenses);
            cm.setBasePrice(basePrice);
            cm.setSellPrice(sellPrice);
            cm.setArtExpensesOne(orientationMaster.getArtExpensesOne());
            cm.setArtExpensesTwo(orientationMaster.getArtExpensesTwo());
            cm.setArtExpensesThree(orientationMaster.getArtExpensesThree());
            cm.setShapeStatus("Active");
            cm.setContributorMarkupPercentage(orientationMaster.getContributorMarkupPercentage());
            cm.setMarginPercentage(orientationMaster.getMarginPercentage());
            cm.setArtMarginPercentage(orientationMaster.getArtMarginPercentage());
            cm.setOrientationId(orientationMaster.getOrientationId());
            cm.setShape(orientationMaster.getShape());
            cm.setHeight(orientationMaster.getHeight());
            cm.setWidth(orientationMaster.getWidth());
            temp.add(cm);
        }
        contributorArtMarkupMaster.setArtMaster(artMaster2);
        contributorArtMarkupMaster.setDate(LocalDate.now());
        contributorArtMarkupMaster.setOrientationMasters(temp);
        ContributorArtMarkupMaster am = contributorArtMarkupDao.save(contributorArtMarkupMaster);
        System.out.println("am...." + am.getOrientationMasters().size());
        Random random = new Random();
        int artProductRandomNo = random.nextInt(10000000);
        if (flag) {
            String artProductNo = String.valueOf(artProductRandomNo);
            if (artProductNo.length() == 1) {
                artProductNo = "ANS00000000" + artProductNo;
            } else if (artProductNo.length() == 2) {
                artProductNo = "ANS0000" + artProductNo;
            } else if (artProductNo.length() == 4) {
                artProductNo = "ANS00000" + artProductNo;
            } else if (artProductNo.length() == 3) {
                artProductNo = "ANS000" + artProductNo;
            } else if (artProductNo.length() == 5) {
                artProductNo = "ANS00" + artProductNo;
            } else if (artProductNo.length() == 6) {
                artProductNo = "ANS0" + artProductNo;
            } else if (artProductNo.length() == 7) {
                artProductNo = "ANS" + artProductNo;
            }
            try {
                Optional<ArtMaster> artMasterOptional = artMasterDao.findByArtId(artMaster2.getArtId());
                if (artMasterOptional.isPresent()) {
                    ArtMaster artMaster3 = artMasterOptional.get();
                    artMaster3.setArProductNo(artProductNo);
                    for (ReleaseMaster releaseMaster : artMaster3.getReleaseMasterList())
                    {
                        releaseMaster.setArProductNo(artMaster3.getArProductNo());
                        releaseMasterDao.save(releaseMaster);
                    }
                    artMasterDao.save(artMaster3);
                    flag = true;
                } else {
                    flag = false;
                }
                artMasterRes.setFlag(flag);
                artMasterRes.setArtId(artMaster2.getArtId());
                return artMasterRes;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        artMasterRes.setFlag(flag);
        artMasterRes.setArtId(artMaster2.getArtId());
        return artMasterRes;
    }

    private void increseFileManagerLimit(UserMaster userMaster) {
        FileUploadLimitMaster fileUploadLimitMaster=new FileUploadLimitMaster();
        fileUploadLimitMaster=fileUploadLimitRepository.findByUserMaster(userMaster);
        fileUploadLimitMaster.setArtCount(fileUploadLimitMaster.getArtCount()+1);
        fileUploadLimitRepository.save(fileUploadLimitMaster);
    }

    private Boolean checkUserArts(String userId) {
        Boolean flag = false;

        FileUploadLimitMaster fileUploadLimitMaster=new FileUploadLimitMaster();
        fileUploadLimitMaster=fileUploadLimitRepository.findByUserMaster_UserId(userId);
        switch (fileUploadLimitMaster.getLevel())
        {
            case 0:
                    if(fileUploadLimitMaster.getArtCount()>50)
                        flag=false;
                    else
                        flag=true;
                break;
            case 1:
                if(fileUploadLimitMaster.getArtCount()>100)
                    flag=false;
                else
                    flag=true;
                break;
            case 2:
                if(fileUploadLimitMaster.getArtCount()>150)
                    flag=false;
                else
                    flag=true;
                break;
            case 3:
                if(fileUploadLimitMaster.getArtCount()>200)
                    flag=false;
                else
                    flag=true;
                break;
            case 4:
                if(fileUploadLimitMaster.getArtCount()>250)
                    flag=false;
                else
                    flag=true;
                break;
            case 5:
                if(fileUploadLimitMaster.getArtCount()>500)
                    flag=false;
                else
                    flag=true;
                break;
        }
        return flag;
    }

    @Override
    public Boolean updateArtMaster(ArtMasterReqDto artMasterReqDto) {
        SubjectMaster subjectMaster = subjectDao.getSubject(artMasterReqDto.getSubjectId());
        StyleMaster styleMaster = styleMasterDao.getStyle(artMasterReqDto.getStyleId());
        MediumMaster mediumMaster=mediumMasterDao.findById(artMasterReqDto.getArtMedium()).get();

        ArtMaster artMaster = new ArtMaster();
        BeanUtils.copyProperties(artMasterReqDto, artMaster);
        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(artMasterReqDto.getUserId());
        AddDetailsMaster addDetailsMaster=new AddDetailsMaster();
        addDetailsMaster.setArtDetailsId(artMasterReqDto.getArtDetailsId());

        artMaster.setUserMaster(userMaster);
        artMaster.setSubjectMaster(subjectMaster);
        artMaster.setStyleMaster(styleMaster);
        artMaster.setAddDetailsMaster(addDetailsMaster);
        artMaster.setMediumMaster(mediumMaster);
        artMaster.setUpdatedDate(new Date());


        artMasterReqDto.setArtId(artMaster.getArtId());
        try {
            artMasterDao.save(artMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Boolean updateDArtMaster(ArtMasterUpdateReqDto artMasterUpdateReqDto) {
        ArtMaster artMaster = new ArtMaster();
        artMaster=artMasterDao.findByArtId(artMasterUpdateReqDto.getArtId()).get();

        SubjectMaster subjectMaster = artMaster.getSubjectMaster();
        StyleMaster styleMaster = artMaster.getStyleMaster();
        MediumMaster mediumMaster=artMaster.getMediumMaster();

        BeanUtils.copyProperties(artMasterUpdateReqDto, artMaster);
        UserMaster userMaster = new UserMaster();
        userMaster=artMaster.getUserMaster();

//        if(artMasterUpdateReqDto.getStatus().equalsIgnoreCase("Approved"))
//        {
//            userMaster.setMessage("");
//        }

//        userMaster.setUserId(artMaster.getUserMaster().getUserId());
        artMaster.setUserMaster(artMaster.getUserMaster());
        artMaster.setSubjectMaster(subjectMaster);
        artMaster.setStyleMaster(styleMaster);
        artMaster.setMediumMaster(mediumMaster);
        artMaster.setAddDetailsMaster(artMaster.getAddDetailsMaster());

        //
        artMaster.setDescription(artMasterUpdateReqDto.getDescription());
        artMaster.setArtName(artMasterUpdateReqDto.getArtName());
        artMaster.setStatus(artMasterUpdateReqDto.getStatus());

        List<ReleaseMaster> releaseMasterList=new ArrayList<>();

        if(artMaster.getStatus().equalsIgnoreCase("Approved"))
        {
            artMaster.setApproveDate(new Date());
            artMaster.setUpdatedDate(new Date());
            for (ReleaseMaster releaseMaster : releaseMasterList) {
                releaseMaster.setReleaseStatus("Approved");
                releaseMasterDao.save(releaseMaster);
            }
        }

//        UserMessageMaster userMessageMaster=new UserMessageMaster();
//        userMessageMaster=userMessageDao.findByUserMaster_UserId(artMaster.getUserMaster().getUserId());
//        userMessageMaster.setMessage("Congrats. You have content live on Artnstock. ");
//        userMessageDao.save(userMessageMaster);

        //
        artMasterUpdateReqDto.setArtId(artMaster.getArtId());

        try {
            artMasterDao.save(artMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Boolean updateDsArtMaster() {

       List<ArtMaster> artMasterList=new ArrayList<>();
       artMasterList=artMasterDao.findAllByStatus("Approved");

        for (ArtMaster artMaster : artMasterList) {
//            ArtMaster artMaster = new ArtMaster();
//            artMaster=artMasterDao.findByArtId(artMasterUpdateReqDto.getArtId()).get();

            SubjectMaster subjectMaster = artMaster.getSubjectMaster();
            StyleMaster styleMaster = artMaster.getStyleMaster();
            MediumMaster mediumMaster=new MediumMaster();

            if(artMaster.getArtMedium()!=null) {
               Optional<MediumMaster> mediumMastero= mediumMasterDao.findById(artMaster.getArtMedium());
               mediumMastero.ifPresent(element -> BeanUtils.copyProperties(element,mediumMaster));
            }
//            BeanUtils.copyProperties(artMasterUpdateReqDto, artMaster);
            UserMaster userMaster = new UserMaster();
            userMaster=artMaster.getUserMaster();

//        if(artMasterUpdateReqDto.getStatus().equalsIgnoreCase("Approved"))
//        {
//            userMaster.setMessage("");
//        }

//        userMaster.setUserId(artMaster.getUserMaster().getUserId());
            artMaster.setUserMaster(artMaster.getUserMaster());
            artMaster.setSubjectMaster(subjectMaster);
            artMaster.setStyleMaster(styleMaster);
            artMaster.setMediumMaster(mediumMaster);
            artMaster.setAddDetailsMaster(artMaster.getAddDetailsMaster());

            //
//            artMaster.setDescription(artMasterUpdateReqDto.getDescription());
//            artMaster.setArtName(artMasterUpdateReqDto.getArtName());
//            artMaster.setStatus(artMasterUpdateReqDto.getStatus());

            List<ReleaseMaster> releaseMasterList=new ArrayList<>();

            if(artMaster.getStatus().equalsIgnoreCase("Approved"))
            {
                artMaster.setApproveDate(new Date());
                artMaster.setUpdatedDate(new Date());
                for (ReleaseMaster releaseMaster : releaseMasterList) {
                    releaseMaster.setReleaseStatus("Approved");
                    releaseMasterDao.save(releaseMaster);
                }
            }

//        UserMessageMaster userMessageMaster=new UserMessageMaster();
//        userMessageMaster=userMessageDao.findByUserMaster_UserId(artMaster.getUserMaster().getUserId());
//        userMessageMaster.setMessage("Congrats. You have content live on Artnstock. ");
//        userMessageDao.save(userMessageMaster);

            //
//            artMasterUpdateReqDto.setArtId(artMaster.getArtId());
            try {
                System.out.println("  aajajhaj");
                artMasterDao.save(artMaster);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

       return true;
    }


    @Override
    public List gerReleaseStatusByArtMasterId(String artId) {

        List list=new ArrayList();

        ArtMaster artMaster=new ArtMaster();
        artMaster=artMasterDao.getArtId(artId);

        for (String release : artMaster.getReleases()) {
            ReleaseMaster releaseMaster = new ReleaseMaster();
//            releaseMaster=releaseMasterDao.getById(release);
            releaseMaster = releaseMasterDao.findById(release).get();
        }
        return list;
    }

    @Override
    public ContributorArtMarkupMaster getContrubuterMarkupByArtId(String artId) {
        ContributorArtMarkupMaster contributorArtMarkupMaster=new ContributorArtMarkupMaster();
        contributorArtMarkupMaster=contributorArtMarkupDao.findByArtMasterArtId(artId);

//        ArtMaster artMaster=new ArtMaster();
//        artMaster=artMasterDao.getArtId(artId);
//        System.out.println("  Art Master ="+artMaster);
//        contributorArtMarkupMaster.setArtMaster(artMaster);

        System.out.println("  contributer Master ="+contributorArtMarkupMaster.toString());
        System.out.println("  contributer Master  orientation ="+contributorArtMarkupMaster.getOrientationMasters().toString());

        return contributorArtMarkupMaster;
    }

    @Override
    public List getAllArtsAndProductByUserId(String userId) {
        List list=new ArrayList();

        UserMaster userMaster=new UserMaster();
        userMaster=userDao.getUserMaster(userId);

        List<ArtMaster> artMasterList=new ArrayList<>();
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
        artMasterList=artMasterDao.findAllByUserMaster(userMaster);
        adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
//        artMasterList.stream().parallel().sorted(Comparator.comparing(ArtMaster::getSubmittedDate)).collect(Collectors.toList());
//        adminArtProductMasterList.stream().parallel().sorted(Comparator.comparing(AdminArtProductMaster::getDate)).collect(Collectors.toList());

        list.addAll(artMasterList);
        list.addAll(adminArtProductMasterList);

        return list;
    }

    Map<String,List> Cache=new HashMap<>();

    @Override
    public List getAllArtsAndProductBySort(SortRequest sortRequest) {
        System.out.println("  SortRequest"+sortRequest.toString());
        getAllArtsAndProductBySort1(sortRequest);
        if(Cache.containsKey(sortRequest.toString()))
        {
            System.out.println( " size ="+Cache.size());
            return Cache.get(sortRequest.toString());
        }

        List list=new ArrayList();
        UserMaster userMaster=new UserMaster();
        userMaster=userDao.getUserMaster(sortRequest.getUserId());
        List<ArtMaster> artMasterList=new ArrayList<>();
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();

//        Query query=new Query();
//        query.addCriteria(Criteria.where("userMaster").is(userMaster));
//        query.addCriteria(Criteria.where("status").is("Approved"));

//        Query query1=new Query();
//        query1.addCriteria(Criteria.where("userMaster").is(userMaster));
//        query1.addCriteria(Criteria.where("status").is("Approved"));

//        if (sortRequest.getSearchKey() !=null && sortRequest.getSearchKey()!="" ) {
//            query.addCriteria(Criteria.where("artName").regex(sortRequest.getSearchKey()));
//            query.addCriteria(Criteria.where("keywords").regex(sortRequest.getSearchKey()));
//            query1.addCriteria(Criteria.where("artMaster.$artName").regex(sortRequest.getSearchKey()));
//            query1.addCriteria(Criteria.where("artMaster.$keywords").regex(sortRequest.getSearchKey()));
//        }

        if(sortRequest.getType().equalsIgnoreCase("art"))
        {
            Query query=new Query();
            query.addCriteria(Criteria.where("userMaster").is(userMaster));
            query.addCriteria(Criteria.where("status").is("Approved"));

            if (sortRequest.getSearchKey() !=null && sortRequest.getSearchKey()!="" ) {
                query.addCriteria(new Criteria().orOperator(
                        Criteria.where("artName").regex(sortRequest.getSearchKey()),
                        Criteria.where("keywords").regex(sortRequest.getSearchKey()),
                        Criteria.where("arProductNo").regex(sortRequest.getSearchKey())
                ));
           }

            switch (sortRequest.getOrder())
            {
                case "Newest":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);

//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
//                    artMasterList.sort(Comparator.comparing(ArtMaster::getReviewData));
                    list.addAll(artMasterList);
                break;
                case "Oldest":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
//                    artMasterList.sort(Comparator.comparing(ArtMaster::getReviewData));
                    Collections.reverse(artMasterList);
                    list.addAll(artMasterList);
                    break;
                case "MostPopular":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(artMasterList);
                    break;
                case "Featured":
                    query.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(artMasterList);
                break;
                case "Exclusive":
                    query.addCriteria(Criteria.where("typeOfContent").is("Exclusive Art"));
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(artMasterList);
                    break;
                case "A-Z":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    artMasterList.sort(Comparator.comparing(ArtMaster::getArtName));
                    list.addAll(artMasterList);
                    break;
                case "Z-A":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    artMasterList.sort(Comparator.comparing(ArtMaster::getArtName));
                    Collections.reverse(artMasterList);
                    list.addAll(artMasterList);
                    break;
                default:
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(artMasterList);
                    break;
            }
        }else if(sortRequest.getType().equalsIgnoreCase("Products"))
        {
            Query query1=new Query();
            query1.addCriteria(Criteria.where("userMaster").is(userMaster));

            if (sortRequest.getSearchKey() !=null && sortRequest.getSearchKey()!="" ) {
                Aggregation aggregation = Aggregation.newAggregation(
                        // Join AdminArtProductMaster with ArtMaster
                        Aggregation.lookup("art_master", "artMaster.$id", "_id", "artMasterDetails"),

                        // Unwind the resulting array (because `$lookup` results in an array)
                        Aggregation.unwind("artMasterDetails", true), // true to keep documents without matches

                        // Match any of the fields: adminArtProductName, artProductNo, or artMasterDetails.keywords
                        Aggregation.match(new Criteria().orOperator(
                                Criteria.where("adminArtProductName").regex(sortRequest.getSearchKey(), "i"),
                                Criteria.where("artProductNo").regex(sortRequest.getSearchKey(), "i"),
                                Criteria.where("artMasterDetails.keywords").regex(sortRequest.getSearchKey(), "i")
                        ))
                );
                AggregationResults<AdminArtProductMaster> results = mongoTemplate.aggregate(aggregation, "admin_art_product_master", AdminArtProductMaster.class);
                list.addAll(results.getMappedResults());
            }

            System.out.println("product Default");
            switch (sortRequest.getOrder())
            {
                case "Newest":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                list.addAll(adminArtProductMasterList);
                    break;
                case "Oldest":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    Collections.reverse(adminArtProductMasterList);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "MostPopular":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
//                case "Featured":
//                    query1.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
//                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
//////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
//                    list.addAll(adminArtProductMasterList);
//                    break;
//                case "Exclusive":
//                    query1.addCriteria(Criteria.where("typeOfContent").is("Exclusive Art"));
//                    artMasterList=mongoTemplate.find(query1,ArtMaster.class);
//                    list.addAll(artMasterList);
////                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                    list.addAll(adminArtProductMasterList);
//                    break;
                case "A-Z":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Z-A":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    Collections.reverse(adminArtProductMasterList);
                    list.addAll(adminArtProductMasterList);
                    break;
                default:
                    System.out.println("product Default");
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
            }
        }else {

            Query query=new Query();
            query.addCriteria(Criteria.where("userMaster").is(userMaster));
            query.addCriteria(Criteria.where("status").is("Approved"));

            Query query1=new Query();
            query1.addCriteria(Criteria.where("userMaster").is(userMaster));


            if (sortRequest.getSearchKey() !=null && sortRequest.getSearchKey()!="" ) {

                query1.addCriteria(new Criteria().orOperator(
                        Criteria.where("artProductUniqueNo").regex(sortRequest.getSearchKey(),"i"),
                        Criteria.where("adminArtProductName").regex(sortRequest.getSearchKey(),"i"),
                        Criteria.where("artMasterDetails.keywords").regex(sortRequest.getSearchKey(),"i"),
                        Criteria.where("keywords").regex(sortRequest.getSearchKey(),"i")
                ));

                query.addCriteria(new Criteria().orOperator(
                        Criteria.where("artName").regex(sortRequest.getSearchKey(), "i"),
                        Criteria.where("keywords").regex(sortRequest.getSearchKey(), "i"),
                        Criteria.where("arProductNo").regex(sortRequest.getSearchKey(), "i")
                ));

            }


//            if (sortRequest.getSearchKey() !=null && sortRequest.getSearchKey()!="" ) {
//                Aggregation aggregation = Aggregation.newAggregation(
//                        // Join AdminArtProductMaster with ArtMaster
//                        Aggregation.lookup("art_master", "artMaster.$id", "_id", "artMasterDetails"),
//
//                        // Unwind the resulting array (because `$lookup` results in an array)
//                        Aggregation.unwind("artMasterDetails", true), // true to keep documents without matches
//
//                        // Match any of the fields: adminArtProductName, artProductNo, or artMasterDetails.keywords
//                        Aggregation.match(new Criteria().orOperator(
//                                Criteria.where("adminArtProductName").regex(sortRequest.getSearchKey(), "i"),
//                                Criteria.where("artProductNo").regex(sortRequest.getSearchKey(), "i"),
//                                Criteria.where("artMasterDetails.keywords").regex(sortRequest.getSearchKey(), "i")
//                        ))
//                );
//                AggregationResults<AdminArtProductMaster> results = mongoTemplate.aggregate(aggregation, "admin_art_product_master", AdminArtProductMaster.class);
//
//                list.addAll(results.getMappedResults());
//            }

            switch (sortRequest.getOrder())
            {
//                System.out.println(" Inside Default ");
                case "Newest":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Oldest":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    Collections.reverse(artMasterList);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1, AdminArtProductMaster.class);
                    Collections.reverse(adminArtProductMasterList);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "MostPopular":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Featured":
                    query.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(artMasterList);
//                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
//                    list.addAll(adminArtProductMasterList);
                    break;
                case "Exclusive":
                    query.addCriteria(Criteria.where("typeOfContent").is("Exclusive Art"));
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(artMasterList);
//                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
//                    list.addAll(adminArtProductMasterList);
                    break;
                case "A-Z":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    artMasterList.sort(Comparator.comparing(ArtMaster::getArtName));
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Z-A":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    artMasterList.sort(Comparator.comparing(ArtMaster::getArtName));
                    Collections.reverse(artMasterList);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    Collections.reverse(adminArtProductMasterList);
                    list.addAll(adminArtProductMasterList);
                    break;
                default:
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
                    list.addAll(adminArtProductMasterList);
                    break;
            }
        }






//        switch(sortRequest.getType())
//        {
//            case "art":
//                artMasterList=mongoTemplate.find(query,ArtMaster.class);
////                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
//                list.addAll(artMasterList);
//
//                switch ()
//                {
//                    case "":
//                    break;
//
//                    case "":
//                    break;
//
//                    case "":
//                    break;
//                }
//
//                break;
//            case "product":
//                adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
//                list.addAll(adminArtProductMasterList);
//                break;
//            default:
//                artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
////        artMasterList.stream().parallel().sorted(Comparator.comparing(ArtMaster::getSubmittedDate)).collect(Collectors.toList());
////        adminArtProductMasterList.stream().parallel().sorted(Comparator.comparing(AdminArtProductMaster::getDate)).collect(Collectors.toList());
//                list.addAll(artMasterList);
//                list.addAll(adminArtProductMasterList);
//                break;
//        }

        Cache.put(sortRequest.toString(),list);

        return list;
    }

    @Override
    public List<ArtMaster> getAllArtMasterByStatusandUserId(String status, String userId) {
        UserMaster userMaster=userDao.getUserMaster(userId);
        List<ArtMaster> artMasterList=artMasterDao.findAllByStatus(status);
        artMasterList.stream().filter(artMaster -> artMaster.getTypeOfContent().contains(userMaster.getPreferences())).collect(Collectors.toList());
        return artMasterList;
    }

    @Override
    public Integer getCountOfArtByStatus(String status, String userId) {

        UserMaster userMaster=new UserMaster();
        userMaster=userDao.getUserMaster(userId);
        List<ArtMaster> artMasterList=new ArrayList<>();
        artMasterList=artMasterDao.findAllByStatusAndUserMaster(status,userMaster);
        return artMasterList.size();
    }

    @Override
    public List<ArtMaster> getArtByKeyword(String keyword, String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userMaster.userId").is(userId));
        query.addCriteria(Criteria.where("keywords").is(keyword));
        List<ArtMaster> artMasterList = mongoTemplate.find(query, ArtMaster.class);
        return artMasterList;
    }

    @Override
    public Set<String> getAllKeywordByArtName(String artName) {
        Set<String> keywordsList=new HashSet<>();

        Set<String> keywords=new HashSet<>();

        List<KeywordsMaster> keywordsMasters=new ArrayList<>();
        keywordsMasters=keywordsMasterDao.findAll();

        KeywordsMaster keywordsMaster=keywordsMasters.get(0);
        keywords=keywordsMaster.getKeywords();

        Set<String> keys=new HashSet<>();

        System.out.println(" ArtName ="+artName);

      String[] StringArray=artName.split(" ");

        Set<String> finalKeywords = keywords;

        Thread thread=new Thread(()->
      {
          for (String s : StringArray) {
              for (String keyword : finalKeywords) {
                  String[] abc=keyword.split(s);
                  if(abc.length>0)
                  {
                      keys.add(keyword);
                  }
              }
          }
      });

        thread.start();

//        if(keys.isEmpty())
//        {
//
//        }else if(keys.size()<20){
//
//        }

        keywordsList.addAll(keys);

        for (String s : StringArray) {
            keywordsList= finalKeywords.stream().filter(s1 -> s1.equalsIgnoreCase(s.trim())).collect(Collectors.toSet());
        }

        if(keywordsList.size()<20)
        {
            for (String finalKeyword : finalKeywords) {
                if(keywordsList.size()>20)
                {
                    break;
                }
                keywordsList.add(finalKeyword.substring(0,1).toString()+finalKeyword.substring(1).toLowerCase());
            }
        }
        return keywordsList;
    }

    @Override


    public List getActiveArtMasterList() {

        Query query=new Query();
        query.addCriteria(Criteria.where("status").is("Active"));

        List<ArtMaster> artMasterList=new ArrayList<>();
        artMasterList=mongoTemplate.find(query,ArtMaster.class);
        System.out.println(" Size1 ="+artMasterList.size());

//        artMasterList= artMasterDao.findAllByStatus("Active");
//        System.out.println(" Size2 ="+artMasterList.size());
        return artMasterList;
    }


    @Override
    public List getUserIdWiseArtMasterList(String userId) {
        List<ArtMaster> list = artMasterDao.findByUserMasterUserId(userId);
        return list;
    }

    @Override
    public List getStyleIdIdWiseStyleMaster(String styleId) {
        List list = artMasterDao.findByStyleMasterStyleId(styleId);
        return list;
    }

    @Override
    public List ArtMasterFilter(ArtMasterFilterReqDto artMasterFilterReqDto) {
        List<ArtMaster> artMasters = artMasterDao.findAll();
        List<ArtMaster> tempList = new ArrayList<>();
        List<ArtMaster> filtered = new ArrayList<>();

        if (artMasterFilterReqDto.getStyleName() == null && artMasterFilterReqDto.getUserFirstName() == null && artMasterFilterReqDto.getSubjectName() == null && artMasterFilterReqDto.getSize() == null && artMasterFilterReqDto.getOrientation() == null && artMasterFilterReqDto.getPrice() == null) {
            // Return all artMasters
            return artMasters;
        }
// Add default condition to populate tempList with all artMasters
        if (tempList.isEmpty()) {
            tempList.addAll(artMasters);
        }
// StyleId
        if (artMasterFilterReqDto.getStyleName() != null && !artMasterFilterReqDto.getStyleName().isEmpty()) {
            tempList.removeIf(artMaster -> artMaster.getStyleMaster() == null || !artMaster.getStyleMaster().getStyleId().equals(artMasterFilterReqDto.getStyleName()));
        }

// UserFirstName
        if (artMasterFilterReqDto.getUserFirstName() != null && !artMasterFilterReqDto.getUserFirstName().isEmpty()) {
            tempList.removeIf(artMaster -> artMaster.getUserMaster() == null || !artMaster.getUserMaster().getUserFirstName().contains(artMasterFilterReqDto.getUserFirstName()));
        }

// SubjectId
        if (artMasterFilterReqDto.getSubjectName() != null && !artMasterFilterReqDto.getSubjectName().isEmpty()) {
            tempList.removeIf(artMaster -> artMaster.getStyleMaster() == null || artMaster.getSubjectMaster() == null || !artMaster.getSubjectMaster().getSubjectId().equals(artMasterFilterReqDto.getSubjectName()));
        }

        filtered = tempList.stream()
                .filter(artMasterFilterReqDto.getSize() != null && !artMasterFilterReqDto.getSize().isEmpty() ? (t -> t.getSize().equals(artMasterFilterReqDto.getSize())) : (t -> true))
                .filter(artMasterFilterReqDto.getOrientation() != null && !artMasterFilterReqDto.getOrientation().isEmpty() ? (t -> t.getOrientation().equals(artMasterFilterReqDto.getOrientation())) : (t -> true))
                .filter(artMasterFilterReqDto.getPrice() != null && artMasterFilterReqDto.getPrice() != 0 ? (t -> t.getPrice().equals(artMasterFilterReqDto.getPrice())) : (t -> true))
//                .filter(artMasterFilterReqDto.getPrice() != null && artMasterFilterReqDto.getColorCode().isEmpty()? (t -> t.getImageMaster().getColorInfos().get(0).equals(artMasterFilterReqDto.getColorCode())) : (t -> true))
                .collect(Collectors.toList());

        return filtered;
    }

    @Async
    public void getAllArtsAndProductBySort1(SortRequest sortRequest) {
        System.out.println("  SortRequest"+sortRequest.toString());

        List list=new ArrayList();
        UserMaster userMaster=new UserMaster();
        userMaster=userDao.getUserMaster(sortRequest.getUserId());
        List<ArtMaster> artMasterList=new ArrayList<>();
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();

//        Query query=new Query();
//        query.addCriteria(Criteria.where("userMaster").is(userMaster));
//        query.addCriteria(Criteria.where("status").is("Approved"));

//        Query query1=new Query();
//        query1.addCriteria(Criteria.where("userMaster").is(userMaster));
//        query1.addCriteria(Criteria.where("status").is("Approved"));

//        if (sortRequest.getSearchKey() !=null && sortRequest.getSearchKey()!="" ) {
//            query.addCriteria(Criteria.where("artName").regex(sortRequest.getSearchKey()));
//            query.addCriteria(Criteria.where("keywords").regex(sortRequest.getSearchKey()));
//            query1.addCriteria(Criteria.where("artMaster.$artName").regex(sortRequest.getSearchKey()));
//            query1.addCriteria(Criteria.where("artMaster.$keywords").regex(sortRequest.getSearchKey()));
//        }

        if(sortRequest.getType().equalsIgnoreCase("art"))
        {
            Query query=new Query();
            query.addCriteria(Criteria.where("userMaster").is(userMaster));
            query.addCriteria(Criteria.where("status").is("Approved"));

            if (sortRequest.getSearchKey() !=null && sortRequest.getSearchKey()!="" ) {
                query.addCriteria(new Criteria().orOperator(
                        Criteria.where("artName").regex(sortRequest.getSearchKey()),
                        Criteria.where("keywords").regex(sortRequest.getSearchKey()),
                        Criteria.where("arProductNo").regex(sortRequest.getSearchKey())
                ));
            }

            switch (sortRequest.getOrder())
            {
                case "Newest":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);

//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
//                    artMasterList.sort(Comparator.comparing(ArtMaster::getReviewData));
                    list.addAll(artMasterList);
                    break;
                case "Oldest":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
//                    artMasterList.sort(Comparator.comparing(ArtMaster::getReviewData));
                    Collections.reverse(artMasterList);
                    list.addAll(artMasterList);
                    break;
                case "MostPopular":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(artMasterList);
                    break;
                case "Featured":
                    query.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(artMasterList);
                    break;
                case "Exclusive":
                    query.addCriteria(Criteria.where("typeOfContent").is("Exclusive Art"));
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(artMasterList);
                    break;
                case "A-Z":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    artMasterList.sort(Comparator.comparing(ArtMaster::getArtName));
                    list.addAll(artMasterList);
                    break;
                case "Z-A":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    artMasterList.sort(Comparator.comparing(ArtMaster::getArtName));
                    Collections.reverse(artMasterList);
                    list.addAll(artMasterList);
                    break;
                default:
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(artMasterList);
                    break;
            }
        }else if(sortRequest.getType().equalsIgnoreCase("Products"))
        {
            Query query1=new Query();
            query1.addCriteria(Criteria.where("userMaster").is(userMaster));

            if (sortRequest.getSearchKey() !=null && sortRequest.getSearchKey()!="" ) {
                Aggregation aggregation = Aggregation.newAggregation(
                        // Join AdminArtProductMaster with ArtMaster
                        Aggregation.lookup("art_master", "artMaster.$id", "_id", "artMasterDetails"),

                        // Unwind the resulting array (because `$lookup` results in an array)
                        Aggregation.unwind("artMasterDetails", true), // true to keep documents without matches

                        // Match any of the fields: adminArtProductName, artProductNo, or artMasterDetails.keywords
                        Aggregation.match(new Criteria().orOperator(
                                Criteria.where("adminArtProductName").regex(sortRequest.getSearchKey(), "i"),
                                Criteria.where("artProductNo").regex(sortRequest.getSearchKey(), "i"),
                                Criteria.where("artMasterDetails.keywords").regex(sortRequest.getSearchKey(), "i")
                        ))
                );
                AggregationResults<AdminArtProductMaster> results = mongoTemplate.aggregate(aggregation, "admin_art_product_master", AdminArtProductMaster.class);
                list.addAll(results.getMappedResults());
            }

            System.out.println("product Default");
            switch (sortRequest.getOrder())
            {
                case "Newest":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Oldest":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    Collections.reverse(adminArtProductMasterList);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "MostPopular":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
//                case "Featured":
//                    query1.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
//                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
//////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
//                    list.addAll(adminArtProductMasterList);
//                    break;
//                case "Exclusive":
//                    query1.addCriteria(Criteria.where("typeOfContent").is("Exclusive Art"));
//                    artMasterList=mongoTemplate.find(query1,ArtMaster.class);
//                    list.addAll(artMasterList);
////                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                    list.addAll(adminArtProductMasterList);
//                    break;
                case "A-Z":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Z-A":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    Collections.reverse(adminArtProductMasterList);
                    list.addAll(adminArtProductMasterList);
                    break;
                default:
                    System.out.println("product Default");
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
            }
        }else {

            Query query=new Query();
            query.addCriteria(Criteria.where("userMaster").is(userMaster));
            query.addCriteria(Criteria.where("status").is("Approved"));

            Query query1=new Query();
            query1.addCriteria(Criteria.where("userMaster").is(userMaster));


            if (sortRequest.getSearchKey() !=null && sortRequest.getSearchKey()!="" ) {

                query1.addCriteria(new Criteria().orOperator(
                        Criteria.where("artProductUniqueNo").regex(sortRequest.getSearchKey(),"i"),
                        Criteria.where("adminArtProductName").regex(sortRequest.getSearchKey(),"i"),
                        Criteria.where("artMasterDetails.keywords").regex(sortRequest.getSearchKey(),"i"),
                        Criteria.where("keywords").regex(sortRequest.getSearchKey(),"i")
                ));

                query.addCriteria(new Criteria().orOperator(
                        Criteria.where("artName").regex(sortRequest.getSearchKey(), "i"),
                        Criteria.where("keywords").regex(sortRequest.getSearchKey(), "i"),
                        Criteria.where("arProductNo").regex(sortRequest.getSearchKey(), "i")
                ));

            }


//            if (sortRequest.getSearchKey() !=null && sortRequest.getSearchKey()!="" ) {
//                Aggregation aggregation = Aggregation.newAggregation(
//                        // Join AdminArtProductMaster with ArtMaster
//                        Aggregation.lookup("art_master", "artMaster.$id", "_id", "artMasterDetails"),
//
//                        // Unwind the resulting array (because `$lookup` results in an array)
//                        Aggregation.unwind("artMasterDetails", true), // true to keep documents without matches
//
//                        // Match any of the fields: adminArtProductName, artProductNo, or artMasterDetails.keywords
//                        Aggregation.match(new Criteria().orOperator(
//                                Criteria.where("adminArtProductName").regex(sortRequest.getSearchKey(), "i"),
//                                Criteria.where("artProductNo").regex(sortRequest.getSearchKey(), "i"),
//                                Criteria.where("artMasterDetails.keywords").regex(sortRequest.getSearchKey(), "i")
//                        ))
//                );
//                AggregationResults<AdminArtProductMaster> results = mongoTemplate.aggregate(aggregation, "admin_art_product_master", AdminArtProductMaster.class);
//
//                list.addAll(results.getMappedResults());
//            }

            switch (sortRequest.getOrder())
            {
//                System.out.println(" Inside Default ");
                case "Newest":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Oldest":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    Collections.reverse(artMasterList);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1, AdminArtProductMaster.class);
                    Collections.reverse(adminArtProductMasterList);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "MostPopular":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Featured":
                    query.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(artMasterList);
//                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
//                    list.addAll(adminArtProductMasterList);
                    break;
                case "Exclusive":
                    query.addCriteria(Criteria.where("typeOfContent").is("Exclusive Art"));
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(artMasterList);
//                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
//                    list.addAll(adminArtProductMasterList);
                    break;
                case "A-Z":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    artMasterList.sort(Comparator.comparing(ArtMaster::getArtName));
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Z-A":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    artMasterList.sort(Comparator.comparing(ArtMaster::getArtName));
                    Collections.reverse(artMasterList);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    Collections.reverse(adminArtProductMasterList);
                    list.addAll(adminArtProductMasterList);
                    break;
                default:
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
                    list.addAll(adminArtProductMasterList);
                    break;
            }
        }






//        switch(sortRequest.getType())
//        {
//            case "art":
//                artMasterList=mongoTemplate.find(query,ArtMaster.class);
////                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
//                list.addAll(artMasterList);
//
//                switch ()
//                {
//                    case "":
//                    break;
//
//                    case "":
//                    break;
//
//                    case "":
//                    break;
//                }
//
//                break;
//            case "product":
//                adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
//                list.addAll(adminArtProductMasterList);
//                break;
//            default:
//                artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
////        artMasterList.stream().parallel().sorted(Comparator.comparing(ArtMaster::getSubmittedDate)).collect(Collectors.toList());
////        adminArtProductMasterList.stream().parallel().sorted(Comparator.comparing(AdminArtProductMaster::getDate)).collect(Collectors.toList());
//                list.addAll(artMasterList);
//                list.addAll(adminArtProductMasterList);
//                break;
//        }

        Cache.put(sortRequest.toString(),list);

    }

    @Override
    public int getAllArtsByUserId(String userId) {

        UserMaster userMaster=new UserMaster();
        userMaster=userDao.findById(userId).get();


        List<ArtMaster> artMasterList=new ArrayList<>();

        int count=0;

        Query query=new Query();
        query.addCriteria(Criteria.where("userMaster").is(userMaster));
        query.addCriteria(Criteria.where("status").is("Approved"));
        artMasterList=mongoTemplate.find(query,ArtMaster.class);
        count=count+artMasterList.size();

        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
        adminArtProductMasterList=mongoTemplate.find(query,AdminArtProductMaster.class);
        count=count+artMasterList.size();
        return count;
    }



    /*
 @Async
    public void getAllArtsAndProductBySort1(SortRequest sortRequest) {

        long startTime =System.currentTimeMillis();
        System.out.println("  startT ="+startTime);

        System.out.println("  called async ");
        System.out.println("  SortRequest"+sortRequest.toString());

        List list=new ArrayList();
        UserMaster userMaster=new UserMaster();
        userMaster=userDao.getUserMaster(sortRequest.getUserId());
        List<ArtMaster> artMasterList=new ArrayList<>();
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();

        Query query=new Query();
        query.addCriteria(Criteria.where("userMaster").is(userMaster));
        query.addCriteria(Criteria.where("status").is("Approved"));

        Query query1=new Query();
        query1.addCriteria(Criteria.where("userMaster").is(userMaster));

//        if (sortRequest.getSearchKey() !=null && sortRequest.getSearchKey()!="" ) {
//            query.addCriteria(Criteria.where("artName").regex(sortRequest.getSearchKey()));
//            query.addCriteria(Criteria.where("keywords").regex(sortRequest.getSearchKey()));
//            query1.addCriteria(Criteria.where("artMaster.$artName").regex(sortRequest.getSearchKey()));
//            query1.addCriteria(Criteria.where("artMaster.$keywords").regex(sortRequest.getSearchKey()));
//        }

        if(sortRequest.getType().equalsIgnoreCase("art"))
        {
            if (sortRequest.getSearchKey() !=null && sortRequest.getSearchKey()!="" ) {
                query.addCriteria(new Criteria().orOperator(
                        Criteria.where("artName").regex(sortRequest.getSearchKey()),
                        Criteria.where("keywords").regex(sortRequest.getSearchKey()),
                        Criteria.where("arProductNo").regex(sortRequest.getSearchKey())
                ));
            }

            switch (sortRequest.getOrder())
            {
                case "Newest":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);

//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
//                    artMasterList.sort(Comparator.comparing(ArtMaster::getReviewData));
                    list.addAll(artMasterList);
                    break;
                case "Oldest":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
//                    artMasterList.sort(Comparator.comparing(ArtMaster::getReviewData));
                    Collections.reverse(artMasterList);
                    list.addAll(artMasterList);
                    break;
                case "MostPopular":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(artMasterList);
                    break;
                case "Featured":
                    query.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(artMasterList);
                    break;
                case "A-Z":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    artMasterList.sort(Comparator.comparing(ArtMaster::getArtName));
                    list.addAll(artMasterList);
                    break;
                case "Z-A":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    artMasterList.sort(Comparator.comparing(ArtMaster::getArtName));
                    Collections.reverse(artMasterList);
                    list.addAll(artMasterList);
                    break;
                default:
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(artMasterList);
                    break;
            }
        }else if(sortRequest.getType().equalsIgnoreCase("Products"))
        {
            if (sortRequest.getSearchKey() !=null && sortRequest.getSearchKey()!="" ) {
                Aggregation aggregation = Aggregation.newAggregation(
                        // Join AdminArtProductMaster with ArtMaster
                        Aggregation.lookup("art_master", "artMaster.$id", "_id", "artMasterDetails"),

                        // Unwind the resulting array (because `$lookup` results in an array)
                        Aggregation.unwind("artMasterDetails", true), // true to keep documents without matches

                        // Match any of the fields: adminArtProductName, artProductNo, or artMasterDetails.keywords
                        Aggregation.match(new Criteria().orOperator(
                                Criteria.where("adminArtProductName").regex(sortRequest.getSearchKey(), "i"),
                                Criteria.where("artProductNo").regex(sortRequest.getSearchKey(), "i"),
                                Criteria.where("artMasterDetails.keywords").regex(sortRequest.getSearchKey(), "i")
                        ))
                );
                AggregationResults<AdminArtProductMaster> results = mongoTemplate.aggregate(aggregation, "admin_art_product_master", AdminArtProductMaster.class);
                list.addAll(results.getMappedResults());
            }

            System.out.println("product Default");
            switch (sortRequest.getOrder())
            {
                case "Newest":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Oldest":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    Collections.reverse(adminArtProductMasterList);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "MostPopular":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Featured":
                    query1.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "A-Z":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Z-A":
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    Collections.reverse(adminArtProductMasterList);
                    list.addAll(adminArtProductMasterList);
                    break;
                default:
                    System.out.println("product Default");
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
            }
        }else {

            if (sortRequest.getSearchKey() !=null && sortRequest.getSearchKey()!="" ) {
                Aggregation aggregation = Aggregation.newAggregation(
                        // Join AdminArtProductMaster with ArtMaster
                        Aggregation.lookup("art_master", "artMaster.$id", "_id", "artMasterDetails"),

                        // Unwind the resulting array (because `$lookup` results in an array)
                        Aggregation.unwind("artMasterDetails", true), // true to keep documents without matches

                        // Match any of the fields: adminArtProductName, artProductNo, or artMasterDetails.keywords
                        Aggregation.match(new Criteria().orOperator(
                                Criteria.where("adminArtProductName").regex(sortRequest.getSearchKey(), "i"),
                                Criteria.where("artProductNo").regex(sortRequest.getSearchKey(), "i"),
                                Criteria.where("artMasterDetails.keywords").regex(sortRequest.getSearchKey(), "i")
                        ))
                );
                AggregationResults<AdminArtProductMaster> results = mongoTemplate.aggregate(aggregation, "admin_art_product_master", AdminArtProductMaster.class);
                list.addAll(results.getMappedResults());

                query.addCriteria(new Criteria().orOperator(
                        Criteria.where("artName").regex(sortRequest.getSearchKey()),
                        Criteria.where("keywords").regex(sortRequest.getSearchKey()),
                        Criteria.where("arProductNo").regex(sortRequest.getSearchKey())
                ));
            }

            switch (sortRequest.getOrder())
            {
//                System.out.println(" Inside Default ");
                case "Newest":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Oldest":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    Collections.reverse(artMasterList);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    Collections.reverse(adminArtProductMasterList);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "MostPopular":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
//                artMasterList=artMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Featured":
                    query.addCriteria(Criteria.where("typeOfContent").is("Featured Art"));
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "A-Z":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    artMasterList.sort(Comparator.comparing(ArtMaster::getArtName));
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    list.addAll(adminArtProductMasterList);
                    break;
                case "Z-A":
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    artMasterList.sort(Comparator.comparing(ArtMaster::getArtName));
                    Collections.reverse(artMasterList);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
////                adminArtProductMasterList=adminArtProductMasterDao.findAllByUserMaster(userMaster);
                    Collections.reverse(adminArtProductMasterList);
                    list.addAll(adminArtProductMasterList);
                    break;
                default:
                    artMasterList=mongoTemplate.find(query,ArtMaster.class);
                    list.addAll(artMasterList);
                    adminArtProductMasterList=mongoTemplate.find(query1,AdminArtProductMaster.class);
                    adminArtProductMasterList.sort(Comparator.comparing(AdminArtProductMaster::getAdminArtProductName));
                    list.addAll(adminArtProductMasterList);
                    break;
            }
        }
 */








//    @Override
//    public List ArtFilter(ArtMasterFilterReqDto artMasterFilterReqDto, String type, String text) {
//
//        System.out.println("  min price ="+ artMasterFilterReqDto.getMinPrice());
//        System.out.println("  max price  ="+artMasterFilterReqDto.getMaxPrice());
//
//        List<ArtMaster> list = artMasterDao.findAll();
//        int i = 0;
//        List<ArtMaster> filtered = new ArrayList<>();
//        List<ArtMaster> tempList = new ArrayList<>();
//        if (type.equals("All") && text.equals("All")) {
//            List<ArtMaster> artMasters = artMasterDao.findAll();
//            if (artMasterFilterReqDto.getStyleName() == null && artMasterFilterReqDto.getUserFirstName() == null && artMasterFilterReqDto.getSubjectName() == null && artMasterFilterReqDto.getSize() == null && artMasterFilterReqDto.getOrientation() == null && artMasterFilterReqDto.getPrice() == null && artMasterFilterReqDto.getKeyword() == null && artMasterFilterReqDto.getColorCode() == null && artMasterFilterReqDto.getMinPrice() == null && artMasterFilterReqDto.getMaxPrice() == null) {
//                return artMasters;
//            }
//            if (tempList.isEmpty()) {
//                tempList.addAll(artMasters);
//            }
//            if (artMasterFilterReqDto.getStyleName() != null && !artMasterFilterReqDto.getStyleName().isEmpty()) {
//                filtered = artMasters.stream()
//                        .filter(artMaster -> artMaster.getStyleMaster() != null && artMaster.getStyleMaster().getName().equals(artMasterFilterReqDto.getStyleName()))
//                        .collect(Collectors.toList());
//            } else {
//                filtered = artMasters;
//            }
//            filtered = filtered.stream()
//                    .filter(artMasterFilterReqDto.getSubjectName() != null && !artMasterFilterReqDto.getSubjectName().isEmpty()
//                            ? (artMaster -> artMaster.getSubjectMaster() != null && artMaster.getSubjectMaster().getSubjectName() != null && artMaster.getSubjectMaster().getSubjectName().equals(artMasterFilterReqDto.getSubjectName()))
//                            : (artMaster -> true))
//                    .filter(artMasterFilterReqDto.getMinPrice() != null && artMasterFilterReqDto.getMinPrice() != 0 ? (t -> t.getPrice()!=null && t.getPrice().compareTo(artMasterFilterReqDto.getMinPrice())>=0 ) : (t -> true))
//                    .filter(artMasterFilterReqDto.getMaxPrice() != null && artMasterFilterReqDto.getMaxPrice() != 0 ? (t -> t.getPrice()!=null && t.getPrice().compareTo(artMasterFilterReqDto.getMaxPrice())<=0 ) : (t -> true))
//                    .filter(artMasterFilterReqDto.getSize() != null && !artMasterFilterReqDto.getSize().isEmpty() ? (t -> t.getSize().equals(artMasterFilterReqDto.getSize())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getOrientation() != null && !artMasterFilterReqDto.getOrientation().isEmpty() ? (t -> t.getOrientation().equals(artMasterFilterReqDto.getOrientation())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getUserFirstName() != null && !artMasterFilterReqDto.getUserFirstName().isEmpty() ? (t -> t.getUserMaster() != null && t.getUserMaster().getUserFirstName().equalsIgnoreCase(artMasterFilterReqDto.getUserFirstName())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getKeyword() != null && !artMasterFilterReqDto.getKeyword().isEmpty() ? (t -> t.getKeywords().contains(artMasterFilterReqDto.getKeyword())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getColorCode() != null && !artMasterFilterReqDto.getColorCode().isEmpty() ? (t -> t.getImageMaster() != null && t.getImageMaster().getColorInfos() != null && t.getImageMaster().getColorInfos().stream().anyMatch(colorInfo -> colorInfo.getColor().equalsIgnoreCase(artMasterFilterReqDto.getColorCode()))) : (t -> true))
//                    .collect(Collectors.toList());
//            System.out.println("filtered = " + filtered.size());
//
//            for (ArtMaster artMaster : filtered) {
//                System.out.println("  artMaster ="+artMaster);
//            }
//
////            if(artMasterFilterReqDto.getMinPrice()!=null && artMasterFilterReqDto.getMaxPrice()!=null)
////            {
////                filtered = filtered.stream().filter(t -> t.getPrice().compareTo(artMasterFilterReqDto.getMinPrice())>=0 && t.getPrice().compareTo(artMasterFilterReqDto.getMaxPrice())<=0).collect(Collectors.toList());
////            }
//
//        } else if (type.equals("subject")) {
//            Criteria criteria = Criteria.where("subjectMaster.subjectName").is(text);
//            Query query = Query.query(criteria);
//            List<ArtMaster> subList = mongoTemplate.find(query, ArtMaster.class);
//            if (artMasterFilterReqDto.getStyleName() != null && !artMasterFilterReqDto.getStyleName().isEmpty()) {
//                filtered = subList.stream()
//                        .filter(artMaster -> artMaster.getStyleMaster() != null && artMaster.getStyleMaster().getName().equals(artMasterFilterReqDto.getStyleName()))
//                        .collect(Collectors.toList());
//            } else {
//                filtered = subList;
//            }
//            filtered = filtered.stream()
//                    .filter(artMasterFilterReqDto.getMinPrice() != null && artMasterFilterReqDto.getMinPrice() != 0 && artMasterFilterReqDto.getMinPrice() != null && artMasterFilterReqDto.getMinPrice() != 0? (t -> t.getPrice()!=null && t.getPrice().compareTo(artMasterFilterReqDto.getMinPrice())>=0 && t.getPrice().compareTo(artMasterFilterReqDto.getMaxPrice())<=0) : (t -> true))
//                    .filter(artMasterFilterReqDto.getSize() != null && !artMasterFilterReqDto.getSize().isEmpty() ? (t -> t.getSize().equals(artMasterFilterReqDto.getSize())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getOrientation() != null && !artMasterFilterReqDto.getOrientation().isEmpty() ? (t -> t.getOrientation().equals(artMasterFilterReqDto.getOrientation())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getUserFirstName() != null && !artMasterFilterReqDto.getUserFirstName().isEmpty() ? (t -> t.getUserMaster() != null && t.getUserMaster().getUserFirstName().equalsIgnoreCase(artMasterFilterReqDto.getUserFirstName())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getKeyword() != null && !artMasterFilterReqDto.getKeyword().isEmpty() ? (t -> t.getKeywords().contains(artMasterFilterReqDto.getKeyword())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getColorCode() != null && !artMasterFilterReqDto.getColorCode().isEmpty() ? (t -> t.getImageMaster() != null && t.getImageMaster().getColorInfos() != null && t.getImageMaster().getColorInfos().stream().anyMatch(colorInfo -> colorInfo.getColor().equalsIgnoreCase(artMasterFilterReqDto.getColorCode()))) : (t -> true))
//                    .collect(Collectors.toList());
//
////            if(artMasterFilterReqDto.getMinPrice()!=null && artMasterFilterReqDto.getMaxPrice()!=null)
////            {
////                filtered = filtered.stream().filter(t -> t.getPrice().compareTo(artMasterFilterReqDto.getMinPrice())>=0 && t.getPrice().compareTo(artMasterFilterReqDto.getMaxPrice())<=0).collect(Collectors.toList());
////            }
//
//        } else if (type.equals("style")) {
//            Criteria criteria = Criteria.where("styleMaster.name").is(text);
//            Query query = Query.query(criteria);
//            List<ArtMaster> styleList = mongoTemplate.find(query, ArtMaster.class);
//            if (artMasterFilterReqDto.getSubjectName() != null && !artMasterFilterReqDto.getSubjectName().isEmpty()) {
//                filtered = styleList.stream()
//                        .filter(artMaster -> artMaster.getSubjectMaster() != null && artMaster.getSubjectMaster().getSubjectName().equals(artMasterFilterReqDto.getSubjectName()))
//                        .collect(Collectors.toList());
//            } else {
//                filtered = styleList;
//            }
//            filtered = filtered.stream()
//                    .filter(artMasterFilterReqDto.getMinPrice() != null && artMasterFilterReqDto.getMinPrice() != 0 && artMasterFilterReqDto.getMinPrice() != null && artMasterFilterReqDto.getMinPrice() != 0? (t -> t.getPrice()!=null && t.getPrice().compareTo(artMasterFilterReqDto.getMinPrice())>=0 && t.getPrice().compareTo(artMasterFilterReqDto.getMaxPrice())<=0) : (t -> true))
//                    .filter(artMasterFilterReqDto.getSize() != null && !artMasterFilterReqDto.getSize().isEmpty() ? (t -> t.getSize().equals(artMasterFilterReqDto.getSize())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getOrientation() != null && !artMasterFilterReqDto.getOrientation().isEmpty() ? (t -> t.getOrientation().equals(artMasterFilterReqDto.getOrientation())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getUserFirstName() != null && !artMasterFilterReqDto.getUserFirstName().isEmpty() ? (t -> t.getUserMaster() != null && t.getUserMaster().getUserFirstName().equalsIgnoreCase(artMasterFilterReqDto.getUserFirstName())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getKeyword() != null && !artMasterFilterReqDto.getKeyword().isEmpty() ? (t -> t.getKeywords().contains(artMasterFilterReqDto.getKeyword())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getColorCode() != null && !artMasterFilterReqDto.getColorCode().isEmpty() ? (t -> t.getImageMaster() != null && t.getImageMaster().getColorInfos() != null && t.getImageMaster().getColorInfos().stream().anyMatch(colorInfo -> colorInfo.getColor().equalsIgnoreCase(artMasterFilterReqDto.getColorCode()))) : (t -> true))
//                    .collect(Collectors.toList());
//
////            if(artMasterFilterReqDto.getMinPrice()!=null && artMasterFilterReqDto.getMaxPrice()!=null)
////            {
////                filtered = filtered.stream().filter(t -> t.getPrice().compareTo(artMasterFilterReqDto.getMinPrice())>=0 && t.getPrice().compareTo(artMasterFilterReqDto.getMaxPrice())<=0).collect(Collectors.toList());
////            }
//
//        } else if (type.equals("search")) {
//            List<ArtMaster> searchList = artMasterDao.findBySubjectMasterSubjectNameContainingOrStyleMasterNameContainingOrArtNameContaining(text, text, text);
//            System.out.println("searchList" + searchList.size());
//
//            if (artMasterFilterReqDto.getStyleName() != null && !artMasterFilterReqDto.getStyleName().isEmpty()) {
//                filtered = searchList.stream()
//                        .filter(artMaster -> artMaster.getStyleMaster() != null && artMaster.getStyleMaster().getName().equals(artMasterFilterReqDto.getStyleName()))
//                        .collect(Collectors.toList());
//            } else {
//                filtered = searchList;
//            }
//            filtered = filtered.stream()
//                    .filter(artMasterFilterReqDto.getSubjectName() != null && !artMasterFilterReqDto.getSubjectName().isEmpty()
//                            ? (artMaster -> artMaster.getSubjectMaster() != null && artMaster.getSubjectMaster().getSubjectName() != null && artMaster.getSubjectMaster().getSubjectName().equals(artMasterFilterReqDto.getSubjectName()))
//                            : (artMaster -> true))
//                    .filter(artMasterFilterReqDto.getSize() != null && !artMasterFilterReqDto.getSize().isEmpty() ? (t -> t.getSize().equals(artMasterFilterReqDto.getSize())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getOrientation() != null && !artMasterFilterReqDto.getOrientation().isEmpty() ? (t -> t.getOrientation().equals(artMasterFilterReqDto.getOrientation())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getMinPrice() != null && artMasterFilterReqDto.getMinPrice() != 0 && artMasterFilterReqDto.getMaxPrice() != null && artMasterFilterReqDto.getMaxPrice() != 0? (t ->  t.getPrice()!=null && t.getPrice().compareTo(artMasterFilterReqDto.getMinPrice())>=0 && t.getPrice().compareTo(artMasterFilterReqDto.getMaxPrice())<=0) : (t -> true))
//                    .filter(artMasterFilterReqDto.getUserFirstName() != null && !artMasterFilterReqDto.getUserFirstName().isEmpty() ? (t -> t.getUserMaster() != null && t.getUserMaster().getUserFirstName().equalsIgnoreCase(artMasterFilterReqDto.getUserFirstName())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getKeyword() != null && !artMasterFilterReqDto.getKeyword().isEmpty() ? (t -> t.getKeywords().contains(artMasterFilterReqDto.getKeyword())) : (t -> true))
//                    .filter(artMasterFilterReqDto.getColorCode() != null && !artMasterFilterReqDto.getColorCode().isEmpty() ? (t -> t.getImageMaster() != null && t.getImageMaster().getColorInfos() != null && t.getImageMaster().getColorInfos().stream().anyMatch(colorInfo -> colorInfo.getColor().equalsIgnoreCase(artMasterFilterReqDto.getColorCode()))) : (t -> true))
//                    .collect(Collectors.toList());
//            System.out.println("filtered2.." + filtered.size());
//        }
//        return filtered;
//    }

    /////////
    @Override
    public List<ArtMaster> ArtFilter(ArtMasterFilterReqDto artMasterFilterReqDto, String type, String text) {

        List<ArtMaster> filtered=new ArrayList<>();
        long startTime = System.currentTimeMillis();
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(Criteria.where("status").is("Approved"));

        List<UserMaster> userMasterList=new ArrayList<>();
        Query queryu=new Query();
        queryu.addCriteria(Criteria.where("status").is("Active"));
        userMasterList=mongoTemplate.find(queryu,UserMaster.class);

        // new
        List<String> userIds=userMasterList.stream().map(userMaster -> userMaster.getUserId()).collect(Collectors.toList());
        System.out.println("  userIds"+ userIds.size());
        System.out.println("  userIds"+ userIds.toString());
//        criteriaList.add(Criteria.where("userMaster.userId").in(userIds));

//        criteriaList.add(Criteria.where("userMaster.$id").in(userIds));
        //


        if(type.equalsIgnoreCase("All"))
        {
//            Pageable pageable = (Pageable) PageRequest.of(1, 5);
            List<ArtMaster> artMasterList=new ArrayList<>();
            filtered=artMasterDao.findAllByStatus("Approved");
            return filtered;
        }

        System.out.println("  type ="+type);
        System.out.println("  text ="+text);

        switch (type) {

            case "All":
                // No additional criteria needed
//                criteriaList.add(Criteria.where("status").is("Approved"));

                break;
            case "subject":
                criteriaList.add(Criteria.where("subjectMaster.subjectName").is(text));  break;
                case "style":
                criteriaList.add(Criteria.where("styleMaster.name").is(text));
                break;
            case "search":
                criteriaList.add(Criteria.where("subjectMaster.subjectName").regex(text, "i"));
                criteriaList.add(Criteria.where("styleMaster.name").regex(text, "i"));
                criteriaList.add(Criteria.where("artName").regex(text, "i"));
                criteriaList.add(Criteria.where("keywords").regex(text,"i"));
                break;
            default:
                // No additional criteria needed
                break;
        }

        System.out.println("  Criateria size1 ="+criteriaList.size());

        List<Criteria> criteriaListOR = new ArrayList<>();

        // Apply additional filters from artMasterFilterReqDto
        applyFiltersInCriteria(criteriaListOR, artMasterFilterReqDto);


        // Create aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])).orOperator(criteriaListOR))
//                Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize()),
//                Aggregation.limit(pageable.getPageSize())
        );

        System.out.println("  Criateria size 2 ="+criteriaList.size());

        criteriaList.forEach(criteria -> System.out.println(criteria));

        AggregationResults<ArtMaster> aggregationResults = mongoTemplate.aggregate(aggregation, "art_master", ArtMaster.class);
        filtered = aggregationResults.getMappedResults();

        // Execute aggregation pipeline
//        List<ArtMaster> filtered = mongoTemplate.aggregate(aggregation, "artMaster", ArtMaster.class).getMappedResults();

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("ArtFilter execution time: " + elapsedTime + " ms");

        System.out.println(filtered.size());
        return filtered;
    }

    private void applyFiltersInCriteria(List<Criteria> criteriaList, ArtMasterFilterReqDto filterDto) {
        if (filterDto.getMinPrice() != null) {
            criteriaList.add(Criteria.where("price").gte(filterDto.getMinPrice()));
        }
        if (filterDto.getMaxPrice() != null) {
            criteriaList.add(Criteria.where("price").lte(filterDto.getMaxPrice()));
        }
        if (filterDto.getSize() != null) {
            criteriaList.add(Criteria.where("size").is(filterDto.getSize()));
        }
        if (filterDto.getStyleName() != null) {
            criteriaList.add(Criteria.where("styleMaster.name").is(filterDto.getStyleName()));
        }
        if (filterDto.getSubjectName() != null) {
            criteriaList.add(Criteria.where("subjectMaster.subjectName").is(filterDto.getSubjectName()));
        }

        if (filterDto.getOrientation() != null) {
            if(filterDto.getOrientation().equalsIgnoreCase("Custom"))
            {
                System.out.println("  Orientation is Custom in ");
                String[] orientations={"Horizontal","Vertical","Square"};
                criteriaList.add(Criteria.where("orientation").in(orientations));
            }else {
                criteriaList.add(Criteria.where("orientation").is(filterDto.getOrientation()));
            }
        }

        if (filterDto.getUserFirstName() != null) {
            criteriaList.add(Criteria.where("userMaster.userFirstName").is(filterDto.getUserFirstName()));
        }

        if(filterDto.getKeyword()!=null)
        {
            criteriaList.add(Criteria.where("keywords").is(filterDto.getKeyword()));
        }

        if(filterDto.getColorCode()!=null)
        {
            criteriaList.add(Criteria.where("imageMaster.colorInfos.color").is(filterDto.getColorCode()));
        }
        // Add more filters as needed...
    }



    ///////


    // New Art Filter
    @Override
    public List ArtFilterNew(ArtMasterFilterReqDto artMasterFilterReqDto, String type, String text, Integer page, Integer number) {
        List<ArtMaster> filtered=new ArrayList<>();
        long startTime = System.currentTimeMillis();
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(Criteria.where("status").is("Approved"));

        List<UserMaster> userMasterList=new ArrayList<>();
        Query queryu=new Query();
        queryu.addCriteria(Criteria.where("status").is("Active"));
        userMasterList=mongoTemplate.find(queryu,UserMaster.class);

        // new
        List<String> userIds=userMasterList.stream().map(userMaster -> userMaster.getUserId()).collect(Collectors.toList());
        System.out.println("  userIds"+ userIds.size());
        System.out.println("  userIds"+ userIds.toString());
//        criteriaList.add(Criteria.where("userMaster.userId").in(userIds));

//        criteriaList.add(Criteria.where("userMaster.$id").in(userIds));
        //


        if(type.equalsIgnoreCase("All"))
        {
            PageRequest pageable = PageRequest.of(page, number);
            System.out.println("  page = "+page+"  number = "+number);
            List<ArtMaster> artMasterList=new ArrayList<>();
            artMasterList=artMasterDao.findAllByStatus("Approved",pageable).getContent();
            filtered=artMasterList;
            System.out.println(artMasterList.size());
            return filtered;
        }

        switch (type) {
            case "All":
                // No additional criteria needed
//                criteriaList.add(Criteria.where("status").is("Approved"));
                break;
            case "subject":
                criteriaList.add(Criteria.where("subjectMaster.subjectName").is(text));  break;
            case "style":
                criteriaList.add(Criteria.where("styleMaster.name").is(text));
                break;
            case "search":
                criteriaList.add(Criteria.where("subjectMaster.subjectName").regex(text, "i"));
                criteriaList.add(Criteria.where("styleMaster.name").regex(text, "i"));
                criteriaList.add(Criteria.where("artName").regex(text, "i"));
                criteriaList.add(Criteria.where("keywords").regex(text,"i"));
                break;
            default:
                // No additional criteria needed
                break;
        }

        System.out.println("  Criateria size1 ="+criteriaList.size());

        List<Criteria> criteriaListOR = new ArrayList<>();

        // Apply additional filters from artMasterFilterReqDto
        applyFiltersInCriteria(criteriaListOR, artMasterFilterReqDto);


        // Apply additional filters from artMasterFilterReqDto
//        applyFiltersInCriteria(criteriaList, artMasterFilterReqDto);


        page=(page==0)?1:page;


        // Create aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])).orOperator(criteriaListOR)),
                Aggregation.skip( (page - 1) * number),
                Aggregation.limit(number)
        );

        System.out.println("  Criateria size 2 ="+criteriaList.size());

        criteriaList.forEach(criteria -> System.out.println(criteria));

        AggregationResults<ArtMaster> aggregationResults = mongoTemplate.aggregate(aggregation, "art_master", ArtMaster.class);

        filtered = aggregationResults.getMappedResults();

        // Execute aggregation pipeline
//        List<ArtMaster> filtered = mongoTemplate.aggregate(aggregation, "artMaster", ArtMaster.class).getMappedResults();

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("ArtFilter execution time: " + elapsedTime + " ms");

        System.out.println(filtered.size());
        System.out.println(" size filter ="+filtered.size());
        return filtered;
    }


    @Override
    public List getKeywordMasterList() {
        List<KeywordCountMaster> list = keywordCountMasterDao.findAll();
        System.out.println("ssss" + list.size());
        list.sort(Comparator.comparing(KeywordCountMaster::getCount).reversed());
        return list;
    }

    @Override
    public KeywordCountMaster getKeywordCountIdWiseData(String keywordCountId) {
        KeywordCountMaster keywordCountMaster = new KeywordCountMaster();
        try {
            Optional<KeywordCountMaster> keywordCountMaster1 = keywordCountMasterDao.findById(keywordCountId);
            keywordCountMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, keywordCountMaster));
            return keywordCountMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return keywordCountMaster;
        }
    }

    @Override
    public List<ArtMaster> getUserIdAndStatusWiseUserMaster(String userId, String status) {
        List<ArtMaster> list = artMasterDao.findByUserMaster_UserIdAndStatus(userId, status);
        return list;
    }

    @Override
    public Boolean updateUserIdWiseStatus(String userId, String status) {
        Boolean flag = false;
        Optional<ArtMaster> optionalArtMaster = artMasterDao.findByUserMaster_UserId(userId);
        if (optionalArtMaster.isPresent()) {
            ArtMaster artMaster = optionalArtMaster.get();
            artMaster.setStatus(status);
            artMasterDao.save(artMaster);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public List<ArtMaster> getKeywordWiseArtMasterList(String keyword) {
        Query query = new Query(
                new Criteria().orOperator(
                        Criteria.where("keywords").is(keyword)));
        List<ArtMaster> matchingArtList = mongoTemplate.find(query, ArtMaster.class);
        System.out.println("matchingArtList" + matchingArtList.size() + "matchingArtList22.........." + matchingArtList.size());
        return matchingArtList;
    }

    @Override
    public List<KeywordCountMaster> searchKeywordCountMaster(String keyword) {
        List<KeywordCountMaster> list = keywordCountMasterDao.findByKeywordContaining(keyword);
        list.sort(Comparator.comparing(KeywordCountMaster::getCount).reversed());

        return list;
    }

    @Override
    public List<ContributorArtMarkupMaster> getArtIdWiseContributorArtMarkup(String artId) {
        List<ContributorArtMarkupMaster> list = contributorArtMarkupDao.findByArtMaster_ArtId(artId);
        return list;
    }


    //        @Override
//    public List<ContributorArtMarkupMaster> getArtIdAndShapeWiseContributorArtMarkup(String artId, String shape) {
//        List<ContributorArtMarkupMaster> contributorArtMarkupList = contributorArtMarkupDao.findByArtMaster_ArtId(artId);
//        System.out.println("contributorArtMarkupList.."+contributorArtMarkupList.size());
//        List<ContributorArtMarkupMaster> matchingContributorArtMarkupList = new ArrayList<>();
//
//        List<OrientationMaster> orientationMasterList = orientationMasterDao.findAll();
//        System.out.println("orientationMasterList..."+orientationMasterList.size());
//
//        for (ContributorArtMarkupMaster contributorArtMarkup : contributorArtMarkupList) {
//            for (OrientationMaster orientationMaster : orientationMasterList) {
//                if (shape.equals(orientationMaster.getShape())) {
//                    // Match found based on shape, now check if it's the right artId
//                    if (contributorArtMarkup.getArtMaster().getArtId().equals(artId)) {
//                        // Both artId and shape match, add contributor art markup to the result list
//                        matchingContributorArtMarkupList.add(contributorArtMarkup);
//                        break; // No need to check other orientations
//                    }
//                }
//            }
//        }
//
//        return matchingContributorArtMarkupList;
//    }
    @Override
    public List<ContributorArtMarkupMaster> getArtIdAndShapeWiseContributorArtMarkup(String artId, String shape) {
        List<ContributorArtMarkupMaster> contributorArtMarkupList = contributorArtMarkupDao.findByArtMaster_ArtId(artId);
        List<ContributorArtMarkupMaster> matchingContributorArtMarkupList = new ArrayList<>();

        for (ContributorArtMarkupMaster contributorArtMarkup : contributorArtMarkupList) {
            ArtMaster artMaster = contributorArtMarkup.getArtMaster();
            if (artMaster != null && artMaster.getArtId().equals(artId)) {
                List<OrientationMaster> filteredOrientationMasters = new ArrayList<>();
                for (OrientationMaster orientationMaster : contributorArtMarkup.getOrientationMasters()) {
                    if (orientationMaster.getShape().equals(shape)) {
                        filteredOrientationMasters.add(orientationMaster);
                    }
                }
                if (!filteredOrientationMasters.isEmpty()) {
                    ContributorArtMarkupMaster filteredContributorArtMarkup = new ContributorArtMarkupMaster();
                    filteredContributorArtMarkup.setContributorArtMarkupId(contributorArtMarkup.getContributorArtMarkupId()); // Set contributorArtMarkupId
                    filteredContributorArtMarkup.setDate(contributorArtMarkup.getDate()); // Set date
                    filteredContributorArtMarkup.setArtMaster(artMaster);
                    filteredContributorArtMarkup.setOrientationMasters(filteredOrientationMasters);
                    matchingContributorArtMarkupList.add(filteredContributorArtMarkup);
                }
            }
        }

        return matchingContributorArtMarkupList;
    }

    @Override
    public List<ArtMaster> getAllArtMasterByStatus(String status) {
        List<ArtMaster> artMasterList=new ArrayList<>();
        artMasterList=artMasterDao.findAllByStatus(status);
        return artMasterList;
    }




    @Override
    public Long getByCountUser() {
        List<ArtMaster> artMasterList = artMasterDao.findAll();
        final Query query = new Query();
        final List<Criteria> criteria = new ArrayList<>();

        for (ArtMaster artMaster : artMasterList) {
            if (artMaster.getUserMaster() != null) {
                criteria.add(Criteria.where("userMaster.userId").is(artMaster.getUserMaster().getUserId()));
            }
        }
        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        return mongoTemplate.count(query, ArtMaster.class);
    }

    @Override
    public ArtMaster editArtMaster(String artId) {
        System.out.println("artId" + artId);
        ArtMaster artMaster = artMasterDao.getArtId(artId);
        System.out.println("artMaster" + artMaster.getImageMaster().getImageOrientation());
        return artMaster;
//        ArtMaster artMaster = new ArtMaster();
//        try {
//            Optional<ArtMaster> artMaster1 = artMasterDao.findById(artId);
//            artMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, artMaster));
//            System.out.println("artMaster" + artMaster.getImageMaster().getImageOrientation());
//            return artMaster;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return artMaster;
//        }
    }

    @Override
    public List<ArtMaster> searchByText(String searchText) {
        List<ArtMaster> artMasterList = artMasterDao.findAllByKeywordsLikeSearchText(searchText);
        return artMasterList;
    }

    @Override
    public List getSubjectIdWiseSubjectMaster(String subjectId) {
        List list = artMasterDao.findBySubjectMasterSubjectId(subjectId);
        return list;
    }

    @Override
    public List<ArtMaster> searchTextByArtName(String searchText) {
        List<ArtMaster> artMasterList = artMasterDao.findAllByArtNameLikeSearchText(searchText);
        return artMasterList;
    }

    @Override
    public Boolean getArtIdWiseChangeStatus(String artId) {
        Boolean flag = false;
        Optional<ArtMaster> artMasterOptional = artMasterDao.findByArtId(artId);
        if (artMasterOptional.isPresent()) {
            ArtMaster artMaster = artMasterOptional.get();
            artMaster.setStatus("Approved");
            artMasterDao.save(artMaster);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    @Override
    public Boolean getUserIdWiseApprovedPerChange(String userId) {
        Boolean flag = false;

        List<ArtMaster> artMasterList = artMasterDao.findByUserMasterUserId(userId);
        int n = artMasterList.size();
        double n1 = n;
        List<ArtMaster> masterList = artMasterDao.findByStatusAndUserMasterUserId("Approved", userId);
        Integer m = masterList.size();
        double m1 = m;
        double approvedPer = (m1 / n1) * 100;
        Optional<UserMaster> optionalUserMaster = userDao.findByUserId(userId);
        if (optionalUserMaster.isPresent()) {
            UserMaster userMaster = optionalUserMaster.get();
            userMaster.setApprovalPercentage(approvedPer);
            userDao.save(userMaster);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    @Override
    public List<ArtMaster> searchTextByUserFirstName(String userFirstName) {
        UserMaster userMaster = userDao.getuser(userFirstName);
        List<ArtMaster> artMasterList = artMasterDao.findByUserMasterUserId(userMaster.getUserId());
        return artMasterList;

    }

    @Override
    public List<ArtMaster> getSimilarImage(String image) {
        List<ArtMaster> list = artMasterDao.findByImage(image);
        return list;
    }

    @Override
    public List<ArtMaster> FindSimilarImageList(String image) throws JsonProcessingException {
        // API endpoint URL
//            String url = "https://ik.imagekit.io/kz6vwng9bi/ProductsNew_8sR3-11PE.png";
//        System.out.println("...url..."+url);
        List<ArtMaster> list = artMasterDao.findByImage(image);
        return list;

//            // Request body parameters
//            Map<String, Object> requestBody = new HashMap<>();
//            requestBody.put("src", image);
//
//            // Build the HTTP request
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(RequestBody.create(MediaType.parse("application/json"), new ObjectMapper().writeValueAsString(requestBody)))
//                    .build();
//
//            // Execute the HTTP request and parse the response
//            try {
//                Response response = client.newCall(request).execute();
//                String responseBody = response.body().string();
//                List<ArtMaster> artMasters = new ObjectMapper().readValue(responseBody, new TypeReference<List<ArtMaster>>() {});
//                return artMasters;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
    }

    @Override
    public List subjectNameWiseArtList(String subjectName) {
        Criteria criteria = Criteria.where("sujectMaster.subjectName").is(subjectName);

        Query query = Query.query(criteria);

        return mongoTemplate.find(query, ArtMaster.class);
    }

    @Override
    public List styleNameWiseArtList(String name) {
        Criteria criteria = Criteria.where("styleMaster.name").is(name);
        Query query = Query.query(criteria);

        return mongoTemplate.find(query, ArtMaster.class);
    }
}


//    public List<ArtMaster> getRecentlyViewedArts(int limit) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.HOUR_OF_DAY, -24); // limit to the past 24 hours
//        System.out.println("ddd"+calendar.getTime());
//        List<ArtMaster> list= artMasterDao.findByViewedAtGreaterThanEqualOrderByViewedAtDesc(calendar.getTime())
//                .stream().limit(limit).collect(Collectors.toList());
//        System.out.println("list"+list.size());
//        return list
//    }






