package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginAerosoft extends AppCompatActivity {

    Button btnRegisterAerosoft, btnLoginAerosoft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_aerosoft);

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

                Intent intent = new Intent(getApplicationContext(), HomeAerosoft.class);
                startActivity(intent);
            }

        });
    }
}