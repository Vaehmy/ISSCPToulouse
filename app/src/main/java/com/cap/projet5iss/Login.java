package com.cap.projet5iss;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Nana on 01/12/2015.
 */
public class Login extends Activity {

    ImageButton bLoginNext;
    EditText etUsername, etPhone, etChipNum, etStreet, etPostalCode, etCity;
    RadioButton rbH, rbF  ;
    String name, phone, chipid, street, postalcode, city, sexe;
    Boolean isMale, isLoggedIn ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initialize();

        rbH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rbF.isChecked()) {
                    rbH.setChecked(false);
                    rbF.setChecked(true);
                    isMale = false;
                } else {
                    rbF.setChecked(false);
                    rbH.setChecked(true);
                    isMale = true;
                }
            }
        });


        rbH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbH.isChecked()){
                    rbH.setChecked(false);
                    rbF.setChecked(true);
                    isMale = false ;
                }else{
                    rbF.setChecked(false);
                    rbH.setChecked(true);
                    isMale = true ;
                }
            }
        });

        bLoginNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LOGIN", "Clicked on NEXT button");

                JSONObject obj = new JSONObject();

                try {
                    obj.put("name", etUsername.getText());
                    obj.put("addStreet", etStreet.getText());
                    obj.put("addCity", etCity.getText());
                    obj.put("addPosteCode", etPostalCode.getText());
                    obj.put("chiId", etChipNum.getText());
                    String sexe;
                    if (isMale) {
                        sexe = "M";
                    } else {
                        sexe = "F";
                    }
                    obj.put("sexe", sexe);
                    obj.put("telephone", etPhone.getText());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //File file = new File(Environment.getExternalStorageDirectory() + "/DCToulouse/", "userID.json");
                String toFile;
                toFile = obj.toString();
                Log.i("LOGIN", "Creating : "
                        + toFile);

                try {
                    FileWriter out = new FileWriter(new File(Environment.getExternalStorageDirectory() + "/DCToulouse/", "userID.json"));
                    out.write(toFile);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                go();
            }
        });

    }

    public void go(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initialize() {
        // Creating the directory for our application if it does not already exist
        File file = new File(Environment.getExternalStorageDirectory(), "/DCToulouse/");
        if (!file.exists()) {
            file.mkdirs();
        }

        etUsername = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etChipNum = (EditText) findViewById(R.id.et_serialnumber);
        etStreet = (EditText) findViewById(R.id.et_addStreet);
        etPostalCode = (EditText) findViewById(R.id.et_addPostalCode);
        etCity = (EditText) findViewById(R.id.et_addCity);
        rbF = (RadioButton) findViewById(R.id.rb_femme);
        rbH = (RadioButton) findViewById(R.id.rb_homme);
        rbH.setChecked(true);
        rbF.setChecked(false);
        bLoginNext = (ImageButton) findViewById(R.id.ib_next);
        isMale = true;
    }

}
