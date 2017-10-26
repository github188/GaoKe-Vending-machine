package com.aircontrol.demo.udp;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Silenoff on 2016/11/22.
 */

public class UDPSendReceive {

    /**
     * 是否停止接收数据
     */
    private boolean isReceive = true;
    /**
     * 实例
     */
    private static UDPSendReceive instance = null;
    /**
     * 异步线程池，可执行多个线程
     */
    private static final ExecutorService executor = Executors.newCachedThreadPool();
    /**
     * 接发送信息回调函数
     */
    private MesssageCallBack messageCallBack;
    private MessageCallBack1 messageCallBack1;
    /**
     * UDP指定发送地址
     */
    private InetAddress local = null;
    /**
     * UDP套接字
     */
    public static DatagramSocket udpSocket = null;
    /**
     * Wifi多播锁
     */
    private WifiManager.MulticastLock lock = null;
    /**
     * UDP指定端口号
     */
    private int port = 0;
    /**
     * 私有化构造函数
     */
    private UDPSendReceive() {
    }

    public static UDPSendReceive getInstance() {
        if (instance == null) {
            instance = new UDPSendReceive();
        }
        return instance;
    }

    public void initUdpSocket(final Context context, final int port) {
        this.port = port;
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        lock = manager.createMulticastLock("test wifi");
        if (udpSocket == null) {
            try {
                udpSocket = new DatagramSocket(port);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(final byte[] sendMsg) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    local = InetAddress.getByName("255.255.255.255");//UDP广播地址
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                try {
                    if (udpSocket != null && !udpSocket.isClosed()) {
                        udpSocket.setBroadcast(true);
                        /**
                         * UDP数据包
                         */
                        DatagramPacket udpPacket = null;
                        udpPacket = new DatagramPacket(sendMsg, sendMsg.length);
                        udpPacket.setData(sendMsg);//设置数据
                        udpPacket.setLength(sendMsg.length);
                        udpPacket.setPort(port);
                        udpPacket.setAddress(local);//广播地址
                        lock.acquire();//获得广播锁
                        udpSocket.send(udpPacket);
                        lock.release();
                        Thread.sleep(500);//当前线程休眠500ms
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
//接收服务器端的消息
//    public void receive() {
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                byte[] data = new byte[1024];
//                DatagramPacket udpPacket = null;//DatagramPacket对象是数据报的载体
//                try {
//                    udpPacket = new DatagramPacket(data, data.length);//参数分别是存放数据的字节型数组，length是能接收的最大长度
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//                while (true) {
//                    try {
//                        if (udpSocket.isClosed() || udpSocket == null) {
//                            continue;//进行下一次检查udpSocket
//                        }
//                        udpSocket.receive(udpPacket);//把接收到的信息放到udpPacket数据包中
//                        if (udpPacket.getAddress() != null) {
//                            String quest_ip = udpPacket.getAddress().toString().substring(1);//获得所请求的ip地址，从第二个字符开始直到该字符串结束
//                            String host_ip = PhoneStateUtil.getLocalIpAddress();
//                            if ("255.255.255.255".equals(quest_ip) || host_ip.equals(quest_ip)) {  //不接受广播数据或者自己发出的广播
//                                continue;
//                            }
//                        }
//                        byte[] result = Arrays.copyOf(udpPacket.getData(), udpPacket.getLength());//复制到字节数组result
//                        //LocalInsideNumber way = UdpParser.parseUdpSearch(result);//转换为javaBean
//                             //MyDeviceFragment
//                             if (messageCallBack != null) {
//                                 messageCallBack.onNewMessageCome(result);//回调函数调用
//                             }
//                             //SettingFragment
//                             if (messageCallBack1 != null) {
//                                 messageCallBack1.onNewMessageCome1(result);//回调函数调用
//
//                         }
//                        Thread.sleep(500);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }

    /**
     * 接发送消息的回调函数
     * 接收到新消息调用onNewMessageCome()
     */
    public static interface MesssageCallBack {
        void onNewMessageCome(byte[] receiveData);
    }
    public static interface MessageCallBack1 {
        void onNewMessageCome1(byte[] receiveData);
    }
    public MesssageCallBack getMessageCallBack() {
        return messageCallBack;
    }

    public void setMessageCallBack(MesssageCallBack messageCallBack) {
        this.messageCallBack = messageCallBack;
    }
    public void setMesageCallBack1(MessageCallBack1 messageCallBack1){
        this.messageCallBack1=messageCallBack1;
    }
}
