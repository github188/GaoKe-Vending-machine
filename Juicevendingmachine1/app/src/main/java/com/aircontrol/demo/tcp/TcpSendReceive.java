package com.aircontrol.demo.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;


public class TcpSendReceive {

    /**
     * Wifi管理器
     */
    private WifiManager wifiManager;

    /**
     * 网关信息
     */
    private DhcpInfo dhcpinfo;

    /**
     * 当前连接的IP地址
     */
    private String ipAddress;

    /**
     * Socket套接字
     */
    private Socket mSocketClient = null;

    /**
     * Socket输入流
     */
    private InputStream mReadBuffer = null;

    /**
     * Socket输出流
     */
    private OutputStream mWriteBuffer = null;

    /**
     * Socket通道是否连接成功标志
     */
    private boolean IsConnected = false;

    /**
     * 实例
     */
    private static final TcpSendReceive instance = new TcpSendReceive();

    /**
     * 异步线程池
     */
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * 连接情况的回调函数
     */
    private ConnectionCallBack connectionCallBack;

    /**
     * 接发送信息回调函数
     */
    private MesssageCallBack messageCallBack;

    /**
     * 私有化构造函数
     */
    private TcpSendReceive() {
    }

    /**
     * 获取蓝牙管理器的实例
     *
     * @return
     */
    public static TcpSendReceive getInstance() {
        return instance;
    }

    /**
     * 初始化Wifi信息，得到当前连接的IP地址
     *
     * @param context 上下文
     */
    public void init(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        dhcpinfo = wifiManager.getDhcpInfo();
        ipAddress = intToIp(dhcpinfo.serverAddress);
    }

    /**
     * 判断当前是否连入WIFI
     *
     * @return
     */
    public boolean getIsWifiEnabled() {
        if (!wifiManager.isWifiEnabled()) {    //判断wifi是否开启
            return false;
        } else {
            return true;
        }
    }

    /**
     * 进行Socket连接
     * @param port 端口号
     */
    public void connect(final int port) {
        final byte[] bytedata = new byte[1024];
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!IsConnected) {
                        try {
                            Log.i("TAG", "开始连接");
                            mSocketClient = new Socket(ipAddress, port);
                            IsConnected = true;
                            Log.i("TAG", "已连接");
                            mReadBuffer = mSocketClient.getInputStream();
                            mWriteBuffer = mSocketClient.getOutputStream();
                            if (mSocketClient != null) {
                                connectionCallBack.onConnected();
                            }
                            break;
                        } catch (Exception e) {
                            Log.i("TAG", "catch IsConnected " + String.valueOf(IsConnected));
                            if (connectionCallBack != null) {
                                connectionCallBack.onDisConnected();
                            }
                            if (mSocketClient != null) {
                                try {
                                    mSocketClient.close();
                                    mSocketClient = null;
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                Log.i("TAG", "");
                            }
                            return;
                        }
                    }
                }
                int count = 0;
                while (true) {
                    try {
                        if ((count = mReadBuffer.read(bytedata)) != -1) {
                            int[] datas = new int[count];
                            Log.i("TAG", "接收" + String.valueOf(count) +"字节");
                            for (int i = 0; i < count; i++) {
                                int results = bytedata[i] & 0xFF;
                                datas[i] = results;
                            }
                            if (messageCallBack != null) {
                                messageCallBack.onNewMessageCome(datas);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (connectionCallBack != null) {
                            connectionCallBack.onDisConnected();
                        }
                    }
                }
            }
        });
    }

    /**
     * 发送数据到模块
     *
     * @param sendbuffer 需要发送的字节数组
     */
    public void SendDataToSensor(byte[] sendbuffer) {
        try {
            if (CheckSum(sendbuffer, sendbuffer.length)) {
                mWriteBuffer.write(sendbuffer);
                if (messageCallBack != null) {
                    messageCallBack.onSendSeccess();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (messageCallBack != null) {
                messageCallBack.onSendFail();
            }
        }
    }

    /**
     * 校验和
     * @param byteData 需要校验和的数组
     * @param count    需要校验的长度
     * @return 返回是否校验成功
     */
    private boolean CheckSum(byte[] byteData, int count) {

        byte sum = 0;
        int i = 0;
        for (i = 0; i < count - 1; i++) {
            sum += byteData[i] & 0xff;
        }
        if ((sum & 0xff) == (byteData[count - 1] & 0xff)) {
            return true;
        } else
            return false;
    }

    /**
     * Socket的连接回调函数
     * 连接成功将会调用onConnected()方法
     * 连接失败将会调用onDisConnected()方法
     *
     * @author FindYou
     */
    public static interface ConnectionCallBack {

        void onConnected();

        void onDisConnected();

    }

    /**
     * 接发送消息的回调函数
     * 消息发送成功调用onSendSeccess()
     * 消息发送失败调用onSendFail()
     * 接收到新消息调用onNewMessageCome()
     *
     * @author FindYou
     */
    public static interface MesssageCallBack {

        void onSendSeccess();

        void onSendFail();

        void onNewMessageCome(int[] receiveData);
    }


    public ConnectionCallBack getConnectionCallBack() {
        return connectionCallBack;
    }

    public void setConnectionCallBack(ConnectionCallBack connectionCallBack) {
        this.connectionCallBack = connectionCallBack;
    }

    public MesssageCallBack getMessageCallBack() {
        return messageCallBack;
    }

    public void setMessageCallBack(MesssageCallBack messageCallBack) {
        this.messageCallBack = messageCallBack;
    }

    /**
     * 把IP地址转为xxx.xxx.xxx.xxx
     *
     * @param i
     * @return
     */
    private String intToIp(int i) {     //转为IPString
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }

}
