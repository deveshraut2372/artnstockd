package com.zplus.ArtnStockMongoDB.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "notification_m_Master")
public class NotificationMaster {

    @Id
    private String notificationId;

    private String title;

    private String description;

    private String logo;

    private Date startDate;

    private Date endDate;

    private  String status;






}
