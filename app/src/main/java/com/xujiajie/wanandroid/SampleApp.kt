package com.xujiajie.wanandroid

import android.app.Application
import android.content.Context

/**
 * 创建日期 2020/9/24
 * 描述：
 */
class SampleApp : Application() {

    lateinit var mContext:Context
    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}