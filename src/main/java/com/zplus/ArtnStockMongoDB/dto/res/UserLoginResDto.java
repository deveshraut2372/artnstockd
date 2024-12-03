package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.ShippingAddress;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class UserLoginResDto {
    private String userId;
    private String userFirstName;
    private String userLastName;
    private String displayName;
    private String emailAddress;
    private String  password;
    private String  status;
    private List<String> userRole;
    private Integer responseCode;
    private String message;
    private ShippingAddress shippingAddress;
    private Boolean flag;
    private String userUniqueNo;
    private String profileImage;

    private String jwtToken;



}
