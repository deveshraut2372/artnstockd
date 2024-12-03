package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter@Setter@ToString
@Document(collection = "shipping_method")
public class ShippingMethod {

        @Id
        private String shippingMethodId;
        private String shippingMethodName;
        private Double shippingMethodPrice;
        private String dayToReceive;
}
