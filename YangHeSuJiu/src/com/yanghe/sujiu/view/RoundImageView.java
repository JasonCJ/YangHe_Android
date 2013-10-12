package com.yanghe.sujiu.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundImageView extends ImageView {

	private float xRadius;
	private float yRadius;

	public RoundImageView(Context context) {
		super(context);
	}

	public float getxRadius() {
		return xRadius;
	}

	public void setxRadius(float xRadius) {
		this.xRadius = xRadius;
	}

	public float getyRadius() {
		return yRadius;
	}

	public void setyRadius(float yRadius) {
		this.yRadius = yRadius;
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		xRadius = 10;
//		yRadius = 10;
		
		xRadius = 250;
		yRadius = 250;
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@SuppressLint({ "NewApi", "DrawAllocation" })	
	protected void onDraw(Canvas canvas) {
		Drawable srcDrawable = getDrawable();
		Drawable bgDrawable = getBackground();
		BitmapDrawable bitmapDrawable = null;
		Bitmap bitmap = null;
		if(null != srcDrawable){
			bitmapDrawable = (BitmapDrawable)srcDrawable;
			bitmap = bitmapDrawable.getBitmap();
		}else if(null != bgDrawable){
			bitmapDrawable = (BitmapDrawable)bgDrawable;
			bitmap = bitmapDrawable.getBitmap();
		}else{
			super.onDraw(canvas);
			return;
		}
		BitmapShader shader;
		shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		// 设置映射否则图片显示不全
		RectF rect = new RectF(0.0f, 0.0f, getWidth(), getHeight());
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		RectF src = new RectF(0.0f, 0.0f, width, height);
		Matrix matrix = new Matrix();
		matrix.setRectToRect(src, rect, Matrix.ScaleToFit.CENTER);
		shader.setLocalMatrix(matrix);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(shader);
		canvas.drawRoundRect(rect, xRadius, yRadius, paint);
	}

}