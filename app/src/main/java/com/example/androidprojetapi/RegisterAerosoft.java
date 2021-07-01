package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class RegisterAerosoft extends AppCompatActivity {

    Button btnLinkLoginAerosoft, btnConfirmRegisterAerosoft;

    EditText txtMailAerosoft, txtMotDePasseAerosoft, txtRoleAerosoft;

    Spinner SpinnerRole;

    List<String> rolesList = new ArrayList<String>();

    private PropertyReader propertyReader;

    private Properties properties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_aerosoft);

        extractRole();

        btnLinkLoginAerosoft = (Button) findViewById(R.id.btnLinkLoginAerosoft);
        btnConfirmRegisterAerosoft = (Button) findViewById(R.id.btnConfirmRegisterAerosoft);

        btnConfirmRegisterAerosoft.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                createUtilisateur();
            }

        });

        btnLinkLoginAerosoft.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginAerosoft.class);
                startActivity(intent);
            }

        });
    }

    private void extractRole() {

        SpinnerRole = (Spinner) findViewById(R.id.SpinnerRole);

        Intent intent = getIntent();

        propertyReader = new PropertyReader(this);
        properties = propertyReader.getMyProperties("app.properties");

        String API_URL="http://"+ properties.getProperty("IP_Machine") +"/AerosoftAPI/role";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray RoleArray = obj.getJSONArray("Roles");
                            for (int i = 0; i < RoleArray.length(); i++) {
                                JSONObject RoleObject = RoleArray.getJSONObject(i);

                                String Role = RoleObject.getString("RoleNom");

                                rolesList.add(Role);

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                        (RegisterAerosoft.this, android.R.layout.simple_spinner_item,rolesList);

                                dataAdapter.setDropDownViewResource
                                        (android.R.layout.simple_spinner_dropdown_item);

                                SpinnerRole.setAdapter(dataAdapter);

                            }

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

    private void createUtilisateur() {

        txtMailAerosoft = (EditText) findViewById(R.id.txtMailAerosoft);
        txtMotDePasseAerosoft = (EditText) findViewById(R.id.txtMotDePasseAerosoft);
        SpinnerRole = (Spinner) findViewById(R.id.SpinnerRole);

        String role = null;

        if(SpinnerRole.getSelectedItem().toString().equals("Chargé Clientèle")) {
            role = "01011";
        } else if(SpinnerRole.getSelectedItem().toString().equals("Pilote")) {
            role = "11111";
        } else if(SpinnerRole.getSelectedItem().toString().equals("Technicien d'exploitation")) {
            role = "44444";
        } else if(SpinnerRole.getSelectedItem().toString().equals("Administrateur")) {
            role = "55555";
        }

        Integer uniqueID = UUID.randomUUID().hashCode();

        try {
            propertyReader = new PropertyReader(this);
            properties = propertyReader.getMyProperties("app.properties");

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String API_URL="http://"+ properties.getProperty("IP_Machine") +"/AerosoftAPI/utilisateur/create";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("IdUtilisateur", uniqueID.toString());
            jsonBody.put("Mail", txtMailAerosoft.getText().toString());
            jsonBody.put("MotDePasse", txtMotDePasseAerosoft.getText().toString());
            jsonBody.put("Statut", true);
            jsonBody.put("IdRole", role);

            JsonObjectRequest stringRequest = new JsonObjectRequest (Request.Method.POST, API_URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");
                        if (message.equals("Votre compte a été créé avec succès")) {
                            Intent intent = new Intent(RegisterAerosoft.this, LoginAerosoft.class);
                            intent.putExtra("message", message);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterAerosoft.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}