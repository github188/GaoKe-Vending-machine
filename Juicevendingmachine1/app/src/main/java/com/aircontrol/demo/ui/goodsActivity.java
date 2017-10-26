package com.aircontrol.demo.ui;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aircontrol.demo.Config;
import com.aircontrol.demo.R;
import com.aircontrol.demo.adapter.shop_adapter;
import com.aircontrol.demo.domain.goods_bean;
import com.aircontrol.demo.domain.shopCar;


import java.util.ArrayList;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class goodsActivity extends Activity {

    private ImageView close;
    private Button addCar,Buy;
    private TextView item_number,price;
    private EditText et_describe;
    public goods_bean bean;
    private  TextView closetitle;
    public static final String action = "jason.broadcast.action";
    private ListView gridshopgoods;
    private TextView topBack,righthelp;
    private ArrayList<shopCar>shopcar;
    public static shop_adapter shopadapter;
    private String preName="",nowName="";
    private double totalPrice=0,totalPrice1=0;
    private ArrayList<shopCar> shop;
    private TextView totaltip,settle;
    private String goodsId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        initView();
        initEvent();
        shop = (ArrayList<shopCar>) getIntent().getSerializableExtra("shopping");
        /**
         * 把相同的记录合并*/
        /**
         * shop的数量即为购物车的number数量的大小*/
        for(int i=0;i<shop.size()-1;i++){
            for(int j=1;j<shop.size();j++){
                preName=shop.get(i).getGoodsName();//上一个名字
                nowName=shop.get(j).getGoodsName();//现在名字
                if(preName.equals(nowName)){
                    shop.get(i).setPurchaseNumber(shop.get(i).getPurchaseNumber()+1);
                    shop.remove(j);
                }
            }
        }
        initData();
        shopadapter=new shop_adapter(goodsActivity.this,shop,handler);
        gridshopgoods.setAdapter(shopadapter);
       //shopadapter.notifyDataSetChanged();
    }
    public void initData(){

        for(int i=0;i<shop.size();i++){
            totalPrice+=shop.get(i).getPrice()*shop.get(i).getPurchaseNumber();
        }
        totaltip.setText("共计"+totalPrice+""+"元");
    }


  private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            totalPrice1=(double)msg.what;
            switch (msg.arg1){
                case Config.MESSAGE_WHAT_ADD_GOODS_SUCCESS:
                    break;
                case Config.MESSAGE_WHAT_OPERTATOR_SUCCESS:
                    totalPrice=totalPrice1;
                    totaltip.setText("共计"+totalPrice1+""+"元");
                    break;
                default:
                    break;
            }
        }
    };
    public void initView(){
        closetitle=(TextView)findViewById(R.id.closetitle);//关闭案列
        gridshopgoods=(ListView)findViewById(R.id.gridshopgoods);//ListView列表
        topBack=(TextView)findViewById(R.id.topBack);//返回
        righthelp=(TextView)findViewById(R.id.righthelp);//帮助按钮
        totaltip=(TextView)findViewById(R.id.totaltip);//总金额
        settle=(TextView)findViewById(R.id.settle);//付款
    }
    public void initEvent(){

        closetitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsActivity.this.finish();
            }
        });

        topBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsActivity.this.finish();
            }
        });

        settle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(goodsActivity.this,payOptionActivity.class);
                Bundle b=new Bundle();
                b.putDouble("price",totalPrice);
                b.putString("goods_id",goodsId);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
