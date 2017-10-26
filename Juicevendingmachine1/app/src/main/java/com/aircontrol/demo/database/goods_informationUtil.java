package com.aircontrol.demo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aircontrol.demo.domain.goods_bean;

import java.util.ArrayList;

/**
 * Created by 大东 on 2017/4/19 0019.
 */

public class goods_informationUtil {
    private DataBaseOpenHelper helper = null;
    public goods_informationUtil(Context context){
        helper = new DataBaseOpenHelper(context);//实例化DataBaseOpenHelper,可对表进行增删查看
    }
    //添加货物
    public boolean add(goods_bean bean){
        SQLiteDatabase db = helper.getWritableDatabase();
        boolean state = false;
        db.beginTransaction();//开启一个事物
        try {
            db.execSQL("insert into " + DataBaseOpenHelper.database_goods_information + "(id,goodsName, goodsPrice, goodsNumber, goodsDescribe) values(?,?,?,?,?)",
                    new Object[]{null, bean.getGoods_name(), bean.getPrice(), bean.getNumber(), bean.getDescribe()});
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
    //删除信息
    public void delete(String goodsName){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(DataBaseOpenHelper.database_goods_information,"goodsName=?",new String[]{goodsName});//删除某条记录
        db.close();
    }
   //修改信息
    public boolean update(goods_bean bean){
        SQLiteDatabase db = helper.getWritableDatabase();
        boolean state = false;
        ContentValues values = new ContentValues();
        values.put("goods_number", bean.getNumber());
        db.beginTransaction();
        try{
            db.update(DataBaseOpenHelper.database_goods_information, values,"goodsName=?", new String[]{bean.getGoods_name()});
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
    //查询信息
    public goods_bean query(String goodsName){
        goods_bean bean=new goods_bean();
        int goodsNumber=0;
        double price=0;
        String describe="";
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+DataBaseOpenHelper.database_goods_information+" where goodsName=?",new String[]{goodsName});
        while (cursor.moveToNext()) {
            goodsNumber = cursor.getInt(2);//获取第0列的值
            price=cursor.getDouble(3);
            describe=cursor.getString(4);
        }
        bean.setGoods_name(goodsName);
        bean.setNumber(goodsNumber);
        bean.setPrice(price);
        bean.setDescribe(describe);
        cursor.close();
        db.close();
        return bean;
    }
    public ArrayList<goods_bean> queryGoodsAll(){
        ArrayList<goods_bean>beanArrayList=new ArrayList<>();
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor=db.query(DataBaseOpenHelper.database_goods_information,null,null,null,null,null,"id");
        while (cursor.moveToNext()){
            //数据库，名、价、数、描
            String name=cursor.getString(1).toString().trim();
            double price=cursor.getDouble(2);
            int number=cursor.getInt(3);
            String describe=cursor.getString(4).toString().trim();
            //bean模型 名字，数量，价格，描述
    //        beanArrayList.add(new goods_bean(name,number,price,describe));
        }
        return beanArrayList;
    }
}
