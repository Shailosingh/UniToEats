package com.example.unitoeats;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseHelper
{
    //Datafields
    private static String ACTIVITY_NAME = "DatabaseHelper";
    private static String USER_COLLECTION = "Users";

    public static void createNewUser(User newUser)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(USER_COLLECTION).document(newUser.getId()).set(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Log.d(ACTIVITY_NAME, "User Created");

                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(ACTIVITY_NAME, "Error Creating User", e);
                    }
                });
    }
}
