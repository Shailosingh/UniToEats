package com.example.unitoeats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User admin = new User("564043", "secret", "Shailendra Singh", true);
        User plebian = new User("190786790", "1234", "Alex Lau", false);

        DatabaseHelper.createNewUser(admin);
        DatabaseHelper.createNewUser(plebian);
    }
}