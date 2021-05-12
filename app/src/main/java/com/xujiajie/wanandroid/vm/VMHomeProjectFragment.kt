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

                     /*.subscribe({ data -> }
                     ) { throwable -> mLiveData.update(Resource.failure(throwable.code, throwable.message))}*/
       /* EasyHttp.get(UrlHelp.Api.project_tree)
            .baseUrl(UrlHelp.bsseUrl)
            .execute(object : SimpleCallBack<String>() {
                override fun onError(e: ApiException) {
                    mLiveData.update(Resource.failure(e.code, e.message))
                }

                *//*override fun onSuccess(t: List<HomeProjectTabBean>?) {
                    mLiveData.update(Resource.success(t))
                }*//*

                override fun onSuccess(t: String?) {
                    val json = JSONObject(t)
                    val data = json.optJSONArray("data")
                    val list = arrayListOf<HomeProjectTabBean>()
                    val gson = Gson()
                    for (i in 0 until data.length()) {
                        val bean: HomeProjectTabBean =
                            gson.fromJson(data.optString(i), HomeProjectTabBean::class.java)
                        list.add(bean)
                        Log.d(TAG, "onSuccess: $bean")
                    }
                    mLiveData.update(Resource.success(list))
                }
            })*/
    }
}