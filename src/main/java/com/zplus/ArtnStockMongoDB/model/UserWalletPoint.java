package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserWalletPoint {
    private Date date;
    private Double walletPoints;
}
