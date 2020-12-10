package com.lwlizhe.module.novel.content.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lwlizhe.module.novel.content.entity.NovelCatalogueEntity
import com.lwlizhe.module.novel.content.entity.NovelContentEntity
import com.lwlizhe.moudle.common.base.viewmodel.BaseViewModel

class NovelContentViewModel : BaseViewModel() {
    val contentData = MutableLiveData<NovelContentEntity>()
    val catalogueData= MutableLiveData<NovelCatalogueEntity>()

}