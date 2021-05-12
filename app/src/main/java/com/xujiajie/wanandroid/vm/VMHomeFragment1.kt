package com.xujiajie.wanandroid.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.rxLifeScope
import com.xujiajie.wanandroid.base.BaseLiveData
import com.xujiajie.wanandroid.base.BaseViewModel
import com.xujiajie.wanandroid.data.HomeFragment1HeadData
import com.xujiajie.wanandroid.data.HomeFragment1ListData
import com.xujiajie.wanandroid.data.bean.HomeFragment1BannerBean
import com.xujiajie.wanandroid.data.bean.HomeFragment1Bean
import com.xujiajie.wanandroid.help.PageInfo
import com.xujiajie.wanandroid.help.UrlHelp
import com.xujiajie.wanandroid.utils.MoshiUtils
import com.xujiajie.wanandroid.utils.Resource
import com.xujiajie.wanandroid.utils.fromJson
import com.xujiajie.wanandroid.utils.toJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import rxhttp.RxHttp
import rxhttp.async
import rxhttp.toResponse


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
        val json = """["1","2","3","4"]"""
        val list = json.fromJson<List<String>>()
        Log.d(TAG, "getData:list= $list")
        rxLifeScope.launch({
            RxHttp.get("/article/list/${mPageInfo.page}/json") //第一步，确定请求方式，可以选择postForm、postJson等方法
                .toResponse<HomeFragment1ListData>()    //第二步，确认返回类型，这里代表返回String类型
                .await()?.apply {
                    live.update(Resource.success(this))
                    Log.d(TAG, "HomeFragment1ListData: "+this.toJson())
                }
            //可以直接更新UI
        }, {
            it
            Log.d(TAG, "getData: " + it.message)
            //异常回调，这里可以拿到Throwable对象
        })
        /* RxHttp.get("/article/list/${mPageInfo.page}/json")
             .asResponse(HomeFragment1ListData::class.java)
             .subscribe({ data -> live.update(Resource.success(data)) }
             ) { throwable ->
                 live.update(
                     Resource.failure(
                         throwable.hashCode(),
                         throwable.message
                     )
                 )
             }*/
        /* EasyHttp.get("/article/list/${mPageInfo.page}/json")
             .baseUrl(UrlHelp.bsseUrl)
             .execute(object : SimpleCallBack<HomeFragment1ListData>() {
                 override fun onError(e: ApiException) {
                     live.update(Resource.failure(e.code, e.message))
                 }

                 override fun onSuccess(t: HomeFragment1ListData) {
                     live.update(Resource.success(t))
                 }
             })*/
    }

    fun getHeadData() {//https://www.wanandroid.com/banner/json  https://www.wanandroid.com/article/top/json
//https://www.wanandroid.com/article/top/json
        /*val declaredMethods: Array<Method> = HomeFragment1HeadData::class.java.getDeclaredMethods()
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
                } else {
                    Log.d(TAG, "getHeadData:222 a=$a b=$b " + it)
                }
            }
        }*/
        rxLifeScope.launch({
            val ban = getBannerData(this)
            val top = getTopJsonData(this)
            val bannerData = ban.await()
            val topData = top.await()
            headDataLive.update(Resource.success(HomeFragment1HeadData(bannerData, topData)))
        }, {
            headDataLive.update(Resource.error(it))
        })
        /* val payOrderInfoBeanObservable: Observable<List<HomeFragment1BannerBean>> =
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
         })*/

    }

    suspend fun getBannerData(scope: CoroutineScope): Deferred<List<HomeFragment1BannerBean>> {
        return RxHttp.get(UrlHelp.Api.banner_json)
            .toResponse<List<HomeFragment1BannerBean>>()
            .async(scope)  //注意这里使用async异步操作符
    }

    suspend fun getTopJsonData(scope: CoroutineScope): Deferred<List<HomeFragment1Bean>> {
        return RxHttp.get(UrlHelp.Article.top_json)
            .toResponse<List<HomeFragment1Bean>>()
            .async(scope)  //注意这里使用async异步操作符
    }
}