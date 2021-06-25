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

public class PiloteAerosoft extends AppCompatActivity {

    ListView listView;

    ArrayList<HashMap<String, String>> pilotesList;

    private static String API_URL="http://10.75.25.40:8080/AerosoftAPI/pilote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilote_aerosoft);

        pilotesList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listViewPilote);

        extractPilotes();
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

    private void extractPilotes() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray piloteArray = obj.getJSONArray("Pilotes");
                            for (int i = 0; i < piloteArray.length(); i++) {
                                JSONObject piloteObject = piloteArray.getJSONObject(i);

                                        Integer id = piloteObject.getInt("IdPilote");
                                        String surname = piloteObject.getString("NomPilote");
                                        String name = piloteObject.getString("PrenomPilote");
                                        String matricule = piloteObject.getString("Matricule");

                                HashMap<String, String> pilotes = new HashMap<>();

                                pilotes.put("IdPilote", String.valueOf(id));
                                pilotes.put("NomPilote", surname);
                                pilotes.put("PrenomPilote", name);
                                pilotes.put("Matricule", matricule);

                                pilotesList.add(pilotes);

                            }
                            ListAdapter adapter = new SimpleAdapter(
                                    PiloteAerosoft.this, pilotesList,
                                    R.layout.list_item_pilote, new String[]{"IdPilote", "NomPilote",
                                    "PrenomPilote", "Matricule"}, new int[]{R.id.IdPilote,
                                    R.id.NomPilote, R.id.PrenomPilote, R.id.Matricule});

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