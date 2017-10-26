package com.aircontrol.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;

import com.aircontrol.demo.Config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.List;

/**
 * Created by Silenoff on 2016/11/21.
 */

public class SharedPreferencesUtil {

    private static final String CONFIG="config";

    /**
     * 获取SharedPreferences实例对象
     *
     * @param fileName
     */
    private static SharedPreferences getSharedPreference(String fileName) {
        return QRCodeApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * 保存一个String类型的值！
     */
    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putString(key, value).apply();
    }

    /**
     * 获取String的value
     */
    public static String getString(String key, String defValue) {
        SharedPreferences sharedPreference = getSharedPreference(CONFIG);
        return sharedPreference.getString(key, defValue);
    }




    public static void save(Context context, String key, String value) {
        Editor editor = context.getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void save(Context context, String key, int value) {
        Editor editor = context.getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void save(Context context, String key, boolean value) {
        Editor editor = context.getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void save(Context context, String key, long value) {
        Editor editor = context.getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void save(Context context, String key, float value) {
        Editor editor = context.getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        editor.commit();
    }

//    public static boolean saveList(Context context, String key, List<GateWay> value) {
//        if (value == null) {
//            return false;
//        }
//        Editor editor = context.getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE).edit();
//        boolean res;
//        try {
//            String liststr = SceneList2String(value);
//            editor.putString(key, liststr);
//            editor.commit();
//            res = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            res = false;
//        }
//        return res;
//    }

//    public static Object query(Context context, String key, String type) {
//        try {
//            type = type.toUpperCase();
//            if (type.equals("STRING")) {
//                return context.getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE).getString(key, null);
//            } else if (type.equals("INT") | type.equals("INTEGER")) {
//                return context.getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE).getInt(key, -1);
//            } else if (type.equals("BOOLEAN") | type.equals("BOOL")) {
//                return context.getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE).getBoolean(key, false);
//            } else if (type.equals("LONG")) {
//                return context.getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE).getLong(key, -1);
//            } else if (type.equals("FLOAT")) {
//                return context.getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE).getFloat(key, -1);
//            } else if (type.equals("LIST")) {
//                List<GateWay> list = null;
//                String liststr = "";
//                liststr = context.getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE).getString(key, "");
//                if (TextUtils.isEmpty(liststr)) {
//                    return null;
//                }
//                list = String2SceneList(liststr);
//                return list;
//            } else {
//                return null;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static String SceneList2String(List SceneList) throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(SceneList);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    @SuppressWarnings("unchecked")
    public static List String2SceneList(String SceneListString) throws StreamCorruptedException, IOException, ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        List SceneList = (List) objectInputStream.readObject();
        objectInputStream.close();
        return SceneList;
    }

}
