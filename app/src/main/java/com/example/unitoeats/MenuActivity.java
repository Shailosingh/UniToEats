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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    String ACTIVITY_NAME = "MenuActivity";
    public static String restaurantID;

    MyRecyclerViewAdapter adapter;
    ArrayList<Item> itemsSelected;

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
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
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
        cart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO start orders activity with array of menu items selected

                Intent intent = new Intent(MenuActivity.this, CartActivity.class);

                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)itemsSelected);
                intent.putExtra("BUNDLE",args);
//                startActivity(intent);
//
//                intent.putExtra("Items", itemsSelected);
                startActivityForResult(intent,10);
            }
        });



        // Get the restaurant that was clicked
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                restaurantID= null;
            } else {
                restaurantID= extras.getString("Restaurant");
            }
        } else {
            restaurantID= (String) savedInstanceState.getSerializable("Restaurant");
        }
        Log.i(ACTIVITY_NAME, "restaurntDI: " +restaurantID );
        DatabaseHelper.retrieveRestaurant(restaurantID, new DatabaseCallback()
        {
            @Override
            public void onSuccess(Object retrievedRestaurant)
            {
                if(retrievedRestaurant != null)
                {
                    Log.w(ACTIVITY_NAME, "bloop");
                    List<Item> menuItems = Restaurant.class.cast(retrievedRestaurant).getMenu().getItems();
                    List<Object> objItems = new ArrayList<>();
                    for(Item currItem : menuItems){
                        objItems.add(Object.class.cast(currItem));
                    }
                    // set up the RecyclerView using the MENU type
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MenuActivity.this));
                    adapter = new MyRecyclerViewAdapter(MenuActivity.this, objItems, MyRecyclerViewAdapter.Type.MENU);
                    adapter.setClickListener(MenuActivity.this);
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Exception e)
            {
                Log.w(ACTIVITY_NAME, "Error Retrieving Restaurant", e);
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
        itemsSelected.add(Item.class.cast(adapter.getItem(position)));
        Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Log.i(ACTIVITY_NAME,"in result");
            itemsSelected = (ArrayList<Item>) data.getSerializableExtra("items");
        }
    }
}