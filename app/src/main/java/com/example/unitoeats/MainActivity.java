package com.example.unitoeats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.sql.Array;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private static String ACTIVITY_NAME = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User admin = new User("190777790", "blahblah", "Shailendra Singh", true);
        User admin2 = new User("190954880", "password", "Riley Huston", true);
        User plebian = new User("190786790", "1234", "Alex Lau", false);

        /*
        //Create User test
        DatabaseHelper.createNewUser(admin, new DatabaseCallback() {
            @Override
            public void onSuccess(Object successMessage)
            {
                Log.d(ACTIVITY_NAME, successMessage.toString());
            }

            @Override
            public void onFailure(Exception e)
            {
                Log.w(ACTIVITY_NAME, "Error Creating User", e);
            }
        });

        DatabaseHelper.createNewUser(plebian, new DatabaseCallback()
        {
            @Override
            public void onSuccess(Object successMessage)
            {
                Log.d(ACTIVITY_NAME, successMessage.toString());

            }

            @Override
            public void onFailure(Exception e)
            {
                Log.w(ACTIVITY_NAME, "Error Creating User", e);
            }
        });
         */

        /*
        //Retrieve user test
        DatabaseHelper.retrieveUser(admin.getId(), new DatabaseCallback()
        {
            @Override
            public void onSuccess(Object retrievedUser)
            {
                if(retrievedUser != null)
                {
                    Log.d(ACTIVITY_NAME, String.format("Retrieved User: %s", User.class.cast(retrievedUser).getFullName()));
                }
            }

            @Override
            public void onFailure(Exception e)
            {
                Log.w(ACTIVITY_NAME, "Error Retrieving User", e);
            }
        });
        */



        /*
        //Test createRestaurant
        List<Item> harveysItemList = new ArrayList<>();
        harveysItemList.add(new Item("Frings", 2.25, "https://cdn.discordapp.com/attachments/1049140791471259648/1049140870957502505/unknown.png", true));
        harveysItemList.add(new Item("Fries", 2.25, "https://cdn.discordapp.com/attachments/1049140791471259648/1049142042900562011/unknown.png", true));
        Menu harveysMenu = new Menu(harveysItemList);
        Restaurant harveys = new Restaurant("Harvey's", "https://media.discordapp.net/attachments/1021115434025615562/1049075205970075710/harveys2.webp", "Harveys0", harveysMenu);

        DatabaseHelper.createRestaurant(harveys, new DatabaseCallback()
        {
            @Override
            public void onSuccess(Object successMessage)
            {
                Log.d(ACTIVITY_NAME, successMessage.toString());
            }

            @Override
            public void onFailure(Exception e)
            {
                Log.w(ACTIVITY_NAME, "Error Creating Restaurant", e);
            }
        });
         */

        /*
        //Test retrieveRestaurant
        DatabaseHelper.retrieveRestaurant(harveys.getId(), new DatabaseCallback()
        {
            @Override
            public void onSuccess(Object retrievedRestaurant)
            {
                if(retrievedRestaurant != null)
                {
                    Log.w(ACTIVITY_NAME, "bloop");
                    Log.d(ACTIVITY_NAME, String.format("Retrieved Restaurant: %s", Restaurant.class.cast(retrievedRestaurant).getName()));
                    Log.d(ACTIVITY_NAME, String.format("Retrieved Restaurant: %s", Restaurant.class.cast(retrievedRestaurant).getMenu().getItems().get(0).getName()));
                    Log.d(ACTIVITY_NAME, String.format("Retrieved Restaurant: %s", Restaurant.class.cast(retrievedRestaurant).getMenu().getItems().get(1).getName()));

                }
            }

            @Override
            public void onFailure(Exception e)
            {
                Log.w(ACTIVITY_NAME, "Error Retrieving Restaurant", e);
            }
        });
         */


        /*
        //Test createOrder
        Order newOrder = new Order(plebian.getId(), harveys.getId(), LocalDateTime.now());
        newOrder.addToOrder(new Item("Frings", 2.25, "https://cdn.discordapp.com/attachments/1049140791471259648/1049140870957502505/unknown.png", true));

        DatabaseHelper.createOrder(newOrder, new DatabaseCallback()
        {
            @Override
            public void onSuccess(Object newOrderID)
            {
                Log.d(ACTIVITY_NAME, String.format("Order placed: %s",newOrderID.toString()));
            }

            @Override
            public void onFailure(Exception e)
            {
                Log.w(ACTIVITY_NAME, "Error Creating Order", e);
            }
        });
         */

        /*
        //Test removeOrder
        DatabaseHelper.removeOrder("LDNx4obSZxMwtGEYuqWy", newOrder.getUserID(), newOrder.getRestaurantID(), new DatabaseCallback()
        {
            @Override
            public void onSuccess(Object obj)
            {
                Log.d(ACTIVITY_NAME, "Order removed");
            }

            @Override
            public void onFailure(Exception e)
            {
                Log.w(ACTIVITY_NAME, "Error Removing Order", e);
            }
        });
         */

        //Test retrieveAllRestaurants
        DatabaseHelper.retrieveAllRestaurants(new DatabaseCallback()
        {
            @Override
            public void onSuccess(Object listOfRestaurantsObj)
            {
                List<Restaurant> restaurantList = ArrayList.class.cast(listOfRestaurantsObj);
                Log.d(ACTIVITY_NAME, Integer.toString(restaurantList.size()));

                for(Restaurant currentRestaurant : restaurantList)
                {
                    Log.d(ACTIVITY_NAME, currentRestaurant.getName());
                }
            }

            @Override
            public void onFailure(Exception e)
            {
                Log.w(ACTIVITY_NAME, "Error getting all restaurants", e);
            }
        });
    }
}