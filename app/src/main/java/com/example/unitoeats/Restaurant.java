package com.example.unitoeats;

import java.util.ArrayList;
import java.util.List;

public class Restaurant
{
    private String name;
    private String photoLink;
    private String id;
    private Menu menu;
    List<String> pendingOrderIDList;

    //For Firestore
    public Restaurant()
    {
        pendingOrderIDList = new ArrayList<>();
    }

    public Restaurant(String name, String photoLink, String id, Menu menu)
    {
        this.name = name;
        this.photoLink = photoLink;
        this.id = id;
        this.menu = menu;
        pendingOrderIDList = new ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public String getPhotoLink()
    {
        return photoLink;
    }

    public String getId()
    {
        return id;
    }

    public Menu getMenu()
    {
        return menu;
    }

    public List<String> getPendingOrderIDList()
    {
        return pendingOrderIDList;
    }
}
