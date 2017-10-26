package com.aircontrol.demo.domain;

/**
 * Created by 大东 on 2017/7/19 0019.
 */

public class shopgoods {
    private String goodsName;
    private double goodsPrice;
    private int  goodsNumber=0;
    public shopgoods(){

    }
    public shopgoods(String goodsName,double goodsPrice,int goodsNumber){
        this.goodsName=goodsName;
        this.goodsPrice=goodsPrice;
        this.goodsNumber=goodsNumber;
    }
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public double getGoodsPrice() {
        return goodsPrice;
    }
    public void setGoodsPrice(float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
    public int getGoodsNumber() {
        return goodsNumber;
    }
    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }
}
