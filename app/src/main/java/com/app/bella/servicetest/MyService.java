package com.app.bella.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by bella on 2017/5/3.
 */

public class MyService extends Service {
    private static final String TAG = "ooo_Service";

    public class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    private LocalBinder mLocBin = new LocalBinder();

    public void myMethod() {
        Log.v(TAG, "myMethod");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "onCreate");
        Log.v(TAG,"MyService thread id is  " + Thread.currentThread().getId());


        Notification.Builder builder = new Notification.Builder(this);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentTitle("Title Name");  //設置標題
        builder.setContentText("The content"); //內容
        builder.setSmallIcon(R.mipmap.ic_launcher);//設圖片
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.getNotification();
        startForeground(1,notification);//系統狀態列會顯示
        Log.d(TAG, "onCreate() executed");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "onStartCommand id="+startId);
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(TAG, "onUnbind");
        return super.onUnbind(intent);
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "onStaonBindrtCommand");
        return mLocBin;
    }



}
