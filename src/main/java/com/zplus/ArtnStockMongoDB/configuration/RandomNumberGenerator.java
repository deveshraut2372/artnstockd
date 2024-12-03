package com.zplus.ArtnStockMongoDB.configuration;



import java.util.Random;

public class RandomNumberGenerator {
  public  static Integer getNumber()
    {
        Random rnd = new Random();
        int number = rnd.nextInt(9999);

        int otp = (int) Math.round(Math.random() * (1000 - 9999+ 1) + 9999);
//        String otp= new DecimalFormat("0000").format(new Random().nextInt(9999));
        // this will convert any number sequence into 6 character.
        return Integer.valueOf(otp);
    }


//    public static void main(String... args){
//
//        System.out.println(RandomNumberGenerator.OTP());
//    }
}
