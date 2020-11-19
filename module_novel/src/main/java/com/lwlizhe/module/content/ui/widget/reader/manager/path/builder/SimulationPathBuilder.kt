package com.lwlizhe.module.content.ui.widget.reader.manager.path.builder

import android.graphics.Path
import android.graphics.Point
import android.graphics.PointF
import android.util.Log
import com.lwlizhe.module.content.ui.widget.reader.manager.path.NovelContentPathManager
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

class SimulationPathBuilder(manager: NovelContentPathManager) : BasePathBuilder(manager) {

    var mBezierControlBottom: PointF = PointF()
    var mBezierStartBottom: PointF = PointF()
    var mBezierEndBottom: PointF = PointF()
    var mBezierVertexBottom: PointF = PointF()

    var mBezierControlRight: PointF = PointF()
    var mBezierStartRight: PointF = PointF()
    var mBezierEndRight: PointF = PointF()
    var mBezierVertexRight: PointF = PointF()

    var width = 0F
    var height = 0F

    var cornerX = 0F
    var cornerY = 0F

    var isMiddlePath = false
    var isFromTop = false


    override fun setPathArea(width: Int, height: Int) {
        this.width = width.toFloat()
        this.height = height.toFloat()
    }

    override fun onFirstTouch(touchPoint: Point) {
        cornerX = width
        calculateCorner(touchPoint)

        isMiddlePath = abs(touchPoint.y - (height / 2)) <= 100
        mTouchPoint.x = touchPoint.x

        if (isMiddlePath) {
            mTouchPoint.y = if (isFromTop) {
                1
            } else {
                (height - 1F).toInt()
            }
        } else {
            mTouchPoint.y = (height / 4 * 3).toInt()
        }
    }

    override fun buildPath(
        x: Int,
        y: Int
    ): Path? {

        mTouchPoint.x = x
        if (!isMiddlePath) {
            mTouchPoint.y = y
        }

        if (abs(width - mTouchPoint.x) < 5) {
            return null
        }

        calBezierPoint()
        val result = Path()
        result.reset()

        val baseY = if (isFromTop) 0F else height

        result.moveTo(0F, height - baseY)
        result.lineTo(cornerX, height - baseY)
        result.lineTo(mBezierStartRight.x, mBezierStartRight.y)
        result.quadTo(
            mBezierControlRight.x,
            mBezierControlRight.y,
            mBezierEndRight.x,
            mBezierEndRight.y
        )
        result.lineTo(mTouchPoint.x.toFloat(), mTouchPoint.y.toFloat())
        result.lineTo(mBezierEndBottom.x, mBezierEndBottom.y)
        result.quadTo(
            mBezierControlBottom.x,
            mBezierControlBottom.y,
            mBezierStartBottom.x,
            mBezierStartBottom.y
        )
        result.lineTo(0F, baseY)
        result.close()

        return result
    }

