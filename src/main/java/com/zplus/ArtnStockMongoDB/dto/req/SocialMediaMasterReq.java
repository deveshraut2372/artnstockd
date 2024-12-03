package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
public class SocialMediaMasterReq {

    private String socialMediaAdminId;

    private String logo;

    private String name;

    private String link;

    private String status;

    private Date date;
}
