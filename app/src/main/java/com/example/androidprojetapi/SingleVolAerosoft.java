package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
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

import java.util.HashMap;
import java.util.Properties;

public class SingleVolAerosoft extends AppCompatActivity {

    EditText SinlgeNumVol, SingleAeroDept, SingleHDept, SingleAeroArr, SingleHArr;

    Button SingleVolEdit, SingleVolDelete;

    private PropertyReader propertyReader;

    private Properties properties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_vol_aerosoft);

        extractSingleVol();

        SingleVolEdit = (Button) findViewById(R.id.SingleVolEdit);
        SingleVolDelete = (Button) findViewById(R.id.SingleVolDelete);

        SingleVolEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                editVol();
            }

        });

        SingleVolDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                deleteVol();
            }

        });

    }

    private void extractSingleVol() {

        SinlgeNumVol = (EditText) findViewById(R.id.SingleNumVol);
        SingleAeroDept = (EditText) findViewById(R.id.SingleAeroDept);
        SingleHDept = (EditText) findViewById(R.id.SingleHDept);
        SingleAeroArr = (EditText) findViewById(R.id.SingleAeroArr);
        SingleHArr = (EditText) findViewById(R.id.SingleHArr);

        Intent intent = getIntent();
        String NumVol1 = intent.getStringExtra("NumVol");

        propertyReader = new PropertyReader(this);
        properties = propertyReader.getMyProperties("app.properties");

        String API_URL="http://"+ properties.getProperty("IP_Machine") +"/AerosoftAPI/vol/" + NumVol1;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONObject volArray = obj.getJSONObject("SingleVol");

                            String NumVol = volArray.getString("NumVol");
                            String AeroportDept = volArray.getString("AeroportDept");
                            String HDepart = volArray.getString("HDepart");
                            String AeroportArr = volArray.getString("AeroportArr");
                            String HArrivee = volArray.getString("HArrivee");

                            SinlgeNumVol.setText(NumVol);
                            SingleAeroDept.setText(AeroportDept);
                            SingleHDept.setText(HDepart);
                            SingleAeroArr.setText(AeroportArr);
                            SingleHArr.setText(HArrivee);

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

    private void editVol() {

        SinlgeNumVol = (EditText) findViewById(R.id.SingleNumVol);
        SingleAeroDept = (EditText) findViewById(R.id.SingleAeroDept);
        SingleHDept = (EditText) findViewById(R.id.SingleHDept);
        SingleAeroArr = (EditText) findViewById(R.id.SingleAeroArr);
        SingleHArr = (EditText) findViewById(R.id.SingleHArr);

        try {
            propertyReader = new PropertyReader(this);
            properties = propertyReader.getMyProperties("app.properties");

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String API_URL="http://"+ properties.getProperty("IP_Machine") +"/AerosoftAPI/vol/update";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("NumVol", SinlgeNumVol.getText().toString());
            jsonBody.put("AeroportDept", SingleAeroDept.getText().toString());
            jsonBody.put("HDepart", SingleHDept.getText().toString());
            jsonBody.put("AeroportArr", SingleAeroArr.getText().toString());
            jsonBody.put("HArrivee", SingleHArr.getText().toString());

            String requestBody = jsonBody.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest (Request.Method.POST, API_URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");
                        Toast.makeText(SingleVolAerosoft.this, message, Toast.LENGTH_LONG).show();
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

    private void deleteVol() {

        SinlgeNumVol = (EditText) findViewById(R.id.SingleNumVol);
        SingleAeroDept = (EditText) findViewById(R.id.SingleAeroDept);
        SingleHDept = (EditText) findViewById(R.id.SingleHDept);
        SingleAeroArr = (EditText) findViewById(R.id.SingleAeroArr);
        SingleHArr = (EditText) findViewById(R.id.SingleHArr);

        Intent intent = getIntent();
        String NumVol1 = intent.getStringExtra("NumVol");

        propertyReader = new PropertyReader(this);
        properties = propertyReader.getMyProperties("app.properties");

        String API_URL="http://"+ properties.getProperty("IP_Machine") +"/AerosoftAPI/vol/delete";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONObject volArray = obj.getJSONObject("SingleVol");

                            String NumVol = volArray.getString("NumVol");
                            String AeroportDept = volArray.getString("AeroportDept");
                            String HDepart = volArray.getString("HDepart");
                            String AeroportArr = volArray.getString("AeroportArr");
                            String HArrivee = volArray.getString("HArrivee");

                            SinlgeNumVol.setText(NumVol);
                            SingleAeroDept.setText(AeroportDept);
                            SingleHDept.setText(HDepart);
                            SingleAeroArr.setText(AeroportArr);
                            SingleHArr.setText(HArrivee);

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