package com.aircontrol.demo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Encoder {
	public static String encode(String password){
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");//摘要为MD5
			byte[] result = digest.digest(password.getBytes());//password完成Hash计算
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<result.length;i++)
			{
				int number = result[i]&0xff;//保留1的位数
				String str = Integer.toHexString(number);//byte类型的十六进制数
				if(str.length()==1){
					sb.append("0");
					sb.append(str);
				}else{
					sb.append(str);
				}
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

}
