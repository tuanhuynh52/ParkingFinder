package com.example.tuanhuynh.parkingfinder.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    /*
    Edittext username and password
     */
    EditText uName, pwd;
    /*
    Button login and signup
     */
    Button loginButton, signupButton;

    /**
     * Create activity when register button and log in button clicked
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        uName = (EditText)findViewById(R.id.uNameToLogin_editText);
        pwd = (EditText)findViewById(R.id.pwdToLogin_editText);
        /*
        * Sign up button clicked takes user to registration form
         */
        signupButton = (Button)findViewById(R.id.signupButton);
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
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = uName.getText().toString();
                String password = pwd.getText().toString();
                //log in successfull using correct username and password
                String pass = databaseHelper.searchPassword(username);
                if (pass.equals(password)){
                    Toast.makeText(LoginActivity.this, "Welcome! " + username,
                            Toast.LENGTH_LONG).show();
                    //change to new search intent
                    Intent newIntent = new Intent(LoginActivity.this, FinderActivity.class);
                    newIntent.putExtra("Username", username);
                    startActivity(newIntent);
                } else {
                    //error shows if inccorrect password or username
                    Toast.makeText(LoginActivity.this, "Username or Password Incorrect!, Try Again",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    /**
     * adding menu of items
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}