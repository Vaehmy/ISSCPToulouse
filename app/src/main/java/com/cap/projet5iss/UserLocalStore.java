package com.cap.projet5iss;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Nana on 01/12/2015.
 */
public class UserLocalStore{
    public static final String SP_NAME ="userDetails";
    static SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public static void StoreUserData(User user){
        SharedPreferences.Editor spEditor= userLocalDatabase.edit();
        spEditor.putString("name", user.name);
        spEditor.putInt("age", user.age);
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.commit();
    }

    public User getLoggedInUser(){
        String name =userLocalDatabase.getString("name", "");
        int age = userLocalDatabase.getInt("age", -1);
        String username =userLocalDatabase.getString("username", "");
        String password =userLocalDatabase.getString("password","");

        User storedUser= new User(name, age, username, password);
        return storedUser;
    }

    public static void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor= userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public static boolean getUserLoggedIn() {
        if (userLocalDatabase.getBoolean("loggedIn",false)== true){
            return true;
        } else{
            return false;
        }}

    public void clearUserData(){
        SharedPreferences.Editor spEditor= userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();

    }
}
