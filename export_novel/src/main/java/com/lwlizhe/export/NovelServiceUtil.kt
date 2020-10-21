package com.lwlizhe.export

import com.alibaba.android.arouter.launcher.ARouter
import com.lwlizhe.export.router.NovelRouterTable
import com.lwlizhe.export.service.INovelService

class NovelServiceUtil {
    companion object{
        @JvmStatic
        fun jumpToQueryPage(){

        }

        @JvmStatic
        fun getService():INovelService{
            return ARouter.getInstance().build(NovelRouterTable.PATH_NOVEL_SERVICE).navigation() as INovelService
        }

    }
}