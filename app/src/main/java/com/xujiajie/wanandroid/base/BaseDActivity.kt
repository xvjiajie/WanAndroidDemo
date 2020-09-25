package com.xujiajie.wanandroid.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 创建日期 2020/9/24
 * 描述：
 */
open abstract class BaseDActivity<VDB : ViewDataBinding> :BaseActivity() {
    protected var mBinding: VDB? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getContentLayout())
        mBinding?.lifecycleOwner = this
        initImmersionBar()
        initView(savedInstanceState)
    }
}