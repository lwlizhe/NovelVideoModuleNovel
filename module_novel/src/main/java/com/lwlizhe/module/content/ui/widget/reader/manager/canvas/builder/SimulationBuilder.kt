package com.lwlizhe.module.content.ui.widget.reader.manager.canvas.builder

import android.graphics.*
import android.util.Log
import com.lwlizhe.module.content.ui.widget.reader.manager.canvas.NovelContentCanvasManager
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot

class SimulationBuilder(manager: NovelContentCanvasManager) : BaseBuilder(manager) {

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

    var paint = Paint()


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

    override fun onDetached() {
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

    override fun buildCanvas(baseCanvas: Canvas, copyCanvas: Canvas, copyBitmap: Bitmap) {
        test(baseCanvas, copyCanvas, copyBitmap)
//        drawBackCurlyArea(baseCanvas, copyCanvas, copyBitmap)
//        drawContent(baseCanvas, copyCanvas, copyBitmap)
    }

    /**
     * 画主要的内容页
     */
    private fun drawContent(baseCanvas: Canvas, copyCanvas: Canvas, copyBitmap: Bitmap) {
        val contentPath = Path()
        contentPath.reset()

        val baseY = if (isFromTop) 0F else height

        contentPath.moveTo(0F, height - baseY)
        contentPath.lineTo(cornerX, height - baseY)
        contentPath.lineTo(mBezierStartRight.x, mBezierStartRight.y)
        contentPath.quadTo(
            mBezierControlRight.x,
            mBezierControlRight.y,
            mBezierEndRight.x,
            mBezierEndRight.y
        )
        contentPath.lineTo(mTouchPoint.x.toFloat(), mTouchPoint.y.toFloat())
        contentPath.lineTo(mBezierEndBottom.x, mBezierEndBottom.y)
        contentPath.quadTo(
            mBezierControlBottom.x,
            mBezierControlBottom.y,
            mBezierStartBottom.x,
            mBezierStartBottom.y
        )
        contentPath.lineTo(0F, baseY)
        contentPath.close()

        baseCanvas.save()
        baseCanvas.clipPath(contentPath)
        baseCanvas.drawColor(Color.parseColor("#FFB6C1"))
//        baseCanvas.drawBitmap(copyBitmap, 0F, 0F, paint)
        baseCanvas.restore()
    }

    /**
     * 画卷曲翻过来的部分
     */
    private fun drawBackCurlyArea(baseCanvas: Canvas, copyCanvas: Canvas, copyBitmap: Bitmap) {

        val backAreaPath = Path()
        backAreaPath.reset()

        backAreaPath.moveTo(mTouchPoint.x.toFloat(),mTouchPoint.y.toFloat())
        backAreaPath.lineTo(mBezierVertexBottom.x,mBezierVertexBottom.y)
        backAreaPath.lineTo(mBezierVertexRight.x,mBezierVertexRight.y)
        backAreaPath.close()

        val backAreaContentPath=Path()
        backAreaContentPath.moveTo(mBezierControlRight.x,mBezierControlRight.y)
        backAreaContentPath.lineTo(cornerX,cornerY)
        backAreaContentPath.lineTo(mBezierControlBottom.x,mBezierControlBottom.y)

        baseCanvas.save()
        baseCanvas.scale(-1F,1F,(mBezierControlRight.x + mBezierControlBottom.x) / 2f,(mBezierControlRight.y + mBezierControlBottom.y) / 2f)
        val degress = Math.toDegrees(
            atan2(
                (mBezierControlRight.y - mBezierControlBottom.y).toDouble(),
                (mBezierControlRight.x - mBezierControlBottom.x).toDouble()
            )
        )
        Log.d("back","$degress")
        baseCanvas.rotate((-degress).toFloat(),mBezierControlBottom.x,mBezierControlBottom.y)

//        baseCanvas.translate(
//            (mBezierControlRight.x + mBezierControlBottom.x) / 2f,
//            (mBezierControlRight.y + mBezierControlBottom.y) / 2f
//        )
//        baseCanvas.scale(-1f, 1f)
//        baseCanvas.translate(
//            (mBezierControlRight.x - mBezierControlBottom.x) / 2.toFloat(),
//            (mBezierControlRight.y - mBezierControlBottom.y) / 2.toFloat()
//        )
//        val degress = Math.toDegrees(
//            atan2(
//                (mBezierControlRight.y - mBezierControlBottom.y).toDouble(),
//                (mBezierControlRight.x - mBezierControlBottom.x).toDouble()
//            )
//        ).toFloat()
//        baseCanvas.rotate(-degress)
//        baseCanvas.translate(-(mBezierControlBottom.x), -mBezierControlBottom.y)

        baseCanvas.clipPath(backAreaContentPath)
        baseCanvas.drawColor(Color.parseColor("#90EE90"))
//        baseCanvas.drawBitmap(copyBitmap, 0f, 0f, paint)

        baseCanvas.restore()

    }

    private fun test(baseCanvas: Canvas, copyCanvas: Canvas, copyBitmap: Bitmap){
        val point1 = Point(cornerX.toInt(), cornerY.toInt())

       var x1 = mBezierControlBottom.x
       var y1 = mBezierControlRight.y

        var testPath=Path()

        testPath.reset()
        testPath.moveTo(point1.x.toFloat(), point1.y - y1.toFloat())
        testPath.lineTo(point1.x.toFloat(), point1.y.toFloat())
        testPath.lineTo(point1.x - x1.toFloat(), point1.y.toFloat())
        testPath.close()

        baseCanvas.save()

        baseCanvas.translate(point1.x - x1 / 2f, point1.y - y1 / 2f)
        baseCanvas.scale(-1f, 1f)
        baseCanvas.translate(x1 / 2.toFloat(), y1 / 2.toFloat())
        val degress = Math.toDegrees(
            Math.atan2(
                y1.toDouble(),
                x1.toDouble()
            )
        ).toFloat()
        baseCanvas.rotate(-degress)
        baseCanvas.translate(-(point1.x - x1).toFloat(), -point1.y.toFloat())

//        mPaint.setColorFilter(mColorMatrixFilter)
        baseCanvas.clipPath(testPath)
//        baseCanvas.drawColor(Color.parseColor("#90EE90"))
//        baseCanvas.drawBitmap(copyBitmap, 0f, 0f, paint)

        baseCanvas.restore()

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