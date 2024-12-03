package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.configuration.UniqueNumber;
import com.zplus.ArtnStockMongoDB.dao.*;
import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.OrderCartProductResDto;
import com.zplus.ArtnStockMongoDB.dto.res.OrderResDto;
import com.zplus.ArtnStockMongoDB.dto.res.UpdateOrderStatusRes;
import com.zplus.ArtnStockMongoDB.dto.res.UserOrderRes;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.OrderMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterDao orderMasterDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private CartArtFrameDao cartArtFrameDao;
    @Autowired
    private UserPromoCodeDao userPromoCodeDao;
    @Autowired
    private UserGiftCodeDao userGiftCodeDao;
    private final MongoOperations mongoOperations;
    @Autowired
    private CartProductDao cartProductDao;

    @Autowired
    private CartAdminArtProductDao cartAdminArtProductDao;

    @Autowired
    private UserDao userDao;


    @Autowired
    private MongoTemplate mongoTemplate;


    public OrderMasterServiceImpl(OrderMasterDao orderMasterDao, MongoOperations mongoOperations) {
        this.orderMasterDao = orderMasterDao;
        this.mongoOperations = mongoOperations;
    }

    //1
    @Override
    public Boolean createOrderMaster(OrderMasterRequest orderMasterRequest) {
        System.out.println("  OrderMasterRequest ="+orderMasterRequest.toString());

        Boolean flag = false;
        OrderMaster orderMaster = new OrderMaster();
        OrderMaster orderMaster1 = new OrderMaster();
        UserMaster userMaster = new UserMaster();
        List<CartArtFrameMaster> list = new ArrayList<>();
        List<CartProductMaster> list2 = new ArrayList<>();

        //change
        List<CartAdminArtProductMaster> list3=new ArrayList<>();

        List<String> cartProductIdsToDelete = new ArrayList<>();
        List<String> cartArtFrameIdsToDelete = new ArrayList<>();
        List<String> cartAdminArtProductIdsDelete=new ArrayList<>();


        List<CartMaster> cartMasterList = cartDao.findByUserMasterUserId(orderMasterRequest.getUserId());
//        System.out.println("cartMasterList: " + cartMasterList.size());

//        System.out.println("cartMasterList: " + cartMasterList.toString());

        for (CartMaster cartMaster : cartMasterList) {
            orderMaster.setTotalQty(cartMaster.getTotalQty());
            orderMaster.setTotalAmount(cartMaster.getTotalAmount());
            orderMaster.setOrderStatus("Shipping Soon");
            orderMaster.setTax(cartMaster.getTax());
            orderMaster.setPromoCode(cartMaster.getPromoCode());
            orderMaster.setGiftCode(cartMaster.getGiftCode());
            orderMaster.setPromoCodeAmount(cartMaster.getPromoCodeAmount());
            orderMaster.setGiftCodeAmount(cartMaster.getGiftCodeAmount());
            orderMaster.setOrderDate(new Date());
            orderMaster.setEstimatedShipping(cartMaster.getEstimateShipping());
            orderMaster.setEstimatedTotal(cartMaster.getEstimateAmount());
            orderMaster.setTaxAmount(cartMaster.getTaxAmount());
            orderMaster.setFinalAmount(cartMaster.getFinalAmount());
            orderMaster.setOrderUniqueNo(UniqueNumber.generateUniqueNumber());
            orderMaster.setGiftCode(cartMaster.getGiftCode());
            orderMaster.setPromoCode(cartMaster.getPromoCode());

            userMaster.setUserId(orderMasterRequest.getUserId());
            orderMaster.setUserMaster(userMaster);
            if (orderMasterRequest.getCartArtFrameId() != null && !orderMasterRequest.getCartArtFrameId().isEmpty()) {
                for (String id : orderMasterRequest.getCartArtFrameId()) {
                    System.out.println("art111.." + id);
                    CartArtFrameMaster cartArtFrameMaster = cartArtFrameDao.getId(id);
                    cartArtFrameMaster.setNewOrderUniqueNo(UniqueNumber.generateUniqueNumber());
                    list.add(cartArtFrameMaster);
                    cartArtFrameMaster.setStatus("Order Placed");
                    cartArtFrameDao.save(cartArtFrameMaster);
                }
            }

            if (orderMasterRequest.getCartProductId() != null && !orderMasterRequest.getCartProductId().isEmpty()) {
                for (String id : orderMasterRequest.getCartProductId()) {
                    System.out.println("art111.." + id);
                    CartProductMaster cartProductMaster = cartProductDao.getId(id);
                    cartProductMaster.setCartProductUniqueNo(UniqueNumber.generateUniqueNumber());
                    list2.add(cartProductMaster);
                    cartProductMaster.setStatus("Order Placed");
                    cartProductDao.save(cartProductMaster);
                }
            }


            // changes
            if (orderMasterRequest.getCartAdminArtProductId() != null && !orderMasterRequest.getCartAdminArtProductId().isEmpty())
            {
                for (String s : orderMasterRequest.getCartAdminArtProductId()) {

                    System.out.println("  admin art Product = "+s );
                   CartAdminArtProductMaster cartAdminArtProductMaster=new CartAdminArtProductMaster();
                   cartAdminArtProductMaster=cartAdminArtProductDao.findByCartAdminArtProductId(s);
                    System.out.println(" cartAdminArtProductMaster id  = "+cartAdminArtProductMaster.getCartAdminArtProductId());
                   cartAdminArtProductMaster.setCartAdminArtProductUniqueNo(UniqueNumber.generateUniqueNumber());
                   list3.add(cartAdminArtProductMaster);
                   cartAdminArtProductMaster.setStatus("Order Placed");
                   cartAdminArtProductDao.save(cartAdminArtProductMaster);
                }
            }

//            System.out.println(" list ="+list);
//            System.out.println(" list2 ="+list2);
//            System.out.println(" list3 ="+list3);

            System.out.println(" list ="+list.size());
            System.out.println(" list2 ="+list2.size());
            System.out.println(" list3 ="+list3.size());

            Integer cnt = list.size() + list2.size() + list3.size();
            orderMaster.setTotalCount(cnt);
            orderMaster.setCartProductMaster(list2);
            orderMaster.setCartArtFrameMaster(list);
            //changes
            orderMaster.setCartAdminArtProductMaster(list3);

            System.out.println(" hii 1");
//            System.out.println("  orderMaster ="+orderMaster.toString());

            try {
                orderMaster1 = orderMasterDao.save(orderMaster);
//                System.out.println("  save order  orderMaster1="+orderMaster1.toString());
                System.out.println(" hii 2");
                flag = true;
                if (flag) {
                    UserPromoCodeMaster userPromoCodeMaster = userPromoCodeDao.findByUserMaster_UserIdAndPromoCode(orderMaster1.getUserMaster().getUserId(), orderMaster1.getPromoCode());
                   if (userPromoCodeMaster!=null) {
                       userPromoCodeMaster.setStatus("Used");
                       userPromoCodeDao.save(userPromoCodeMaster);
                   }
                    System.out.println(" hii 3");

                   UserGiftCodeMaster userGiftCodeMaster=userGiftCodeDao.findByUserMaster_UserIdAndGiftCode(orderMaster1.getUserMaster().getUserId(), orderMaster1.getGiftCode());
                    if (userGiftCodeMaster!=null) {
                        userGiftCodeMaster.setStatus("Used");
                        userGiftCodeDao.save(userGiftCodeMaster);
                    }

                    System.out.println(" hii 4");
                    List<CartProductMaster> cartProductMasterList = cartMaster.getCartProductMaster();
                    cartProductMasterList.removeIf(cartProductMaster -> cartProductIdsToDelete.contains(cartProductMaster.getCartProductId()));

                    List<CartArtFrameMaster> cartArtFrameMasterList = cartMaster.getCartArtFrameMaster();
                    cartArtFrameMasterList.removeIf(cartArtFrameMaster -> cartArtFrameIdsToDelete.contains(cartArtFrameMaster.getCartArtFrameId()));

                    //changes
                    List<CartAdminArtProductMaster> cartAdminArtProductMasterList=cartMaster.getCartAdminArtProductMaster();
                    cartAdminArtProductMasterList.removeIf(cartAdminArtProductMaster -> cartAdminArtProductIdsDelete.contains(cartAdminArtProductMaster.getCartAdminArtProductId()));

                    System.out.println(" hii 5");

                    cartMaster.setFinalAmount(0.0);
                    cartMaster.setTotalAmount(0.0);
                    cartMaster.setEstimateAmount(0.0);
                    cartMaster.setPromoCodeAmount(0.0);
                    cartMaster.setGiftCodeAmount(0.0);
                    cartMaster.setTotalQty(0);
                    cartMaster.setTaxAmount(0.0);
                    cartMaster.setStatus("Cart is Empty");
                    cartMaster.setTotalCount(0);
                    cartDao.save(cartMaster);

                    System.out.println(" hii 6");

                }
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }


    @Override
    public Boolean createSingleOrder(SingleOrderMasterRequest singleOrderMasterRequest) {

        Boolean flag = false;
        OrderMaster orderMaster = new OrderMaster();
        OrderMaster orderMaster1 = new OrderMaster();
        UserMaster userMaster = new UserMaster();
        userMaster=userDao.getId(singleOrderMasterRequest.getUserId());
        List<CartArtFrameMaster> list = new ArrayList<>();
        List<CartProductMaster> list2 = new ArrayList<>();
        //change
        List<CartAdminArtProductMaster> list3=new ArrayList<>();
        List<String> cartProductIdsToDelete = new ArrayList<>();
        List<String> cartArtFrameIdsToDelete = new ArrayList<>();
        List<String> cartAdminArtProductIdsDelete=new ArrayList<>();
        //

        CartMaster cartMaster=new CartMaster();
        cartMaster=cartDao.findByUserMaster_UserIdAndCartId(singleOrderMasterRequest.getUserId(),singleOrderMasterRequest.getCartId());

        try {
            switch (singleOrderMasterRequest.getType()) {
                case "cartProduct":
                    if (singleOrderMasterRequest.getProductId() != null){
                        CartProductMaster cartProductMaster=cartProductDao.getId(singleOrderMasterRequest.getProductId());

                        orderMaster.setTotalQty(cartProductMaster.getQuantity());
                        orderMaster.setTotalAmount(cartProductMaster.getAmount()*cartProductMaster.getQuantity());
                        orderMaster.setOrderStatus("Shipping Soon");
                        orderMaster.setTax(cartMaster.getTax());
                        orderMaster.setPromoCode(cartMaster.getPromoCode());
                        orderMaster.setGiftCode(cartMaster.getGiftCode());
                        orderMaster.setPromoCodeAmount(cartMaster.getPromoCodeAmount());
                        orderMaster.setGiftCodeAmount(cartMaster.getGiftCodeAmount());
                        orderMaster.setOrderDate(new Date());
                        orderMaster.setEstimatedShipping(cartMaster.getEstimateShipping());
                        orderMaster.setTaxAmount(orderMaster.getTotalAmount()*cartMaster.getTax()/100);
                        orderMaster.setEstimatedTotal(orderMaster.getTotalAmount()+orderMaster.getTaxAmount());
                        orderMaster.setFinalAmount(orderMaster.getTotalAmount());
                        orderMaster.setOrderUniqueNo(UniqueNumber.generateUniqueNumber());
                        orderMaster.setGiftCode(cartMaster.getGiftCode());
                        orderMaster.setPromoCode(cartMaster.getPromoCode());

                        cartProductMaster.setCartProductUniqueNo(UniqueNumber.generateUniqueNumber());
                        list2.add(cartProductMaster);
                        cartProductMaster.setStatus("Order Placed");
                        userMaster.setUserId(singleOrderMasterRequest.getUserId());
                        orderMaster.setUserMaster(userMaster);

                        cartMaster.getCartProductMaster().removeIf(cartProductMaster1 -> cartProductMaster1.getCartProductId().equalsIgnoreCase(singleOrderMasterRequest.getProductId()));
                        cartProductDao.deleteById(singleOrderMasterRequest.getProductId());

                        cartMaster.setFinalAmount(cartMaster.getFinalAmount()-orderMaster.getFinalAmount());
                        cartMaster.setTotalAmount(cartMaster.getTotalAmount()-orderMaster.getTotalAmount());
                        cartMaster.setEstimateAmount(cartMaster.getEstimateAmount()-orderMaster.getEstimatedTotal());
                        cartMaster.setPromoCodeAmount(0.0);
                        cartMaster.setGiftCodeAmount(0.0);
                        cartMaster.setTotalQty(cartMaster.getTotalQty()-orderMaster.getTotalQty());
                        cartMaster.setTaxAmount(cartMaster.getTaxAmount()-orderMaster.getTaxAmount());
                        cartMaster.setStatus("Active");
                        cartMaster.setTotalCount(cartMaster.getTotalCount()-orderMaster.getTotalQty());
                        cartDao.save(cartMaster);
                    }
                    flag = true;
                    break;
                case "cartArtFrame":
                    if (singleOrderMasterRequest.getProductId() != null) {
//                        cartMaster.getCartArtFrameMaster().removeIf(cartArtFrameMaster -> cartArtFrameMaster.getCartArtFrameId().equalsIgnoreCase(singleOrderMasterRequest.getProductId()));
                        CartArtFrameMaster cartArtFrameMaster=cartArtFrameDao.getId(singleOrderMasterRequest.getProductId());

                        orderMaster.setTotalQty(cartArtFrameMaster.getQuantity());
                        orderMaster.setTotalAmount(cartArtFrameMaster.getAmount()*cartArtFrameMaster.getQuantity());
                        orderMaster.setOrderStatus("Shipping Soon");
                        orderMaster.setTax(cartMaster.getTax());
                        orderMaster.setPromoCode(cartMaster.getPromoCode());
                        orderMaster.setGiftCode(cartMaster.getGiftCode());
                        orderMaster.setPromoCodeAmount(cartMaster.getPromoCodeAmount());
                        orderMaster.setGiftCodeAmount(cartMaster.getGiftCodeAmount());
                        orderMaster.setOrderDate(new Date());
                        orderMaster.setEstimatedShipping(cartMaster.getEstimateShipping());
                        orderMaster.setEstimatedTotal(orderMaster.getTotalAmount()+orderMaster.getTaxAmount());
                        orderMaster.setTaxAmount(orderMaster.getTotalAmount()*cartMaster.getTax()/100);
                        orderMaster.setFinalAmount(orderMaster.getTotalAmount());
                        orderMaster.setOrderUniqueNo(UniqueNumber.generateUniqueNumber());
                        orderMaster.setGiftCode(cartMaster.getGiftCode());
                        orderMaster.setPromoCode(cartMaster.getPromoCode());

                        userMaster.setUserId(singleOrderMasterRequest.getUserId());
                        orderMaster.setUserMaster(userMaster);
//                        cartMaster.getCartProductMaster().removeIf(cartProductMaster1 -> cartProductMaster1.getCartProductId().equalsIgnoreCase(singleOrderMasterRequest.getProductId()));

                        cartArtFrameMaster.setNewOrderUniqueNo(UniqueNumber.generateUniqueNumber());
                        list.add(cartArtFrameMaster);
                        cartArtFrameMaster.setStatus("Order Placed");
                        cartArtFrameDao.deleteById(singleOrderMasterRequest.getProductId());

                        cartMaster.setFinalAmount(cartMaster.getFinalAmount()-orderMaster.getFinalAmount());
                        cartMaster.setTotalAmount(cartMaster.getTotalAmount()-orderMaster.getTotalAmount());
                        cartMaster.setEstimateAmount(cartMaster.getEstimateAmount()-orderMaster.getEstimatedTotal());
                        cartMaster.setPromoCodeAmount(0.0);
                        cartMaster.setGiftCodeAmount(0.0);
                        cartMaster.setTotalQty(cartMaster.getTotalQty()-orderMaster.getTotalQty());
                        cartMaster.setTaxAmount(cartMaster.getTaxAmount()-orderMaster.getTaxAmount());
                        cartMaster.setStatus("Active");
                        cartMaster.setTotalCount(cartMaster.getTotalCount()-orderMaster.getTotalQty());
                        cartDao.save(cartMaster);
                    }
                    flag = true;
                    break;
                case "cartAdminArtProduct":
                    if (singleOrderMasterRequest.getProductId() != null) {
//                        cartMaster.getCartAdminArtProductMaster().removeIf(cartAdminArtProductMaster -> cartAdminArtProductMaster.getCartAdminArtProductId().equalsIgnoreCase(singleOrderMasterRequest.getProductId()));
                        CartAdminArtProductMaster cartAdminArtProductMaster=cartAdminArtProductDao.findByCartAdminArtProductId(singleOrderMasterRequest.getProductId());

                        orderMaster.setTotalQty(cartAdminArtProductMaster.getQuantity());
                        orderMaster.setTotalAmount(cartAdminArtProductMaster.getAmount()*cartAdminArtProductMaster.getQuantity());
                        orderMaster.setOrderStatus("Shipping Soon");
                        orderMaster.setTax(cartMaster.getTax());
                        orderMaster.setPromoCode(cartMaster.getPromoCode());
                        orderMaster.setGiftCode(cartMaster.getGiftCode());
                        orderMaster.setPromoCodeAmount(cartMaster.getPromoCodeAmount());
                        orderMaster.setGiftCodeAmount(cartMaster.getGiftCodeAmount());
                        orderMaster.setOrderDate(new Date());
                        orderMaster.setEstimatedShipping(cartMaster.getEstimateShipping());
                        orderMaster.setEstimatedTotal(orderMaster.getTotalAmount()+orderMaster.getTaxAmount());
                        orderMaster.setTaxAmount(orderMaster.getTotalAmount()*cartMaster.getTax()/100);
                        orderMaster.setFinalAmount(orderMaster.getTotalAmount());
                        orderMaster.setOrderUniqueNo(UniqueNumber.generateUniqueNumber());
                        orderMaster.setGiftCode(cartMaster.getGiftCode());
                        orderMaster.setPromoCode(cartMaster.getPromoCode());

                        userMaster.setUserId(singleOrderMasterRequest.getUserId());
                        orderMaster.setUserMaster(userMaster);
//                        cartMaster.getCartProductMaster().removeIf(cartProductMaster1 -> cartProductMaster1.getCartProductId().equalsIgnoreCase(singleOrderMasterRequest.getProductId()));

                        cartAdminArtProductMaster.setCartAdminArtProductUniqueNo(UniqueNumber.generateUniqueNumber());
                        cartAdminArtProductMaster.setStatus("Order Placed");
                        cartAdminArtProductDao.deleteById(singleOrderMasterRequest.getProductId());

                        cartMaster.setFinalAmount(cartMaster.getFinalAmount()-orderMaster.getFinalAmount());
                        cartMaster.setTotalAmount(cartMaster.getTotalAmount()-orderMaster.getTotalAmount());
                        cartMaster.setEstimateAmount(cartMaster.getEstimateAmount()-orderMaster.getEstimatedTotal());
                        cartMaster.setPromoCodeAmount(0.0);
                        cartMaster.setGiftCodeAmount(0.0);
                        cartMaster.setTotalQty(cartMaster.getTotalQty()-orderMaster.getTotalQty());
                        cartMaster.setTaxAmount(cartMaster.getTaxAmount()-orderMaster.getTaxAmount());
                        cartMaster.setStatus("Active");
                        cartMaster.setTotalCount(cartMaster.getTotalCount()-orderMaster.getTotalQty());
                        cartDao.save(cartMaster);
                    }
                    flag = true;
                    break;
                default:
                    flag = false;
                    break;
            }

            Integer cnt = list.size() + list2.size() + list3.size();
            orderMaster.setTotalCount(cnt);
            orderMaster.setCartProductMaster(list2);
            orderMaster.setCartArtFrameMaster(list);
            //changes
            orderMaster.setCartAdminArtProductMaster(list3);

            try {
                orderMaster1 = orderMasterDao.save(orderMaster);
                flag = true;
                if (flag) {
                    UserPromoCodeMaster userPromoCodeMaster = userPromoCodeDao.findByUserMaster_UserIdAndPromoCode(orderMaster1.getUserMaster().getUserId(), orderMaster1.getPromoCode());
                    if (userPromoCodeMaster!=null) {
                        userPromoCodeMaster.setStatus("Used");
                        userPromoCodeDao.save(userPromoCodeMaster);
                    }
                    UserGiftCodeMaster userGiftCodeMaster=userGiftCodeDao.findByUserMaster_UserIdAndGiftCode(orderMaster1.getUserMaster().getUserId(), orderMaster1.getGiftCode());
                    if (userGiftCodeMaster!=null) {
                        userGiftCodeMaster.setStatus("Used");
                        userGiftCodeDao.save(userGiftCodeMaster);
                    }

//
//                    List<CartProductMaster> cartProductMasterList = cartMaster.getCartProductMaster();
//                    cartProductMasterList.removeIf(cartProductMaster -> cartProductIdsToDelete.contains(cartProductMaster.getCartProductId()));
//
//                    List<CartArtFrameMaster> cartArtFrameMasterList = cartMaster.getCartArtFrameMaster();
//                    cartArtFrameMasterList.removeIf(cartArtFrameMaster -> cartArtFrameIdsToDelete.contains(cartArtFrameMaster.getCartArtFrameId()));
//
//                    //changes
//                    List<CartAdminArtProductMaster> cartAdminArtProductMasterList=cartMaster.getCartAdminArtProductMaster();
//                    cartAdminArtProductMasterList.removeIf(cartAdminArtProductMaster -> cartAdminArtProductIdsDelete.contains(cartAdminArtProductMaster.getCartAdminArtProductId()));



                }
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }

            return flag;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        //
//        CartMaster cartMaster=cartDao.findByUserMaster_UserId(singleOrderMasterRequest.getUserId());

//        List<CartMaster> cartMasterList = cartDao.findByUserMasterUserId(singleOrderMasterRequest.getUserId());
//        System.out.println("cartMasterList: " + cartMasterList.size());

//        for (CartMaster cartMaster : cartMasterList) {
//            orderMaster.setTotalQty(cartMaster.getTotalQty());
//            orderMaster.setTotalAmount(cartMaster.getTotalAmount());
//            orderMaster.setOrderStatus("Shipping Soon");
//            orderMaster.setTax(cartMaster.getTax());
//            orderMaster.setPromoCode(cartMaster.getPromoCode());
//            orderMaster.setGiftCode(cartMaster.getGiftCode());
//            orderMaster.setPromoCodeAmount(cartMaster.getPromoCodeAmount());
//            orderMaster.setGiftCodeAmount(cartMaster.getGiftCodeAmount());
//            orderMaster.setOrderDate(new Date());
//            orderMaster.setEstimatedShipping(cartMaster.getEstimateShipping());
//            orderMaster.setEstimatedTotal(cartMaster.getEstimateAmount());
//            orderMaster.setTaxAmount(cartMaster.getTaxAmount());
//            orderMaster.setFinalAmount(cartMaster.getFinalAmount());
//            orderMaster.setOrderUniqueNo(UniqueNumber.generateUniqueNumber());
//            orderMaster.setGiftCode(cartMaster.getGiftCode());
//            orderMaster.setPromoCode(cartMaster.getPromoCode());

//            userMaster.setUserId(singleOrderMasterRequest.getUserId());
//            orderMaster.setUserMaster(userMaster);
//            if (orderMasterRequest.getCartArtFrameId() != null && !orderMasterRequest.getCartArtFrameId().isEmpty()) {
//                for (String id : orderMasterRequest.getCartArtFrameId()) {
//                    System.out.println("art111.." + id);
//                    CartArtFrameMaster cartArtFrameMaster = cartArtFrameDao.getId(id);
//                    cartArtFrameMaster.setNewOrderUniqueNo(UniqueNumber.generateUniqueNumber());
//                    list.add(cartArtFrameMaster);
//                    cartArtFrameMaster.setStatus("Order Placed");
//                    cartArtFrameDao.save(cartArtFrameMaster);
//                }
//            }
//            if (orderMasterRequest.getCartProductId() != null && !orderMasterRequest.getCartProductId().isEmpty()) {
//                for (String id : orderMasterRequest.getCartProductId()) {
//                    System.out.println("art111.." + id);
//                    CartProductMaster cartProductMaster = cartProductDao.getId(id);
//                    cartProductMaster.setCartProductUniqueNo(UniqueNumber.generateUniqueNumber());
//                    list2.add(cartProductMaster);
//                    cartProductMaster.setStatus("Order Placed");
//                    cartProductDao.save(cartProductMaster);
//                }
//            }
//
//            // changes
//            if(orderMasterRequest.getCartAdminArtProductId()!=null && !orderMasterRequest.getCartAdminArtProductId().isEmpty())
//            {
//                for (String s : orderMasterRequest.getCartAdminArtProductId()) {
//                    CartAdminArtProductMaster cartAdminArtProductMaster=new CartAdminArtProductMaster();
//                    cartAdminArtProductMaster=cartAdminArtProductDao.findByCartAdminArtProductId(s);
//                    cartAdminArtProductMaster.setCartAdminArtProductUniqueNo(UniqueNumber.generateUniqueNumber());
//                    list3.add(cartAdminArtProductMaster);
//                    cartAdminArtProductMaster.setStatus("Order Placed");
//                    cartAdminArtProductDao.save(cartAdminArtProductMaster);
//                }
//            }

//            Integer cnt = list.size() + list2.size() + list3.size();
//            orderMaster.setTotalCount(cnt);
//            orderMaster.setCartProductMaster(list2);
//            orderMaster.setCartArtFrameMaster(list);
//            changes
//            orderMaster.setCartAdminArtProductMaster(list3);

//            try {
//                orderMaster1 = orderMasterDao.save(orderMaster);
//                flag = true;
//                if (flag) {
//                    UserPromoCodeMaster userPromoCodeMaster = userPromoCodeDao.findByUserMaster_UserIdAndPromoCode(orderMaster1.getUserMaster().getUserId(), orderMaster1.getPromoCode());
//                    if (userPromoCodeMaster!=null) {
//                        userPromoCodeMaster.setStatus("Used");
//                        userPromoCodeDao.save(userPromoCodeMaster);
//                    }
//                    UserGiftCodeMaster userGiftCodeMaster=userGiftCodeDao.findByUserMaster_UserIdAndGiftCode(orderMaster1.getUserMaster().getUserId(), orderMaster1.getGiftCode());
//                    if (userGiftCodeMaster!=null) {
//                        userGiftCodeMaster.setStatus("Used");
//                        userGiftCodeDao.save(userGiftCodeMaster);
//                    }
//
//
//                    List<CartProductMaster> cartProductMasterList = cartMaster.getCartProductMaster();
//                    cartProductMasterList.removeIf(cartProductMaster -> cartProductIdsToDelete.contains(cartProductMaster.getCartProductId()));
//
//                    List<CartArtFrameMaster> cartArtFrameMasterList = cartMaster.getCartArtFrameMaster();
//                    cartArtFrameMasterList.removeIf(cartArtFrameMaster -> cartArtFrameIdsToDelete.contains(cartArtFrameMaster.getCartArtFrameId()));
//
//                    changes
//                    List<CartAdminArtProductMaster> cartAdminArtProductMasterList=cartMaster.getCartAdminArtProductMaster();
//                    cartAdminArtProductMasterList.removeIf(cartAdminArtProductMaster -> cartAdminArtProductIdsDelete.contains(cartAdminArtProductMaster.getCartAdminArtProductId()));
//
//                    cartMaster.setFinalAmount(0.0);
//                    cartMaster.setTotalAmount(0.0);
//                    cartMaster.setEstimateAmount(0.0);
//                    cartMaster.setPromoCodeAmount(0.0);
//                    cartMaster.setGiftCodeAmount(0.0);
//                    cartMaster.setTotalQty(0);
//                    cartMaster.setTaxAmount(0.0);
//                    cartMaster.setStatus("Cart is Empty");
//                    cartMaster.setTotalCount(0);
//                    cartDao.save(cartMaster);
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                flag = false;
//            }
//        }
//        return flag;
    }

    @Override
    public UserOrderRes getAllOrdersByUserId(String userId) {

        UserOrderRes userOrderRes=new UserOrderRes();
        userOrderRes.setUserMaster(userDao.findByUserId(userId).get());

        List<OrderMaster> orderMasterList=new ArrayList<>();
        orderMasterList=orderMasterDao.findAllByUserMaster_UserId(userId);
        userOrderRes.setOrderMasterList(orderMasterList);

        return userOrderRes;
    }

    @Override
    public UserOrderRes getAllOrdersByUserIdAndDate(OrderFilterReq orderFilterReq) {
        UserOrderRes userOrderRes=new UserOrderRes();
        userOrderRes.setUserMaster(userDao.findByUserId(orderFilterReq.getUserId()).get());

        List<OrderMaster> orderMasterList=new ArrayList<>();
        orderMasterList=orderMasterDao.findAllByUserMaster_UserId(orderFilterReq.getUserId());
        orderMasterList=  orderMasterList.stream().filter(orderMaster -> orderMaster.getOrderDate().equals(orderFilterReq.getDate())).collect(Collectors.toList());
        userOrderRes.setOrderMasterList(orderMasterList);

//        Query query = new Query();

//        query.addCriteria(Criteria.where("orderDate").gte(orderFilterReq.getDate()).lte(orderFilterReq.getDate())
//                .and("userMaster.userId").is(orderFilterReq.getUserId()));

//        query.addCriteria(Criteria.where("orderDate").gte(orderFilterReq.getDate()).lte(orderFilterReq.getDate())
//                .and("userMaster.userId").is(orderFilterReq.getUserId()));

//        query.addCriteria(Criteria.where("orderDate").gte(orderFilterReq.getDate()).lte(orderFilterReq.getDate())
//                .and("userMaster.userId").is(orderFilterReq.getUserId()));

//        query.addCriteria(Criteria.where("userMaster").is(userOrderRes.getUserMaster()));
//        orderMasterList= mongoTemplate.find(query, OrderMaster.class);

        return userOrderRes;

    }


    @Override
    public List getUserIdWiseOrderList(String userId) {
        List<OrderMaster> orderMasterList = orderMasterDao.findByUserMaster_UserId(userId);
        System.out.println("orderMasterList: " + orderMasterList.size());
        return orderMasterList;
    }

    @Override
    public OrderMaster editOrderMaster(String orderId) {
        OrderMaster orderMaster = new OrderMaster();
        try {
            Optional<OrderMaster> optionalOrderMaster = orderMasterDao.findById(orderId);
            optionalOrderMaster.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, orderMaster));
            return orderMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return orderMaster;
        }
    }

//    @Override
//    public UpdateOrderStatusRes updateOrderStatus(OrderStatusUpdateRequest orderStatusUpdateRequest) {
//        UpdateOrderStatusRes updateOrderStatusRes = new UpdateOrderStatusRes();
//        Optional<OrderMaster> optionalOrderMaster = orderMasterDao.findByOrderId(orderStatusUpdateRequest.getOrderId());
//        if (optionalOrderMaster.isPresent()) {
//            OrderMaster orderMaster = optionalOrderMaster.get();
//            CartArtFrameMaster cartArtFrameMaster = orderMaster.getCartArtFrameMaster().get(0);
//            cartArtFrameMaster.setStatus(orderStatusUpdateRequest.getOrderStatus());
//            cartArtFrameMaster.setDate(new Date());
//            orderMaster.setCartArtFrameMaster(cartArtFrameMaster));
//            orderMasterDao.save(orderMaster);
//            updateOrderStatusRes.setMsg("Order Status Updated Successfully");
//            updateOrderStatusRes.setFlag(true);
//        } else {
//            updateOrderStatusRes.setMsg("Order Status Not Updated");
//            updateOrderStatusRes.setFlag(false);
//        }
//        return updateOrderStatusRes;
//    }

    @Override
    public UpdateOrderStatusRes updateOrderStatus(OrderStatusUpdateRequest orderStatusUpdateRequest) {
        UpdateOrderStatusRes updateOrderStatusRes = new UpdateOrderStatusRes();
        Optional<OrderMaster> optionalOrderMaster = orderMasterDao.findByOrderId(orderStatusUpdateRequest.getOrderId());
        if (optionalOrderMaster.isPresent()) {
            OrderMaster orderMaster = optionalOrderMaster.get();
            String cartArtFrameIdToUpdate = orderStatusUpdateRequest.getCartArtFrameId();
            CartArtFrameMaster cartArtFrameToUpdate = null;
            for (CartArtFrameMaster cartArtFrame : orderMaster.getCartArtFrameMaster()) {
                if (cartArtFrame.getCartArtFrameId().equals(cartArtFrameIdToUpdate)) {
                    cartArtFrameToUpdate = cartArtFrame;
                    break;
                }
            }
            if (cartArtFrameToUpdate != null) {
                cartArtFrameToUpdate.setStatus(orderStatusUpdateRequest.getOrderStatus());
                cartArtFrameToUpdate.setUpdatedDate(new Date());
                orderMasterDao.save(orderMaster);
                updateOrderStatusRes.setMsg("Order Status Updated Successfully");
                updateOrderStatusRes.setFlag(true);
            } else {
                updateOrderStatusRes.setMsg("CartArtFrame with the given ID not found in the Order");
                updateOrderStatusRes.setFlag(false);
            }
        } else {
            updateOrderStatusRes.setMsg("Order not found");
            updateOrderStatusRes.setFlag(false);
        }
        return updateOrderStatusRes;
    }


    @Override
    public OrderMaster getCartArtFrameIAndOrderIdWiseOrderList(String cartArtFrameId, String orderId) {
        System.out.println("cartArtFrameId: " + cartArtFrameId + " orderId: " + orderId);

        // Step 1: Get the OrderMaster by cartArtFrameId and orderId
        OrderMaster orderMaster = orderMasterDao.findByCartArtFrameMaster_CartArtFrameIdAndOrderId(cartArtFrameId, orderId);
        if (orderMaster == null) {
            // Handle the case when no order is found with the given cartArtFrameId and orderId
            return null;
        }
        // Step 2: Retrieve the CartArtFrameMasterList from the OrderMaster
        List<CartArtFrameMaster> cartArtFrameMasterList = orderMaster.getCartArtFrameMaster();

        // Step 3: Find the specific object within the CartArtFrameMasterList that matches the given cartArtFrameId
        CartArtFrameMaster specificCartArtFrameMaster = null;

        for (CartArtFrameMaster cartArtFrameMaster : cartArtFrameMasterList) {
            if (cartArtFrameMaster.getCartArtFrameId().equals(cartArtFrameId)) {
                specificCartArtFrameMaster = cartArtFrameMaster;
                break;
            }
        }
        if (specificCartArtFrameMaster != null) {
            // Step 4: Create a new list with the specific object and set it back to the orderMaster
            List<CartArtFrameMaster> newList = new ArrayList<>();
            newList.add(specificCartArtFrameMaster);
            orderMaster.setCartArtFrameMaster(newList);
        } else {
            // Handle the case when no matching cartArtFrameId is found
            return null;
        }
        // Return the modified orderMaster object containing only the specific object in the CartArtFrameMasterList
        return orderMaster;
    }

    @Override
    public List<OrderResDto> getUserIdWiseCartArtFrameMasterList(String userId) {
        List<OrderResDto> orderResDtoList = new ArrayList<>();
        List<OrderMaster> orderMasterList = orderMasterDao.findByUserMaster_UserId(userId);
        for (OrderMaster orderMaster : orderMasterList) {
            OrderResDto orderResDto = new OrderResDto();
            //  first cart item details in the response
            if (orderMaster.getCartArtFrameMaster() != null && !orderMaster.getCartArtFrameMaster().isEmpty()) {
                CartArtFrameMaster cartItem = orderMaster.getCartArtFrameMaster().get(0);
                orderResDto.setArtFrameName(cartItem.getArtMaster().getArtName());
                orderResDto.setAmount(cartItem.getAmount());
                orderResDto.setImgUrl(cartItem.getImgUrl());
                orderResDto.setStatus(cartItem.getStatus());
                orderResDto.setArtDescription(cartItem.getArtMaster().getDescription());
                orderResDto.setCartArtFrameUniqueNo(cartItem.getCartArtFrameUniqueNo());
                orderResDto.setContributorFirstName(cartItem.getArtMaster().getUserMaster().getUserFirstName());
                orderResDto.setContributorLastName(cartItem.getArtMaster().getUserMaster().getUserLastName());
                orderResDto.setContributorDisplayName(cartItem.getArtMaster().getUserMaster().getDisplayName());
                orderResDto.setOrientationMaster(cartItem.getOrientationMaster());
            }
            orderResDto.setShippingMethod(orderMaster.getShippingMethod());
            orderResDto.setOrderId(orderMaster.getOrderId());
            orderResDto.setUserFirstName(orderMaster.getUserMaster().getUserFirstName());
            orderResDto.setUserLastName(orderMaster.getUserMaster().getUserLastName());
            orderResDto.setUserDisplayName(orderMaster.getUserMaster().getUserLastName());
            orderResDto.setShippingAddress(orderMaster.getUserMaster().getShippingAddress());
            orderResDto.setOrderDate(orderMaster.getOrderDate());
            orderResDto.setEmailAddress(orderMaster.getUserMaster().getEmailAddress());
            orderResDto.setUserId(orderMaster.getUserMaster().getUserId());
            orderResDto.setTotalAmount(orderMaster.getTotalAmount());
            orderResDto.setTotalQty(orderMaster.getTotalQty());
            orderResDtoList.add(orderResDto);
        }
        return orderResDtoList;
    }

    @Override
    public List<OrderMaster> getAllOrderList() {
        return orderMasterDao.findAll();
    }

    @Override
    public List<OrderResDto> getStatusWiseOrderList(String status) {
        List<OrderResDto> orderResDtoList = new ArrayList<>();
        List<OrderMaster> list = orderMasterDao.findByCartArtFrameMaster_Status(status);
        for (OrderMaster orderMaster : list) {


            OrderResDto orderResDto = new OrderResDto();
            CartArtFrameMaster cartItem = orderMaster.getCartArtFrameMaster().get(0);
            orderResDto.setArtFrameName(cartItem.getArtMaster().getArtName());
            orderResDto.setAmount(cartItem.getAmount());
            orderResDto.setImgUrl(cartItem.getImgUrl());
            orderResDto.setStatus(cartItem.getStatus());
            orderResDto.setArtDescription(cartItem.getArtMaster().getDescription());
            orderResDto.setCartArtFrameUniqueNo(cartItem.getCartArtFrameUniqueNo());
            orderResDto.setContributorFirstName(cartItem.getArtMaster().getUserMaster().getUserFirstName());
            orderResDto.setContributorLastName(cartItem.getArtMaster().getUserMaster().getUserLastName());
            orderResDto.setContributorDisplayName(cartItem.getArtMaster().getUserMaster().getDisplayName());
            orderResDto.setOrientationMaster(cartItem.getOrientationMaster());
            orderResDto.setShippingMethod(orderMaster.getShippingMethod());
            orderResDto.setOrderId(orderMaster.getOrderId());
            orderResDto.setUserFirstName(orderMaster.getUserMaster().getUserFirstName());
            orderResDto.setUserLastName(orderMaster.getUserMaster().getUserLastName());
            orderResDto.setUserDisplayName(orderMaster.getUserMaster().getUserLastName());
            orderResDto.setShippingAddress(orderMaster.getUserMaster().getShippingAddress());
            orderResDto.setOrderDate(orderMaster.getOrderDate());
            orderResDto.setUserId(orderMaster.getUserMaster().getUserId());
            orderResDto.setTotalQty(orderMaster.getTotalQty());
            orderResDto.setTotalAmount(orderMaster.getTotalAmount());
            orderResDto.setEmailAddress(orderMaster.getUserMaster().getEmailAddress());
            orderResDtoList.add(orderResDto);
        }
        System.out.println("list: " + orderResDtoList.size());
        return orderResDtoList;
    }

    @Override
    public OrderResDto getCartArtFrameUniqueNoWiseCartArtFrameMaster(String cartArtFrameUniqueNo, String orderId) {
        OrderMaster orderMaster = orderMasterDao.getOrder(orderId);
        OrderResDto orderResDto = new OrderResDto();
        System.out.println("orderMaster: " + orderMaster.toString());
        for (CartArtFrameMaster cartArtFrameMaster : orderMaster.getCartArtFrameMaster()) {
            if (cartArtFrameMaster.getCartArtFrameUniqueNo().equals(cartArtFrameUniqueNo)) {
                orderResDto.setArtFrameName(cartArtFrameMaster.getArtMaster().getArtName());
                orderResDto.setAmount(cartArtFrameMaster.getAmount());
                orderResDto.setImgUrl(cartArtFrameMaster.getImgUrl());
                orderResDto.setStatus(cartArtFrameMaster.getStatus());
                orderResDto.setArtDescription(cartArtFrameMaster.getArtMaster().getDescription());
                orderResDto.setCartArtFrameUniqueNo(cartArtFrameMaster.getCartArtFrameUniqueNo());
                orderResDto.setContributorFirstName(cartArtFrameMaster.getArtMaster().getUserMaster().getUserFirstName());
                orderResDto.setContributorLastName(cartArtFrameMaster.getArtMaster().getUserMaster().getUserLastName());
                orderResDto.setContributorDisplayName(cartArtFrameMaster.getArtMaster().getUserMaster().getDisplayName());
                orderResDto.setOrientationMaster(cartArtFrameMaster.getOrientationMaster());
                orderResDto.setOrderId(orderMaster.getOrderId());
                orderResDto.setUserFirstName(orderMaster.getUserMaster().getUserFirstName());
                orderResDto.setUserLastName(orderMaster.getUserMaster().getUserLastName());
                orderResDto.setUserDisplayName(orderMaster.getUserMaster().getUserLastName());
                orderResDto.setShippingAddress(orderMaster.getUserMaster().getShippingAddress());
                orderResDto.setOrderDate(orderMaster.getOrderDate());
                orderResDto.setTotalQty(orderMaster.getTotalQty());
                orderResDto.setTotalAmount(orderMaster.getTotalAmount());
                orderResDto.setEmailAddress(orderMaster.getUserMaster().getEmailAddress());
                orderResDto.setUserId(orderMaster.getUserMaster().getUserId());
                return orderResDto;
            }
        }
        return orderResDto;
    }

    @Override
    public OrderMaster getCartProductIAndOrderIdWiseOrderList(String cartProductId, String orderId) {
        OrderMaster orderMaster = orderMasterDao.findByCartProductMaster_CartProductIdAndOrderId(cartProductId, orderId);
        if (orderMaster == null) {
            return null;
        }
        List<CartProductMaster> cartProductMasterList = orderMaster.getCartProductMaster();
        CartProductMaster specificCartProductMaster = null;

        for (CartProductMaster cartProductMaster : cartProductMasterList) {
            if (cartProductMaster.getCartProductId().equals(cartProductId)) {
                specificCartProductMaster = cartProductMaster;
                break;
            }
        }
        if (specificCartProductMaster != null) {
            List<CartProductMaster> newList = new ArrayList<>();
            newList.add(specificCartProductMaster);
            orderMaster.setCartProductMaster(newList);
        } else {
            return null;
        }
        return orderMaster;
    }

    @Override
    public OrderCartProductResDto getCartProductUniqueNoWiseData(String cartProductNo, String orderId) {
        OrderMaster orderMaster = orderMasterDao.getOrder(orderId);
        OrderCartProductResDto cartProductResDto = new OrderCartProductResDto();
        System.out.println("orderMaster: " + orderMaster.toString());
        for (CartProductMaster cartProductMaster : orderMaster.getCartProductMaster()) {
            if (cartProductMaster.getCartProductNo().equals(cartProductNo)) {
                cartProductResDto.setArtProductName(cartProductMaster.getArtProductMaster().getArtMaster().getArtName());
                cartProductResDto.setAmount(cartProductMaster.getAmount());
                cartProductResDto.setStatus(cartProductMaster.getStatus());
                cartProductResDto.setArtDescription(cartProductMaster.getArtProductMaster().getArtMaster().getDescription());
                cartProductResDto.setCartProductNo(cartProductMaster.getCartProductNo());
                cartProductResDto.setContributorFirstName(cartProductMaster.getArtProductMaster().getArtMaster().getUserMaster().getUserFirstName());
                cartProductResDto.setContributorLastName(cartProductMaster.getArtProductMaster().getArtMaster().getUserMaster().getUserLastName());
                cartProductResDto.setContributorDisplayName(cartProductMaster.getArtProductMaster().getArtMaster().getUserMaster().getDisplayName());
                cartProductResDto.setOrderId(orderMaster.getOrderId());
                cartProductResDto.setTotalAmount(orderMaster.getTotalAmount());
                cartProductResDto.setTotalQty(orderMaster.getTotalQty());

                cartProductResDto.setUserFirstName(orderMaster.getUserMaster().getUserFirstName());
                cartProductResDto.setUserLastName(orderMaster.getUserMaster().getUserLastName());
                cartProductResDto.setUserDisplayName(orderMaster.getUserMaster().getUserLastName());
                cartProductResDto.setShippingAddress(orderMaster.getUserMaster().getShippingAddress());
                cartProductResDto.setOrderDate(orderMaster.getOrderDate());

                cartProductResDto.setEmailAddress(orderMaster.getUserMaster().getEmailAddress());

                cartProductResDto.setUserId(orderMaster.getUserMaster().getUserId());
                return cartProductResDto;
            }
        }
        return cartProductResDto;
    }

    @Override
    public UpdateOrderStatusRes updateOrderStatusCartArtProductWise(OrderStatusUpdateArtProductRequest orderStatusUpdateArtProductRequest) {
        UpdateOrderStatusRes updateOrderStatusRes = new UpdateOrderStatusRes();
        Optional<OrderMaster> optionalOrderMaster = orderMasterDao.findByOrderId(orderStatusUpdateArtProductRequest.getOrderId());
        if (optionalOrderMaster.isPresent()) {
            OrderMaster orderMaster = optionalOrderMaster.get();
            String cartArtProductIdToUpdate = orderStatusUpdateArtProductRequest.getCartProductId();
            CartProductMaster cartArtProductToUpdate = null;
            for (CartProductMaster cartProductMaster : orderMaster.getCartProductMaster()) {
                if (cartProductMaster.getCartProductId().equals(cartArtProductIdToUpdate)) {
                    cartArtProductToUpdate = cartProductMaster;
                    break;
                }
            }
            if (cartArtProductToUpdate != null) {
                cartArtProductToUpdate.setStatus(orderStatusUpdateArtProductRequest.getOrderStatus());
                cartArtProductToUpdate.setUpdatedDate(new Date());
                orderMasterDao.save(orderMaster);
                updateOrderStatusRes.setMsg("Order Status Updated Successfully");
                updateOrderStatusRes.setFlag(true);
            } else {
                updateOrderStatusRes.setMsg("CartArtProduct with the given ID not found in the Order");
                updateOrderStatusRes.setFlag(false);
            }
        } else {
            updateOrderStatusRes.setMsg("Order not found");
            updateOrderStatusRes.setFlag(false);
        }
        return updateOrderStatusRes;
    }




//    public Boolean createOrderMasters(OrderMasterRequest orderMasterRequest) {
//        System.out.println("  OrderMasterRequest ="+orderMasterRequest.toString());
//
//        Boolean flag = false;
//        List<CartArtFrameMaster> list = new ArrayList<>();
//        List<CartProductMaster> list2 = new ArrayList<>();
//        //change
//        List<CartAdminArtProductMaster> list3=new ArrayList<>();
//
//        List<String> cartProductIdsToDelete = new ArrayList<>();
//        List<String> cartArtFrameIdsToDelete = new ArrayList<>();
//        List<String> cartAdminArtProductIdsDelete=new ArrayList<>();
//
//        CartMaster cartMaster=new CartMaster();
//        cartMaster=cartDao.findByUserMaster_UserId(orderMasterRequest.getUserId());
//
//        list=cartMaster.getCartArtFrameMaster();
//        list2=cartMaster.getCartProductMaster();
//        list3=cartMaster.getCartAdminArtProductMaster();
//
//        UserMaster userMaster = new UserMaster();
//         userMaster=userDao.getUserMaster(orderMasterRequest.getUserId());
//
//        OrderMaster orderMaster = new OrderMaster();
//        OrderMaster orderMaster1 = new OrderMaster();
//
//        if(!list.isEmpty())
//        {
//            OrderMaster orderMaster2=new OrderMaster();
//        }
//
////        List<CartMaster> cartMasterList = cartDao.findByUserMasterUserId(orderMasterRequest.getUserId());
////        System.out.println("cartMasterList: " + cartMasterList.size());
//
////        System.out.println("cartMasterList: " + cartMasterList.toString());
//
//
//        for (CartArtFrameMaster cartArtFrameMaster : list) {
//            OrderMaster orderMaster2=new OrderMaster();
//
//            orderMaster2.setTotalQty(cartArtFrameMaster.getQuantity());
//            orderMaster2.setTotalAmount(cartArtFrameMaster.getTotalAmount()*cartArtFrameMaster.getQuantity());
//            orderMaster2.setOrderStatus("Shipping Soon");
//            orderMaster2.setTax(12.0);
//            orderMaster2.setPromoCode(cartMaster.getPromoCode());
//            orderMaster2.setGiftCode(cartMaster.getGiftCode());
//            orderMaster2.setPromoCodeAmount(cartMaster.getPromoCodeAmount());
//            orderMaster2.setGiftCodeAmount(cartMaster.getGiftCodeAmount());
//            orderMaster2.setOrderDate(new Date());
//            orderMaster2.setEstimatedShipping(cartMaster.getShippingMethod().getShippingMethodPrice());
//            orderMaster2.setEstimatedTotal(cartMaster.getShippingMethod().getShippingMethodPrice());
//            orderMaster2.setTaxAmount(orderMaster2.getTotalAmount()*12/100);
//            double orderFinalAmount=orderMaster2.getTotalAmount()+orderMaster2.getTaxAmount()+orderMaster2.getEstimatedShipping();
//            orderMaster2.setFinalAmount(orderFinalAmount);
//            orderMaster2.setOrderUniqueNo(UniqueNumber.generateUniqueNumber());
//            orderMaster2.setGiftCode(cartMaster.getGiftCode());
//            orderMaster2.setPromoCode(cartMaster.getPromoCode());
//
//            List<CartArtFrameMaster> cartArtFrameMasterList=new ArrayList<>();
//            cartArtFrameMasterList.add(cartArtFrameMaster);
//            orderMaster2.setCartArtFrameMaster(cartArtFrameMasterList);
//
//            try
//            {
//                orderMasterDao.save(orderMaster2);
//                flag= true;
//            }catch (Exception e)
//            {
//                e.printStackTrace();
//                return false;
//            }
//        }
//
//        for (CartProductMaster cartProductMaster : list2) {
//            OrderMaster orderMaster2=new OrderMaster();
//
//            orderMaster2.setTotalQty(cartProductMaster.getQuantity());
//            orderMaster2.setTotalAmount(cartProductMaster.getAmount()*cartProductMaster.getQuantity());
//            orderMaster2.setOrderStatus("Shipping Soon");
//            orderMaster2.setTax(12.0);
//            orderMaster2.setPromoCode(cartMaster.getPromoCode());
//            orderMaster2.setGiftCode(cartMaster.getGiftCode());
//            orderMaster2.setPromoCodeAmount(cartMaster.getPromoCodeAmount());
//            orderMaster2.setGiftCodeAmount(cartMaster.getGiftCodeAmount());
//            orderMaster2.setOrderDate(new Date());
//            orderMaster2.setEstimatedShipping(cartMaster.getShippingMethod().getShippingMethodPrice());
//            orderMaster2.setEstimatedTotal(cartMaster.getShippingMethod().getShippingMethodPrice());
//            orderMaster2.setTaxAmount(orderMaster2.getTotalAmount()*12/100);
//            double orderFinalAmount=orderMaster2.getTotalAmount()+orderMaster2.getTaxAmount()+orderMaster2.getEstimatedShipping();
//            orderMaster2.setFinalAmount(orderFinalAmount);
//            orderMaster2.setOrderUniqueNo(UniqueNumber.generateUniqueNumber());
//            orderMaster2.setGiftCode(cartMaster.getGiftCode());
//            orderMaster2.setPromoCode(cartMaster.getPromoCode());
//
//
//            SizeAndPrice sizeAndPrice=new SizeAndPrice();
//            sizeAndPrice=cartProductMaster.getArtProductMaster().getSizeAndPrices().stream().parallel().filter(sizeAndPrice1 -> sizeAndPrice1.getSize().equalsIgnoreCase(cartProductMaster.getSize())).findFirst().get();
//            orderMaster2.setSizeAndPrice(sizeAndPrice);
//
//            List<CartProductMaster> cartProductMasterList=new ArrayList<>();
//            cartProductMasterList.add(cartProductMaster);
//            orderMaster2.setCartProductMaster(cartProductMasterList);
//
//            try
//            {
//                orderMasterDao.save(orderMaster);
//                flag= true;
//            }catch (Exception e)
//            {
//                e.printStackTrace();
//                return false;
//            }
//        }
//
//
//        for (CartAdminArtProductMaster cartAdminArtProductMaster : list3) {
//            OrderMaster orderMaster2=new OrderMaster();
//
//            orderMaster2.setTotalQty(cartAdminArtProductMaster.getQuantity());
//            orderMaster2.setTotalAmount(cartAdminArtProductMaster.getAmount()*cartAdminArtProductMaster.getQuantity());
//            orderMaster2.setOrderStatus("Shipping Soon");
//            orderMaster2.setTax(12.0);
//            orderMaster2.setPromoCode(cartMaster.getPromoCode());
//            orderMaster2.setGiftCode(cartMaster.getGiftCode());
//            orderMaster2.setPromoCodeAmount(cartMaster.getPromoCodeAmount());
//            orderMaster2.setGiftCodeAmount(cartMaster.getGiftCodeAmount());
//            orderMaster2.setOrderDate(new Date());
//            orderMaster2.setEstimatedShipping(cartMaster.getShippingMethod().getShippingMethodPrice());
//            orderMaster2.setEstimatedTotal(cartMaster.getShippingMethod().getShippingMethodPrice());
//            orderMaster2.setTaxAmount(orderMaster2.getTotalAmount()*12/100);
//            double orderFinalAmount=orderMaster2.getTotalAmount()+orderMaster2.getTaxAmount()+orderMaster2.getEstimatedShipping();
//            orderMaster2.setFinalAmount(orderFinalAmount);
//            orderMaster2.setOrderUniqueNo(UniqueNumber.generateUniqueNumber());
//            orderMaster2.setGiftCode(cartMaster.getGiftCode());
//            orderMaster2.setPromoCode(cartMaster.getPromoCode());
//
//            SizeAndPrice sizeAndPrice=new SizeAndPrice();
//            sizeAndPrice=cartAdminArtProductMaster.getAdminArtProductMaster().getSizeAndPrices().stream().parallel().filter(sizeAndPrice1 -> sizeAndPrice1.getSize().equalsIgnoreCase(cartAdminArtProductMaster.getSize())).findFirst().get();
//            orderMaster2.setSizeAndPrice(sizeAndPrice);
//
//            List<CartAdminArtProductMaster> cartAdminArtProductMasterList=new ArrayList<>();
//            cartAdminArtProductMasterList.add(cartAdminArtProductMaster);
//            orderMaster2.setCartAdminArtProductMaster(cartAdminArtProductMasterList);
//            try
//            {
//                orderMasterDao.save(orderMaster2);
//                flag= true;
//            }catch (Exception e)
//            {
//                e.printStackTrace();
//                return false;
//            }
//        }
//
//
//        for (CartMaster cartMaster : cartMasterList) {
//            orderMaster.setTotalQty(cartMaster.getTotalQty());
//            orderMaster.setTotalAmount(cartMaster.getTotalAmount());
//            orderMaster.setOrderStatus("Shipping Soon");
//            orderMaster.setTax(cartMaster.getTax());
//            orderMaster.setPromoCode(cartMaster.getPromoCode());
//            orderMaster.setGiftCode(cartMaster.getGiftCode());
//            orderMaster.setPromoCodeAmount(cartMaster.getPromoCodeAmount());
//            orderMaster.setGiftCodeAmount(cartMaster.getGiftCodeAmount());
//            orderMaster.setOrderDate(new Date());
//            orderMaster.setEstimatedShipping(cartMaster.getEstimateShipping());
//            orderMaster.setEstimatedTotal(cartMaster.getEstimateAmount());
//            orderMaster.setTaxAmount(cartMaster.getTaxAmount());
//            orderMaster.setFinalAmount(cartMaster.getFinalAmount());
//            orderMaster.setOrderUniqueNo(UniqueNumber.generateUniqueNumber());
//            orderMaster.setGiftCode(cartMaster.getGiftCode());
//            orderMaster.setPromoCode(cartMaster.getPromoCode());
//
//            userMaster.setUserId(orderMasterRequest.getUserId());
//            orderMaster.setUserMaster(userMaster);
//            if (orderMasterRequest.getCartArtFrameId() != null && !orderMasterRequest.getCartArtFrameId().isEmpty())
//            {
//                for (String id : orderMasterRequest.getCartArtFrameId()) {
//                    System.out.println("art111.." + id);
//                    CartArtFrameMaster cartArtFrameMaster = cartArtFrameDao.getId(id);
//                    cartArtFrameMaster.setNewOrderUniqueNo(UniqueNumber.generateUniqueNumber());
//                    list.add(cartArtFrameMaster);
//                    cartArtFrameMaster.setStatus("Order Placed");
//                    cartArtFrameDao.save(cartArtFrameMaster);
//                }
//            }
//            if (orderMasterRequest.getCartProductId() != null && !orderMasterRequest.getCartProductId().isEmpty())
//            {
//                for (String id : orderMasterRequest.getCartProductId()) {
//                    System.out.println("art111.." + id);
//                    CartProductMaster cartProductMaster = cartProductDao.getId(id);
//                    cartProductMaster.setCartProductUniqueNo(UniqueNumber.generateUniqueNumber());
//                    list2.add(cartProductMaster);
//                    cartProductMaster.setStatus("Order Placed");
//                    cartProductDao.save(cartProductMaster);
//                }
//            }
//
//
//            // changes
//            if (orderMasterRequest.getCartAdminArtProductId() != null && !orderMasterRequest.getCartAdminArtProductId().isEmpty())
//            {
//                for (String s : orderMasterRequest.getCartAdminArtProductId()) {
//
//                    System.out.println("  admin art Product = "+s );
//                    CartAdminArtProductMaster cartAdminArtProductMaster=new CartAdminArtProductMaster();
//                    cartAdminArtProductMaster=cartAdminArtProductDao.findByCartAdminArtProductId(s);
//                    System.out.println(" cartAdminArtProductMaster id  = "+cartAdminArtProductMaster.getCartAdminArtProductId());
//                    cartAdminArtProductMaster.setCartAdminArtProductUniqueNo(UniqueNumber.generateUniqueNumber());
//                    list3.add(cartAdminArtProductMaster);
//                    cartAdminArtProductMaster.setStatus("Order Placed");
//                    cartAdminArtProductDao.save(cartAdminArtProductMaster);
//                }
//            }
//
////            System.out.println(" list ="+list);
////            System.out.println(" list2 ="+list2);
////            System.out.println(" list3 ="+list3);
//
//            System.out.println(" list ="+list.size());
//            System.out.println(" list2 ="+list2.size());
//            System.out.println(" list3 ="+list3.size());
//
//            Integer cnt = list.size() + list2.size() + list3.size();
//            orderMaster.setTotalCount(cnt);
//            orderMaster.setCartProductMaster(list2);
//            orderMaster.setCartArtFrameMaster(list);
//            //changes
//            orderMaster.setCartAdminArtProductMaster(list3);
//
//            System.out.println(" hii 1");
////            System.out.println("  orderMaster ="+orderMaster.toString());
//
//            try {
//                orderMaster1 = orderMasterDao.save(orderMaster);
////                System.out.println("  save order  orderMaster1="+orderMaster1.toString());
//                System.out.println(" hii 2");
//                flag = true;
//                if (flag) {
//                    UserPromoCodeMaster userPromoCodeMaster = userPromoCodeDao.findByUserMaster_UserIdAndPromoCode(orderMaster1.getUserMaster().getUserId(), orderMaster1.getPromoCode());
//                    if (userPromoCodeMaster!=null) {
//                        userPromoCodeMaster.setStatus("Used");
//                        userPromoCodeDao.save(userPromoCodeMaster);
//                    }
//                    System.out.println(" hii 3");
//
//                    UserGiftCodeMaster userGiftCodeMaster=userGiftCodeDao.findByUserMaster_UserIdAndGiftCode(orderMaster1.getUserMaster().getUserId(), orderMaster1.getGiftCode());
//                    if (userGiftCodeMaster!=null) {
//                        userGiftCodeMaster.setStatus("Used");
//                        userGiftCodeDao.save(userGiftCodeMaster);
//                    }
//
//                    System.out.println(" hii 4");
//                    List<CartProductMaster> cartProductMasterList = cartMaster.getCartProductMaster();
//                    cartProductMasterList.removeIf(cartProductMaster -> cartProductIdsToDelete.contains(cartProductMaster.getCartProductId()));
//
//                    List<CartArtFrameMaster> cartArtFrameMasterList = cartMaster.getCartArtFrameMaster();
//                    cartArtFrameMasterList.removeIf(cartArtFrameMaster -> cartArtFrameIdsToDelete.contains(cartArtFrameMaster.getCartArtFrameId()));
//
//                    //changes
//                    List<CartAdminArtProductMaster> cartAdminArtProductMasterList=cartMaster.getCartAdminArtProductMaster();
//                    cartAdminArtProductMasterList.removeIf(cartAdminArtProductMaster -> cartAdminArtProductIdsDelete.contains(cartAdminArtProductMaster.getCartAdminArtProductId()));
//
//                    System.out.println(" hii 5");
//
//                    cartMaster.setFinalAmount(0.0);
//                    cartMaster.setTotalAmount(0.0);
//                    cartMaster.setEstimateAmount(0.0);
//                    cartMaster.setPromoCodeAmount(0.0);
//                    cartMaster.setGiftCodeAmount(0.0);
//                    cartMaster.setTotalQty(0);
//                    cartMaster.setTaxAmount(0.0);
//                    cartMaster.setStatus("Cart is Empty");
//                    cartMaster.setTotalCount(0);
//                    cartDao.save(cartMaster);
//
//                    System.out.println(" hii 6");
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                flag = false;
//            }
//        }
//        return flag;
//    }



    /*
     @Override
    public Boolean createOrderMaster(OrderMasterRequest orderMasterRequest) {
        Boolean flag = false;
        OrderMaster orderMaster = new OrderMaster();
        OrderMaster orderMaster1 = new OrderMaster();
        UserMaster userMaster = new UserMaster();
        List<CartArtFrameMaster> list = new ArrayList<>();
        List<CartProductMaster> list2 = new ArrayList<>();
        //change
        List<CartAdminArtProductMaster> list3=new ArrayList<>();
        List<String> cartProductIdsToDelete = new ArrayList<>();
        List<String> cartArtFrameIdsToDelete = new ArrayList<>();
        List<String> cartAdminArtProductIdsDelete=new ArrayList<>();


        List<CartMaster> cartMasterList = cartDao.findByUserMasterUserId(orderMasterRequest.getUserId());
        System.out.println("cartMasterList: " + cartMasterList.size());

        for (CartMaster cartMaster : cartMasterList) {
            orderMaster.setTotalQty(cartMaster.getTotalQty());
            orderMaster.setTotalAmount(cartMaster.getTotalAmount());
            orderMaster.setOrderStatus("Shipping Soon");
            orderMaster.setTax(cartMaster.getTax());
            orderMaster.setPromoCode(cartMaster.getPromoCode());
            orderMaster.setGiftCode(cartMaster.getGiftCode());
            orderMaster.setPromoCodeAmount(cartMaster.getPromoCodeAmount());
            orderMaster.setGiftCodeAmount(cartMaster.getGiftCodeAmount());
            orderMaster.setOrderDate(new Date());
            orderMaster.setEstimatedShipping(cartMaster.getEstimateShipping());
            orderMaster.setEstimatedTotal(cartMaster.getEstimateAmount());
            orderMaster.setTaxAmount(cartMaster.getTaxAmount());
            orderMaster.setFinalAmount(cartMaster.getFinalAmount());
            orderMaster.setOrderUniqueNo(UniqueNumber.generateUniqueNumber());
            orderMaster.setGiftCode(cartMaster.getGiftCode());
            orderMaster.setPromoCode(cartMaster.getPromoCode());

            userMaster.setUserId(orderMasterRequest.getUserId());
            orderMaster.setUserMaster(userMaster);
            if (orderMasterRequest.getCartArtFrameId() != null && !orderMasterRequest.getCartArtFrameId().isEmpty()) {
                for (String id : orderMasterRequest.getCartArtFrameId()) {
                    System.out.println("art111.." + id);
                    CartArtFrameMaster cartArtFrameMaster = cartArtFrameDao.getId(id);
                    cartArtFrameMaster.setNewOrderUniqueNo(UniqueNumber.generateUniqueNumber());
                    list.add(cartArtFrameMaster);
                    cartArtFrameMaster.setStatus("Order Placed");
                    cartArtFrameDao.save(cartArtFrameMaster);
                }
            }
            if (orderMasterRequest.getCartProductId() != null && !orderMasterRequest.getCartProductId().isEmpty()) {
                for (String id : orderMasterRequest.getCartProductId()) {
                    System.out.println("art111.." + id);
                    CartProductMaster cartProductMaster = cartProductDao.getId(id);
                    cartProductMaster.setCartProductUniqueNo(UniqueNumber.generateUniqueNumber());
                    list2.add(cartProductMaster);
                    cartProductMaster.setStatus("Order Placed");
                    cartProductDao.save(cartProductMaster);
                }
            }

            // changes
            if(orderMasterRequest.getCartAdminArtProductId()!=null && !orderMasterRequest.getCartAdminArtProductId().isEmpty())
            {
                for (String s : orderMasterRequest.getCartAdminArtProductId()) {
                   CartAdminArtProductMaster cartAdminArtProductMaster=new CartAdminArtProductMaster();
                   cartAdminArtProductMaster=cartAdminArtProductDao.findByCartAdminArtProductId(s);
                   list3.add(cartAdminArtProductMaster);
                   cartAdminArtProductMaster.setStatus("Order Placed");
                   cartAdminArtProductDao.save(cartAdminArtProductMaster);
                }
            }

            Integer cnt = list.size() + list2.size() + list3.size();
            orderMaster.setTotalCount(cnt);
            orderMaster.setCartProductMaster(list2);
            orderMaster.setCartArtFrameMaster(list);
            //changes
            orderMaster.setCartAdminArtProductMaster(list3);

            try {
                orderMaster1 = orderMasterDao.save(orderMaster);
                flag = true;
                if (flag) {
                    UserPromoCodeMaster userPromoCodeMaster = userPromoCodeDao.findByUserMaster_UserIdAndPromoCode(orderMaster1.getUserMaster().getUserId(), orderMaster1.getPromoCode());
                   if (userPromoCodeMaster!=null) {
                       userPromoCodeMaster.setStatus("Used");
                       userPromoCodeDao.save(userPromoCodeMaster);
                   }
                   UserGiftCodeMaster userGiftCodeMaster=userGiftCodeDao.findByUserMaster_UserIdAndGiftCode(orderMaster1.getUserMaster().getUserId(), orderMaster1.getGiftCode());
                    if (userGiftCodeMaster!=null) {
                        userGiftCodeMaster.setStatus("Used");
                        userGiftCodeDao.save(userGiftCodeMaster);
                    }


                    List<CartProductMaster> cartProductMasterList = cartMaster.getCartProductMaster();
                    cartProductMasterList.removeIf(cartProductMaster -> cartProductIdsToDelete.contains(cartProductMaster.getCartProductId()));

                    List<CartArtFrameMaster> cartArtFrameMasterList = cartMaster.getCartArtFrameMaster();
                    cartArtFrameMasterList.removeIf(cartArtFrameMaster -> cartArtFrameIdsToDelete.contains(cartArtFrameMaster.getCartArtFrameId()));

                    //changes
                    List<CartAdminArtProductMaster> cartAdminArtProductMasterList=cartMaster.getCartAdminArtProductMaster();
                    cartAdminArtProductMasterList.removeIf(cartAdminArtProductMaster -> cartAdminArtProductIdsDelete.contains(cartAdminArtProductMaster.getCartAdminArtProductId()));

                    cartMaster.setFinalAmount(0.0);
                    cartMaster.setTotalAmount(0.0);
                    cartMaster.setEstimateAmount(0.0);
                    cartMaster.setPromoCodeAmount(0.0);
                    cartMaster.setGiftCodeAmount(0.0);
                    cartMaster.setTotalQty(0);
                    cartMaster.setTaxAmount(0.0);
                    cartMaster.setStatus("Cart is Empty");
                    cartMaster.setTotalCount(0);
                    cartDao.save(cartMaster);

                }
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
     */


//    public Boolean createOrderMasterD(OrderMasterRequest orderMasterRequest) {
//
//        List list=new ArrayList();
//        list.addAll(orderMasterRequest.getCartArtFrameId());
//        list.addAll(orderMasterRequest.getCartProductId());
//        list.addAll(orderMasterRequest.getCartAdminArtProductId());
//
//
//        OrderMaster orderMaster=new OrderMaster();
//        String uniqNo= String.valueOf(System.currentTimeMillis());
//
//        orderMaster.setOrderUniqueNo(uniqNo);
//        orderMaster.setOrderStatus("Shipping Soon");
////        orderMaster.setOrderPaymentStatus();
//
//        CartMaster cartMaster=new CartMaster();
//        cartMaster=cartDao.findByUserMaster_UserId(orderMasterRequest.getUserId());
//
//
//        orderMaster.setTotalQty(list.size());
//        orderMaster.setOrderDate(new Date());
//        orderMaster.setTotalAmount(cartMaster.getTotalAmount());
//        orderMaster.setTax(cartMaster.getTax());
////        orderMaster.setCoupon();
//        orderMaster.setPromoCode(cartMaster.getPromoCode());
//        orderMaster.setGiftCode(cartMaster.getGiftCode());
//        orderMaster.setEstimatedTotal(cartMaster.getEstimateAmount());
//        orderMaster.setEstimatedShipping(cartMaster.getEstimateShipping());
//        orderMaster.setPromoCodeAmount(cartMaster.getPromoCodeAmount());
//        orderMaster.setGiftCodeAmount(cartMaster.getGiftCodeAmount());
////        orderMaster.setCouponDiscount(cartMaster.getc);
//        orderMaster.setTaxAmount(cartMaster.getTaxAmount());
//        orderMaster.setFinalAmount(cartMaster.getFinalAmount());
//        orderMaster.setTotalCount(cartMaster.getTotalCount());
////
////        orderMaster.setPaymentInformation();
////        orderMaster.setShippingMethod();
////
////        UserMaster userMaster=new UserMaster();
////        userMaster.setUserId(orderMasterRequest.getUserId());
////        orderMaster.setUserMaster(userMaster);
////
////        orderMaster.setCartProductMaster();
////        orderMaster.setCartArtFrameMaster();
////        orderMaster.setCartAdminArtProductMaster();
//
//
//
//
//
//
//
//        try
//        {
//
//
//            return true;
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//            return false;
//        }
//
//    }

}

