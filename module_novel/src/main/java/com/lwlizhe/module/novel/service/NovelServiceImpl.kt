package com.lwlizhe.module.novel.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.lwlizhe.export.router.NovelRouterTable
import com.lwlizhe.export.service.INovelService

@Route(path = NovelRouterTable.PATH_NOVEL_SERVICE)
class NovelServiceImpl :INovelService{
    override fun queryTargetNovelByName(name: String) {
        TODO("Not yet implemented")
    }

    override fun init(context: Context?) {
        TODO("Not yet implemented")
    }
}