package com.xujiajie.wanandroid.data

import com.xujiajie.wanandroid.data.bean.HomeFragment1BannerBean
import com.xujiajie.wanandroid.data.bean.HomeFragment1Bean
import java.io.Serializable

/**
 * 创建日期 2020/9/28
 * 描述：
 */

data class HomeFragment1HeadData(
    val banner: List<HomeFragment1BannerBean>,
    val top: List<HomeFragment1Bean>
) : Serializable