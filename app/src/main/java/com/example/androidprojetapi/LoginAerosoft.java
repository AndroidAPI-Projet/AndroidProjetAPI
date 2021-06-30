package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Properties;

public class LoginAerosoft extends AppCompatActivity {

    Button btnRegisterAerosoft, btnLoginAerosoft;

    EditText txtUsernameAerosoft, txtPasswordAerosoft;

    private PropertyReader propertyReader;

    private Properties properties;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_aerosoft);

        pref = getSharedPreferences("SessionLogin", Context.MODE_PRIVATE);

        btnRegisterAerosoft = (Button) findViewById(R.id.btnRegisterAerosoft);
        btnLoginAerosoft = (Button) findViewById(R.id.btnLoginAerosoft);

        btnRegisterAerosoft.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterAerosoft.class);
                startActivity(intent);
            }

        });

        btnLoginAerosoft.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Login();
            }

        });
    }

    private void Login() {

        txtUsernameAerosoft = (EditText) findViewById(R.id.txtUsernameAerosoft);
        txtPasswordAerosoft = (EditText) findViewById(R.id.txtPasswordAerosoft);

        try {
            propertyReader = new PropertyReader(this);
            properties = propertyReader.getMyProperties("app.properties");

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String API_URL="http://"+ properties.getProperty("IP_Machine") +"/AerosoftAPI/utilisateur/login";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("Mail", txtUsernameAerosoft.getText().toString());
            jsonBody.put("MotDePasse", txtPasswordAerosoft.getText().toString());
            jsonBody.put("Statut", "true");

            JsonObjectRequest stringRequest = new JsonObjectRequest (Request.Method.POST, API_URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");

                        if(message.equals("Mot de passe ou login erroné")) {
                            Toast.makeText(LoginAerosoft.this, message, Toast.LENGTH_SHORT).show();
                        } else {
                            String IdUtilisateur = response.getString("IdUtilisateur");
                            String Mail = response.getString("Mail");
                            String MotDePasse = response.getString("MotDePasse");
                            String Statut = response.getString("Statut");
                            String IdRole = response.getString("IdRole");

                            Intent intent = new Intent(LoginAerosoft.this, HomeAerosoft.class);
                            intent.putExtra("message", message);

                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("IdUtilisateur", IdUtilisateur);
                            editor.putString("Mail", Mail);
                            editor.putString("MotDePasse", MotDePasse);
                            editor.putString("Mail", Mail);
                            editor.putString("Statut", Statut);
                            editor.putString("IdRole", IdRole);
                            editor.commit();

                            startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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