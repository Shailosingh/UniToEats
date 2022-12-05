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

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    protected static final String ACTIVITY_NAME = "OrdersActivity";
    MyRecyclerViewAdapter adapter;
    ArrayList<Object> orders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        String user;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView info = findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(OrdersActivity.this);
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

        Log.d(ACTIVITY_NAME, "BEFORE");
        // data to populate the RecyclerView with
        DatabaseHelper.retrieveAllUserOrders(RestaurantActivity.user, new DatabaseCallback() {
            @Override
            public void onSuccess(Object orderListObj) {
                Log.d(ACTIVITY_NAME, "AFTER");

                List<Object> orderList = ArrayList.class.cast(orderListObj);

                Log.d(ACTIVITY_NAME, Integer.toString(orderList.size()));
                // set up the RecyclerView using the MENU type
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(OrdersActivity.this));
                adapter = new MyRecyclerViewAdapter(OrdersActivity.this, orderList, MyRecyclerViewAdapter.Type.ORDERS);
                adapter.setClickListener(OrdersActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Exception e) {
                Log.w(ACTIVITY_NAME, "Error Retrieving Database", e);
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
        Intent intent = new Intent(OrdersActivity.this, OrderActivity.class);
        intent.putExtra("orderID", Order.class.cast(adapter.getItem(position)).getOrderID());
        startActivityForResult(intent,10);
    }
}