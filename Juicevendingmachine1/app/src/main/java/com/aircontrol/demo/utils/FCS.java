package com.aircontrol.demo.utils;

/**
 * Created by 大东 on 2017/9/25 0025.
 */

public class FCS {
    //计算真校验序列
    public static String fnFCS(String str){

        char c;
        String Fcs;
        if(str==null){
            return "";
        }
        c=str.charAt(0);
        for(int i=1;i<str.length();i++){
            c^=str.charAt(i);
        }
        char ct=(char) (c>>4);
        if(ct>9){
            ct+='A'-10;
        }
        else{
            ct+='0';
        }
        Fcs=ct+"";
        ct=(char) (c&0x0F);
        if(ct>9){
            ct+='A'-10;
        }
        else{
            ct+='0';
        }
        Fcs+=ct;
        return Fcs;
    }
}
