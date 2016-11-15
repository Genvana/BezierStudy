package com.genvana.study.bezierdemo.view;

/**
 * Created by Genvana on 2016/11/15.
 */

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 波浪图形
 * <p/>
 * Created by xuyisheng on 16/7/11.
 */
public class WaveBezier extends View implements View.OnClickListener {

    private Paint mPaint;
    private Path mPath;
    private int mWaveLength = 1000;
    private int mOffset;
    private int mScreenHeight;
    private int mScreenWidth;
    private int mWaveCount;
    private int mCenterY;

    public WaveBezier(Context context) {
        super(context);
    }

    public WaveBezier(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        setOnClickListener(this);
    }

    public WaveBezier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 第一次控件显示的时候会调用一次
     * 第一次调用oldw和oldh为0
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenHeight = h;
        mScreenWidth = w;
        //Math.round(double d)返回最接近参数的 long。
        //Math.round(float f)返回最接近参数的 int .
        //遵循舍入原则,等同于(int)Math.floor(a + 0.5f)
        mWaveCount = (int) Math.round(mScreenWidth / mWaveLength + 1.5);//结果为大于1.5的整数
        mCenterY = mScreenHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*
        Path使用方法
        1.初始化
        2.reset()
        3.moveTo起始点
        4.lineTo或者quadTo或者cubicTo
        5.Path.close()
        6.canvas.drawPath
         */
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(-mWaveLength + mOffset, mCenterY);//Path起点在屏幕外
        for (int i = 0; i < mWaveCount; i++) {
            // + (i * mWaveLength)
            // + mOffset
            mPath.quadTo((-mWaveLength * 3 / 4) + (i * mWaveLength)
                    + mOffset, mCenterY + 60, (-mWaveLength / 2) +
                    (i * mWaveLength) + mOffset, mCenterY);//波浪上半圆

            mPath.quadTo((-mWaveLength / 4) + (i * mWaveLength)
                    + mOffset, mCenterY - 60, i * mWaveLength + mOffset, mCenterY);//波浪下半圆
        }
        //填充波浪底部
        mPath.lineTo(mScreenWidth, mScreenHeight);
        mPath.lineTo(0, mScreenHeight);

        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void onClick(View view) {
        ValueAnimator animator = ValueAnimator.ofInt(0, mWaveLength);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);//无限重复动画
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
