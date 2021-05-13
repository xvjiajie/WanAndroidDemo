package com.xujiajie.wanandroid.vm

import android.app.Application
import androidx.lifecycle.rxLifeScope
import com.xujiajie.wanandroid.base.BaseLiveData
import com.xujiajie.wanandroid.base.BaseViewModel
import com.xujiajie.wanandroid.data.bean.HomeProjectTabBean
import com.xujiajie.wanandroid.help.PageInfo
import com.xujiajie.wanandroid.help.UrlHelp
import com.xujiajie.wanandroid.utils.Resource
import rxhttp.RxHttp
import rxhttp.toResponse

/**
 * 创建日期 2021/5/13
 * 描述：
 */
class HomeSetUpFragmentViewModel(application: Application) :BaseViewModel(application) {

    val mLiveData = BaseLiveData<Resource<List<HomeProjectTabBean>>>()

    fun getTabData() {
        rxLifeScope.launch ({
            RxHttp.get(UrlHelp.Api.tree_json)
                .toResponse<List<HomeProjectTabBean>>()
                .await()?.apply {
                    mLiveData.update(Resource.success(this))
                }
        },{
            mLiveData.update(Resource.error(it))
        })
    }
}