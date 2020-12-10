package com.lwlizhe.module.novel.content.ui.view

import android.content.Context
import android.graphics.Color
import com.lwlizhe.module.R
import com.lwlizhe.module.novel.content.ui.viewmodel.NovelMainViewModel
import com.lwlizhe.module.novel.util.ViewPager2BindHelper
import com.lwlizhe.moudle.common.base.entity.AppGlobalStateConfig
import com.lwlizhe.moudle.common.base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_novel_main.*
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

class NovelMainActivity : BaseActivity<NovelMainViewModel>() {

    var tabContent = arrayOf("Test1", "Test2", "Test3")

    override fun getRootViewResId(): Int {
        return R.layout.activity_novel_main
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun initView() {
        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView =
                    ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.GRAY
                colorTransitionPagerTitleView.selectedColor = Color.BLACK
                colorTransitionPagerTitleView.text = tabContent[index]
                colorTransitionPagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return colorTransitionPagerTitleView
            }

            override fun getCount(): Int {
                return tabContent.size
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                return indicator
            }
        }

        indicator.navigator = commonNavigator

        ViewPager2BindHelper.bind(indicator, viewPager)
    }

    override fun onConfigDataChanged(configData: AppGlobalStateConfig) {
    }
}