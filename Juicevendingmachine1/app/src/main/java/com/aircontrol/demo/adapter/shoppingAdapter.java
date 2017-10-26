package com.aircontrol.demo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.aircontrol.demo.R;
import com.aircontrol.demo.domain.goods_bean;
import com.aircontrol.demo.domain.shopgoods;
import com.aircontrol.demo.ui.FunctionActivity;

import java.util.ArrayList;

/**
 * Created by 大东 on 2017/7/19 0019.
 */

public class shoppingAdapter extends BaseAdapter {

    public shoppingAdapter(Context context, ArrayList<shopgoods>data){

    }
    @Override
    public int getCount() {
        return 0;
    }
    @Override
    public Object getItem(int position) {
        return "";
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){

        }else{

        }
        return convertView;
    }
    public final class ViewHolder{
    }
}
