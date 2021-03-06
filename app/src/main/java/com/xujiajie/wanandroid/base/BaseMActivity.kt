package com.xujiajie.wanandroid.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 创建日期 2020/9/24
 * 描述：
 */
open abstract class BaseMActivity<VM : BaseViewModel, VDB : ViewDataBinding> :BaseDActivity<VDB>() {
    protected var mViewModel: VM? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        createViewModel()
        super.onCreate(savedInstanceState)
    }

    open fun createViewModel() {
        if (mViewModel == null) {
//            val modelClass: Class<*>
            val type: Type? = this.javaClass.getGenericSuperclass()
            val modelClass = if (type is ParameterizedType) {
                type.actualTypeArguments[0]
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                BaseViewModel::class.java
            }
            mViewModel = ViewModelProviders.of(this).get(modelClass as Class<VM>)
        }
    }
}