package com.example.unitoeats;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    String ACTIVITY_NAME = "OrderActivity";

    MyRecyclerViewAdapter adapter;
    ArrayList<String> itemsSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        String orderID;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        itemsSelected = new ArrayList<>();

        ImageView info = findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
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

        // Get the order ID
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                orderID = null;
            } else {
                orderID = extras.getString("orderID");
            }
        } else {
            orderID = (String) savedInstanceState.getSerializable("orderID");
        }



        DatabaseHelper.retrieveOrder(orderID, new DatabaseCallback() {
            @Override
            public void onSuccess(Object orderObj) {
                Order retrievedOrder = Order.class.cast(orderObj);
                List<Item> orderItems = Order.class.cast(retrievedOrder).getItemList();
                List<Object> objItems = new ArrayList<>();
                for (Item currItem : orderItems) {
                    objItems.add(Object.class.cast(currItem));
                }
                // set up the RecyclerView using the MENU type
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
                adapter = new MyRecyclerViewAdapter(OrderActivity.this, objItems, MyRecyclerViewAdapter.Type.MENU);
                adapter.setClickListener(OrderActivity.this);
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
        Log.i(ACTIVITY_NAME, "Selected " + Integer.toString(position) + " order.");
    }
}