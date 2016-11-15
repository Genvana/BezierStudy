package com.genvana.study.bezierdemo.view;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import com.genvana.study.bezierdemo.utils.BezierUtil;

/**
 * Created by Genvana on 2016/11/15.
 */

public class BerizeEvaluator implements TypeEvaluator<PointF> {
    private PointF mCtrlPoint;

    public BerizeEvaluator(PointF mCtrlPoint) {
        this.mCtrlPoint = mCtrlPoint;
    }

    /**
     *
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return BezierUtil.calcPointFormQuad(mCtrlPoint,fraction,startValue,endValue);
    }
}
