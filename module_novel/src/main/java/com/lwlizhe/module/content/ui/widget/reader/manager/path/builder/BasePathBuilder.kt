package com.lwlizhe.module.content.ui.widget.reader.manager.path.builder

import android.graphics.Path
import android.graphics.Point
import android.graphics.PointF
import com.lwlizhe.module.content.ui.widget.reader.manager.path.NovelContentPathManager

open abstract class BasePathBuilder {

    protected var pathManager: NovelContentPathManager
    var mTouchPoint = Point()


    constructor(pathManager: NovelContentPathManager) {
        this.pathManager = pathManager
    }

    abstract fun buildPath(
        point: Point,
        dx: Int,
        width: Int,
        height: Int,
        isOperateByUser: Boolean
    ): Path?
}