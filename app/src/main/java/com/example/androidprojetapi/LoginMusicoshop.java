package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class LoginMusicoshop extends AppCompatActivity {

    EditText txtUsernameMusicoshop, txtPasswordMusicoshop;

    Button btnLoginMusicoshop,btnRegisterMusicoshop;

    Utilisateur user;

    byte[] input, output;

    protected Resources resources;

    private String API_URL="";

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_musicoshop);

        txtUsernameMusicoshop = (EditText) findViewById(R.id.txtUsernameMusicoshop);
        txtPasswordMusicoshop = (EditText) findViewById(R.id.txtPasswordMusicoshop);

        btnLoginMusicoshop = (Button) findViewById(R.id.btnLoginMusicoshop);
        btnRegisterMusicoshop = (Button) findViewById(R.id.btnRegisterMusicoshop);

        user = new Utilisateur();

        API_URL="http://"+ getString(R.string.IP_Machine)+"/MusicoshopAPI/api/utilisateur/login2.php?";

        btnLoginMusicoshop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                user.setEmail((String) txtUsernameMusicoshop.getText().toString());
                user.setPassword((String) txtPasswordMusicoshop.getText().toString());
                //Toast.makeText(getApplicationContext(), "btn conexion", Toast.LENGTH_SHORT).show();

                login(user);
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

        String uri = API_URL + "email=" + user.getEmail() + "&password=" + password;

        Log.d("uri : ", uri);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Login Response: ", "Login Response: " + response.toString());
                        try {
                            JSONObject obj = new JSONObject(response);

                            if(!obj.getString("idUtilisateur").isEmpty()) {

                                Log.e("JSONObject(response)", String.valueOf(obj));

                                user.setIdUtilisateur(obj.getString("idUtilisateur"));
                                user.setUserName(obj.getString("userName"));
                                user.setEmail(obj.getString("email"));
                                user.setType(obj.getString("type"));
                                user.setPassword(obj.getString("password"));
                                user.setValideuser(obj.getString("valideuser"));
                                user.setChangepwd(obj.getString("changepwd"));
                                user.setSexe(obj.getString("sexe"));
                                user.setNom(obj.getString("nom"));
                                user.setPrenom(obj.getString("prenom"));
                                user.setTel(obj.getString("tel"));
                                user.setAdresse(obj.getString("adresse"));
                                user.setVille(obj.getString("ville"));
                                user.setCodePostal(obj.getString("codePostal"));

                                session = new SessionManager(LoginMusicoshop.this);
                                session.setKey("idUtilisateur", user.getIdUtilisateur());


                                finish();
                                Intent intent = new Intent(getApplicationContext(), HomeMusicoshop.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(getApplicationContext(), "Mot de passe ou login erroné", Toast.LENGTH_SHORT).show();
                                Log.e("idUtilisateur", "Mot de passe ou login erroné");
                            }

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
    };

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