package com.genvana.study.bezierdemo.utils;

import android.graphics.PointF;

/**
 * Created by Genvana on 2016/11/15.
 */

public class BezierUtil {
    /**
     * 二阶贝塞尔曲线求点公式
     * B(t) = (1 - t)^2 * P0 + 2t * (1 - t) * P1 + t^2 * P2, t ∈ [0,1]
     *
     * @param fraction  曲线长度比例 t
     * @param startValue 起始点 po
     * @param mCtrlPoint 控制点 p1
     * @param endValue 终止点 p2
     * @return PointF 对应的点
     */
    public static PointF calcPointFormQuad(PointF mCtrlPoint, float fraction, PointF startValue, PointF endValue) {
        float currentPointX = (1-fraction)*(1-fraction)* startValue.x+2*fraction*(1-fraction)*mCtrlPoint.x+fraction*fraction*endValue.x;
        float currentPointY = (1-fraction)*(1-fraction)* startValue.y+2*fraction*(1-fraction)*mCtrlPoint.y+fraction*fraction*endValue.y;
        return new PointF(currentPointX,currentPointY);
    }
}