    /**
     * 计算贝塞尔曲线控制点、起始点
     */
    private fun calBezierPoint() {
        var mMiddleX = (mTouchPoint.x + cornerX) / 2F
        var mMiddleY = (mTouchPoint.y + cornerY) / 2F
        mBezierControlBottom.x =
            mMiddleX - (cornerY - mMiddleY) * (cornerY - mMiddleY) / (cornerX - mMiddleX)
        mBezierControlBottom.y = cornerY.toFloat()
        mBezierControlRight.x = cornerX.toFloat()
        //mBezierControlRight.y = mMiddleY - (cornerX - mMiddleX) * (cornerX - mMiddleX) / (cornerY - mMiddleY);
        mBezierControlRight.y =
            mMiddleY - (cornerX - mMiddleX) * (cornerX - mMiddleX) / if (cornerY - mMiddleY == 0f) 0.1f else cornerY - mMiddleY

        mBezierStartBottom.x = mBezierControlBottom.x - (cornerX - mBezierControlBottom.x) / 2
        mBezierStartBottom.y = cornerY.toFloat()

        // 当mBezierStartBottom.x < 0或者mBezierStartBottom.x > 480时
        // 如果继续翻页，会出现BUG故在此限制
        if (mTouchPoint.x > 0 && mTouchPoint.x < width) {
            if (mBezierStartBottom.x < 0 || mBezierStartBottom.x > width) {
                if (mBezierStartBottom.x < 0) mBezierStartBottom.x = width - mBezierStartBottom.x
                val f1 = abs(cornerX - mTouchPoint.x)
                val f2 = width * f1 / mBezierStartBottom.x
                mTouchPoint.x = (abs(cornerX - f2) + 0.5F).toInt()
                val f3 =
                    abs(cornerX - mTouchPoint.x) * abs(cornerY - mTouchPoint.y) / f1
                mTouchPoint.y = (abs(cornerY - f3) + 0.5F).toInt()
                mMiddleX = (mTouchPoint.x + cornerX) / 2
                mMiddleY = (mTouchPoint.y + cornerY) / 2
                mBezierControlBottom.x =
                    mMiddleX - (cornerY - mMiddleY) * (cornerY - mMiddleY) / (cornerX - mMiddleX)
                mBezierControlBottom.y = cornerY.toFloat()
                mBezierControlRight.x = cornerX.toFloat()
                //mBezierControlRight.y = mMiddleY - (cornerX - mMiddleX) * (cornerX - mMiddleX) / (cornerY - mMiddleY);
                val f5 = cornerY - mMiddleY
                if (f5 == 0f) {
                    mBezierControlRight.y =
                        mMiddleY - (cornerX - mMiddleX) * (cornerX - mMiddleX) / 0.1f
                } else {
                    mBezierControlRight.y =
                        mMiddleY - (cornerX - mMiddleX) * (cornerX - mMiddleX) / (cornerY - mMiddleY)
                }
                mBezierStartBottom.x =
                    mBezierControlBottom.x - (cornerX - mBezierControlBottom.x) / 2
            }
        }
        mBezierStartRight.x = cornerX.toFloat()
        mBezierStartRight.y = mBezierControlRight.y - (cornerY - mBezierControlRight.y) / 2
        val mTouchToCornerDis = hypot(
            (mTouchPoint.x - cornerX).toDouble(),
            (mTouchPoint.y - cornerY).toDouble()
        ).toFloat()

        val tempTouchPointF =
            PointF(mTouchPoint.x.toFloat(), mTouchPoint.y.toFloat())
        mBezierEndBottom =
            getCross(tempTouchPointF, mBezierControlBottom, mBezierStartBottom, mBezierStartRight)
        mBezierEndRight =
            getCross(tempTouchPointF, mBezierControlRight, mBezierStartBottom, mBezierStartRight)

        /*
         * mBezierVertexBottom.x 推导
		 * ((mBezierStartBottom.x+mBezierEndBottom.x)/2+mBezierControlBottom.x)/2 化简等价于
		 * (mBezierStartBottom.x+ 2*mBezierControlBottom.x+mBezierEndBottom.x) / 4
		 */mBezierVertexBottom.x =
            (mBezierStartBottom.x + 2 * mBezierControlBottom.x + mBezierEndBottom.x) / 4
        mBezierVertexBottom.y =
            (2 * mBezierControlBottom.y + mBezierStartBottom.y + mBezierEndBottom.y) / 4
        mBezierVertexRight.x =
            (mBezierStartRight.x + 2 * mBezierControlRight.x + mBezierEndRight.x) / 4
        mBezierVertexRight.y =
            (2 * mBezierControlRight.y + mBezierStartRight.y + mBezierEndRight.y) / 4
    }

    /**
     * 求解直线P1P2和直线P3P4的交点坐标
     *
     * @param P1
     * @param P2
     * @param P3
     * @param P4
     * @return
     */
    private fun getCross(P1: PointF, P2: PointF, P3: PointF, P4: PointF): PointF {
        val crossP = PointF()
        // 二元函数通式： y=kx+b
        val k1 = (P2.y - P1.y) / (P2.x - P1.x)
        val b1 = (P1.x * P2.y - P2.x * P1.y) / (P1.x - P2.x)
        val k2 = (P4.y - P3.y) / (P4.x - P3.x)
        val b2 = (P3.x * P4.y - P4.x * P3.y) / (P3.x - P4.x)
        crossP.x = (b2 - b1) / (k1 - k2)
        crossP.y = k1 * crossP.x + b1
        return crossP
    }

    private fun calculateCorner(touchPoint: Point) {
        isFromTop = touchPoint.y <= height / 2
        cornerY = if (isFromTop) {
            0F
        } else {
            height
        }
    }
}