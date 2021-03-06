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

public class PiloteAerosoft extends AppCompatActivity {

    ListView listView;

    Button b1, volButton, avionButton, affectationButton, logoutButton;

    ArrayList<HashMap<String, String>> pilotesList;

    private SharedPreferences pref;

    private String API_URL="";

    private PropertyReader propertyReader;

    private Properties properties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilote_aerosoft);

        pref = getSharedPreferences("SessionLogin", Context.MODE_PRIVATE);

        pilotesList = new ArrayList<>();

        propertyReader = new PropertyReader(this);
        properties = propertyReader.getMyProperties("app.properties");

        API_URL="http://"+ properties.getProperty("IP_Machine") +"/AerosoftAPI/pilote";

        listView = (ListView) findViewById(R.id.listViewPilote);

        extractPilotes();

        b1 = findViewById(R.id.titleAerosoft);
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