package com.aircontrol.demo.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aircontrol.demo.Config;
import com.aircontrol.demo.R;
import com.aircontrol.demo.domain.shopCar;

import java.util.ArrayList;

import static com.aircontrol.demo.Config.tempPrice;
import static com.aircontrol.demo.ui.goodsActivity.shopadapter;
import static com.aircontrol.demo.ui.shopgoodsActivity.shopCarNumber;
import static com.aircontrol.demo.ui.shopgoodsActivity.shopNumber;
import static com.aircontrol.demo.ui.shopgoodsActivity.shopping;

/**
 * Created by 大东 on 2017/8/2 0002.
 */

public class shop_adapter extends BaseAdapter {

    private ArrayList<shopCar> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private Message msg;
    private Handler handler;
    private shopCar shopcar;//购物车
    private int goodsnumber=0;
    private TextView totaltip;
    private double totalPrice;
    private double totalPrice1;
    private boolean isView=false;
    public shop_adapter(Context context, ArrayList<shopCar>data,Handler handler){
        this.context=context;
        this.data=data;
        this.handler=handler;
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//获取LayoutInflater服务
        shopcar=new shopCar();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TextView title,price,number,delete,add;
        if(convertView==null||!isView){
            convertView=layoutInflater.inflate(R.layout.listitem,null);
            title=(TextView)convertView.findViewById(R.id.listgoodsname);//名称
            price=(TextView)convertView.findViewById(R.id.centerprice);//价格
            number=(TextView)convertView.findViewById(R.id.listnumber);//数量
            delete=(TextView)convertView.findViewById(R.id.cutline);//减少
            add=(TextView)convertView.findViewById(R.id.addline);//增加
        }
        else{
            ViewHolder viewHolder=(ViewHolder)convertView.getTag();
            title=viewHolder.title;
            price=viewHolder.price;
            number=viewHolder.number;
            delete=viewHolder.delete;
            add=viewHolder.add;
        }
        /**传进来的是一个ArrayList数组*/
        final shopCar bean=data.get(position);
        title.setText(bean.getGoodsName()+"");
        price.setText(bean.getPrice()+"");
        number.setText(bean.getPurchaseNumber()+"");//减少货物，改变的是ArrayList<shopCar>shop的值
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsnumber=data.get(position).getPurchaseNumber();//获取某项货物数量
                goodsnumber=goodsnumber-1;//货物数量减一
                shopCarNumber=shopCarNumber-1;//购物车数量减一
                if(shopCarNumber<=0){
                    shopNumber.setVisibility(View.GONE);
                }
                else{
                    shopNumber.setVisibility(View.VISIBLE);
                }
                if(goodsnumber<=0){
                    goodsnumber=0;
                    data.get(position).setPurchaseNumber(goodsnumber);
                    shopNumber.setText(shopCarNumber+"");//购物车数量
                   //totalPrice-=data.get(position).getPrice();
                    tempPrice-=data.get(position).getPrice();
                    if(tempPrice<=0){
                        tempPrice=0;
                    }
                   //tempPrice=totalPrice;
                    data.remove(position);//删除此条记录
                    shopadapter.notifyDataSetChanged();//重新更新adapter
                    shopping=data;
                    for(int i=0;i<data.size();i++){
                        tempPrice+=(data.get(i).getPrice())*(data.get(i).getPurchaseNumber());
                    }
                    msg=new Message();
                    msg.arg1= Config.MESSAGE_WHAT_OPERTATOR_SUCCESS;//标志
                    msg.what=(int)tempPrice;
                    tempPrice=0;
                    handler.sendMessage(msg);//发送消息
                    return;
                }
                    data.get(position).setPurchaseNumber(goodsnumber);
                    number.setText(goodsnumber+"");//数量
                    shopNumber.setVisibility(View.VISIBLE);
                    for(int i=0;i<data.size();i++){
                        tempPrice+=(data.get(i).getPrice())*(data.get(i).getPurchaseNumber());
                    }
                 // tempPrice=totalPrice;
                    shopNumber.setText(shopCarNumber+"");//购物车数量
                    shopping=data;//购物车数量减一
                    msg=new Message();
                    msg.arg1= Config.MESSAGE_WHAT_OPERTATOR_SUCCESS;//标志
                    msg.what=(int)tempPrice;
                    tempPrice=0;
                    handler.sendMessage(msg);//发送消息
            }
        });
        //增加货物
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsnumber=data.get(position).getPurchaseNumber();//货物数量
                goodsnumber=goodsnumber+1;//数量减一
                data.get(position).setPurchaseNumber(goodsnumber);
                number.setText(goodsnumber+"");
                for(int i=0;i<data.size();i++){
                    totalPrice1+=(data.get(i).getPrice())*(data.get(i).getPurchaseNumber());
                }
                shopNumber.setText(goodsnumber+"");//购物车数量
                shopping=data;
                msg=new Message();
                msg.arg1= Config.MESSAGE_WHAT_OPERTATOR_SUCCESS;//标志
                msg.what=(int)totalPrice1;
                totalPrice1 = 0;
                handler.sendMessage(msg);//发送消息
            }
        });
        return convertView;
    }

    public final class ViewHolder{
        public TextView title;
        public TextView price;
        public TextView number;
        public TextView delete;
        public TextView add;
        public ViewHolder(TextView title,TextView price,TextView number,TextView delete,TextView add){
            this.title=title;
            this.price=price;
            this.number=number;
            this.delete=delete;
            this.add=add;
        }
    }
}
