package com.xujiajie.wanandroid.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * 创建日期 2019/10/21
 * 描述：
 */
abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val TAG = this.javaClass.simpleName

    //这个是为了退出页面，取消请求的
    protected var mContext: Context = application
    open val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.let {
            it.dispose()
        }
    }

}