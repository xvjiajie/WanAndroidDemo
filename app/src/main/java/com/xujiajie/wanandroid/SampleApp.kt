package com.xujiajie.wanandroid

import android.app.Application
import android.content.Context
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp

/**
 * 创建日期 2020/9/24
 * 描述：
 */
@HiltAndroidApp
class SampleApp : Application() {

    lateinit var mContext:Context
    override fun onCreate() {
        super.onCreate()
        mContext = this
        MMKV.initialize(this)
        LiveEventBus.config()
        RxHttpManager.init(this)
    }

}