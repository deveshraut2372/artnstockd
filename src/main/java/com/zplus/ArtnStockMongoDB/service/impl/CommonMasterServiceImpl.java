//package com.zplus.ArtnStockMongoDB.service.impl;
//
//import com.zplus.ArtnStockMongoDB.dao.ArtMasterDao;
//import com.zplus.ArtnStockMongoDB.dao.CommonDao;
//import com.zplus.ArtnStockMongoDB.dao.ProductMasterDao;
//import com.zplus.ArtnStockMongoDB.dto.req.CommonMasterReqDto;
//import com.zplus.ArtnStockMongoDB.dto.req.UpdateCommonMasterReqDto;
//import com.zplus.ArtnStockMongoDB.model.ArtMaster;
//import com.zplus.ArtnStockMongoDB.model.CommonMaster;
//import com.zplus.ArtnStockMongoDB.model.ProductMaster;
//import com.zplus.ArtnStockMongoDB.service.CommonMasterService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CommonMasterServiceImpl implements CommonMasterService {
//
//    @Autowired
//    private CommonDao commonDao;
//
//    @Autowired
//    private ArtMasterDao artMasterDao;
//
//    @Autowired
//    private ProductMasterDao productMasterDao;
//
//    @Override
//    public Boolean createCommonMaster(CommonMasterReqDto commonMasterReqDto) {
//        CommonMaster commonMaster = new CommonMaster();
//
//        ArtMaster artMaster=new ArtMaster();
//        artMaster.setArtId(commonMasterReqDto.getArtId());
//        commonMaster.setArtMaster(artMaster);
//        ArtMaster artMaster1=artMasterDao.getArtId(commonMasterReqDto.getArtId());
//
//        ProductMaster productMaster=new ProductMaster();
//        productMaster.setProductId(commonMasterReqDto.getProductId());
//        commonMaster.setProductMaster(productMaster);
//        ProductMaster productMaster1=productMasterDao.getProductId(commonMasterReqDto.getProductId());
//
//        Double price=artMaster1.getPrice()+productMaster1.getPrice();
//
//        commonMaster.setCommonPrice(price);
//        commonMaster.setQty(1);
//        BeanUtils.copyProperties(commonMasterReqDto, commonMaster);
//
//        try {
//            commonDao.save(commonMaster);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public Boolean updateCommonMaster(UpdateCommonMasterReqDto updateCommonMasterReqDto) {
//        CommonMaster commonMaster = new CommonMaster();
//        BeanUtils.copyProperties(updateCommonMasterReqDto, commonMaster);
//
//        ArtMaster artMaster=new ArtMaster();
//        artMaster.setArtId(updateCommonMasterReqDto.getArtId());
//        commonMaster.setArtMaster(artMaster);
//
//        ProductMaster productMaster=new ProductMaster();
//        productMaster.setProductId(updateCommonMasterReqDto.getProductId());
//        commonMaster.setProductMaster(productMaster);
//        try {
//            commonDao.save(commonMaster);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public List getAllCommonMaster() {
//        List<CommonMaster> commonMasterList = commonDao.findAll();
//        return commonMasterList;
//    }
//
//    @Override
//    public CommonMaster editCommonMaster(String commonId) {
//        CommonMaster commonMaster=new CommonMaster();
//        try {
//            Optional<CommonMaster> commonMaster1=commonDao.findById(commonId);
//            commonMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, commonMaster));
//            return commonMaster;
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return commonMaster;
//        }
//    }
//
//    @Override
//    public List getActiveCommonMaster() {
//        return commonDao.findAllByStatus("Active");
//    }
//
//    @Override
//    public List getArtIddWiseCommonMaster(String artId) {
//        List list=commonDao.findByArtMasterArtId(artId);
//        return list;
//    }
//
//    @Override
//    public List getProductIddWiseCommonMaster(String productId) {
//        List list=commonDao.findByProductMasterProductId(productId);
//        return list;
//    }
//
//
//}
