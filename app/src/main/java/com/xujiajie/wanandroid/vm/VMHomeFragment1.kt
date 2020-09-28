package com.xujiajie.wanandroid.vm

import android.app.Application
import android.util.Log
import com.xujiajie.wanandroid.base.BaseLiveData
import com.xujiajie.wanandroid.base.BaseViewModel
import com.xujiajie.wanandroid.data.HomeFragment1HeadData
import com.xujiajie.wanandroid.data.HomeFragment1ListData
import com.xujiajie.wanandroid.data.bean.HomeFragment1BannerBean
import com.xujiajie.wanandroid.data.bean.HomeFragment1Bean
import com.xujiajie.wanandroid.help.PageInfo
import com.xujiajie.wanandroid.help.UrlHelp
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.CallClazzProxy
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import com.zhouyou.http.model.ApiResult
import com.zhouyou.http.subsciber.BaseSubscriber
import com.zhouyou.http.utils.http.Resource
import io.reactivex.Observable
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


/**
 * 创建日期 2020/9/25
 * 描述：
 */
class VMHomeFragment1(application: Application) : BaseViewModel(application) {
    val mPageInfo = PageInfo()
    val live: BaseLiveData<Resource<HomeFragment1ListData>> by lazy {
        BaseLiveData()
    }
    val headDataLive: BaseLiveData<Resource<HomeFragment1HeadData>> by lazy {
        BaseLiveData()
    }

    fun getData() {
        EasyHttp.get("/article/list/${mPageInfo.page}/json")
            .baseUrl(UrlHelp.bsseUrl)
            .execute(object : SimpleCallBack<HomeFragment1ListData>() {
                override fun onError(e: ApiException) {
                    live.update(Resource.failure(e.code, e.message))
                }

                override fun onSuccess(t: HomeFragment1ListData) {
                    live.update(Resource.success(t))
                }
            })
    }

    fun getHeadData() {//https://www.wanandroid.com/banner/json  https://www.wanandroid.com/article/top/json
//https://www.wanandroid.com/article/top/json
        val declaredMethods: Array<Method> = HomeFragment1HeadData::class.java.getDeclaredMethods()
        var a = 0
        var b = 0
         val typeList = arrayListOf<Type>()
        for (declaredMethod in declaredMethods) {
            a++
            b = 0
//            Log.d(TAG, "getHeadData:a=$a $declaredMethod")
            val types: Array<Type> = declaredMethod.genericParameterTypes
            types.forEach {
                b++
                if (it is ParameterizedType) {
                    val parameterizedType: ParameterizedType = it
//                    println("ParameterizedType type :$parameterizedType")
                    typeList.add(parameterizedType)
                    Log.d(TAG, "getHeadData:a=$a b=$b " + parameterizedType)
                }else{
                    Log.d(TAG, "getHeadData:222 a=$a b=$b " + it)
                }
            }
        }
        val payOrderInfoBeanObservable: Observable<List<HomeFragment1BannerBean>> =
            EasyHttp.get(UrlHelp.Api.banner_json)
                .baseUrl(UrlHelp.bsseUrl)
                .execute(object :
                    CallClazzProxy<ApiResult<List<HomeFragment1BannerBean>>, List<HomeFragment1BannerBean>>(
                        typeList[0]
                    ) {})
        val boxCashRegisterDataObservable: Observable<List<HomeFragment1Bean>> =
            EasyHttp.get(UrlHelp.Article.top_json)
                .baseUrl(UrlHelp.bsseUrl)
                .execute(object :
                    CallClazzProxy<ApiResult<List<HomeFragment1Bean>>, List<HomeFragment1Bean>>(
                        typeList[1]
                    ) {})

        Observable.zip(
            payOrderInfoBeanObservable,
            boxCashRegisterDataObservable,
            { bannerBean, fragment1Bean ->
                HomeFragment1HeadData(bannerBean, fragment1Bean)
            }).subscribe(object : BaseSubscriber<HomeFragment1HeadData?>() {
            override fun onError(e: ApiException) {
                headDataLive.update(Resource.error(e))
            }

            override fun onNext(boxCashRegisterData: HomeFragment1HeadData) {
                super.onNext(boxCashRegisterData)
//                boxCashRegisterData.component1()
//                boxCashRegisterData.component2()
                headDataLive.update(Resource.success(boxCashRegisterData))
            }
        })

    }
}