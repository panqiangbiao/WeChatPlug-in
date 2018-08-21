package com.example.qbpan.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.provider.Settings;

public class helloworld extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//new
        setContentView(R.layout.activity_helloworld);
    }


    // Method to start the service
    public void startService(View view) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
        //startService(new Intent(getBaseContext(), HelloService.class));
        Log.v("serv1", "start Service button");
    }





}



