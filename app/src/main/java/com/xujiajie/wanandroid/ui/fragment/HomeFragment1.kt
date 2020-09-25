package com.xujiajie.wanandroid.ui.fragment

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ktx.immersionBar
import com.xujiajie.wanandroid.HomeFragment1Bean
import com.xujiajie.wanandroid.R
import com.xujiajie.wanandroid.adapter.HomeFragment1Adapter
import com.xujiajie.wanandroid.base.BaseMFragment
import com.xujiajie.wanandroid.data.HomeFragment1ListData1
import com.xujiajie.wanandroid.databinding.FragmentHome1Binding
import com.xujiajie.wanandroid.ext.resourceId
import com.xujiajie.wanandroid.utils.ToastUtils
import com.xujiajie.wanandroid.vm.VMHomeFragment1
import com.zhouyou.http.utils.http.Resource

/**
 * 创建日期 2020/9/24
 * 描述：
 */
class HomeFragment1 : BaseMFragment<VMHomeFragment1, FragmentHome1Binding>() {
    override fun getContentLayout(): Int {
        return R.layout.fragment_home_1
    }

    val mAdapter: HomeFragment1Adapter by lazy {
        HomeFragment1Adapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.live.observe(this, { it ->
            it.handler(object : Resource.OnHandleCallback<HomeFragment1ListData1> {
                override fun onLoading(showMessage: String?) {

                }

                override fun onSuccess(data: HomeFragment1ListData1) {
                    Log.d(TAG, "onSuccess: $data")
                    val i = data.datas.map {
                        if (it.apkLink.isNotEmpty()) {
                            Log.d(TAG, "onSuccess: "+it.apkLink)
                        }
                    }

                    if (mViewModel.mPageInfo.isFirstPage) {
                        if (data.datas.isNotEmpty()) {
                            mAdapter.setList(data.datas)
                        }
                    } else {
                        mAdapter.addData(data.datas)
                    }
                    if (data.isHasNext) {
                        mAdapter.loadMoreModule.loadMoreComplete()
                    } else {
                        mAdapter.loadMoreModule.loadMoreEnd()
                    }
                }

                override fun onFailure(code: Int, msg: String?) {

                }

                override fun onError(error: Throwable?) {
                    ToastUtils.showToast(mContext, error?.message)
                }

                override fun onCompleted() {
                    dismissProgressDialog()
                }

            })
        })

        mAdapter.apply {
            loadMoreModule.isAutoLoadMore = true
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.mPageInfo.nextPage()
                mViewModel.getData()
            }
            setOnItemClickListener { adapter, view, position ->

            }
        }
        mBinding.recyclerView?.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
        mViewModel.getData()
    }

    override fun initImmersionBar() {
//        super.initImmersionBar()
        mContext?.let {
            ContextCompat.getColor(
                it, TypedValue().resourceId(
                    R.attr.colorPrimaryDark,
                    activity!!.theme
                )
            )
        }?.let {
            mBinding?.toolbar?.setBackgroundColor(
                it
            )
        }
        immersionBar {
            titleBar(mBinding?.toolbar)
        }
    }

}

