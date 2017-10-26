package com.aircontrol.demo.sample;

import android.app.Activity;
import android.os.Bundle;

import com.warwee.serialporttest.constants.IConstant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;

/**
 * Created by 大东 on 2017/9/19 0019.
 */

public abstract class SerialPortActivity extends Activity {

    public static InputStream mInputStream=null;
    public static OutputStream mOutputStream=null;
    private SerialPort serialPort;
    private ReadThread mReadThread;
    private class ReadThread extends Thread{
        @Override
        public void run(){
            super.run();//继承关系
            while(!isInterrupted()){
                int size;
                try {
                    byte[] buffer= new byte[64];
                    if(mInputStream==null)return;
                    size=mInputStream.read(buffer);//读取数据
                    if(size>0){
                        //界面更新
                        onDataReceived(buffer,size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
      //serialPort=new SerialPort(new File("/dev/"+ IConstant.PORT), IConstant.BAUDRATE,0);
        //打开串口设置波特率、数据位、校验位
        try {
            serialPort = new SerialPort(new File("/dev/"+ IConstant.PORT),IConstant.BAUDRATE,IConstant.dataBits,IConstant.stopBits,IConstant.paritys);
            mInputStream=serialPort.getInputStream();
            mOutputStream=serialPort.getOutputStream();
            mReadThread=new ReadThread();
            mReadThread.start();//开启读写线程
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void onDataReceived(final byte[] buffer, final int size);

}
