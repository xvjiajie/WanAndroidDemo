package com.xujiajie.wanandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


/**
 * 创建日期 2020/11/26
 * 描述：
 */
class HomeProjectFragmentAdapter : FragmentStateAdapter {
    private var mData: List<Fragment>

    constructor(
        fragmentActivity: FragmentActivity,
        mData: List<Fragment>
    ) : super(fragmentActivity) {
        this.mData = mData
    }

    constructor(fragment: Fragment, mData: List<Fragment>) : super(fragment) {
        this.mData = mData
    }

    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        mData: List<Fragment>
    ) : super(fragmentManager, lifecycle) {
        this.mData = mData
    }
    override fun getItemCount(): Int {
        return mData?.size
    }

    override fun createFragment(position: Int): Fragment {
        return mData[position]
    }


}