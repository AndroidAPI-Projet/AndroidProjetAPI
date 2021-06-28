package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListAdapter;
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

import java.util.HashMap;
import java.util.Properties;

public class SingleVolAerosoft extends AppCompatActivity {

    EditText SinlgeNumVol, SingleAeroDept, SingleHDept, SingleAeroArr, SingleHArr;

    private PropertyReader propertyReader;

    private Properties properties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_vol_aerosoft);

        extractSingleVol();

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
}