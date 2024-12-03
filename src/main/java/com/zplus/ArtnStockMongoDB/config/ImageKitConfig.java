package com.zplus.ArtnStockMongoDB.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.Arrays;

@org.springframework.context.annotation.Configuration
public class ImageKitConfig {

    @Value("${publicKey}")
    private String publicKey;

    @Value("${privateKey}")
    private String privateKey;

    @Value("${urlEndpoint}")
    private String urlEndpoint;

//    private final String publicKey = "public_9psnRj17qUZRpu9Ig+2sgwkf9Mg";
//    private final String privateKey = "private_SeeywUEWems+jmix/pvLTjhnkmI";
//    private final String urlEndpoint = "https://ik.imagekit.io/kz6vwng9bi/";

    @Bean
    public ImageKit imageKit() {
        Configuration config = new Configuration(publicKey, privateKey, urlEndpoint);
        ImageKit imageKit = ImageKit.getInstance();
        imageKit.setConfig(config);
        return imageKit;
    }
//    @Bean
//    private ImageKit imageKit() {
//        Configuration config = new Configuration(publicKey, privateKey, urlEndpoint);
//        return new ImageKit(config);
//    }
}