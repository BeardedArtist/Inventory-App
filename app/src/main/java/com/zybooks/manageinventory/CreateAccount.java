package com.zybooks.manageinventory;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    // Setting up variable names for XML object items & database.
    EditText mNewUsername;
    EditText mNewPassword;
    Button mRegisterUser;
    LoginDBHelper DB;

    @Override
    //Initializing screen objects, XML file variable objects and click listener
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mNewUsername = findViewById(R.id.CreateUsername);
        mNewPassword = findViewById(R.id.CreatePassword);
        mRegisterUser = findViewById(R.id.Register);
        DB = new LoginDBHelper(this);

        //Setting up functionality for registering new user. User input is checked against database "DB".
        //If no existing user, user's input is recorded and they move to the next screen.
        //Else two possible error messages are displayed.
        mRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mNewUsername.getText().toString();
                String pass = mNewPassword.getText().toString();

                if(user.equals("")||pass.equals("")) {
                    Toast.makeText(CreateAccount.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkuser = DB.checkusername(user);
                    if(checkuser == false) {
                        Boolean insert = DB.insertData(user, pass);
                        if(insert == true) {
                            Toast.makeText(CreateAccount.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), DisplayInventory.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(CreateAccount.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(CreateAccount.this, "User already exists! Sign in", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }
}