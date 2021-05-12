package com.xujiajie.wanandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xujiajie.wanandroid.R
import com.xujiajie.wanandroid.data.ProjectTypeData
import com.xujiajie.wanandroid.data.bean.ProjectTypeBean
import com.xujiajie.wanandroid.ext.htmlToSpanned

/**
 * 创建日期 2020/12/7
 * 描述：
 */
class ProjectTypeAdapter :
    BaseQuickAdapter<ProjectTypeBean, BaseViewHolder>(R.layout.item_project_type),LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: ProjectTypeBean) {
        holder.apply {
            setText(R.id.tvHomePageItemAuthor,when {
                !item.author.isNullOrEmpty() -> {
                    item.author
                }
                !item.shareUser.isNullOrEmpty() -> {
                    item.shareUser
                }
                else -> context.getString(R.string.anonymous)
            })
            setText(R.id.tvHomePageItemContent,item.title.htmlToSpanned())
            setText(R.id.tvHomePageItemType,when {
                !item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                    "${item.superChapterName.htmlToSpanned()}·${item.chapterName.htmlToSpanned()}"
                item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                    item.chapterName.htmlToSpanned()
                !item.superChapterName.isNullOrEmpty() && item.chapterName.isNullOrEmpty() ->
                    item.superChapterName.htmlToSpanned()
                else -> ""
            })
           setText(R.id.tvHomePageItemDate,item.niceDate)

        }
    }
}