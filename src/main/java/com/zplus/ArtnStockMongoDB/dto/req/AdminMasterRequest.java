package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AdminMasterRequest {
    private String adminId;
    private String FacebookLink;
    private String InstagramLink;
    private String LinkedInLink;
    private String shippingPrice;
    private String tax;
}
