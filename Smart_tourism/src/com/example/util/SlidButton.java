package com.example.util;

import com.example.demo01.R;

import android.content.Context;  
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.graphics.Canvas;  
import android.graphics.Matrix;  
import android.graphics.Paint;  
import android.graphics.Rect;  
import android.util.AttributeSet;  
import android.view.MotionEvent;  
import android.view.View;  
import android.view.View.OnTouchListener;  
  
public class SlidButton extends View implements OnTouchListener {  
  
    private boolean nowChoose = false;// ��¼��ǰ��ť�Ƿ�򿪣�trueΪ�򿪣�falseΪ�ر�  
    private boolean onSlip = false;// ��¼�û��Ƿ��ڻ���  
    private float downX, nowX; // ����ʱ��x����ǰ��x  
    private Rect btn_on, btn_off;// �򿪺͹ر�״̬�£��α��Rect  
  
    private boolean isChgLsnOn = false;//�Ƿ����ü���  
    private OnChangedListener changedLis;  
    
    int begin,end;//�ؼ���ǰ��λ��
  
    private Bitmap bg_on, bg_off, slip_btn;  
  
    public SlidButton(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        init();  
    }  
  
    public SlidButton(Context context) {  
        super(context);  
        init();  
    }  
  
      
    private void init() {  
        // ����ͼƬ��Դ  
        bg_on = BitmapFactory.decodeResource(getResources(),  
                R.drawable.sild_bg_on);  
        bg_off = BitmapFactory.decodeResource(getResources(),  
                R.drawable.sild_bg_off);  
        slip_btn = BitmapFactory.decodeResource(getResources(),  
                R.drawable.sild_bg_btn);  
        // �����Ҫ��Rect����  
        btn_on = new Rect(0, 0, slip_btn.getWidth(), slip_btn.getHeight());  
        btn_off = new Rect(bg_off.getWidth() - slip_btn.getWidth(), 0,  
                bg_off.getWidth(), slip_btn.getHeight());  
        setOnTouchListener(this);  
    }  
      
    @Override  
    protected void onDraw(Canvas canvas) {  
        // TODO Auto-generated method stub  
        super.onDraw(canvas);  
          
        Matrix matrix = new Matrix(); 
        begin			=this.getWidth()-bg_off.getWidth();
        end				=9;
        matrix.postTranslate(begin, end);
        Paint paint = new Paint();  
        float x;  
       
        
        {  
//            if (nowX<(bg_on.getWidth()/2)) //������ǰ�������εı�����ͬ,�ڴ����ж�  
        	if(!nowChoose)
                canvas.drawBitmap(bg_off, matrix, paint);//�����ر�ʱ�ı���  
            else  
                canvas.drawBitmap(bg_on, matrix, paint);//������ʱ�ı���   
                  
            if (onSlip) {//�Ƿ����ڻ���״̬,    
                if(nowX >= bg_on.getWidth())//�Ƿ񻮳�ָ����Χ,�������α��ܵ���ͷ,����������ж�  
                    x = bg_on.getWidth() - slip_btn.getWidth()/2;//��ȥ�α�1/2�ĳ���  
                else  
                    x = nowX - slip_btn.getWidth()/2;  
            }else {  
                if(nowChoose)//�������ڵĿ���״̬���û��α��λ��   
                    x = btn_off.left;  
                else  
                    x = btn_on.left;  
            }  
              
            if (x < 0 ) //���α�λ�ý����쳣�ж�..  
                x = 0;  
            else if(x > bg_on.getWidth() - slip_btn.getWidth())  
                x = bg_on.getWidth() - slip_btn.getWidth();  
              
            canvas.drawBitmap(slip_btn, x+begin,end, paint);//�����α�.     
        }  
    }  
  
    
    @Override  
    public boolean onTouch(View v, MotionEvent event) {  
          
        switch (event.getAction()) {//���ݶ�����ִ�д���  
              
            case MotionEvent.ACTION_MOVE://����  
                nowX = event.getX();  
                break;  
            case MotionEvent.ACTION_DOWN://����  
                if (event.getX() < begin)   
                    return false;  
                onSlip = true;  
                downX = event.getX();  
                nowX = downX;  
                break;  
            case MotionEvent.ACTION_UP://�ɿ�  
                onSlip = false;  
                boolean lastChoose = nowChoose;  
                /*if (event.getX() >= begin+(bg_on.getWidth()/2))   
                    nowChoose = true;  
                else   
                    nowChoose = false; */ 
                nowChoose		=!nowChoose;
                if(isChgLsnOn && (lastChoose != nowChoose))//��������˼�����,�͵����䷽��.  
                    changedLis.OnChanged(nowChoose);  
                break;  
            default:  
                break;  
        }  
        invalidate();  
        return true;  
    }  
  
      
    public void SetOnChangedListener(OnChangedListener l){//���ü�����,��״̬�޸ĵ�ʱ��  
        isChgLsnOn = true;  
        changedLis = l;  
    }  
      
    public interface OnChangedListener {  
        abstract void OnChanged(boolean checkState);  
    }  
}  