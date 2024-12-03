package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMessageMasterRequest {
    private String userMessageId;
    private String message;
    private String status;
    private String userId;
}
