package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    private static String API_URL="http://10.75.25.182:8080/apache/AerosoftAPI/avion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avion_aerosoft);

        avionsList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listViewAvion);

        extractAvions();
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