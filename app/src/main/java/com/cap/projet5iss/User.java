package com.cap.projet5iss;

/**
 * Created by Nana on 01/12/2015.
 */
public class User  {
    String name, username, password;
    int age;
    public User(String name, int age,  String username, String password){
        this.name=name;
        this.age =age;
        this.username= username;
        this.password= password;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.name ="";
        this.age =-1;
    }

}
