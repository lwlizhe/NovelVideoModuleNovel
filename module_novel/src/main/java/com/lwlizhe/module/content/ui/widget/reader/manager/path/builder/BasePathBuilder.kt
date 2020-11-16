package com.lwlizhe.module.content.ui.widget.reader.manager.path.builder

import android.graphics.Path
import android.graphics.PointF
import com.lwlizhe.module.content.ui.widget.reader.manager.path.NovelContentPathManager

open abstract class BasePathBuilder {

    protected var pathManager: NovelContentPathManager
    var mTouchPointF = PointF()


    constructor(pathManager: NovelContentPathManager) {
        this.pathManager = pathManager
    }

    abstract fun buildPath(
        pointF: PointF,
        dx: Int,
        width: Int,
        height: Int,
        isOperateByUser: Boolean
    ): Path?
}