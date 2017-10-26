package com.aircontrol.demo.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aircontrol.demo.R;
import com.aircontrol.demo.domain.goods_bean;
import com.aircontrol.demo.domain.shopCar;


import static com.aircontrol.demo.R.drawable.goods;

/**
 * Created by 大东 on 2017/4/17 0017.
 */

public class goodsDialog extends Dialog {
    private ImageView goods_img,close;//图片
    private TextView price,total;//价格
    private EditText describe;//描述
    private TextView NumberInCar,FuNumber;//购物车数量
    private Button AddCar,BuyNow;//加入购物车，立即购买
    private goods_bean bean;//货物信息
    private shopCar shopcar;//购物车
    private int Shopnumber,goodsnumber;
    private String diaprice;
    private String dianame;
    private String diadescribe;
  //  private FunctionActivity functionActivity;
    private Context mContext;
    public goodsDialog(Context context, goods_bean bean,int number,TextView FuNumber,TextView total,int theme){
        super(context,theme);//this.context=context;
        mContext=context;
        this.bean=bean;
        this.Shopnumber=number;
        this.FuNumber=FuNumber;
        this.total=total;
    }

    public goodsDialog(@NonNull Context context) {
        super(context);
    }

    public goodsDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    protected goodsDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_item);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化数据
        initData();
        //时间响应
        initEvent();
    }
    public void initView(){
        goods_img=(ImageView)findViewById(R.id.goods_picture);//货物图片
        close=(ImageView)findViewById(R.id.close);//关闭按钮
        price=(TextView)findViewById(R.id.price);//价格
        describe=(EditText)findViewById(R.id.et_describe);//货物描述
        NumberInCar=(TextView)findViewById(R.id.item_number);//购物车数量
        AddCar=(Button)findViewById(R.id.addCar);//加入购物车
        BuyNow=(Button)findViewById(R.id.buynow);//立即购买
    }
   private double getPrice(){
       double price=0.01;
       price=Double.parseDouble(diaprice);
       return price;
   }
   private String getName(){
       return dianame;
   }
   private String getBody(){
       return diadescribe;
   }
    public void initEvent(){
        AddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsnumber=bean.getNumber();//获取货物数量
                goodsnumber=goodsnumber-1;//数量减一
                bean.setNumber(goodsnumber);//重新设置数量
                if(goodsnumber>=0){
                    Shopnumber=Shopnumber+1;
                    double price=bean.getPrice();//价格
                    double ShopCarTotal= Double.parseDouble(total.getText().toString().substring(0,total.length()-1));
                    price=price+ShopCarTotal;
                    total.setText(price+"元");
                    NumberInCar.setText(Shopnumber+"");
                    FuNumber.setText(Shopnumber+"");//Function界面的
                    //货物信息修改
                    //购物车信息修改或者增加
                }
               else{
                    describe.setText(describe.getText().toString()+"\n"+"已抢完!");
                    bean.setNumber(0);//重新设置数量，货物已经用完
                    //货物信息修改
                }
            }
        });
        BuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击购买，扫码支付
//                diaprice=String.valueOf(bean.getPrice());
//                dianame=bean.getGoods_name();
//                diadescribe=bean.getDescribe();
//                Intent intent=new Intent(mContext,mode_of_payment.class);
//                intent.putExtra("name",dianame);
//                intent.putExtra("price",diaprice);
//                intent.putExtra("describe",diadescribe);
//                mContext.startActivity(intent);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置购物车
                // FuNumber.setText(Shopnumber+"");
                goodsDialog.this.dismiss();
            }
        });
    }
    public void initData(){
        goods_img.setBackgroundResource(R.drawable.abc_ic_clear_disabled);
        price.setText("￥"+bean.getPrice());
        price.setTextColor(Color.RED);
        describe.setText(bean.getDescribe());
        NumberInCar.setText(Shopnumber+"");
    }
}
