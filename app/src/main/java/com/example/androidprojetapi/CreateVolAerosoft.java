package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Properties;

public class CreateVolAerosoft extends AppCompatActivity {

    EditText SinlgeNumVol, SingleAeroDept, SingleHDept, SingleAeroArr, SingleHArr;

    Button CreateVol;

    private PropertyReader propertyReader;

    private Properties properties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vol_aerosoft);

        CreateVol = (Button) findViewById(R.id.CreateVol);

        CreateVol.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                createVol();
            }

        });
    }

    private void createVol() {

        SinlgeNumVol = (EditText) findViewById(R.id.SingleNumVol);
        SingleAeroDept = (EditText) findViewById(R.id.SingleAeroDept);
        SingleHDept = (EditText) findViewById(R.id.SingleHDept);
        SingleAeroArr = (EditText) findViewById(R.id.SingleAeroArr);
        SingleHArr = (EditText) findViewById(R.id.SingleHArr);

        try {
            propertyReader = new PropertyReader(this);
            properties = propertyReader.getMyProperties("app.properties");

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String API_URL="http://"+ properties.getProperty("IP_Machine") +"/AerosoftAPI/vol/create";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("NumVol", SinlgeNumVol.getText().toString());
            jsonBody.put("AeroportDept", SingleAeroDept.getText().toString());
            jsonBody.put("HDepart", SingleHDept.getText().toString());
            jsonBody.put("AeroportArr", SingleAeroArr.getText().toString());
            jsonBody.put("HArrivee", SingleHArr.getText().toString());

            JsonObjectRequest stringRequest = new JsonObjectRequest (Request.Method.POST, API_URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");
                        Intent intent = new Intent(CreateVolAerosoft.this, VolAerosoft.class);
                        intent.putExtra("message", message);
                        startActivity(intent);

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