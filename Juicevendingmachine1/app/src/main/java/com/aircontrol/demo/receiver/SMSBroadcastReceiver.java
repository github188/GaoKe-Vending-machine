package com.aircontrol.demo.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

public class SMSBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Object[] pdus = (Object[])intent.getExtras().get("pdus");
		for(Object p : pdus){
			byte[] pdu = (byte[])p;
			SmsMessage message = SmsMessage.createFromPdu(pdu);
			String content = message.getMessageBody();
			Date date = new Date(message.getTimestampMillis());
			SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");//数据格式
			String receiverTime = format.format(date);
			String senderNumber = message.getOriginatingAddress();
			sendSMS(content, receiverTime, senderNumber);
		}
	}

	private void sendSMS(String content, String receiverTime,
			String senderNumber) {
		//网络请求
	}

}
