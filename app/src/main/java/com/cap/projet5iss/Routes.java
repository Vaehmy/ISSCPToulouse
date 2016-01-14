package com.cap.projet5iss;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

/**
 * Created by Nana on 10/01/2016.
 */
public class Routes extends Activity{

    EditText heureDepart, heureArrivee ;
    RadioButton rbTrajetregulier, rbTrajetoccas;
    CheckBox cbLundi, cbMardi, cbMercredi, cbJeudi, cbVendredi, cbSamedi, cbDimanche ;
    ImageButton addRoute;
    Boolean editModeON = false;
    EditText routeName,addDepart,addArrivee;

    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes);
        initialize();
        setListeners();
    }

    public void initialize(){
        heureArrivee = (EditText) findViewById(R.id.et_heureVers);
        heureArrivee.setEnabled(false);
        heureDepart = (EditText) findViewById(R.id.et_heureDepuis);
        heureDepart.setEnabled(false);
        rbTrajetoccas = (RadioButton)findViewById(R.id.rb_trajetOccas);
        rbTrajetoccas.setEnabled(false);
        rbTrajetregulier = (RadioButton)findViewById(R.id.rb_trajetRegul);
        rbTrajetregulier.setEnabled(false);
        cbLundi = (CheckBox)findViewById(R.id.cblund) ;
        cbMardi = (CheckBox)findViewById(R.id.cbmard) ;
        cbMercredi = (CheckBox)findViewById(R.id.cbmerc) ;
        cbJeudi = (CheckBox)findViewById(R.id.cbjeu) ;
        cbVendredi = (CheckBox)findViewById(R.id.cbvend) ;
        cbSamedi = (CheckBox)findViewById(R.id.cbsam) ;
        cbDimanche = (CheckBox)findViewById(R.id.cbdim) ;
        cbLundi.setEnabled(false);
        cbMardi.setEnabled(false);
        cbMercredi.setEnabled(false);
        cbJeudi.setEnabled(false);
        cbVendredi.setEnabled(false);
        cbSamedi.setEnabled(false);
        cbDimanche.setEnabled(false);
        addRoute = (ImageButton) findViewById(R.id.ib_addRoute);
        addArrivee = (EditText)findViewById(R.id.tv_addVers);
        addDepart = (EditText)findViewById(R.id.tv_addDepuis);
        routeName= (EditText)findViewById(R.id.tv_routeName);
        addDepart.setEnabled(false);
        addArrivee.setEnabled(false);
        routeName.setEnabled(false);
    }

    public void setListeners(){
        rbTrajetregulier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbTrajetoccas.setChecked(false);
                rbTrajetregulier.setChecked(true);
                cbLundi.setEnabled(true);
                cbMardi.setEnabled(true);
                cbMercredi.setEnabled(true);
                cbJeudi.setEnabled(true);
                cbVendredi.setEnabled(true);
                cbSamedi.setEnabled(true);
                cbDimanche.setEnabled(true);
            }
        });
        rbTrajetoccas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbTrajetoccas.setChecked(true);
                rbTrajetregulier.setChecked(false);
                cbLundi.setEnabled(false);
                cbMardi.setEnabled(false);
                cbMercredi.setEnabled(false);
                cbJeudi.setEnabled(false);
                cbVendredi.setEnabled(false);
                cbSamedi.setEnabled(false);
                cbDimanche.setEnabled(false);
            }
        });

        addRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editModeON){
                    editModeON = false ;
                    addRoute.setBackgroundResource(R.drawable.add);
                    setEditable();
                    decalerRoutes();
                    restPOSTRoute();
                }else{
                    editModeON = true ;
                    addRoute.setBackgroundResource(R.drawable.confirm);
                    setEditable();
                }

            }
        });

    }


    // TODO
    public void decalerRoutes(){
        // faire paser 2 en 3 etc
    }


    public void setEditable(){

        if(editModeON) {
            rbTrajetregulier.setEnabled(true);
            rbTrajetoccas.setEnabled(true);
            cbLundi.setEnabled(true);
            cbMardi.setEnabled(true);
            cbMercredi.setEnabled(true);
            cbJeudi.setEnabled(true);
            cbVendredi.setEnabled(true);
            cbSamedi.setEnabled(true);
            cbDimanche.setEnabled(true);
            addDepart.setEnabled(true);
            addArrivee.setEnabled(true);
            routeName.setEnabled(true);
        }else{
            rbTrajetregulier.setEnabled(false);
            rbTrajetoccas.setEnabled(false);
            cbLundi.setEnabled(false);
            cbMardi.setEnabled(false);
            cbMercredi.setEnabled(false);
            cbJeudi.setEnabled(false);
            cbVendredi.setEnabled(false);
            cbSamedi.setEnabled(false);
            cbDimanche.setEnabled(false);
            addDepart.setEnabled(false);
            routeName.setEnabled(false);
            addArrivee.setEnabled(false);
        }
    }

    // TODO
    public void restPOSTRoute(){
        // mettre nouvelle route en forme et l'envoyer dans une requête rest
    }
}
