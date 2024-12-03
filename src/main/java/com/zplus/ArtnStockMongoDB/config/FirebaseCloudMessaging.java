package com.zplus.ArtnStockMongoDB.config;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FirebaseCloudMessaging {

    public void sendPushNotification(String token, String fcmDesc, String fcmTitle, String fcmDate ){

        String AUTH_KEY_FCM= "AAAAr0vkwoQ:APA91bGfaKI6iNSN1U2m1kI4o0CHEVOVwYGHyCQRoN3dmD9m2F8Jhmb-S4D9SlWHE84xFKOay2l9tXKFByrJa97OYcBk4PaCCQ52K_XoGhqyF3DICz1b90CWAz7UnDWua9Er_CqLvSR0" +
                "";

        final String API_URL_FCM="https://fcm.googleapis.com/fcm/send";
        try {
            System.out.println("1");
            String authKey = AUTH_KEY_FCM;
            String FMCurl = API_URL_FCM;

            URL url =new URL(FMCurl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            System.out.println("2");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "key="+authKey);
            conn.setRequestProperty("Content-Type", "application/json");


            JSONObject json = new JSONObject();
            //json.put("to", userDeviceIdKey.trim());
            System.out.println("3");
            JSONObject info=new JSONObject();
            json.put("to",token);
            info.put("title",fcmTitle);
            info.put("body",fcmDesc);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm a");
            LocalDateTime now = LocalDateTime.now();
            String time=now.toString();
            System.out.println("Sachin"+time);
            System.out.println(dtf.format(now));

            String time1=dtf.format(now);
            System.out.println("TIme 1"+time1);
            info.put("time",time1);

            json.put("data", info);

            OutputStreamWriter wr=new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            conn.getInputStream();
            //json.put("to", "aUniqueKey");
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("5");
        }
    }
}

