package com.zplus.ArtnStockMongoDB.configuration;
import java.util.UUID;

public class UniqueNumber {



    public static String generateUniqueNumber() {
        UUID uuid = UUID.randomUUID();
        String uniqueNumber = uuid.toString();
        System.out.println("Unique Number: " + uniqueNumber);

        // 32-bit representation
        UUID uuid1 = UUID.fromString(uniqueNumber);
        long mostSignificantBits = uuid1.getMostSignificantBits();
        int intValue = (int) mostSignificantBits;

        String prefix = "ANS";
        String result = prefix + intValue;
        System.out.println(result);
        String output = result.replace("-", "");
        System.out.println(output);
        return output;
    }
}
