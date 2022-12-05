package com.example.unitoeats;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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

public class MenuActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    String ACTIVITY_NAME = "MenuActivity";

    MyRecyclerViewAdapter adapter;
    ArrayList<String> itemsSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemsSelected = new ArrayList<>();

        ImageView info = findViewById(R.id.info);
        ImageView cart = findViewById(R.id.cart);
        cart.setVisibility(View.VISIBLE);
        info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO make a dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                // Set the message show for the Alert time
                builder.setMessage("This app was made by:\n - Riley Huston\n - Shailendra Singh?\n - Alex Lau\n - Tatiana Olenciuc");

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
        cart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO start orders activity with array of menu items selected
                Toast.makeText(MenuActivity.this, "CART", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MenuActivity.this, CartActivity.class);
                intent.putStringArrayListExtra("items", itemsSelected);
                startActivityForResult(intent,10);
            }
        });



        // Get the restaurant that was clicked
        String restaurant;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                restaurant= null;
            } else {
                restaurant= extras.getString("Restaurant");
            }
        } else {
            restaurant= (String) savedInstanceState.getSerializable("Restaurant");
        }

        // data to populate the RecyclerView with
        ArrayList<String> menuItems = new ArrayList<>();

        // TODO retrieve menu items based on restaurant from database and populate list
        for(int i = 0; i < 15; i++){
            menuItems.add("Food "+ Integer.toString(i) + ", " + restaurant);
        }
        // set up the RecyclerView using the MENU type
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, menuItems, MyRecyclerViewAdapter.Type.MENU);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
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
        itemsSelected.add(adapter.getItem(position));
        Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Log.i(ACTIVITY_NAME,"in result");
            itemsSelected = data.getStringArrayListExtra("items");
        }
    }
}