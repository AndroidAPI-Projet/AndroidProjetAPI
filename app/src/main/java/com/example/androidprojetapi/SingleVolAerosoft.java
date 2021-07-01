package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;

public class SingleVolAerosoft extends AppCompatActivity {

    EditText SinlgeNumVol, SingleAeroDept, SingleHDept, SingleAeroArr, SingleHArr;

    Button b1, piloteButton, volButton, avionButton, affectationButton, logoutButton, SingleVolEdit, SingleVolDelete;

    private PropertyReader propertyReader;

    private Properties properties;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_vol_aerosoft);

        pref = getSharedPreferences("SessionLogin", Context.MODE_PRIVATE);

        extractSingleVol();

        SingleVolEdit = (Button) findViewById(R.id.SingleVolEdit);
        SingleVolDelete = (Button) findViewById(R.id.SingleVolDelete);

        SinlgeNumVol = (EditText) findViewById(R.id.SingleNumVol);

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

                TimePickerDialog timePickerDialog = new TimePickerDialog(SingleVolAerosoft.this,
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(SingleVolAerosoft.this,
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

        SingleVolEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                editVol();
            }

        });

        SingleVolDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SingleVolAerosoft.this)
                    .setMessage("Êtes vous sur de vouloir supprimer le vol " + SinlgeNumVol.getText().toString() +" ?")
                    .setCancelable(false)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteVol();
                        }
                    })
                .setNegativeButton("Non", null)
                .show();
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

            JsonObjectRequest stringRequest = new JsonObjectRequest (Request.Method.POST, API_URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");
                        Intent intent = new Intent(SingleVolAerosoft.this, VolAerosoft.class);
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

    private void deleteVol() {

        SinlgeNumVol = (EditText) findViewById(R.id.SingleNumVol);

        try {
            propertyReader = new PropertyReader(this);
            properties = propertyReader.getMyProperties("app.properties");

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String API_URL="http://"+ properties.getProperty("IP_Machine") +"/AerosoftAPI/vol/delete";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("NumVol", SinlgeNumVol.getText().toString());

            JsonObjectRequest stringRequest = new JsonObjectRequest (Request.Method.POST, API_URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");
                        Intent intent = new Intent(SingleVolAerosoft.this, VolAerosoft.class);
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