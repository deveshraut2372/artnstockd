package com.zplus.ArtnStockMongoDB.service.impl;


import com.zplus.ArtnStockMongoDB.Advice.Exception.NoValueFoundException;
import com.zplus.ArtnStockMongoDB.configuration.UniqueNumber;
import com.zplus.ArtnStockMongoDB.dao.*;
import com.zplus.ArtnStockMongoDB.dto.req.AdminArtProductRequest;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterFilterReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ArtProductReq;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.AdminArtProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class adminArtProductServiceImpl implements AdminArtProductService {

    @Autowired
    private AdminArtProductMasterDao adminArtProductMasterDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductMasterDao productMasterDao;

    @Autowired
    private ArtProductMasterDao artProductMasterDao;

    @Autowired
    private ArtMasterDao artMasterDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Boolean createAdminArtProductMaster(AdminArtProductRequest adminArtProductRequest) {
        Boolean flag=false;
        int i = 0;
        List list = new ArrayList();

//        ArtProductMaster artProductMaster1=new ArtProductMaster();
//        ArtProductMaster artProductMaster = new ArtProductMaster();

        AdminArtProductMaster adminArtProductMaster=new AdminArtProductMaster();
        AdminArtProductMaster adminArtProductMaster1=new AdminArtProductMaster();

        ProductMaster productMaster = new ProductMaster();
        productMaster.setProductId(adminArtProductRequest.getProductId());
        adminArtProductMaster.setProductMaster(productMaster);

        adminArtProductMaster.setAdminArtProductName(adminArtProductRequest.getAdminArtProductName());

        ArtProductMaster artProductMaster=new ArtProductMaster();
        artProductMaster=artProductMasterDao.findById(adminArtProductRequest.getArtProductId()).get();
        adminArtProductMaster.setArtProductMaster(artProductMaster);


        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(adminArtProductRequest.getUserId());
        adminArtProductMaster.setUserMaster(userMaster);
        adminArtProductMaster.setCanvasSize(adminArtProductRequest.getCanvasSize());
        adminArtProductMaster.setCanvasX(adminArtProductRequest.getCanvasX());
        adminArtProductMaster.setCanvasY(adminArtProductRequest.getCanvasY());

        adminArtProductMaster.setSubmittedDate(artProductMaster.getSubmittedDate());
        artProductMaster.setReviewData(artProductMaster.getReviewData());
        artProductMaster.setUpdatedDate(new Date());
        artProductMaster.setApproveDate(new Date());

//        adminArtProductMaster.setImage(adminArtProductRequest.getImage());
        List<ColorImagesModel> images =new ArrayList<>();
//        images.add(adminArtProductRequest.getImages());
        images.addAll( adminArtProductRequest.getImages());
        adminArtProductMaster.setImages(images);
//        adminArtProductMaster.setImages((List<ColorImagesModel>) adminArtProductRequest.getImages());

        //   artname on productname
//        adminArtProductMaster.setAdminArtProductName(artProductMaster.getArtProductName());

        ArtMaster artMaster = new ArtMaster();
        artMaster.setArtId(adminArtProductRequest.getArtId());
        artMaster=artMasterDao.getArtId(adminArtProductRequest.getArtId());

        adminArtProductMaster.setArtMaster(artMaster);
        adminArtProductMaster.setDate(new Date());
        adminArtProductMaster.setStatus("Active");
        adminArtProductMaster.setQty(1);
//        adminArtProductMaster.setKeywords(artMaster.getKeywords());

        userMaster = userDao.getUserMaster(adminArtProductRequest.getUserId());
        System.out.println("markupPer.."+userMaster.getMarkupPer());
        productMaster = productMasterDao.getProductId(adminArtProductRequest.getProductId());
        System.out.println("basePrice..."+productMaster.getSizeAndPrices().get(i).getBasePrice());

//        for (SizeAndPrice sizeAndPrice : productMaster.getSizeAndPrices()) {
//            sizeAndPrice.setSize(productMaster.getSizeAndPrices().get(i).getSize());
//            sizeAndPrice.setBasePrice(productMaster.getSizeAndPrices().get(i).getBasePrice());
//
//            System.out.println("basePrice..."+productMaster.getSizeAndPrices().get(i).getBasePrice());
//            System.out.println("markupPer.."+userMaster.getMarkupPer());
//            System.out.println("  i ==="+i);
//            Double aa = productMaster.getSizeAndPrices().get(i).getBasePrice() * userMaster.getMarkupPer() / 100;
//            System.out.println("aa.."+aa);
//            Double sellPrice = productMaster.getSizeAndPrices().get(i).getBasePrice() + aa;
//            sizeAndPrice.setSellPrice(sellPrice);
//
//            list.add(sizeAndPrice);
//        }

        for (SizeAndPrice sizeAndPrice : productMaster.getSizeAndPrices()) {
//            sizeAndPrice.setSize(productMaster.getSizeAndPrices().get(i).getSize());
//            sizeAndPrice.setBasePrice(productMaster.getSizeAndPrices().get(i).getBasePrice());

//            System.out.println("basePrice..."+productMaster.getSizeAndPrices().get(i).getBasePrice());
//            System.out.println("markupPer.."+userMaster.getMarkupPer());
//            System.out.println("  i ==="+i);
            Double aa = sizeAndPrice.getBasePrice() * userMaster.getMarkupPer() / 100;
            System.out.println("aa.."+aa);
            Double sellPrice = sizeAndPrice.getBasePrice() + aa;
            sizeAndPrice.setSellPrice(sellPrice);
            list.add(sizeAndPrice);
        }

        adminArtProductMaster.setSizeAndPrices(list);
        adminArtProductMaster.setAdminArtProductName(adminArtProductRequest.getAdminArtProductName());
//        adminArtProductMaster.setAdminArtProductPrice();
        BeanUtils.copyProperties(adminArtProductRequest, adminArtProductMaster);
        adminArtProductMaster.setStatus("Active");
        adminArtProductMaster.setType("product");
        try {
            adminArtProductMaster1=adminArtProductMasterDao.save(adminArtProductMaster);
            flag= true;
        } catch (Exception e) {
            e.printStackTrace();
            flag= false;
        }
        String artProductNo = UniqueNumber.generateUniqueNumber();
        System.out.println("artProductNo: " + artProductNo);
        try {
            Optional<AdminArtProductMaster> optionalAdminArtProductMaster = adminArtProductMasterDao.findByAdminArtProductId(adminArtProductMaster1.getAdminArtProductId());
            if (optionalAdminArtProductMaster.isPresent()) {
                AdminArtProductMaster master = optionalAdminArtProductMaster.get();
                master.setArtProductUniqueNo(artProductNo);
                AdminArtProductMaster adminArtProductMaster2 = adminArtProductMasterDao.save(master);
                System.out.println("artProductNo111: " + adminArtProductMaster2.getArtProductUniqueNo());
                flag = true;
            } else {
                flag = false;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<AdminArtProductMaster> getAllAdminArtProductMaster() {
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
        adminArtProductMasterList=adminArtProductMasterDao.findAll();
        return adminArtProductMasterList;
    }

    @Override
    public List<AdminArtProductMaster> getActiveArtProducts() {
        List<AdminArtProductMaster> adminArtProductMasterList = new ArrayList<>();
        adminArtProductMasterList = adminArtProductMasterDao.findAllByStatus("Active");
        return adminArtProductMasterList;
    }

    @Override
    public Optional<AdminArtProductMaster> getByAdminArtProductId(String adminArtProductId) {

        System.out.println(adminArtProductId);
//        AdminArtProductMaster adminArtProductMaster=new AdminArtProductMaster();
//        Optional<AdminArtProductMaster> adminArtProductMaster1=adminArtProductMasterDao.findById(adminArtProductId);
//        adminArtProductMaster1.ifPresent(adminArtProductMaster2 -> BeanUtils.copyProperties(adminArtProductMaster,adminArtProductMaster));

        Optional<AdminArtProductMaster> adminArtProductMaster=adminArtProductMasterDao.findByAdminArtProductId(adminArtProductId);

        if(adminArtProductMaster==null)
        {
            throw new NoValueFoundException("404"," No Value Found ");
        }
        return adminArtProductMasterDao.findByAdminArtProductId(adminArtProductId);
    }

    @Override
    public Boolean updateAdminArtProductMaster(AdminArtProductRequest adminArtProductRequest) {
        Boolean flag=false;
        int i = 0;
        List list = new ArrayList();

//        ArtProductMaster artProductMaster1=new ArtProductMaster();
//        ArtProductMaster artProductMaster = new ArtProductMaster();

        AdminArtProductMaster adminArtProductMaster=new AdminArtProductMaster();
        adminArtProductMaster=adminArtProductMasterDao.findByAdminArtProductId(adminArtProductRequest.getAdminArtProductId()).get();
        AdminArtProductMaster adminArtProductMaster1=new AdminArtProductMaster();

        ProductMaster productMaster = new ProductMaster();
        productMaster.setProductId(adminArtProductRequest.getProductId());
        adminArtProductMaster.setProductMaster(productMaster);

        adminArtProductMaster.setAdminArtProductName(adminArtProductRequest.getAdminArtProductName());

        ArtProductMaster artProductMaster=new ArtProductMaster();
        artProductMaster=artProductMasterDao.findById(adminArtProductRequest.getArtProductId()).get();
        adminArtProductMaster.setArtProductMaster(artProductMaster);


        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(adminArtProductRequest.getUserId());
        adminArtProductMaster.setUserMaster(userMaster);
        adminArtProductMaster.setCanvasSize(adminArtProductRequest.getCanvasSize());
        adminArtProductMaster.setCanvasX(adminArtProductRequest.getCanvasX());
        adminArtProductMaster.setCanvasY(adminArtProductRequest.getCanvasY());

//        adminArtProductMaster.setImage(adminArtProductRequest.getImage());
        List<ColorImagesModel> images =new ArrayList<>();
        images.addAll((List) adminArtProductRequest.getImages());
        adminArtProductMaster.setImages(images);

        //   artname on productname
//        adminArtProductMaster.setAdminArtProductName(artProductMaster.getArtProductName());


        ArtMaster artMaster = new ArtMaster();
        artMaster.setArtId(adminArtProductRequest.getArtId());
        artMaster=artMasterDao.getArtId(adminArtProductRequest.getArtId());

        adminArtProductMaster.setArtMaster(artMaster);
        adminArtProductMaster.setDate(new Date());
        //
        adminArtProductMaster.setStatus(adminArtProductRequest.getStatus());
//        adminArtProductMaster.setStatus("Active");
        //
        adminArtProductMaster.setQty(1);

        userMaster = userDao.getUserMaster(adminArtProductRequest.getUserId());
        System.out.println("markupPer.."+userMaster.getMarkupPer());
        productMaster = productMasterDao.getProductId(adminArtProductRequest.getProductId());
        System.out.println("basePrice..."+productMaster.getSizeAndPrices().get(i).getBasePrice());

        for (SizeAndPrice sizeAndPrice : productMaster.getSizeAndPrices()) {
            sizeAndPrice.setSize(productMaster.getSizeAndPrices().get(i).getSize());
            sizeAndPrice.setBasePrice(productMaster.getSizeAndPrices().get(i).getBasePrice());

            System.out.println("basePrice..."+productMaster.getSizeAndPrices().get(i).getBasePrice());
            System.out.println("markupPer.."+userMaster.getMarkupPer());
            System.out.println("  i ==="+i);
            Double aa = productMaster.getSizeAndPrices().get(i).getBasePrice() * userMaster.getMarkupPer() / 100;
            System.out.println("aa.."+aa);
            Double sellPrice = productMaster.getSizeAndPrices().get(i).getBasePrice() + aa;
            sizeAndPrice.setSellPrice(sellPrice);

            list.add(sizeAndPrice);
        }
        adminArtProductMaster.setSizeAndPrices(list);
        adminArtProductMaster.setAdminArtProductName(adminArtProductRequest.getAdminArtProductName());
        BeanUtils.copyProperties(adminArtProductRequest, adminArtProductMaster);
        adminArtProductMaster.setStatus("Active");
        try {
            adminArtProductMaster1=adminArtProductMasterDao.save(adminArtProductMaster);
            flag= true;
        } catch (Exception e) {
            e.printStackTrace();
            flag= false;
        }
        String artProductNo = UniqueNumber.generateUniqueNumber();
        System.out.println("artProductNo: " + artProductNo);
        try {
            Optional<AdminArtProductMaster> optionalAdminArtProductMaster = adminArtProductMasterDao.findByAdminArtProductId(adminArtProductMaster1.getAdminArtProductId());
            if (optionalAdminArtProductMaster.isPresent()) {
                AdminArtProductMaster master = optionalAdminArtProductMaster.get();
                master.setArtProductUniqueNo(artProductNo);
                AdminArtProductMaster adminArtProductMaster2 = adminArtProductMasterDao.save(master);
                System.out.println("artProductNo111: " + adminArtProductMaster2.getArtProductUniqueNo());
                flag = true;
            } else {
                flag = false;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<AdminArtProductMaster> getAdminArtProductsByStatus(String status) {
        List list=adminArtProductMasterDao.getAllByStatus(status);
        return list;
    }

    @Override
    public List<AdminArtProductMaster> getAdminArtProductsBySubCatagoryIdWise(String productSubCategoryId) {

        List<ProductMaster> productMasters=new ArrayList<>();
        productMasters=productMasterDao.findByProductSubCategoryMaster_ProductSubCategoryId(productSubCategoryId);
        System.out.println(" productMasters _size ="+productMasters.size());

        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
        productMasters.stream().forEach(productMaster -> {
            List<AdminArtProductMaster> adminArtProductMasterList1=new ArrayList<>();
            adminArtProductMasterList1=adminArtProductMasterDao.findAllByProductMaster_productId(productMaster.getProductId());
            System.out.println(" adminArtProductMasterList1size ="+adminArtProductMasterList1.size());
            adminArtProductMasterList.addAll(adminArtProductMasterList1);
                });
        return adminArtProductMasterList;
    }

    @Override
    public AdminArtProductMaster getAdminArtProductMaster(String adminArtProductId) {

        AdminArtProductMaster adminArtProductMaster =adminArtProductMasterDao.findAllByStatus("Active").stream().filter(adminArtProductMaster1 -> adminArtProductMaster1.getAdminArtProductId().equalsIgnoreCase(adminArtProductId)).findFirst().get();
        return adminArtProductMasterDao.findById(adminArtProductId).get();
    }

    @Override
    public List AdminArtProductFilter(ArtMasterFilterReqDto artMasterFilterReqDto, String type, String text) {
        System.out.println("  min price ="+ artMasterFilterReqDto.getMinPrice());
        System.out.println("  max price  ="+artMasterFilterReqDto.getMaxPrice());

        List<AdminArtProductMaster> list = adminArtProductMasterDao.findAll();

        int i = 0;
        List<AdminArtProductMaster> filtered = new ArrayList<>();
        List<AdminArtProductMaster> tempList = new ArrayList<>();
        if (type.equals("All") && text.equals("All")) {
            List<AdminArtProductMaster> adminArtProductMasters = adminArtProductMasterDao.findAll();
            if (artMasterFilterReqDto.getStyleName() == null && artMasterFilterReqDto.getUserFirstName() == null && artMasterFilterReqDto.getSubjectName() == null && artMasterFilterReqDto.getSize() == null && artMasterFilterReqDto.getOrientation() == null && artMasterFilterReqDto.getPrice() == null && artMasterFilterReqDto.getKeyword() == null && artMasterFilterReqDto.getColorCode() == null && artMasterFilterReqDto.getMinPrice() == null && artMasterFilterReqDto.getMaxPrice() == null) {
                return adminArtProductMasters;
            }
            if (tempList.isEmpty()) {
                tempList.addAll(adminArtProductMasters);
            }
            if (artMasterFilterReqDto.getStyleName() != null && !artMasterFilterReqDto.getStyleName().isEmpty()) {
                filtered = adminArtProductMasters.stream()
                        .filter(adminArtProductMaster -> adminArtProductMaster.getArtMaster().getStyleMaster() != null && adminArtProductMaster.getArtMaster().getStyleMaster().getName().equals(artMasterFilterReqDto.getStyleName()))
                        .collect(Collectors.toList());
            } else {
                filtered = adminArtProductMasters;
            }

            filtered=filtered.stream()
                    .filter(artMasterFilterReqDto.getSubjectName() != null && !artMasterFilterReqDto.getSubjectName().isEmpty()
                            ? (adminArtProductMaster -> adminArtProductMaster.getArtMaster().getSubjectMaster() != null && adminArtProductMaster.getArtMaster().getSubjectMaster().getSubjectName() != null && adminArtProductMaster.getArtMaster().getSubjectMaster().getSubjectName().equals(artMasterFilterReqDto.getSubjectName()))
                            : (adminArtProductMaster -> true))
                    .filter(artMasterFilterReqDto.getMinPrice() != null && artMasterFilterReqDto.getMinPrice() != 0 ? (t -> t.getArtProductMaster().getArtMaster().getPrice()!=null && t.getArtProductMaster().getArtMaster().getPrice().compareTo(artMasterFilterReqDto.getMinPrice())>=0 ) : (t -> true))
                    .filter(artMasterFilterReqDto.getMaxPrice() != null && artMasterFilterReqDto.getMaxPrice() != 0 ? (t -> t.getArtProductMaster().getArtMaster().getPrice()!=null && t.getArtProductMaster().getArtMaster().getPrice().compareTo(artMasterFilterReqDto.getMaxPrice())<=0 ) : (t -> true))
                    .filter(artMasterFilterReqDto.getSize() != null && !artMasterFilterReqDto.getSize().isEmpty() ? (t -> t.getSize().equals(artMasterFilterReqDto.getSize())) : (t -> true))
                    .filter(artMasterFilterReqDto.getOrientation() != null && !artMasterFilterReqDto.getOrientation().isEmpty() ? (t -> t.getArtProductMaster().getArtMaster().getOrientation().equals(artMasterFilterReqDto.getOrientation())) : (t -> true))
                    .filter(artMasterFilterReqDto.getUserFirstName() != null && !artMasterFilterReqDto.getUserFirstName().isEmpty() ? (t -> t.getUserMaster() != null && t.getUserMaster().getUserFirstName().equalsIgnoreCase(artMasterFilterReqDto.getUserFirstName())) : (t -> true))
                    .filter(artMasterFilterReqDto.getKeyword() != null && !artMasterFilterReqDto.getKeyword().isEmpty() ? (t -> t.getArtProductMaster().getArtMaster().getKeywords().contains(artMasterFilterReqDto.getKeyword())) : (t -> true))
                    .filter(artMasterFilterReqDto.getColorCode() != null && !artMasterFilterReqDto.getColorCode().isEmpty() ? (t -> t.getArtProductMaster().getArtMaster().getImageMaster() != null && t.getArtProductMaster().getArtMaster().getImageMaster().getColorInfos() != null && t.getArtProductMaster().getArtMaster().getImageMaster().getColorInfos().stream().anyMatch(colorInfo -> colorInfo.getColor().equalsIgnoreCase(artMasterFilterReqDto.getColorCode()))) : (t -> true))
                    .collect(Collectors.toList());
            System.out.println("filtered = " + filtered.size());


            filtered = filtered.stream()
                    .filter(artMasterFilterReqDto.getSubjectName() != null && !artMasterFilterReqDto.getSubjectName().isEmpty()
                            ? (adminArtProductMaster -> adminArtProductMaster.getArtMaster().getSubjectMaster() != null && adminArtProductMaster.getArtMaster().getSubjectMaster().getSubjectName() != null && adminArtProductMaster.getArtMaster().getSubjectMaster().getSubjectName().equals(artMasterFilterReqDto.getSubjectName()))
                            : (adminArtProductMaster -> true))
                    .filter(artMasterFilterReqDto.getMinPrice() != null && artMasterFilterReqDto.getMinPrice() != 0 ? (t -> t.getArtMaster().getPrice()!=null && t.getArtMaster().getPrice().compareTo(artMasterFilterReqDto.getMinPrice())>=0 ) : (t -> true))
                    .filter(artMasterFilterReqDto.getMaxPrice() != null && artMasterFilterReqDto.getMaxPrice() != 0 ? (t -> t.getArtMaster().getPrice()!=null && t.getArtMaster().getPrice().compareTo(artMasterFilterReqDto.getMaxPrice())<=0 ) : (t -> true))
                    .filter(artMasterFilterReqDto.getSize() != null && !artMasterFilterReqDto.getSize().isEmpty() ? (t -> t.getSize().equals(artMasterFilterReqDto.getSize())) : (t -> true))
                    .filter(artMasterFilterReqDto.getOrientation() != null && !artMasterFilterReqDto.getOrientation().isEmpty() ? (t -> t.getArtMaster().getOrientation().equals(artMasterFilterReqDto.getOrientation())) : (t -> true))
                    .filter(artMasterFilterReqDto.getUserFirstName() != null && !artMasterFilterReqDto.getUserFirstName().isEmpty() ? (t -> t.getUserMaster() != null && t.getUserMaster().getUserFirstName().equalsIgnoreCase(artMasterFilterReqDto.getUserFirstName())) : (t -> true))
                    .filter(artMasterFilterReqDto.getKeyword() != null && !artMasterFilterReqDto.getKeyword().isEmpty() ? (t -> t.getArtMaster().getKeywords().contains(artMasterFilterReqDto.getKeyword())) : (t -> true))
                    .filter(artMasterFilterReqDto.getColorCode() != null && !artMasterFilterReqDto.getColorCode().isEmpty() ? (t -> t.getArtMaster().getImageMaster() != null && t.getArtMaster().getImageMaster().getColorInfos() != null && t.getArtMaster().getImageMaster().getColorInfos().stream().anyMatch(colorInfo -> colorInfo.getColor().equalsIgnoreCase(artMasterFilterReqDto.getColorCode()))) : (t -> true))
                    .collect(Collectors.toList());
            System.out.println("filtered = " + filtered.size());

            for (AdminArtProductMaster adminArtProductMaster : filtered) {
                System.out.println("  adminArtProductMaster ="+adminArtProductMaster);
            }

//            if(artMasterFilterReqDto.getMinPrice()!=null && artMasterFilterReqDto.getMaxPrice()!=null)
//            {
//                filtered = filtered.stream().filter(t -> t.getPrice().compareTo(artMasterFilterReqDto.getMinPrice())>=0 && t.getPrice().compareTo(artMasterFilterReqDto.getMaxPrice())<=0).collect(Collectors.toList());
//            }

        } else if (type.equals("subject")) {
            Criteria criteria = Criteria.where("artMaster.subjectMaster.subjectName").is(text);
            Query query = Query.query(criteria);
            List<AdminArtProductMaster> subList = mongoTemplate.find(query, AdminArtProductMaster.class);
            if (artMasterFilterReqDto.getStyleName() != null && !artMasterFilterReqDto.getStyleName().isEmpty()) {
                filtered = subList.stream()
                        .filter(adminArtProductMaster -> adminArtProductMaster.getArtMaster().getStyleMaster() != null && adminArtProductMaster.getArtMaster().getStyleMaster().getName().equals(artMasterFilterReqDto.getStyleName()))
                        .collect(Collectors.toList());
            } else {
                filtered = subList;
            }
            filtered = filtered.stream()
                    .filter(artMasterFilterReqDto.getMinPrice() != null && artMasterFilterReqDto.getMinPrice() != 0 && artMasterFilterReqDto.getMinPrice() != null && artMasterFilterReqDto.getMinPrice() != 0? (t -> t.getArtMaster().getPrice()!=null && t.getArtMaster().getPrice().compareTo(artMasterFilterReqDto.getMinPrice())>=0 && t.getArtMaster().getPrice().compareTo(artMasterFilterReqDto.getMaxPrice())<=0) : (t -> true))
                    .filter(artMasterFilterReqDto.getSize() != null && !artMasterFilterReqDto.getSize().isEmpty() ? (t -> t.getSize().equals(artMasterFilterReqDto.getSize())) : (t -> true))
                    .filter(artMasterFilterReqDto.getOrientation() != null && !artMasterFilterReqDto.getOrientation().isEmpty() ? (t -> t.getArtMaster().getOrientation().equals(artMasterFilterReqDto.getOrientation())) : (t -> true))
                    .filter(artMasterFilterReqDto.getUserFirstName() != null && !artMasterFilterReqDto.getUserFirstName().isEmpty() ? (t -> t.getUserMaster() != null && t.getUserMaster().getUserFirstName().equalsIgnoreCase(artMasterFilterReqDto.getUserFirstName())) : (t -> true))
                    .filter(artMasterFilterReqDto.getKeyword() != null && !artMasterFilterReqDto.getKeyword().isEmpty() ? (t -> t.getArtMaster().getKeywords().contains(artMasterFilterReqDto.getKeyword())) : (t -> true))
                    .filter(artMasterFilterReqDto.getColorCode() != null && !artMasterFilterReqDto.getColorCode().isEmpty() ? (t -> t.getArtMaster().getImageMaster() != null && t.getArtMaster().getImageMaster().getColorInfos() != null && t.getArtMaster().getImageMaster().getColorInfos().stream().anyMatch(colorInfo -> colorInfo.getColor().equalsIgnoreCase(artMasterFilterReqDto.getColorCode()))) : (t -> true))
                    .collect(Collectors.toList());

//            if(artMasterFilterReqDto.getMinPrice()!=null && artMasterFilterReqDto.getMaxPrice()!=null)
//            {
//                filtered = filtered.stream().filter(t -> t.getPrice().compareTo(artMasterFilterReqDto.getMinPrice())>=0 && t.getPrice().compareTo(artMasterFilterReqDto.getMaxPrice())<=0).collect(Collectors.toList());
//            }

        } else if (type.equals("style")) {
            Criteria criteria = Criteria.where("artMaster.styleMaster.name").is(text);
            Query query = Query.query(criteria);
            List<AdminArtProductMaster> styleList = mongoTemplate.find(query, AdminArtProductMaster.class);
            if (artMasterFilterReqDto.getSubjectName() != null && !artMasterFilterReqDto.getSubjectName().isEmpty()) {
                filtered = styleList.stream()
                        .filter(adminArtProductMaster -> adminArtProductMaster.getArtMaster().getSubjectMaster() != null && adminArtProductMaster.getArtMaster().getSubjectMaster().getSubjectName().equals(artMasterFilterReqDto.getSubjectName()))
                        .collect(Collectors.toList());
            } else {
                filtered = styleList;
            }
            filtered = filtered.stream()
                    .filter(artMasterFilterReqDto.getMinPrice() != null && artMasterFilterReqDto.getMinPrice() != 0 && artMasterFilterReqDto.getMinPrice() != null && artMasterFilterReqDto.getMinPrice() != 0? (t -> t.getArtMaster().getPrice()!=null && t.getArtMaster().getPrice().compareTo(artMasterFilterReqDto.getMinPrice())>=0 && t.getArtMaster().getPrice().compareTo(artMasterFilterReqDto.getMaxPrice())<=0) : (t -> true))
                    .filter(artMasterFilterReqDto.getSize() != null && !artMasterFilterReqDto.getSize().isEmpty() ? (t -> t.getSize().equals(artMasterFilterReqDto.getSize())) : (t -> true))
                    .filter(artMasterFilterReqDto.getOrientation() != null && !artMasterFilterReqDto.getOrientation().isEmpty() ? (t -> t.getArtMaster().getOrientation().equals(artMasterFilterReqDto.getOrientation())) : (t -> true))
                    .filter(artMasterFilterReqDto.getUserFirstName() != null && !artMasterFilterReqDto.getUserFirstName().isEmpty() ? (t -> t.getUserMaster() != null && t.getUserMaster().getUserFirstName().equalsIgnoreCase(artMasterFilterReqDto.getUserFirstName())) : (t -> true))
                    .filter(artMasterFilterReqDto.getKeyword() != null && !artMasterFilterReqDto.getKeyword().isEmpty() ? (t -> t.getArtMaster().getKeywords().contains(artMasterFilterReqDto.getKeyword())) : (t -> true))
                    .filter(artMasterFilterReqDto.getColorCode() != null && !artMasterFilterReqDto.getColorCode().isEmpty() ? (t -> t.getArtMaster().getImageMaster() != null && t.getArtMaster().getImageMaster().getColorInfos() != null && t.getArtMaster().getImageMaster().getColorInfos().stream().anyMatch(colorInfo -> colorInfo.getColor().equalsIgnoreCase(artMasterFilterReqDto.getColorCode()))) : (t -> true))
                    .collect(Collectors.toList());

            if(artMasterFilterReqDto.getMinPrice()!=null && artMasterFilterReqDto.getMaxPrice()!=null)
            {
                filtered = filtered.stream().filter(t -> t.getArtMaster().getPrice().compareTo(artMasterFilterReqDto.getMinPrice())>=0 && t.getArtMaster().getPrice().compareTo(artMasterFilterReqDto.getMaxPrice())<=0).collect(Collectors.toList());
            }

        }
//        else if (type.equals("search")) {
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

        return filtered;
    }

    public List<AdminArtProductMaster> getAllAdminArtProducts(ArtProductReq artProductReq)
    {
        System.out.println("  ArtProductReq ="+artProductReq.toString());
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
        Query query = new Query();
        Criteria criteria = new Criteria();

        if(artProductReq.getProductId()!=null)
        criteria = criteria.and("productMaster.productId").is(artProductReq.getProductId());
        if(artProductReq.getArtId()!=null)
        criteria = criteria.and("artMaster.artId").is(artProductReq.getArtId());

        query.addCriteria(criteria);
        adminArtProductMasterList=mongoTemplate.find(query,AdminArtProductMaster.class);

        return adminArtProductMasterList;


//        Criteria criteria = Criteria.where("artMaster.subjectMaster.subjectName").is(text);
//        Query query = Query.query(criteria);

//        Criteria criteria = Criteria.where("userMaster.userId").is(userId);
//        Query query = new Query(criteria).with(Sort.by(Sort.Direction.DESC, "recentlySearchId"));
//        List<RecentlySearchMaster> list = mongoTemplate.find(query, RecentlySearchMaster.class).stream().limit(10).collect(Collectors.toList());
//        return list;


    }


}
