package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "customer_contact_master")
public class CustomerContactMaster {

    @Id
    private String customerContactId;
    private String fullName;
    private String emailAddress;
    private String message;
    private String helpQuery;


}
