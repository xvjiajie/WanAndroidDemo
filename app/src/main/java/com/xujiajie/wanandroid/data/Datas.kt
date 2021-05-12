package com.xujiajie.wanandroid.data

import com.xujiajie.wanandroid.data.bean.HomeFragment1BannerBean
import com.xujiajie.wanandroid.data.bean.HomeFragment1Bean
import com.xujiajie.wanandroid.data.bean.ProjectTypeBean
import java.io.Serializable

/**
 * 创建日期 2020/9/28
 * 描述：
 */

data class HomeFragment1HeadData(
    val banner: List<HomeFragment1BannerBean>,
    val top: List<HomeFragment1Bean>
) : Serializable

data class ProjectTypeData(
    val curPage: Int,
    val datas: List<ProjectTypeBean>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
):Serializable{
    val isHasNext: Boolean
        get() = pageCount >= curPage
}

