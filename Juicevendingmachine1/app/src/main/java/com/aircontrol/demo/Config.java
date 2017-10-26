package com.aircontrol.demo;

import android.content.Context;
import android.os.Environment;

import com.aircontrol.demo.utils.SharedPreferencesUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class Config {
    /**
     * 是否开启debug模式
     */
    public static final boolean DEBUG = false;
    public static final String DEBUG_FILE_NAME = "/TICA_DEBUG.txt";
    public static File DEBUG_FILE = null;
    public static boolean isEmptyRoom;
    public static String token="a8985e944bdd6acdd955a03e27cfe0f6";
    public static final int RequestSuccess=1;
    public static final String StringRequestSuccess="Success";
    public static void writeToDebug(String msg) {
        if (DEBUG) {
            try {
                if (DEBUG_FILE == null) {
                    Config.DEBUG_FILE = new File(Environment.getExternalStorageDirectory(), Config.DEBUG_FILE_NAME);
                }
                //第二个参数意义是说是否以append方式添加内容
                BufferedWriter bw = new BufferedWriter(new FileWriter(Config.DEBUG_FILE, true));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(System.currentTimeMillis());
                bw.write(dateString);
                bw.write(" " + msg);
                bw.write("\r\n");
                bw.flush();
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Config.DEBUG_FILE = new File(Environment.getExternalStorageDirectory(), Config.DEBUG_FILE_NAME);
            if(Config.DEBUG_FILE.exists()){
                Config.DEBUG_FILE.delete();
            }
        }
    }

    public static final String BmobSMSToken = "e1ece4992023d2886aae2920f0648162";
    /**
     * 服务器的路径
     */
    public static final String SERVER_URL = "http://icloud.ticachina.com/jeesite";//服务器路径

    /**
     * TCP通信端口
     */
    public static final int TCP_PORT = 9999;
    /**
     * UDP通信端口
     */
    public static final int UDP_PORT = 6500;

    //请求参数键
    public static final String KEY_TOKEN = "sessionid";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_REMEMBER_PWD = "remember_password";
    public static final String KEY_MOBILE_LOGIN = "mobileLogin";
    public static final String KEY_DATA_LIST = "dutnlits";
    public static final String KEY_SHARED_SAVE_LIST = "gayeway_list";
    public static final String KEY_IP = "ip";
    public static final String KEY_PORT = "port";
    public static final String KEY_GATEWAY_NUMBER = "gatewayNumber";
    public static  int FragmentState=0;
    /**
     * 本地存储xml文件名
     */
    public static final String APP_ID = "com.example.frame";


    //HTTP消息，用于发送系统消息的what值，从100开始
    /**
     * 发送短信到来的消息
     */
    public static final int MESSAGE_WHAT_SMS_OBSERVER_REGIST = 100;

    public static final int MESSAGE_WHAT_ADD_GOODS_SUCCESS=1;
    public static final int MESSAGE_WHAT_OBTAIN_GOODS_SUCCESS=2;
    public static final int MESSAGE_WHAT_OPERTATOR_SUCCESS=3;
    public static final String action = "jason.broadcast.action";
    public static final String TRADE_SUCCESS="TRADE_SUCCESS";
    public static final String TRADE_FALIURE="WAIT_BUYER_PAY";
    public static final String APP_DATABASE_NAME = "Juice_vending_machine";
    public static boolean ISshopping=false;
    public static double tempPrice=0;
    /*********************************************************************************************/
    /****************************************总的请求路径*****************************************/
   // public static final String Request_path="http://108.61.201.243:8080/AutomatControlSystem";

    public static final String Request_path="http://192.168.191.1:8080/AutomatControlSystem";
    /**
     * 请求商品接口
     * */
    public static final String getGoods="goods/all";
    /**
     * 支付宝支付接口
     * */
    public static final String Alipay="alipay/pay";
    /**
     * 轮询接口
     * */
    public static final String IsPaySuccess="goods/tradeStatus";
   // @00RD0100000156*\CR

    public static final String SendDataTest="@00RD01000001";
    public static String Fcs="";
    public static final String Enter="*\r\n";

    /**********************关联表***************************/




    /**********************关联表***************************/


/*************************************************************************************************/
    /**
     * 得到相应action请求的URL路径
     *
     * @param action 需要请求的action
     * @return URL路径
     */
    public static String getRequestURL(String action) {

        return Request_path + "/" + action;//得到服务器路径加上请求的动作

    }

    //    public static String getAfterLoginRequestURL(Context context, String action) {
    //        String token = (String) SharedPreferencesUtil.query(context, KEY_TOKEN, "String");
    //        String url = SERVER_URL + "/" + action + ";" + "JSESSIONID=" + token + "?__ajax=true";
    //        return url;
    //    }

}
