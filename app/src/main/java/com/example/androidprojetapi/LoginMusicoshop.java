package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginMusicoshop extends AppCompatActivity {

    EditText txtUsernameMusicoshop, txtPasswordMusicoshop;

    Button btnLoginMusicoshop,btnRegisterMusicoshop;

    Utilisateur user;

    byte[] input, output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_musicoshop);

        txtUsernameMusicoshop = (EditText) findViewById(R.id.txtUsernameMusicoshop);
        txtPasswordMusicoshop = (EditText) findViewById(R.id.txtPasswordMusicoshop);

        btnLoginMusicoshop = (Button) findViewById(R.id.btnLoginMusicoshop);
        btnRegisterMusicoshop = (Button) findViewById(R.id.btnRegisterMusicoshop);

        user = new Utilisateur();

        btnLoginMusicoshop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                user.setEmail((String) txtUsernameMusicoshop.getText().toString());
                user.setPassword((String) txtPasswordMusicoshop.getText().toString());
                Log.e("user.setEmail", user.getEmail());
                Log.e("user.setPassword", user.getPassword());

                Toast.makeText(getApplicationContext(), "btn conexion", Toast.LENGTH_SHORT).show();

                login(user);

                /*Intent intent = new Intent(getApplicationContext(), LoginMusicoshop.class);
                startActivity(intent);*/
            }

        });

    }
    @SuppressLint("NewApi")
    private void login(Utilisateur user){

        MessageDigest sha256=null;
        String password = "";

        try {
            sha256 = MessageDigest.getInstance("SHA-256");

        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        sha256.reset();

        output = sha256.digest(user.getPassword().getBytes(StandardCharsets.UTF_8));
        password = bytesToHex(output);

        String uri = String.format("http://192.168.56.1:8080/Musicoshop/login.php?param1=%1$s&param2=%2$s",
                user.getEmail(),password);

        Log.e("uri : ", uri);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            Log.e("response : ", response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("VolleyError Error", "VolleyError Error " + error.getMessage());
                    }
                });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}