package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.OrderMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import lombok.Data;

import java.util.List;

@Data
public class UserOrderRes {

    private UserMaster userMaster;

    private List<OrderMaster> orderMasterList;


}
