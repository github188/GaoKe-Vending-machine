package com.aircontrol.demo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aircontrol.demo.Config;

/**
 * Created by 大东 on 2017/4/19 0019.
 */

public class DataBaseOpenHelper extends SQLiteOpenHelper {
    public static final int database_version = 3;  //数据库版本
    //标明
    public static final String database_goods_information = "goods_information";
    public static final String database_shopcar_information = "shopcar_information";

    public DataBaseOpenHelper(Context context) {
        super(context, Config.APP_DATABASE_NAME, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建货物表和购物车表
        sqLiteDatabase.execSQL("CREATE TABLE " + database_goods_information + "(id INTEGER PRIMARY KEY AUTOINCREMENT, goodsName varchar(10), goodsPrice double, goodsNumber integer,goodsDescribe varchar(30))");
        sqLiteDatabase.execSQL("CREATE TABLE " + database_shopcar_information  + "(id INTEGER PRIMARY KEY AUTOINCREMENT,goodsName varchar(20),goodsPrice double, goodsNumber integer,goodsDescribe varchar(30))");
    }
//升级
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
          sqLiteDatabase.execSQL("drop table if exists " + database_goods_information);
          sqLiteDatabase.execSQL("drop table if exists " + database_shopcar_information);
          onCreate(sqLiteDatabase);
    }
}
