package com.aircontrol.demo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aircontrol.demo.Config;
import com.aircontrol.demo.R;
import com.aircontrol.demo.database.goods_informationUtil;
import com.aircontrol.demo.database.shopcar_informationUtil;
import com.aircontrol.demo.domain.goods_bean;
import com.aircontrol.demo.domain.shopCar;
import com.aircontrol.demo.ui.FunctionActivity;

import java.util.ArrayList;



/**
 * Created by 大东 on 2017/4/14 0014.
 */

public class grid_adapter extends BaseAdapter {
    private ArrayList<goods_bean>data;
    private LayoutInflater layoutInflater;
    private Context context;
    private Handler handler;
    private int number=0;
    private int BaseNumber=1;
    private  shopCar shop;//购物车
    private  goods_bean bean;
    private Message msg;
    private String oldName="",nowName="";
    private boolean isClick=false;
    public grid_adapter(Context context, ArrayList<goods_bean>data,Handler handler){
        this.context=context;
        this.data=data;
        this.handler=handler;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//获取LayoutInflater服务
        shop=new shopCar();
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
    public void setData(ArrayList<goods_bean>data){
        this.data=data;//数据
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageView goodsImageView;//视图
        final ImageView uselessImageview;
        final TextView price;//价格
        TextView addGoods;//添加数量
        if(convertView==null)
        {
            convertView=layoutInflater.inflate(R.layout.grid_layout,null);
            goodsImageView=(ImageView) convertView.findViewById(R.id.goodsImage);
            uselessImageview=(ImageView)convertView.findViewById(R.id.useless);
            price=(TextView)convertView.findViewById(R.id.pricetext);
            addGoods=(TextView)convertView.findViewById(R.id.add);
        }
        else{
            ViewHolder viewHolder=(ViewHolder)convertView.getTag();
            goodsImageView=viewHolder.imageView;
            uselessImageview=viewHolder.uselessImageView;
            price=viewHolder.price;
            addGoods=viewHolder.addGoods;
        }
        final goods_bean bean=data.get(position);//获取某一项
        goodsImageView.setImageBitmap(bean.getBitmap());//设置图片
        uselessImageview.setImageResource(R.drawable.useless);//事先隐藏已售光的图片
        price.setText(bean.getPrice()+"");//设置价格
        addGoods.setBackgroundResource(R.drawable.addshopcar);//添加货物至购物车的按钮

        addGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number=bean.getNumber();//获取bean数量
                number=number-1;//货物数量减一
                /**
                 * 如果数量大于等于0，重新设置数量*/
                bean.setNumber(number);//重新设置货物数量
                shop=new shopCar(bean.getGoods_name(),bean.getGoodsId(),1,bean.getPrice(),bean.getDescribe());//添加至购物车
                msg=new Message();//消息
                msg.arg1=Config.MESSAGE_WHAT_ADD_GOODS_SUCCESS;//信息
                msg.what=1;//数量
                msg.obj=shop;//货物
                handler.sendMessage(msg);//发送消息
            }
        });
        return convertView;
    }

    public boolean checkIsEmpty(int position){

          if(data.get(position).getNumber()<=0)
            {
                return true;
            }
            return false;
    }

    public final class ViewHolder{
        public ImageView imageView;
        public ImageView uselessImageView;
        public TextView price;
        public TextView addGoods;
        public ViewHolder(ImageView imageView,ImageView uselessImageView,TextView price,TextView addGoods){
            this.imageView=imageView;
            this.price=price;
            this.uselessImageView=uselessImageView;
            this.addGoods=addGoods;
        }
    }
}
