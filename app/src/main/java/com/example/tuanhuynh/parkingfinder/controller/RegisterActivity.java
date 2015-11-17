package com.example.tuanhuynh.parkingfinder.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tuanhuynh.parkingfinder.R;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.DatabaseHelper;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.User;

/**
 * Registration form for new user
 */
public class RegisterActivity extends AppCompatActivity {
    /**
     * Database helper field
     */
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    /**
     * Edittext
     */
    EditText uName, Email, pwd, confirmPwd;
    /**
     * Button
     */
    Button registerButton;

    /**
     * Requires new user to fill out information for registration
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //retrieving Edit texts
        uName = (EditText)findViewById(R.id.uNameToSignup_editText);
        Email = (EditText)findViewById(R.id.email_editText);
        pwd = (EditText)findViewById(R.id.pwd_editText);
        confirmPwd = (EditText)findViewById(R.id.confirmPwd_editText);
        //retrieving buttons
        registerButton = (Button)findViewById(R.id.reg_Button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get input string from edit texts
                String username = uName.getText().toString();
                String emailStr = Email.getText().toString();
                String password = pwd.getText().toString();
                String conPwd = confirmPwd.getText().toString();

                String storedEmail = databaseHelper.searchEmail(emailStr);

                //All field must be include characters
                if (username.equals("") || Email.equals("") ||
                        password.equals("") || conPwd.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please fill out blank",
                            Toast.LENGTH_LONG).show();
                }
                //Passqword input must include at least 4 characters
                if (password.length()<3){
                    Toast.makeText(RegisterActivity.this, "Password must be at least 4 characters",
                            Toast.LENGTH_LONG).show();
                }//check if email is valid
                else if (!databaseHelper.isEmailValid(emailStr)){
                    Toast.makeText(RegisterActivity.this, "Please enter valid email address",
                            Toast.LENGTH_LONG).show();
                }
                //if input confirm password is different from inserted password
                 else if (!password.equals(conPwd)){
                    Toast.makeText(RegisterActivity.this, "Password Not Match",
                            Toast.LENGTH_LONG).show();

                }
                // check if email has been registered in the system
                else if (emailStr.equals(storedEmail)){
                    Toast.makeText(RegisterActivity.this, "Email has been already registered",
                            Toast.LENGTH_LONG).show();
                }
                //all requirements met, new user is created
                else {
                    //insert user info into database since account created successfull
                    User user = new User();
                    user.setUsername(username);
                    user.setEmail(emailStr);
                    user.setPassword(password);
                    databaseHelper.insert(user);
                    Toast.makeText(RegisterActivity.this, "Account Created Successfully!",
                            Toast.LENGTH_LONG).show();
                    Intent backIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(backIntent);
                }
            }
        });
    }
}
