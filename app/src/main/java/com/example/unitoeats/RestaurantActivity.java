package com.example.unitoeats;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    protected static final String ACTIVITY_NAME = "RestaurantActivity";
    MyRecyclerViewAdapter adapter;
    public static String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView info = findViewById(R.id.info);
        ImageView orders = findViewById(R.id.orders);
        orders.setVisibility(View.VISIBLE);
        info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO make a dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(RestaurantActivity.this);
                // Set the message show for the Alert time
                builder.setMessage("This app was made by:\n - Riley Huston\n - Shailendra Singh\n - Alex Lau\n - Christine Nguyen\n - Tatiana Olenciuc");

                // Set Alert Title
                builder.setTitle("Info");


                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // When the user click yes button then app will close
                    dialog.cancel();
                });
                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();
            }
        });

        // Get the user
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                user = null;
            } else {
                user = extras.getString("User");
            }
        } else {
            user = (String) savedInstanceState.getSerializable("User");
        }

        orders.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(RestaurantActivity.this, OrdersActivity.class);
                startActivityForResult(intent,10);
            }
        });



        // TODO retrieve restaurants from database and populate list
        DatabaseHelper.retrieveAllRestaurants(new DatabaseCallback()
        {
            @Override
            public void onSuccess(Object listOfRestaurantsObj)
            {
                List<Object> restaurantList = ArrayList.class.cast(listOfRestaurantsObj);

                Log.d(ACTIVITY_NAME, Integer.toString(restaurantList.size()));
                // set up the RecyclerView using the MENU type
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(RestaurantActivity.this));
                adapter = new MyRecyclerViewAdapter(RestaurantActivity.this, restaurantList, MyRecyclerViewAdapter.Type.RESTAURANT);
                adapter.setClickListener(RestaurantActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Exception e)
            {
                Log.w(ACTIVITY_NAME, "Error getting all restaurants", e);
            }
        });


    }
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(RestaurantActivity.this, MenuActivity.class);
        intent.putExtra("Restaurant", Restaurant.class.cast(adapter.getItem(position)).getId());
        startActivityForResult(intent,10);
    }
}