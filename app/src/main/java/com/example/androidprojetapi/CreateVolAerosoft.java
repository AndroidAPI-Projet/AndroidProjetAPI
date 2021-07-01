package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

public class CreateVolAerosoft extends AppCompatActivity {

    EditText SinlgeNumVol, SingleAeroDept, SingleHDept, SingleAeroArr, SingleHArr;

    Button b1, piloteButton, volButton, avionButton, affectationButton, logoutButton, CreateVol;

    Spinner SingleSpinnerAeroportDept, SingleSpinnerAeroportArr;

    List<String> aeroportList = new ArrayList<String>();

    private PropertyReader propertyReader;

    private Properties properties;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vol_aerosoft);

        extractAeroport();

        pref = getSharedPreferences("SessionLogin", Context.MODE_PRIVATE);

        CreateVol = (Button) findViewById(R.id.CreateVol);
        b1 = findViewById(R.id.titleAerosoft);
        piloteButton = (Button) findViewById(R.id.piloteButton);
        volButton = (Button) findViewById(R.id.volButton);
        avionButton = (Button) findViewById(R.id.avionButton);
        affectationButton = (Button) findViewById(R.id.affectationButton);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        SingleHDept = (EditText) findViewById(R.id.SingleHDept);
        SingleHArr = (EditText) findViewById(R.id.SingleHArr);

        SingleHDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final Calendar c = Calendar.getInstance();
                int Hour = c.get(Calendar.HOUR_OF_DAY);
                int Minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateVolAerosoft.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hour,
                                                  int minute) {

                                String hrs, mins;

                                if(minute<10){
                                    mins = "0"+minute;
                                }else{
                                    mins = String.valueOf(minute);
                                }
                                if(hour<10){
                                    hrs = "0"+hour;
                                }else{
                                    hrs = String.valueOf(hour);
                                }

                                SingleHDept.setText(hrs + ":" + mins + ":00");
                            }
                        }, Hour, Minute, false);
                timePickerDialog.show();
            }
        });

        SingleHArr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final Calendar c = Calendar.getInstance();
                int Hour = c.get(Calendar.HOUR_OF_DAY);
                int Minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateVolAerosoft.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hour,
                                                  int minute) {

                                String hrs, mins;

                                if(minute<10){
                                    mins = "0"+minute;
                                }else{
                                    mins = String.valueOf(minute);
                                }
                                if(hour<10){
                                    hrs = "0"+hour;
                                }else{
                                    hrs = String.valueOf(hour);
                                }

                                SingleHArr.setText(hrs + ":" + mins + ":00");
                            }
                        }, Hour, Minute, false);
                timePickerDialog.show();
            }
        });

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

        CreateVol.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                createVol();
            }

        });
    }

    private void extractAeroport() {

        SingleSpinnerAeroportDept = (Spinner) findViewById(R.id.SingleSpinnerAeroportDept);
        SingleSpinnerAeroportArr = (Spinner) findViewById(R.id.SingleSpinnerAeroportArr);

        Intent intent = getIntent();

        propertyReader = new PropertyReader(this);
        properties = propertyReader.getMyProperties("app.properties");

        String API_URL="http://"+ properties.getProperty("IP_Machine") +"/AerosoftAPI/aeroport";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray aeroportArray = obj.getJSONArray("Aeroports");
                            for (int i = 0; i < aeroportArray.length(); i++) {
                                JSONObject aeroportObject = aeroportArray.getJSONObject(i);

                                String AeroportDept = aeroportObject.getString("IdAeroport");

                                aeroportList.add(AeroportDept);

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                        (CreateVolAerosoft.this, android.R.layout.simple_spinner_item,aeroportList);

                                dataAdapter.setDropDownViewResource
                                        (android.R.layout.simple_spinner_dropdown_item);

                                SingleSpinnerAeroportDept.setAdapter(dataAdapter);
                                SingleSpinnerAeroportArr.setAdapter(dataAdapter);

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

    private void createVol() {

        SinlgeNumVol = (EditText) findViewById(R.id.SingleNumVol);
        SingleSpinnerAeroportDept = (Spinner) findViewById(R.id.SingleSpinnerAeroportDept);
        SingleHDept = (EditText) findViewById(R.id.SingleHDept);
        SingleSpinnerAeroportArr = (Spinner) findViewById(R.id.SingleSpinnerAeroportArr);
        SingleHArr = (EditText) findViewById(R.id.SingleHArr);

        try {
            propertyReader = new PropertyReader(this);
            properties = propertyReader.getMyProperties("app.properties");

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String API_URL="http://"+ properties.getProperty("IP_Machine") +"/AerosoftAPI/vol/create";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("NumVol", SinlgeNumVol.getText().toString());
            jsonBody.put("AeroportDept", SingleSpinnerAeroportDept.getSelectedItem().toString());
            jsonBody.put("HDepart", SingleHDept.getText().toString());
            jsonBody.put("AeroportArr", SingleSpinnerAeroportArr.getSelectedItem().toString());
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
                    Toast.makeText(getApplicationContext(), "Le vol n'a pas pu être créé", Toast.LENGTH_SHORT).show();
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