package com.zybooks.manageinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItem extends AppCompatActivity {

    // Setting up variable names for XML object items
    EditText mItemName;
    EditText mOwnerName;
    EditText mItemNum;
    Button mAddButton;

    @Override
    //Initializing object variables and the addButton setOnClickListener
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        mItemName = findViewById(R.id.itemName_input);
        mOwnerName = findViewById(R.id.owner_input);
        mItemNum = findViewById(R.id.num_input);
        mAddButton = findViewById(R.id.add_button);

        //This click listener sets up functionality for the add item button. The addItem method is called from the MyDBHelper class.
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TESTING
                String itemName = mItemName.getText().toString().trim();
                String itemOwner = mOwnerName.getText().toString().trim();
                String itemNum = mItemNum.getText().toString().trim();

                //This IF/ELSE branch checks to see if any fields in AddItem are empty. If so, an error message displays. Else, it is entered into the database.
                if(itemName.equals("")||itemOwner.equals("")||itemNum.equals("")) {
                    Toast.makeText(AddItem.this, "Please enter data to all fields!", Toast.LENGTH_SHORT).show();
                }
                else {
                    MyDBHelper myDatabase = new MyDBHelper(AddItem.this);
                    myDatabase.addItem(mItemName.getText().toString().trim(), mOwnerName.getText().toString().trim(), Integer.valueOf(mItemNum.getText().toString().trim()));

                    //This will add functionality to return to the DisplayInventory Screen after pressing button.
                    Intent intent = new Intent(AddItem.this, DisplayInventory.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
}