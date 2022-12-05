package com.example.unitoeats;

import java.util.ArrayList;
import java.util.List;

public class Menu
{
    private List<Item> items;

    //For firestore
    public Menu()
    {
        items = new ArrayList<>();
    }

    public Menu(List<Item> items)
    {
        this.items = items;
    }

    public List<Item> getItems()
    {
        return items;
    }
}
