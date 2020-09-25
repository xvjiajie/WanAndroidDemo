package com.xujiajie.wanandroid.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.xujiajie.wanandroid.utils.ToastUtils
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 创建日期 2020/9/24
 * 描述：
 */
open abstract class BaseMFragment<VM : BaseViewModel, VDB : ViewDataBinding>:BaseDFragment<VDB>(){
    protected lateinit var mViewModel: VM
    private fun isViewModel() = ::mViewModel.isInitialized
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewModel()
    }

    open fun createViewModel() {
        if (!isViewModel()) {
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