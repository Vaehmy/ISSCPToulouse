package com.cap.projet5iss.handlers;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Nana on 20/12/2015.
 */

public class JSONHandler{

    Context context ;
    String file ;

    AllDriversHandler allDriversHandler ;

    public JSONHandler(Context context, String file) {
        this.context = context ;
        this.file = file ;
    }

    public AllDriversHandler getAllDriversHandler() {
        return allDriversHandler;
    }

    public ArrayList jsonFile2ArrayList(Context context, String file) throws JSONException {
        String jsonString = loadJSONFromAsset(context, file);
        JSONObject json = new JSONObject(jsonString);

        if (file.equals("allDrivers.json")){
            this.allDriversHandler = new AllDriversHandler(json,context);
            return allDriversHandler.allDriversfromJSON(json);
        }else{
            return null ;
        }

    }

    public String loadJSONFromAsset(Context context, String file) {
        String json = null;
        try {
            InputStream is = context.getResources().getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
