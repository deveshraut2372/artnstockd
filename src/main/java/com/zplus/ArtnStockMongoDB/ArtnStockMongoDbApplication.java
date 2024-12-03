package com.zplus.ArtnStockMongoDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.zplus.ArtnStockMongoDB")
@EnableAsync(proxyTargetClass=true)
@EnableCaching(proxyTargetClass=true)
@EnableTransactionManagement(proxyTargetClass=true)
//@EnableScheduling
public class ArtnStockMongoDbApplication {
	public static void main(String[] args) {
		SpringApplication.run(ArtnStockMongoDbApplication.class, args);
	}
}
