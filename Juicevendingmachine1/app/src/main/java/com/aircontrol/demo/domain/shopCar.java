package com.aircontrol.demo.domain;

import java.io.Serializable;

/**
 * Created by 大东 on 2017/4/14 0014.
 */

public class shopCar implements Serializable{

    private String goodsName;//商品名
    private int purchaseNumber;//购买数量
    private double price;//价格
    private String describe;//描述
    private String goodsId;//货物Id

    public shopCar(String goodsName,String goodsId,int purchaseNumber,double price,String describe){

        this.goodsName=goodsName;
        this.goodsId=goodsId;
        this.purchaseNumber=purchaseNumber;
        this.price=price;
        this.describe=describe;

    }

   public shopCar(){

   }

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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
