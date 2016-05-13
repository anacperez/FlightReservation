package com.example.anarts6.airplaneticketreservation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.String.*;

/**
 * Created by anarts6 on 5/9/16.
 */
public class CreateAccount extends Activity implements View.OnClickListener{

    private String username;
    private String password;
    private Button createAccountButton;
    private int attemps = 0;

    public static final String specialCharacters = "!@#$%^&*()~`-=_+[]{}|:\";',./<>?";
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);


        createAccountButton = (Button) findViewById(R.id.DoneButton);
        createAccountButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        EditText cinPassword;
        EditText cinUsername;
        String inputUsername;
        String inputPassword;

        //instance of database flightreservation
        MySQLiteHelper db = new MySQLiteHelper(this);
        db.getWritableDatabase();

        if(view.getId()==R.id.DoneButton)
        {
            cinPassword = (EditText)findViewById(R.id.password_edittext);
            cinUsername = (EditText)findViewById(R.id.username_edittext);
            inputUsername = cinUsername.getText().toString();
            inputPassword = cinPassword.getText().toString();
            boolean validUsername = verifyUsernameNotTaken(inputUsername, db);
            boolean correctPassword = verifyPassword(inputPassword);
            boolean correctUsername = verifyUsername(inputUsername);
            if(correctUsername == true && correctPassword == true&& validUsername== true)
            {
                //save password and username to database
                //System.out.println("password " + inputPassword);
                Toast.makeText(this, "Right username or password", Toast.LENGTH_SHORT).show();
                //adding new account to database.
                db.addAccount(new Account(inputUsername, inputPassword));
                //System.out.println("username" + inputUsername);
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                attemps++;
            }
        }
        if(attemps==2){
            Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
    public boolean verifyPassword(String inputPassword)
    {
        boolean upperCaseChar = false;
        boolean lowerCaseChar = false;
        boolean specialCharacter = false;
        boolean oneNumber = false;
        if(inputPassword.isEmpty())
        {
            return false;
        }
        else{
            char[] aC = inputPassword.toCharArray();
            for(char c : aC){
                if(Character.isUpperCase(c))
                {
                    upperCaseChar = true;
                }
                else if(Character.isLowerCase(c))
                {
                    lowerCaseChar = true;
                }
                else if(Character.isDigit(c))
                {
                    oneNumber = true;
                }
                else if(specialCharacters.indexOf(valueOf(c))>=0)
                {
                    specialCharacter = true;
                }
            }
        }
        if(upperCaseChar== true && lowerCaseChar == true && specialCharacter == true
                && oneNumber == true)
        {
            return true;
        }
        else
            return false;
    }
    public boolean verifyUsername(String inputUsername)
    {
        boolean upperCaseChar = false;
        boolean lowerCaseChar = false;
        boolean specialCharacter = false;
        boolean oneNumber = false;
        if(inputUsername.isEmpty())
        {
            return false;
        }
        else
        {
            char[] aC = inputUsername.toCharArray();
            for(char c : aC){
                if(Character.isUpperCase(c)){
                    upperCaseChar = true;
                }
                else if(Character.isLowerCase(c)){
                    lowerCaseChar = true;
                }
                else if(Character.isDigit(c)){
                    oneNumber = true;
                }
                else if(specialCharacters.indexOf(valueOf(c))>=0){
                    specialCharacter = true;
                }
            }
        }
        if(upperCaseChar== true && lowerCaseChar == true && specialCharacter == true
                && oneNumber == true)
        {
            return true;
        }
        else
            return false;
    }
    public boolean verifyUsernameNotTaken(String inputUsername, MySQLiteHelper db)
    {
        db.getReadableDatabase();
        ArrayList<Account> accounts = db.getAllAccounts();
        for(int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getUsername().equals(inputUsername)){
                return false;
            }
          //  Log.i("Account: " + i, valueOf(accounts.get(i).getUsername()));
        }
        return true;
        /*for(Account account : accounts){
            Log.i("Database info: ", String.valueOf(account));
        } */

    }


}
