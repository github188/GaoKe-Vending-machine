package com.aircontrol.demo.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.aircontrol.demo.Config;
import com.aircontrol.demo.R;
import com.aircontrol.demo.adapter.grid_adapter;
import com.aircontrol.demo.domain.goods_bean;
import com.aircontrol.demo.domain.shopCar;
import com.aircontrol.demo.utils.StreamTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class shopgoodsActivity extends Activity {
    public  String terminal_id="111";
    private TextView backlevel,helpImag;
    public static TextView shopppingCar;
    private String goodsId;
    private String goodsName;
    private double price;
    private String describe;
    private String imgUrl;
    private Bitmap bitmap;
    private  String url;
    private ArrayList<goods_bean>data=new ArrayList<>();
    private grid_adapter gridAdpter;
    private GridView gridgoods;
    public static TextView shopNumber;
    private goods_bean goodsbean=null;
    private String shopName="";//购物名
    public static ArrayList<shopCar>shopping=new ArrayList<>();//购物车
    private Dialog dialog;
    public static int shopCarNumber=0;
    private shopCar shopcar;//购物车
    private Message msg;
    private Thread gain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopgoods);
        initView();//初始化控件
        initEvent();//事件
        url=Config.getRequestURL(Config.getGoods);//http://108.61.201.243:8080/AutomatControlSystem/goods/all
        startDialog("正在获取数据");
        gain=new Thread(getgoodsinformation);
        gain.start();
        //getGoodsInformation(terminal_id,url);//获取服务器数据
        gridAdpter=new grid_adapter(shopgoodsActivity.this,data,handler);
        gridgoods.setAdapter(gridAdpter);
        gridAdpter.notifyDataSetChanged();
        gridgoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goodsbean=data.get(position);//获取货物信息
                Intent intent=new Intent(shopgoodsActivity.this,payOptionActivity.class);
                Bundle b=new Bundle();
                b.putDouble("price",goodsbean.getPrice());
                b.putString("goods_id",goodsbean.getGoodsId());
                intent.putExtras(b);
                gain.interrupt();
                startActivity(intent);
            }
        });
    }
    private Runnable getgoodsinformation=new Runnable() {
        @Override
        public void run() {
            final HashMap<String,String> params=new HashMap<>();
            params.put("terminal_id",terminal_id);
            StringBuilder data1=new StringBuilder();
            data1.append("?");
            if(params!=null&&!params.isEmpty()){
                for(Map.Entry<String,String>entry:params.entrySet()){
                    try {
                        data1.append(entry.getKey()).append("=");
                        data1.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
                        data1.append("&");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                data1.deleteCharAt(data1.length()-1);
                byte[] entity=data1.toString().getBytes();//发送的参数，转换为字节数组
                String str = url+data1.toString();
                try {//走去grid_adapter
                    HttpURLConnection conn=null;
                    conn=(HttpURLConnection)new URL(str).openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    conn.setDoOutput(false);
                    conn.setDoInput(true);//打开读取的管道
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
                    conn.setRequestProperty("Charset","utf-8");
                    if(conn.getResponseCode()==200){
                        InputStream is=conn.getInputStream();//获取返回数据流
                        String result = new String(StreamTool.read(is));//获取服务器的返回值
                        JSONObject object=new JSONObject(result);//转换为JSON数据格式
                        int status=object.getInt("state");//获取状态
                        switch(status){
                            case Config.RequestSuccess:
                                JSONArray array=object.optJSONArray("goods_list");//获取JSON数组
                                for(int i=0;i<array.length();i++) {
                                    goodsId = array.getJSONObject(i).getString("goodsId");
                                    goodsName = array.getJSONObject(i).getString("goodsName");
                                    price = array.getJSONObject(i).getDouble("price");
                                    describe = array.getJSONObject(i).getString("goodsCategory");
                                    imgUrl = array.getJSONObject(i).getString("showUrl");
                                    bitmap = getBitmap(imgUrl);//添加数据
                                    data.add(new goods_bean(goodsId, goodsName, price, describe, bitmap));
                                }
                                gridAdpter.setData(data);
                                gridAdpter.notifyDataSetChanged();
                                gridAdpter.notifyDataSetInvalidated();
//                              msg=new Message();
//                              msg.arg1=Config.MESSAGE_WHAT_OBTAIN_GOODS_SUCCESS;
//                              msg.obj=data;
//                              handler.sendMessage(msg);
                                break;
                            default:
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(shopgoodsActivity.this,"访问错误!!!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                        }
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(shopgoodsActivity.this,"访问服务器失败!!!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
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
            try {
                Thread.sleep(200);
                endDialog();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    public void initData(){
     //data.add(new goods_bean("123456","我爱你",12.0,"哈哈",));
    }
    public void initView(){
        backlevel=(TextView)findViewById(R.id.backlevel);//返回
        helpImag=(TextView)findViewById(R.id.helpImag);//帮助
        shopppingCar=(TextView)findViewById(R.id.shopppingCar);//购物车
        gridgoods=(GridView)findViewById(R.id.gridgoods);//货物栏
        shopNumber=(TextView)findViewById(R.id.number);//数量
    }

    private void startDialog(String msg) {
        dialog = new Dialog(shopgoodsActivity.this, R.style.MyDialogStyle);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);//dialog弹出后点击屏幕，dialog不消失;点击物理键返回键后dialog消失
        TextView message = (TextView) dialog.getWindow().findViewById(R.id.load_msg);//绑定里面的组件
        if (dialog != null && !dialog.isShowing()) {
            message.setText(msg);//设置提示信息
            dialog.show();
        }
    }
    private void endDialog(){
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

 private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.arg1){
                case Config.MESSAGE_WHAT_ADD_GOODS_SUCCESS:
                    shopcar=(shopCar) msg.obj;//获取购物车货物
                if(shopCarNumber>=0){
                    shopCarNumber=shopCarNumber+1;//数量加一
                    shopping.add(shopcar);//添加到购物车
                    shopNumber.setVisibility(View.VISIBLE);//货物数量显示
                    shopNumber.setText(shopCarNumber+"");//显示货物数量
                }
                else{
                    shopNumber.setVisibility(View.INVISIBLE);
                }
                case Config.MESSAGE_WHAT_OBTAIN_GOODS_SUCCESS:
//                        gridAdpter=new grid_adapter(shopgoodsActivity.this,data,handler);
//                        gridgoods.setAdapter(gridAdpter);
//                        JSONArray array=(JSONArray) msg.obj;
//                        for(int i=0;i<array.length();i++){
//                            try {
//                                goodsId=array.getJSONObject(i).getString("goodsId");
//                                goodsName=array.getJSONObject(i).getString("goodsName");
//                                price=array.getJSONObject(i).getDouble("price");
//                                describe=array.getJSONObject(i).getString("goodsCategory");
//                                imgUrl=array.getJSONObject(i).getString("showUrl");
//                                bitmap=getBitmap(imgUrl);//添加数据
//                                data.add(new goods_bean(goodsId,goodsName,price,describe,bitmap));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    gridAdpter=new grid_adapter(shopgoodsActivity.this,data,handler);
//                    gridgoods.setAdapter(gridAdpter);
//                    gridAdpter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    public void getGoodsInformation(String terminl_id,final String url){
        final HashMap<String,String> params=new HashMap<>();
        params.put("terminal_id",terminl_id);
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder data1=new StringBuilder();
                data1.append("?");
                if(params!=null&&!params.isEmpty()){
                    for(Map.Entry<String,String>entry:params.entrySet()){
                        try {
                            data1.append(entry.getKey()).append("=");
                            data1.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
                            data1.append("&");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    data1.deleteCharAt(data1.length()-1);
                    byte[] entity=data1.toString().getBytes();//发送的参数，转换为字节数组
                    String str = url+data1.toString();
                    try {//走去grid_adapter
                        HttpURLConnection conn=null;
                        conn=(HttpURLConnection)new URL(str).openConnection();
                        conn.setConnectTimeout(5000);
                        conn.setRequestMethod("GET");
                        conn.setDoOutput(false);
                        conn.setDoInput(true);//打开读取的管道
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
                        conn.setRequestProperty("Charset","utf-8");
                      if(conn.getResponseCode()==200){
                            InputStream is=conn.getInputStream();//获取返回数据流
                            String result = new String(StreamTool.read(is));//获取服务器的返回值
                            JSONObject object=new JSONObject(result);//转换为JSON数据格式
                            int status=object.getInt("state");//获取状态
                            switch(status){
                                case Config.RequestSuccess:
                                    JSONArray array=object.optJSONArray("goods_list");//获取JSON数组
                                    for(int i=0;i<array.length();i++) {
                                        goodsId = array.getJSONObject(i).getString("goodsId");
                                        goodsName = array.getJSONObject(i).getString("goodsName");
                                        price = array.getJSONObject(i).getDouble("price");
                                        describe = array.getJSONObject(i).getString("goodsCategory");
                                        imgUrl = array.getJSONObject(i).getString("showUrl");
                                        bitmap = getBitmap(imgUrl);//添加数据
                                        data.add(new goods_bean(goodsId, goodsName, price, describe, bitmap));
                                       //gridAdpter.notifyDataSetChanged();
                                    }
                                    gridAdpter.setData(data);
                                    gridAdpter.notifyDataSetChanged();
                                    gridAdpter.notifyDataSetInvalidated();
//                                  msg=new Message();
//                                  msg.arg1=Config.MESSAGE_WHAT_OBTAIN_GOODS_SUCCESS;
//                                  msg.obj=data;
//                                  handler.sendMessage(msg);
                                    break;
                                default:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(shopgoodsActivity.this,"访问错误!!!",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                            }
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(shopgoodsActivity.this,"访问服务器失败!!!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
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
                try {
                    Thread.sleep(200);
                    endDialog();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
       public Bitmap getBitmap(String path){
       try {
           URL url=new URL(path);
           HttpURLConnection conn=(HttpURLConnection)url.openConnection();
           conn.setConnectTimeout(5000);
           conn.setRequestMethod("GET");
           if(conn.getResponseCode()==200){
               InputStream inputStream=conn.getInputStream();
               Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
               return bitmap;
           }
       } catch (MalformedURLException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }

    public void initEvent(){

        backlevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopgoodsActivity.this.finish();
            }
        });

        shopppingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(shopgoodsActivity.this,goodsActivity.class);//
                intent.putExtra("shopping",shopping);
                startActivity(intent);
            }
        });
    }
}
