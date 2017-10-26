package com.aircontrol.demo.utils;

import android.app.ProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadUtil {

	public static File getFile(String urlpath, String filepath, ProgressDialog pd){
		try{
			URL url = new URL(urlpath);
			File file = new File(filepath);
			FileOutputStream fos = new FileOutputStream(file);
			HttpURLConnection conn= (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(2000);
			int max = conn.getContentLength();
			pd.setMax(max);
			InputStream is = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			int process = 0;
			while((len=is.read(buffer))!=-1){
				fos.write(buffer,0,len);
				process+=len;
				pd.setProgress(process);
				Thread.sleep(30);
			}
			fos.flush();
			fos.close();
			is.close();
			return file;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}