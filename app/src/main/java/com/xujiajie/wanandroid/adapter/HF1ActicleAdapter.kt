package com.xujiajie.wanandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.xujiajie.wanandroid.R
import com.xujiajie.wanandroid.data.bean.HomeFragment1Bean
import com.xujiajie.wanandroid.databinding.ItemLayoutHf1ArticleBinding

/**
 * 创建日期 2020/9/28
 * 描述：
 */
class HF1ActicleAdapter : BaseQuickAdapter<HomeFragment1Bean,BaseDataBindingHolder<ItemLayoutHf1ArticleBinding>>(
    R.layout.item_layout_hf1_article
) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemLayoutHf1ArticleBinding>,
        item: HomeFragment1Bean
    ) {
        holder.dataBinding?.apply {
            tvStickContent.text = item.title
            setVariable(R.id.viewDivision,holder.adapterPosition == (data.size-1))
        }
    }
}