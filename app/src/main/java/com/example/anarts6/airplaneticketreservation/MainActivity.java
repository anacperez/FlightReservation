package com.example.anarts6.airplaneticketreservation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.anarts6.airplaneticketreservation.R.layout.activity_main;

/**
 * Created by anarts6 on 5/9/16.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create database for the application
        MySQLiteHelper db = new MySQLiteHelper(this);

        //add flights to the database table.
        db.addFlight(new Flight("Otter101", "Monterey", "Los Angeles", "10:30(AM)", 10, 150.00));
        db.addFlight(new Flight("Otter102", "Lost Angeles", "Monterey", "1:00(PM)", 10, 150.00));
        db.addFlight(new Flight("Otter201", "Monterey", "Seattle", "11:00(AM)", 5, 200.20));
        db.addFlight(new Flight("Otter205", "Monterey", "Seattle", "3:45(PM)", 15, 150.00));
        db.addFlight(new Flight("Otter202", "Seattle", "Monterey", "2:10(PM)", 5, 200.50));

        //adding accounts to database table
        db.addAccount(new Account("A@lice5", "@cSit100"));
        db.addAccount(new Account("$BriAn7", "123aBc##"));
        db.addAccount(new Account("!chriS12!", "CHrIS12!!"));
        db.addAccount(new Account("!admiM2 ", "!admiM2 "));
        // Read the all flights information from the database
        // and store it to a list
        List<Flight> listFlights = db.getAllFlights();

        //read all accounts information from the database and store it to an arraylist
        ArrayList<Account> listAccounts = db.getAllAccounts();
        // Set up a click listener for the reserve seat button.
        View reserveSeatButton = findViewById(R.id.ReserveSeatButton);
        reserveSeatButton.setOnClickListener(this);

        //set up a click listener for the manage system button.
        View manageSystemButton = findViewById(R.id.ManageSystemButton);
        manageSystemButton.setOnClickListener(this);

        //set up a click listener for the register button
        View cancelReservationButton = findViewById(R.id.CancelReservationButton);
        cancelReservationButton.setOnClickListener(this);

        //set up a click listener for the register button
        Button createAccountButton = (Button) findViewById(R.id.CreateAccountButton);
        createAccountButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.CreateAccountButton){
            Toast.makeText(this, "TEST", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, CreateAccount.class);
            startActivity(i);
        }
        else if(view.getId()==R.id.ReserveSeatButton){
            Toast.makeText(this,"TEST", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, ReserveSeat.class);
            startActivity(i);
        }
    }
}
