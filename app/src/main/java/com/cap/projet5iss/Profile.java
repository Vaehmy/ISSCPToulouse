package com.cap.projet5iss;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Nana on 02/12/2015.
 */

public class Profile extends Activity {

    ImageButton b_Profile;
    ImageButton b_Edit;
    TextView tv_addMailEditmode;
    TextView tv_addMailViewmode;
    TextView tv_addTelEditmode;
    TextView tv_addTelViewmode;
    EditText et_profileName;
    TextView tv_profileName;
    EditText et_profileStreet;
    TextView tv_profileStreet;
    EditText et_profilePostalCode;
    TextView tv_profilePostalCode;
    EditText et_profileCity;
    TextView tv_profileCity;
    EditText et_profileMail;
    TextView tv_profileMail;
    EditText et_profileTel;
    TextView tv_profileTel;
    Boolean inEditMode;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
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
                    b_Edit.setBackgroundResource(R.drawable.ok);
                }
            }
        });
    }



    private void initialize() {
        inEditMode = false;
        tv_addMailEditmode = (TextView) findViewById(R.id.tv_addMailet);
        tv_addMailViewmode = (TextView) findViewById(R.id.tv_addMailtv);
        tv_addTelEditmode = (TextView) findViewById(R.id.tv_addTelet);
        tv_addTelViewmode = (TextView) findViewById(R.id.tv_addTeltv);
        b_Profile = (ImageButton) findViewById(R.id.ib_profilePicture);
        b_Edit = (ImageButton) findViewById(R.id.b_editProfile);
        et_profileName = (EditText) findViewById(R.id.et_profileName);
        tv_profileName = (TextView) findViewById(R.id.tv_profileName);
        et_profileStreet = (EditText) findViewById(R.id.et_profileRue);
        tv_profileStreet = (TextView) findViewById(R.id.tv_profileRue);
        et_profilePostalCode = (EditText) findViewById(R.id.et_profilePostal);
        tv_profilePostalCode = (TextView) findViewById(R.id.tv_profilePostal);
        et_profileCity = (EditText) findViewById(R.id.et_profileCity);
        tv_profileCity = (TextView) findViewById(R.id.tv_profileCity);
        et_profileMail = (EditText) findViewById(R.id.et_profileMail);
        tv_profileMail = (TextView) findViewById(R.id.tv_profileMail);
        et_profileTel = (EditText) findViewById(R.id.et_profileTel);
        tv_profileTel = (TextView) findViewById(R.id.tv_profileTel);

    }

    /***
     * Fait apparaître/disparaitre les TextViews / EditTexts en fonction de notre mode
     *
     * @param boolEdit Vrai si on est en mode "Editer"
     */
    private void displayeditmode(Boolean boolEdit) {


        if (!boolEdit) {
            this.et_profileCity.setVisibility(View.GONE);
            this.et_profileMail.setVisibility(View.GONE);
            this.et_profileName.setVisibility(View.GONE);
            this.et_profilePostalCode.setVisibility(View.GONE);
            this.et_profileStreet.setVisibility(View.GONE);
            this.tv_addMailEditmode.setVisibility(View.GONE);
            this.et_profileTel.setVisibility(View.GONE);
            this.tv_addMailViewmode.setVisibility(View.VISIBLE);
            this.tv_addTelEditmode.setVisibility(View.GONE);
            this.tv_addTelViewmode.setVisibility(View.VISIBLE);
            this.tv_profileCity.setVisibility(View.VISIBLE);
            this.tv_profileMail.setVisibility(View.VISIBLE);
            this.tv_profileName.setVisibility(View.VISIBLE);
            this.tv_profilePostalCode.setVisibility(View.VISIBLE);
            this.tv_profileStreet.setVisibility(View.VISIBLE);
            this.tv_profileTel.setVisibility(View.VISIBLE);
        } else {
            this.et_profileCity.setVisibility(View.VISIBLE);
            this.et_profileTel.setVisibility(View.VISIBLE);
            this.et_profileMail.setVisibility(View.VISIBLE);
            this.et_profileName.setVisibility(View.VISIBLE);
            this.et_profilePostalCode.setVisibility(View.VISIBLE);
            this.et_profileStreet.setVisibility(View.VISIBLE);
            this.tv_addMailEditmode.setVisibility(View.VISIBLE);
            this.tv_addMailViewmode.setVisibility(View.GONE);
            this.tv_addTelEditmode.setVisibility(View.VISIBLE);
            this.tv_addTelViewmode.setVisibility(View.GONE);
            this.tv_profileCity.setVisibility(View.GONE);
            this.tv_profileMail.setVisibility(View.GONE);
            this.tv_profileName.setVisibility(View.GONE);
            this.tv_profilePostalCode.setVisibility(View.GONE);
            this.tv_profileStreet.setVisibility(View.GONE);
            this.tv_profileTel.setVisibility(View.GONE);
        }
    }

    /**
     * Valide les changements de données du profil
     * Envoi vers la BDD
     */
    private void validateChanges() {

        /*
         * FONCTION A COMPLETER AVEC
         * - des fonctions de vérification des données
         * - un envoi des données actualisées vers la BDD
         *************************************************/

        // Les lignes suivantes seront à supprimer plus tard
        this.tv_profileName.setText(this.et_profileName.getText());
        this.tv_profileStreet.setText(this.et_profileStreet.getText());
        this.tv_profilePostalCode.setText(this.et_profilePostalCode.getText());
        this.tv_profileCity.setText(this.et_profileCity.getText());
        this.tv_profileMail.setText(this.et_profileMail.getText());

    }

}
