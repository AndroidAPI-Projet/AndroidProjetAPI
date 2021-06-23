package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginMusicoshop extends AppCompatActivity {

    TextView txtUsernameMusicoshop, txtPasswordMusicoshop;

    Button btnLoginMusicoshop,btnRegisterMusicoshop;

    Utilisateur user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_musicoshop);

        txtUsernameMusicoshop = (TextView) findViewById(R.id.txtUsernameMusicoshop);
        txtPasswordMusicoshop = (TextView) findViewById(R.id.txtPasswordMusicoshop);

        btnLoginMusicoshop = (Button) findViewById(R.id.btnLoginMusicoshop);
        btnRegisterMusicoshop = (Button) findViewById(R.id.btnRegisterMusicoshop);

        user = new Utilisateur();

        btnLoginMusicoshop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "btn conexion", Toast.LENGTH_SHORT).show();

                /*user.setEmail((String) txtUsernameMusicoshop.getText());
                user.setPassword((String) txtPasswordMusicoshop.getText());
                Log.e("user.setEmail : ", user.getEmail());
                Log.e("user.setPassword : ", user.getPassword());

                login(user);

                /*Intent intent = new Intent(getApplicationContext(), LoginMusicoshop.class);
                startActivity(intent);*/
            }

        });

    }
    private void login(Utilisateur user){

        MessageDigest digest=null;
        String password = "";

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        digest.reset();
        try {
            password = digest.digest(user.getPassword().getBytes("UTF-8")).toString();
            Log.e("password : ", password);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String uri = String.format("http://192.168.1.18/Musicoshop/login.php?param1=%1$s&param2=%2$s",
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

}