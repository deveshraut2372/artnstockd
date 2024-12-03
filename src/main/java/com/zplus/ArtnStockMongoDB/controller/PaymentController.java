package com.zplus.ArtnStockMongoDB.controller;

import com.google.gson.Gson;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.zplus.ArtnStockMongoDB.configuration.razorPay.RazorPay;
import com.zplus.ArtnStockMongoDB.configuration.razorPay.Response;
import com.zplus.ArtnStockMongoDB.dto.req.PaymentReqDto;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@CrossOrigin(origins = "*")
public class PaymentController {

    private static Gson gson = new Gson();
    private RazorpayClient client;

    public PaymentController() throws RazorpayException {
        this.client =  new RazorpayClient(SECRET_ID, SECRET_KEY);
    }
   private static final String SECRET_ID = "rzp_test_HCTUbGUU3bXXQ6";
   private static final String SECRET_KEY = "DRTmi8DXC3M3WUQonV7UizQj";


    @PostMapping("/createPayment")
    public ResponseEntity<String> createOrder(@RequestBody PaymentReqDto paymentReqDto) {

        try {

            Order order = createRazorPayOrder(paymentReqDto.getAmount());
            System.out.println("Order"+order.toString());
            RazorPay razorPay = getRazorPay((String)order.get("id"), paymentReqDto);
            System.out.println("RazorPay"+razorPay.toString());
            System.out.println("RazorPay.........."+razorPay.getApplicationFee());
            return new ResponseEntity<String>(gson.toJson(getResponse(razorPay, 200)),
                    HttpStatus.OK);
        } catch (RazorpayException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>(gson.toJson(getResponse(new RazorPay(), 500)),
                HttpStatus.EXPECTATION_FAILED);
    }
    private Response getResponse(RazorPay razorPay, int statusCode) {
        Response response = new Response();
        response.setStatusCode(statusCode);
        response.setRazorPay(razorPay);
        return response;
    }
    private RazorPay getRazorPay(String orderId, PaymentReqDto paymentReqDto) {

        RazorPay razorPay = new RazorPay();
        razorPay.setApplicationFee(convertRupeeToPaise(paymentReqDto.getAmount()));
        razorPay.setCustomerName(paymentReqDto.getCustomerFullName());
        razorPay.setCustomerEmail(paymentReqDto.getCustomerMail());
        razorPay.setMerchantName("AuctionBanks");
        razorPay.setPurchaseDescription("OFEER FORGB");
        razorPay.setRazorpayOrderId(orderId);
        razorPay.setSecretKey(SECRET_ID);
        razorPay.setImageURL("https://s3.ap-south-1.amazonaws.com/zpluscyber/onlineschool/1598002560797-logo.png");
        razorPay.setTheme("#00B5D1");
        razorPay.setNotes("notes 1");

        return razorPay;
    }

    private Order createRazorPayOrder(String amount) throws RazorpayException {

        JSONObject options = new JSONObject();
        options.put("amount", convertRupeeToPaise(amount));
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        options.put("payment_capture", true); // You can enable this if you want to do Auto Capture.
        return client.Orders.create(options);
    }

    private String convertRupeeToPaise(String paise) {
        BigDecimal b = new BigDecimal(paise);
        BigDecimal value = b.multiply(new BigDecimal("100"));
        return value.setScale(0, RoundingMode.UP).toString();

    }

    @GetMapping("/paymentstatus/{razorpayOrderId}")
    @ResponseBody
    public ResponseEntity getOrder(@PathVariable String razorpayOrderId) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(SECRET_ID, SECRET_KEY);
        Order order = null;
        try {
             order = razorpay.Orders.fetch(razorpayOrderId);
             String s = order.get("status");
            System.out.println("status"+s);
        } catch (RazorpayException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity(order,HttpStatus.OK);
    }
}
