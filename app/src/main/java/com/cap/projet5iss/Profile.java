package com.cap.projet5iss;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cap.projet5iss.handlers.JSONHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nana on 02/12/2015.
 */

public class Profile extends Activity {

    ImageButton b_Profile;
    ImageButton b_Edit;
    EditText et_profileName;
    EditText et_profileStreet;
    EditText et_profilePostalCode;
    EditText et_profileCity;
    EditText et_profileMail;
    EditText et_profileTel;
    Boolean inEditMode;
    JSONHandler jsonHandler ;
    JSONObject profileJSON ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        try {
            jsonHandler = new JSONHandler(getApplicationContext(),"");
            profileJSON = jsonHandler.localUserToObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initialize();
        displayeditmode(false);

        this.b_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inEditMode) {
                    inEditMode = false;
                    displayeditmode(false);
                    validateChanges();
                    b_Edit.setBackgroundResource(R.drawable.edit);
                } else {
                    inEditMode = true;
                    displayeditmode(true);
                    b_Edit.setBackgroundResource(R.drawable.confirm);
                }
            }
        });
    }



    private void initialize() {

        // TODO AJOUTER CHIP ID
        // TODO AJOUTER SEXE



        try {
            inEditMode = false;

            b_Profile = (ImageButton) findViewById(R.id.ib_profilePicture);
            b_Edit = (ImageButton) findViewById(R.id.b_editProfile);
            et_profileName = (EditText) findViewById(R.id.et_profileName);
            et_profileStreet = (EditText) findViewById(R.id.et_profileRue);
            et_profilePostalCode = (EditText) findViewById(R.id.et_profilePostal);
            et_profileCity = (EditText) findViewById(R.id.et_profileCity);
            et_profileMail = (EditText) findViewById(R.id.et_profileMail);
            et_profileTel = (EditText) findViewById(R.id.et_profileTel);

            et_profileName.setText(profileJSON.getString("name"));
            et_profileStreet.setText(profileJSON.getString("addStreet"));
            et_profilePostalCode.setText(profileJSON.getString("addPosteCode"));
            et_profileCity.setText(profileJSON.getString("addCity"));
            et_profileTel.setText(profileJSON.getString("tel"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void displayeditmode(Boolean boolEdit) {

        if (!boolEdit) {
            this.et_profileCity.setEnabled(false);
            this.et_profileMail.setEnabled(false);
            this.et_profileName.setEnabled(false);
            this.et_profilePostalCode.setEnabled(false);
            this.et_profileStreet.setEnabled(false);
            this.et_profileTel.setEnabled(false);
        } else {
            this.et_profileCity.setEnabled(true);
            this.et_profileTel.setEnabled(true);
            this.et_profileMail.setEnabled(true);
            this.et_profilePostalCode.setEnabled(true);
            this.et_profileStreet.setEnabled(true);
        }
    }

    /**
     * Enregistrement en JSON Local
     * Envoi vers la BDD
     */
    private void validateChanges() {

        // TODO Sauvegarde des données

        // TODO Envoi vers la DB
    }

}
