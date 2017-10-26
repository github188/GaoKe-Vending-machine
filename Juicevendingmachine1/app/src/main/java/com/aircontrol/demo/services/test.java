package com.aircontrol.demo.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aircontrol.demo.R;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

/**
 * Created by 大东 on 2017/3/20 0020.
 */

public class test extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
//    static Timer timer=null;
//    public test(){
//        Log.i("HELLO","我爱你test");
//    }
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        //startService();//开启服务
//        Log.i("HELLO","我爱你");
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        return super.onUnbind(intent);
//    }
///**
// * StartCommand可以获取Intent的值*/
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        long period=6000;//时间间隔为6秒
//        int delay=1000;//延迟时间
//        Log.i("HELLO","我爱你");
//        if(null==timer){
//            timer=new Timer();
//        }
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                /**
//                 * 获取iport，通过网关，ip,port获取使用键，同时加载进adapter的数据模型中去里
//                 *  RemoteIPPortDatabaseUtil util = new RemoteIPPortDatabaseUtil(getActivity());//对数据库的操作
//                 *  网关
//                 *  List<String> list = util.queryGateNum((String) (SharedPreferencesUtil.query(getActivity(), Config.KEY_USERNAME, "String")));
//                 *  List<IPort> iPortList = new ArrayList<>();
//                 *   for (int i = 0; i < list.size(); i++) {
//                 根据网关号获取ip和端口
//                 IPort iPort = util.query((String) (SharedPreferencesUtil.query(getActivity(), Config.KEY_USERNAME, "String")), list.get(i));
//                 iPortList.add(iPort);//端口
//                 Config.writeToDebug("查询数据库中的IP地址和端口号：iPortList" + i + ":" + iPort.getIp() + " " + iPort.getPort() + " " + iPort.getGateWayNumber());
//                 }
//                 防备设备服务器，获取值后往数据模型里添加数据，得到的一定是一个Json数组，然后模仿MyDeviceFragment进行书写
//                 */
//                NotificationManager mn= (NotificationManager) test.this.getSystemService(NOTIFICATION_SERVICE);//获取NotifacationManager服务
//                Notification.Builder builder=new Notification.Builder(test.this);
//                Intent notificationIntent=new Intent(test.this, TabFragmentActivity.class);//点击跳转位置
//                PendingIntent contentIntent=PendingIntent.getActivity(test.this,0,notificationIntent,0);
//                builder.setContentIntent(contentIntent);
//                builder.setSmallIcon(R.drawable.abc_ic_search);
//                builder.setTicker("滤网通知信息");
//                builder.setContentText("阿斯顿发的说法");
//                builder.setContentTitle("滤网");
//                builder.setAutoCancel(true);
//                builder.setDefaults(Notification.DEFAULT_ALL);
//                Notification notification=builder.build();
//                mn.notify((int)System.currentTimeMillis(),notification);
//
//            }
//        },delay,period);//一秒后开始执行，每隔6s执行一次
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
////        Log.i("HELLO","stop");
////        test.this.stopSelf();
//    }
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//    public void cleanAllNotification(){
//        NotificationManager mn= (NotificationManager)test.this.getSystemService(NOTIFICATION_SERVICE);
//        mn.cancelAll();
//        if(timer!=null){
//            timer.cancel();
//            timer=null;
//        }
//    }
//    public void startService(){
//
//    }
}
