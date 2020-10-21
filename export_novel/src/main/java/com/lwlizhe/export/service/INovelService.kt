package com.lwlizhe.export.service

import com.alibaba.android.arouter.facade.template.IProvider

interface INovelService : IProvider {

    fun queryTargetNovelByName(name: String)
}