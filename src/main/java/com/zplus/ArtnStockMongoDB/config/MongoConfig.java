//package com.zplus.ArtnStockMongoDB.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
//import org.springframework.data.mongodb.core.convert.CustomConversions;
//
//import java.util.Arrays;
//
//@Configuration
//public class MongoConfig extends AbstractMongoClientConfiguration {
//    @Override
//    protected String getDatabaseName() {
//        return "artnstock_database";
//    }
//
//    @Override
//    public CustomConversions customConversions() {
//        return new CustomConversions(Arrays.asList(new ArrayListToStringConverter()));
//    }
//}
//
