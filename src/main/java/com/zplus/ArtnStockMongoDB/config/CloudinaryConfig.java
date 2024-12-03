    package com.zplus.ArtnStockMongoDB.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    @Configuration
    public class CloudinaryConfig {

        @Bean
        public Cloudinary cloudinary() {
            // Replace "cloud_name", "api_key", and "api_secret" with your Cloudinary credentials
            return new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "artnstockimg",
                    "api_key", "632591959119914",
                    "api_secret", "jp4n7VfrNS_cBjwuV0AUiAFZdQ4"));
        }
    }
