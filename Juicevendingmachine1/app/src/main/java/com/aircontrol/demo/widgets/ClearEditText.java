package com.aircontrol.demo.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.aircontrol.demo.R;


public class ClearEditText extends EditText implements OnFocusChangeListener,TextWatcher{

	private Drawable mClearDrawable;
	
	private boolean hasFoucs; 

	public ClearEditText(Context context) {   
        this(context, null);   
    }  
	
	public ClearEditText(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle); 
	}
	
	public ClearEditText(Context context, AttributeSet attrs, int defStyle) {  
		super(context, attrs, defStyle);  
		init();
    }  
	
	
	private void init() {   
        mClearDrawable = getCompoundDrawables()[2]; //获得图片的高
        if (mClearDrawable == null) {   
            mClearDrawable = getResources().getDrawable(R.drawable.et_delete_right);   
        }   
          
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());   
        setClearIconVisible(false);   
        setOnFocusChangeListener(this);    
        addTextChangedListener(this);   
    } 

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		this.hasFoucs = hasFocus;  
        if (hasFocus) {   
            setClearIconVisible(getText().length() > 0);//内容不为空就会显示delete图标
        } else {   
            setClearIconVisible(false);   
        } 
	}
	
	@Override   
    public void onTextChanged(CharSequence s, int start, int count,int after) {   
        if(hasFoucs){  
            setClearIconVisible(s.length() > 0); 
        }  
    } 
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,int after) {
	
	}
	@Override
	public void afterTextChanged(Editable s) {
		
	}
	@Override
	public void setText(CharSequence text, BufferType type) {
		// TODO Auto-generated method stub
		super.setText(text, type);
	}
	@Override   
    public boolean onTouchEvent(MotionEvent event) {  
        if (event.getAction() == MotionEvent.ACTION_UP) {  
            if (getCompoundDrawables()[2] != null) {  
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())  
                        && (event.getX() < ((getWidth() - getPaddingRight())));  
                if (touchable) {  
                    this.setText("");  
                }  
            }  
        }  
        return super.onTouchEvent(event);  
    }
	protected void setClearIconVisible(boolean visible) {   
        Drawable right = visible ? mClearDrawable : null;   //高
        setCompoundDrawables(getCompoundDrawables()[0],   
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);   
    }
}
