package com.example.unitoeats;

import java.util.ArrayList;
import java.util.List;

public class User
{
    private String fullName;
    private String id;
    private String password;
    private List<String> pendingOrderIDs;
    private boolean isAdmin;
    private List<String> restaurantIDs;

    //For Firestore
    public User() {}

    //Create new user
    public User(String id, String password, String fullName, boolean isAdmin)
    {
        this.fullName = fullName;
        this.id = id;
        this.password = password;
        this.isAdmin = false;
        pendingOrderIDs = new ArrayList<>();
        restaurantIDs = new ArrayList<>();
    }

    public String getFullName()
    {
        return fullName;
    }
    public String getId()
    {
        return id;
    }

    public String getPassword()
    {
        return password;
    }

    public List<String> getPendingOrderIDs()
    {
        return pendingOrderIDs;
    }

    public boolean getIsAdmin()
    {
        return isAdmin;
    }

    public List<String> getRestaurantIDs()
    {
        return restaurantIDs;
    }
}
