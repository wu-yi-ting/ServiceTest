package com.app.bella.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/*
 * Created by bella on 2017/5/3.
 */

public class MyService extends Service {
    private static final String TAG = "ooo_Service";
    private CameraServiceCallback mCallback=null;
    private LocalBinder mLocBin = new LocalBinder();

    public class LocalBinder extends Binder {

        public void registerCallback(CameraServiceCallback callback) {
            Log.v(TAG, "setCallback");
            mCallback = callback;
        }
        public void addNumber(int data) {
            Log.v(TAG, "callService");
            if (mCallback != null) {
                mCallback.onGetNumber(data + 1);
            }
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "onCreate");

        Notification.Builder builder = new Notification.Builder(this);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentTitle("CameraService");  //設置標題
        builder.setContentText("New is open "); //內容
        builder.setSmallIcon(R.mipmap.ic_launcher);//設圖片
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.getNotification();
        startForeground(1,notification);//系統狀態列會顯示
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "onBind");
        return mLocBin;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

}
