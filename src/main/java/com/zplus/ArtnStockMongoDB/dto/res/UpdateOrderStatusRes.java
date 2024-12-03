package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor
public class UpdateOrderStatusRes {

    private String msg;
    private Boolean flag;

    public UpdateOrderStatusRes() {

    }
}
