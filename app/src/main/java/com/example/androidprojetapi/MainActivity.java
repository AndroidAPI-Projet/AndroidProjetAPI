package com.example.androidprojetapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imageAerosoft, imageMusicoshop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageAerosoft = (ImageView) findViewById(R.id.imageAerosoft);
        imageMusicoshop = (ImageView) findViewById(R.id.imageMusicoshop);

        imageAerosoft.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginAerosoft.class);
                startActivity(intent);
            }

        });
    }
}