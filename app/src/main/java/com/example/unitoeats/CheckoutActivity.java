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
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    String ACTIVITY_NAME = "CheckoutActivity";
    MyRecyclerViewAdapter adapter;
    ArrayList<Object> itemsSelected;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        itemsSelected = new ArrayList<>();

        // Get the items selected from the menu
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                itemsSelected = null;
            } else {
                itemsSelected = (ArrayList<Object>) extras.getSerializable("items");
            }
        } else {
            itemsSelected = (ArrayList<Object>) savedInstanceState.getSerializable("Restaurant");
        }


        // set up the RecyclerView using the MENU type
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, itemsSelected, MyRecyclerViewAdapter.Type.CHECKOUT);
        recyclerView.setAdapter(adapter);
        double total = 0;
        for(Object currObj : itemsSelected){
            total += Item.class.cast(currObj).getCost();
        }
        TextView totalText = findViewById(R.id.totalPrice);
        totalText.setText(String.format(getString(R.string.total_price) + " $%1$.2f" ,total));

        findViewById(R.id.placeOrderbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Place order in database
                Order order = new Order(RestaurantActivity.user, MenuActivity.restaurantID, LocalDateTime.now());
                for(Object itemObj : itemsSelected){
                    order.addToOrder(Item.class.cast(itemObj));
                }
                DatabaseHelper.createOrder(order, new DatabaseCallback()
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
                Intent intent = new Intent(CheckoutActivity.this, OrderConfirmationActivity.class);
                intent.putExtra("time",order.getLocalDateTime());
                startActivity(intent);
            }
        });

        ImageView info = findViewById(R.id.info);
        ImageView cart = findViewById(R.id.cart);
        info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO make a dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
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
    }
}