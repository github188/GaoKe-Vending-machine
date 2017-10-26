package com.aircontrol.demo.domain;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by 大东 on 2017/4/14 0014.
 */

public class goods_bean implements Serializable{
    private String goods_name;//商品名
    private int number=0;//数量
    private double price;//价格
    private String describe;//商品描述
    private String goodsId;//商品Id
    private Bitmap bitmap;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public goods_bean(String goodsId, String goods_name,double price, String describe, Bitmap bitmap){
        this.goodsId=goodsId;//商品名字
        this.goods_name=goods_name;//名字
     // this.number=number;//数量
        this.price=price;//价格
        this.describe=describe;//描述
        this.bitmap=bitmap;//图片
    }
    public goods_bean(){

    }
    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
