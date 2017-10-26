package com.aircontrol.demo.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Range extends View{
	
	Canvas drawCanvas = null;
	private int mTextOriginColor = 0xffffffff;
	private int backColor = 0xff1f71a5;  //光圈背景颜色
	private int mWidth=0;            
	private int mHeight=0;            
	private int fragmentHeight = 104;  
	private int[] colorsInt = new int[]{0xffffffff,0xffffffff,0xffffffff,0xffffffff};
	private int mSpeedRoate = 0;           

	private int positionX = 0;
	private int positionY = 0;
	
	public Range(Context context)
	{
		super(context);
	}
	public Range(Context context, AttributeSet attrs) {
		super(context, attrs);
		drawCanvas = new Canvas();
	}
	
	@Override
	public void draw(Canvas canvas) {
		mWidth = this.getWidth();
		mHeight = this.getHeight();
		DrawControls(canvas,mWidth,mHeight,mSpeedRoate);  //1080x720
        ShowSpeedText(canvas,mWidth,mHeight,mSpeedRoate);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	
	private void DrawControls(Canvas canvas,int controlsWidth,int controlsHeight,int speed)
	{
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//画笔
        paint.setStrokeWidth(16);
        paint.setStyle(Style.FILL);
        positionX = (int)controlsWidth/2;
		positionY = controlsHeight-fragmentHeight-130;  
		
		int defaultX = controlsWidth/5;  
		int defaultY = controlsHeight - fragmentHeight+30;  
		
		if(speed>=0&&speed<15)
		{
			paint.setColor(colorsInt[0]); 
		}
		else if(speed>=15&&speed<30)
		{
			paint.setColor(colorsInt[1]);  
		}
		else if(speed>=30&&speed<45)
		{
			paint.setColor(colorsInt[2]); 
		}
		else
		{
			paint.setColor(colorsInt[3]);  
		}
		
		DrawBackground(canvas,defaultX,defaultY,positionX,positionY,speed);
		DrawLines(canvas,paint,defaultX,defaultY,positionX,positionY,speed);
	}
	
	private void ShowSpeedText(Canvas canvas,int controlsWidth,int controlsHeight,int speed)
	{
		int x = (int)controlsWidth/2;
		int y = controlsHeight-fragmentHeight;
		Paint paintspeed = new Paint(Paint.ANTI_ALIAS_FLAG);
		paintspeed.setColor(mTextOriginColor);
		paintspeed.setTextSize(180);
		paintspeed.setStyle(Style.FILL);
		Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
		paintText.setColor(mTextOriginColor);
		paintText.setTextSize(60);
		paintText.setStyle(Style.FILL);
		if(speed<10)
		{
			canvas.drawText("0"+String.valueOf(speed), x-100, y - 100, paintspeed);
		}else{
			canvas.drawText(String.valueOf(speed), x-100, y - 100, paintspeed);
		}
		canvas.drawText("空气质量评分", x-180, y + 20, paintText);
		Log.i("TAG", String.valueOf(speed)); 
	}
	
    private void DrawBackground(Canvas canvas,int defaultX, int defaultY, int posX, int posY, int speed) {
    	int r = (int) Math.sqrt((double)(posX-defaultX)*(posX-defaultX)+(posY-defaultY)*(posY-defaultY)) - 50;
		int R = r+50;
		double beta;
		double m=0;
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        p.setStrokeWidth(16);//设置画笔的空心线宽
        p.setStyle(Style.FILL);//设置画笔的风格，实心还是空心
        p.setColor(backColor);//设置颜色
		for(float i=0;i<100*2.65;i+=5)
		{
			m =i * Math.PI / 180;
			beta = 40* Math.PI / 180;
			float x1 = (float) (posX - r*Math.cos(beta-m));
			float y1 = (float) (posY + r*Math.sin(beta-m));
			float x2 = (float) (posX - R*Math.cos(beta-m));
			float y2 = (float) (posY + R*Math.sin(beta-m));
			canvas.drawLine(x1, y1, x2, y2, p);
		}
		
	}
	private void DrawLines(Canvas canvas,Paint paint,int defaultX,int defaultY,int posX,int posY,int speed)
	{
		int r = (int) Math.sqrt((double)(posX-defaultX)*(posX-defaultX)+(posY-defaultY)*(posY-defaultY)) - 50;
		int R = r+50;
		double beta;
		double m=0;
		for(float i=0;i<speed*2.65;i+=5)
		{
			m =i * Math.PI / 180;
			beta = 40* Math.PI / 180;
			float x1 = (float) (posX - r*Math.cos(beta-m));
			float y1 = (float) (posY + r*Math.sin(beta-m));
			float x2 = (float) (posX - R*Math.cos(beta-m));
			float y2 = (float) (posY + R*Math.sin(beta-m));
			canvas.drawLine(x1, y1, x2, y2, paint);  
		}
	}
	
	
	public void SetSpeedRotate(int speedrotate)
	{
		this.mSpeedRoate = speedrotate;
        this.invalidate();
	}
}
