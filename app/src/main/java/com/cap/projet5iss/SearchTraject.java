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
import java.util.Arrays;

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

    // fonction qui retourne le nombre de mot dans une phrase
   /* public int nombreDeMots(String phrase){
        int i=0;
        int nbrMot=1;
        for( i=0; i < phrase.length(); i++){
            if(phrase.charAt(i) == ' '){ nbrMot++;}
        }
        return nbrMot;
    }*/
   // Fonction qui me permet de remplir un tableau avec les mots d'une phrase donnée
   /* public void remplirTabl(String phrase,char motTab[]){
        int prochainMot=0, i=0, j=0;
        String buffer="";
        for(i = 0; i < phrase.length(); i++)
        {
            if(phrase.charAt(i) == ' ') // Si on rencontre un espace dans la phrase on change de case car chaque case de motTab correspond à un mot
            {
                motTab[j] = '\0';
                prochainMot++;
                j = 0;
            }
            else if(phrase.charAt(i) != '\0')
            {
                motTab[j] = phrase.charAt(i);
                j++;
            }
        }
        motTab[j] = '\0';
    }*/
    
    public void parcourirJSON(){
        jARoutes = jHAllRoutes.getjAallRoutes();

        for (int i=0; i<jARoutes.length();i++){
            try {
                routeObj = jARoutes.getJSONObject(i);

                String departJSON = routeObj.getString("departPlace");
                String arriveJSON = routeObj.getString("arrivePlace");
                String departEdit = adresseDepart;
                String arriveEdit = adresseDestination;

                ArrayList<String> departJSONMots = new ArrayList<String>(Arrays.asList(departJSON.split(" ")));
                ArrayList<String> arriveJSONMots = new ArrayList<String>(Arrays.asList(arriveJSON.split(" ")));
                ArrayList<String> departEditMots = new ArrayList<String>(Arrays.asList(departEdit.split(" ")));
                ArrayList<String> arriveEditMots = new ArrayList<String>(Arrays.asList(arriveEdit.split(" ")));

                for(int pos = 0; pos < departJSONMots.size(); pos++){
                    String s1 = (String)departJSONMots.get(pos);
                    String s2 = (String)departEditMots.get(pos);
                    String s3 = (String)arriveJSONMots.get(pos);
                    String s4 = (String)arriveEditMots.get(pos);

                    if( s1.compareTo(s2) == 0 || departJSONMots.contains(s2) )
                    {
                        Log.i("SEARCH ACTIVITY", "Comparing \""+routeObj.getString("departPlace")+ "\" with \""+adresseDepart+"\" ; MATCH FOUND" );
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));

                    }else if( s3.compareTo(s4) == 0 || arriveJSONMots.contains(s3)){
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));
                    }
                    else if( (routeObj.getString("departHour").equals(hDepart))){
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));
                    }
                    else if( (routeObj.getString("arriveHour").equals(hDestination))){
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));
                    }
                    else if( (routeObj.getString("arriveHour").equals(hDestination)) && (routeObj.getString("departHour").equals(hDepart)) ){
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));
                    }
                    else if( (routeObj.getString("arriveHour").equals(hDestination)) && ( s3.compareTo(s4) == 0 || arriveJSONMots.contains(s3)) ){
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));
                    }
                    else if( (routeObj.getString("arriveHour").equals(hDestination)) && ( s1.compareTo(s2) == 0 || departJSONMots.contains(s2) ) ){
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));
                    }
                    else if( (routeObj.getString("departHour").equals(hDepart)) && ( s3.compareTo(s4) == 0 || arriveJSONMots.contains(s3)) ){
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));
                    }
                    else if( (routeObj.getString("departHour").equals(hDepart)) && ( s1.compareTo(s2) == 0 || departJSONMots.contains(s2) ) ){
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));
                    }
                    else if( ( s3.compareTo(s4) == 0 || arriveJSONMots.contains(s3)) && ( s1.compareTo(s2) == 0 || departJSONMots.contains(s2) ) ){
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));
                    }
                    else if( ( s3.compareTo(s4) == 0 || arriveJSONMots.contains(s3)) && ( s1.compareTo(s2) == 0 || departJSONMots.contains(s2) )&& (routeObj.getString("departHour").equals(hDepart)) ){
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));
                    }
                    else if( ( s3.compareTo(s4) == 0 || arriveJSONMots.contains(s3)) && ( s1.compareTo(s2) == 0 || departJSONMots.contains(s2) )&& (routeObj.getString("arriveHour").equals(hDestination)) ){
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));
                    }
                    else if( ( s3.compareTo(s4) == 0 || arriveJSONMots.contains(s3)) && (routeObj.getString("departHour").equals(hDepart))&& (routeObj.getString("arriveHour").equals(hDestination)) ){
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));
                    }
                    else if( ( s1.compareTo(s2) == 0 || departJSONMots.contains(s2) ) && (routeObj.getString("departHour").equals(hDepart))&& (routeObj.getString("arriveHour").equals(hDestination)) ){
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));
                    }
                    else if( ( s1.compareTo(s2) == 0 || departJSONMots.contains(s2) ) && ( s3.compareTo(s4) == 0 || arriveJSONMots.contains(s3)) && (routeObj.getString("departHour").equals(hDepart))&& (routeObj.getString("arriveHour").equals(hDestination)) ){
                        Log.i("SEARCH ACTIVITY", "Found a match for user #" + routeObj.getString("idUser"));
                    }
                    else {
                        Log.i("SEARCH ACTIVITY", "Comparing \""+routeObj.getString("departPlace")+ "\" with \""+adresseDepart+"\" ; NO RESULT" );

                    }
                }

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

