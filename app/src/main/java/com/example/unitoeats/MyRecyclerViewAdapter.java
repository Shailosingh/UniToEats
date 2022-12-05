package com.example.unitoeats;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;


import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    String ACTIVITY_NAME = "MyRecyclerViewAdapter";
    enum Type {
        MENU,
        RESTAURANT,
        ORDERS,
        CHECKOUT
    }

    private List<Object> data;
    private LayoutInflater inflater;
    private Type type;
    private ItemClickListener clickListener;

    // constructor
    MyRecyclerViewAdapter(Context context, List<Object> data, Type type) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.type = type;
    }

    // inflates the row layout from xml
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Log.i(ACTIVITY_NAME, this.type.toString());

        if(this.type == Type.MENU){
            view = inflater.inflate(R.layout.recyclerview_menu, parent, false);
            Log.i(ACTIVITY_NAME, "INSIDE MENU");
        }
        else if(this.type == Type.ORDERS){
            Log.i(ACTIVITY_NAME, "INSIDE ORDERS");
            view = inflater.inflate(R.layout.recyclerview_orders, parent, false);
        }
        else if(this.type == Type.RESTAURANT){
            Log.i(ACTIVITY_NAME, "INSIDE RESTAURANT");
            view = inflater.inflate(R.layout.recyclerview_restaurants, parent, false);
        }
        else if(this.type == Type.CHECKOUT){
            Log.i(ACTIVITY_NAME, "INSIDE CHECKOUT");
            view = inflater.inflate(R.layout.recyclerview_checkout, parent, false);
        }
        else {
            Log.i(ACTIVITY_NAME, "INSIDE ELSE");
            view = inflater.inflate(R.layout.recyclerview_restaurants, parent, false);
        }

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(this.type == Type.MENU){
            Log.i(ACTIVITY_NAME, "INSIDE MENU");
            Item i = Item.class.cast(data.get(position));
            holder.myTextView.setText(i.getName());
            holder.myPriceView.setText(String.format("$%1$.2f" , i.getCost()));
            new LoadImageFromWeb(holder.myImageView)
                    .execute(i.getPhotoLink());
        }
        else if(this.type == Type.ORDERS){
            Log.i(ACTIVITY_NAME, "INSIDE ORDERS");
            String item = Order.class.cast(data.get(position)).getRestaurantID();
            holder.myTimeView.setText(Order.class.cast(data.get(position)).getLocalDateTime());
            holder.myTextView.setText(item);
        }
        else if(this.type == Type.RESTAURANT){
            Log.i(ACTIVITY_NAME, "INSIDE RESTAURANT");
            Restaurant i = Restaurant.class.cast(data.get(position));
            String item = i.getName();
            holder.myTextView.setText(item);
            new LoadImageFromWeb(holder.myImageView)
                    .execute(i.getPhotoLink());
        }
        else if(this.type == Type.CHECKOUT){
            Log.i(ACTIVITY_NAME, "INSIDE CHECKOUT");
            Item i = Item.class.cast(data.get(position));
            holder.myTextView.setText(i.getName());
            holder.myPriceView.setText(String.format("$%1$.2f" , i.getCost()));
        }
        else {
            Log.i(ACTIVITY_NAME, "INSIDE ELSE");
            Restaurant i = Restaurant.class.cast(data.get(position));
            String item = i.getName();
            holder.myTextView.setText(item);
            new LoadImageFromWeb(holder.myImageView)
                    .execute(i.getPhotoLink());
        }

    }

    // gets the amount of items in the list view
    @Override
    public int getItemCount() {
        return data.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView myImageView;
        TextView myPriceView;
        TextView myTimeView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.item);
            myImageView = itemView.findViewById(R.id.ImageView);
            myPriceView = itemView.findViewById(R.id.price);
            myTimeView = itemView.findViewById(R.id.timeTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // gets the data at the entry index 'id'
    Object getItem(int id) {
        return data.get(id);
    }

    // sets the clicklistener to all items
    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private class LoadImageFromWeb extends AsyncTask<String, Void, Bitmap> {
        String CLASS_NAME = "LoadImageFromWeb";
        ImageView img;
        public LoadImageFromWeb(ImageView bmImage) {
            this.img = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap icon = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                icon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e(CLASS_NAME, e.getMessage());
            }
            return icon;
        }
        protected void onPostExecute(Bitmap result) {
            img.setImageBitmap(result);
        }
    }
}
