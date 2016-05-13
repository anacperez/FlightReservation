package com.example.anarts6.airplaneticketreservation;

/**
 * Created by anarts6 on 5/10/16.
 */
public class Account {
    private  String username;
    private int id;
    private String password;
    public Account(){

    }
    public Account(String username, String password){
        this.username = username;
        this.password = password;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setId(int id){
        this.id = id;
    }
    @Override
    public String toString(){
        return "Account [id=" + id + ", username=" + username + ", password=" + password +"]";
    }
    public String getUsername() {return this.username;}
    public String getPassword() {return this.password;}
    public int getId() {return this.id;}

}
