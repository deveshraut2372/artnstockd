package com.zplus.ArtnStockMongoDB.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController@CrossOrigin("*")
public class PaymentIntegrationController {
    @Value("${rzp_key_id}")
    private String keyId;

    @Value("${rzp_key_secret}")
    private String secret;

    @GetMapping("/payment/{amount}")
    public String Payment(@PathVariable String amount) throws RazorpayException {

        double amt= Double.parseDouble(amount);
        int amt1 = (int) amt;
        RazorpayClient razorpayClient = new RazorpayClient(keyId, secret);
        JSONObject orderRequest = new JSONObject();

        orderRequest.put("amount", amt1);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_receipt_11");

        Order order = razorpayClient.Orders.create(orderRequest);
        System.out.println("aa  "+order.toString());
        String orderId = order.get("id");

        System.out.println(orderId+"  orderId");
        return orderId;
    }
}
