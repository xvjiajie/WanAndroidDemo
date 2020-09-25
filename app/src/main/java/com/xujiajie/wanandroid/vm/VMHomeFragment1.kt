package com.xujiajie.wanandroid.vm

import android.app.Application
import com.xujiajie.wanandroid.HomeFragment1Bean
import com.xujiajie.wanandroid.base.BaseBean
import com.xujiajie.wanandroid.base.BaseLiveData
import com.xujiajie.wanandroid.base.BaseViewModel
import com.xujiajie.wanandroid.data.HomeFragment1ListData1
import com.xujiajie.wanandroid.help.PageInfo
import com.xujiajie.wanandroid.help.UrlHelp
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import com.zhouyou.http.utils.http.Resource

/**
 * 创建日期 2020/9/25
 * 描述：
 */
class VMHomeFragment1(application: Application) : BaseViewModel(application) {
    val mPageInfo = PageInfo()
    val live:BaseLiveData<Resource<HomeFragment1ListData1>> by lazy {
        BaseLiveData()
    }

    fun getData(){
        EasyHttp.get("/article/list/${mPageInfo.page}/json")
            .baseUrl(UrlHelp.bsseUrl)
            .execute(object : SimpleCallBack<HomeFragment1ListData1>() {
                override fun onError(e: ApiException) {
                    live.update(Resource.failure(e.code, e.message))
                }

                override fun onSuccess(t: HomeFragment1ListData1) {
                    live.update(Resource.success(t))
                }
            })
    }
}