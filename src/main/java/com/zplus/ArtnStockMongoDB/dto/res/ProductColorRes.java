package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.ProductColor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProductColorRes {

    Set<ProductColor> standaredColors;

    Set<ProductColor> neonColors;

}
