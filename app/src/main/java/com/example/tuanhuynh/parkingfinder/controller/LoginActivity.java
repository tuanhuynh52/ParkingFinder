package com.example.tuanhuynh.parkingfinder.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tuanhuynh.parkingfinder.R;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.DatabaseHelper;

/**
 * LoginActivity class allows user to use their account and password to sign in the app system.
 */
public class LoginActivity extends AppCompatActivity {

    /*
         * Database helper field
         */
    private DatabaseHelper databaseHelper;
    /*
    Edittext username and password
     */
    private EditText uName, pwd;
    /*
    Button login and signup
     */
    private Button loginButton, signupButton;
    /*
    username and password
     */
    private String username, password;

    /**
     * Create activity when register button and log in button clicked
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        toolbar.setTitle("LOGIN");

        databaseHelper = new DatabaseHelper(this);

        uName = (EditText) findViewById(R.id.uNameToLogin_editText);
        pwd = (EditText) findViewById(R.id.pwdToLogin_editText);
        //using SharedPrefs to bypass login activity after first time loggedin
        SharedPreferences sharedPreferences = getSharedPreferences
                (getString(R.string.SHARED_PREFS), MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false);
        if (isLoggedIn) {
            String restoredUName = sharedPreferences.getString("select_username", "");
            if (!restoredUName.equals("")) {
                Intent newIntent = new Intent(LoginActivity.this, FinderActivity.class);
                newIntent.putExtra("Username", restoredUName);
                startActivity(newIntent);
                finish();
            }

        }
        /*
        * Sign up button clicked takes user to registration form
         */
        signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        /*
        *Log in activity
         */
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = uName.getText().toString();
                password = pwd.getText().toString();
                //log in successfull using correct username and password
                String pass = databaseHelper.searchPassword(username);
                if (pass.equals(password)) {

                    SharedPreferences sharedPreferences = getSharedPreferences
                            (getString(R.string.SHARED_PREFS), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.LOGGEDIN), true);
                    //store username
                    editor.putString("select_username", username);
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "Welcome! " + username,
                            Toast.LENGTH_LONG).show();
                    //change to new search intent
                    Intent newIntent = new Intent(LoginActivity.this, FinderActivity.class);
                    newIntent.putExtra("Username", username);
                    startActivity(newIntent);
                    finish();
                } else {
                    //error shows if inccorrect password or username
                    Toast.makeText(LoginActivity.this, "Username or Password Incorrect!, Try Again",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
