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

public class AffectationAerosoft extends AppCompatActivity {

    ListView listView;

    ArrayList<HashMap<String, String>> affectationsList;

    private static String API_URL="http://192.168.1.42/apache/AerosoftAPI/affectation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affectation_aerosoft);

        affectationsList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listViewAffectation);

        extractAffectations();
        Button b1;
        b1 = findViewById(R.id.titleAerosoft);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),HomeAerosoft.class);
                startActivity(i);
            }
        });
    }

    private void extractAffectations() {

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