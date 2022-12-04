package com.example.unitoeats;

public class Item
{
    private String name;
    private float cost;
    private String photoiLnk;
    private boolean isAvailable;

    public Item(String name, float cost, boolean isAvailable)
    {
        this.name = name;
        this.cost = cost;
        this.isAvailable = isAvailable;
    }
}
