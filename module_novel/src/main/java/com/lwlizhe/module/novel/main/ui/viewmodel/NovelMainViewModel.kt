package com.lwlizhe.module.novel.content.ui.viewmodel

import com.lwlizhe.module.novel.main.ui.livedata.BookLibraryLiveData
import com.lwlizhe.module.novel.main.ui.model.BookLibraryModel
import com.lwlizhe.moudle.common.base.viewmodel.BaseViewModel

class NovelMainViewModel:BaseViewModel() {

    var bookLibraryModel = BookLibraryModel(this)


    var bookLibraryLiveData = BookLibraryLiveData()


}