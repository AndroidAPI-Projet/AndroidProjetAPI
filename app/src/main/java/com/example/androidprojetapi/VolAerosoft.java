package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class VolAerosoft extends AppCompatActivity {

    ListView listView;

    ArrayList<HashMap<String, String>> volsList;

    FloatingActionButton fab;

    Button b1, piloteButton, volButton, avionButton, affectationButton, logoutButton;

    private String API_URL="";

    private PropertyReader propertyReader;

    private Properties properties;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vol_aerosoft);

        pref = getSharedPreferences("SessionLogin", Context.MODE_PRIVATE);

        volsList = new ArrayList<>();

        propertyReader = new PropertyReader(this);
        properties = propertyReader.getMyProperties("app.properties");

        API_URL="http://"+ properties.getProperty("IP_Machine") +"/AerosoftAPI/vol";

        listView = (ListView) findViewById(R.id.listViewVol);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        Integer IdRole = Integer.parseInt(pref.getString("IdRole", null));

        extractVols();

        Intent intent = getIntent();

        String message = intent.getStringExtra("message");

        if(message != null) {
            Toast.makeText(VolAerosoft.this, message, Toast.LENGTH_LONG).show();
        }

        if(IdRole == 55555) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(VolAerosoft.this, CreateVolAerosoft.class);
                    startActivity(intent);
                }
            });
        } else {
            fab.hide();
        }

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

        if(IdRole == 55555) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                    TextView NumVol = (TextView) view.findViewById(R.id.NumVol);

                    Intent intent = new Intent(getApplicationContext(), SingleVolAerosoft.class);
                    intent.putExtra("NumVol", NumVol.getText());
                    startActivity(intent);
                }
            });
        }
    }

    private void extractVols() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray volArray = obj.getJSONArray("Vols");
                            for (int i = 0; i < volArray.length(); i++) {
                                JSONObject volObject = volArray.getJSONObject(i);

                                String NumVol = volObject.getString("NumVol");
                                String AeroportDept = volObject.getString("AeroportDept");
                                String HDepart = volObject.getString("HDepart");
                                String AeroportArr = volObject.getString("AeroportArr");
                                String HArrivee = volObject.getString("HArrivee");

                                HashMap<String, String> vols = new HashMap<>();

                                vols.put("NumVol", NumVol);
                                vols.put("AeroportDept", AeroportDept);
                                vols.put("HDepart", HDepart);
                                vols.put("AeroportArr", AeroportArr);
                                vols.put("HArrivee", HArrivee);

                                volsList.add(vols);

                            }
                            ListAdapter adapter = new SimpleAdapter(
                                    VolAerosoft.this, volsList,
                                    R.layout.list_item_vol, new String[]{"NumVol", "AeroportDept",
                                    "HDepart", "AeroportArr", "HArrivee"}, new int[]{R.id.NumVol,
                                    R.id.AeroportDept, R.id.HDepart, R.id.AeroportArr, R.id.HArrivee});

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