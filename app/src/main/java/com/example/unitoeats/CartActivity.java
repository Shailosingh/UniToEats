package com.example.unitoeats;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    String ACTIVITY_NAME = "CartActivity";
    MyRecyclerViewAdapter adapter;
    ArrayList<Object> itemsSelected;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemsSelected = new ArrayList<>();

        ImageView info = findViewById(R.id.info);
        ImageView cart = findViewById(R.id.cart);
        info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO make a dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
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
        Log.i(ACTIVITY_NAME, "USER ID: " + RestaurantActivity.user);


        // Get the items selected from the menu
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        itemsSelected = (ArrayList<Object>) args.getSerializable("ARRAYLIST");


        // set up the RecyclerView using the MENU type
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, itemsSelected, MyRecyclerViewAdapter.Type.MENU);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.checkoutbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemsSelected.size() > 0) {
                    Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                    intent.putExtra("items", itemsSelected);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(CartActivity.this, "Your cart is empty.", Toast.LENGTH_SHORT).show();
                }
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

        itemsSelected.remove(adapter.getItem(position));
        adapter = new MyRecyclerViewAdapter(this, itemsSelected, MyRecyclerViewAdapter.Type.MENU);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        Intent data = new Intent();
        data.putExtra("items", itemsSelected);
        setResult(RESULT_OK, data);

//        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}