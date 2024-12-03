package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.CartDao;
import com.zplus.ArtnStockMongoDB.dao.PromoCodeDao;
import com.zplus.ArtnStockMongoDB.dao.UserGiftCodeDao;
import com.zplus.ArtnStockMongoDB.dao.UserPromoCodeDao;
import com.zplus.ArtnStockMongoDB.dto.req.UserPromoCodeRequest;
import com.zplus.ArtnStockMongoDB.dto.res.UserPromoCodeResponse;
import com.zplus.ArtnStockMongoDB.model.CartMaster;
import com.zplus.ArtnStockMongoDB.model.PromoCodeMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import com.zplus.ArtnStockMongoDB.model.UserPromoCodeMaster;
import com.zplus.ArtnStockMongoDB.service.UserPromoCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class UserPromoCodeServiceImpl implements UserPromoCodeService {

    @Autowired
    private UserPromoCodeDao userPromoCodeDao;
    @Autowired
    private PromoCodeDao promoCodeDao;
    @Autowired
    private CartDao cartDao;

    @Autowired
    private UserGiftCodeDao userGiftCodeDao;

    @Override
    public UserPromoCodeResponse createUserPromoCodeMaster(UserPromoCodeRequest userPromoCodeRequest) {
        UserPromoCodeResponse message = new UserPromoCodeResponse();

        CartMaster cartMaster = cartDao.findByUserMaster_UserId(userPromoCodeRequest.getUserId());
        UserPromoCodeMaster userPromoCodeMaster1 = userPromoCodeDao.findByUserMasterUserIdAndPromoCode(
                userPromoCodeRequest.getUserId(), userPromoCodeRequest.getPromoCode());

        if (userPromoCodeMaster1 != null && userPromoCodeMaster1.getPromoCode().equals(userPromoCodeRequest.getPromoCode())) {
            message.setMessage("Promo code or user already used");
            message.setFlag(false);
            return message;
        }

        PromoCodeMaster promoCodeMaster = promoCodeDao.findByPromoCode(userPromoCodeRequest.getPromoCode());
        System.out.println("promoCodeMaster"+promoCodeMaster);
        if (promoCodeMaster.getStatus().equals("InActive")) {
            message.setMessage("Promo code expired");
            message.setFlag(false);
            return message;
        }


        UserPromoCodeMaster userPromoCodeMaster = new UserPromoCodeMaster();
        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(userPromoCodeRequest.getUserId());
        userPromoCodeMaster.setUserMaster(userMaster);
        userPromoCodeMaster.setPromoCode(promoCodeMaster.getPromoCode());
        userPromoCodeMaster.setDiscount(promoCodeMaster.getDiscount());
        userPromoCodeMaster.setStatus("Unused");
        System.out.println("promoCodeMaster" + promoCodeMaster.getDiscount());

//        int maxDiscountAmount1 = (int) (cartMaster.getTotalAmount() * promoCodeMaster.getDiscount() / 100);

        int maxDiscountAmount1 = (int) (cartMaster.getProductsValue() * promoCodeMaster.getDiscount() / 100);

        double maxDiscountAmount= maxDiscountAmount1 ;
        System.out.println("  Max Discount Amounnt ="+maxDiscountAmount);

        System.out.println( promoCodeMaster.getMaxAmount());

        if (maxDiscountAmount > promoCodeMaster.getMaxAmount()) {
            userPromoCodeMaster.setDiscount(promoCodeMaster.getMaxAmount());
            System.out.println(" inside prmo max ");
        } else {
            userPromoCodeMaster.setDiscount(maxDiscountAmount);
            System.out.println(" inside  max ");
        }

        System.out.println("   Discount Amounnt ="+userPromoCodeMaster.getDiscount());
        try {
            userGiftCodeDao.deleteByGiftCode(cartMaster.getGiftCode());
            UserPromoCodeMaster um = userPromoCodeDao.save(userPromoCodeMaster);
            cartMaster.setPromoCodeAmount(um.getDiscount());
            cartMaster.setPromoCode(um.getPromoCode());
            cartMaster.setFinalAmount(cartMaster.getFinalAmount()+cartMaster.getGiftCodeAmount());
            cartMaster.setGiftCodeAmount(0.0);
            cartMaster.setGiftCode(null);
            cartMaster.setCodeType(userPromoCodeRequest.getCodeType());

//            double am= um.getDiscount();
//            double dm= cartMaster.getEstimateAmount();
//            double amt =dm-am;
//            cartMaster.setFinalAmount(amt);
            System.out.println("cartMaster.getEstimateAmount()..."+cartMaster.getEstimateAmount());
            // new change
//            cartMaster.setEstimateAmount(cartMaster.getEstimateAmount()-userPromoCodeMaster.getDiscount());
            System.out.println("cartMaster.getEstimateAmount()..."+cartMaster.getEstimateAmount());
            cartMaster.setFinalAmount(cartMaster.getFinalAmount()-userPromoCodeMaster.getDiscount());
            System.out.println("cartMaster.final Amount ()..."+cartMaster.getFinalAmount());

            //
            CartMaster cm = cartDao.save(cartMaster);
            System.out.println("cm..."+cm.getFinalAmount());
            System.out.println("cartMaster.getEstimateAmount()."+cm.getEstimateAmount());

            getPromocodeAmt(cm);
            message.setFlag(true);
            message.setMessage("Promo code applied successfully");
            message.setDiscount(um.getDiscount());
        } catch (Exception e) {
            e.printStackTrace();
            message.setFlag(false);
            message.setMessage("Failed to apply promo code");
        }
        return message;
    }

    public void getPromocodeAmt(CartMaster cartMaster) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double amt =  Double.parseDouble(decimalFormat.format(cartMaster.getEstimateAmount() - cartMaster.getPromoCodeAmount() - cartMaster.getGiftCodeAmount()));

        System.out.println("cartMaster.getGiftCodeAmount()"+cartMaster.getGiftCodeAmount());
        System.out.println("cartMaster.getEstimateAmount()..."+cartMaster.getEstimateAmount());
        System.out.println("a1"+cartMaster.getPromoCodeAmount());
        System.out.println("a2"+cartMaster.getGiftCodeAmount());

        System.out.println("amt"+amt);
        cartMaster.setFinalAmount(amt);
        cartDao.save(cartMaster);
    }

    @Override
    public Boolean updateUserPromoCodeMaster(UserPromoCodeRequest userPromoCodeRequest) {
        Boolean flag = false;
        UserPromoCodeMaster userPromoCodeMaster = new UserPromoCodeMaster();
        PromoCodeMaster promoCodeMaster = promoCodeDao.findByPromoCodeAndStatus(userPromoCodeRequest.getPromoCode(), "Active");
        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(userPromoCodeRequest.getUserId());
        userPromoCodeMaster.setUserMaster(userMaster);
        userPromoCodeMaster.setPromoCode(promoCodeMaster.getPromoCode());
        userPromoCodeMaster.setDiscount(promoCodeMaster.getDiscount());
        userPromoCodeMaster.setUserPromoCodeId(userPromoCodeRequest.getUserPromoCodeId());
        try {
            userPromoCodeDao.save(userPromoCodeMaster);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;

    }

    @Override
    public List getAllUserPromoCodeMaster() {
        List list = userPromoCodeDao.findAll();
        return list;
    }

    @Override
    public UserPromoCodeMaster editUserPromoCodeMaster(String userPromoCodeId) {
        UserPromoCodeMaster userPromoCodeMaster = new UserPromoCodeMaster();
        try {
            Optional<UserPromoCodeMaster> userPromoCodeMaster1 = userPromoCodeDao.findById(userPromoCodeId);
            userPromoCodeMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, userPromoCodeMaster));
            return userPromoCodeMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return userPromoCodeMaster;
        }
    }

    @Override
    public List getUserWisePromoCodeCodeMaster(String userId) {
        List list = userPromoCodeDao.findByUserMasterUserId(userId);
        return list;
    }
}
