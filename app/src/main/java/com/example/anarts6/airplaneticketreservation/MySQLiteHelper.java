package com.example.anarts6.airplaneticketreservation;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by anarts6 on 5/9/16.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    //Database Name -FlightReservation
    private static final String DATABASE_NAME = "FlightReservation";

    private static final String TABLE_ACCOUNTS = "accounts";
    private static final String TABLE_FLIGHTS = "flights";
    private static final String TABLE_TRANSACTIONS = "transactions";

    //columns for account table
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";

    //columns for flights table
    private static final String KEY_ID2 ="_id";
    private static final String KEY_FLIGHT_NO = "flightNo";
    private static final String KEY_DEPARTURE = "departure";
    private static final String KEY_ARRIVAL = "arrival";
    private static final String KEY_DEPARTURE_TIME = "departureTime";
    private static final String  KEY_FLIGHT_CAPACITY = "flightCapacity";
    private static final String KEY_PRICE = "price";

    //columns for transactions table
    private static final String KEY_ID3 = "id";
    private static final String KEY_USERNAME2 = "username";
    private static final String KEY_FLIGHTNO = "flightNo";
    private static final String KEY_DEPARTURE2 = "departure";
    private static final String KEY_ARRIVAL2 = "arrival";
    private static final String KEY_DEPARTURETIME = "departureTime";
    private static final String KEY_NUM_TICKETS = "numTickets";
    private static final String KEY_TOTAL ="total";
    private static final String KEY_DATE_TIME = "dateTime";
    private static final String KEY_TRANSACTION_TYPE = "transactionType";

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Log TAG for debugging purpose
    private static final String TAG = "SQLiteAppLog";

    // Constructor
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        //SQL statement to create account table
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE accounts (" +"id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "username TEXT,"+ "password TEXT)";

        //sql statement to create flights table;
        String CREATE_FLIGHTS_TABLE = "CREATE TABLE flights(" +" _id INTEGER PRIMARY KEY AUTOINCREMENT," +"flightNo TEXT," +
                "departure TEXT," + "arrival TEXT, " +" departureTime TEXT," +
                "flightCapacity INTEGER," + "price DOUBLE)";

        String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE transactions(" +"id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "username TEXT," + "flightNo TEXT," +" departure TEXT," + "arrival TEXT," +" departureTime TEXT, " +
                " numTickets INTEGER," + "total DOUBLE," + "transactionType TEXT, " + "dateTime TEXT)";
        //execute an sql statement to create table
        db.execSQL(CREATE_ACCOUNTS_TABLE);
        db.execSQL(CREATE_FLIGHTS_TABLE);
        db.execSQL(CREATE_TRANSACTIONS_TABLE);
    }
    // onUpdate() is invoked when you upgrade the database scheme.
    // Don’t consider it seriously for the sample app
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS accounts");
        db.execSQL("DROP TABLE IF EXISTS flights");
        db.execSQL("DROP TABLE IF EXISTS transactions");
        //create fresh new accounts table
        this.onCreate(db);
    }

    public void addAccount(Account account){
        Log.d(TAG, "addAccount() - " + account.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, account.getUsername()); // get username
        values.put(KEY_PASSWORD, account.getPassword()); // get password

        // 3. insert
        db.insert(TABLE_ACCOUNTS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close - release the reference of writable DB
        db.close();
    }
    public void addFlight(Flight flight){
        Log.d(TAG, "addFlight() - " + flight.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isInDB = checkFlightRepeat(flight);
        if(!isInDB){
            // 2. create ContentValues to add key "column"/value
            ContentValues values = new ContentValues();
            values.put(KEY_FLIGHT_NO, flight.getFlightNumber()); // get flight number
            values.put(KEY_DEPARTURE, flight.getDeparture()); // get departure
            values.put(KEY_ARRIVAL, flight.getArrival());
            values.put(KEY_DEPARTURE_TIME, flight.getDepartureTime());
            values.put(KEY_FLIGHT_CAPACITY, flight.getCapacity());
            values.put(KEY_PRICE, flight.getPrice());

            // 3. insert
            db.insert(TABLE_FLIGHTS, // table
                    null, //nullColumnHack
                    values); // key/value -> keys = column names/ values = column values

            // 4. close - release the reference of writable DB
            db.close();
        }

    }
    // Get all books from the database
    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> accounts = new ArrayList<Account>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_ACCOUNTS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Account account = null;
        if (cursor.moveToFirst()) {
            do {
                account = new Account();
                account.setId(Integer.parseInt(cursor.getString(0)));
                account.setUsername(cursor.getString(1));
                account.setPassword(cursor.getString(2));

                // Add book to books
                accounts.add(account);
            } while (cursor.moveToNext());
        }

        Log.d(TAG, "getAllAccounts() - " + accounts.toString());

        // return books
        return accounts;
    }
    // Get all flights from the database
    public ArrayList<Flight> getAllFlights() {
        ArrayList<Flight> flights = new ArrayList<Flight>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_FLIGHTS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build flight and add it to list
        Flight flight = null;
        if (cursor.moveToFirst()) {
            do {
                flight = new Flight();
                flight.setId(cursor.getString(0));
                flight.setFlightNumber(cursor.getString(1));
                flight.setDeparture(cursor.getString(2));
                flight.setArrival(cursor.getString(3));
                flight.setDepartureTime(cursor.getString(4));
                flight.setCapacity(Integer.parseInt(cursor.getString(5)));
                flight.setPrice(Double.parseDouble(cursor.getString(6)));

                // Add flight to flights arraylist
                flights.add(flight);
            } while (cursor.moveToNext());
        }

        Log.d(TAG, "getAllFlights() - " + flights.toString());

        // return flights
        return flights;
    }
    public int updateAccount(Account account){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("username", account.getUsername()); // get username
        values.put("password", account.getPassword()); // get password

        // 3. updating row
        int i = db.update(TABLE_ACCOUNTS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(account.getId()) }); //selection args

        // 4. close
        db.close();

        return i;
    }
    //Deleting an Account
    public void deleteAccount(Account account) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_ACCOUNTS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(account.getId()) });

        // 3. close
        db.close();

        Log.d(TAG, "deleteAccount() - " + account.toString());
    }
    public Cursor fetchAllFlights(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {KEY_ID2, KEY_FLIGHT_NO, KEY_DEPARTURE,
        KEY_ARRIVAL, KEY_DEPARTURE_TIME, KEY_PRICE, KEY_FLIGHT_CAPACITY};
        //String query = "SELECT  * FROM " + TABLE_FLIGHTS;
        Cursor mCursor = db.query(TABLE_FLIGHTS, columns, null, null, null, null, null);

        if(mCursor !=null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public Cursor fetchSomeFlights(String departure, String arrival) throws SQLException{
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(departure == null || arrival == null || departure.length() == 0 || arrival.length() == 0){
            String[] columns = new String[] {KEY_ID2, KEY_FLIGHT_NO, KEY_DEPARTURE,
                    KEY_ARRIVAL, KEY_DEPARTURE_TIME, KEY_PRICE, KEY_FLIGHT_CAPACITY};
            cursor = db.query(TABLE_FLIGHTS,columns, null, null, null,null, null );
        }
        else {
            //  String[] columns = new String[] {String.valueOf(departure), String.valueOf(arrival)};
            String[] selectionArgs = {departure, arrival};
            String selection = "departure=? AND arrival=?";
            String[] columns = new String[]{KEY_ID2, KEY_FLIGHT_NO, KEY_DEPARTURE,
                    KEY_ARRIVAL, KEY_DEPARTURE_TIME, KEY_PRICE, KEY_FLIGHT_CAPACITY};
            cursor = db.query(TABLE_FLIGHTS, columns, selection, selectionArgs, null, null, null);
        }

        if(cursor !=null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public boolean checkFlightRepeat(Flight flight){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Flight> allDBFlights = getAllFlights();
        for(int i = 0; i <allDBFlights.size(); i++){
            if(allDBFlights.get(i).getArrival().equalsIgnoreCase(flight.getArrival()) && allDBFlights.get(i).getDeparture().equalsIgnoreCase(flight.getDeparture()) &&
                    allDBFlights.get(i).getDepartureTime().equalsIgnoreCase(flight.getDepartureTime())&& allDBFlights.get(i).getCapacity() == (flight.getCapacity()) && allDBFlights.get(i).getPrice() == flight.getPrice()
                    && allDBFlights.get(i).getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())){
                return true;
            }
        }
        return false;

    }
  //  public Cursor query (boolean distinct, String table, String[] columns, )



}
