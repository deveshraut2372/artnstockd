package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.TempArtProductMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TempArtProductMasterRes {

    private Boolean flag;

    private TempArtProductMaster tempArtProductMaster;


}
