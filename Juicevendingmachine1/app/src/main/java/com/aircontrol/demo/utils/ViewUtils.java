package com.aircontrol.demo.utils;

import android.content.Context;
import android.os.Looper;

public class ViewUtils {

    private static Context mContext;

    public static void init(Context context) {
        ViewUtils.mContext = context.getApplicationContext();
    }


    /**
     * dip转换像素
     *
     * @param dp 需要转换的dip
     * @return 转化后的像素
     */
    public static int dp2pxF(float dp) {
        return (int) (mContext.getResources().getDisplayMetrics().density * dp + 0.5);
    }


    /**
     * 像素转换为dip,结果为int型
     *
     * @param px 需要转换的像素值
     * @return 转换后的dip值
     */
    public static int px2dp(int px) {
        initChecker();
        return (int) px2dpF(px);
    }

    /**
     * 像素转换为dip,结果为浮点型
     *
     * @param px 需要转换的像素
     * @return 像素转换后对应的dip
     */
    public static float px2dpF(int px) {
        initChecker();
        return (px / mContext.getResources().getDisplayMetrics().density + 0.5f);
    }


    /**
     * 初始化检测器
     * 检测是否已经初始化这个工具类
     */
    private static void initChecker() {
        if (mContext == null)
            throw new IllegalStateException("Call this method before, please call init()");
    }

    /**
     * 主线程检查
     * 如果没有在主线程将抛出异常
     */
    public static void mainThreadChecker() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new RuntimeException("Only on main thread operating.");
        }
    }


    /**
     * 检查当前的线程是否在主线程中
     */
    @Deprecated
    public synchronized static void checkThread() {
        mainThreadChecker();
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕的宽度
     */
    public static int getWidth() {
        initChecker();
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getHeight() {
        initChecker();
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
