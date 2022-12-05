package com.example.unitoeats;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;


import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    enum Type {
        MENU,
        RESTAURANT,
        ORDERS
    }

    private List<String> mData;
    private LayoutInflater mInflater;
    private Type type;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<String> data, Type type) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.type = type;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Log.i("RECYCLEADAPTER", this.type.toString());

        if(this.type == Type.MENU){
            view = mInflater.inflate(R.layout.recyclerview_menu, parent, false);
            Log.i("RECYCLEADAPTER", "INSIDE MENU");

        }
        else if(this.type == Type.ORDERS){
            Log.i("RECYCLEADAPTER", "INSIDE ORDERS");
            view = mInflater.inflate(R.layout.recyclerview_orders, parent, false);
        }
        else if(this.type == Type.RESTAURANT){
            Log.i("RECYCLEADAPTER", "INSIDE RESTAURNT");
            view = mInflater.inflate(R.layout.recyclerview_restaurants, parent, false);
        }
        else {
            Log.i("RECYCLEADAPTER", "INSIDE ELSE");

            view = mInflater.inflate(R.layout.recyclerview_restaurants, parent, false);
        }

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.myTextView.setText(animal);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}