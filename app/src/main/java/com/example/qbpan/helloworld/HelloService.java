package com.example.qbpan.helloworld;

import android.app.Service;
import android.os.IBinder;
import android.content.Intent;
import android.os.Bundle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class HelloService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "服务已经启动2", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "服务已经停止2", Toast.LENGTH_LONG).show();
    }
}

