package com.xujiajie.wanandroid.base

import android.os.Looper
import androidx.lifecycle.MutableLiveData

/**
 * 创建日期 2020/4/14
 * 描述：
 */
class BaseLiveData<T> : MutableLiveData<T>() {
    fun update(value: T) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.setValue(value)
        } else {
            postValue(value)
        }
    }
}