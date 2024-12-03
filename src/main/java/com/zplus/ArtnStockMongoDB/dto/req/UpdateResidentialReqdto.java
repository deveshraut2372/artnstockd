package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ResidentialAddress;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateResidentialReqdto {
    private String userId;
    private ResidentialAddress residentialAddress;
}
