package com.example.unitoeats;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderConfirmationActivity extends AppCompatActivity {
    String ACTIVITY_NAME = "OrderConfirmationActivity";
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderconfirmation);
        Toast.makeText(this, MenuActivity.restaurantID, Toast.LENGTH_SHORT).show();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the user
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                time = null;
            } else {
                time = extras.getString("time");
            }
        } else {
            time = (String) savedInstanceState.getSerializable("time");
        }
        TextView timeText = findViewById(R.id.pickupTimeText);
        timeText.setText(getString(R.string.pickupTime)+" "+time);

        findViewById(R.id.return_main_menu_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderConfirmationActivity.this, RestaurantActivity.class);
                intent.putExtra("User", RestaurantActivity.user);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        ImageView info = findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO make a dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderConfirmationActivity.this);
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