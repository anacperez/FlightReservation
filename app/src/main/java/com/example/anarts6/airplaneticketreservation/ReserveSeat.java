package com.example.anarts6.airplaneticketreservation;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anarts6.airplaneticketreservation.CustomFlightsAdapter;
import com.example.anarts6.airplaneticketreservation.Flight;
import com.example.anarts6.airplaneticketreservation.Login;
import com.example.anarts6.airplaneticketreservation.MainActivity;
import com.example.anarts6.airplaneticketreservation.MySQLiteHelper;
import com.example.anarts6.airplaneticketreservation.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anarts6 on 5/11/16.
 */
public class ReserveSeat extends Activity implements View.OnClickListener{
    private ListView listView;
    MySQLiteHelper db;
    private SimpleCursorAdapter dataAdapter;
    private Button createSearchButton;
    public static ArrayList<Flight> flightsFound = new ArrayList<Flight>();
    private  ArrayList<Flight> flights;
    String departure, arrival;
    private int attempTimes = 0;
    private EditText cinUsername;
    private String inputUsername;
    private EditText cinPassword;
    private String inputPassword;
    private int inputTicketAmount;
    private Button createLoginButton;
    private String data = "";
    private String flightNoReserved, departureReserved, arrivalReserved, departureTimeReserved;
    private String priceReserved;
    private Flight flightPurchased;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_seat_page);
        //instantiate button
        createSearchButton = (Button) findViewById(R.id.SearchButton);
        createSearchButton.setOnClickListener(this);

        //instantiate loginbutton
       // createLoginButton = (Button) findViewById(R.id.loginButton);
       // createLoginButton.setOnClickListener(this);

        //instance of database flightreservation
        db = new MySQLiteHelper(this);
        db.getWritableDatabase();
       // displayListView();
    }
    private void displayListView() {


       ArrayList<Flight> flights = flightsFound;
        CustomFlightsAdapter adapter = new CustomFlightsAdapter(this, flights);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l){


                flightPurchased = new Flight();
                flightPurchased.setFlightNumber(((TextView) v.findViewById(R.id.flightNo)).getText().toString());
                flightPurchased.setDepartureTime(((TextView)v.findViewById(R.id.departureTimeID)).getText().toString());
                flightPurchased.setDeparture(((TextView)v.findViewById(R.id.departureID)).getText().toString());
                flightPurchased.setArrival(((TextView)v.findViewById(R.id.arrivalID)).getText().toString());
                flightPurchased.setPrice(Double.parseDouble(((TextView)v.findViewById(R.id.priceID)).getText().toString()));
                flightPurchased.setTicketAmount(inputTicketAmount);
                flightPurchased.setTotal();

                Log.d("clicked item", "clicked");
               final Dialog dialog = new Dialog(ReserveSeat.this);
                dialog.setContentView(R.layout.login);
                createLoginButton = (Button) dialog.findViewById(R.id.loginButton);
                createLoginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            cinPassword = (EditText) dialog.findViewById(R.id.passwordLogin);
                            cinUsername = (EditText)dialog.findViewById(R.id.usernameLogin);
                            inputUsername = cinUsername.getText().toString();
                            inputPassword = cinPassword.getText().toString();
                            boolean validLogin = verifyLogin(inputUsername, inputPassword);
                            if(validLogin){
                                AlertDialog.Builder dialogConfirm = new AlertDialog.Builder(ReserveSeat.this);
                                dialogConfirm.setMessage(String.valueOf(flightPurchased));
                                // dialogConfirm.setContentView(R.layout.reserve_seat_confirmation);
                                dialogConfirm.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                dialogConfirm.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                dialogConfirm.show();
                                //  Window window = dialogConfirm.getWindow();
                                // window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

                            }
                            else {
                             //   Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                            }
                    }
                });

                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

            }
        });


    }
    @Override
    public void onClick(View view) {
        EditText cinDeparture;
        EditText cinArrival;
        EditText cinTicketAmount;
        String inputDeparture;
        String inputArrival;
        if(view.getId()==R.id.SearchButton) {
            cinDeparture = (EditText) findViewById(R.id.departureEntry);
            cinArrival = (EditText) findViewById(R.id.arrivalEntry);
            cinTicketAmount = (EditText) findViewById(R.id.ticketAmountEntry);
            inputDeparture = cinDeparture.getText().toString();
            inputArrival = cinArrival.getText().toString();
            inputTicketAmount = Integer.parseInt(cinTicketAmount.getText().toString());
            boolean isValidFlight = checkFlight(inputDeparture,inputArrival,inputTicketAmount);
            if(isValidFlight){
                Toast.makeText(this, "EXISTS", Toast.LENGTH_SHORT).show();
                displayListView();
              //  displayListResults( inputDeparture, inputArrival);
            }
            else{
                Toast.makeText(this, "Not a valid flight try again", Toast.LENGTH_SHORT).show();
                attempTimes++;
            }
        }
        if(attempTimes==2){
            Toast.makeText(this, "Flight does not exists", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

    }
    public boolean checkFlight(String inputDeparture, String inputArrival, int inputTicketAmount){
        db.getReadableDatabase();
        ArrayList<Flight> flights = db.getAllFlights();
        for(int i = 0; i < flights.size(); i++){
            if(flights.get(i).getDeparture().equalsIgnoreCase(inputDeparture) && flights.get(i).getArrival().equalsIgnoreCase(inputArrival) && inputTicketAmount<= flights.get(i).getCapacity() && inputTicketAmount>0){
                //flightsFound.add(new Flight(flights.get(i).getFlightNumber(), flights.get(i).getDeparture(), flights.get(i).getArrival(), flights.get(i).getDepartureTime(), flights.get(i).getCapacity(), flights.get(i).getPrice()));
                flightsFound.add(flights.get(i));
                return true;
            }
            //  Log.i("Account: " + i, valueOf(accounts.get(i).getUsername()));
        }
        return false;
    }
    public boolean verifyLogin(String cinInputUsername, String cinInputPassword){
        ArrayList<Account> accounts = db.getAllAccounts();
        for(int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getUsername().equals(cinInputUsername) && accounts.get(i).getPassword().equals(cinInputPassword))
                return true;
        }
        return false;

    }
}
