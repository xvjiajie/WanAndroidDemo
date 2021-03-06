package com.xujiajie.wanandroid.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.rxLifeScope
import com.google.gson.Gson
import com.xujiajie.wanandroid.base.BaseLiveData
import com.xujiajie.wanandroid.base.BaseViewModel
import com.xujiajie.wanandroid.data.bean.HomeProjectTabBean
import com.xujiajie.wanandroid.help.UrlHelp
import com.xujiajie.wanandroid.utils.Resource
import org.json.JSONObject
import rxhttp.RxHttp
import rxhttp.toResponse

/**
 * 创建日期 2020/9/29
 * 描述：
 */
class VMHomeProjectFragment(application: Application) : BaseViewModel(application) {
//HomeProjectTabBean

    val mLiveData = BaseLiveData<Resource<List<HomeProjectTabBean>>>()

    fun getTabData() {
        rxLifeScope.launch ({
            RxHttp.get(UrlHelp.Api.project_tree)
                .toResponse<List<HomeProjectTabBean>>()
                .await()?.apply {
                    mLiveData.update(Resource.success(this))
                }
        },{
            mLiveData.update(Resource.error(it))
        })
    }
}