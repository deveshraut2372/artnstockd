package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.configuration.UniqueNumber;
import com.zplus.ArtnStockMongoDB.dao.*;
import com.zplus.ArtnStockMongoDB.dto.req.CartArtFrameMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.res.CartArtSaveResponse;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.CartArtFrameMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class CartArtFrameMasterServiceImpl implements CartArtFrameMasterService {

    @Autowired
    private CartArtFrameDao cartArtFrameDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private MatDao matDao;
    @Autowired
    private PrintingSizeMasterDao printingSizeMasterDao;
    @Autowired
    private PrintingMaterialMasterDao printingMaterialMasterDao;

    @Autowired
    private OrientationMasterDao orientationMasterDao;
    @Autowired
    private FrameDao frameDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CartMasterServiceImpl cartMasterService;
    @Autowired
    private ArtMasterDao artMasterDao;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private UserDao userdao;
    @Autowired
    private CartMasterServiceImpl cartMasterServiceImpl;
    @Autowired
    private ShippingMethodDao shippingMethodDao;


    @Override
    public CartArtSaveResponse saveCartArtFrame(CartArtFrameMasterRequest cartArtFrameMasterRequest) {
        Boolean flag = false;
        CartArtSaveResponse cartArtSaveResponse=new CartArtSaveResponse();
        double amount = 0.0;

        DecimalFormat df=new DecimalFormat("0.00");

        UserMaster userMaster = userdao.getUserMaster(cartArtFrameMasterRequest.getUserId());
        CartMaster cartMaster = cartDao.findByUserMaster_UserId(cartArtFrameMasterRequest.getUserId());
        ShippingMethod shippingMethod = shippingMethodDao.findByShippingMethodName("Standard");
        System.out.println("   cart add ");

        System.out.println(" cartMaster.getTotalAmount "+cartMaster.getTotalAmount());

        CartArtFrameMaster cartArtFrameMaster = new CartArtFrameMaster();
        BeanUtils.copyProperties(cartArtFrameMasterRequest, cartArtFrameMaster);


//        System.out.println("price.." + cartArtFrameMasterRequest.getFrameMaster().getPrice());
//        System.out.println("mat..." + cartArtFrameMasterRequest.getMatMasterBottom().getPrice());
//        System.out.println("mattop..." + cartArtFrameMasterRequest.getMatMasterTop().getPrice());
//        System.out.println("printing material..." + cartArtFrameMasterRequest.getPrintingMaterialMaster().getPrintingMaterialPrice());
//        System.out.println("orientation..." + cartArtFrameMasterRequest.getOrientationMaster().getPrice());
//
//        Double rate = cartArtFrameMasterRequest.getFrameMaster().getPrice() + cartArtFrameMasterRequest.getMatMasterBottom().getPrice() + cartArtFrameMasterRequest.getMatMasterTop().getPrice() /*+ cartArtFrameMasterRequest.getPrintingSizeMaster().getPrice()*/ + cartArtFrameMasterRequest.getPrintingMaterialMaster().getPrintingMaterialPrice() + cartArtFrameMasterRequest.getOrientationMaster().getPrice();
//        System.out.println("rate" + rate);
        System.out.println(" cartMaster.getTotalAmount "+cartMaster.getTotalAmount());

        System.out.println("aa"+cartArtFrameMasterRequest.getTotalAmount());
        System.out.println("nn"+cartArtFrameMasterRequest.getQuantity());
        amount = cartArtFrameMasterRequest.getTotalAmount() * cartArtFrameMasterRequest.getQuantity();

        System.out.println(" cartMaster.getTotalAmount "+cartMaster.getTotalAmount());

        cartArtFrameMaster.setRate(Double.valueOf(df.format(cartArtFrameMasterRequest.getTotalAmount())));
        cartArtFrameMaster.setStatus("Incart");
        cartArtFrameMaster.setImgUrl(cartArtFrameMasterRequest.getImgUrl());
        userMaster.setUserId(userMaster.getUserId());
        cartArtFrameMaster.setUserMaster(userMaster);
        cartArtFrameMaster.setFrameMaster(cartArtFrameMasterRequest.getFrameMaster());
        cartArtFrameMaster.setMatMasterTop(cartArtFrameMasterRequest.getMatMasterTop());
        cartArtFrameMaster.setMatMasterBottom(cartArtFrameMasterRequest.getMatMasterBottom());
        cartArtFrameMaster.setAmount(Double.valueOf(df.format(amount)));
        cartArtFrameMaster.setCartArtFrameUniqueNo(UniqueNumber.generateUniqueNumber());
        cartArtFrameMaster.setDate(new Date());
        cartArtFrameMaster.setUpdatedDate(new Date());
        cartArtFrameMaster.setType("cartArtFrame");
        cartArtFrameMaster.setCartMaster(cartMaster);

        System.out.println(" cartMaster.getTotalAmount "+cartMaster.getTotalAmount());

        ArtMaster artFrameMaster =artMasterDao.getArtId(cartArtFrameMasterRequest.getArtId());
        artFrameMaster.setArtId(artFrameMaster.getArtId());
        cartArtFrameMaster.setArtMaster(artFrameMaster);
        cartArtFrameMaster.setArtFrameName(artFrameMaster.getArtName());

        System.out.println(" cartMaster.getTotalAmount "+cartMaster.getTotalAmount());

        try {
            CartArtFrameMaster cm = cartArtFrameDao.save(cartArtFrameMaster);
            System.out.println("cm...."+cm.getArtMaster().toString());
            cartArtSaveResponse.setFlag(true);
            cartArtSaveResponse.setCartArtFrameMaster(cm);
            List<CartArtFrameMaster> cartArtFrameMasters = cartArtFrameDao.findByStatusAndUserMaster_UserId("Incart", cm.getUserMaster().getUserId());
            System.out.println("list------------------------------------------------------------->>" + cartArtFrameMasters.size());
            Integer totalQty = 0;
            Double totalAmount = 0.0;

            if (cartMaster != null) {
                List<CartArtFrameMaster> list = new ArrayList<>();
                for (CartArtFrameMaster cartArtFrameMaster1 : cartArtFrameMasters) {
                    if (cartMaster.getCartArtFrameMaster() != null) {
                        totalAmount = totalAmount + cartArtFrameMaster1.getAmount();
                        totalQty = totalQty + cartArtFrameMaster1.getQuantity();
                    } else {
                        totalAmount = totalAmount + cm.getAmount();
                        totalQty = totalQty + cm.getQuantity();
                    }
                    list.add(cartArtFrameMaster1); // Add the updated CartArtFrameMaster object to the new list
                }

                totalAmount= Double.valueOf(df.format(totalAmount));

                cartMaster.setTotalAmount(totalAmount);
                cartMaster.setShippingMethod(shippingMethod);
                cartMaster.setUserMaster(userMaster);
                cartMaster.setCartArtFrameMaster(list);
                cartMaster.setStatus("Active");
                cartMaster.setCartDate(new Date());
                cartMaster.setEstimateShipping(shippingMethod.getShippingMethodPrice()*cartArtFrameMasterRequest.getQuantity());
                CartMaster cartMaster1 = cartDao.save(cartMaster);


                cartMasterServiceImpl.getCartMaster(cartArtFrameMasterRequest.getUserId(), cartMaster1.getCartId());

              flag=true;
            }
//            }
        } catch (Exception e) {
            e.printStackTrace();
            cartArtSaveResponse.setFlag(false);
        }

        // changes //
        cartArtSaveResponse.setCartMaster(cartMaster);
        //
        return cartArtSaveResponse;
    }


    @Override
    public Boolean IncreaseCartQty(String cartArtFrameId) {
        Boolean flag = false;
        try {
            CartArtFrameMaster cartArtFrameMaster = cartArtFrameDao.findByCartArtFrameId(cartArtFrameId);
            CartMaster cartMaster = cartDao.findByCartArtFrameMasterCartArtFrameId(cartArtFrameMaster.getCartArtFrameId());
            Integer qty = cartArtFrameMaster.getQuantity() + 1;
            List<CartArtFrameMaster> cartArtFrameMasters = cartDao.getCartId(cartMaster.getCartId());
            System.out.println("cartProductMasters" + cartArtFrameMasters.size());
            Double amt = cartArtFrameMaster.getRate() * qty;

            cartArtFrameMaster.setQuantity(qty);
            cartArtFrameMaster.setAmount(amt);
            CartArtFrameMaster cm = cartArtFrameDao.save(cartArtFrameMaster);




            cartMaster.getCartArtFrameMaster().stream().parallel().forEach(cartArtFrameMaster1 -> {
                if (cartArtFrameMaster1.getCartArtFrameId().equals(cartArtFrameMaster.getCartArtFrameId())) {
                    cartArtFrameMaster1.setQuantity(qty);
                    cartArtFrameMaster1.setAmount(amt);
                }
                CartMaster cartMaster1 = cartDao.save(cartMaster);
            });
//            cartMasterServiceImpl.getCartMaster(cartArtFrameMaster.getUserMaster().getUserId(), cartMaster1.getCartId());
            flag = true;


//            for (CartArtFrameMaster cartArtFrameMaster1 : cartMaster.getCartArtFrameMaster()) {
//                if (cartArtFrameMaster1.getCartArtFrameId().equals(cartArtFrameMaster.getCartArtFrameId())) {
//                    cartArtFrameMaster1.setQuantity(qty);
//                    cartArtFrameMaster1.setAmount(amt);
//                }
//                CartMaster cartMaster1 = cartDao.save(cartMaster);
//                cartMasterServiceImpl.getCartMaster(cartArtFrameMaster.getUserMaster().getUserId(), cartMaster1.getCartId());
//                flag = true;
//            }


        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public CartMaster getUserIdWiseCartMasterData(String userId) {
        CartMaster cartMaster = cartDao.findByUserMaster_UserId(userId);
        return cartMaster;
    }

    @Override
    public List<CartArtFrameMaster> getUserIdWiseCartArtFrameData(String userId) {
        List<CartArtFrameMaster> list = cartArtFrameDao.findByUserMaster_UserId(userId);
        return list;
    }

    @Override
    public Boolean deleteCart(String cartArtFrameId) {
        Boolean flag = false;
        CartArtFrameMaster cartArtFrameMaster = cartArtFrameDao.findByCartArtFrameId(cartArtFrameId);
        CartMaster cm = cartDao.findByUserMaster_UserId(cartArtFrameMaster.getUserMaster().getUserId());
        try {
            List<CartArtFrameMaster> cartArtFrameMasters = cm.getCartArtFrameMaster();
            cartArtFrameMasters.removeIf(item -> item.getCartArtFrameId().equals(cartArtFrameId));
            cm.setCartArtFrameMaster(cartArtFrameMasters);
            CartMaster cmm = cartDao.save(cm);
            System.out.println("ccc" + cmm.getCartArtFrameMaster().size());
            cartArtFrameDao.deleteById(cartArtFrameId);
//            cartMasterServiceImpl.getCartMaster(cartArtFrameMaster.getUserMaster().getUserId(), cm.getCartId());
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public Boolean DecreaseCartQty(String cartArtFrameId) {
        Boolean flag = false;
        try {
            CartArtFrameMaster cartArtFrameMaster = cartArtFrameDao.findByCartArtFrameId(cartArtFrameId);
            CartMaster cartMaster = cartDao.findByCartArtFrameMasterCartArtFrameId(cartArtFrameMaster.getCartArtFrameId());
            Integer qty = cartArtFrameMaster.getQuantity() - 1;
            List<CartArtFrameMaster> cartArtFrameMasters = cartDao.getCartId(cartMaster.getCartId());
            System.out.println("cartProductMasters" + cartArtFrameMasters.size());
            Double amt = cartArtFrameMaster.getRate() * qty;
            cartArtFrameMaster.setQuantity(qty);
            cartArtFrameMaster.setAmount(amt);
            CartArtFrameMaster cm = cartArtFrameDao.save(cartArtFrameMaster);

            for (CartArtFrameMaster cartArtFrameMaster1 : cartMaster.getCartArtFrameMaster()) {
                if (cartArtFrameMaster1.getCartArtFrameId().equals(cartArtFrameMaster.getCartArtFrameId())) {
                    cartArtFrameMaster1.setQuantity(qty);
                    cartArtFrameMaster1.setAmount(amt);
                }
                CartMaster cartMaster1 = cartDao.save(cartMaster);
                cartMasterServiceImpl.getCartMaster(cartArtFrameMaster.getUserMaster().getUserId(), cartMaster1.getCartId());
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public CartArtFrameMaster getCratArtFrameMaster(String cartArtFrameId) {
        CartArtFrameMaster cartArtFrameMaster=new CartArtFrameMaster();
        Optional<CartArtFrameMaster> cartArtFrameMaster1=cartArtFrameDao.findById(cartArtFrameId);
        cartArtFrameMaster1.ifPresent(master->BeanUtils.copyProperties(master,cartArtFrameMaster));
        return cartArtFrameMaster;
    }


    public void getAmount(String userId, CartMaster cartMaster) {
        Integer totalQty = 0;
        Double totalAmount = 0.0;
        UserMaster userMaster = userDao.getUserMaster(userId);

        CartMaster cartMaster1 = cartDao.findByUserMaster_UserId(userId);
        List<CartArtFrameMaster> cartArtFrameMasters = cartArtFrameDao.findByStatusAndUserMaster_UserId("Incart", userId);
        System.out.println("list" + cartArtFrameMasters.size());

        for (CartArtFrameMaster cartArtFrameMaster : cartArtFrameMasters) {
            if (cartMaster1.getCartProductMaster() == null) {
                totalAmount = totalAmount + cartArtFrameMaster.getAmount();
                totalQty = totalQty + cartArtFrameMaster.getQuantity();
                System.out.println("cartArtFrameMaster" + cartArtFrameMaster.getAmount());
            }
        }
        System.out.println("tm" + totalAmount);
        System.out.println("tq" + totalQty);
//tax=5.0
        Double taxAmount = (totalAmount * 5) / 100;
        System.out.println("taxAmount" + taxAmount);
//shipping charges=5
        double em = totalAmount + taxAmount + 5;
        System.out.println("em" + em);

        cartMaster.setEstimateAmount(em);
        cartMaster.setEstimateShipping(5.0);
        cartMaster.setTaxAmount(taxAmount);
        cartMaster.setTotalAmount(totalAmount);
        cartMaster.setTotalQty(totalQty);
        cartMaster.setUserMaster(userMaster);
        cartMaster.setCartArtFrameMaster(cartArtFrameMasters);
        cartMaster.setStatus("Active");
        cartMaster.setCartDate(new Date());
        CartMaster cm = cartDao.save(cartMaster1);
    }
}

