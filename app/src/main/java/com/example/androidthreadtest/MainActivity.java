package com.example.androidthreadtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "abc";
    public static final int UPDATE_TEXT = 1;
    public static final int UPDATE_COLOR = 2;
    public int num = 0;

    private TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "oncreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        Button changeText = (Button) findViewById(R.id.change_text);
        Button changeColor = findViewById(R.id.change_color);

        changeText.setOnClickListener(this);
        changeColor.setOnClickListener(this);


        Button test = findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "oncreate click");
            }
        });

    }

    //定义一个handler
    private Handler handler = new Handler() {
        //重写handlemessage
        public void handleMessage(Message msg) {
            Log.d(TAG, "handlemesage");
            switch (msg.what) {
                case UPDATE_TEXT:
                    //	在这里可以进行UI操作
                    text.setText("Nice	to	meet	you");
                    break;
                case UPDATE_COLOR:
                    changeColor();
                    break;
                default:
                    break;
            }
        }
    };

    public void changeColor() {
        switch (num) {
            case 0:
                text.setTextColor(getResources().getColor(R.color.colorAccent));
                break;
            case 1:
                text.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            default:
                break;
        }
        num = (num > 2) ? 0 : ++num;
    }


    //mainactivity实现了view.onclicklistener
    @Override
    public void onClick(View v) {

        //捕捉屏幕点击
        switch (v.getId()) {
            case R.id.change_text:
                //开启一个线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "click text");

                        //持有message对象，更新message.what值，传递到handlemessage
                        Message message = new Message();
                        message.what = UPDATE_TEXT;

                        handler.sendMessage(message);
                        //	将Message对象发送出去
                    }
                }).start();

                //关闭服务
                Intent stopService = new Intent(MainActivity.this, MyService.class);
                stopService(stopService);

                break;
            case R.id.change_color:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "click color");

                        Message message = new Message();
                        message.what = UPDATE_COLOR;
                        handler.sendMessage(message);
                    }
                }).start();
                //启动服务
                Intent startService = new Intent(this, MyService.class);
                startService(startService);

                break;
            default:
                break;
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(TAG, "start" + "  " + this + " " + getTaskId());
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG, "resume" + "  " + this + " " + getTaskId());
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d(TAG, "pause" + "  " + this + " " + getTaskId());
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d(TAG, "stop" + "  " + this + " " + getTaskId());
//
//    }
//
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.d(TAG, "restart" + "  " + this + " " + getTaskId());
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "destroy" + "  " + this + " " + getTaskId());
//
//    }
}