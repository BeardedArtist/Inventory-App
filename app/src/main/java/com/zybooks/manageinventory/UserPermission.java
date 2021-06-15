package com.zybooks.manageinventory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserPermission extends AppCompatActivity {

    Button mUserPermission;
    private int notificationId = 1;
    MyDBHelper myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_permission);

        mUserPermission = findViewById(R.id.userPermission);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Channel ID", "My Channel ID", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


    }

    public void userPermission(View view) {
        givePermission();
    }


    void givePermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Give Permission?");
        builder.setMessage("Are you sure you want to give this app privileges?");
        builder.setPositiveButton("I'd love to", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDBHelper myDatabase = new MyDBHelper(UserPermission.this);


                // Notification will display if users accept conditions
                String NotificationMessage = "Thank you for allowing notifications!";
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(UserPermission.this, "My Channel ID")
                        .setSmallIcon(R.drawable.notifications)
                        .setContentTitle("Inventory Notification")
                        .setContentText(NotificationMessage)
                        .setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(UserPermission.this);
                managerCompat.notify(1,mBuilder.build());


                Intent intent = new Intent(UserPermission.this, DisplayInventory.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("No, I'm good!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }


}