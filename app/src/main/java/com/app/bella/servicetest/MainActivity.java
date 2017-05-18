package com.app.bella.servicetest;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "ooo_MainActivity";

    private Button mBtnStart, mBtnStop, mBtnBind, mBtnUnBind, mBtnCall;
    private Intent it;
    private int mData = 0;

    private MyService.LocalBinder mMyService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnBind = (Button) findViewById(R.id.btnCon);
        mBtnUnBind = (Button) findViewById(R.id.btnUnbind);
        mBtnCall = (Button) findViewById(R.id.btnCall);
        it = new Intent(MainActivity.this, MyService.class);
    }

    @Override
    protected void onResume() {
        super.onResume();


        mBtnBind.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "mBtnBind");
                mMyService = null;
                bindService(it, mServConn, BIND_AUTO_CREATE);
            }
        });

        mBtnUnBind.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "mBtnUnBind");
                mMyService = null;
                unbindService(mServConn);
                mData=0;
            }
        });

        mBtnCall.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "mBtnCall");
                if (mMyService != null) {
                    mMyService.addNumber(mData);
                    mData++;
                }
            }
        });
    }

    private ServiceConnection mServConn = new ServiceConnection() {
        @Override //當與service的連接建立後被調用
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "on Service connected");
            mMyService = (MyService.LocalBinder) service;
            mMyService.registerCallback(mCamServiceCallback);
        }

        @Override //當與service的連接意外斷開時被調用
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "on Service Disconnected");
            mMyService = null;
        }
    };

    private CameraServiceCallback mCamServiceCallback=new CameraServiceCallback() {
        @Override
        public void onGetNumber(int data) {
            Log.v(TAG, "mCameraServiceCallback.onCameraCallback = " + data); //可獲得service 更新完的資訊
        }
    };
}

