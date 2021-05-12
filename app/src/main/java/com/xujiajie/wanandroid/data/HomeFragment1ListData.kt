package com.xujiajie.wanandroid.data

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.xujiajie.wanandroid.data.bean.HomeFragment1Bean
import java.io.Serializable
import java.util.*

/**
 * 创建日期 2020/9/25
 * 描述：
 */
/*data class HomeFragment1ListData() : Serializable {
    //{"offset":0,"over":false,"pageCount":463,"size":20,"total":9258}
    var curPage = 0
    var offset = 0
    var isOver = false
    var pageCount = 0
    var size = 0
    var total = 0
    var datas: List<HomeFragment1Bean>? = null
        get() = if (field == null) {
            ArrayList()
        } else field
    val isHasNext: Boolean
        get() = pageCount >= curPage

}*/

@JsonClass(generateAdapter = true)
data class HomeFragment1ListData(
    var curPage:Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: List<HomeFragment1Bean>? = null
):Serializable{
    val isHasNext: Boolean
        get() = pageCount >= curPage
}