package com.zybooks.manageinventory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateItem extends AppCompatActivity {

    // Setting up all XML file object variables and item String variables.
    EditText mUpdateItemName;
    EditText mUpdateItemOwner;
    EditText mUpdateItemNumber;
    TextView mSecretUpdate;
    Button mUpdateItemButton;
    ImageButton mDeleteButton;
    String item_id;
    String item_name;
    String item_owner;
    String Num_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        //Initializing XML file object variables.
        mUpdateItemName = findViewById(R.id.itemName_update);
        mUpdateItemOwner = findViewById(R.id.owner_update);
        mUpdateItemNumber = findViewById(R.id.num_update);
        mUpdateItemButton = findViewById(R.id.update_button);
        mDeleteButton = findViewById(R.id.delete_button);
        mSecretUpdate = findViewById(R.id.textView);
        recieveData();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(item_name);
        }


        //This Update Item Button click listener sets up functionality to take in user input and place it into the database for update.
        mUpdateItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper myDatabase = new MyDBHelper(UpdateItem.this);
                item_name = mUpdateItemName.getText().toString().trim();
                item_owner = mUpdateItemOwner.getText().toString().trim();
                Num_items = mUpdateItemNumber.getText().toString().trim();
                myDatabase.updateData(item_id, item_name, item_owner, Num_items);

                //This will add functionality to return to the DisplayInventory Screen after pressing button.
                Intent intent = new Intent(UpdateItem.this, DisplayInventory.class);
                startActivity(intent);
                finish();

            }
        });

        //Simple click listener that calls the deleteConfirmation method to ask users if they want to delete items.
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteConfirmation();
            }
        });

    }

    //This method recieves that data that has been updated and displays it in the onCreate method.
    void recieveData() {
        if(getIntent().hasExtra("item_id") && getIntent().hasExtra("item_name") && getIntent().hasExtra("item_owner") && getIntent().hasExtra("Num_items")) {
            item_id = getIntent().getStringExtra("item_id");
            item_name = getIntent().getStringExtra("item_name");
            item_owner = getIntent().getStringExtra("item_owner");
            Num_items = getIntent().getStringExtra("Num_items");

            mUpdateItemName.setText(item_name);
            mUpdateItemOwner.setText(item_owner);
            mUpdateItemNumber.setText(Num_items);

        }
        else {
            Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();  //KEEP this error message. This may come in handy if users need to report a bug.
        }
    }

    //Method that allows for item deletion functionality
    void deleteConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete: " + item_name + "?");
        builder.setMessage("Are you sure you want to Erase " + item_name + "?");
        builder.setPositiveButton("For sure!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDBHelper myDatabase = new MyDBHelper(UpdateItem.this);
                myDatabase.deleteItem(item_id);
                finish();
            }
        });
        builder.setNegativeButton("No Way!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mSecretUpdate.setText("Nothing was Changed! Nothing at all...");
            }
        });
        builder.create().show();
    }
}