//package com.zplus.ArtnStockMongoDB.configuration.Firebase;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthException;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.google.firebase.messaging.Message;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value = "/api/notifications")
//public class NotificationController{
//
////     Created By Devesh
//    @PostMapping("/send")
//    public String sendNotification(@RequestParam String token,@RequestParam String title,@RequestParam String body) throws FirebaseMessagingException {
//        Message message=Message.builder()
//                .setToken(token)
//                .putData("title",title)
//                .putData("body",body)
//                .build();
//
//        String response= FirebaseMessaging.getInstance().send(message);
//        return response;
//    }
//
//    @GetMapping("/createCustomToken")
//    public String createCustomToken(@RequestParam String uid) {
//        try {
//            String customToken = FirebaseAuth.getInstance().createCustomToken(uid);
//            System.out.println("  customer Token ="+customToken);
//            return customToken;
//        } catch (FirebaseAuthException e) {
//            e.printStackTrace();
//            return "Error creating custom token: " + e.getMessage();
//        }
//    }
//
//
//}
