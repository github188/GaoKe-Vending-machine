package com.aircontrol.demo.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	
	private static Toast mToast;
	
	/**
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void showToast(Context context, CharSequence text, int duration){
		if(mToast == null){
			mToast = Toast.makeText(context, text, duration);
		}else{
			mToast.setText(text);
			mToast.setDuration(duration);
		}
		mToast.show();
	}
}
