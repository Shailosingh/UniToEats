package com.example.unitoeats;

import java.time.LocalDateTime;
import java.util.List;

public class Order
{
    String orderID;
    String userID;
    List<Item> itemList;
    LocalDateTime pickupTime;
    boolean readyForPickup;
}
