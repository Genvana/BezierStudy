package com.genvana.study.bezierdemo.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Genvana on 2016/11/15..
 * 利用贝塞尔曲线实现小球运动轨迹设定
 * 从屏幕右边到屏幕底部中间
 * 再到屏幕右边
 */

public class PathBezierView extends View implements View.OnClickListener{
    private float mStartX;
    private float mStartY;
    private float mBallX;
    private float mBallY;
    private float mEndX;
    private float mEndY;
    private float mFallPointX;
    private float mFallPointY;
    private float mCtrlOneX;
    private float mCtrlOneY;
    private float mCtrlTwoX;
    private float mCtrlTwoY;
    private Paint mPaint;//用来绘制小球

    public PathBezierView(Context context) {
        super(context);
    }

    public PathBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setARGB(255,25,55,143);
        setOnClickListener(this);
    }

    public PathBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mStartX = w;
        mStartY = h/4;
        mBallX = mStartX;
        mBallY = mStartY;
        mEndX = 0;
        mEndY = h/2;
        mFallPointX = w/2;
        mFallPointY = h;
        mCtrlOneX = w/2;
        mCtrlOneY = h*3/8;
        mCtrlTwoX = w/2;
        mCtrlTwoY = h*4/5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mBallX,mBallY,80,mPaint);
    }

    @Override
    public void onClick(View v) {
        PointF StartPoint = new PointF(mStartX,mStartY);
        PointF EndPoint = new PointF(mEndX,mEndY);
        PointF FallPoint = new PointF(mFallPointX,mFallPointY);
        PointF CtrlOnePoint = new PointF(mCtrlOneX,mCtrlOneY);
        PointF CtrlTwoPoint = new PointF(mCtrlTwoX,mCtrlTwoY);
        //第一段动作
        BerizeEvaluator firstEvaluator = new BerizeEvaluator(CtrlOnePoint);
        ValueAnimator firstAnimator = ValueAnimator.ofObject(firstEvaluator,StartPoint,FallPoint);
        firstAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF p = (PointF) animation.getAnimatedValue();
                mBallX = p.x;
                mBallY = p.y;
                postInvalidate();
            }
        });
        firstAnimator.setDuration(500);
        firstAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        firstAnimator.start();
        //第二段动作
        BerizeEvaluator secondEvaluator = new BerizeEvaluator(CtrlTwoPoint);
        ValueAnimator secondAnimator = ValueAnimator.ofObject(secondEvaluator,FallPoint,EndPoint);
        secondAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF p = (PointF) animation.getAnimatedValue();
                mBallX = p.x;
                mBallY = p.y;
                postInvalidate();
            }
        });
//        secondAnimator.setStartDelay(1000);
        secondAnimator.setDuration(500);
        secondAnimator.setInterpolator(new DecelerateInterpolator());
//        secondAnimator.start();
//        //动画集
        AnimatorSet set = new AnimatorSet();
        set.play(firstAnimator).before(secondAnimator);
        set.start();


    }
}
