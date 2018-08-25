package com.example.qbpan.helloworld;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings;

public class helloworld extends AppCompatActivity {

    public static  Switch   mySwitch ;
    public static  EditText myEditViewStr1;
    public static  EditText myEditViewStr2;
    public static  Button   myButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//new
        setContentView(R.layout.activity_helloworld);
        mySwitch = (Switch)findViewById(R.id.switch1);
        myButton = (Button)findViewById(R.id.buttonModify);
        myEditViewStr1 = (EditText) findViewById(R.id.editTextStr1);
        myEditViewStr2 = (EditText) findViewById(R.id.editTextStr2);
        activityWidgetInit();
        mySwitch.setOnClickListener(new View.OnClickListener(){
            @Override
             public void onClick(View arg)
            {
                Log.v("serv1", "switch button");
                SharedPreferences.Editor edit = getSharedPreferences
                        ("com.example.qbpan.hello",MODE_PRIVATE).edit();
                edit.putBoolean("service_status", mySwitch.isChecked());
                //Toast.makeText(this, "打开去重功能", Toast.LENGTH_LONG).show();
                edit.commit();
            }
        });
        myButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg)
            {
                SharedPreferences.Editor edit = getSharedPreferences
                        ("com.example.qbpan.hello",MODE_PRIVATE).edit();
                edit.putString("myEditViewStr1",myEditViewStr1.getText().toString());
                edit.putString("myEditViewStr2",myEditViewStr2.getText().toString());
                //Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
                edit.commit();
                RobService.MatchNotification = myEditViewStr1.getText().toString();
                RobService.MatchContentYuyin = myEditViewStr2.getText().toString();
            }
        });
    }
    public void activityWidgetInit()
    {
        SharedPreferences prefs = getSharedPreferences("com.example.qbpan.hello", MODE_PRIVATE);
        boolean switchState = prefs.getBoolean("service_status", false);
        mySwitch.setChecked(switchState);
        String editText1 = prefs.getString("myEditViewStr1",RobService.MatchNotification);
        String editText2 = prefs.getString("myEditViewStr2",RobService.MatchContentYuyin);
        myEditViewStr1.setText(editText1);
        myEditViewStr2.setText(editText2);
    }


    // Method to start the service
    public void startService(View view) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
        //startService(new Intent(getBaseContext(), HelloService.class));
        Log.v("serv1", "start Service button");
    }





}



