package com.xujiajie.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 创建日期 2020/9/24
 * 描述：
 */
open abstract class BaseDFragment<VDB : ViewDataBinding> :BaseFragment() {
    protected lateinit var mBinding: VDB
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater!!, getContentLayout(), container, false)
        mBinding?.setLifecycleOwner(this)
        return mBinding?.getRoot()
    }
}