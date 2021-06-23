package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterAerosoft extends AppCompatActivity {

    Button btnLinkLoginAerosoft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_aerosoft);

        btnLinkLoginAerosoft = (Button) findViewById(R.id.btnLinkLoginAerosoft);

        btnLinkLoginAerosoft.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginAerosoft.class);
                startActivity(intent);
            }

        });
    }

}