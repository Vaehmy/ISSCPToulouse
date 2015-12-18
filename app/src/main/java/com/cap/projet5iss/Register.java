package com.cap.projet5iss;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Nana on 01/12/2015.
 */
public class Register  extends Activity implements View.OnClickListener{

    Button bRegister;
    EditText etName, etAge, etUsername, etPassword;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRegister:
                String name= etName.getText().toString();
                int age= Integer.parseInt(etAge.getText().toString());
                String username= etUsername.getText().toString();
                String password= etPassword.getText().toString();
                User registredData= new User(name, age, username, password);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName= (EditText) findViewById(R.id.etName);
        etAge= (EditText) findViewById(R.id.etAge);
        etUsername= (EditText) findViewById(R.id.etUsername);
        etPassword= (EditText) findViewById(R.id.etPassword);
        bRegister= (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(this);
    }
}
