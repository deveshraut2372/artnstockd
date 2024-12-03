package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.model.CartMaster;
import lombok.Data;

//@Document(collection = "CartItemMaster") // MongoDB document annotation
@Data
public class CartItemMaster {

    //    @Id
    private String id; // MongoDB document ID

    private String cartItemId; // Unique identifier for the cart item
    private String name; // Name or description of the item
    private double price; // Price of the item
    private int quantity; // Quantity of the item in the cart
    private String status; // Status of the item (e.g., Active, Deleted, etc.)
    private CartMaster cartMaster; // Reference to the parent cart
}