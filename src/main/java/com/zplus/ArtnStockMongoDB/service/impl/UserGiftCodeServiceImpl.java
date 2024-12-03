package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.CartDao;
import com.zplus.ArtnStockMongoDB.dao.GiftCodeDao;
import com.zplus.ArtnStockMongoDB.dao.UserGiftCodeDao;
import com.zplus.ArtnStockMongoDB.dao.UserPromoCodeDao;
import com.zplus.ArtnStockMongoDB.dto.req.UserGiftCodeRequest;
import com.zplus.ArtnStockMongoDB.dto.res.UserPromoCodeResponse;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.UserGiftCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserGiftCodeServiceImpl implements UserGiftCodeService {

    @Autowired
    private UserGiftCodeDao userGiftCodeDao;
    @Autowired
    private GiftCodeDao giftCodeDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private UserPromoCodeServiceImpl service;

    @Autowired
    private UserPromoCodeDao userPromoCodeDao;


    @Override
    public UserPromoCodeResponse createUserGiftCodeMaster(UserGiftCodeRequest userGiftCodeRequest) {

        UserPromoCodeResponse userPromoCodeResponse = new UserPromoCodeResponse();

        CartMaster cartMaster = cartDao.findByUserMaster_UserId(userGiftCodeRequest.getUserMasterId());

        GiftCodeMaster giftCodeMaster = giftCodeDao.findByGiftCode( userGiftCodeRequest.getGiftCode());
        if (giftCodeMaster.getStatus().equals("InActive")) {
            userPromoCodeResponse.setFlag(false);
            userPromoCodeResponse.setMessage("Gift Code is not valid");
            return userPromoCodeResponse;
        } else {
            UserGiftCodeMaster userGiftCodeMaster = new UserGiftCodeMaster();
            userGiftCodeMaster.setGiftCode(userGiftCodeRequest.getGiftCode());
            UserMaster userMaster = new UserMaster();
            userMaster.setUserId(userGiftCodeRequest.getUserMasterId());
            userGiftCodeMaster.setUserMaster(userMaster);
            userGiftCodeMaster.setDiscount(giftCodeMaster.getDiscount());
            userGiftCodeMaster.setStatus("Unused");

            if (cartMaster.getGiftCode() !=null)
            {
                cartMaster.setPromoCode(null);
                cartMaster.setPromoCodeAmount(0.0);
            }
            int maxDiscountAmount1 = (int) (cartMaster.getProductsValue() * giftCodeMaster.getDiscount() / 100);

           double maxDiscountAmount= maxDiscountAmount1 ;

            if (maxDiscountAmount > giftCodeMaster.getMaxAmount()) {
                userGiftCodeMaster.setDiscount(giftCodeMaster.getMaxAmount());
            } else {
                userGiftCodeMaster.setDiscount(maxDiscountAmount);
            }
            try {
              userPromoCodeDao.deleteByPromoCode(cartMaster.getPromoCode());
              UserGiftCodeMaster gm=  userGiftCodeDao.save(userGiftCodeMaster);
              userPromoCodeResponse.setDiscount(userGiftCodeMaster.getDiscount());
                cartMaster.setGiftCodeAmount(gm.getDiscount());
                cartMaster.setGiftCode(gm.getGiftCode());
                cartMaster.setFinalAmount(cartMaster.getFinalAmount()+cartMaster.getPromoCodeAmount());
                cartMaster.setCodeType(userGiftCodeRequest.getCodeType());
                //
                cartMaster.setPromoCodeAmount(0.0);
                cartMaster.setPromoCode(null);
                // changes
//                cartMaster.setPromoCodeAmount(userGiftCodeMaster.getDiscount());
//                cartMaster.setPromoCode(userGiftCodeMaster.getGiftCode());
//                //New
//                cartMaster.setEstimateAmount(cartMaster.getEstimateAmount()-userGiftCodeMaster.getDiscount());
//                //
                CartMaster cm= cartDao.save(cartMaster);
                service.getPromocodeAmt(cm);
                userPromoCodeResponse.setFlag(true);
                userPromoCodeResponse.setMessage("Success");
            } catch (Exception e) {
                userPromoCodeResponse.setFlag(false);
                userPromoCodeResponse.setMessage("Failed");
            }
        }
        return userPromoCodeResponse;
    }

    @Override
    public List getAllUserGiftCodeMaster() {
        return userGiftCodeDao.findAll();
    }

    @Override
    public UserGiftCodeMaster editUserGiftCodeMaster(String userGiftCodeId) {
        UserGiftCodeMaster userGiftCodeMaster = new UserGiftCodeMaster();
        try {
            Optional<UserGiftCodeMaster> userGiftCodeMaster1 = userGiftCodeDao.findById(userGiftCodeId);
            userGiftCodeMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, userGiftCodeMaster));
            return userGiftCodeMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return userGiftCodeMaster;
        }
    }

    @Override
    public List getUserWiseGiftCodeMaster(String userId) {
        List list = userGiftCodeDao.findByUserMaster_UserId(userId);
        return list;
    }
}
