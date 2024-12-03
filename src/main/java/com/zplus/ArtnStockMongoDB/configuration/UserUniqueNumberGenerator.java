package com.zplus.ArtnStockMongoDB.configuration;


import java.util.Locale;

public class UserUniqueNumberGenerator {



    public String usingMath(String str) {



        //  String alphabetsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //   String alphabetsInLowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        // create a super set of all characters
        String allCharacters = /*alphabetsInLowerCase + alphabetsInUpperCase +*/ numbers;
        // initialize a string to hold result
        StringBuffer randomString = new StringBuffer();
        // loop for 10 times
        for (int i = 0; i < 4; i++) {
            // generate a random number between 0 and length of all characters
            int randomIndex = (int) (Math.random() * allCharacters.length());
            // retrieve character at index and add it to result
            randomString.append(allCharacters.charAt(randomIndex));
        }
//        String initials = "";
//        for (String s : fullname.split(" ")) {
//            initials+=s.charAt(0);
//        }
      String pin=str.length() > 2 ? str.substring(str.length() - 2) : str;
        System.out.println("ssssssssssssssssssssss "+pin);
        return pin.toUpperCase(Locale.ROOT)+randomString;
    }

}
