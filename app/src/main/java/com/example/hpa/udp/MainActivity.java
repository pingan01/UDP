package com.example.hpa.udp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.net.SocketException;

public class MainActivity extends Activity implements View.OnClickListener {

    private UdpMessageTool mUdpMessageTool;
    //服务器主机IP
    private static final String HOST = "192.168.1.209";
    // 服务器请求端口号
    private static final int PORT = 18089;
    //发送内容
    private byte[] mBytePlay = {82};

    private Button mBtnPlay;
    private Button mBtnPause;
    private Button mBtnStop;
    private Button mBtnAdd;
    private Button mBtnSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mBtnPlay.setOnClickListener(this);
        mBtnPause.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
        mBtnSub.setOnClickListener(this);
    }

    private void initView() {
        mBtnPlay = findViewById(R.id.play);
        mBtnPause = findViewById(R.id.pause);
        mBtnStop = findViewById(R.id.stop);
        mBtnAdd = findViewById(R.id.add);
        mBtnSub = findViewById(R.id.sub);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:
                Toast.makeText(this, "播放", Toast.LENGTH_SHORT).show();
                // TODO: 2018/5/25 UDP发送指令
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Log.e("TAG", "线程开启");
                        sendDataByUDP();
                    }
                }).start();
                break;
            case R.id.pause:
                Toast.makeText(this, "暂停", Toast.LENGTH_SHORT).show();
                break;
            case R.id.stop:
                Toast.makeText(this, "停止", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add:
                Toast.makeText(this, "音量+", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sub:
                Toast.makeText(this, "音量-", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void sendDataByUDP() {
        Log.e("TAG", "开始UDP");
        try {
            mUdpMessageTool = UdpMessageTool.getInstance();
            Log.e("TAG", "1");
            mUdpMessageTool.setTimeOut(5000);//超时时间为5s
            //向服务器发送数据
            mUdpMessageTool.send(HOST, PORT, mBytePlay);
        } catch (SocketException e) {
            Log.e("TAG", "e==" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG", "IOE==" + e.getMessage());
        }
    }
}
