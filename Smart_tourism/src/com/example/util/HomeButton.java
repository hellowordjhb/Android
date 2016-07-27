package com.example.util;

import com.example.demo01.BitmapUtils;
import com.example.demo01.R;
import com.example.myinterface.HomeClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class HomeButton extends ImageView {

	private Bitmap bitmap;
	private Bitmap home_flight;
	private Bitmap label_new;
	private int state = 0; // ����

	private int color;
	private float textsize;
	private boolean big;
	private int home;
	private String text;

	private int screenW;
	private int screenH;

	// ����¼�
	private HomeClickListener listener = null;

	private int[] colors = { getResources().getColor(R.color.red),
			getResources().getColor(R.color.orange),
			getResources().getColor(R.color.blue),
			getResources().getColor(R.color.purple),
			getResources().getColor(R.color.air),
			getResources().getColor(R.color.texi),
			getResources().getColor(R.color.jingdian),
			getResources().getColor(R.color.green) };

	private Bitmap[] bitmaps = {
			BitmapFactory.decodeResource(getResources(), R.drawable.image1),
			BitmapFactory.decodeResource(getResources(), R.drawable.image2),
			BitmapFactory.decodeResource(getResources(), R.drawable.image3),
			BitmapFactory.decodeResource(getResources(), R.drawable.image4),
			BitmapFactory.decodeResource(getResources(), R.drawable.image5),
			BitmapFactory.decodeResource(getResources(), R.drawable.image6),
			BitmapFactory.decodeResource(getResources(), R.drawable.image7),
			BitmapFactory.decodeResource(getResources(), R.drawable.image8),

	};

	public HomeButton(Context context) {
		super(context);
	}

	public HomeButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		bitmap = BitmapUtils.zoomImage(BitmapFactory.decodeResource(
				getResources(), R.drawable.fingerprint), 127, 122);

		label_new = BitmapFactory.decodeResource(getResources(),
				R.drawable.label_new);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.HomeButton);
		color = typedArray.getInt(R.styleable.HomeButton_backcolor, 0);
		textsize = typedArray.getDimension(R.styleable.HomeButton_textSize, 24);
		big = typedArray.getBoolean(R.styleable.HomeButton_big, true);
		home = typedArray.getInt(R.styleable.HomeButton_home, 0);
		text = typedArray.getString(R.styleable.HomeButton_text);
		System.out.println("color:" + color + " textsize:" + textsize + " big:"
				+ big + " home:" + home);
		home_flight = bitmaps[home];
		// ��ȡ��Ļ�Ĵ�С
		screenW = ((Activity) context).getWindow().getWindowManager()
				.getDefaultDisplay().getWidth() / 2 - 16;
		if (big) {
			screenH = screenW;
		} else {
			screenH = screenW / 2 - 4;
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// ����������Ļ��С
		setMeasuredDimension(screenW, screenH);
	}

	// ��������С��Ҫ��cnavas�Ĵ�С�����㣬�������Ϊ�����ģ�����еõ��Ĵ�С��һ��������Ϊʲô��������û���ҵ�ԭ��
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawColor(colors[color]);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(24);
		if (big) {
			Matrix matrix = new Matrix();
			matrix.postTranslate(this.getWidth() / 2 - home_flight.getWidth()
					/ 2, this.getHeight() / 2 - home_flight.getHeight() / 2);
			canvas.drawText(text, 10, 40, paint);
			canvas.drawBitmap(home_flight, matrix, paint);
		} else {
			Matrix matrix_small = new Matrix();
			matrix_small.postTranslate(10,
					this.getHeight() / 2 - home_flight.getHeight() / 2);
			canvas.drawBitmap(home_flight, matrix_small, new Paint());
			// if(home==3){
			// paint.setTextSize(16);
			// canvas.drawText("", home_flight.getWidth()+20, this.getHeight() /
			// 2-home_flight.getHeight()/2+10 , paint);
			// canvas.drawText("", home_flight.getWidth()+20, this.getHeight() /
			// 2+home_flight.getHeight()/2, paint);
			// }else if(home==5){
			// paint.setTextSize(16);
			// canvas.drawText("", home_flight.getWidth()+20, this.getHeight() /
			// 2-home_flight.getHeight()/2+10 , paint);
			// canvas.drawText("", home_flight.getWidth()+20, this.getHeight() /
			// 2+home_flight.getHeight()/2, paint);
			// }else{
			canvas.drawText(text, home_flight.getWidth() + 20, this.getHeight()
					/ 2 + home_flight.getHeight() / 2, paint);
			// }
			// if(home==6){
			// Matrix matrix_new =new Matrix();
			// matrix_new.postTranslate(screenW-label_new.getWidth(), 0);
			// canvas.drawBitmap(label_new, matrix_new, new Paint());
			// }

		}
		if (state == 1) {
			Matrix matrix2 = new Matrix();
			matrix2.postTranslate(this.getWidth() / 2 - bitmap.getWidth() / 2,
					this.getHeight() / 2 - bitmap.getHeight() / 2);
			canvas.drawBitmap(bitmap, matrix2, new Paint());
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float start = 1.0f;
		float end = 0.95f;
		Animation scaleAnimation = new ScaleAnimation(start, end, start, end,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		Animation endAnimation = new ScaleAnimation(end, start, end, start,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAnimation.setDuration(200);
		scaleAnimation.setFillAfter(true);
		endAnimation.setDuration(200);
		endAnimation.setFillAfter(true);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.startAnimation(scaleAnimation);
			state = 1;
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			this.startAnimation(endAnimation);
			state = 0;
			invalidate();
			if (listener != null) {
				listener.onclick();
			}
			break;
		// ������ȥ�������action_up,����action_cancel
		case MotionEvent.ACTION_CANCEL:
			this.startAnimation(endAnimation);
			state = 0;
			invalidate();
			break;
		}
		// ������true��Action_up����Ӧ����
		return true;
	}

	/**
	 * ������Ӧ�¼�
	 * 
	 * @param clickListener
	 */
	public void setOnHomeClick(HomeClickListener clickListener) {
		this.listener = clickListener;
	}

}
