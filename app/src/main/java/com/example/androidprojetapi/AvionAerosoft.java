package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class AvionAerosoft extends AppCompatActivity {

    ListView listView;

    ArrayList<HashMap<String, String>> avionsList;

    private static String API_URL="http://10.75.25.40:8080/AerosoftAPI/avion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avion_aerosoft);

        avionsList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listViewAvion);

        extractAvions();
        Button b1,piloteButton, volButton, avionButton, affectationButton;;
        b1 = findViewById(R.id.titleAerosoft);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),HomeAerosoft.class);
                startActivity(i);
            }
        });
        piloteButton = (Button) findViewById(R.id.piloteButton);
        volButton = (Button) findViewById(R.id.volButton);
        avionButton = (Button) findViewById(R.id.avionButton);
        affectationButton = (Button) findViewById(R.id.affectationButton);

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