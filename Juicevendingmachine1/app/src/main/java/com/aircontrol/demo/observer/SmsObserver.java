package com.aircontrol.demo.observer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.aircontrol.demo.Config;

public class SmsObserver extends ContentObserver{
	
	private static final String DEBUG = "SmsObserver";
	private Context mContext;
	private Handler mHandler;
	private String code="";

	public SmsObserver(Context context, Handler handler) {
		super(handler);
		this.mContext = context;
		this.mHandler = handler;
	}

	@Override
	public void onChange(boolean selfChange, Uri uri) {
		super.onChange(selfChange, uri);
		if(Config.DEBUG){
			Log.i(DEBUG, "uri发生变化");
			Log.i(DEBUG, uri.toString());
		}
		
		if(uri.toString().equals("content://sms/raw")){
			return;
		}
		Uri inboxUri = Uri.parse("content://sms/inbox");
		Cursor c = mContext.getContentResolver().query(inboxUri, null, null, null, "date desc");
		if(c != null){
		    if(c.moveToNext()){
		    	String address = c.getString(c.getColumnIndex("address"));
		    	String body = c.getString(c.getColumnIndex("body"));
		    	
		    	if(Config.DEBUG){
					Log.i(DEBUG, "address is:" + address);
					Log.i(DEBUG, "body is:" + body);
				}
		    	Pattern pattern = Pattern.compile("(\\d{6})");
		    	Matcher matcher = pattern.matcher(body);
		    	if(matcher.find()){
		    		code = matcher.group(0);
		    		if(Config.DEBUG){
		    			Log.i(DEBUG, "code is :" + code);
		    		}
		    	}
		    }
		    c.close();
		    mHandler.obtainMessage(Config.MESSAGE_WHAT_SMS_OBSERVER_REGIST, code).sendToTarget();
		    
		}
	}

	
}
