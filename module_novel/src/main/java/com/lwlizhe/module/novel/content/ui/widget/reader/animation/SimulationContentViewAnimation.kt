//package com.lwlizhe.module.novel.content.ui.widget.simulation
//
//import android.graphics.*
//import android.graphics.drawable.GradientDrawable
//import android.os.Build
//import android.view.MotionEvent
//import android.view.View
//import android.view.ViewConfiguration
//import java.util.*
//import kotlin.math.atan2
//import kotlin.math.hypot
//
//class TurnAnimationPageBitmapLoader(targetView: View) :
//    BaseContentViewAnimation(targetView) {
//
//
//
//
//    private val mCurPagePath: Path
//    private val mNextPagePath: Path
//    private var mCornerX = 1 // 拖拽点对应的页脚
//    private var mCornerY = 1
//    var mIsRTandLB // 是否属于右上左下
//            = false
//    var mBezierStart1 = PointF() // 贝塞尔曲线起始点
//    var mBezierControl1 = PointF() // 贝塞尔曲线控制点
//    var mBeziervertex1 = PointF() // 贝塞尔曲线顶点
//    var mBezierEnd1 = PointF() // 贝塞尔曲线结束点
//    var mBezierStart2 = PointF() // 另一条贝塞尔曲线
//    var mBezierControl2 = PointF()
//    var mBeziervertex2 = PointF()
//    var mBezierEnd2 = PointF()
//    var mBackShadowDrawableLR // 有阴影的GradientDrawable
//            : GradientDrawable? = null
//    var mBackShadowDrawableRL: GradientDrawable? = null
//    var mFolderShadowDrawableLR: GradientDrawable? = null
//    var mFolderShadowDrawableRL: GradientDrawable? = null
//    var mFrontShadowDrawableHBT: GradientDrawable? = null
//    var mFrontShadowDrawableHTB: GradientDrawable? = null
//    var mFrontShadowDrawableVLR: GradientDrawable? = null
//    var mFrontShadowDrawableVRL: GradientDrawable? = null
//    lateinit var mBackShadowColors // 背面颜色组
//            : IntArray
//    lateinit var mFrontShadowColors // 前面颜色组
//            : IntArray
//    private val mMaxLength: Float
//    var mMiddleX = 0f
//    var mMiddleY = 0f
//    var mDegrees = 0f
//    var mTouchToCornerDis = 0f
//    private val mPaint: Paint
//    var mColorMatrixFilter: ColorMatrixColorFilter
//    var mMatrix: Matrix
//    var mMatrixArray = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 1.0f)
//    private var isTouchMoving = false
//    override fun calTouchPoint() {
//        calBezierPoint()
//    }
//
//    /**
//     * 计算贝塞尔曲线控制点、起始点
//     */
//    private fun calBezierPoint() {
//        mMiddleX = (mTouch.x + mCornerX) / 2
//        mMiddleY = (mTouch.y + mCornerY) / 2
//        mBezierControl1.x =
//            mMiddleX - (mCornerY - mMiddleY) * (mCornerY - mMiddleY) / (mCornerX - mMiddleX)
//        mBezierControl1.y = mCornerY.toFloat()
//        mBezierControl2.x = mCornerX.toFloat()
//        //mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX) * (mCornerX - mMiddleX) / (mCornerY - mMiddleY);
//        val f4 = mCornerY - mMiddleY
//        if (f4 == 0f) {
//            mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX) * (mCornerX - mMiddleX) / 0.1f
//        } else {
//            mBezierControl2.y =
//                mMiddleY - (mCornerX - mMiddleX) * (mCornerX - mMiddleX) / (mCornerY - mMiddleY)
//        }
//        mBezierStart1.x = mBezierControl1.x - (mCornerX - mBezierControl1.x) / 2
//        mBezierStart1.y = mCornerY.toFloat()
//
//        // 当mBezierStart1.x < 0或者mBezierStart1.x > 480时
//        // 如果继续翻页，会出现BUG故在此限制
//        if (mTouch.x > 0 && mTouch.x < mScreenWidth) {
//            if (mBezierStart1.x < 0 || mBezierStart1.x > mScreenWidth) {
//                if (mBezierStart1.x < 0) mBezierStart1.x = mScreenWidth - mBezierStart1.x
//                val f1 = Math.abs(mCornerX - mTouch.x)
//                val f2 = mScreenWidth * f1 / mBezierStart1.x
//                mTouch.x = Math.abs(mCornerX - f2)
//                val f3 = Math.abs(mCornerX - mTouch.x)
//                * Math.abs(mCornerY - mTouch.y) / f1
//                mTouch.y = Math.abs(mCornerY - f3)
//                mMiddleX = (mTouch.x + mCornerX) / 2
//                mMiddleY = (mTouch.y + mCornerY) / 2
//                mBezierControl1.x =
//                    mMiddleX - (mCornerY - mMiddleY) * (mCornerY - mMiddleY) / (mCornerX - mMiddleX)
//                mBezierControl1.y = mCornerY.toFloat()
//                mBezierControl2.x = mCornerX.toFloat()
//                //mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX) * (mCornerX - mMiddleX) / (mCornerY - mMiddleY);
//                val f5 = mCornerY - mMiddleY
//                if (f5 == 0f) {
//                    mBezierControl2.y =
//                        mMiddleY - (mCornerX - mMiddleX) * (mCornerX - mMiddleX) / 0.1f
//                } else {
//                    mBezierControl2.y =
//                        mMiddleY - (mCornerX - mMiddleX) * (mCornerX - mMiddleX) / (mCornerY - mMiddleY)
//                }
//                mBezierStart1.x = mBezierControl1.x - (mCornerX - mBezierControl1.x) / 2
//            }
//        }
//        mBezierStart2.x = mCornerX.toFloat()
//        mBezierStart2.y = mBezierControl2.y - (mCornerY - mBezierControl2.y) / 2
//        mTouchToCornerDis = Math.hypot(
//            (mTouch.x - mCornerX).toDouble(),
//            (mTouch.y - mCornerY).toDouble()
//        ).toFloat()
//        mBezierEnd1 = getCross(mTouch, mBezierControl1, mBezierStart1, mBezierStart2)
//        mBezierEnd2 = getCross(mTouch, mBezierControl2, mBezierStart1, mBezierStart2)
//
//        /*
//         * mBeziervertex1.x 推导
//		 * ((mBezierStart1.x+mBezierEnd1.x)/2+mBezierControl1.x)/2 化简等价于
//		 * (mBezierStart1.x+ 2*mBezierControl1.x+mBezierEnd1.x) / 4
//		 */mBeziervertex1.x = (mBezierStart1.x + 2 * mBezierControl1.x + mBezierEnd1.x) / 4
//        mBeziervertex1.y = (2 * mBezierControl1.y + mBezierStart1.y + mBezierEnd1.y) / 4
//        mBeziervertex2.x = (mBezierStart2.x + 2 * mBezierControl2.x + mBezierEnd2.x) / 4
//        mBeziervertex2.y = (2 * mBezierControl2.y + mBezierStart2.y + mBezierEnd2.y) / 4
//    }
//
//    /**
//     * 求解直线P1P2和直线P3P4的交点坐标
//     *
//     * @param P1
//     * @param P2
//     * @param P3
//     * @param P4
//     * @return
//     */
//    fun getCross(P1: PointF, P2: PointF, P3: PointF, P4: PointF): PointF {
//        val CrossP = PointF()
//        // 二元函数通式： y=ax+b
//        val a1 = (P2.y - P1.y) / (P2.x - P1.x)
//        val b1 = (P1.x * P2.y - P2.x * P1.y) / (P1.x - P2.x)
//        val a2 = (P4.y - P3.y) / (P4.x - P3.x)
//        val b2 = (P3.x * P4.y - P4.x * P3.y) / (P3.x - P4.x)
//        CrossP.x = (b2 - b1) / (a1 - a2)
//        CrossP.y = a1 * CrossP.x + b1
//        return CrossP
//    }
//
//    /**
//     * 计算拖拽点对应的拖拽脚
//     *
//     * @param x
//     * @param y
//     */
//    public override fun calcCornerXY(x: Float, y: Float) {
//        mCornerX = if (x <= mScreenWidth / 2) {
//            0
//        } else {
//            mScreenWidth
//        }
//        mCornerY = if (y <= mScreenHeight / 2) {
//            0
//        } else {
//            mScreenHeight
//        }
//        mIsRTandLB = (mCornerX == 0 && mCornerY == mScreenHeight
//                || mCornerX == mScreenWidth && mCornerY == 0)
//    }
//
//    override fun drawStatic(canvas: Canvas) {
//        TODO("Not yet implemented")
//    }
//
//    /**
//     * 画第二页
//     * @param canvas
//     */
//    override fun drawNextPage(canvas: Canvas) {
//        drawNextPageCanvas(canvas)
//    }
//
//    /**
//     * 画下一页
//     *
//     * @param canvas
//     */
//    private fun drawNextPageCanvas(canvas: Canvas) {
//        mNextPagePath.reset()
//        mNextPagePath.moveTo(mBezierStart1.x, mBezierStart1.y)
//        mNextPagePath.lineTo(mBeziervertex1.x, mBeziervertex1.y)
//        mNextPagePath.lineTo(mBeziervertex2.x, mBeziervertex2.y)
//        mNextPagePath.lineTo(mBezierStart2.x, mBezierStart2.y)
//        mNextPagePath.lineTo(mCornerX.toFloat(), mCornerY.toFloat())
//        mNextPagePath.close()
//        mDegrees = Math.toDegrees(
//            Math.atan2(
//                mBezierControl1.x - mCornerX.toDouble(),
//                mBezierControl2.y - mCornerY.toDouble()
//            )
//        ).toFloat()
//        val leftx: Int
//        val rightx: Int
//        val mBackShadowDrawable: GradientDrawable?
//        if (mIsRTandLB) {  //左下及右上
//            leftx = mBezierStart1.x.toInt()
//            rightx = (mBezierStart1.x + mTouchToCornerDis / 4).toInt()
//            mBackShadowDrawable = mBackShadowDrawableLR
//        } else {
//            leftx = (mBezierStart1.x - mTouchToCornerDis / 4).toInt()
//            rightx = mBezierStart1.x.toInt()
//            mBackShadowDrawable = mBackShadowDrawableRL
//        }
//        canvas.save()
//        try {
//            canvas.clipPath(mCurPagePath)
//            canvas.clipPath(mNextPagePath, Region.Op.INTERSECT)
//        } catch (e: Exception) {
//        }
//        val nextPageBitmapResult: HashMap<String, Any> =
//            mBitmapManager.getNextPageBitmap()
//        if (nextPageBitmapResult["result"] as Int == 0) {
//            canvas.drawBitmap((nextPageBitmapResult["bitmap"] as Bitmap?)!!, 0f, 0f, null)
//        }
//        canvas.rotate(mDegrees, mBezierStart1.x, mBezierStart1.y)
//        mBackShadowDrawable!!.setBounds(
//            leftx, mBezierStart1.y.toInt(),
//            rightx, (mMaxLength + mBezierStart1.y) as Int
//        ) //左上及右下角的xy坐标值,构成一个矩形
//        mBackShadowDrawable.draw(canvas)
//        canvas.restore()
//    }
//
//    /**
//     * 画第一页
//     * @param canvas
//     */
//    override fun drawCurPage(canvas: Canvas) {
//        mCurPagePath.reset()
//        drawCurrentPageCanvas(canvas)
//        drawCurrentPageShadowCanvas(canvas)
//        drawCurrentPageBackArea(canvas)
//    }
//
//    /**
//     * 画翻起页
//     *
//     * @param canvas
//     */
//    private fun drawCurrentPageBackArea(canvas: Canvas) {
//        val i = (mBezierStart1.x + mBezierControl1.x).toInt() / 2
//        val f1 = Math.abs(i - mBezierControl1.x)
//        val i1 = (mBezierStart2.y + mBezierControl2.y).toInt() / 2
//        val f2 = Math.abs(i1 - mBezierControl2.y)
//        val f3 = Math.min(f1, f2)
//        mNextPagePath.reset()
//        mNextPagePath.moveTo(mBeziervertex2.x, mBeziervertex2.y)
//        mNextPagePath.lineTo(mBeziervertex1.x, mBeziervertex1.y)
//        mNextPagePath.lineTo(mBezierEnd1.x, mBezierEnd1.y)
//        mNextPagePath.lineTo(mTouch.x, mTouch.y)
//        mNextPagePath.lineTo(mBezierEnd2.x, mBezierEnd2.y)
//        mNextPagePath.close()
//        val mFolderShadowDrawable: GradientDrawable?
//        val left: Int
//        val right: Int
//        if (mIsRTandLB) {
//            left = (mBezierStart1.x - 1).toInt()
//            right = (mBezierStart1.x + f3 + 1).toInt()
//            mFolderShadowDrawable = mFolderShadowDrawableLR
//        } else {
//            left = (mBezierStart1.x - f3 - 1).toInt()
//            right = (mBezierStart1.x + 1).toInt()
//            mFolderShadowDrawable = mFolderShadowDrawableRL
//        }
//        canvas.save()
//        try {
//            canvas.clipPath(mCurPagePath)
//            canvas.clipPath(mNextPagePath, Region.Op.INTERSECT)
//        } catch (e: Exception) {
//        }
//        mPaint.colorFilter = mColorMatrixFilter
//        val dis = Math.hypot(
//            mCornerX - mBezierControl1.x.toDouble(),
//            mBezierControl2.y - mCornerY.toDouble()
//        ).toFloat()
//        val f8 = (mCornerX - mBezierControl1.x) / dis
//        val f9 = (mBezierControl2.y - mCornerY) / dis
//        mMatrixArray[0] = 1 - 2 * f9 * f9
//        mMatrixArray[1] = 2 * f8 * f9
//        mMatrixArray[3] = mMatrixArray[1]
//        mMatrixArray[4] = 1 - 2 * f8 * f8
//        mMatrix.reset()
//        mMatrix.setValues(mMatrixArray)
//        mMatrix.preTranslate(-mBezierControl1.x, -mBezierControl1.y)
//        mMatrix.postTranslate(mBezierControl1.x, mBezierControl1.y)
//        canvas.drawBitmap(mBitmapManager.getCurrentPageBitmap(), mMatrix, mPaint)
//        // canvas.drawBitmap(bitmap, mMatrix, null);
//        mPaint.colorFilter = null
//        canvas.rotate(mDegrees, mBezierStart1.x, mBezierStart1.y)
//        mFolderShadowDrawable!!.setBounds(
//            left, mBezierStart1.y.toInt(), right,
//            (mBezierStart1.y + mMaxLength).toInt()
//        )
//        mFolderShadowDrawable.draw(canvas)
//        canvas.restore()
//    }
//
//    /**
//     * 画翻起页面
//     */
//    private fun drawCurrentPageCanvas(canvas: Canvas) {
//        mCurPagePath.reset()
//        mCurPagePath.moveTo(mBezierStart1.x, mBezierStart1.y)
//        mCurPagePath.quadTo(
//            mBezierControl1.x, mBezierControl1.y, mBezierEnd1.x,
//            mBezierEnd1.y
//        )
//        mCurPagePath.lineTo(mTouch.x, mTouch.y)
//        mCurPagePath.lineTo(mBezierEnd2.x, mBezierEnd2.y)
//        mCurPagePath.quadTo(
//            mBezierControl2.x, mBezierControl2.y, mBezierStart2.x,
//            mBezierStart2.y
//        )
//        mCurPagePath.lineTo(mCornerX.toFloat(), mCornerY.toFloat())
//        mCurPagePath.close()
//        canvas.save()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            canvas.clipOutPath(mCurPagePath)
//        } else {
//            canvas.clipPath(mCurPagePath, Region.Op.XOR)
//        }
//        canvas.drawBitmap(mBitmapManager.getCurrentPageBitmap(), 0, 0, null)
//        try {
//            canvas.restore()
//        } catch (e: Exception) {
//        }
//    }
//
//    /**
//     * 画翻起页面的阴影
//     */
//    private fun drawCurrentPageShadowCanvas(canvas: Canvas) {
//        val degree: Double
//        degree = if (mIsRTandLB) {
//            Math.PI / 4 - Math.atan2(
//                mBezierControl1.y - mTouch.y.toDouble(),
//                mTouch.x - mBezierControl1.x.toDouble()
//            )
//        } else {
//            Math.PI / 4 - Math.atan2(
//                mTouch.y - mBezierControl1.y.toDouble(),
//                mTouch.x - mBezierControl1.x.toDouble()
//            )
//        }
//        // 翻起页阴影顶点与touch点的距离
//        val d1 = 25.toFloat() * 1.414 * Math.cos(degree)
//        val d2 = 25.toFloat() * 1.414 * Math.sin(degree)
//        val x = (mTouch.x + d1).toFloat()
//        val y: Float
//        y = if (mIsRTandLB) {
//            (mTouch.y + d2).toFloat()
//        } else {
//            (mTouch.y - d2).toFloat()
//        }
//        mNextPagePath.reset()
//        mNextPagePath.moveTo(x, y)
//        mNextPagePath.lineTo(mTouch.x, mTouch.y)
//        mNextPagePath.lineTo(mBezierControl1.x, mBezierControl1.y)
//        mNextPagePath.lineTo(mBezierStart1.x, mBezierStart1.y)
//        mNextPagePath.close()
//        var rotateDegrees: Float
//        canvas.save()
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                canvas.clipOutPath(mCurPagePath)
//            } else {
//                canvas.clipPath(mCurPagePath, Region.Op.XOR)
//            }
//            canvas.clipPath(mNextPagePath, Region.Op.INTERSECT)
//        } catch (e: Exception) {
//        }
//        var leftx: Int
//        var rightx: Int
//        var mCurrentPageShadow: GradientDrawable?
//        if (mIsRTandLB) {
//            leftx = mBezierControl1.x.toInt()
//            rightx = mBezierControl1.x.toInt() + 25
//            mCurrentPageShadow = mFrontShadowDrawableVLR
//        } else {
//            leftx = (mBezierControl1.x - 25).toInt()
//            rightx = mBezierControl1.x.toInt() + 1
//            mCurrentPageShadow = mFrontShadowDrawableVRL
//        }
//        rotateDegrees = Math.toDegrees(
//            Math.atan2(
//                mTouch.x - mBezierControl1.x.toDouble(),
//                mBezierControl1.y - mTouch.y.toDouble()
//            )
//        ).toFloat()
//        canvas.rotate(rotateDegrees, mBezierControl1.x, mBezierControl1.y)
//        mCurrentPageShadow!!.setBounds(
//            leftx, (mBezierControl1.y - mMaxLength).toInt(),
//            rightx, mBezierControl1.y.toInt()
//        )
//        mCurrentPageShadow.draw(canvas)
//        canvas.restore()
//        mNextPagePath.reset()
//        mNextPagePath.moveTo(x, y)
//        mNextPagePath.lineTo(mTouch.x, mTouch.y)
//        mNextPagePath.lineTo(mBezierControl2.x, mBezierControl2.y)
//        mNextPagePath.lineTo(mBezierStart2.x, mBezierStart2.y)
//        mNextPagePath.close()
//        canvas.save()
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                canvas.clipOutPath(mCurPagePath)
//            } else {
//                canvas.clipPath(mCurPagePath, Region.Op.XOR)
//            }
//            canvas.clipPath(mNextPagePath, Region.Op.INTERSECT)
//        } catch (e: Exception) {
//        }
//        if (mIsRTandLB) {
//            leftx = mBezierControl2.y.toInt()
//            rightx = (mBezierControl2.y + 25).toInt()
//            mCurrentPageShadow = mFrontShadowDrawableHTB
//        } else {
//            leftx = (mBezierControl2.y - 25).toInt()
//            rightx = (mBezierControl2.y + 1).toInt()
//            mCurrentPageShadow = mFrontShadowDrawableHBT
//        }
//        rotateDegrees = Math.toDegrees(
//            atan2(
//                mBezierControl2.y - mTouch.y.toDouble(),
//                mBezierControl2.x - mTouch.x.toDouble()
//            )
//        ).toFloat()
//        canvas.rotate(rotateDegrees, mBezierControl2.x, mBezierControl2.y)
//        val temp: Float = if (mBezierControl2.y < 0) mBezierControl2.y - mScreenHeight else mBezierControl2.y
//        val hmg = hypot(mBezierControl2.x.toDouble(), temp.toDouble()).toInt()
//        if (hmg > mMaxLength) {
//            mCurrentPageShadow!!.setBounds(
//                (mBezierControl2.x - 25).toInt() - hmg, leftx,
//                (mBezierControl2.x + mMaxLength).toInt() - hmg, rightx
//            )
//        } else {
//            mCurrentPageShadow!!.setBounds(
//                (mBezierControl2.x - mMaxLength).toInt(), leftx,
//                mBezierControl2.x.toInt(), rightx
//            )
//        }
//        mCurrentPageShadow.draw(canvas)
//        canvas.restore()
//    }
//
//    /**
//     * 创建阴影的GradientDrawable
//     */
//    private fun createDrawable() {
//        val color = intArrayOf(0x333333, -0x4fcccccd)
//        mFolderShadowDrawableRL = GradientDrawable(
//            GradientDrawable.Orientation.RIGHT_LEFT, color
//        )
//        mFolderShadowDrawableRL!!.gradientType = GradientDrawable.LINEAR_GRADIENT
//        mFolderShadowDrawableLR = GradientDrawable(
//            GradientDrawable.Orientation.LEFT_RIGHT, color
//        )
//        mFolderShadowDrawableLR!!.gradientType = GradientDrawable.LINEAR_GRADIENT
//        mBackShadowColors = intArrayOf(-0xeeeeef, 0x111111)
//        mBackShadowDrawableRL = GradientDrawable(
//            GradientDrawable.Orientation.RIGHT_LEFT, mBackShadowColors
//        )
//        mBackShadowDrawableRL!!.gradientType = GradientDrawable.LINEAR_GRADIENT
//        mBackShadowDrawableLR = GradientDrawable(
//            GradientDrawable.Orientation.LEFT_RIGHT, mBackShadowColors
//        )
//        mBackShadowDrawableLR!!.gradientType = GradientDrawable.LINEAR_GRADIENT
//        mFrontShadowColors = intArrayOf(-0x7feeeeef, 0x111111)
//        mFrontShadowDrawableVLR = GradientDrawable(
//            GradientDrawable.Orientation.LEFT_RIGHT, mFrontShadowColors
//        )
//        mFrontShadowDrawableVLR!!.gradientType = GradientDrawable.LINEAR_GRADIENT
//        mFrontShadowDrawableVRL = GradientDrawable(
//            GradientDrawable.Orientation.RIGHT_LEFT, mFrontShadowColors
//        )
//        mFrontShadowDrawableVRL!!.gradientType = GradientDrawable.LINEAR_GRADIENT
//        mFrontShadowDrawableHTB = GradientDrawable(
//            GradientDrawable.Orientation.TOP_BOTTOM, mFrontShadowColors
//        )
//        mFrontShadowDrawableHTB!!.gradientType = GradientDrawable.LINEAR_GRADIENT
//        mFrontShadowDrawableHBT = GradientDrawable(
//            GradientDrawable.Orientation.BOTTOM_TOP, mFrontShadowColors
//        )
//        mFrontShadowDrawableHBT!!.gradientType = GradientDrawable.LINEAR_GRADIENT
//    }
//
//    init {
//        mCurPagePath = Path()
//        mNextPagePath = Path()
//        mMaxLength =
//            hypot(mScreenWidth.toDouble(), mScreenHeight.toDouble()).toFloat()
//        mPaint = Paint()
//        mPaint.style = Paint.Style.FILL
//        val cm = ColorMatrix() //设置颜色数组
//        val array = floatArrayOf(
//            0.55f,
//            0f,
//            0f,
//            0f,
//            80.0f,
//            0f,
//            0.55f,
//            0f,
//            0f,
//            80.0f,
//            0f,
//            0f,
//            0.55f,
//            0f,
//            80.0f,
//            0f,
//            0f,
//            0f,
//            0.2f,
//            0f
//        )
//        cm.set(array)
//        mColorMatrixFilter = ColorMatrixColorFilter(cm)
//        mMatrix = Matrix()
//        createDrawable()
//    }
//}
