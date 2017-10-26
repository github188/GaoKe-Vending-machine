package com.aircontrol.demo.ui;

import android.app.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aircontrol.demo.Config;
import com.aircontrol.demo.R;
import com.aircontrol.demo.domain.goods_bean;
import com.aircontrol.demo.sample.SerialPortActivity;
import com.aircontrol.demo.utils.FCS;
import com.aircontrol.demo.utils.QRCodeUtil;
import com.aircontrol.demo.utils.StreamTool;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android_serialport_api.SerialPort;


public class alipayActivity extends SerialPortActivity {
    private ImageView barcodeImage;
    private QRCodeUtil qrCodeUtil;
    private double total_amount=0.00;//总金额
    private String terminal_id="111";
    private String Subject="alipay";
    private goods_bean goods;
    private String url="";
    private String isSuccessUrl="";
    private TextView barprice;
    private TextView barback,barclose;

    private String qr_code="";
    private String sub_code="";
    private String code="";
    private String out_trade_no="";
    private String sub_msg="";
    private String goodsId="";
    private Dialog dialog;
    private Thread requestAlipay;
    private Thread ispaySuccess;
    private boolean isSuccess=true;
    private SerialPort serialPort;
    private String Fcs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//启动串口，获得输入输出流
        setContentView(R.layout.activity_alipay);
        initView();
        startDialog("正在生成二维码");
        qrCodeUtil=new QRCodeUtil();
        url=Config.getRequestURL(Config.Alipay);//支付接口
        isSuccessUrl=Config.getRequestURL(Config.IsPaySuccess);//轮询接口
        Intent intent=getIntent();
        Bundle data=intent.getExtras();
        total_amount=data.getDouble("price");
        goodsId=data.getString("goods_id");
        requestAlipay=new Thread(Alipayment);
        requestAlipay.start();
        ispaySuccess=new Thread(ispay);
        initEvent();
    }

    @Override
    protected void onDataReceived(byte[] buffer, int size) {
            //对接收到的数据进行处理
    }

    private Runnable ispay=new Runnable() {
        @Override
        public void run() {
            final HashMap<String,String> params=new HashMap<>();
            params.put("tradeNo",out_trade_no);//订单号
            StringBuilder data1=new StringBuilder();
            data1.append("?");
            if(params!=null&&!params.isEmpty()){
                for(Map.Entry<String,String>entry:params.entrySet()){
                    try {
                        data1.append(entry.getKey()).append("=");
                        data1.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                        data1.append("&");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                data1.deleteCharAt(data1.length()-1);
             // byte[] entity=data1.toString().getBytes();//发送的参数，转换为字节数组
                String str = isSuccessUrl+data1.toString();
                HttpURLConnection conn=null;
                try {
                    while(isSuccess){
                        conn=(HttpURLConnection)new URL(str).openConnection();
                        conn.setConnectTimeout(5000);
                        conn.setRequestMethod("GET");
                        conn.setDoOutput(false);
                        conn.setDoInput(true);//打开读取的管道
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        //conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
                        conn.setRequestProperty("Charset","utf-8");
                        if(conn.getResponseCode()==200){
                            InputStream is=conn.getInputStream();//获取返回数据流
                            String result = new String(StreamTool.read(is));//获取服务器的返回值
                            JSONObject object=new JSONObject(result);//转换为JSON数据格式
                            String tradeStatus=object.getString("tradeStatus");
                            switch (tradeStatus){
                                case Config.TRADE_SUCCESS:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(alipayActivity.this,"支付成功!!",Toast.LENGTH_SHORT).show();
                                            isSuccess=false;
                                            //向串口发送指令数据
//                                            int i;
//                                            CharSequence t;
//                                            Fcs= FCS.fnFCS(Config.SendDataTest);
//                                            t=Config.SendDataTest+Fcs+Config.Enter;
//                                            char[]text=new char[t.length()];
//                                            for( i=0;i<t.length();i++){
//                                                text[i]=t.charAt(i);
//                                            }
//                                            try {
//                                                mOutputStream.write(new String(text).getBytes());//发送数据
//                                            } catch (IOException e) {
//                                                e.printStackTrace();
//                                            }

                                        }
                                    });
                                    break;
                                case Config.TRADE_FALIURE:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(alipayActivity.this,"支付失败!!",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Runnable Alipayment=new Runnable() {
        @Override
        public void run() {
            final HashMap<String,String> params=new HashMap<>();
            params.put("terminal_id",terminal_id);
            params.put("Subject",Subject);

            final HashMap<String,Double>params1=new HashMap<>();
            params1.put("total_amount",total_amount);

            StringBuilder data1=new StringBuilder();
            data1.append("?");
            if((params!=null)&&(!params.isEmpty())&&(params1!=null)&&(!params1.isEmpty())){

                for(Map.Entry<String,String>entry:params.entrySet()){
                    try {
                        data1.append(entry.getKey()).append("=");
                        data1.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                        data1.append("&");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                for(Map.Entry<String,Double>entry1:params1.entrySet()){
                    try {
                        data1.append(entry1.getKey()).append("=");
                        data1.append(URLEncoder.encode(String.valueOf(entry1.getValue()), "UTF-8"));
                        data1.append("&");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                data1.deleteCharAt(data1.length() - 1);
                byte[] entity=data1.toString().getBytes();//发送的参数，转换为字节数组
                HttpURLConnection conn=null;
                try {
                    conn=(HttpURLConnection)new URL(url).openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);//打开读取的管道
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
                    conn.setRequestProperty("Charset","utf-8");
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(entity);//发送请求
                    outputStream.flush();
                    if(conn.getResponseCode()==200){
                        
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            //Toast.makeText(alipayActivity.this,"哈哈",Toast.LENGTH_SHORT).show();
                            }
                        });
                        InputStream is=conn.getInputStream();//获取返回数据流
                        String result = new String(StreamTool.read(is));//获取服务器的返回值
                        JSONObject object=new JSONObject(result);//转换为JSON数据格式
                        String status=object.getString("msg");
                        switch (status){
                            case Config.StringRequestSuccess:
                                qr_code=object.getString("qr_code");
                                sub_code=object.getString("sub_code");
                                code=object.getString("code");
                                out_trade_no=object.getString("out_trade_no");
                                sub_msg=object.getString("sub_msg");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        createBarcode(qr_code);
                                    }
                                });
                                break;
                            default:
                                break;
                        }
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(alipayActivity.this,"失败",Toast.LENGTH_SHORT).show();
                                alipayActivity.this.finish();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            endDialog();
        }
    };
    private void startDialog(String msg) {
        dialog = new Dialog(alipayActivity.this, R.style.MyDialogStyle);
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

    public void initEvent(){

        barprice.setText("￥"+total_amount);

        barback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alipayActivity.this.finish();
                ispaySuccess.interrupt();
                requestAlipay.interrupt();
                isSuccess=false;
            }
        });

        barclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alipayActivity.this.finish();
                requestAlipay.interrupt();
                ispaySuccess.interrupt();
                isSuccess=false;
            }
        });
    }
    public void requestAlipay(final String url, double total_amount, String terminal_id, String Subject){

        final HashMap<String,String> params=new HashMap<>();
        params.put("terminal_id",terminal_id);
        params.put("Subject",Subject);

        final HashMap<String,Double>params1=new HashMap<>();
        params1.put("total_amount",total_amount);
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder data1=new StringBuilder();
                data1.append("?");
                if((params!=null)&&(!params.isEmpty())&&(params1!=null)&&(!params1.isEmpty())){

                    for(Map.Entry<String,String>entry:params.entrySet()){
                        try {
                            data1.append(entry.getKey()).append("=");
                            data1.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                            data1.append("&");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                   for(Map.Entry<String,Double>entry1:params1.entrySet()){
                       try {
                           data1.append(entry1.getKey()).append("=");
                           data1.append(URLEncoder.encode(String.valueOf(entry1.getValue()), "UTF-8"));
                           data1.append("&");
                       } catch (UnsupportedEncodingException e) {
                           e.printStackTrace();
                       }
                   }
                    data1.deleteCharAt(data1.length() - 1);
                    byte[] entity=data1.toString().getBytes();//发送的参数，转换为字节数组
                    HttpURLConnection conn=null;
                    try {
                        conn=(HttpURLConnection)new URL(url).openConnection();
                        conn.setConnectTimeout(5000);
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);//打开读取的管道
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
                        conn.setRequestProperty("Charset","utf-8");
                        OutputStream outputStream = conn.getOutputStream();
                        outputStream.write(entity);//发送请求
                        outputStream.flush();
                        if(conn.getResponseCode()==200){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                  //Toast.makeText(alipayActivity.this,"哈哈",Toast.LENGTH_SHORT).show();
                                }
                            });
                            InputStream is=conn.getInputStream();//获取返回数据流
                            String result = new String(StreamTool.read(is));//获取服务器的返回值
                            JSONObject object=new JSONObject(result);//转换为JSON数据格式
                            String status=object.getString("msg");
                            switch (status){
                                case Config.StringRequestSuccess:
                                    qr_code=object.getString("qr_code");
                                    sub_code=object.getString("sub_code");
                                    code=object.getString("code");
                                    out_trade_no=object.getString("out_trade_no");
                                    sub_msg=object.getString("sub_msg");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            createBarcode(qr_code);
                                        }
                                    });
                                    break;
                                default:
                                    break;
                            }
                        }
                        else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(alipayActivity.this,"失败",Toast.LENGTH_SHORT).show();
                                    alipayActivity.this.finish();
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                endDialog();
            }
        }).start();
    }
    public void initView(){
        barcodeImage=(ImageView)findViewById(R.id.barImage);
        barprice=(TextView)findViewById(R.id.barprice);
        barback=(TextView)findViewById(R.id.barback);
        barclose=(TextView)findViewById(R.id.barclose);
    }
    public void createBarcode(String qr_code){
        //访问服务器携带几个参数，等待服务器返回数据，然后将返回链接转换成二维码
        Bitmap qrBitmap=qrCodeUtil.createBitmap(qr_code,200,200);
        Bitmap logoBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.alipay);
        Bitmap bitmap=qrCodeUtil.addLogo(qrBitmap,logoBitmap);
        barcodeImage.setImageBitmap(bitmap);
       //Toast.makeText(alipayActivity.this,""+out_trade_no,Toast.LENGTH_SHORT).show();
        /**********检查是否支付成功************/
       if(out_trade_no!=null){
           ispaySuccess.start();
       }
    }
}

