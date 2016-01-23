package com.cap.projet5iss;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cap.projet5iss.handlers.JSONHandler;
import com.cap.projet5iss.handlers.RoutesHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ChaySi on 19/01/2016.
 */
public class SearchTraject extends Activity {


    Button etButton;
    EditText etDepartTraject, etArriveTraject, etHeureDepart, etHeureArrive ;
    String adresseDepart ;
    String adresseDestination ;
    String hDepart ;
    String hDestination ;
    RoutesHandler jHAllRoutes ;
    JSONArray jARoutes ;
    JSONObject routeObj ;

    protected void onStart() {
        super.onStart();
    }

    public void getValeursChamps() {
        // On récupère les valeurs des champs
        this.adresseDepart = etDepartTraject.getText().toString();
        this.adresseDestination = etArriveTraject.getText().toString();
        this.hDepart = etHeureDepart.getText().toString();
        this.hDestination = etHeureArrive.getText().toString();
    }

    public void parcourirJSON(){
        jARoutes = jHAllRoutes.getjAallRoutes();

        for (int i=0; i<jARoutes.length();i++){
            try {
                routeObj = jARoutes.getJSONObject(i);
                if((routeObj.getString("departPlace")== adresseDepart)
                      /* || (routeObj.getString("arrivePlace")== adresseDestination)
                       || (routeObj.getString("departHour")== hDepart)
                       || (routeObj.getString("arriveHour")== hDestination)*/)
                {
                    // routeObj.getString("idUser");
                    Log.i("PARSING TEST", routeObj.getString("idUser"));
                    //Log.i("TROUVE DANS LA BD", "C'est OK redirection");
                } else{
                    Log.i("NE TROUVE RIEN", "C'est OK sauf qu'il trouve rien dans le json");
                }

                // toute comparaison et voir ce que ça affiche comme résultats
                // Gestion d'erreur


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public String rechercher(){
        String result ="0";

        // On récupère les valeurs des champs
        getValeursChamps();

        // On fait les GET correspondants
        parcourirJSON();

        // On déduit l'ID de l'utilisateur

        return result;
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_traject);
        initialize();

        etButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SEARCH TRAJECT", "Clicked on Valider");
                Intent intent= new Intent();
                intent.putExtra("IDUser",rechercher());
                setResult(1, intent);
                finish();
            }
        });
    }

    /**
     * Lie toutes les views aux objets de notre classe
     */
    private void initialize() {

        etDepartTraject = (EditText) findViewById(R.id.et_depart);
        etArriveTraject = (EditText) findViewById(R.id.et_destination);
        etHeureDepart = (EditText) findViewById(R.id.et_Hdepart);
        etHeureArrive = (EditText) findViewById(R.id.et_Hdestination);
        etButton = (Button) findViewById(R.id.bt_button);

        jHAllRoutes = new RoutesHandler(getApplicationContext());

    }

}

