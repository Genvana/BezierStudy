package com.genvana.study.bezierdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by Genvana on 2016/11/14.
 */

public class CubicBezierView extends View {
    private float mStartX;
    private float mStartY;
    private float mEndX;
    private float mEndY;
    private float mCtrlOneX;
    private float mCtrlOneY;
    private float mCtrlTwoX;
    private float mCtrlTwoY;
    private int pointCount ;//判断设置了几个控制点,若没有则为0,有一个为1,两个为2
    private Paint mPaint;
    private Path mPath;
    public CubicBezierView(Context context) {
        super(context);
        mPaint = new Paint(ANTI_ALIAS_FLAG);
        mPaint.setARGB(255,253,25,3);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
    }

    public CubicBezierView(Context context, AttributeSet attrs) {

        super(context, attrs);
        mPaint = new Paint(ANTI_ALIAS_FLAG);
        mPaint.setARGB(255,253,25,3);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();



    }


    public CubicBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mStartX = w/4;
        mStartY = h/2 -200;
        mEndX = w/4*3;
        mEndY = h/2 -200;
        mCtrlOneX = mStartX;
        mCtrlOneY = mStartY;
        mCtrlTwoX = mEndX;
        mCtrlTwoY = mEndY;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartX,mStartY);
        switch (pointCount){
            case 0:
                canvas.drawLine(mStartX,mStartY,mEndX,mEndY,mPaint);
                break;
            case 1:
                mPath.quadTo(mCtrlOneX,mCtrlOneY,mEndX,mEndY);
                canvas.drawLine(mStartX,mStartY,mCtrlOneX,mCtrlOneY,mPaint);
                canvas.drawLine(mCtrlOneX,mCtrlOneY,mEndX,mEndY,mPaint);
                canvas.drawPath(mPath,mPaint);
                break;
            case 2:
                mPath.cubicTo(mCtrlOneX,mCtrlOneY,mCtrlTwoX,mCtrlTwoY,mEndX,mEndY);
                canvas.drawLine(mStartX,mStartY,mCtrlOneX,mCtrlOneY,mPaint);
                canvas.drawLine(mCtrlOneX,mCtrlOneY,mCtrlTwoX,mCtrlTwoY,mPaint);
                canvas.drawLine(mCtrlTwoX,mCtrlTwoY,mEndX,mEndY,mPaint);
                canvas.drawPath(mPath,mPaint);
                break;
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (pointCount){
            case 0:
                mCtrlOneX = event.getX();
                mCtrlOneY = event.getY();
                ++pointCount;
                invalidate();
                break;
            case 1:
                mCtrlTwoX = event.getX();
                mCtrlTwoY = event.getY();
                ++pointCount;
                invalidate();
                break;
            case 2:
                pointCount = 0;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }
}
