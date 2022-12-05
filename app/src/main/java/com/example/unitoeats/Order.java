package com.example.unitoeats;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order
{
    private String orderID;
    private String userID;
    private String restaurantID;
    private List<Item> itemList;
    private long pickupTime;
    private boolean isReadyForPickup;

    //For firestore
    public Order(){}

    public Order(String userID, String restaurantID, List<Item> itemList, LocalDateTime pickupTime)
    {
        this.orderID = ""; //Fill it using the UID made by database
        this.userID = userID;
        this.restaurantID = restaurantID;
        this.itemList = itemList;
        this.pickupTime = System.currentTimeMillis(); //Accomodate for different pickup times later
        this.isReadyForPickup = false;
    }

    public Order(String userID, String restaurantID, LocalDateTime pickupTime)
    {
        this.orderID = ""; //Fill it using the UID made by database
        this.userID = userID;
        this.restaurantID = restaurantID;
        this.itemList = new ArrayList<>();
        this.pickupTime = System.currentTimeMillis(); //Accomodate for different pickup times later
        this.isReadyForPickup = false;
    }

    public String getOrderID()
    {
        return orderID;
    }

    public void setOrderID(String uniqueID)
    {
        this.orderID = uniqueID;
    }

    public String getUserID()
    {
        return userID;
    }

    public String getRestaurantID()
    {
        return restaurantID;
    }

    public List<Item> getItemList()
    {
        return itemList;
    }

    public long getPickupTime()
    {
        return pickupTime;
    }

    public String getLocalDateTime()
    {
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.pickupTime), ZoneId.systemDefault());

        // Create DateTimeFormatter instance
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Format LocalDateTime to String
        String formattedDateTime = date.format(dateTimeFormatter);
        return formattedDateTime;
    }

    public boolean getIsReadyForPickup()
    {
        return isReadyForPickup;
    }

    public void addToOrder(Item newItem)
    {
        itemList.add(newItem);
    }
}