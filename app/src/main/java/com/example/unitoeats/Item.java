package com.example.unitoeats;

import java.io.Serializable;

public class Item implements Serializable
{
    private String name;
    private double cost;
    private String photoLink;
    private boolean isAvailable;

    //For firebase
    public Item(){}

    public Item(String name, double cost, String photolink, boolean isAvailable)
    {
        this.name = name;
        this.cost = cost;
        this.photoLink = photolink;
        this.isAvailable = isAvailable;
    }

    public String getName()
    {
        return name;
    }

    public double getCost()
    {
        return cost;
    }

    public String getPhotoLink()
    {
        return photoLink;
    }

    public boolean getIsAvailable()
    {
        return isAvailable;
    }
}
