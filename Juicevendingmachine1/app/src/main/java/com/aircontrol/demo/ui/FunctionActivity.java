package com.aircontrol.demo.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aircontrol.demo.Config;
import com.aircontrol.demo.R;
import com.aircontrol.demo.sample.SerialPortActivity;
import com.aircontrol.demo.utils.FCS;
import com.warwee.serialporttest.constants.IConstant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;
import c.b.BP;

public class FunctionActivity extends SerialPortActivity implements View.OnClickListener{
    /***
     * 货物信息，等待赋值
     */
    private TextView Vip,help,shop,groupbuy;
    private TextView tx_data;
    private String Fcs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//打开串口，设置输入输出流
        setContentView(R.layout.activity_function);
        initView();
        initEvent();
    }

    @Override
    protected void onDataReceived(final byte[] buffer, final int size) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tx_data.append(new String(buffer,0,size));
            }
        });
    }
   //接收另一个Activity的广播信息
    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg){

               switch (msg.arg1){
                   case Config.MESSAGE_WHAT_ADD_GOODS_SUCCESS:
                       break;
                   default:
                       break;
               }
        }
    };
    public void initView(){
        Vip=(TextView)findViewById(R.id.Vip);
        help=(TextView)findViewById(R.id.help);
        shop=(TextView)findViewById(R.id.shopping);
        groupbuy=(TextView)findViewById(R.id.groupbuy);
        tx_data=(TextView)findViewById(R.id.tx_data);
       // tx_data=(TextView)findViewById(R.id.tx_data);
//        try {
//            serialPort = new SerialPort(new File("/dev/"+ IConstant.PORT),IConstant.BAUDRATE,IConstant.dataBits,IConstant.stopBits,IConstant.paritys);
//            outputStream=serialPort.getOutputStream();
//            inputStream=serialPort.getInputStream();
//            Toast.makeText(FunctionActivity.this,"串口打开成功",Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    //存取货物信息
    //访问服务器
    public void initData(){
        /*********赋值********/
    }
    public void initEvent(){

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(FunctionActivity.this,shopgoodsActivity.class));
                int i;
                CharSequence t;
                Fcs= FCS.fnFCS(Config.SendDataTest);
                t=Config.SendDataTest+Fcs+Config.Enter;
                char[]text=new char[t.length()];
                for( i=0;i<t.length();i++){
                    text[i]=t.charAt(i);
                }
                try {
                    mOutputStream.write(new String(text).getBytes());//发送数据
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            default:
                break;
        }
    }

}
