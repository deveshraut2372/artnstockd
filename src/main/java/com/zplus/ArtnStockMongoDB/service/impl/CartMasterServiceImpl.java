package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.configuration.UniqueNumber;
import com.zplus.ArtnStockMongoDB.configuration.UserUniqueNumberGenerator;
import com.zplus.ArtnStockMongoDB.dao.*;
import com.zplus.ArtnStockMongoDB.dto.UpdateEstimateAmountGiftCodeRequest;
import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.*;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.CartMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartMasterServiceImpl implements CartMasterService {

    UserUniqueNumberGenerator userUniqueNumberGenerator = new UserUniqueNumberGenerator();
    @Autowired
    private CartDao cartDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ArtProductMasterDao artProductMasterDao;
    @Autowired
    private ProductMasterDao productMasterDao;
    @Autowired
    private CartProductDao cartProductDao;
    @Autowired
    private CartArtFrameDao cartArtFrameDao;
    @Autowired
    private UserPromoCodeDao userPromoCodeDao;
    @Autowired
    private PromoCodeDao promoCodeDao;
    @Autowired
    private GiftCodeDao giftCodeDao;
    @Autowired
    private UserGiftCodeDao userGiftCodeDao;
    @Autowired
    private UserDao userdao;
    @Autowired
    private ShippingMethodDao shippingMethodDao;

    @Autowired
    private AdminArtProductMasterDao adminArtProductMasterDao;

    @Autowired
    private CartAdminArtProductDao cartAdminArtProductDao;

    @Autowired
    private DiscountMasterDao discountMasterDao;


    @Autowired
    private MongoTemplate mongoTemplate;


//    @Override
//    public Boolean deleteCart(String cartProductId) {
//        Boolean flag = false;
//        CartProductMaster cartProductMaster = cartProductDao.findByCartProductId(cartProductId);
//        try {
//            cartProductDao.deleteById(cartProductId);
//            getCartMaster(cartProductMaster.getUserMaster().getUserId(), cartProductMaster.getCartMaster());
//            cartDao.save(cartProductMaster.getCartMaster());
//            flag = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            flag = false;
//        }
//        return flag;
//    }

    @Override
    public CartMaster getUserIdWiseCartData(String userId) {
        CartMaster cartMaster = cartDao.findByUserMaster_UserId(userId);
        System.out.println("cartMaster : " + cartMaster.getUserMaster().getUserId());
        return cartMaster;
    }

    @Override
    public List<CartProductMaster> getUserIdWiseCartProductData(String userId) {
        List<CartProductMaster> cartProductMasters = cartProductDao.findByUserMaster_UserId(userId);
        return cartProductMasters;
    }

    @Override
    public Boolean saveCartProduct(AddToCartProductRequest addToCartProductRequest) {
        Boolean flag = false;
        double amount = 0.0;
        System.out.println(addToCartProductRequest.toString());

        //
        ArtProductMaster artProductMaster = new ArtProductMaster();
        if(addToCartProductRequest.getArtProductMaster()!=null) {
            artProductMaster= artProductMasterDao.getArtProductMaster(addToCartProductRequest.getArtProductMaster().getArtProductId());
        }
        //
//        ArtProductMaster artProductMaster = artProductMasterDao.getArtProductMaster(addToCartProductRequest.getArtProductMaster().getArtProductId());
        UserMaster userMaster = userdao.getUserMaster(addToCartProductRequest.getUserId());

        ShippingMethod shippingMethod = shippingMethodDao.findByShippingMethodName("Standard");

        // changes  admin_artproductMaster
//        AdminArtProductMaster adminArtProductMaster = new AdminArtProductMaster();
//        if(addToCartProductRequest.getAdminArtProductId()!=null) {
//           adminArtProductMaster = adminArtProductMasterDao.findByAdminArtProductId(addToCartProductRequest.getAdminArtProductId()).get();
//        }
        //

        CartMaster cartMaster = cartDao.findByUserMaster_UserId(addToCartProductRequest.getUserId());
        System.out.println(cartMaster.getCartArtFrameMaster());
        CartProductMaster cartProductMaster = new CartProductMaster();
        cartProductMaster.setProductName(addToCartProductRequest.getProductName());
        cartProductMaster.setCartProductNo(UniqueNumber.generateUniqueNumber());
        cartProductMaster.setQuantity(addToCartProductRequest.getQuantity());
        cartProductMaster.setStatus("Incart");
        cartProductMaster.setCartProductUniqueNo(UniqueNumber.generateUniqueNumber());
        cartProductMaster.setUserMaster(userMaster);
        cartProductMaster.setArtProductMaster(artProductMaster);
        cartProductMaster.setCartMaster(cartMaster);
        cartProductMaster.setType("cartProduct");
//        // changes
//        cartProductMaster.setAdminArtProductMaster(adminArtProductMaster);
//        //

        System.out.println(cartMaster);
        for (SizeAndPrice sizeAndPrice : artProductMaster.getSizeAndPrices()) {
            Double rate = sizeAndPrice.getSellPrice();
            if (sizeAndPrice.getSize().equals(addToCartProductRequest.getSize())) {
                System.out.println("------------------------------------------------------->> I am in Size and price loop ");
                cartProductMaster.setSize(addToCartProductRequest.getSize());
                cartProductMaster.setRate(rate);
                amount = addToCartProductRequest.getQuantity() * rate;
                System.out.println("------------------------------------------------------->> I am in Size and price loop value of amount " + amount);
            }
        }
        cartProductMaster.setAmount(amount);
        try {
            CartProductMaster cm = cartProductDao.save(cartProductMaster);

            List<CartProductMaster> cartProductMasters = cartProductDao.findByStatusAndUserMaster_UserId("Incart", cm.getUserMaster().getUserId());
            System.out.println("list------------------------------------------------------------->>" + cartProductMasters.size());
            Integer totalQty = 0;
            Double totalAmount = 0.0;

            if (cartMaster != null) {
                System.out.println("done.......");
                List<CartProductMaster> list = new ArrayList<>();
                for (CartProductMaster cartProductMaster1 : cartProductMasters) {
                    System.out.println("test 1");
                    if (cartMaster.getCartProductMaster() != null) {
                        System.out.println("test 2");
                        totalAmount = totalAmount + cartProductMaster1.getAmount();
                        totalQty = totalQty + cartProductMaster1.getQuantity();
                        System.out.println("cart master quatity " + cartProductMaster1.getQuantity());
                    } else {
                        System.out.println("test 3");
                        totalAmount = totalAmount + cm.getAmount();
                        totalQty = totalQty + cm.getQuantity();
                    }
                    list.add(cartProductMaster1); // Add the updated CartArtFrameMaster object to the new list
                }
                cartMaster.setStyle(addToCartProductRequest.getStyle());
                cartMaster.setShippingMethod(shippingMethod);
                cartMaster.setUserMaster(userMaster);
                cartMaster.setCartProductMaster(list);
                cartMaster.setTotalCount(list.size() + cartMaster.getCartArtFrameMaster().size());
                cartMaster.setStatus("Active");
                cartMaster.setCartDate(new Date());
                cartMaster.setEstimateShipping(cartMaster.getEstimateShipping()+(shippingMethod.getShippingMethodPrice()*addToCartProductRequest.getQuantity()));

                CartMaster cartMaster1 = cartDao.save(cartMaster);
                getCartMaster(addToCartProductRequest.getUserId(), cartMaster1.getCartId());
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Transactional
    @Async
    public void getCartMaster(String userId, String cartId) {
        System.out.println("i am in art frame master get cart master");
//        ResDto resDto=new ResDto();

        ResDto resDtoCartProduct = new ResDto();
        resDtoCartProduct.setTotalAmount(0.0);
        ResDto resDtoCartArtFrame=new ResDto();
        resDtoCartArtFrame.setTotalAmount(0.0);
        ResDto resDtoCartAdminArtFrame=new ResDto();
        resDtoCartAdminArtFrame.setTotalAmount(0.0);

        Integer cnt1 = 0;
        Integer cnt = 0;
        Integer cnt2 =0;

        Double productsValue=0.0;

        CartMaster cartMaster = cartDao.findByCartId(cartId).get();
        UserMaster userMaster = userdao.getUserMaster(userId);

        ShippingMethod shippingMethod=cartMaster.getShippingMethod();
        Double shippingCharges=0.0;


        if (cartMaster.getCartProductMaster() != null) {
            List<CartProductMaster> cartProductMasters = cartProductDao.findByStatusAndUserMaster_UserId("Incart", userId);
            if (cartProductMasters != null) {
                List<CartProductMaster> list = new ArrayList<>();

//                Aggregation aggregation=new Aggregation(
//                        Aggregation.match(Criteria.where("userMaster.userId").is(userId),
//                                            Criteria.where("status").is("userId"))
//                                .
//                );
                resDtoCartProduct.setTotalAmount(cartProductMasters.stream().parallel().mapToDouble(CartProductMaster::getAmount).sum());
                resDtoCartProduct.setTotalQty(cartProductMasters.stream().parallel().mapToInt(CartProductMaster::getQuantity).sum());
                productsValue=resDtoCartProduct.getTotalAmount();
                shippingCharges=shippingCharges+(shippingMethod.getShippingMethodPrice()*resDtoCartProduct.getTotalQty());

//                for (CartProductMaster cartProductMaster1 : cartProductMasters) {
//                    resDtoCartProduct.setTotalAmount(resDtoCartProduct.getTotalAmount() + cartProductMaster1.getAmount());
//                    resDtoCartProduct.setTotalQty(resDtoCartProduct.getTotalQty() + cartProductMaster1.getQuantity());
//                    productsValue=productsValue+cartProductMaster1.getAmount();
//                    shippingCharges=shippingCharges+(shippingMethod.getShippingMethodPrice()*cartProductMaster1.getQuantity());
//                }

                cnt1 = cartMaster.getCartProductMaster().size();
            }
        } else {
            cnt1 = 0;
        }

//        System.out.println(" resDtoCartArtFrame totalAmount ="+ resDtoCartArtFrame.getTotalAmount());
        resDtoCartArtFrame.setTotalAmount(0.0);



        if (cartMaster.getCartArtFrameMaster() != null) {
            List<CartArtFrameMaster> cartArtFrameMasters = cartArtFrameDao.findByStatusAndUserMaster_UserId("Incart", userId);
            List<CartArtFrameMaster> list = new ArrayList<>();
            if (cartArtFrameMasters != null) {
                System.out.println("done.......");

                resDtoCartArtFrame.setTotalAmount(cartArtFrameMasters.stream().parallel().mapToDouble(CartArtFrameMaster::getAmount).sum());
                resDtoCartArtFrame.setTotalQty(cartArtFrameMasters.stream().parallel().mapToInt(CartArtFrameMaster::getQuantity).sum());
                productsValue=resDtoCartArtFrame.getTotalAmount();
                shippingCharges=shippingCharges+(shippingMethod.getShippingMethodPrice()*resDtoCartArtFrame.getTotalQty());


//                for (CartArtFrameMaster cartArtFrameMaster1 : cartArtFrameMasters) {
////                    System.out.println(" resDtoCartArtFrame totalAmount ="+ resDtoCartArtFrame.getTotalAmount());
////                    System.out.println("   cartArtFrameMaster1.getAmount() in method ="+cartArtFrameMaster1.getAmount());
//                    resDtoCartArtFrame.setTotalAmount(resDtoCartArtFrame.getTotalAmount() + cartArtFrameMaster1.getAmount());
//                    resDtoCartArtFrame.setTotalQty(resDtoCartArtFrame.getTotalQty() + cartArtFrameMaster1.getQuantity());
//                    shippingCharges=shippingCharges+(shippingMethod.getShippingMethodPrice()*cartArtFrameMaster1.getQuantity());
//                }

                cnt = cartMaster.getCartArtFrameMaster().size();
            }
        } else {
            cnt = 0;
        }

//        System.out.println(" res Dto totalAmount ="+ resDtoCartAdminArtFrame.getTotalAmount());

        if (cartMaster.getCartArtFrameMaster() != null) {
            List<CartAdminArtProductMaster> cartAdminArtProductMasterList = cartAdminArtProductDao.findByStatusAndUserMaster_UserId("Incart", userId);
            List<CartAdminArtProductMaster> list = new ArrayList<>();
            if (cartAdminArtProductMasterList != null) {
                System.out.println("done.......");

                resDtoCartAdminArtFrame.setTotalAmount(cartAdminArtProductMasterList.stream().parallel().mapToDouble(CartAdminArtProductMaster::getAmount).sum());
                resDtoCartAdminArtFrame.setTotalQty(cartAdminArtProductMasterList.stream().parallel().mapToInt(CartAdminArtProductMaster::getQuantity).sum());
                productsValue=resDtoCartAdminArtFrame.getTotalAmount();
                shippingCharges=shippingCharges+(shippingMethod.getShippingMethodPrice()*resDtoCartAdminArtFrame.getTotalQty());

//                for (CartAdminArtProductMaster cartAdminArtProductMaster : cartAdminArtProductMasterList) {
//                    resDtoCartAdminArtFrame.setTotalAmount(resDtoCartAdminArtFrame.getTotalAmount() + cartAdminArtProductMaster.getAmount());
//                    resDtoCartAdminArtFrame.setTotalQty(resDtoCartAdminArtFrame.getTotalQty() + cartAdminArtProductMaster.getQuantity());
//                    productsValue=productsValue+cartAdminArtProductMaster.getAmount();
//                    shippingCharges=shippingCharges+(shippingMethod.getShippingMethodPrice()*cartAdminArtProductMaster.getQuantity());
//                }

                cnt2 = cartMaster.getCartAdminArtProductMaster().size();
            }
        }else
        {
            cnt2=0;
        }

        cartMaster.setTotalCount(cnt2 + cnt1 + cnt );
//        cartMaster.setEstimateShipping(5.0);

        double amt =resDtoCartProduct.getTotalAmount()+resDtoCartArtFrame.getTotalAmount()+resDtoCartAdminArtFrame.getTotalAmount();
        DecimalFormat df=new DecimalFormat("#.00");
        amt= Double.parseDouble(df.format(amt));
        cartMaster.setTotalAmount(amt);
        cartMaster.setTotalQty(resDtoCartProduct.getTotalQty()+resDtoCartArtFrame.getTotalQty()+resDtoCartAdminArtFrame.getTotalQty());
        cartMaster.setUserMaster(userMaster);
        cartMaster.setStatus("Active");
        cartMaster.setCartDate(new Date());
//        cartMaster.setTotalCount(cartMaster.getCartArtFrameMaster().size() + cartMaster.getCartProductMaster().size());
        cartMaster.setTaxAmount(cartMaster.getTotalAmount() * cartMaster.getTax() / 100);
// Round all amounts to 2 decimal places
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double totalAmount = Double.parseDouble(decimalFormat.format(cartMaster.getTotalAmount()));
        double taxAmount = Double.parseDouble(decimalFormat.format(cartMaster.getTaxAmount()));
        double estimateShipping=shippingCharges;
//        double estimateShipping = Double.parseDouble(decimalFormat.format(cartMaster.getShippingMethod().getShippingMethodPrice()));
        double estimateAmount = Double.parseDouble(decimalFormat.format(totalAmount + taxAmount));
        estimateAmount=estimateAmount+estimateShipping;
        double finalAmount = estimateAmount;



        cartMaster.setTotalAmount(totalAmount);
        cartMaster.setTaxAmount(taxAmount);
        cartMaster.setEstimateShipping(estimateShipping);
        cartMaster.setEstimateAmount(estimateAmount);
        cartMaster.setFinalAmount(finalAmount);
        cartMaster.setProductsValue(productsValue);

        if(cartMaster.getPromoCode()!=null)
        {
            cartMaster.setFinalAmount(cartMaster.getFinalAmount()-cartMaster.getProductsValue());
        }

        if(cartMaster.getGiftCode()!=null)
        {
            cartMaster.setFinalAmount(cartMaster.getFinalAmount()-cartMaster.getProductsValue());
        }

//        countByUserMaster_UserIdAndStatus
        int cnt_=cartProductDao.countByUserMaster_UserIdAndStatus( userId,"Incart");
        int cnt_1=cartArtFrameDao.countByUserMaster_UserIdAndStatus( userId,"Incart");
        int cnt_2=cartAdminArtProductDao.countByUserMaster_UserIdAndStatus( userId,"Incart");

        System.out.println("  count ="+(cnt_1+cnt_2+cnt_));

        if(cnt_1+cnt_2+cnt_<=0)
        {
            cartMaster.setEstimateShipping(0.0);
            cartMaster.setEstimateAmount(0.0);
            cartMaster.setFinalAmount(0.0);
        }


        CartMaster cartMaster1 = cartDao.save(cartMaster);

        // New Discount Master ;
        DiscountMaster discountMaster=new DiscountMaster();
        List<DiscountMaster> discountMasterList=discountMasterDao.findAllByStatus("Active");
//        discountMaster=discountMasterList.stream().findFirst().get();
        discountMaster=discountMasterList.get(0);
        Double discountvalue=0.0;
        if(userMaster.getBuyCount()==0)
        {
            discountvalue=discountMaster.getFirstDiscount();
        }
        if(discountMaster.getFestivalDiscountStatus())
        {
            discountvalue=discountvalue+discountMaster.getFestivalDiscount();
        }
        if(discountMaster.getSeasonalDiscountStatus())
        {
            discountvalue=discountvalue+discountMaster.getSeasonalDiscount();
        }

        System.out.println("  Discount value ="+discountvalue);

        ///

//        cartMaster.setEstimateShipping(5.0);
//        cartMaster.setTotalAmount(resDto.getTotalAmount());
//        cartMaster.setTotalQty(resDto.getTotalQty());
//        cartMaster.setUserMaster(userMaster);
//        cartMaster.setTaxAmount(cartMaster.getTotalAmount() * cartMaster.getTax() / 100);
//        cartMaster.setEstimateAmount(cartMaster.getTotalAmount() + cartMaster.getTaxAmount() + cartMaster.getEstimateShipping());
//        cartMaster.setFinalAmount(cartMaster.getTotalAmount() + cartMaster.getTaxAmount() + cartMaster.getEstimateShipping());
//        cartMaster.setStatus("Active");
//        cartMaster.setCartDate(new Date());
//        cartMaster.setTotalCount(cartMaster.getCartArtFrameMaster().size()+cartMaster.getCartProductMaster().size());
//        CartMaster cartMaster1 = cartDao.save(cartMaster);
//        System.out.println("cartMaster1" + cartMaster1.getEstimateAmount());
    }

    @Override
    public Message updateCartStatus(String cartId) {
        Message message = new Message();
        Optional<CartMaster> cartMasterOptional = cartDao.findByCartId(cartId);
        if (cartMasterOptional.isPresent()) {
            CartMaster cartMaster = cartMasterOptional.get();
            cartMaster.setStatus("InActive");
            cartDao.save(cartMaster);
            message.setMessage("Cart Status Updated Successfully");
            message.setFlag(true);
        } else {
            message.setMessage("Cart Status Not Updated Successfully");
            message.setFlag(false);
        }
        return message;
    }

//    @Override
//    public UserWiseCartResponse UserWiseGetTotalQty(String userId) {
//        UserWiseCartResponse userWiseCartResponse = new UserWiseCartResponse();
//        List<CartArtFrameMaster> list = new ArrayList<>();
//        List<CartProductMaster> list1=new ArrayList<>();
//        try {
//            CartMaster cartMaster = cartDao.findByUserMasterUserIdAndStatus(userId, "Active");
//            list = cartMaster.getCartArtFrameMaster();
//            list1=cartMaster.getCartProductMaster();
//            Integer cnt=list.size()+list1.size();
//            System.out.println("list.........." + cnt);
//            userWiseCartResponse.setTotalCount(cnt);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return userWiseCartResponse;
//    }

    @Override
    public UserWiseCartResponse UserWiseGetTotalQty(String userId) {
        UserWiseCartResponse userWiseCartResponse = new UserWiseCartResponse();
        List<CartArtFrameMaster> list = new ArrayList<>();
        List<CartProductMaster> list1 = new ArrayList<>();
        List<CartAdminArtProductMaster> list2=new ArrayList<>();
        try {
            CartMaster cartMaster = cartDao.findByUserMasterUserIdAndStatus(userId, "Active");
            Integer cnt = 0;
            Integer cnt1 = 0;
            Integer cnt2 = 0;
//            if (cartMaster != null) {
//                list = cartMaster.getCartArtFrameMaster();
//                list1 = cartMaster.getCartProductMaster();
//                Integer cnt = list.size() + list1.size();
//                System.out.println("list.........." + cnt);
//                userWiseCartResponse.setTotalCount(cnt);
//            }

            list=cartMaster.getCartArtFrameMaster().stream().parallel().filter(cartArtFrameMaster -> cartArtFrameMaster.getStatus().equalsIgnoreCase("Incart")).collect(Collectors.toList());
            list1=cartMaster.getCartProductMaster().stream().parallel().filter(cartProductMaster -> cartProductMaster.getStatus().equalsIgnoreCase("Incart")).collect(Collectors.toList());
            list2=cartMaster.getCartAdminArtProductMaster().stream().parallel().filter(cartAdminArtProductMaster -> cartAdminArtProductMaster.getStatus().equalsIgnoreCase("Incart")).collect(Collectors.toList());

            if (cartMaster.getCartProductMaster() != null) {
                cnt = list1.size();
            } else {
                cnt = 0;
            }
            if (cartMaster.getCartArtFrameMaster() != null) {
                cnt1 = list.size();
            } else {
                cnt1 = 0;
            }

            if(cartMaster.getCartAdminArtProductMaster() !=null)
            {
                cnt2= list2.size();
            }else{
                cnt2=0;
            }
            userWiseCartResponse.setTotalCount(cnt + cnt1 +cnt2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userWiseCartResponse;
    }


    @Override
    public Message CartIdWiseDeleteCartMaster(String cartId) {
        Message message = new Message();
        try {
            cartDao.deleteById(cartId);
            message.setMessage("Cart Deleted Successfully");
            message.setFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("Cart Not Deleted Successfully");
            message.setFlag(false);
        }
        return message;
    }

//    @Override
//    public UpdateOrderStatusRes UpdateEstimateAmountUsingPromoCode(UpdateEstimateAmountRequest updateEstimateAmountRequest) {
//        UpdateOrderStatusRes updateOrderStatusRes = new UpdateOrderStatusRes();
//        double estAmount = 0.0;
//
//        System.out.println("codePromo" + updateEstimateAmountRequest.getPromoCode());
//        System.out.println("userId" + updateEstimateAmountRequest.getUserId());
//        CartMaster cartMaster = cartDao.findByUserMaster_UserId(updateEstimateAmountRequest.getUserId());
//
//        PromoCodeMaster promoCodeMaster = promoCodeDao.findByPromoCode(updateEstimateAmountRequest.getPromoCode());
//
//        UserPromoCodeMaster userPromoCodeMaster = userPromoCodeDao.findByUserMaster_UserIdAndPromoCode(updateEstimateAmountRequest.getUserId(), updateEstimateAmountRequest.getPromoCode());
//        System.out.println("userPromoCodeMaster" + userPromoCodeMaster.toString());
//        if (userPromoCodeMaster.getStatus() != null && userPromoCodeMaster.getStatus().equals("Unused")) {
//
//            if (userPromoCodeMaster.getDiscount() > promoCodeMaster.getMaxAmount()) {
//                estAmount = cartMaster.getEstimateAmount() - promoCodeMaster.getMaxAmount();
//            } else {
//                estAmount = cartMaster.getEstimateAmount() - userPromoCodeMaster.getDiscount();
//            }
//
//            System.out.println("estAmount" + estAmount);
//            cartMaster.setEstimateAmount(estAmount);
//            CartMaster sm = cartDao.save(cartMaster);
//            System.out.println("sm" + sm.toString());
//            updateOrderStatusRes.setMsg("Promo Code Applied Successfully");
//            updateOrderStatusRes.setFlag(true);
//        } else {
//            updateOrderStatusRes.setMsg("Promo Code Already Used");
//            updateOrderStatusRes.setFlag(false);
//        }
//        return updateOrderStatusRes;
//    }


    @Override
    public UpdateOrderStatusRes UpdateEstimateAmountUsingPromoCode(UpdateEstimateAmountRequest updateEstimateAmountRequest) {
        UpdateOrderStatusRes updateOrderStatusRes = new UpdateOrderStatusRes();
        double estAmount = 0.0;

        System.out.println("codePromo" + updateEstimateAmountRequest.getPromoCode());
        System.out.println("userId" + updateEstimateAmountRequest.getUserId());
        CartMaster cartMaster = cartDao.findByUserMaster_UserId(updateEstimateAmountRequest.getUserId());

        PromoCodeMaster promoCodeMaster = promoCodeDao.findByPromoCode(updateEstimateAmountRequest.getPromoCode());

        UserPromoCodeMaster userPromoCodeMaster = userPromoCodeDao.findByUserMaster_UserIdAndPromoCode(updateEstimateAmountRequest.getUserId(), updateEstimateAmountRequest.getPromoCode());
//        UserPromoCodeMaster userPromoCodeMaster=userPromoCodeDao.findByPromoCode(updateEstimateAmountRequest.getPromoCode());

        System.out.println("userPromoCodeMaster" + userPromoCodeMaster.toString());
        if (userPromoCodeMaster.getStatus() != null && userPromoCodeMaster.getStatus().equals("Unused")) {

            if (userPromoCodeMaster.getDiscount() > promoCodeMaster.getMaxAmount()) {
                estAmount = cartMaster.getEstimateAmount() - promoCodeMaster.getMaxAmount();
            } else {
                estAmount = cartMaster.getEstimateAmount() - userPromoCodeMaster.getDiscount();
            }

            System.out.println("estAmount" + estAmount);
            cartMaster.setEstimateAmount(estAmount);

            CartMaster sm = cartDao.save(cartMaster);
            System.out.println("sm" + sm.toString());
            updateOrderStatusRes.setMsg("Promo Code Applied Successfully");
            updateOrderStatusRes.setFlag(true);
        } else {
            updateOrderStatusRes.setMsg("Promo Code Already Used");
            updateOrderStatusRes.setFlag(false);
        }
        return updateOrderStatusRes;
        }

    @Override
    public UpdateOrderStatusRes UpdateEstimateAmountUsingGiftCode(UpdateEstimateAmountGiftCodeRequest updateEstimateAmountGiftCodeRequest) {
        UpdateOrderStatusRes updateOrderStatusRes = new UpdateOrderStatusRes();
        double estAmount = 0.0;
        CartMaster cartMaster = cartDao.findByUserMaster_UserId(updateEstimateAmountGiftCodeRequest.getUserId());
        GiftCodeMaster giftCodeMaster = giftCodeDao.findByGiftCode(updateEstimateAmountGiftCodeRequest.getGiftCode());
        UserGiftCodeMaster userGiftCodeMaster = userGiftCodeDao.findByUserMaster_UserIdAndGiftCode(updateEstimateAmountGiftCodeRequest.getUserId(), updateEstimateAmountGiftCodeRequest.getGiftCode());
        if (userGiftCodeMaster.getStatus() != null && userGiftCodeMaster.getStatus().equals("Unused")) {

            if (userGiftCodeMaster.getDiscount() > giftCodeMaster.getMaxAmount()) {
                estAmount = cartMaster.getEstimateAmount() - giftCodeMaster.getMaxAmount();
            } else {
                estAmount = cartMaster.getEstimateAmount() - userGiftCodeMaster.getDiscount();
            }

            System.out.println("estAmount" + estAmount);
            cartMaster.setEstimateAmount(estAmount);
            CartMaster sm = cartDao.save(cartMaster);
            System.out.println("sm" + sm.toString());
            updateOrderStatusRes.setMsg("Gift Code Applied Successfully");
            updateOrderStatusRes.setFlag(true);
        } else {
            updateOrderStatusRes.setMsg("Gift Code Already Used");
            updateOrderStatusRes.setFlag(false);
        }
        return updateOrderStatusRes;
    }

    @Override
    public Boolean IncreaseCartQty(String cartProductId) {
        Boolean flag = false;
        try {
            CartProductMaster cartProductMaster = cartProductDao.findByCartProductId(cartProductId);
            CartMaster cartMaster = cartDao.findByCartProductMasterCartProductId(cartProductMaster.getCartProductId());
            Integer qty = cartProductMaster.getQuantity() + 1;
            List<CartProductMaster> cartProductMasters = cartDao.getCartMasterId(cartMaster.getCartId());
            System.out.println("cartProductMasters" + cartProductMasters.size());
            Double amt = cartProductMaster.getRate() * qty;
            cartProductMaster.setQuantity(qty);
            cartProductMaster.setAmount(amt);
            CartProductMaster cm = cartProductDao.save(cartProductMaster);

            for (CartProductMaster cartProductMaster1 : cartMaster.getCartProductMaster()) {
                if (cartProductMaster1.getCartProductId().equals(cartProductMaster.getCartProductId())) {
                    cartProductMaster1.setQuantity(qty);
                    cartProductMaster1.setAmount(amt);
                }
//                System.out.println("amt" + cm.getAmount());
//                cartMaster.setTotalQty(cm.getQuantity());
//                cartMaster.setTotalAmount(cm.getAmount());
//                Double taxAmount = (cm.getAmount() * cartMaster.getTax()) / 100;
//                System.out.println("taxAmount" + taxAmount);
//                cartMaster.setTaxAmount(taxAmount);
//                cartMaster.setEstimateAmount(cm.getAmount() + taxAmount + 5);
                CartMaster cartMaster1 = cartDao.save(cartMaster);
                getCartMaster(cartProductMaster.getUserMaster().getUserId(), cartMaster1.getCartId());
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }


    @Override
    public Boolean IncreaseCartQtyByType(String cartProductId, String type) {
        Boolean flag = false;

        System.out.println(" IncreaseCartQtyByType ");
        System.out.println("cartProductId =="+cartProductId+" type = "+type);

        try {
            switch (type)
            {
                case "cartProduct":
                    CartProductMaster cartProductMaster=new CartProductMaster();
                    cartProductMaster=cartProductDao.findByCartProductId(cartProductId);
                    CartMaster cartMaster = cartDao.findByCartProductMasterCartProductId(cartProductMaster.getCartProductId());
                    Integer qty = cartProductMaster.getQuantity() + 1;
                    List<CartProductMaster> cartProductMasters = cartDao.getCartMasterId(cartMaster.getCartId());
                    System.out.println("cartProductMasters" + cartProductMasters.size());
                    Double amt = cartProductMaster.getRate() * qty;
                    cartProductMaster.setQuantity(qty);
                    cartProductMaster.setAmount(amt);
                    CartProductMaster cm = cartProductDao.save(cartProductMaster);

                    for (CartProductMaster cartProductMaster1 : cartMaster.getCartProductMaster()) {
                        if (cartProductMaster1.getCartProductId().equals(cartProductMaster.getCartProductId())) {
                            cartProductMaster1.setQuantity(qty);
                            cartProductMaster1.setAmount(amt);
                        }
//                System.out.println("amt" + cm.getAmount());
//                cartMaster.setTotalQty(cm.getQuantity());
//                cartMaster.setTotalAmount(cm.getAmount());
//                Double taxAmount = (cm.getAmount() * cartMaster.getTax()) / 100;
//                System.out.println("taxAmount" + taxAmount);
//                cartMaster.setTaxAmount(taxAmount);
//                cartMaster.setEstimateAmount(cm.getAmount() + taxAmount + 5);
                        CartMaster cartMaster1 = cartDao.save(cartMaster);
                        getCartMaster(cartProductMaster.getUserMaster().getUserId(), cartMaster1.getCartId());
                    }
                flag =true;
                    break;

                case "cartArtFrame":
                    CartArtFrameMaster cartArtFrameMaster=new CartArtFrameMaster();
                    cartArtFrameMaster=cartArtFrameDao.findByCartArtFrameId(cartProductId);
                    System.out.println(" cartArtFrameMaster = "+cartArtFrameMaster.toString());
                    CartMaster cartMaster1 = cartDao.findByCartProductMasterCartProductId(cartArtFrameMaster.getCartArtFrameId());
                    Integer qty1 = cartArtFrameMaster.getQuantity() + 1;
                    List<CartArtFrameMaster> cartArtFrameMasters = cartDao.getCartArtFramesMasterId(cartMaster1.getCartId());
                    System.out.println("cartProductMasters" + cartArtFrameMasters.size());
                    Double amt1 = cartArtFrameMaster.getRate() * qty1;
                    cartArtFrameMaster.setQuantity(qty1);
                    cartArtFrameMaster.setAmount(amt1);
                    CartArtFrameMaster cam=cartArtFrameDao.save(cartArtFrameMaster);

                    for (CartArtFrameMaster cartArtFrameMaster1 : cartMaster1.getCartArtFrameMaster()) {
                        if (cartArtFrameMaster1.getCartArtFrameId().equals(cartArtFrameMaster.getCartArtFrameId())) {
                            cartArtFrameMaster1.setQuantity(qty1);
                            cartArtFrameMaster1.setAmount(amt1);
                        }
//                System.out.println("amt" + cm.getAmount());
//                cartMaster.setTotalQty(cm.getQuantity());
//                cartMaster.setTotalAmount(cm.getAmount());
//                Double taxAmount = (cm.getAmount() * cartMaster.getTax()) / 100;
//                System.out.println("taxAmount" + taxAmount);
//                cartMaster.setTaxAmount(taxAmount);
//                cartMaster.setEstimateAmount(cm.getAmount() + taxAmount + 5);
                        CartMaster cartMaster2 = cartDao.save(cartMaster1);
                        getCartMaster(cartArtFrameMaster.getUserMaster().getUserId(), cartMaster1.getCartId());
                    }
                    flag =true;
                    break;

                case "cartAdminArtProduct":
                    CartAdminArtProductMaster cartAdminArtProductMaster=new CartAdminArtProductMaster();
                    cartAdminArtProductMaster=cartAdminArtProductDao.findByCartAdminArtProductId(cartProductId);


                    CartMaster cartMaster2 = cartDao.findByCartAdminArtProductMasterCartAdminArtProductId(cartAdminArtProductMaster.getCartAdminArtProductId());
                    Integer qty2 = cartAdminArtProductMaster.getQuantity() + 1;
                    List<CartAdminArtProductMaster> cartAdminArtProductMasters = cartDao.getCartAdminArtProductMasterId(cartMaster2.getCartId());
                    System.out.println("cartProductMasters" + cartAdminArtProductMasters.size());
                    Double amt2 = cartAdminArtProductMaster.getRate() * qty2;
                    DecimalFormat df=new DecimalFormat("#.00");
                    amt2= Double.valueOf(df.format(amt2));

                    cartAdminArtProductMaster.setQuantity(qty2);
                    cartAdminArtProductMaster.setAmount(amt2);
                    CartAdminArtProductMaster carpm=cartAdminArtProductDao.save(cartAdminArtProductMaster);

                    for (CartAdminArtProductMaster cartAdminArtProductMaster1 : cartMaster2.getCartAdminArtProductMaster()) {
                        if (cartAdminArtProductMaster1.getCartAdminArtProductId().equals(cartAdminArtProductMaster.getCartAdminArtProductId())) {
                            cartAdminArtProductMaster1.setQuantity(qty2);
                            cartAdminArtProductMaster1.setAmount(amt2);
                        }
//                System.out.println("amt" + cm.getAmount());
//                cartMaster.setTotalQty(cm.getQuantity());
//                cartMaster.setTotalAmount(cm.getAmount());
//                Double taxAmount = (cm.getAmount() * cartMaster.getTax()) / 100;
//                System.out.println("taxAmount" + taxAmount);
//                cartMaster.setTaxAmount(taxAmount);
//                cartMaster.setEstimateAmount(cm.getAmount() + taxAmount + 5);
                        CartMaster cartMaster3 = cartDao.save(cartMaster2);
                        getCartMaster(cartAdminArtProductMaster.getUserMaster().getUserId(), cartMaster2.getCartId());
                    }
                    flag =true;
                    break;
                default:
                    flag= false;
                    break;
            }

//            CartProductMaster cartProductMaster = cartProductDao.findByCartProductId(cartProductId);
//            CartMaster cartMaster = cartDao.findByCartProductMasterCartProductId(cartProductMaster.getCartProductId());
//            Integer qty = cartProductMaster.getQuantity() + 1;
//            List<CartProductMaster> cartProductMasters = cartDao.getCartMasterId(cartMaster.getCartId());
//            System.out.println("cartProductMasters" + cartProductMasters.size());
//            Double amt = cartProductMaster.getRate() * qty;
//            cartProductMaster.setQuantity(qty);
//            cartProductMaster.setAmount(amt);
//            CartProductMaster cm = cartProductDao.save(cartProductMaster);
//
//            for (CartProductMaster cartProductMaster1 : cartMaster.getCartProductMaster()) {
//                if (cartProductMaster1.getCartProductId().equals(cartProductMaster.getCartProductId())) {
//                    cartProductMaster1.setQuantity(qty);
//                    cartProductMaster1.setAmount(amt);
//                }
////                System.out.println("amt" + cm.getAmount());
////                cartMaster.setTotalQty(cm.getQuantity());
////                cartMaster.setTotalAmount(cm.getAmount());
////                Double taxAmount = (cm.getAmount() * cartMaster.getTax()) / 100;
////                System.out.println("taxAmount" + taxAmount);
////                cartMaster.setTaxAmount(taxAmount);
////                cartMaster.setEstimateAmount(cm.getAmount() + taxAmount + 5);
//                CartMaster cartMaster1 = cartDao.save(cartMaster);
//                getCartMaster(cartProductMaster.getUserMaster().getUserId(), cartMaster1.getCartId());
//                flag = true;
            return flag;
            } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;

//        return null;
    }

    @Override
    public Boolean DecreaseCartQtyByType(String cartProductId, String type) {
        Boolean flag = false;

        System.out.println(" IncreaseCartQtyByType ");
        System.out.println("cartProductId =="+cartProductId+" type = "+type);

        try {
            switch (type)
            {
                case "cartProduct":
                    CartProductMaster cartProductMaster=new CartProductMaster();
                    cartProductMaster=cartProductDao.findByCartProductId(cartProductId);
                    CartMaster cartMaster = cartDao.findByCartProductMasterCartProductId(cartProductMaster.getCartProductId());
                    Integer qty = cartProductMaster.getQuantity() - 1;
                    List<CartProductMaster> cartProductMasters = cartDao.getCartMasterId(cartMaster.getCartId());
                    System.out.println("cartProductMasters" + cartProductMasters.size());
                    Double amt = cartProductMaster.getRate() * qty;
                    cartProductMaster.setQuantity(qty);
                    cartProductMaster.setAmount(amt);
                    CartProductMaster cm = cartProductDao.save(cartProductMaster);

                    for (CartProductMaster cartProductMaster1 : cartMaster.getCartProductMaster()) {
                        if (cartProductMaster1.getCartProductId().equals(cartProductMaster.getCartProductId())) {
                            cartProductMaster1.setQuantity(qty);
                            cartProductMaster1.setAmount(amt);
                        }
//                System.out.println("amt" + cm.getAmount());
//                cartMaster.setTotalQty(cm.getQuantity());
//                cartMaster.setTotalAmount(cm.getAmount());
//                Double taxAmount = (cm.getAmount() * cartMaster.getTax()) / 100;
//                System.out.println("taxAmount" + taxAmount);
//                cartMaster.setTaxAmount(taxAmount);
//                cartMaster.setEstimateAmount(cm.getAmount() + taxAmount + 5);
                        CartMaster cartMaster1 = cartDao.save(cartMaster);
                        getCartMaster(cartProductMaster.getUserMaster().getUserId(), cartMaster1.getCartId());
                    }
                    flag =true;
                    break;

                case "cartArtFrame":
                    CartArtFrameMaster cartArtFrameMaster=new CartArtFrameMaster();
                    cartArtFrameMaster=cartArtFrameDao.findByCartArtFrameId(cartProductId);
                    System.out.println(" cartArtFrameMaster = "+cartArtFrameMaster.toString());
                    CartMaster cartMaster1 = cartDao.findByCartProductMasterCartProductId(cartArtFrameMaster.getCartArtFrameId());
                    Integer qty1 = cartArtFrameMaster.getQuantity() - 1;
                    List<CartArtFrameMaster> cartArtFrameMasters = cartDao.getCartArtFramesMasterId(cartMaster1.getCartId());
                    System.out.println("cartProductMasters" + cartArtFrameMasters.size());
                    Double amt1 = cartArtFrameMaster.getRate() * qty1;
                    cartArtFrameMaster.setQuantity(qty1);
                    cartArtFrameMaster.setAmount(amt1);
                    CartArtFrameMaster cam=cartArtFrameDao.save(cartArtFrameMaster);

                    for (CartArtFrameMaster cartArtFrameMaster1 : cartMaster1.getCartArtFrameMaster()) {
                        if (cartArtFrameMaster1.getCartArtFrameId().equals(cartArtFrameMaster.getCartArtFrameId())) {
                            cartArtFrameMaster1.setQuantity(qty1);
                            cartArtFrameMaster1.setAmount(amt1);
                        }
//                System.out.println("amt" + cm.getAmount());
//                cartMaster.setTotalQty(cm.getQuantity());
//                cartMaster.setTotalAmount(cm.getAmount());
//                Double taxAmount = (cm.getAmount() * cartMaster.getTax()) / 100;
//                System.out.println("taxAmount" + taxAmount);
//                cartMaster.setTaxAmount(taxAmount);
//                cartMaster.setEstimateAmount(cm.getAmount() + taxAmount + 5);
                        CartMaster cartMaster2 = cartDao.save(cartMaster1);
                        getCartMaster(cartArtFrameMaster.getUserMaster().getUserId(), cartMaster1.getCartId());
                    }
                    flag =true;
                    break;

                case "cartAdminArtProduct":
                    CartAdminArtProductMaster cartAdminArtProductMaster=new CartAdminArtProductMaster();
                    cartAdminArtProductMaster=cartAdminArtProductDao.findByCartAdminArtProductId(cartProductId);


                    CartMaster cartMaster2 = cartDao.findByCartAdminArtProductMasterCartAdminArtProductId(cartAdminArtProductMaster.getCartAdminArtProductId());
                    Integer qty2 = cartAdminArtProductMaster.getQuantity() - 1;
                    List<CartAdminArtProductMaster> cartAdminArtProductMasters = cartDao.getCartAdminArtProductMasterId(cartMaster2.getCartId());
                    System.out.println("cartProductMasters" + cartAdminArtProductMasters.size());
                    Double amt2 = cartAdminArtProductMaster.getRate() * qty2;
                    cartAdminArtProductMaster.setQuantity(qty2);
                    cartAdminArtProductMaster.setAmount(amt2);
                    CartAdminArtProductMaster carpm=cartAdminArtProductDao.save(cartAdminArtProductMaster);

                    for (CartAdminArtProductMaster cartAdminArtProductMaster1 : cartMaster2.getCartAdminArtProductMaster()) {
                        if (cartAdminArtProductMaster1.getCartAdminArtProductId().equals(cartAdminArtProductMaster.getCartAdminArtProductId())) {
                            cartAdminArtProductMaster1.setQuantity(qty2);
                            cartAdminArtProductMaster1.setAmount(amt2);
                        }
//                System.out.println("amt" + cm.getAmount());
//                cartMaster.setTotalQty(cm.getQuantity());
//                cartMaster.setTotalAmount(cm.getAmount());
//                Double taxAmount = (cm.getAmount() * cartMaster.getTax()) / 100;
//                System.out.println("taxAmount" + taxAmount);
//                cartMaster.setTaxAmount(taxAmount);
//                cartMaster.setEstimateAmount(cm.getAmount() + taxAmount + 5);
                        CartMaster cartMaster3 = cartDao.save(cartMaster2);
                        getCartMaster(cartAdminArtProductMaster.getUserMaster().getUserId(), cartMaster2.getCartId());
                    }
                    flag =true;
                    break;
                default:
                    flag= false;
                    break;
            }

//            CartProductMaster cartProductMaster = cartProductDao.findByCartProductId(cartProductId);
//            CartMaster cartMaster = cartDao.findByCartProductMasterCartProductId(cartProductMaster.getCartProductId());
//            Integer qty = cartProductMaster.getQuantity() + 1;
//            List<CartProductMaster> cartProductMasters = cartDao.getCartMasterId(cartMaster.getCartId());
//            System.out.println("cartProductMasters" + cartProductMasters.size());
//            Double amt = cartProductMaster.getRate() * qty;
//            cartProductMaster.setQuantity(qty);
//            cartProductMaster.setAmount(amt);
//            CartProductMaster cm = cartProductDao.save(cartProductMaster);
//
//            for (CartProductMaster cartProductMaster1 : cartMaster.getCartProductMaster()) {
//                if (cartProductMaster1.getCartProductId().equals(cartProductMaster.getCartProductId())) {
//                    cartProductMaster1.setQuantity(qty);
//                    cartProductMaster1.setAmount(amt);
//                }
////                System.out.println("amt" + cm.getAmount());
////                cartMaster.setTotalQty(cm.getQuantity());
////                cartMaster.setTotalAmount(cm.getAmount());
////                Double taxAmount = (cm.getAmount() * cartMaster.getTax()) / 100;
////                System.out.println("taxAmount" + taxAmount);
////                cartMaster.setTaxAmount(taxAmount);
////                cartMaster.setEstimateAmount(cm.getAmount() + taxAmount + 5);
//                CartMaster cartMaster1 = cartDao.save(cartMaster);
//                getCartMaster(cartProductMaster.getUserMaster().getUserId(), cartMaster1.getCartId());
//                flag = true;
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public Boolean updateStatus(CartUpdateReq cartUpdateReq) {
        System.out.println(" cartUpdateReq = "+cartUpdateReq.toString());
        Boolean flag = false;

        CartMaster cartMaster=new CartMaster();
        cartMaster=cartDao.findByUserMaster_UserId(cartUpdateReq.getId());

        System.out.println("  cart master id ="+cartMaster.getCartId());
        try {
            switch (cartUpdateReq.getType()) {
                case "cartProduct":
//                    if (cartUpdateReq.getDeleteId() != null){
//                        for (CartProductMaster cartProductMaster : cartMaster.getCartProductMaster()) {
//                            if(cartProductMaster.getCartProductId().equalsIgnoreCase(cartUpdateReq.getDeleteId()))
//                            {
//                                cartProductMaster.setStatus("Incart");
//                                cartProductMaster.setCartMaster(cartMaster);
//                                cartProductDao.save(cartProductMaster);
//                            }
//                        }
//                        cartDao.save(cartMaster);
//                    }

                    if (cartUpdateReq.getDeleteId() != null) {
                        Query query = new Query(Criteria.where("_id").is(cartMaster.getCartId())
                                .and("cartProductMaster.cartProductId").is(cartUpdateReq.getDeleteId()));
                        Update update = new Update().set("cartProductMaster.$.status", "Incart");
                        // Perform the update directly in MongoDB
                        mongoTemplate.updateFirst(query, update, CartMaster.class);

                        CartProductMaster cartProductMaster=new CartProductMaster();
                        cartProductMaster=cartProductDao.findByCartProductId(cartUpdateReq.getDeleteId());
                        cartProductMaster.setStatus("Incart");
                        cartProductDao.save(cartProductMaster);
                    }

                    flag = true;
                    break;
                case "cartArtFrame":
//                    if (cartUpdateReq.getDeleteId() != null) {
//                        for (CartArtFrameMaster cartArtFrameMaster : cartMaster.getCartArtFrameMaster()) {
//                            if(cartArtFrameMaster.getCartArtFrameId().equalsIgnoreCase(cartUpdateReq.getDeleteId()))
//                            {
//                                cartArtFrameMaster.setStatus("Incart");
//                                cartArtFrameMaster.setCartMaster(cartMaster);
//                                cartArtFrameDao.save(cartArtFrameMaster);
//                            }
//                        }
//                           cartDao.save(cartMaster);
//                    }

                    if (cartUpdateReq.getDeleteId() != null) {
                        Query query = new Query(Criteria.where("_id").is(cartMaster.getCartId())
                                .and("cartArtFrameMaster.cartArtFrameId").is(cartUpdateReq.getDeleteId()));
                        Update update = new Update().set("cartArtFrameMaster.$.status", "Incart");
                        // Perform the update directly in MongoDB
                        mongoTemplate.updateFirst(query, update, CartMaster.class);

                        CartArtFrameMaster cartArtFrameMaster=new CartArtFrameMaster();
                        cartArtFrameMaster=cartArtFrameDao.findByCartArtFrameId(cartUpdateReq.getDeleteId());
                        cartArtFrameMaster.setStatus("Incart");
                        cartArtFrameDao.save(cartArtFrameMaster);
                    }

                    flag = true;
                    break;
                case "cartAdminArtProduct":
                    System.out.println("   cartUpdateReq.getDeleteId() ="+cartUpdateReq.getDeleteId());
//                    if (cartUpdateReq.getDeleteId() != null) {
//                        for (CartAdminArtProductMaster cartAdminArtProductMaster : cartMaster.getCartAdminArtProductMaster()) {
//                            if(cartAdminArtProductMaster.getCartAdminArtProductId().equalsIgnoreCase(cartUpdateReq.getDeleteId()))
//                            {
//                                System.out.println(" cartAdminArtProductMaster id = "+cartAdminArtProductMaster.getCartAdminArtProductId());
//                                cartAdminArtProductMaster.setStatus("Incart");
//                                cartAdminArtProductMaster.setCartMaster(cartMaster);
//                                cartAdminArtProductDao.save(cartAdminArtProductMaster);
//                            }
//                        }
////                        cartMaster.getCartAdminArtProductMaster().removeIf(cartAdminArtProductMaster -> cartAdminArtProductMaster.getCartAdminArtProductId().equalsIgnoreCase(cartUpdateReq.getDeleteId()));
//                          cartDao.save(cartMaster);
//                    }

                    if (cartUpdateReq.getDeleteId() != null) {
                        Query query = new Query(Criteria.where("_id").is(cartMaster.getCartId())
                                .and("cartAdminArtProductMaster.cartAdminArtProductId").is(cartUpdateReq.getDeleteId()));
                        Update update = new Update().set("cartAdminArtProductMaster.$.status", "Incart");
                        // Perform the update directly in MongoDB
                        mongoTemplate.updateFirst(query, update, CartMaster.class);

                        CartAdminArtProductMaster cartAdminArtProductMaster=new CartAdminArtProductMaster();
                        cartAdminArtProductMaster=cartAdminArtProductDao.findByCartAdminArtProductId(cartUpdateReq.getDeleteId());
                        cartAdminArtProductMaster.setStatus("Incart");
                        cartAdminArtProductDao.save(cartAdminArtProductMaster);
                    }

                    flag = true;
                    break;
                default:
                    flag = false;
                    break;
            }
            getCartMaster(cartUpdateReq.getId(), cartMaster.getCartId());
            return flag;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public BuyNowRes BuyNowNew(BuyNowReqq buyNowReqq) {

        OrderMaster orderMaster=new OrderMaster();

//        orderMaster.setOrderUniqueNo();
//        orderMaster.setOrderPaymentStatus();
//        orderMaster.setTotalQty();
//        orderMaster.setOrderDate();
//        orderMaster.setTotalAmount();
//        orderMaster.setTaxAmount();
//        orderMaster.setTax();
//
//        orderMaster.setCoupon();
//        orderMaster.setPromoCode();
//        orderMaster.setGiftCode();
//
//        orderMaster.setEstimatedTotal();
//        orderMaster.setEstimatedShipping();
//        orderMaster.setPromoCodeAmount();
//        orderMaster.setGiftCodeAmount();
//        orderMaster.setCouponDiscount();
//
//        orderMaster.setTaxAmount();
//        orderMaster.setFinalAmount();

        
        return null;
    }

    @Override
    public Map getCartTotalAmount(String userId) {

        long startTime =System.currentTimeMillis();

        Double totalAmount=0.0;
        Integer totalQty=0;

//        List<CartArtFrameMaster> cartArtFrameMasterList=new ArrayList<>();
//        cartArtFrameMasterList=cartArtFrameDao.findByStatusAndUserMaster_UserId("Incart",userId);
//
//        totalAmount=cartArtFrameMasterList.stream().mapToDouble(CartArtFrameMaster::getAmount).sum();
//        totalQty=cartArtFrameMasterList.stream().mapToInt(CartArtFrameMaster::getQuantity).sum();

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userMaster.userId").is(userId).and("status").is("Incart")),
                Aggregation.group().sum("amount").as("totalAmount").sum("quantity").as("totalQuantity")
        );

        AggregationResults<SumResult> result = mongoTemplate.aggregate(aggregation, "cart_art_frame_master", SumResult.class);
        SumResult sumResult = result.getUniqueMappedResult();

        System.out.println("  Result ="+sumResult.toString());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("totalAmount", sumResult != null ? sumResult.getTotalAmount() : 0.0);
        resultMap.put("totalQuantity", sumResult != null ? sumResult.getTotalQuantity() : 0);

        System.out.println("  Total Amount ="+totalAmount+"  Total Quantity ="+totalQty);

        long endtime=System.currentTimeMillis();
        long dtime=endtime-startTime;
        System.out.println(" dtime ="+dtime);
//        return totalAmount;

        return resultMap;

    }




    @Override
    public Boolean tempDelete(CartDeleteReq cartDeleteReq) {
        System.out.println(" cartDeleteReq = "+cartDeleteReq.toString());
        Boolean flag = false;

        CartMaster cartMaster=new CartMaster();
        cartMaster=cartDao.findByUserMaster_UserId(cartDeleteReq.getId());

        try {
            switch (cartDeleteReq.getType()) {
                case "cartProduct":
//                    if (cartDeleteReq.getDeleteId() != null){
//                        cartMaster.getCartProductMaster().parallelStream().forEach(cartProductMaster ->{
//                            if(cartProductMaster.getCartProductId().equalsIgnoreCase(cartDeleteReq.getDeleteId()) && cartProductMaster!=null)
//                            {
//                                cartProductMaster.setStatus("Delete");
//                                cartProductMaster.setCartMaster(cartProductMaster.getCartMaster());
//                                cartProductDao.save(cartProductMaster);
//                            }
//                        });
//                        cartDao.save(cartMaster);
//                    }

                    if (cartDeleteReq.getDeleteId() != null) {
                        Query query = new Query(Criteria.where("_id").is(cartMaster.getCartId())
                                .and("cartProductMaster.cartProductId").is(cartDeleteReq.getDeleteId()));
                        Update update = new Update().set("cartProductMaster.$.status", "Delete");
                        // Perform the update directly in MongoDB
                        mongoTemplate.updateFirst(query, update, CartMaster.class);

                        CartProductMaster cartProductMaster=new CartProductMaster();
                        cartProductMaster=cartProductDao.findByCartProductId(cartDeleteReq.getDeleteId());
                        cartProductMaster.setStatus("Delete");
                        cartProductDao.save(cartProductMaster);
                    }

                    flag = true;
                    break;
                case "cartArtFrame":
//                    if (cartDeleteReq.getDeleteId() != null) {
//                        cartMaster.getCartArtFrameMaster().parallelStream().forEach(cartArtFrameMaster ->{
//                            if(cartArtFrameMaster.getCartArtFrameId().equalsIgnoreCase(cartDeleteReq.getDeleteId()) && cartArtFrameMaster!=null)
//                            {
//                                cartArtFrameMaster.setStatus("Delete");
//                                cartArtFrameMaster.setCartMaster(cartArtFrameMaster.getCartMaster());
//                                cartArtFrameDao.save(cartArtFrameMaster);
//                            }
//                        });
//                        cartDao.save(cartMaster);
//                    }

                    if (cartDeleteReq.getDeleteId() != null) {
                        Query query = new Query(Criteria.where("_id").is(cartMaster.getCartId())
                                .and("cartArtFrameMaster.cartArtFrameId").is(cartDeleteReq.getDeleteId()));
                        Update update = new Update().set("cartArtFrameMaster.$.status", "Delete");
                        // Perform the update directly in MongoDB
                        mongoTemplate.updateFirst(query, update, CartMaster.class);

                        CartArtFrameMaster cartArtFrameMaster=new CartArtFrameMaster();
                        cartArtFrameMaster=cartArtFrameDao.findByCartArtFrameId(cartDeleteReq.getDeleteId());
                        cartArtFrameMaster.setStatus("Delete");
                        cartArtFrameDao.save(cartArtFrameMaster);
                    }

                    flag = true;
                    break;
                case "cartAdminArtProduct":
//                    if (cartDeleteReq.getDeleteId() != null) {
//                                         cartMaster.getCartAdminArtProductMaster().parallelStream().forEach(cartAdminArtProductMaster -> {
//                                             if(cartAdminArtProductMaster.getCartAdminArtProductId().equalsIgnoreCase(cartDeleteReq.getDeleteId()))
//                                             {
//                                                 cartAdminArtProductMaster.setStatus("Delete");
//                                                 cartAdminArtProductMaster.setCartMaster(cartAdminArtProductMaster.getCartMaster());
//                                                 cartAdminArtProductDao.save(cartAdminArtProductMaster);
//                                             }
//                        });
//                        cartDao.save(cartMaster);
//                    }
                    /////////////////////////////////////////////////////////
                    if (cartDeleteReq.getDeleteId() != null) {
                        Query query = new Query(Criteria.where("_id").is(cartMaster.getCartId())
                                .and("cartAdminArtProductMaster.cartAdminArtProductId").is(cartDeleteReq.getDeleteId()));
                        Update update = new Update().set("cartAdminArtProductMaster.$.status", "Delete");
                        // Perform the update directly in MongoDB
                        mongoTemplate.updateFirst(query, update, CartMaster.class);

                        CartAdminArtProductMaster cartAdminArtProductMaster=new CartAdminArtProductMaster();
                        cartAdminArtProductMaster=cartAdminArtProductDao.findByCartAdminArtProductId(cartDeleteReq.getDeleteId());
                        cartAdminArtProductMaster.setStatus("Delete");
                        cartAdminArtProductDao.save(cartAdminArtProductMaster);

                    }
                    flag = true;
                    break;
                default:
                    flag = false;
                    break;
            }
            getCartMaster(cartDeleteReq.getId(), cartMaster.getCartId());
            return flag;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public CartMasterRes getUserIdWisetempDeleteCartDetails(String userId) {

        CartMasterRes cartMasterRes=new CartMasterRes();
        CartMaster cartMaster=new CartMaster();
        cartMaster=cartDao.findByUserMaster_UserId(userId);
        BeanUtils.copyProperties(cartMaster,cartMasterRes);
        List list=new ArrayList();

        System.out.println("  cart product");
        if(cartMaster.getCartProductMaster()!=null) {
            List cartProductLi=new ArrayList();
            cartMaster.getCartProductMaster().forEach(cartProductMaster -> System.out.println(cartProductMaster.getStatus()));
            cartProductLi=cartMaster.getCartProductMaster().stream().parallel().filter(cartProductMaster -> cartProductMaster.getStatus().equalsIgnoreCase("Delete")).collect(Collectors.toList());
//            List<CartProductMaster> cartProductMasterList=cartProductDao.findAllByCartMaster_CartIdAndStatus(cartMaster.getCartId(),"Delete");
            list.addAll(cartProductLi);
        }
        System.out.println("  cart ArtFrames");
        if(cartMaster.getCartArtFrameMaster()!=null) {
            List cartArtFrameMasterLi=new ArrayList();
            cartMaster.getCartArtFrameMaster().forEach(cartArtFrameMaster -> System.out.println(cartArtFrameMaster.getStatus()));
            cartArtFrameMasterLi=cartMaster.getCartArtFrameMaster().stream().parallel().filter(cartArtFrameMaster -> cartArtFrameMaster.getStatus().equalsIgnoreCase("Delete")).collect(Collectors.toList());
            System.out.println("  cartArtFrameMasterLi ="+cartArtFrameMasterLi.toString() );
//            List<CartArtFrameMaster> cartArtFrameMasters=cartArtFrameDao.findAllByCartMaster_CartIdAndStatus(cartMaster.getCartId(),"Delete");
            list.addAll(cartArtFrameMasterLi);
//            list.addAll(cartArtFrameMasterLi);
        }
        System.out.println("  cartAdmin Art Products");
        if(cartMaster.getCartAdminArtProductMaster()!=null)
        {
            List cartAdminArtProductLi=new ArrayList();
            cartMaster.getCartAdminArtProductMaster().forEach(cartAdminArtProductMaster -> System.out.println(cartAdminArtProductMaster.getStatus()));
            cartAdminArtProductLi=cartMaster.getCartAdminArtProductMaster().stream().parallel().filter(cartAdminArtProductMaster -> cartAdminArtProductMaster.getStatus().equalsIgnoreCase("Delete")).collect(Collectors.toList());
//            List<CartAdminArtProductMaster> cartAdminArtProductMasterList=cartAdminArtProductDao.findAllByCartMaster_CartIdAndStatus(cartMaster.getCartId(),"Delete");
            list.addAll(cartAdminArtProductLi);
        }
        cartMasterRes.setList(list);
        return cartMasterRes;
    }



    @Override
    public Boolean DecreaseCartQty(String cartProductId) {
        Boolean flag = false;
        try {
            CartProductMaster cartProductMaster = cartProductDao.findByCartProductId(cartProductId);
            CartMaster cartMaster = cartDao.findByCartProductMasterCartProductId(cartProductMaster.getCartProductId());
            Integer qty = cartProductMaster.getQuantity() - 1;
            List<CartProductMaster> cartProductMasters = cartDao.getCartMasterId(cartMaster.getCartId());
            System.out.println("cartProductMasters" + cartProductMasters.size());
            Double amt = cartProductMaster.getRate() * qty;
            cartProductMaster.setQuantity(qty);
            cartProductMaster.setAmount(amt);
            CartProductMaster cm = cartProductDao.save(cartProductMaster);

            for (CartProductMaster cartProductMaster1 : cartMaster.getCartProductMaster()) {
                if (cartProductMaster1.getCartProductId().equals(cartProductMaster.getCartProductId())) {
                    cartProductMaster1.setQuantity(qty);
                    cartProductMaster1.setAmount(amt);
                }
                CartMaster cartMaster1 = cartDao.save(cartMaster);
                getCartMaster(cartProductMaster.getUserMaster().getUserId(), cartMaster1.getCartId());
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public List<CartMaster> getAll() {
        return cartDao.findAll();
    }

    @Override
    public Boolean deleteCart(String cartProductId) {
        Boolean flag = false;
        CartProductMaster cartProductMaster = cartProductDao.findByCartProductId(cartProductId);
        CartMaster cm = cartDao.findByUserMaster_UserId(cartProductMaster.getUserMaster().getUserId());
        try {
            List<CartProductMaster> cartProductMasters = cm.getCartProductMaster();
            cartProductMasters.removeIf(item -> item.getCartProductId().equals(cartProductId));
            cm.setCartProductMaster(cartProductMasters);
            CartMaster cmm = cartDao.save(cm);
            System.out.println("ccc" + cmm.getCartProductMaster().size());
            cartProductDao.deleteById(cartProductId);
            getCartMaster(cartProductMaster.getUserMaster().getUserId(), cm.getCartId());
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public Message updateCartShippingMethod(UpdateCartShippingMethodReq updateCartShippingMethodReq) {
        Message message = new Message();
        CartMaster cartMaster = cartDao.getCart(updateCartShippingMethodReq.getCartId());
        double amount = cartMaster.getFinalAmount() - cartMaster.getShippingMethod().getShippingMethodPrice();
        cartMaster.setFinalAmount(amount);
        CartMaster c = cartDao.save(cartMaster);
        try {
            c.setShippingMethod(updateCartShippingMethodReq.getShippingMethod());
            CartMaster cartMaster1 = cartDao.save(c);
            double price = cartMaster1.getShippingMethod().getShippingMethodPrice();
            cartMaster1.setEstimateShipping(price);
            double amt = cartMaster1.getFinalAmount() + price;
            cartMaster1.setFinalAmount(amt);
            cartDao.save(cartMaster1);
            message.setFlag(true);
            message.setMessage("Shipping method update successfully");
        } catch (Exception e) {
            e.printStackTrace();
            message.setFlag(false);
            message.setMessage("Shipping method does not update");
        }
        return message;
    }

    @Override
    public CartMasterResponseDto get(String userId) {
        CartMasterResponseDto cartMasterResponseDto = new CartMasterResponseDto();
        CartProductAndArtFrameRes cartProductAndArtFrameRes = new CartProductAndArtFrameRes();
        CartMaster cartMaster = cartDao.findByUserMaster_UserId(userId);
        List temp = new ArrayList();
        List<CartProductAndArtFrameRes> frameRes = new ArrayList<>();
        if (cartMaster.getCartProductMaster() != null) {
            System.out.println("ok");
            for (CartProductMaster cartProductMaster : cartMaster.getCartProductMaster()) {
                System.out.println("ok...");
                cartProductAndArtFrameRes.setCartProductId(cartProductMaster.getCartProductId());
                cartProductAndArtFrameRes.setCartId(cartMaster.getCartId());
                cartProductAndArtFrameRes.setProductName(cartProductMaster.getProductName());
                cartProductAndArtFrameRes.setDiscount(cartProductMaster.getDiscount());
                cartProductAndArtFrameRes.setSize(cartProductMaster.getSize());
                cartProductAndArtFrameRes.setCartProductNo(cartProductMaster.getCartProductNo());
                cartProductAndArtFrameRes.setQuantity(cartProductMaster.getQuantity());
                cartProductAndArtFrameRes.setRate(cartProductMaster.getRate());

                DecimalFormat df=new DecimalFormat("#.00");
                Double amount = Double.valueOf(df.format(cartProductMaster.getAmount()));

                cartProductAndArtFrameRes.setAmount(amount);
//                cartProductAndArtFrameRes.setAmount(cartProductMaster.getAmount());
                cartProductAndArtFrameRes.setStockStatus(cartProductMaster.getStockStatus());
                cartProductAndArtFrameRes.setDescription(cartProductMaster.getDescription());
                cartProductAndArtFrameRes.setStatus(cartProductMaster.getStatus());
                cartProductAndArtFrameRes.setCartProductUniqueNo(cartProductMaster.getCartProductUniqueNo());
                cartProductAndArtFrameRes.setUpdatedDate(cartProductMaster.getUpdatedDate());
                cartProductAndArtFrameRes.setUserId(cartProductMaster.getUserMaster().getUserId());
                cartProductAndArtFrameRes.setArtProductId(cartProductMaster.getArtProductMaster().getArtProductId());
                temp.add(cartProductAndArtFrameRes);
                System.out.println("aaa" + temp.toString());
                cartMasterResponseDto.setList(temp);
            }
            cartMasterResponseDto.setList(temp);
        }
        if (cartMaster.getCartArtFrameMaster() != null) {
            for (CartArtFrameMaster cartArtFrameMaster : cartMaster.getCartArtFrameMaster()) {
                cartProductAndArtFrameRes.setCartArtFrameId(cartArtFrameMaster.getCartArtFrameId());
                cartProductAndArtFrameRes.setArtFrameName(cartArtFrameMaster.getArtMaster().getArtName());
                cartProductAndArtFrameRes.setDate(cartArtFrameMaster.getDate());
                cartProductAndArtFrameRes.setCartArtFrameUniqueNo(cartArtFrameMaster.getCartArtFrameUniqueNo());
                cartProductAndArtFrameRes.setNewOrderUniqueNo(cartArtFrameMaster.getNewOrderUniqueNo());
                cartProductAndArtFrameRes.setExpectedDeliveryDate(cartArtFrameMaster.getExpectedDeliveryDate());

                DecimalFormat df=new DecimalFormat("#.00");
                Double totalAmount =0.0;
                totalAmount=Double.valueOf(df.format(cartArtFrameMaster.getTotalAmount()));

                cartProductAndArtFrameRes.setTotalAmount(totalAmount);
//                cartProductAndArtFrameRes.setTotalAmount(cartArtFrameMaster.getTotalAmount());
                cartProductAndArtFrameRes.setFrameMaster(cartArtFrameMaster.getFrameMaster());
                cartProductAndArtFrameRes.setMatMasterTop(cartArtFrameMaster.getMatMasterTop());
                cartProductAndArtFrameRes.setMatMasterBottom(cartArtFrameMaster.getMatMasterBottom());
                cartProductAndArtFrameRes.setImgUrl(cartArtFrameMaster.getImgUrl());
                cartProductAndArtFrameRes.setPrintingMaterialMaster(cartArtFrameMaster.getPrintingMaterialMaster());
                cartProductAndArtFrameRes.setOrientationMaster(cartArtFrameMaster.getOrientationMaster());
                cartProductAndArtFrameRes.setArtId(cartArtFrameMaster.getArtMaster().getArtId());
                temp.add(cartProductAndArtFrameRes);
                System.out.println("aaa" + temp.toString());
                cartMasterResponseDto.setList(temp);
            }
            cartMasterResponseDto.setList(temp);
        }
        cartMasterResponseDto.setCartId(cartMaster.getCartId());
        cartMasterResponseDto.setCartDate(cartMaster.getCartDate());
        cartMasterResponseDto.setTotalQty(cartMaster.getTotalQty());

        DecimalFormat df=new DecimalFormat("#.00");
        Double totalamt= Double.valueOf(df.format(cartMaster.getTotalAmount()));
//        cartMasterResponseDto.setTotalAmount(cartMaster.getTotalAmount());
        cartMasterResponseDto.setTotalAmount(totalamt);
        cartMasterResponseDto.setTax(cartMaster.getTax());
        cartMasterResponseDto.setTaxAmount(cartMaster.getTaxAmount());
        cartMasterResponseDto.setEstimateAmount(cartMaster.getEstimateAmount());
        cartMasterResponseDto.setEstimateShipping(cartMaster.getEstimateShipping());
        cartMasterResponseDto.setDescription(cartMaster.getDescription());
        cartMasterResponseDto.setStatus(cartMaster.getStatus());
        cartMasterResponseDto.setPromoCode(cartMaster.getPromoCode());
        cartMasterResponseDto.setGiftCode(cartMaster.getGiftCode());
        cartMasterResponseDto.setGiftCodeAmount(cartMaster.getGiftCodeAmount());
        cartMasterResponseDto.setPromoCodeAmount(cartMaster.getPromoCodeAmount());
        cartMasterResponseDto.setTotalCount(cartMaster.getTotalCount());

        Double finalamt= Double.valueOf(df.format(cartMaster.getFinalAmount()));

//        cartMasterResponseDto.setFinalAmount(cartMaster.getFinalAmount());
        cartMasterResponseDto.setFinalAmount(finalamt);

        cartMasterResponseDto.setCodeType(cartMaster.getCodeType());
        cartMasterResponseDto.setStyle(cartMaster.getStyle());
        cartMasterResponseDto.setShippingMethod(cartMaster.getShippingMethod());
        cartMasterResponseDto.setUserId(cartMaster.getUserMaster().getUserId());

        return cartMasterResponseDto;
    }

    @Override
    public BuyNowRes BuyNow(BuyNowReq buyNowReq) {
        BuyNowRes buyNowRes=new BuyNowRes();
        List list123=new ArrayList();

        CartMaster cartMaster=new CartMaster();
        cartMaster=cartDao.getCart(buyNowReq.getCartId());

        buyNowRes.setCartId(cartMaster.getCartId());
        buyNowRes.setCartDate(new Date());
        buyNowRes.setDescription(cartMaster.getDescription());
        buyNowRes.setTotalQty(buyNowReq.getQty());
        buyNowRes.setPromoCode(cartMaster.getPromoCode());
        buyNowRes.setGiftCode(cartMaster.getGiftCode());

        UserMaster userMaster=new UserMaster();
        userMaster=userDao.getId(buyNowReq.getUserId());

        if(buyNowReq.getCartArtFrameId()!=null)
        {
            CartArtFrameMaster cartArtFrameMaster=new CartArtFrameMaster();
            Optional<CartArtFrameMaster> cartArtFrameMaster1 = cartMaster.getCartArtFrameMaster().stream()
                    .filter(obj -> obj.getCartArtFrameId().equals(buyNowReq.getCartArtFrameId()))
                    .findFirst();

            BeanUtils.copyProperties(cartArtFrameMaster1.get(),cartArtFrameMaster);

//            DecimalFormat decimalFormat = new DecimalFormat("0.00");
//            double totalAmount = Double.parseDouble(decimalFormat.format(cartArtFrameMaster.getTotalAmount()));
//            double taxAmount = Double.parseDouble(decimalFormat.format(cartMaster.getTaxAmount()));
//            double estimateShipping = Double.parseDouble(decimalFormat.format(cartMaster.getShippingMethod().getShippingMethodPrice()));
//            double estimateAmount = Double.parseDouble(decimalFormat.format(totalAmount + taxAmount));
//            double finalAmount = estimateAmount;



            List<CartArtFrameMaster> cartArtFrameMasters=new ArrayList<>();
            buyNowRes.setTotalAmount(cartArtFrameMaster.getAmount());
            cartArtFrameMasters.add(cartArtFrameMaster1.get());


//            buyNowRes.setCartArtFrameMaster(cartArtFrameMasters);
//            list123.addAll(cartArtFrameMasters);
            buyNowRes.setTax(12.0);
            System.out.println(" cartArtFrameMaster.getTotalAmount() == "+cartArtFrameMaster1.toString());
            buyNowRes.setTaxAmount(cartArtFrameMaster.getAmount()*12.0/100);
            buyNowRes.setFinalAmount(cartArtFrameMaster.getAmount());
            buyNowRes.setEstimateShipping(cartMaster.getEstimateShipping());
            buyNowRes.setEstimateAmount(buyNowRes.getTotalAmount()+buyNowRes.getTaxAmount());
            System.out.println("  cartArtFrameMaster ="+cartArtFrameMaster.toString());
            buyNowRes.setPromoCodeAmount(null);
            buyNowRes.setGiftCodeAmount(null);
            buyNowRes.setTotalCount(null);
            buyNowRes.setCodeType(cartMaster.getCodeType());
            buyNowRes.setStyle(cartMaster.getStyle());
            buyNowRes.setShippingMethod(cartMaster.getShippingMethod());
            buyNowRes.setUserMaster(userMaster);

            List<CartArtFrameMaster> cartArtFrameMasterList=new ArrayList<>();
            cartArtFrameMasterList.add(cartArtFrameMaster);
            list123.addAll(cartArtFrameMasterList);
//            buyNowRes.setCartArtFrameMaster(cartArtFrameMasterList);

        }else if(buyNowReq.getCartProductId()!=null)
        {
            CartProductMaster cartProductMaster=new CartProductMaster();
            Optional<CartProductMaster> cartProductMaster1 = cartMaster.getCartProductMaster().stream()
                    .filter(obj -> obj.getCartProductId().equals(buyNowReq.getCartProductId()))
                    .findFirst();

            BeanUtils.copyProperties(cartProductMaster1.get(),cartProductMaster);
            System.out.println("  cartProductMaster ="+cartProductMaster.toString());

//            DecimalFormat decimalFormat = new DecimalFormat("0.00");
//            double totalAmount = Double.parseDouble(decimalFormat.format(cartProductMaster.getAmount()));
//            double taxAmount = Double.parseDouble(decimalFormat.format(buyNowRes.getTaxAmount()));
//            double estimateShipping = Double.parseDouble(decimalFormat.format(cartMaster.getShippingMethod().getShippingMethodPrice()));
//            double estimateAmount = Double.parseDouble(decimalFormat.format(totalAmount + taxAmount));
//            double finalAmount = estimateAmount;


            buyNowRes.setTotalAmount(cartProductMaster.getAmount());
            list123.add(cartProductMaster);
//            buyNowRes.setCartProductMaster(cartProductMaster);
            buyNowRes.setTax(12.0);
            buyNowRes.setTaxAmount(cartProductMaster.getAmount()*12.0/100);
            buyNowRes.setFinalAmount(cartProductMaster.getAmount());
            buyNowRes.setEstimateShipping(cartMaster.getEstimateShipping());
            buyNowRes.setEstimateAmount(buyNowRes.getTotalAmount()+buyNowRes.getTaxAmount());
            System.out.println("  cartArtFrameMaster ="+cartProductMaster.toString());
            buyNowRes.setPromoCodeAmount(null);
            buyNowRes.setGiftCodeAmount(null);
            buyNowRes.setTotalCount(null);
            buyNowRes.setCodeType(cartMaster.getCodeType());
            buyNowRes.setStyle(cartMaster.getStyle());
            buyNowRes.setShippingMethod(cartMaster.getShippingMethod());
            buyNowRes.setUserMaster(userMaster);
//            buyNowRes.setCartArtFrameMaster(cartProductMaster);

        }else if(buyNowReq.getCartAdminArtProductId()!=null)
        {
            CartAdminArtProductMaster cartAdminArtProductMaster=new CartAdminArtProductMaster();
            Optional<CartAdminArtProductMaster> cartAdminArtProductMaster1=cartMaster.getCartAdminArtProductMaster().stream().filter(cartAdminArtProductMaster2 -> cartAdminArtProductMaster2.getCartAdminArtProductId().equalsIgnoreCase(buyNowReq.getCartAdminArtProductId())).findFirst();
            BeanUtils.copyProperties(cartAdminArtProductMaster1.get(),cartAdminArtProductMaster);
            System.out.println("  cartAdminArtProductMaster ="+cartAdminArtProductMaster.toString());

//            CartProductMaster cartProductMaster=new CartProductMaster();
//            Optional<CartProductMaster> cartProductMaster1 = cartMaster.getCartProductMaster().stream()
//                    .filter(obj -> obj.getCartProductId().equals(buyNowReq.getCartProductId()))
//                    .findFirst();

//            BeanUtils.copyProperties(cartProductMaster1.get(),cartProductMaster);
//            System.out.println("  cartProductMaster ="+cartProductMaster.toString());

//            DecimalFormat decimalFormat = new DecimalFormat("0.00");
//            double totalAmount = Double.parseDouble(decimalFormat.format(cartProductMaster.getAmount()));
//            double taxAmount = Double.parseDouble(decimalFormat.format(buyNowRes.getTaxAmount()));
//            double estimateShipping = Double.parseDouble(decimalFormat.format(cartMaster.getShippingMethod().getShippingMethodPrice()));
//            double estimateAmount = Double.parseDouble(decimalFormat.format(totalAmount + taxAmount));
//            double finalAmount = estimateAmount;


            buyNowRes.setTotalAmount(cartAdminArtProductMaster.getAmount());
            list123.add(cartAdminArtProductMaster);
//            buyNowRes.setCartAdminArtProductMaster(cartAdminArtProductMaster);
            buyNowRes.setTax(12.0);
            buyNowRes.setTaxAmount(cartAdminArtProductMaster.getAmount()*12.0/100);
            buyNowRes.setFinalAmount(cartAdminArtProductMaster.getAmount());
            buyNowRes.setEstimateShipping(cartMaster.getEstimateShipping());
            buyNowRes.setEstimateAmount(buyNowRes.getTotalAmount()+buyNowRes.getTaxAmount());
            System.out.println("  cartArtFrameMaster ="+cartAdminArtProductMaster.toString());
            buyNowRes.setPromoCodeAmount(null);
            buyNowRes.setGiftCodeAmount(null);
            buyNowRes.setTotalCount(null);
            buyNowRes.setCodeType(cartMaster.getCodeType());
            buyNowRes.setStyle(cartMaster.getStyle());
            buyNowRes.setShippingMethod(cartMaster.getShippingMethod());
            buyNowRes.setUserMaster(userMaster);
//            buyNowRes.setCartArtFrameMaster(cartProductMaster);

        }else
        {

        }

        buyNowRes.setList(list123);
        return buyNowRes;
    }

    @Override
    public CartMasterRes getUserIdWiseCartData1(String userId) {
        CartMasterRes cartMasterRes=new CartMasterRes();
        CartMaster cartMaster=new CartMaster();
        cartMaster=cartDao.findByUserMaster_UserId(userId);
        BeanUtils.copyProperties(cartMaster,cartMasterRes);
        List list=new ArrayList();

//        List tempDeleteList=new ArrayList();

        List<String> cartProductMasterList=new ArrayList<>();
        List<String> cartArtFrameMasterList=new ArrayList<>();
        List<String> cartAdminArtProductMasterList=new ArrayList<>();


        if(cartMaster.getCartProductMaster()!=null) {
         cartProductMasterList=cartMaster.getCartProductMaster().parallelStream().map(CartProductMaster::getCartProductId).collect(Collectors.toList());
         list.addAll(cartMaster.getCartProductMaster().parallelStream().filter(cartProductMaster -> cartProductMaster.getStatus().equalsIgnoreCase("Incart")).collect(Collectors.toList()));
        }
        if(cartMaster.getCartArtFrameMaster()!=null) {
           cartArtFrameMasterList=cartMaster.getCartArtFrameMaster().parallelStream().map(CartArtFrameMaster::getCartArtFrameId).collect(Collectors.toList());
           list.addAll(cartMaster.getCartArtFrameMaster().parallelStream().filter(cartArtFrameMaster -> cartArtFrameMaster.getStatus().equalsIgnoreCase("Incart")).collect(Collectors.toList()));
        }
        if(cartMaster.getCartAdminArtProductMaster()!=null)
        {
            cartAdminArtProductMasterList=cartMaster.getCartAdminArtProductMaster().parallelStream().map(CartAdminArtProductMaster::getCartAdminArtProductId).collect(Collectors.toList());
            list.addAll(cartMaster.getCartAdminArtProductMaster().parallelStream().filter(cartAdminArtProductMaster -> cartAdminArtProductMaster.getStatus().equalsIgnoreCase("Incart")).collect(Collectors.toList()));
        }

        cartMasterRes.setCartProductMaster(cartProductMasterList);
        cartMasterRes.setCartArtFrameMaster(cartArtFrameMasterList);
        cartMasterRes.setCartAdminArtProductMaster(cartAdminArtProductMasterList);
        cartMasterRes.setList(list);

//        if(cartMaster.getCartProductMaster()!=null) {
//            tempDeleteList.addAll(cartMaster.getCartProductMaster().stream().filter(cartProductMaster -> cartProductMaster.getStatus().equalsIgnoreCase("Delete")).collect(Collectors.toList()));
//        }
//        if(cartMaster.getCartArtFrameMaster()!=null) {
//            tempDeleteList.addAll(cartMaster.getCartArtFrameMaster().stream().filter(cartArtFrameMaster -> cartArtFrameMaster.getStatus().equalsIgnoreCase("Delete")).collect(Collectors.toList()));
//        }
//        if(cartMaster.getCartAdminArtProductMaster()!=null)
//        {
//            tempDeleteList.addAll(cartMaster.getCartAdminArtProductMaster().stream().filter(cartAdminArtProductMaster -> cartAdminArtProductMaster.getStatus().equalsIgnoreCase("Delete")).collect(Collectors.toList()));
//        }
//        cartMasterRes.setTempDeleteList(tempDeleteList);

        return cartMasterRes;
    }

    @Override
    public Boolean delete(CartDeleteReq cartDeleteReq) {

        Boolean flag = false;

        CartMaster cartMaster=new CartMaster();
        cartMaster=cartDao.findByUserMaster_UserId(cartDeleteReq.getId());

        try {
            switch (cartDeleteReq.getType()) {
                case "cartProduct":
                    if (cartDeleteReq.getDeleteId() != null){
                        cartMaster.getCartProductMaster().removeIf(cartProductMaster -> cartProductMaster.getCartProductId().equalsIgnoreCase(cartDeleteReq.getDeleteId()));
                        cartProductDao.deleteById(cartDeleteReq.getDeleteId());
//                        cartDao.save(cartMaster);
                        if(cartMaster.getCartProductMaster().isEmpty()&&cartMaster.getCartArtFrameMaster().isEmpty()&& cartMaster.getCartAdminArtProductMaster().isEmpty())
                        {
                            cartMaster.setTotalAmount(0.0);
                            cartMaster.setTotalQty(0);
                            cartMaster.setTotalCount(0);
                            cartMaster.setEstimateShipping(0.0);
                            cartMaster.setTaxAmount(0.0);
                            cartMaster.setEstimateAmount(0.0);
                            cartMaster.setFinalAmount(0.0);
                            cartDao.save(cartMaster);
                        }else
                        {
                            cartDao.save(cartMaster);
                        }
                    }
                    flag = true;
                    break;
                case "cartArtFrame":
                    if (cartDeleteReq.getDeleteId() != null) {
                        cartMaster.getCartArtFrameMaster().removeIf(cartArtFrameMaster -> cartArtFrameMaster.getCartArtFrameId().equalsIgnoreCase(cartDeleteReq.getDeleteId()));
                        cartArtFrameDao.deleteById(cartDeleteReq.getDeleteId());
//                        cartDao.save(cartMaster);
                        if(cartMaster.getCartProductMaster().isEmpty()&&cartMaster.getCartArtFrameMaster().isEmpty()&& cartMaster.getCartAdminArtProductMaster().isEmpty())
                        {
                            cartMaster.setTotalAmount(0.0);
                            cartMaster.setTotalQty(0);
                            cartMaster.setTotalCount(0);
                            cartMaster.setEstimateShipping(0.0);
                            cartMaster.setTaxAmount(0.0);
                            cartMaster.setEstimateAmount(0.0);
                            cartMaster.setFinalAmount(0.0);
                            cartDao.save(cartMaster);
                        }else
                        {
                            cartDao.save(cartMaster);
                        }
                    }
                    flag = true;
                    break;
                case "cartAdminArtProduct":
                    if (cartDeleteReq.getDeleteId() != null) {
                        cartMaster.getCartAdminArtProductMaster().removeIf(cartAdminArtProductMaster -> cartAdminArtProductMaster.getCartAdminArtProductId().equalsIgnoreCase(cartDeleteReq.getDeleteId()));
                        cartAdminArtProductDao.deleteById(cartDeleteReq.getDeleteId());
//                        cartDao.save(cartMaster);
                        if(cartMaster.getCartProductMaster().isEmpty()&&cartMaster.getCartArtFrameMaster().isEmpty()&& cartMaster.getCartAdminArtProductMaster().isEmpty())
                        {
                            cartMaster.setTotalAmount(0.0);
                            cartMaster.setTotalQty(0);
                            cartMaster.setTotalCount(0);
                            cartMaster.setEstimateShipping(0.0);
                            cartMaster.setTaxAmount(0.0);
                            cartMaster.setEstimateAmount(0.0);
                            cartMaster.setFinalAmount(0.0);
                            cartDao.save(cartMaster);
                        }else
                        {
                            cartDao.save(cartMaster);
                        }
                    }
                    flag = true;
                    break;
                default:
                    flag = false;
                    break;
            }
            return flag;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }




}

