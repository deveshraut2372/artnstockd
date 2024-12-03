package com.zplus.ArtnStockMongoDB.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "User_Ser_Time_Master")
public class UserServeTimeMaster {

    @Id
    private String userServeTimeId;
    @DBRef
    private UserMaster userMaster;
    private LocalDateTime loginDate;
    private LocalDateTime logoutDate;

    private String hours;
    private String minutes;
    private String seconds;

}
