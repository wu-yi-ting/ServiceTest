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

    private MyService mMyService = null;

    private ServiceConnection mServConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyService = ((MyService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            getLocalClassName();//getClassName
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStart = (Button) findViewById(R.id.btnStart);
        mBtnStop = (Button) findViewById(R.id.btnStop);
        mBtnBind = (Button) findViewById(R.id.btnCon);
        mBtnUnBind = (Button) findViewById(R.id.btnUnbind);
        mBtnCall = (Button) findViewById(R.id.btnCall);

        Log.v(TAG,"MainActivity thread id is  " + Thread.currentThread().getId());
    }

    @Override
    protected void onResume() {
        super.onResume();

        mBtnStart.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyService = null;
                Intent it = new Intent(MainActivity.this, MyService.class);
                startService(it);  //start service
                Log.v(TAG, "mBtnStart");
            }
        });

        mBtnStop.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "mBtnStop");
                mMyService = null;
                Intent it = new Intent(MainActivity.this, MyService.class);
                stopService(it);

            }
        });

        mBtnBind.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "mBtnBind");
                mMyService = null;
                Intent it = new Intent(MainActivity.this, MyService.class);
                bindService(it, mServConn, BIND_AUTO_CREATE);
            }
        });

        mBtnUnBind.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "mBtnUnBind");
                mMyService = null;
                Intent it = new Intent(MainActivity.this, MyService.class);
                unbindService(mServConn);
            }
        });

        mBtnCall.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v(TAG, "mBtnCall");
                mMyService = null;
                Intent it = new Intent(MainActivity.this, MyService.class);
                if (mMyService != null) {
                    mMyService.myMethod();
                }
            }
        });


    }
}

