package com.lwlizhe.module.content.ui.widget.reader.manager.path.builder

import android.graphics.Path
import android.graphics.PointF
import com.lwlizhe.module.content.ui.widget.reader.manager.path.NovelContentPathManager

class CoverPathBuilder(manager: NovelContentPathManager) : BasePathBuilder(manager) {
    override fun buildPath(pointF:PointF,dx: Int,width:Int,height:Int,isOperateByUser:Boolean): Path? {
        return null
    }
}