package com.lwlizhe.module.novel.content.ui.view.activity

import com.lwlizhe.module.R
import com.lwlizhe.module.novel.content.ui.viewmodel.NovelContentViewModel
import com.lwlizhe.module.novel.content.ui.widget.reader.adapter.SimulationHorizontallyContentAdapter
import com.lwlizhe.moudle.common.base.entity.AppGlobalStateConfig
import com.lwlizhe.moudle.common.base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_novel_content.*


class NovelContentActivity : BaseActivity<NovelContentViewModel>() {

    private val novelAdapter by lazy { SimulationHorizontallyContentAdapter<Any>(this) }

    override fun initData() {
        val data = (0..100).toList()
        novelAdapter.setData(data)
    }

    override fun initListener() {
    }

    override fun initView() {
        rcv_novel_container.adapter = novelAdapter
    }

    override fun onConfigDataChanged(configData: AppGlobalStateConfig) {
    }

    override fun getRootViewResId(): Int {
        return R.layout.activity_novel_content
    }
}