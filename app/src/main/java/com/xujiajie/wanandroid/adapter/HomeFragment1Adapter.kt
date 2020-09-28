package com.xujiajie.wanandroid.adapter

import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.xujiajie.wanandroid.R
import com.xujiajie.wanandroid.data.bean.HomeFragment1Bean
import com.xujiajie.wanandroid.databinding.ItemHomeFragment1ListBinding
import com.xujiajie.wanandroid.ext.htmlToSpanned
import com.xujiajie.wanandroid.ext.text

/**
 * 创建日期 2020/9/25
 * 描述：
 */
class HomeFragment1Adapter :BaseQuickAdapter<HomeFragment1Bean,BaseDataBindingHolder<ItemHomeFragment1ListBinding>>(
    R.layout.item_home_fragment_1_list
), LoadMoreModule {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHomeFragment1ListBinding>,
        item: HomeFragment1Bean
    ) {
        holder?.dataBinding?.apply {
            tvHomePageItemAuthor.text = when {
                !item.author.isNullOrEmpty() -> {
                    item.author
                }
                !item.shareUser.isNullOrEmpty() -> {
                    item.shareUser
                }
                else -> tvHomePageItemAuthor.text(R.string.anonymous)
            }
            tvHomePageItemContent.text = item.title.htmlToSpanned()
            tvHomePageItemType.text = when {
                !item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                    "${item.superChapterName.htmlToSpanned()}·${item.chapterName.htmlToSpanned()}"
                item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                    item.chapterName.htmlToSpanned()
                !item.superChapterName.isNullOrEmpty() && item.chapterName.isNullOrEmpty() ->
                    item.superChapterName.htmlToSpanned()
                else -> ""
            }
            tvHomePageItemDate.text= item.niceDate
        }
    }
    private val TAG = "HomeFragment1Adapter"
}