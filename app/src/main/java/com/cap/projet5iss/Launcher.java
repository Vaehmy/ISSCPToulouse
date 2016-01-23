package com.cap.projet5iss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.cap.projet5iss.handlers.JSONHandler;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.File;

/**
 * Created by Nana on 23/01/2016.
 */
public class Launcher extends Activity{

    TextView tv_username ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        tv_username = ( TextView ) findViewById(R.id.tv_launcher_username);
        Log.i("LAUNCHER", "Getting the user file ...");
        File file = new File(Environment.getExternalStorageDirectory() + "/DCToulouse/", "userID.json");

        if (!file.exists()){
            Log.i("LAUNCHER", "No registred user. Going to launch the login session");
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }else{
            Log.i("LAUNCHER", "User found. Going to launch main activity");
            JSONHandler jsonHandler = new JSONHandler(getApplicationContext(),"");
            try {
                tv_username.setText(jsonHandler.localUserToObject().getString("name"));
                wait(1500);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
