package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user_message_master")
@ToString
public class UserMessageMaster {

    private String userMessageId;

    private String message;

    private String status;

    @DBRef
    private UserMaster userMaster;

}
