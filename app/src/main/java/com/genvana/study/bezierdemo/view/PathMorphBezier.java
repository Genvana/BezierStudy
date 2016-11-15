package com.genvana.study.bezierdemo.view;

/**
 * Created by Genvana on 2016/11/14.
 */

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * 曲线变形
 * <p/>
 * Created by xuyisheng on 16/7/11.
 */
public class PathMorphBezier extends View implements View.OnClickListener{

    private Paint mPaintBezier;
    private Paint mPaintAuxiliary;
    private Paint mPaintAuxiliaryText;

    private float mAuxiliaryOneX;
    private float mAuxiliaryOneY;
    private float mAuxiliaryTwoX;
    private float mAuxiliaryTwoY;

    private float mStartPointX;
    private float mStartPointY;

    private float mEndPointX;
    private float mEndPointY;

    private Path mPath = new Path();
    private ValueAnimator mAnimator;

    public PathMorphBezier(Context context) {
        super(context);
    }

    /**
     * 初始化画笔,并为控件上设置监听器
     * @param context
     * @param attrs
     */
    public PathMorphBezier(Context context, AttributeSet attrs) {
        super(context, attrs);
        //贝塞尔画笔初始化
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿画笔
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setStrokeWidth(8);
        //辅助线画笔初始化
        mPaintAuxiliary = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliary.setStyle(Paint.Style.STROKE);
        mPaintAuxiliary.setStrokeWidth(2);
        //辅助文本初始化
        mPaintAuxiliaryText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliaryText.setStyle(Paint.Style.STROKE);
        mPaintAuxiliaryText.setTextSize(40);

        setOnClickListener(this);
    }

    public PathMorphBezier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;

        mEndPointX = w / 4 * 3;
        mEndPointY = h / 2 - 200;

        mAuxiliaryOneX = mStartPointX;
        mAuxiliaryOneY = mStartPointY;
        mAuxiliaryTwoX = mEndPointX;
        mAuxiliaryTwoY = mEndPointY;

        mAnimator = ValueAnimator.ofFloat(mStartPointY, (float) h);
        //用来修饰动画效果，定义动画的变化率,默认为加速
//   (默认)AccelerateDecelerateInterpolator 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
//        AccelerateInterpolator  在动画开始的地方速率改变比较慢，然后开始加速
//        AnticipateInterpolator 开始的时候向后然后向前甩
//        AnticipateOvershootInterpolator 开始的时候向后然后向前甩一定值后返回最后的值
//        BounceInterpolator   动画结束的时候弹起
//        CycleInterpolator 动画循环播放特定的次数，速率改变沿着正弦曲线
//        DecelerateInterpolator 在动画开始的地方快然后慢
//        LinearInterpolator   以常量速率改变
//        OvershootInterpolator    向前甩一定值后再回到原来位置
        mAnimator.setInterpolator(new BounceInterpolator());
        mAnimator.setDuration(1000);
        //为ValueAnimator添加AnimatorUpdateListener
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAuxiliaryOneY = (float) valueAnimator.getAnimatedValue();
                mAuxiliaryTwoY = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);
        // 辅助点
//        canvas.drawPoint(mAuxiliaryOneX, mAuxiliaryOneY, mPaintAuxiliary);
        canvas.drawText("辅助点1", mAuxiliaryOneX, mAuxiliaryOneY, mPaintAuxiliaryText);
        canvas.drawText("辅助点2", mAuxiliaryTwoX, mAuxiliaryTwoY, mPaintAuxiliaryText);
        canvas.drawText("起始点", mStartPointX, mStartPointY, mPaintAuxiliaryText);
        canvas.drawText("终止点", mEndPointX, mEndPointY, mPaintAuxiliaryText);
        // 辅助线
        canvas.drawLine(mStartPointX, mStartPointY, mAuxiliaryOneX, mAuxiliaryOneY, mPaintAuxiliary);
        canvas.drawLine(mEndPointX, mEndPointY, mAuxiliaryTwoX, mAuxiliaryTwoY, mPaintAuxiliary);
        canvas.drawLine(mAuxiliaryOneX, mAuxiliaryOneY, mAuxiliaryTwoX, mAuxiliaryTwoY, mPaintAuxiliary);
        // 三阶贝塞尔曲线
        mPath.cubicTo(mAuxiliaryOneX, mAuxiliaryOneY, mAuxiliaryTwoX, mAuxiliaryTwoY, mEndPointX, mEndPointY);
        //Draw the specified path using the specified paint. The path will be
        //filled or framed based on the Style in the paint.
        //就是画一个path-.-
        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public void onClick(View view) {
        mAnimator.start();
    }
}
