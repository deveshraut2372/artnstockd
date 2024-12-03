package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CustomerContactMasterReq {
    private String customerContactId;
    private String fullName;
    private String emailAddress;
    private String message;
    private String helpQuery;
}
