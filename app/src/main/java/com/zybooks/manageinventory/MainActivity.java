package com.zybooks.manageinventory;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Setting up variable names for XML object items and database.
    private EditText mUsername;
    private EditText mPassword;
    private TextView mPasswordCounter;
    private Button mLoginButton;
    private Button mCreateAccountButton;
    int PasswordLimit = 5;
    LoginDBHelper DB;

    //The onCreate method initializes the XML object variables and setOnClickListener for Login Button.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsername = findViewById(R.id.Username);
        mPassword = findViewById(R.id.Password);
        mLoginButton = findViewById(R.id.loginButton);
        mCreateAccountButton = findViewById(R.id.createAccount);
        mPasswordCounter = findViewById(R.id.PasswordCounter);
        DB = new LoginDBHelper(this);

        //Setting up login button functionality. User input values are checked against the local database "DB".
        //If successful, user enters the app. Else a toast message lets them know username or password is incorrect.
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = mUsername.getText().toString();
                String pass = mPassword.getText().toString();

                if(user.equals("")||pass.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if(checkuserpass == true) {
                        Toast.makeText(MainActivity.this, "Welcome " + user + "!", Toast.LENGTH_SHORT).show();    // Changed from unnecessary "successful" to "Welcome" message
                        Intent intent = new Intent(getApplicationContext(), DisplayInventory.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                        //This block of nested code adds a limit on the number of times a user can fail at logging in.
                        PasswordLimit--;
                        mPasswordCounter.setText("Attempts Left: " + PasswordLimit);

                        if (PasswordLimit == 0) {
                            Toast.makeText(MainActivity.this, "No More Attempts Left!", Toast.LENGTH_SHORT).show();
                            mLoginButton.setEnabled(false);
                        }
                    }
                }

            }
        });
    }

    //The onCreateAccount method links the CREATE ACCOUNT button from the XML file and uses it to change to the create new account screen.
    public void onCreateAccount(View view) {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }



}