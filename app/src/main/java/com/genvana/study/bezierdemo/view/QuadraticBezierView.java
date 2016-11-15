package com.genvana.study.bezierdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Genvana on 2016/11/14.
 */

public class QuadraticBezierView extends View {
    private Paint mPaintBezier;
    private Paint mPaintAuxiliary;
    private Paint mPaintAuxiliaryText;

    private float mAuxiliaryX;
    private float mAuxiliaryY;

    private float mStartPointX;
    private float mStartPointY;

    private float mEndPointX;
    private float mEndPointY;

    private Path mPath = new Path();

    /**
     * 在代码中初始化调用该构造方法
     * @param context
     */
    public QuadraticBezierView(Context context) {
        super(context);
    }

    /**
     * 通过布局文件声明的控件初始化时调用该构造方法
     * @param context
     * @param attrs
     */
    public QuadraticBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setStrokeWidth(8);

        mPaintAuxiliary = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliary.setStyle(Paint.Style.STROKE);
        mPaintAuxiliary.setStrokeWidth(2);

        mPaintAuxiliaryText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliaryText.setStyle(Paint.Style.STROKE);
        mPaintAuxiliaryText.setTextSize(20);
    }

    /**
     * 有style xxxx 的(不太清楚)
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public QuadraticBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * This is called during layout when the size of this view has changed. If
     * you were just added to the view hierarchy, you're called with the old
     * values of 0.
     *
     * @param w Current width of this view.
     * @param h Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;

        mEndPointX = w / 4 * 3;
        mEndPointY = h / 2 - 200;
    }

    /**
     * 通过起止点与控制点来绘制bezier曲线方法
     * android系统提供Path类
     * 类中lineTo,quadTo,以及cubicTo方法来绘制一阶二阶三阶贝bezier曲线
     * 高阶的bezier曲线可以降阶为低阶bezier曲线来绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Clear any lines and curves from the path, making it empty.
        //This does NOT change the fill-type setting.
        mPath.reset();

        //Set the beginning of the next contour to the point (x,y).
        mPath.moveTo(mStartPointX, mStartPointY);
        // 辅助点
        canvas.drawPoint(mAuxiliaryX, mAuxiliaryY, mPaintAuxiliary);
        canvas.drawText("控制点", mAuxiliaryX, mAuxiliaryY, mPaintAuxiliaryText);
        canvas.drawText("起始点", mStartPointX, mStartPointY, mPaintAuxiliaryText);
        canvas.drawText("终止点", mEndPointX, mEndPointY, mPaintAuxiliaryText);
        // 辅助线
        canvas.drawLine(mStartPointX, mStartPointY, mAuxiliaryX, mAuxiliaryY, mPaintAuxiliary);
        canvas.drawLine(mEndPointX, mEndPointY, mAuxiliaryX, mAuxiliaryY, mPaintAuxiliary);
        // 二阶贝塞尔曲线
        mPath.quadTo(mAuxiliaryX, mAuxiliaryY, mEndPointX, mEndPointY);
        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mAuxiliaryX = event.getX();
                mAuxiliaryY = event.getY();
                invalidate();
        }
        return true;
    }
}

