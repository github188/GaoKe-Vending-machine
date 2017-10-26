package com.aircontrol.demo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aircontrol.demo.domain.goods_bean;
import com.aircontrol.demo.domain.shopCar;

/**
 * Created by 大东 on 2017/4/20 0020.
 */

public class shopcar_informationUtil {
    private DataBaseOpenHelper helper = null;
    public shopcar_informationUtil(Context context){
        helper=new DataBaseOpenHelper(context);
    }
    //增加信息
    public boolean Add(shopCar car){
        SQLiteDatabase db=helper.getWritableDatabase();//获取一个用于操作数据库的SQLiteDatabase实例
        boolean state = false;
        db.beginTransaction();//开启一个事物
        try {
            db.execSQL("insert into " + DataBaseOpenHelper.database_shopcar_information + "(id,goodsName, goodsPrice, goodsNumber, goodsDescribe) values(?,?,?,?,?)",
                    new Object[]{null, car.getGoodsName(), car.getPrice(), car.getPurchaseNumber(), car.getDescribe()});
            db.setTransactionSuccessful();//设置事务标志为成功，当结束事务时就会提交事务
            state = true;
        }catch (Exception e){
            e.printStackTrace();
            state = false;
        }finally {
            db.endTransaction();//无论如何都要结束此服务，finally只能用在try/catch语句中
            db.close();//关闭
        }
        return state;
    }
    //删除
    public void delete(String goodsName){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(DataBaseOpenHelper.database_shopcar_information,"goodsName=?",new String[]{goodsName});//删除某条记录
        db.close();
    }
    //更新
    public boolean update(shopCar car){
        SQLiteDatabase db = helper.getWritableDatabase();
        boolean state = false;
        ContentValues values = new ContentValues();
        values.put("goods_number",car.getPurchaseNumber());
        db.beginTransaction();
        try{
            db.update(DataBaseOpenHelper.database_shopcar_information, values,"goodsName=?", new String[]{car.getGoodsName()});
            db.setTransactionSuccessful();
            state = true;
        }catch (Exception e){
            e.printStackTrace();
            state = false;
        }finally {
            db.endTransaction();
            db.close();
        }
        return state;
    }
    //查询
    public shopCar query(String goodsName){
        shopCar bean=new shopCar();
        String name="";//姓名
        int goodsNumber=0;//数量
        double price=0;//价格
        String describe="";//描述
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+DataBaseOpenHelper.database_shopcar_information+" where goodsName=?",new String[]{goodsName});
        //名、价、数、描
        while (cursor.moveToNext()) {
           // name=cursor.getString(1);
            price = cursor.getDouble(2);//获取第0列的值
            goodsNumber=cursor.getInt(3);
            describe=cursor.getString(4);
        }
        bean.setGoodsName(goodsName);
        bean.setPrice(price);
        bean.setPurchaseNumber(goodsNumber);
        bean.setDescribe(describe);
        cursor.close();
        db.close();
        return bean;
    }
}
