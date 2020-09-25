package com.xujiajie.wanandroid.help

/**
 * 创建日期 2020/4/9
 * 描述：页码
 */
class PageInfo {
    var page = 0
        private set

    fun nextPage() {
        page++
    }

    fun reset() {
        page = 1
    }

    val isFirstPage: Boolean
        get() = page == 1
}