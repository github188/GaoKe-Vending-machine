package com.aircontrol.demo.utils;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

public class PackageUtils {
	
	/**
	 * 获取当前应用程序的版本号。 版本号存在于我们的APK中对应的清单文件中（直接解压APK后，即可看到对应的清单文件），
	 * 版本号是manifest节点中的android:versionName="1.0" 当一个应用程序被装到手机后
	 * ，该apk拷贝到手机的data/app目录下（也就是系统中），所以想得到版本号，我们需要拿到与系统相关的服务，就可以得到apk中的信息了
	 * 
	 * @return
	 */
	public static String getVersion(Context context) {
		// 得到系统的包管理器。已经得到了apk的面向对象的包装
		PackageManager pm = context.getPackageManager();
		try {
			// 参数一：当前应用程序的包名 参数二：可选的附加消息，这里我们用不到 ，可以定义为0
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			// 返回当前应用程序的版本号
			return info.versionName;
		} catch (Exception e) {// 包名未找到的异常，理论上， 该异常不可能会发生
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 用隐式意图安装一个apk文件
	 * @param file 要安装的完整文件名
	 */
	public static void installApk(File file,Context context) {
		// 隐式意图
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");// 设置意图的动作
		intent.addCategory("android.intent.category.DEFAULT");// 为意图添加额外的数据
		// intent.setType("application/vnd.android.package-archive");
		// intent.setData(Uri.fromFile(file));
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");// 设置意图的数据与类型
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);// 激活该意图
	}

}
