package com.cap.projet5iss;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Nana on 04/12/2015.
 */

public class Login2  extends Activity {

    ImageButton ib_register ;
    ImageButton ib_goBack ;
    String name;
    String phone;
    String chipid;
    Boolean isMale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_2);
        initialize();

        final Login2 thisclass = this;
        ib_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisclass, Login.class);
                startActivity(intent);
            }
        });

        ib_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisclass, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initialize(){
        ib_register = (ImageButton) findViewById(R.id.ib_register);
        ib_goBack = (ImageButton) findViewById(R.id.ib_backToLogin);
    }
}