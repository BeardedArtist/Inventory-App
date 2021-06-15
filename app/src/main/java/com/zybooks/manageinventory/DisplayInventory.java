package com.zybooks.manageinventory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class DisplayInventory extends AppCompatActivity {

    // Setting up all my objects from the display inventory layout, Database Helper and Adapter
    RecyclerView mRecyclerView;
    Button mAdd_button;
    MyDBHelper myDatabase;
    UserPermission notification;
    ArrayList<String> itemId;
    ArrayList<String> itemName;
    ArrayList<String> itemOwner;
    ArrayList<String> numItem;
    InventoryAdapter inventoryAdapter;
    TextView mLonelyInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_inventory);

        //Instantiating necessary objects from the layout for functionality
        mRecyclerView = findViewById(R.id.recyclerView);
        mLonelyInventory = findViewById(R.id.LonelyInventory);
        mAdd_button = findViewById(R.id.add_button);

        //On Click listener that will take users from the display inventory screen to add new item screen
        mAdd_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayInventory.this, AddItem.class);
                startActivity(intent);
            }
        });

        //Instantiating ArrayLists that will be used to hold information from the DB
        myDatabase = new MyDBHelper(DisplayInventory.this);
        itemId = new ArrayList<>();
        itemName = new ArrayList<>();
        itemOwner = new ArrayList<>();
        numItem = new ArrayList<>();

        InsertDataToArray();

        inventoryAdapter = new InventoryAdapter(DisplayInventory.this, this, itemId, itemName, itemOwner, numItem);
        mRecyclerView.setAdapter(inventoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(DisplayInventory.this));

        //Checking the version of Android and creating a notification channel for versions past Oreo.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Channel ID", "My Channel ID", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }

    //This function uses cursors to help move data into the arrays we set up
    //setVisibility() lets me show some text when the inventory is empty
    void InsertDataToArray() {
        Cursor cursor = myDatabase.readAllData();
        if(cursor.getCount() == 0) {
            mLonelyInventory.setVisibility(View.VISIBLE);

            //Notification that outputs when inventory is empty
            String NotificationMessage = "No items in Inventory!";
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(DisplayInventory.this, "My Channel ID")
                    .setSmallIcon(R.drawable.notifications)
                    .setContentTitle("Inventory Notification")
                    .setContentText(NotificationMessage)
                    .setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(DisplayInventory.this);
            managerCompat.notify(1,mBuilder.build());
        }
        else {
            while (cursor.moveToNext()) {
                itemId.add(cursor.getString(0));
                itemName.add(cursor.getString(1));
                itemOwner.add(cursor.getString(2));
                numItem.add(cursor.getString(3));
            }
            mLonelyInventory.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //This block of code helps define the functionality of the top right menu that will allow users to delete the inventory or move to the UserPermission screen
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {

            case R.id.InventoryListDelete:
                deleteConfirmation();
                return true;

            case R.id.notifications:
                Toast.makeText(this, "Notifications Selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, UserPermission.class);
                startActivity(intent);
                return true;

            case R.id.help:
                Toast.makeText(this, "Help Selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.info:
                Intent intent1 = new Intent (this, InformationActivity.class);
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Setting up a Alert Dialog box to give users the chance to confirm that they want to delete everything
    void deleteConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Destroy Inventory?");
        builder.setMessage("Are you sure you want to Destroy your Inventory?");
        builder.setPositiveButton("For sure!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDBHelper myDatabase = new MyDBHelper(DisplayInventory.this);
                myDatabase.destroyInventory();

                Intent intent = new Intent(DisplayInventory.this, DisplayInventory.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No Way!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }
}