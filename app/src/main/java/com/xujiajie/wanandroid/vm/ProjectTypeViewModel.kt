package com.xujiajie.wanandroid.vm

import android.app.Application
import androidx.lifecycle.rxLifeScope
import com.xujiajie.wanandroid.base.BaseLiveData
import com.xujiajie.wanandroid.base.BaseViewModel
import com.xujiajie.wanandroid.data.ProjectTypeData
import com.xujiajie.wanandroid.help.PageInfo
import com.xujiajie.wanandroid.utils.Resource
import rxhttp.RxHttp
import rxhttp.toResponse

/**
 * 创建日期 2020/11/26
 * 描述：
 */
class ProjectTypeViewModel(application: Application) : BaseViewModel(application) {

    val mPageInfo by lazy { PageInfo() }


    fun getData(live:BaseLiveData<Resource<ProjectTypeData>>,cid: Int): BaseLiveData<Resource<ProjectTypeData>> {
        rxLifeScope.launch({
            when (cid) {
                0 -> {
                    RxHttp.get("/article/listproject/${mPageInfo.page}/json")
                        .toResponse<ProjectTypeData>().await()?.apply {
                            live.update(Resource.success(this))
                        }
                }
                else -> {
                    RxHttp.get("/project/list/${mPageInfo.page}/json").add("cid", cid)
                        .toResponse<ProjectTypeData>().await()?.apply {
                            live.update(Resource.success(this))
                        }

                }
            }
        },{
            live.update(Resource.error(it))
        })
        return live;
    }
}