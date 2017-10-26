package com.aircontrol.demo.widgets;

import android.app.ActionBar;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aircontrol.demo.R;

public class CenterTitleActionBar {

    private ActionBar mActionBar = null;
    private Context mContext;//上下文
    /*各种标志*/
    private int mBackgroundID = 0;
    private int mBackImageID = 0;
    private String mTitle = "";
    private int mFirstBtnID = 0;
    private int mSecondBtnID = 0;

    private OnClickActionBarListener mOnClickActionBarListener = null;//ActionBar事件监听器

    public CenterTitleActionBar getCenterTitleActionBar() {
        return this;
    }

    /**
     * 标题居中
     */
    public CenterTitleActionBar(Context context,ActionBar actionBar) {
        if (mContext == null) {
            this.mContext = context;
            this.mActionBar = actionBar;
        }
    }
    public void setCustomActionBar() {
        if (mActionBar == null) {
            return;
        }
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); //自定义显示
        mActionBar.setDisplayShowCustomEnabled(true); //使能自定义显示ActionBar
        mActionBar.setCustomView(R.layout.center_title_action_bar); //这里可以设置actionBar的样式，布局可以自己设置，填充actionBar视图
        if (mBackgroundID != 0) {//ActionBar背景标志
            mActionBar.setBackgroundDrawable(mContext.getResources().getDrawable(mBackgroundID));//
        } else {
            mActionBar.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.actionbar_bg));//actionBar的背景图片
        }
        TextView tvTitle = (TextView) mActionBar.getCustomView().findViewById(R.id.menuTvTitle);//ActionBar中间的提示标题
        if (!TextUtils.isEmpty(mTitle)) {
            tvTitle.setText(mTitle);//设置标题
        } else {
            tvTitle.setText(getApplicationName());//获取应用名字
        }
        ImageButton IBtnBack = (ImageButton) mActionBar.getCustomView().findViewById(R.id.menuBtnBack);//返回图片按钮
        if (mBackImageID != 0) {//图片按钮标志
            IBtnBack.setBackground(mContext.getResources().getDrawable(mBackImageID));//
        } else {
            IBtnBack.setBackground(mContext.getResources().getDrawable(R.drawable.back));//设置返回图片的源图片
        }
        TextView TextViewFirst = (TextView) mActionBar.getCustomView().findViewById(R.id.menuFirstTV);//第一个TextView
        if (mFirstBtnID != 0) {
            TextViewFirst.setClickable(true);//设置为可点击
            TextViewFirst.setBackground(mContext.getResources().getDrawable(mFirstBtnID));//空白
        } else {
            TextViewFirst.setClickable(false);//不可点击
        }
        TextView TextViewSecond = (TextView) mActionBar.getCustomView().findViewById(R.id.menuSecondTV);//第二个TextView
        if (mSecondBtnID != 0) {
            TextViewFirst.setClickable(true);//设置为点击
            TextViewSecond.setBackground(mContext.getResources().getDrawable(mSecondBtnID)); //空白
        } else {
            TextViewSecond.setClickable(false);//不可点击
        }
        mActionBar.getCustomView().findViewById(R.id.tvMenuBtnBackClick).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickActionBarListener != null) {
                    mOnClickActionBarListener.onBackBtnClick();//声明这个接口
                }
            }
        });

        mActionBar.getCustomView().findViewById(R.id.menuBtnBack).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickActionBarListener != null) {
                    mOnClickActionBarListener.onBackBtnClick();
                }
            }
        });
        mActionBar.getCustomView().findViewById(R.id.menuTvTitle).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickActionBarListener != null) {
                    mOnClickActionBarListener.onTitleClick();
                }
            }
        });

        if (mFirstBtnID != 0) {
            mActionBar.getCustomView().findViewById(R.id.menuFirstTV).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickActionBarListener != null) {
                        mOnClickActionBarListener.onFirstBtnClick();
                    }
                }
            });
        }
        if (mSecondBtnID != 0) {
            mActionBar.getCustomView().findViewById(R.id.menuSecondTV).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickActionBarListener != null) {
                        mOnClickActionBarListener.onSecondBtnClick();
                    }
                }
            });
        }

    }

    private String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = mContext.getApplicationContext().getPackageManager(); //获取一个PackageManager对象
            applicationInfo = packageManager.getApplicationInfo(mContext.getPackageName(), 0); //参数为包名和标志
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    public static interface OnClickActionBarListener {

        void onBackBtnClick();

        void onTitleClick();

        void onFirstBtnClick();

        void onSecondBtnClick();
    }

    /*getter setter方法*/
    public int getBackgroundID() {
        return mBackgroundID;
    }

    public void setBackgroundID(int backgroundID) {
        mBackgroundID = backgroundID;
    }

    public int getBackImageID() {
        return mBackImageID;
    }

    public void setBackImageID(int backImageID) {
        mBackImageID = backImageID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getFirstBtnID() {
        return mFirstBtnID;
    }

    public void setFirstBtnID(int firstBtnID) {
        mFirstBtnID = firstBtnID;
    }

    public int getSecondBtnID() {
        return mSecondBtnID;
    }

    public void setSecondBtnID(int secondBtnID) {
        mSecondBtnID = secondBtnID;
    }

    public OnClickActionBarListener getOnClickActionBarListener() {
        return mOnClickActionBarListener;
    }

    public void setOnClickActionBarListener(OnClickActionBarListener onClickActionBarListener) {
        mOnClickActionBarListener = onClickActionBarListener;
    }
}