package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.configuration.UniqueNumber;
import com.zplus.ArtnStockMongoDB.dao.*;
import com.zplus.ArtnStockMongoDB.dto.req.AddToCartProductRequest;
import com.zplus.ArtnStockMongoDB.dto.req.AdminArtProductReq;
import com.zplus.ArtnStockMongoDB.dto.req.CartAdminArtProductRequest;
import com.zplus.ArtnStockMongoDB.dto.req.CartDeleteReq;
import com.zplus.ArtnStockMongoDB.dto.res.CartAdminArtProductRes;
import com.zplus.ArtnStockMongoDB.dto.res.ResDto;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.CartAdminArtProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartAdminArtProductServiceImpl implements CartAdminArtProductService {

    @Autowired
    private CartAdminArtProductDao cartAdminArtProductDao;


    @Autowired
    private UserDao userdao;

    @Autowired
    private ShippingMethodDao shippingMethodDao;

    @Autowired
    private AdminArtProductMasterDao adminArtProductMasterDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CartProductDao cartProductDao;

    @Autowired
    private CartArtFrameDao cartArtFrameDao;


    @Override
    public CartAdminArtProductRes saveCartAdminArtProduct(AdminArtProductReq adminArtProductReq) {
        CartAdminArtProductRes cartAdminArtProductRes=new CartAdminArtProductRes();

        Boolean flag = false;
        double amount = 0.0;
        double cartPrice=0.0;

        //
//        ArtProductMaster artProductMaster = new ArtProductMaster();
//        if(addToCartProductRequest.getArtProductMaster()!=null) {
//            artProductMaster= artProductMasterDao.getArtProductMaster(addToCartProductRequest.getArtProductMaster().getArtProductId());
//        }
        //
//        ArtProductMaster artProductMaster = artProductMasterDao.getArtProductMaster(addToCartProductRequest.getArtProductMaster().getArtProductId());

        UserMaster userMaster = userdao.getUserMaster(adminArtProductReq.getUserId());

        ShippingMethod shippingMethod = shippingMethodDao.findByShippingMethodName("Standard");

        // changes  admin_artproductMaster
        AdminArtProductMaster adminArtProductMaster = new AdminArtProductMaster();
        if(adminArtProductReq.getAdminArtProductId()!=null) {
            adminArtProductMaster = adminArtProductMasterDao.findByAdminArtProductId(adminArtProductReq.getAdminArtProductId()).get();
        }
        //

        CartMaster cartMaster = cartDao.findByUserMaster_UserId(adminArtProductReq.getUserId());
//        System.out.println(cartMaster.getCartArtFrameMaster());

        CartAdminArtProductMaster cartAdminArtProductMaster=new CartAdminArtProductMaster();
        cartAdminArtProductMaster.setAdminArtproductName(adminArtProductReq.getAdminArtProductName());
        cartAdminArtProductMaster.setCartAdminArtProductNo(UniqueNumber.generateUniqueNumber());
        cartAdminArtProductMaster.setQuantity(adminArtProductReq.getQuantity());
        cartAdminArtProductMaster.setStatus("Incart");
        cartAdminArtProductMaster.setCartAdminArtProductUniqueNo(UniqueNumber.generateUniqueNumber());
        cartAdminArtProductMaster.setUserMaster(userMaster);
        cartAdminArtProductMaster.setCartMaster(cartMaster);
        cartAdminArtProductMaster.setAdminArtProductMaster(adminArtProductMaster);


//        cartAdminArtProductMaster.setSizeAndPrices(adminArtProductReq.getAdminArtProductRequest().getSizeAndPrices());
//       cartAdminArtProductMaster.setImages(adminArtProductReq.getAdminArtProductRequest().getImages());
//        cartAdminArtProductMaster.setImgUrl(adminArtProductReq.getAdminArtProductRequest().getImages().getImage());

        cartAdminArtProductMaster.setImages(adminArtProductReq.getAdminArtProductRequest().getImages());
        cartAdminArtProductMaster.setImgUrl(adminArtProductReq.getAdminArtProductRequest().getImages().getImage());
        cartAdminArtProductMaster.setType("cartAdminArtProduct");

//        CartProductMaster cartProductMaster = new CartProductMaster();
//        cartProductMaster.setProductName(addToCartProductRequest.getProductName());
//        cartProductMaster.setCartProductNo(UniqueNumber.generateUniqueNumber());
//        cartProductMaster.setQuantity(addToCartProductRequest.getQuantity());
//        cartProductMaster.setStatus("Incart");
//        cartProductMaster.setCartProductUniqueNo(UniqueNumber.generateUniqueNumber());
//        cartProductMaster.setUserMaster(userMaster);
////        cartProductMaster.setArtProductMaster(artProductMaster);
//        cartProductMaster.setCartMaster(cartMaster);
////        cartProductMaster.setAdminArtProductMaster(adminArtProductMaster);
//
//        // changes
//        cartProductMaster.setAdminArtProductMaster(adminArtProductMaster);
        //

//        System.out.println(cartMaster);


        List<SizeAndPrice> sizeAndPriceList1=adminArtProductMaster.getSizeAndPrices().stream().parallel().filter(sizeAndPrice ->  adminArtProductReq.getSize().equalsIgnoreCase(sizeAndPrice.getSize())).collect(Collectors.toList());;
        SizeAndPrice sizeAndPrice=sizeAndPriceList1.get(0);



//        for (SizeAndPrice sizeAndPrice : adminArtProductMaster.getSizeAndPrices()) {
            Double rate = sizeAndPrice.getSellPrice();

            if (sizeAndPrice.getSize().equals(adminArtProductReq.getAdminArtProductRequest().getSizeAndPrices().getSize())) {
                System.out.println("------------------------------------------------------->> I am in Size and price loop ");
                cartAdminArtProductMaster.setSize(adminArtProductReq.getSize());
                cartAdminArtProductMaster.setRate(rate);
//                cartProductMaster.setSize(addToCartProductRequest.getSize());
//                cartProductMaster.setRate(rate);

                ArtMaster artMaster= adminArtProductMaster.getArtMaster();

                Double artprice=0.0;
//                artprice=artMaster.getFinalArtPrice();
//                        artMaster.getPrice();

                amount = adminArtProductReq.getQuantity() * rate;
                amount=amount+artprice;
                cartPrice=rate+artprice;
                System.out.println("------------------------------------------------------->> I am in Size and price loop value of amount " + amount);
            }
//        }

//        DecimalFormat df = new DecimalFormat("#.00");
//        amount = Double.valueOf(df.format(amount));
//        cartPrice = Double.valueOf(df.format(cartPrice));

        cartAdminArtProductMaster.setAmount(amount);
        cartAdminArtProductMaster.setCartArtPrice(cartPrice);
        cartAdminArtProductMaster.setCartMaster(cartMaster);
//        cartProductMaster.setAmount(amount);

        try {
            CartAdminArtProductMaster cam=cartAdminArtProductDao.save(cartAdminArtProductMaster);

            List<CartAdminArtProductMaster> cartAdminArtProductMasterList=cartAdminArtProductDao.findByStatusAndUserMaster_UserId("Incart", cam.getUserMaster().getUserId());

            System.out.println(" cartAdminArtProductMasterList size = "+cartAdminArtProductMasterList.size());
//
//            System.out.println("list------------------------------------------------------------->>" + cartProductMasters.size());
            Integer totalQty = 0;
            Double totalAmount = 0.0;

            if (cartMaster != null) {
                System.out.println("done.......");
                List<CartProductMaster> list = new ArrayList<>();
                List<CartAdminArtProductMaster> cartAdminArtProductMasterList1=new ArrayList<>();

                for (CartAdminArtProductMaster cartAdminArtProductMaster1 : cartAdminArtProductMasterList) {
                    System.out.println("test 1");
                    if (cartMaster.getCartAdminArtProductMaster() != null) {
                        System.out.println("test 2");
                        totalAmount = totalAmount + cartAdminArtProductMaster1.getAmount();
                        totalQty = totalQty + cartAdminArtProductMaster1.getQuantity();
                        System.out.println("cart master quatity " + cartAdminArtProductMaster1.getQuantity());
                    } else {
                        System.out.println("test 3");
                        totalAmount = totalAmount + cam.getAmount();
                        totalQty = totalQty + cam.getQuantity();
                    }
                    cartAdminArtProductMasterList1.add(cartAdminArtProductMaster1); // Add the updated CartArtFrameMaster object to the new list
                }

                cartMaster.setStyle(adminArtProductReq.getStyle());
                cartMaster.setShippingMethod(shippingMethod);
                cartMaster.setUserMaster(userMaster);
                cartMaster.setCartAdminArtProductMaster(cartAdminArtProductMasterList1);
//                cartMaster.setCartProductMaster(list);
                cartMaster.setTotalCount(cartAdminArtProductMasterList1.size() + cartMaster.getCartAdminArtProductMaster().size());
                cartMaster.setStatus("Active");
                cartMaster.setCartDate(new Date());
                cartMaster.setEstimateShipping(cartMaster.getEstimateShipping()+(shippingMethod.getShippingMethodPrice()*adminArtProductReq.getQuantity()));

                CartMaster cartMaster1 = cartDao.save(cartMaster);

                System.out.println("cartMaster1" + cartMaster1.getEstimateAmount());
                getCartMaster(adminArtProductReq.getUserId(), cartMaster1.getCartId());
                flag = true;

                cartAdminArtProductRes.setCartAdminArtProductMaster(cam);
                cartAdminArtProductRes.setCartMaster(cam.getCartMaster());
                cartAdminArtProductRes.setFlag(true);
            }


//            if (cartMaster != null) {
//                System.out.println("done.......");
//                List<CartProductMaster> list = new ArrayList<>();
//                for (CartProductMaster cartProductMaster1 : cartProductMasters) {
//                    System.out.println("test 1");
//                    if (cartMaster.getCartProductMaster() != null) {
//                        System.out.println("test 2");
//                        totalAmount = totalAmount + cartProductMaster1.getAmount();
//                        totalQty = totalQty + cartProductMaster1.getQuantity();
//                        System.out.println("cart master quatity " + cartProductMaster1.getQuantity());
//                    } else {
//                        System.out.println("test 3");
//                        totalAmount = totalAmount + cm.getAmount();
//                        totalQty = totalQty + cm.getQuantity();
//                    }
//                    list.add(cartProductMaster1); // Add the updated CartArtFrameMaster object to the new list
//                }
//                cartMaster.setStyle(addToCartProductRequest.getStyle());
//                cartMaster.setShippingMethod(shippingMethod);
//                cartMaster.setUserMaster(userMaster);
//                cartMaster.setCartProductMaster(list);
//                cartMaster.setTotalCount(list.size() + cartMaster.getCartArtFrameMaster().size());
//                cartMaster.setStatus("Active");
//                cartMaster.setCartDate(new Date());
//                CartMaster cartMaster1 = cartDao.save(cartMaster);
//
//                System.out.println("cartMaster1" + cartMaster1.getEstimateAmount());
//                getCartMaster(addToCartProductRequest.getUserId(), cartMaster1.getCartId());
//                flag = true;
//            }

        } catch (Exception e) {
            e.printStackTrace();
            cartAdminArtProductRes.setFlag(false);
            cartAdminArtProductRes.setCartAdminArtProductMaster(new CartAdminArtProductMaster());
            cartAdminArtProductRes.setCartMaster(cartMaster);
            flag = false;
        }

        return cartAdminArtProductRes;
//        return flag;
    }




    @Override
    public Boolean IncreaseCartQty(String cartAdminArtProductId) {

        try {
            CartAdminArtProductMaster cartAdminArtProductMaster = new CartAdminArtProductMaster();
            Optional<CartAdminArtProductMaster> cartAdminArtProductMaster1 = cartAdminArtProductDao.findById(cartAdminArtProductId);
            cartAdminArtProductMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, cartAdminArtProductMaster));

            AdminArtProductMaster adminArtProductMaster = new AdminArtProductMaster();
            adminArtProductMaster = cartAdminArtProductMaster.getAdminArtProductMaster();

            Double Amount = 0.0;
            Amount = adminArtProductMaster.getArtMaster().getPrice();

            cartAdminArtProductMaster.setAmount(cartAdminArtProductMaster.getAmount() + cartAdminArtProductMaster.getCartArtPrice());
            cartAdminArtProductMaster.setQuantity(cartAdminArtProductMaster.getQuantity()+1);
            cartAdminArtProductDao.save(cartAdminArtProductMaster);

            for (CartAdminArtProductMaster artProductMaster : cartAdminArtProductMaster.getCartMaster().getCartAdminArtProductMaster()) {
                if(artProductMaster.getCartAdminArtProductId().equalsIgnoreCase(cartAdminArtProductMaster.getCartAdminArtProductId()))
                {
                    artProductMaster.setAmount(cartAdminArtProductMaster.getAmount());
                    artProductMaster.setQuantity(cartAdminArtProductMaster.getQuantity());
                }
            }
            cartDao.save(cartAdminArtProductMaster.getCartMaster());

            getCartMaster(cartAdminArtProductMaster.getUserMaster().getUserId(), cartAdminArtProductMaster.getCartMaster().getCartId());
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean DecreaseCartQty(String cartAdminArtProductId) {
        try {
            CartAdminArtProductMaster cartAdminArtProductMaster = new CartAdminArtProductMaster();
            Optional<CartAdminArtProductMaster> cartAdminArtProductMaster1 = cartAdminArtProductDao.findById(cartAdminArtProductId);
            cartAdminArtProductMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, cartAdminArtProductMaster));

            AdminArtProductMaster adminArtProductMaster = new AdminArtProductMaster();
            adminArtProductMaster = cartAdminArtProductMaster.getAdminArtProductMaster();

            Double Amount = 0.0;
            Amount = adminArtProductMaster.getArtMaster().getPrice();

            cartAdminArtProductMaster.setAmount(cartAdminArtProductMaster.getAmount() - cartAdminArtProductMaster.getCartArtPrice());
            cartAdminArtProductMaster.setQuantity(cartAdminArtProductMaster.getQuantity()-1);

            cartAdminArtProductDao.save(cartAdminArtProductMaster);

            for (CartAdminArtProductMaster artProductMaster : cartAdminArtProductMaster.getCartMaster().getCartAdminArtProductMaster()) {
                if(artProductMaster.getCartAdminArtProductId().equalsIgnoreCase(cartAdminArtProductMaster.getCartAdminArtProductId()))
                {
                    artProductMaster.setAmount(cartAdminArtProductMaster.getAmount());
                    artProductMaster.setQuantity(cartAdminArtProductMaster.getQuantity());
                }
            }
            cartDao.save(cartAdminArtProductMaster.getCartMaster());

            getCartMaster(cartAdminArtProductMaster.getUserMaster().getUserId(), cartAdminArtProductMaster.getCartMaster().getCartId());
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteCartAdminProduct(String cartAdminArtProductId)
    {
        try {
            CartAdminArtProductMaster cartAdminArtProductMaster=new CartAdminArtProductMaster();
            cartAdminArtProductMaster=cartAdminArtProductDao.findById(cartAdminArtProductId).get();
            System.out.println(" cartAdminArtProductMaster = "+cartAdminArtProductMaster.toString());
            for (CartAdminArtProductMaster artProductMaster : cartAdminArtProductMaster.getCartMaster().getCartAdminArtProductMaster())
            {
                if(artProductMaster.getCartAdminArtProductId().equalsIgnoreCase(cartAdminArtProductMaster.getCartAdminArtProductId()))
                {
                    cartAdminArtProductMaster.getCartMaster().getCartAdminArtProductMaster().remove(cartAdminArtProductMaster);
                }
            }
            cartAdminArtProductDao.deleteById(cartAdminArtProductId);
            cartDao.save(cartAdminArtProductMaster.getCartMaster());
            getCartMaster(cartAdminArtProductMaster.getUserMaster().getUserId(), cartAdminArtProductMaster.getCartMaster().getCartId());
            return true;
            }catch (Exception e)
            {
            e.printStackTrace();
            return false;
            }
        }

    @Transactional
    public void getCartMaster(String userId, String cartId) {
        System.out.println("i am in art frame master get cart master");
        ResDto resDto = new ResDto();
        //
        ResDto resDtoCartProduct = new ResDto();
        resDtoCartProduct.setTotalAmount(0.0);
        ResDto resDtoCartArtFrame=new ResDto();
        resDtoCartArtFrame.setTotalAmount(0.0);
        ResDto resDtoCartAdminArtFrame=new ResDto();
        resDtoCartAdminArtFrame.setTotalAmount(0.0);

        //
        Integer cnt2 = 0;
        Integer cnt1 = 0;
        Integer cnt = 0;

        Double productsValue=0.0;

        CartMaster cartMaster = cartDao.findByCartId(cartId).get();
        UserMaster userMaster = userdao.getUserMaster(userId);

        ShippingMethod shippingMethod= cartMaster.getShippingMethod();
        Double shippingCharges=0.0;

        if(cartMaster.getCartAdminArtProductMaster()!=null)
        {
            List<CartAdminArtProductMaster> cartAdminArtProductMasterList = cartAdminArtProductDao.findByStatusAndUserMaster_UserId("Incart", userId);
            if (cartAdminArtProductMasterList != null) {
                List<CartAdminArtProductMaster> list = new ArrayList<>();

                //
                resDtoCartAdminArtFrame.setTotalAmount(cartAdminArtProductMasterList.stream().parallel().mapToDouble(CartAdminArtProductMaster::getAmount).sum());
                resDtoCartAdminArtFrame.setTotalQty(cartAdminArtProductMasterList.stream().parallel().mapToInt(CartAdminArtProductMaster::getQuantity).sum());
                productsValue=resDto.getTotalAmount();
                shippingCharges=shippingCharges+(shippingMethod.getShippingMethodPrice()*resDtoCartAdminArtFrame.getTotalQty());
                //

//                for (CartAdminArtProductMaster cartAdminArtProductMaster1 : cartAdminArtProductMasterList) {
//                    resDto.setTotalAmount(resDto.getTotalAmount() + cartAdminArtProductMaster1.getAmount());
//                    resDto.setTotalQty(resDto.getTotalQty() + cartAdminArtProductMaster1.getQuantity());
//                    productsValue=productsValue+cartAdminArtProductMaster1.getAmount();
//                    shippingCharges=shippingCharges+(shippingMethod.getShippingMethodPrice()*cartAdminArtProductMaster1.getQuantity());
//                    System.out.println("  CratProduct Value ="+productsValue );
//                }

                System.out.println("  CratProduct Value 111 ="+productsValue );
                cnt1 = cartMaster.getCartAdminArtProductMaster().size();
            }
        } else {
            cnt2 = 0;
        }


        if (cartMaster.getCartProductMaster() != null) {
            List<CartProductMaster> cartProductMasters = cartProductDao.findByStatusAndUserMaster_UserId("Incart", userId);
            if (cartProductMasters != null) {
                List<CartProductMaster> list = new ArrayList<>();

                //
                resDtoCartProduct.setTotalAmount(cartProductMasters.stream().parallel().mapToDouble(CartProductMaster::getAmount).sum());
                resDtoCartProduct.setTotalQty(cartProductMasters.stream().parallel().mapToInt(CartProductMaster::getQuantity).sum());
                productsValue=resDto.getTotalAmount();
                shippingCharges=shippingCharges+(shippingMethod.getShippingMethodPrice()*resDtoCartProduct.getTotalQty());
                //


//                for (CartProductMaster cartProductMaster1 : cartProductMasters) {
//                    resDto.setTotalAmount(resDto.getTotalAmount() + cartProductMaster1.getAmount());
//                    resDto.setTotalQty(resDto.getTotalQty() + cartProductMaster1.getQuantity());
//                    productsValue=productsValue+cartProductMaster1.getAmount();
//                    shippingCharges=shippingCharges+(shippingMethod.getShippingMethodPrice()*cartProductMaster1.getQuantity());
//                    System.out.println("  CratProduct Value ="+productsValue );
//                }


                System.out.println("  CratProduct Value 111 ="+productsValue );
                cnt1 = cartMaster.getCartArtFrameMaster().size();
            }
        } else {
            cnt1 = 0;
        }

        if (cartMaster.getCartArtFrameMaster() != null) {
            List<CartArtFrameMaster> cartArtFrameMasters = cartArtFrameDao.findByStatusAndUserMaster_UserId("Incart", userId);
            List<CartArtFrameMaster> list = new ArrayList<>();
            if (cartArtFrameMasters != null) {
                System.out.println("done.......");

                //
                resDtoCartArtFrame.setTotalAmount(cartArtFrameMasters.stream().parallel().mapToDouble(CartArtFrameMaster::getAmount).sum());
                resDtoCartArtFrame.setTotalQty(cartArtFrameMasters.stream().parallel().mapToInt(CartArtFrameMaster::getQuantity).sum());
                productsValue=resDto.getTotalAmount();
                shippingCharges=shippingCharges+(shippingMethod.getShippingMethodPrice()*resDtoCartArtFrame.getTotalQty());
                //



//                for (CartArtFrameMaster cartArtFrameMaster1 : cartArtFrameMasters) {
//                    resDto.setTotalAmount(resDto.getTotalAmount() + cartArtFrameMaster1.getAmount());
//                    resDto.setTotalQty(resDto.getTotalQty() + cartArtFrameMaster1.getQuantity());
//                    shippingCharges=shippingCharges+(shippingMethod.getShippingMethodPrice()*cartArtFrameMaster1.getQuantity());
//                }

                cnt = cartMaster.getCartArtFrameMaster().size();
            }
        } else {
            cnt = 0;
        }
        cartMaster.setTotalCount(cnt1 + cnt + cnt2);
        cartMaster.setTotalAmount(resDtoCartAdminArtFrame.getTotalAmount()+resDtoCartArtFrame.getTotalAmount()+resDtoCartProduct.getTotalAmount());
        cartMaster.setTotalQty(resDtoCartAdminArtFrame.getTotalQty()+resDtoCartArtFrame.getTotalQty()+resDtoCartProduct.getTotalQty());
//        cartMaster.setEstimateShipping(5.0);
//        cartMaster.setTotalAmount(resDto.getTotalAmount());
//        cartMaster.setTotalQty(resDto.getTotalQty());
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
        double finalAmount = estimateAmount+estimateShipping;

        System.out.println( "  Total Amount =  cart "+totalAmount);
        System.out.println( "  taxAmount =  cart "+taxAmount);
        System.out.println( "  estimateShipping =  cart "+estimateShipping);
        System.out.println( "  estimateAmount =  cart "+estimateAmount +" ="+totalAmount+"  + "+taxAmount);
        System.out.println( " finalAmount =  cart "+finalAmount+" = "+estimateAmount+""+estimateShipping);

        cartMaster.setTotalAmount(totalAmount);
        cartMaster.setTaxAmount(taxAmount);
        cartMaster.setEstimateShipping(estimateShipping);
        cartMaster.setEstimateAmount(estimateAmount);
        cartMaster.setFinalAmount(finalAmount);
        cartMaster.setProductsValue(productsValue);

//        if(cartMaster.getPromoCode()!=null)
//        {
//            cartMaster.setFinalAmount(cartMaster.getFinalAmount()-cartMaster.getProductsValue());
//        }
        if(cartMaster.getPromoCode()!=null)
        {
            cartMaster.setFinalAmount(cartMaster.getFinalAmount()-cartMaster.getProductsValue());
        }

        if(cartMaster.getGiftCode()!=null)
        {
            cartMaster.setFinalAmount(cartMaster.getFinalAmount()-cartMaster.getProductsValue());
        }

        CartMaster cartMaster1 = cartDao.save(cartMaster);
        System.out.println("cartMaster1 " + cartMaster1.getEstimateAmount());



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



//    @Override
//    public Boolean updateCartAdminArtProduct(AdminArtProductReq adminArtProductReq) {
//
//        Boolean flag=false;
//        double amount=0.0;
//        double cartPrice=0.0;
//
//        UserMaster userMaster=new UserMaster();
//        userMaster=userdao.findByUserId(adminArtProductReq.getUserId()).get();
//
//        ShippingMethod shippingMethod=shippingMethodDao.findByShippingMethodName("Standard");
//
//        AdminArtProductMaster adminArtProductMaster=new AdminArtProductMaster();
//        if(adminArtProductReq.getAdminArtProductId()!=null) {
//            adminArtProductMaster=adminArtProductMasterDao.findByAdminArtProductId(adminArtProductReq.getAdminArtProductId()).get();
//        }
//
//        CartMaster cartMaster=cartDao.findByUserMaster_UserId(userMaster.getUserId());
//
//        CartAdminArtProductMaster cartAdminArtProductMaster=new CartAdminArtProductMaster();
//        cartAdminArtProductMaster.setAdminArtproductName(adminArtProductReq.getAdminArtProductName());
//        cartAdminArtProductMaster.setDescription(adminArtProductReq.getDescription());
//        cartAdminArtProductMaster.setCartAdminArtProductUniqueNo(UniqueNumber.generateUniqueNumber());
//        cartAdminArtProductMaster.setQuantity(adminArtProductReq.getQuantity());
//        cartAdminArtProductMaster.setStatus("Incart");
//        cartAdminArtProductMaster.setUserMaster(userMaster);
//        cartAdminArtProductMaster.setCartMaster(cartMaster);
//        cartAdminArtProductMaster.setAdminArtProductMaster(adminArtProductMaster);
//        cartAdminArtProductMaster.setType("cartAdminArtProduct");
//
//        ProductMaster productMaster=adminArtProductMaster.getProductMaster();
//        cartAdminArtProductMaster.setImages(adminArtProductReq.getAdminArtProductRequest().getImages());
//        cartAdminArtProductMaster.setImgUrl(adminArtProductReq.getAdminArtProductRequest().getImages().getImage());
//
//        for (SizeAndPrice sizeAndPrice : adminArtProductMaster.getSizeAndPrices())
//        {
//            Double rate=sizeAndPrice.getSellPrice();
//            if(adminArtProductReq.getAdminArtProductRequest().getSizeAndPrices().getSize().equalsIgnoreCase(sizeAndPrice.getSize()))
//            {
//                rate=sizeAndPrice.getSellPrice();
//                cartAdminArtProductMaster.setRate(rate);
//                cartAdminArtProductMaster.setSize(sizeAndPrice.getSize());
//
//                ArtMaster artMaster=adminArtProductMaster.getArtMaster();
//                Double artprice=artMaster.getFinalArtPrice();
//                amount = adminArtProductReq.getQuantity() * rate;
//                amount=amount+artprice;
//                cartPrice=rate+artprice;
//            }
//        }
//
//        cartAdminArtProductMaster.setAmount(amount);
//        cartAdminArtProductMaster.setCartArtPrice(cartPrice);
////
////        cartAdminArtProductMaster.setAmount(amount);
////        cartAdminArtProductMaster.setCartArtPrice(cartPrice);
////        cartAdminArtProductMaster.setCartMaster(cartMaster);
//////        cartProductMaster.setAmount(amount);
////
////        try {
////            CartAdminArtProductMaster cam=cartAdminArtProductDao.save(cartAdminArtProductMaster);
//////            CartProductMaster cm = cartProductDao.save(cartProductMaster);
////
//////            List<CartProductMaster> cartProductMasters = cartProductDao.findByStatusAndUserMaster_UserId("Incart", cam.getUserMaster().getUserId());
////
////            List<CartAdminArtProductMaster> cartAdminArtProductMasterList=cartAdminArtProductDao.findByStatusAndUserMaster_UserId("Incart", cam.getUserMaster().getUserId());
////
//////            System.out.println("list------------------------------------------------------------->>" + cartProductMasters.size());
////            Integer totalQty = 0;
////            Double totalAmount = 0.0;
////
////            if (cartMaster != null) {
////                System.out.println("done.......");
////                List<CartProductMaster> list = new ArrayList<>();
////                List<CartAdminArtProductMaster> cartAdminArtProductMasterList1=new ArrayList<>();
////
////                for (CartAdminArtProductMaster cartAdminArtProductMaster1 : cartAdminArtProductMasterList) {
////                    System.out.println("test 1");
////                    if (cartMaster.getCartProductMaster() != null) {
////                        System.out.println("test 2");
////                        totalAmount = totalAmount + cartAdminArtProductMaster1.getAmount();
////                        totalQty = totalQty + cartAdminArtProductMaster1.getQuantity();
////                        System.out.println("cart master quatity " + cartAdminArtProductMaster1.getQuantity());
////                    } else {
////                        System.out.println("test 3");
////                        totalAmount = totalAmount + cam.getAmount();
////                        totalQty = totalQty + cam.getQuantity();
////                    }
////                    cartAdminArtProductMasterList1.add(cartAdminArtProductMaster1); // Add the updated CartArtFrameMaster object to the new list
////                }
////                cartMaster.setStyle(adminArtProductReq.getStyle());
////                cartMaster.setShippingMethod(shippingMethod);
////                cartMaster.setUserMaster(userMaster);
////                cartMaster.setCartAdminArtProductMaster(cartAdminArtProductMasterList1);
//////                cartMaster.setCartProductMaster(list);
////                cartMaster.setTotalCount(cartAdminArtProductMasterList1.size() + cartMaster.getCartAdminArtProductMaster().size());
////                cartMaster.setStatus("Active");
////                cartMaster.setCartDate(new Date());
////                CartMaster cartMaster1 = cartDao.save(cartMaster);
////
////                System.out.println("cartMaster1" + cartMaster1.getEstimateAmount());
////                getCartMaster(adminArtProductReq.getUserId(), cartMaster1.getCartId());
////                flag = true;
////            }
////
////
//////            if (cartMaster != null) {
//////                System.out.println("done.......");
//////                List<CartProductMaster> list = new ArrayList<>();
//////                for (CartProductMaster cartProductMaster1 : cartProductMasters) {
//////                    System.out.println("test 1");
//////                    if (cartMaster.getCartProductMaster() != null) {
//////                        System.out.println("test 2");
//////                        totalAmount = totalAmount + cartProductMaster1.getAmount();
//////                        totalQty = totalQty + cartProductMaster1.getQuantity();
//////                        System.out.println("cart master quatity " + cartProductMaster1.getQuantity());
//////                    } else {
//////                        System.out.println("test 3");
//////                        totalAmount = totalAmount + cm.getAmount();
//////                        totalQty = totalQty + cm.getQuantity();
//////                    }
//////                    list.add(cartProductMaster1); // Add the updated CartArtFrameMaster object to the new list
//////                }
//////                cartMaster.setStyle(addToCartProductRequest.getStyle());
//////                cartMaster.setShippingMethod(shippingMethod);
//////                cartMaster.setUserMaster(userMaster);
//////                cartMaster.setCartProductMaster(list);
//////                cartMaster.setTotalCount(list.size() + cartMaster.getCartArtFrameMaster().size());
//////                cartMaster.setStatus("Active");
//////                cartMaster.setCartDate(new Date());
//////                CartMaster cartMaster1 = cartDao.save(cartMaster);
//////
//////                System.out.println("cartMaster1" + cartMaster1.getEstimateAmount());
//////                getCartMaster(addToCartProductRequest.getUserId(), cartMaster1.getCartId());
//////                flag = true;
////            }
//
////        } catch (Exception e) {
////            e.printStackTrace();
////            flag = false;
////        }
////
////        return flag;
////
//    return false;
//    }

}
