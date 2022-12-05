package com.example.unitoeats;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper
{
    //Constants
    private static String ACTIVITY_NAME = "DatabaseHelper";
    private static String USER_COLLECTION = "Users";
    private static String RESTAURANT_COLLECTION = "Restaurants";
    private static String ORDER_COLLECTION = "Orders";

    /**
     * Purpose: Takes user object and adds it to the database. If successful, passes
     * success string to callback onSuccess. If failed, passes exception to callback onFailure.
     * @param newUser: New User to be added to database
     * @param callbackFunctions: Contains success and failure functions, for database action
     */
    public static void createNewUser(User newUser, DatabaseCallback callbackFunctions)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(USER_COLLECTION).document(newUser.getId()).set(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Log.d(ACTIVITY_NAME, "User Created");
                        callbackFunctions.onSuccess(String.format("User %s Created", newUser.getFullName()));

                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(ACTIVITY_NAME, "Error Creating User", e);
                        callbackFunctions.onSuccess(e);
                    }
                });
    }

    /**
     * Retrieves user object from database. If user found, passes User object to callback onSuccess.
     * If database read succeeded but, user isn't there, pass null to onSuccess.
     * If failed, passes exception to callback onFailure.
     * @param userID: User ID of person supposedly in database
     * @param callbackFunctions: Contains success and failure functions, for database action
     */
    public static void retrieveUser(String userID, DatabaseCallback callbackFunctions)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection(USER_COLLECTION).document(userID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                if(documentSnapshot.exists())
                {
                    Log.w(ACTIVITY_NAME, "User exists");
                }

                else
                {
                    Log.w(ACTIVITY_NAME, "User does not exist");
                }
                callbackFunctions.onSuccess(documentSnapshot.toObject(User.class));
            }
        })
        .addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Log.w(ACTIVITY_NAME, "Error Retrieving User", e);
                callbackFunctions.onFailure(e);
            }
        });
    }

    /**
     * Purpose: Takes restaurant object and adds it to the database. If successful, passes
     * success string to callback onSuccess. If failed, passes exception to callback onFailure.
     * @param newRestaurant: Restaurant object to be placed in database
     * @param callbackFunctions: Contains success and failure functions, for database action
     */
    public static void createRestaurant(Restaurant newRestaurant, DatabaseCallback callbackFunctions)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(RESTAURANT_COLLECTION).document(newRestaurant.getId()).set(newRestaurant)
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Log.d(ACTIVITY_NAME, "User Created");
                        callbackFunctions.onSuccess(String.format("Restaurant %s Created", newRestaurant.getName()));

                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(ACTIVITY_NAME, "Error Creating User", e);
                        callbackFunctions.onSuccess(e);
                    }
                });
    }

    /**
     * Retrieves Restaurant object from database.
     * If Restaurant found, passes Restaurant object to callback onSuccess.
     * If database read succeeded but, Restaurant isn't there, pass null to onSuccess.
     * If failed, passes exception to callback onFailure.
     * @param restaurantID: ID of Restaurant supposedly in database
     * @param callbackFunctions: Contains success and failure functions, for database action
     */
    public static void retrieveRestaurant(String restaurantID, DatabaseCallback callbackFunctions)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection(RESTAURANT_COLLECTION).document(restaurantID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot)
                    {
                        if(documentSnapshot.exists())
                        {
                            Log.w(ACTIVITY_NAME, "Restaurant  exists");
                            callbackFunctions.onSuccess(documentSnapshot.toObject(Restaurant.class));
                        }

                        else
                        {
                            Log.w(ACTIVITY_NAME, "Restaurant does not exist");
                            callbackFunctions.onSuccess(documentSnapshot.toObject(null));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(ACTIVITY_NAME, "Error Retrieving User", e);
                        callbackFunctions.onFailure(e);
                    }
                });
    }

    public static void retrieveAllRestaurants(DatabaseCallback callbackFunctions)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(RESTAURANT_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            List<Restaurant> restaurantList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d(ACTIVITY_NAME, document.getId());
                                restaurantList.add(document.toObject(Restaurant.class));
                            }
                            callbackFunctions.onSuccess(restaurantList);
                        }

                        else
                        {
                            Log.d(ACTIVITY_NAME, "Error getting documents: ", task.getException());
                            callbackFunctions.onFailure(task.getException());
                        }
                    }
                });
    }

    public static void createOrder(Order newOrder, DatabaseCallback callbackFunctions)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(ORDER_COLLECTION).add(newOrder)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        //Update ID of order in database
                        setOrderID(documentReference.getId());
                        Log.i(ACTIVITY_NAME, newOrder.getUserID());
                        //Place order ID into user and restaurant entry
                        DocumentReference userDoc = db.collection(USER_COLLECTION).document(newOrder.getUserID());
                        userDoc.update("pendingOrderIDs", FieldValue.arrayUnion(documentReference.getId()));
                        DocumentReference restaurantDoc = db.collection(RESTAURANT_COLLECTION).document(newOrder.getRestaurantID());
                        restaurantDoc.update("pendingOrderIDList", FieldValue.arrayUnion(documentReference.getId()));

                        Log.d(ACTIVITY_NAME, "Order placed");
                        callbackFunctions.onSuccess(documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(ACTIVITY_NAME, "Error Creating Order", e);
                        callbackFunctions.onSuccess(e);
                    }
                });
    }

    private static void setOrderID(String uniqueID)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference orderReference = db.collection(ORDER_COLLECTION).document(uniqueID);
        orderReference
                .update("orderID", uniqueID)
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Log.d(ACTIVITY_NAME, "Order ID set");
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(ACTIVITY_NAME, "Error doing OrderID", e);
                    }
                });
    }

    public static void removeOrder(String orderIDToBeRemoved, String userID, String restaurantID, DatabaseCallback callbackFunctions)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(ORDER_COLLECTION).document(orderIDToBeRemoved)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        //Remove order ID into user and restaurant entry
                        DocumentReference userDoc = db.collection(USER_COLLECTION).document(userID);
                        userDoc.update("pendingOrderIDs", FieldValue.arrayRemove(orderIDToBeRemoved));
                        DocumentReference restaurantDoc = db.collection(RESTAURANT_COLLECTION).document(restaurantID);
                        restaurantDoc.update("pendingOrderIDList", FieldValue.arrayRemove(orderIDToBeRemoved));

                        Log.d(ACTIVITY_NAME, "Order removed");
                        callbackFunctions.onSuccess("Order removed");
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(ACTIVITY_NAME, "Error deleting order", e);
                        callbackFunctions.onFailure(e);
                    }
                });
    }
    public static void retrieveOrder(String orderID, DatabaseCallback callbackFunctions)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection(ORDER_COLLECTION).document(orderID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot)
                    {
                        if(documentSnapshot.exists())
                        {
                            Log.w(ACTIVITY_NAME, "Order exists");
                            callbackFunctions.onSuccess(documentSnapshot.toObject(Order.class));
                        }

                        else
                        {
                            Log.w(ACTIVITY_NAME, "Order does not exist");
                            callbackFunctions.onSuccess(documentSnapshot.toObject(null));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(ACTIVITY_NAME, "Error Retrieving User", e);
                        callbackFunctions.onFailure(e);
                    }
                });
    }
    public static void retrieveAllUserOrders(String userID, DatabaseCallback callbackFunctions)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(ORDER_COLLECTION)
                .whereEqualTo("userID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            List<Order> orderList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d(ACTIVITY_NAME, document.getId());
                                orderList.add(document.toObject(Order.class));
                            }

                            callbackFunctions.onSuccess(orderList);
                        }

                        else
                        {
                            Log.d(ACTIVITY_NAME, "Error getting documents: ", task.getException());
                            callbackFunctions.onFailure(task.getException());
                        }
                    }
                });
    }
}
