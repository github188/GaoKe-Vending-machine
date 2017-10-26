package com.aircontrol.demo.ui;

import android.app.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aircontrol.demo.Config;
import com.aircontrol.demo.R;
import com.aircontrol.demo.domain.goods_bean;


public class payOptionActivity extends Activity {
    private Dialog dialog;
    private TextView wechatpay,alipay,vippay,back,close;
    private goods_bean goods;
    private double price;
    private String goodsId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_option);
        initView();
        initEvent();
        Intent intent=getIntent();
        Bundle data=intent.getExtras();
        price=data.getDouble("price");
        goodsId=data.getString("goods_id");
    }

    public void initView(){
        wechatpay=(TextView)findViewById(R.id.wechatpay);
        alipay=(TextView)findViewById(R.id.alipay);
        vippay=(TextView)findViewById(R.id.vippay);
        back=(TextView)findViewById(R.id.payback);
        close=(TextView)findViewById(R.id.closepay);
    }

    private void startDialog(String msg) {
        dialog = new Dialog(payOptionActivity.this, R.style.MyDialogStyle);
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payOptionActivity.this.finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payOptionActivity.this.finish();
            }
        });

        alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(payOptionActivity.this,alipayActivity.class);
                Bundle b=new Bundle();
                b.putDouble("price",price);
                b.putString("goods_id",goodsId);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
