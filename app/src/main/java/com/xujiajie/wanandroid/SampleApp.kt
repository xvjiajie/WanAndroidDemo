package com.xujiajie.wanandroid

import android.app.Application
import android.content.Context
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV
import com.xujiajie.wanandroid.interceptor.CookiesManager
import com.xujiajie.wanandroid.interceptor.CustomSignInterceptor
import com.xujiajie.wanandroid.interceptor.ResultInterceptor
import com.xujiajie.wanandroid.interceptor.UnSafeHostnameVerifier
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.cache.converter.SerializableDiskConverter
import com.zhouyou.http.cache.model.CacheMode
import com.zhouyou.http.model.HttpParams

/**
 * 创建日期 2020/9/24
 * 描述：
 */
class SampleApp : Application() {

    lateinit var mContext:Context
    override fun onCreate() {
        super.onCreate()
        mContext = this
        MMKV.initialize(this)
        LiveEventBus.config()
        initHttp()
    }

    private fun initHttp() {
        EasyHttp.init(this) //默认初始化

        //全局设置请求参数
        EasyHttp.getInstance()
            .setCacheTime(-1) //-1表示永久缓存,单位:秒 ，Okhttp和自定义RxCache缓存都起作用
            .debug("RxEasyHttp", BuildConfig.DEBUG)
//            .setCookieStore(CookiesManager(mContext)) //cookie持久化存储，如果cookie不过期，则一直有效
            .setReadTimeOut(60 * 1000.toLong())
            .setWriteTimeOut(60 * 1000.toLong())
            .setConnectTimeout(60 * 1000.toLong())
            .setRetryCount(3) //默认网络不好自动重试3次
            .setRetryDelay(500) //每次延时500ms重试
            .setRetryIncreaseDelay(500) //每次延时叠加500ms
            .setCacheMode(CacheMode.NO_CACHE)
//            .setCacheDiskConverter(SerializableDiskConverter()) //默认缓存使用序列化转化
//            .setCacheMaxSize(50 * 1024 * 1024.toLong()) //设置缓存大小为50M
//            .setCacheVersion(1) //缓存版本为1
            .setHostnameVerifier(UnSafeHostnameVerifier(EasyHttp.getBaseUrl())) //全局访问规则
            .addInterceptor(ResultInterceptor())
            .addInterceptor(CustomSignInterceptor())
        if (!BuildConfig.BUILD_TYPE.contains("release")) {
            EasyHttp.getInstance().setCertificates()
        }
    }
}