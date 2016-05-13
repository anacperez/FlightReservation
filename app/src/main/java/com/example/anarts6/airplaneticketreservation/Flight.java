package com.example.anarts6.airplaneticketreservation;

/**
 * Created by anarts6 on 5/10/16.
 */
public class Flight {
    private String flightNumber;
    private String departure;
    private String departureTime;
    private String arrival;
    private double price;
    private int capacity;
    private String id;
    private double total;
    private int ticketAmount;
    private String username;
    public Flight(){

    }
    public Flight(String flightNumber, String departure, String arrival, String departureTime,
                  int capacity, double price){
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.departureTime = departureTime;
        this.price = price;
        this.capacity = capacity;
        this.arrival = arrival;

    }
    public void setUsername(String username){this.username = username;}
    public void setFlightNumber(String flightNumber){
        this.flightNumber = flightNumber;
    }
    public void setDeparture(String departure){
        this.departure = departure;
    }
    public void setDepartureTime(String departureTime){
        this.departureTime = departureTime;
    }
    public void setArrival(String arrival){
        this.arrival = arrival;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
    public void setId(String id){this.id = id;}
    public String getFlightNumber(){return this.flightNumber;}
    public String getDeparture(){return this.departure;}
    public String getArrival(){return this.arrival;}
    public String getDepartureTime(){return  this.departureTime;}
    public double getPrice(){return this.price;}
    public int getCapacity() {return this.capacity;}
    public String getId(){return this.id;}
    public void setTicketAmount(int ticketAmount){this.ticketAmount = ticketAmount;}
    public void setTotal(){
        calcTotal(price, ticketAmount);
    }
    public void calcTotal(double price, int ticketAmounts){
        this.total = price * ticketAmounts;
    }
    @Override
    public String toString(){
        return "Flight Information:" + "\n" +
                "Username: " + username +"\n" +
                "flightNumber: " + flightNumber + "\n" +
                "departure: " + departure + "\n" +
                "departureTime: " + departureTime + "\n" +
                "Arrival: " + arrival + "\n" +
                "total: " + total + "\n" +
                "tickets: " + ticketAmount;
    }
}
