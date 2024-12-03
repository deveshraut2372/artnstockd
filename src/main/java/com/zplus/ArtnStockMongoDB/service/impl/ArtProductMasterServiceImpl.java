package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.configuration.UniqueNumber;
import com.zplus.ArtnStockMongoDB.dao.*;
import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.ArtProductMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtProductMasterServiceImpl implements ArtProductMasterService {

    @Autowired
    private ArtProductMasterDao artProductMasterDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductMasterDao productMasterDao;

    @Autowired
    private ArtMasterDao artMasterDao;

    @Autowired
    private ProductSubSubCategoryDao productSubSubCategoryDao;

    @Autowired
    private ProductSubCategoryDao productSubCategoryDao;

    @Autowired
    private ProductMainCategoryDao productMainCategoryDao;

    @Autowired
    private ProductStyleRepository productStyleRepository;

    @Autowired
    private ProductColorRepository productColorRepository;

    @Autowired
    private SizeAndPriceRepository sizeAndPriceRepository;


    @Override
    public Boolean createArtProductMaster(ArtProductReqDto artProductReqDto) {
        Boolean flag=false;
        int i = 0;
        List list = new ArrayList();

        ArtProductMaster artProductMaster1=new ArtProductMaster();
        ArtProductMaster artProductMaster = new ArtProductMaster();

        ProductMaster productMaster = new ProductMaster();
        productMaster.setProductId(artProductReqDto.getProductId());
        artProductMaster.setProductMaster(productMaster);

        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(artProductReqDto.getUserId());
        artProductMaster.setUserMaster(userMaster);
        artProductMaster.setCanvasSize(artProductReqDto.getCanvasSize());
        artProductMaster.setCanvasX(artProductReqDto.getCanvasX());
        artProductMaster.setCanvasY(artProductReqDto.getCanvasY());
//        artProductMaster.setImages(artProductReqDto.getImages());

        artProductMaster.setSubmittedDate(new Date());
        artProductMaster.setReviewData(new Date());
        artProductMaster.setUpdatedDate(new Date());
        artProductMaster.setApproveDate(null);

        //   artname on productname

        ArtMaster artMaster = new ArtMaster();
        artMaster=artMasterDao.findByArtId(artProductReqDto.getArtId()).get();
//        artMaster.setArtId(artProductReqDto.getArtId());
        artProductMaster.setArtMaster(artMaster);
        artProductMaster.setDate(new Date());
        artProductMaster.setStatus("Active");
        artProductMaster.setQty(1);

        userMaster = userDao.getUserMaster(artProductReqDto.getUserId());
        System.out.println("markupPer.."+userMaster.getMarkupPer());
        productMaster = productMasterDao.getProductId(artProductReqDto.getProductId());
        System.out.println("basePrice..."+productMaster.getSizeAndPrices().get(i).getBasePrice());

        for(SizeAndPrice sizeAndPrice : productMaster.getSizeAndPrices()) {
            sizeAndPrice.setSize(productMaster.getSizeAndPrices().get(i).getSize());
            Double price =0.0;
            DecimalFormat df = new DecimalFormat("#.00");
            price = Double.valueOf(df.format(productMaster.getSizeAndPrices().get(i).getBasePrice()));
            sizeAndPrice.setBasePrice(price);

            System.out.println("basePrice..."+productMaster.getSizeAndPrices().get(i).getBasePrice());
            System.out.println("markupPer.."+userMaster.getMarkupPer());
            System.out.println("  i ==="+i);
            Double aa = productMaster.getSizeAndPrices().get(i).getBasePrice() * userMaster.getMarkupPer() / 100;
            System.out.println("aa.."+aa);
            Double sellPrice = productMaster.getSizeAndPrices().get(i).getBasePrice() + aa;
            sizeAndPrice.setSellPrice(sellPrice);

            list.add(sizeAndPrice);
        }

        artProductMaster.setSizeAndPrices(list);
        artProductMaster.setArtProductName(artProductReqDto.getArtProductName());
        artProductMaster.setArtProductName(artProductMaster.getArtProductName()+"_"+productMaster.getProductName());

        BeanUtils.copyProperties(artProductReqDto, artProductMaster);

        // new one add for  assing the artproduct to productSubCategoryId , productMainCategoryId, and productSubSubCategory
        ProductMainCategoryMaster productMainCategoryMaster=new ProductMainCategoryMaster();
        productMainCategoryMaster=productMainCategoryDao.findById(artProductReqDto.getProductMainCategoryId()).get();

        ProductSubCategoryMaster productSubCategoryMaster=new ProductSubCategoryMaster();
        productSubCategoryMaster=productSubCategoryDao.findById(artProductReqDto.getProductSubCategoryId()).get();

        ProductSubSubCategory productSubSubCategory=new ProductSubSubCategory();
        productSubSubCategory=productSubSubCategoryDao.findById(artProductReqDto.getProductSubSubCategoryId()).get();

//        artProductMaster.setProductMainCategoryId(productMainCategoryMaster.getProductMainCategoryId());
//        artProductMaster.setProductMainCategoryName(productMainCategoryMaster.getProductMainCategoryName());
//
//        artProductMaster.setProductSubCategoryId(productSubCategoryMaster.getProductSubCategoryId());
//        artProductMaster.setProductSubCategoryName(productSubCategoryMaster.getProductSubCategoryName());
//
////        artProductMaster.setProductSubSubCategoryId(productSubSubCategory.getProductSubSubCategoryId());
//        artProductMaster.setProductSubSubCategoryName(productSubSubCategory.getProductSubSubCategoryName());

        //

        try {
            artProductMaster1=artProductMasterDao.save(artProductMaster);
            flag= true;
        } catch (Exception e) {
            e.printStackTrace();
            flag= false;
        }

        String artProductNo = UniqueNumber.generateUniqueNumber();
        System.out.println("artProductNo: " + artProductNo);
        try {
            Optional<ArtProductMaster> optionalArtProductMaster = artProductMasterDao.findByArtProductId(artProductMaster1.getArtProductId());
            if (optionalArtProductMaster.isPresent()) {
                ArtProductMaster master = optionalArtProductMaster.get();
                master.setArtProductUniqueNo(artProductNo);
                ArtProductMaster artProductMaster2 = artProductMasterDao.save(master);
                System.out.println("artProductNo111: " + artProductMaster2.getArtProductUniqueNo());
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


//    public Boolean createArtProductMasterDe(ArtProductReqDto artProductReqDto) {
//        Boolean flag=false;
//        int i = 0;
//        List list = new ArrayList();
//
//
//
//        ArtProductMaster artProductMaster1=new ArtProductMaster();
//        ArtProductMaster artProductMaster = new ArtProductMaster();
//
//        ProductMaster productMaster = new ProductMaster();
//        productMaster.setProductId(artProductReqDto.getProductId());
//        artProductMaster.setProductMaster(productMaster);
//
//
//        UserMaster userMaster = new UserMaster();
//        userMaster.setUserId(artProductReqDto.getUserId());
//        artProductMaster.setUserMaster(userMaster);
//        artProductMaster.setCanvasSize(artProductReqDto.getCanvasSize());
//        artProductMaster.setCanvasX(artProductReqDto.getCanvasX());
//        artProductMaster.setCanvasY(artProductReqDto.getCanvasY());
//        artProductMaster.setImages(artProductReqDto.getImages());
//
//        artProductMaster.setSubmittedDate(new Date());
//        artProductMaster.setReviewData(new Date());
//        artProductMaster.setUpdatedDate(new Date());
//        artProductMaster.setApproveDate(null);
//
//        //   artname on productname
//
//        ArtMaster artMaster = new ArtMaster();
//        artMaster=artMasterDao.findByArtId(artProductReqDto.getArtId()).get();
////        artMaster.setArtId(artProductReqDto.getArtId());
//        artProductMaster.setArtMaster(artMaster);
//        artProductMaster.setDate(new Date());
//        artProductMaster.setStatus("Active");
//        artProductMaster.setQty(1);
//
//        // new one to add this
////        artProductMaster.setProductSubCategoryId(artProductReqDto.getProductSubCategoryId());
////        artProductMaster.setProductMainCategoryId(artProductReqDto.getProductMainCategoryId());
////        artProductMaster.setProductSubSubCategory(artProductReqDto.getProductSubSubCategory());
//
//        userMaster = userDao.getUserMaster(artProductReqDto.getUserId());
//        System.out.println("markupPer.."+userMaster.getMarkupPer());
//        productMaster = productMasterDao.getProductId(artProductReqDto.getProductId());
//        System.out.println("basePrice..."+productMaster.getSizeAndPrices().get(i).getBasePrice());
//
////        artProductReqDto.getProductSubSubCategory().get
//
//        final  ProductMaster productMaster1=productMaster;
//
////        for (SizeAndPrice sizeAndPrice : productMaster.getSizeAndPrices()) {
////            artProductReqDto.getProductSubSubCategory().getProductStyleList().stream().forEach(productStyle -> {
////                productStyle.getProductColors().stream().forEach(productColor ->{
////                    List<SizeAndPrice> sizeAndPriceList=new ArrayList<>();
////                    for (SizeAndPrice andPrice : productMaster1.getSizeAndPrices()) {
////                        SizeAndPrice sizeAndPrice1=new SizeAndPrice();
////                        sizeAndPrice1.setSize(andPrice.getSize());
////                        sizeAndPrice1.setBasePrice(andPrice.getBasePrice()+ productColor.getColorPrice());
////                        double markup=sizeAndPrice1.getMarkup()*sizeAndPrice1.getBasePrice()/100;
////                        DecimalFormat decimalFormat=new DecimalFormat("#.00");
////                        sizeAndPrice1.setSellPrice(Double.valueOf(decimalFormat.format(markup+sizeAndPrice1.getBasePrice())));
////                        sizeAndPrice1.setSizeName(sizeAndPrice1.getSizeName());
////                        sizeAndPrice1.setMarkup(sizeAndPrice1.getMarkup());
////                    }
////                });
////            });
////        }
//
//
//
//        //
//        for (SizeAndPrice sizeAndPrice : productMaster.getSizeAndPrices()) {
//            sizeAndPrice.setSize(productMaster.getSizeAndPrices().get(i).getSize());
//            Double price =0.0;
//            DecimalFormat df = new DecimalFormat("#.00");
//            price = Double.valueOf(df.format(productMaster.getSizeAndPrices().get(i).getBasePrice()));
//            sizeAndPrice.setBasePrice(price);
//
//            System.out                                    .println("basePrice..."+productMaster.getSizeAndPrices().get(i).getBasePrice());
//            System.out.println("markupPer.."+userMaster.getMarkupPer());
//            System.out.println("  i ==="+i);
//            Double aa = productMaster.getSizeAndPrices().get(i).getBasePrice() * userMaster.getMarkupPer() / 100;
//            System.out.println("aa.."+aa);
//            Double sellPrice = productMaster.getSizeAndPrices().get(i).getBasePrice() + aa;
//
//            sizeAndPrice.setSellPrice(sellPrice);
//
//            list.add(sizeAndPrice);
//        }
//        artProductMaster.setSizeAndPrices(list);
//        //
//
//
//        artProductMaster.setProductMainCategoryId(artProductReqDto.getProductMainCategoryId());
//        artProductMaster.setProductSubCategoryId(artProductReqDto.getProductSubCategoryId());
//
//        //  changes By Devesh  //
//        artProductMaster.setArtProductName(artProductReqDto.getArtProductName());
//        artProductMaster.setArtProductName(artProductMaster.getArtProductName()+"_"+productMaster.getProductName());
//        BeanUtils.copyProperties(artProductReqDto, artProductMaster);
//
//        try {
//            artProductMaster1=artProductMasterDao.save(artProductMaster);
//            flag= true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            flag= false;
//        }
//        String artProductNo = UniqueNumber.generateUniqueNumber();
//        System.out.println("artProductNo: " + artProductNo);
//        try {
//            Optional<ArtProductMaster> optionalArtProductMaster = artProductMasterDao.findByArtProductId(artProductMaster1.getArtProductId());
//            if (optionalArtProductMaster.isPresent()) {
//                ArtProductMaster master = optionalArtProductMaster.get();
//                master.setArtProductUniqueNo(artProductNo);
//                ArtProductMaster artProductMaster2 = artProductMasterDao.save(master);
//                System.out.println("artProductNo111: " + artProductMaster2.getArtProductUniqueNo());
//                flag = true;
//            } else {
//                flag = false;
//            }
//            return flag;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }


//    @Override
//    public List getProductSubCategoryIdWiseArtProductMaster(String productSubCategoryId) {
//        List list=artProductMasterDao.findByProductSubCategoryMaster_ProductSubCategoryId(productSubCategoryId);
//        return list;
//    }

    @Override
    public List getActiveArtProductMaster() {
        return artProductMasterDao.findAllByStatus("Active");
    }

    @Override
    public ArtProductMaster getArtProductIdData(String artProductId) {
        ArtProductMaster artProductMaster=new ArtProductMaster();
        try {
            Optional<ArtProductMaster> artProductMaster1=artProductMasterDao.findById(artProductId);
            artProductMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, artProductMaster));
            return artProductMaster;
        }
        catch (Exception e) {
            e.printStackTrace();
            return artProductMaster;
        }
    }

    @Override
    public List getAllArtProductMaster() {
        return artProductMasterDao.findAll();
    }

    @Override
    public Boolean createArtProductMasterD(ArtProductReqDto artProductReqDto) {

        System.out.println("   called craete artProduct");

        try {
            for (ProductSubSubCategory productSubSubCategory : artProductReqDto.getProductSubSubCategoryList()) {
                System.out.println("  productSubSubCategory.getProductSubCategoryId() ="+productSubSubCategory.getProductSubCategoryId());
                System.out.println(" artProductReqDto.getProductSubCategoryId() ="+artProductReqDto.getProductSubCategoryId());

                System.out.println(productSubSubCategory.getProductSubCategoryId().equalsIgnoreCase(artProductReqDto.getProductSubCategoryId()));

                if(productSubSubCategory.getProductSubCategoryId().equalsIgnoreCase(artProductReqDto.getProductSubCategoryId())) {
                    System.out.println("  inside the product Sub Sub Category ");

                    ArtProductMaster artProductMaster = new ArtProductMaster();
                    artProductMaster.setArtProductName(artProductReqDto.getArtProductName());
                    artProductMaster.setDate(new Date());
                    artProductMaster.setStatus("Active");

//                artProductMaster.setImage();         // artproductImage
//                artProductMaster.setSizeAndPrices(); // list of size and price

                    artProductMaster.setQty(1);
                    String uniqueNumber = UniqueNumber.generateUniqueNumber();
                    artProductMaster.setArtProductUniqueNo(uniqueNumber);
                    artProductMaster.setCanvasX(artProductReqDto.getCanvasX());
                    artProductMaster.setCanvasY(artProductReqDto.getCanvasY());
                    artProductMaster.setCanvasSize(artProductReqDto.getCanvasSize());

                    artProductMaster.setUserMaster(userDao.findByUserId(artProductReqDto.getUserId()).get());
                    artProductMaster.setArtMaster(artMasterDao.findByArtId(artProductReqDto.getArtId()).get());
                    artProductMaster.setProductMaster(productMasterDao.findByProductId(artProductReqDto.getProductId()).get());
                    artProductMaster.setSubmittedDate(new Date());
                    artProductMaster.setReviewData(new Date());
                    artProductMaster.setApproveDate(new Date());
                    artProductMaster.setUpdatedDate(new Date());
                    artProductMaster.setCollectionId(null);

                    artProductMaster.setProductMainCategoryMaster(artProductReqDto.getProductMainCategoryMaster());
                    artProductMaster.setProductSubCategoryMaster(artProductReqDto.getProductSubCategoryMaster());


//                    for (ProductSubSubCategory subSubCategory : artProductReqDto.getProductSubSubCategoryList())
//                    {
//                        if (subSubCategory.getProductSubSubCategoryId() == productSubSubCategory.getProductSubSubCategoryId())
//                        {

                            artProductMaster.setProductSubSubCategory(productSubSubCategory);
                            for (ProductStyle productStyle : artProductReqDto.getProductStyleList())
                            {
                                System.out.println("  inside the product Style Category ");
                                if(productStyle.getProductSubSubCategoryId().equalsIgnoreCase(productSubSubCategory.getProductSubSubCategoryId()))
                                {
                                    artProductMaster.setProductStyle(productStyle);
                                    for (ProductColor productColor : artProductReqDto.getProductColorList())
                                    {
                                        System.out.println("  inside the product Color Category ");
                                        if(productColor.getProductStyleId().equalsIgnoreCase(productStyle.getProductStyleId()))
                                        {
                                            System.out.println(" color Id ="+productColor.getProductColorId());
                                            artProductMaster.setProductColor(productColor);
                                            artProductMaster.setImage(productColor.getProductImage());
                                            List<SizeAndPrice> sizeAndPriceList = sizeAndPriceRepository.findAllByProductColorId(productColor.getProductColorId());
                                            artProductMaster.setSizeAndPrices(sizeAndPriceList);

                                            System.out.println(" sizeAndPriceList  size  ="+sizeAndPriceList.size());
//                                            for (SizeAndPrice sizeAndPrice : sizeAndPriceList) {
//
//                                            }
                                            artProductMasterDao.save(artProductMaster);
                                        }
                                        else {
                                            System.out.println("  outside ProductStyle" );
                                            System.out.println("  productColor.getProductStyleId() ="+productColor.getProductStyleId());
                                            System.out.println("  productStyle.getProductStyleId() ="+productStyle.getProductStyleId());
                                        }
                                    }
                                }else {
                                        System.out.println("  outide subsubcategory " );
                                        System.out.println("  productStyle.getProductSubSubCategoryId() ="+productStyle.getProductSubSubCategoryId());
                                        System.out.println("  productSubSubCat+egory.getProductSubSubCategoryId() ="+productSubSubCategory.getProductSubSubCategoryId());
                                }
                            }
//                        }
//                    }
                }else {
                    System.out.println("  outside sub category " );
                    System.out.println("  productSubSubCategory.getProductSubCategoryId() ="+productSubSubCategory.getProductSubCategoryId());
                    System.out.println(" artProductReqDto.getProductSubCategoryId() ="+artProductReqDto.getProductSubCategoryId());
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
//        return null;
    }
}


