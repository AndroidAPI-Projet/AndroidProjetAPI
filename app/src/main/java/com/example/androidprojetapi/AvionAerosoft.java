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

public class AvionAerosoft extends AppCompatActivity {

    ListView listView;

    Button b1, piloteButton, volButton, avionButton, affectationButton, logoutButton;

    ArrayList<HashMap<String, String>> avionsList;

    private SharedPreferences pref;

    private PropertyReader propertyReader;

    private Properties properties;

    private static String API_URL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avion_aerosoft);

        pref = getSharedPreferences("SessionLogin", Context.MODE_PRIVATE);

        avionsList = new ArrayList<>();

        propertyReader = new PropertyReader(this);
        properties = propertyReader.getMyProperties("app.properties");

        API_URL = "http://"+ properties.getProperty("IP_Machine") +"/AerosoftAPI/avion";

        listView = (ListView) findViewById(R.id.listViewAvion);

        extractAvions();

        b1 = findViewById(R.id.titleAerosoft);
        piloteButton = (Button) findViewById(R.id.piloteButton);
        volButton = (Button) findViewById(R.id.volButton);
        avionButton = (Button) findViewById(R.id.avionButton);
        affectationButton = (Button) findViewById(R.id.affectationButton);
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

        affectationButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AffectationAerosoft.class);
                startActivity(intent);
            }

        });
    }

    private void extractAvions() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray avionArray = obj.getJSONArray("Avions");
                            for (int i = 0; i < avionArray.length(); i++) {
                                JSONObject avionObject = avionArray.getJSONObject(i);

                                String NumAvion = avionObject.getString("NumAvion");
                                String TypeAvion = avionObject.getString("TypeAvion");
                                String BaseAeroport = avionObject.getString("BaseAeroport");

                                HashMap<String, String> avions = new HashMap<>();

                                avions.put("NumAvion", NumAvion);
                                avions.put("TypeAvion", TypeAvion);
                                avions.put("BaseAeroport", BaseAeroport);

                                avionsList.add(avions);

                            }
                            ListAdapter adapter = new SimpleAdapter(
                                    AvionAerosoft.this, avionsList,
                                    R.layout.list_item_avion, new String[]{"NumAvion", "TypeAvion",
                                    "BaseAeroport"}, new int[]{R.id.NumAvion,
                                    R.id.TypeAvion, R.id.BaseAeroport});

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