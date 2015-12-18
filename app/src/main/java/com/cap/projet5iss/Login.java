package com.cap.projet5iss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Nana on 01/12/2015.
 */
public class Login extends Activity implements View.OnClickListener{

    ImageButton bLoginNext;
    EditText etUsername, etPhone, etChipNum;
    RadioButton rbH, rbF  ;
    TextView tvRegisterLink;
    Boolean isMale ;
    UserLocalStore userLocalStore;
    String name ;
    String phone ;
    String chipid ;


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ib_next:

                Log.i("LOGIN", "Clicked on button NEXT");

                // On regarde si on a "Femme" de coché. Si oui, change la valeur du boolean
                if (rbF.isChecked()){
                    isMale = false ;
                }

                String gender ;
                if (isMale){
                    gender = "H";
                }else{
                    gender = "F";
                }
                Log.i("LOGIN", "Creating new user : Name=" + etUsername.getText() +
                        " ; Phone=" + etPhone +
                        " ; Chip=" + etChipNum +
                        " ; Genrer="+gender);

                // On récupère les valeurs des champs
                this.name = etUsername.getText().toString();
                this.phone = etPhone.getText().toString();
                this.chipid = etChipNum.getText().toString();

                Intent intent = new Intent(this, Login2.class);
                startActivity(intent);

                // Je commente car pour le moment ça fait planter l'appli
                /*
                User user=new User(null, null);
                UserLocalStore.StoreUserData(user);
                UserLocalStore.setUserLoggedIn(true);*/

            /*case tvRegisterLink:
                startActivity(new Intent(this, Register.class));
                break;*/
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initialize();
        userLocalStore= new UserLocalStore(this);
        bLoginNext.setOnClickListener(this);
        /*tvRegisterLink.setOnClickListener(this);*/
    }

    /**
     * Lie toutes les views aux objets de notre classe
     */
    private void initialize() {
        etUsername = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etChipNum = (EditText) findViewById(R.id.et_serialnumber);
        rbF = (RadioButton) findViewById(R.id.rb_femme);
        rbH = (RadioButton) findViewById(R.id.rb_homme);
        bLoginNext = (ImageButton) findViewById(R.id.ib_next);
        /*tvRegisterLink= (TextView) findViewById(R.id. tvRegisterLink);*/
        isMale = true;

    }

}
