package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class AffectationAerosoft extends AppCompatActivity {

    ListView listView;

    Button b1, piloteButton, volButton, avionButton, logoutButton;

    ArrayList<HashMap<String, String>> affectationsList;

    private SharedPreferences pref;

    private static String API_URL="";

    private PropertyReader propertyReader;

    private Properties properties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affectation_aerosoft);

        pref = getSharedPreferences("SessionLogin", Context.MODE_PRIVATE);

        affectationsList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listViewAffectation);

        extractAffectations();

        b1 = findViewById(R.id.titleAerosoft);
        piloteButton = (Button) findViewById(R.id.piloteButton);
        volButton = (Button) findViewById(R.id.volButton);
        avionButton = (Button) findViewById(R.id.avionButton);
        logoutButton = (Button) findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();

                Intent i = new Intent(getApplicationContext(),LoginAerosoft.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),HomeAerosoft.class);
                startActivity(i);
            }
        });

        piloteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PiloteAerosoft.class);
                startActivity(intent);
            }

        });

        volButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), VolAerosoft.class);
                startActivity(intent);
            }

        });

        avionButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AvionAerosoft.class);
                startActivity(intent);
            }

        });

    }

    private void extractAffectations() {

        propertyReader = new PropertyReader(this);
        properties = propertyReader.getMyProperties("app.properties");

        API_URL = "http://"+ properties.getProperty("IP_Machine") +"/AerosoftAPI/affectation";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray affectationArray = obj.getJSONArray("Affectations");
                            for (int i = 0; i < affectationArray.length(); i++) {
                                JSONObject affectationObject = affectationArray.getJSONObject(i);

                                String IdAffectation = affectationObject.getString("IdAffectation");
                                String NumVol = affectationObject.getString("NumVol");
                                String DateVol = affectationObject.getString("DateVol");
                                String NumAvion = affectationObject.getString("NumAvion");

                                HashMap<String, String> affectations = new HashMap<>();

                                affectations.put("IdAffectation", IdAffectation);
                                affectations.put("NumVol", NumVol);
                                affectations.put("DateVol", DateVol);
                                affectations.put("NumAvion", NumAvion);

                                affectationsList.add(affectations);

                            }
                            ListAdapter adapter = new SimpleAdapter(
                                    AffectationAerosoft.this, affectationsList,
                                    R.layout.list_item_affectation, new String[]{"IdAffectation", "NumVol",
                                    "DateVol", "NumAvion"}, new int[]{R.id.IdAffectation,
                                    R.id.NumVolAffect, R.id.DateVolAffect, R.id.NumAvionAffect});

                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }
}