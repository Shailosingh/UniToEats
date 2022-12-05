package com.example.unitoeats;

import static com.example.unitoeats.R.id.userID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Patterns;

import com.google.android.material.snackbar.Snackbar;


public class LoginActivity extends AppCompatActivity {
    //CONSTANTS
    protected static final String ACTIVITY_NAME = "LoginActivity";
    private EditText userEdit, passwordEdit;
    private SharedPreferences sharedPrefs;
    private CheckBox remember;
    private SharedPreferences.Editor editor;
    private Boolean saveLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        userEdit = findViewById(R.id.userID);
        passwordEdit = findViewById(R.id.password);
        remember = findViewById(R.id.rememberMe);
        sharedPrefs = getSharedPreferences("myPreferences", MODE_PRIVATE);
        editor = sharedPrefs.edit();

        saveLogin = sharedPrefs.getBoolean("saveLogin",false);
        if(saveLogin == true){
            userEdit.setText(sharedPrefs.getString("username", ""));
            passwordEdit.setText(sharedPrefs.getString("password", ""));
            remember.setChecked(true);
        }

        //Login Button
        Button login_button = findViewById(R.id.loginButton);
        login_button.setOnClickListener(v -> onClick());

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
    private void onClick(){
        String user = userEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        //Gives toast if email is invalid
        if (user.isEmpty()) {
            Toast.makeText(this,R.string.empty_user, Toast.LENGTH_LONG).show();
            return;
        }

        if (!user.matches("\\d+")) {
            Toast.makeText(this,R.string.invalid_user, Toast.LENGTH_LONG).show();
            return;
        }

        //Gives Snackbar if password field is empty
        if (password.isEmpty()) {
            Snackbar.make(findViewById(R.id.loginButton),R.string.invalid_password, Snackbar.LENGTH_LONG).show();
            return;
        }
        if(remember.isChecked()){
            editor.putBoolean("saveLogin", true);
            editor.putString("user", user);
            editor.putString("password", password);
            editor.commit();

        }else{
            editor.clear();
            editor.commit();
        }
        Intent intent = new Intent(LoginActivity.this, RestaurantActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

}