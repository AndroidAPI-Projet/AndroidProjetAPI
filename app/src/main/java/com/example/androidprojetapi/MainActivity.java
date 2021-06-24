package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textViewAerosoft,textViewMusicoshop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewAerosoft = (TextView) findViewById(R.id.textViewAerosoft);
        textViewMusicoshop = (TextView) findViewById(R.id.textViewMusicoshop);

        textViewAerosoft.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PiloteAerosoft.class);
                startActivity(intent);
            }

        });

        textViewMusicoshop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginMusicoshop.class);
                startActivity(intent);
            }

        });
    }
}