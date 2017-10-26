package com.aircontrol.demo.requests;

import android.content.Context;
import android.os.AsyncTask;

import com.aircontrol.demo.Config;
import com.aircontrol.demo.utils.SharedPreferencesUtil;
import com.aircontrol.demo.utils.StreamTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 大东 on 2017/3/5 0005.
 */

public class SearchRoom extends AsyncTask {
     private String gateNumber;//网关号
     private String RoomName;//房间名
    private String URL;
    private String phone;
    private Context Context;
    public SearchRoom(Context mContext, String mgateNumber, String mRoomName, String mURL)
    {
        this.Context=mContext;
        this.gateNumber=mgateNumber;
        this.RoomName=mRoomName;
        this.URL=mURL;
 //       phone=(String) SharedPreferencesUtil.query(Context, Config.KEY_USERNAME, "String");//获取号码
    }
    @Override
    protected Object doInBackground(Object[] params1) {
        final HashMap<String, String> params = new HashMap<>();
        params.put("phoneNumber", phone);
        params.put("gatewayNumber", gateNumber);
     //   new Thread(new Runnable() {
//            @Override
//            public void run() {
                StringBuilder data = new StringBuilder();
                if (params != null && !params.isEmpty()) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        try {
                            data.append(entry.getKey()).append("=");
                            data.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                            data.append("&");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        data.deleteCharAt(data.length() - 1);
                        byte[] entity = data.toString().getBytes();//发送的参数,转换为字节数组
                        HttpURLConnection conn = null;
                        conn = (HttpURLConnection) new URL(URL).openConnection();
                        conn.setConnectTimeout(5000);
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
                        OutputStream outputStream = conn.getOutputStream();
                        outputStream.write(entity);//发送请求,参数
                        outputStream.flush();
                        if (conn.getResponseCode() == 200)//访问成功，取服务器返回的结果
                        {
                            InputStream is = conn.getInputStream();
                            String result = new String(StreamTool.read(is));//获取服务器的返回值
                            JSONObject object = new JSONObject(result);
                            JSONArray array1 = object.optJSONArray("dutnlits");//获取JSON数组
                            if (array1 == null) {
                                Config.isEmptyRoom= true;
                                return null;
                            }
                            int size = array1.length();
                            if (size == 0) {
                                Config.isEmptyRoom = true;
                                return null;
                            }
                            for (int i = 0; i < size; i++) {
                                String name = array1.getJSONObject(i).getString("name");
                                if (name.equals(RoomName)) {
                                    Config.isEmptyRoom = false;
                                    return null;
                                }
                            }
                            Config.isEmptyRoom = true;
                        }
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
   //         }
 //       }).start();
        return null;
    }
}
