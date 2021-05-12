package com.xujiajie.wanandroid.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * 创建日期 2019/10/21
 * 描述：
 */
open abstract class BaseViewModel(application: Application) : AndroidViewModel(application) ,
    LifecycleObserver {
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
    /**
     * 运行在UI线程的协程 viewModelScope 已经实现了在onCleared取消协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        block()
    }
}