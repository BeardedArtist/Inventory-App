package com.zybooks.manageinventory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyViewHolder> {

    //Setting up ArrayList Variables that will be used for the recycler view object.
    private Context context;
    Activity activity;
    private ArrayList<String> itemId;
    private ArrayList<String> itemName;
    private ArrayList<String> itemOwner;
    private ArrayList<String> numItem;
    Animation animation;
    //int position;

    //Setting up the adapter that will help the program remember what text/int need to be displayed when a new item is created.
    InventoryAdapter(Activity activity, Context context, ArrayList itemId, ArrayList itemName, ArrayList itemOwner, ArrayList numItem) {
        this.activity = activity;
        this.context = context;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemOwner = itemOwner;
        this.numItem = numItem;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //this.position = position;
        holder.Recycler_row_itemId.setText(String.valueOf(itemId.get(position)));
        holder.Recycler_row_itemName.setText(String.valueOf(itemName.get(position)));
        holder.Recycler_row_itemOwner.setText(String.valueOf(itemOwner.get(position)));
        holder.Recycler_row_numItems.setText(String.valueOf(numItem.get(position)));

        holder.Recycler_Row_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateItem.class);
                intent.putExtra("item_id", String.valueOf(itemId.get(position)));
                intent.putExtra("item_name", String.valueOf(itemName.get(position)));
                intent.putExtra("item_owner", String.valueOf(itemOwner.get(position)));
                intent.putExtra("Num_items", String.valueOf(numItem.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Recycler_row_itemId;
        TextView Recycler_row_itemName;
        TextView Recycler_row_itemOwner;
        TextView Recycler_row_numItems;
        LinearLayout Recycler_Row_layout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Recycler_row_itemId = itemView.findViewById(R.id.Recycler_row_itemId);
            Recycler_row_itemName = itemView.findViewById(R.id.Recycler_row_itemName);
            Recycler_row_itemOwner = itemView.findViewById(R.id.Recycler_row_itemOwner);
            Recycler_row_numItems = itemView.findViewById(R.id.Recycler_row_numItems);
            Recycler_Row_layout = itemView.findViewById(R.id.Recycler_Row_layout);
            animation = AnimationUtils.loadAnimation(context, R.anim.animation);
            Recycler_Row_layout.setAnimation(animation);
        }
    }
}
