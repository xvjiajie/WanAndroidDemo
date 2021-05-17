package com.xujiajie.wanandroid.ui.fragment

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ktx.immersionBar
import com.xujiajie.wanandroid.R
import com.xujiajie.wanandroid.adapter.HF1ActicleAdapter
import com.xujiajie.wanandroid.adapter.HomeFragment1Adapter
import com.xujiajie.wanandroid.adapter.banner.ImageAdapter
import com.xujiajie.wanandroid.base.BaseMFragment
import com.xujiajie.wanandroid.data.HomeFragment1HeadData
import com.xujiajie.wanandroid.data.HomeFragment1ListData
import com.xujiajie.wanandroid.databinding.FragmentHome1Binding
import com.xujiajie.wanandroid.databinding.HeadFragmentHome1Binding
import com.xujiajie.wanandroid.ext.dp2px
import com.xujiajie.wanandroid.ext.resourceId
import com.xujiajie.wanandroid.ui.WebActivity
import com.xujiajie.wanandroid.utils.Resource
import com.xujiajie.wanandroid.utils.ToastUtils
import com.xujiajie.wanandroid.vm.VMHomeFragment1
import com.youth.banner.indicator.CircleIndicator

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
    val mHeadAdapter: HF1ActicleAdapter by lazy {
        HF1ActicleAdapter()
    }
    val mHeadView :HeadFragmentHome1Binding by lazy {
        HeadFragmentHome1Binding.inflate(layoutInflater)
    }

    val mBannerHeight by lazy {
        mContext!!.dp2px(200f)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVMLive()

        mHeadView.recyclerView?.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mHeadAdapter
        }
        mHeadAdapter.setOnItemClickListener { _, _, position -> kotlin.run {
            mContext?.let { WebActivity.start(it,mHeadAdapter.data[position].link) }
        } }
        initRecyclerView()
        mViewModel.getHeadData()
        mViewModel.getData()
    }

    private fun initRecyclerView() {
        mAdapter.apply {
            loadMoreModule.isAutoLoadMore = true
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.mPageInfo.nextPage()
                mViewModel.getData()
            }
            setOnItemClickListener { _, _, position ->
                mContext?.let { WebActivity.start(it,this.data[position].link) }
            }
            addHeaderView(mHeadView.root)
        }
        mBinding.recyclerView?.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                private var totalDy = 0
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    totalDy += dy
                    if (totalDy < 0) {
                        totalDy = 0
                    }
                    if (totalDy < mBannerHeight) {
                        val alpha: Float = totalDy.toFloat() / mBannerHeight
                        mBinding.toolbar.alpha = alpha
                    } else {
                        mBinding.toolbar.alpha = 1f
                    }
                }
            })
        }
    }

    private fun initVMLive() {
        /*mViewModel?.apply {
            live.observe(this@HomeFragment1,{})
            headDataLive.observe(this@HomeFragment1,{})
        }*/

        mViewModel.live.observe(viewLifecycleOwner, {
            it.handler(object : Resource.OnHandleCallback<HomeFragment1ListData> {
                override fun onLoading(showMessage: String?) {
                    showProgressDialog(showMessage)
                }

                override fun onSuccess(data: HomeFragment1ListData) {
                    Log.d(TAG, "onSuccess: $data")

                    if (mViewModel.mPageInfo.isFirstPage) {
                        if (data.datas?.isNotEmpty() == true) {
                            mAdapter.setList(data.datas)
                        }
                    } else {
//                        mBinding.recyclerView.scrollToPosition(mAdapter.itemCount-mAdapter.headerLayoutCount)
                        mAdapter.addData(data.datas!!)
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
        mViewModel.headDataLive.observe(viewLifecycleOwner, { it ->
            it.handler(object : Resource.OnHandleCallback<HomeFragment1HeadData> {
                override fun onLoading(showMessage: String?) {
                    showProgressDialog(showMessage)
                }

                override fun onSuccess(data: HomeFragment1HeadData) {
                    Log.d(TAG, "onSuccess2: $data")
                    data?.let {
                        mHeadAdapter.setList(data.top)
                        val banners = arrayListOf<String>()
                        data.banner.forEach {it
                            banners.add(it.imagePath)
                        }
                        mContext?.let { it1 ->
                            mHeadView.banner?.setAdapter(ImageAdapter(banners))
                                .setIndicator(CircleIndicator(it1))
                                .setDelayTime(3000)
                                .setBannerRound2(8f)
                                .setIndicatorWidth(
                                    it1.dp2px(8F),
                                    it1.dp2px(8F)
                                ).setOnBannerListener { _, position ->
                                    WebActivity.start(
                                        it1,
                                        it.banner[position].url
                                    )
                                }
                                .start()
                        };
                    }
                }

                override fun onFailure(code: Int, msg: String?) {
                    ToastUtils.showToast(mContext, msg)
                }

                override fun onError(error: Throwable?) {
                    if (error != null) {
                        ToastUtils.showToast(mContext, error.message)
                    }
                }

                override fun onCompleted() {
                    dismissProgressDialog()
                }
            })
        })
    }

    override fun initImmersionBar() {
//        super.initImmersionBar()
        mContext?.let {
            ContextCompat.getColor(
                it, TypedValue().resourceId(
                    R.attr.colorPrimaryDark,
                    requireActivity().theme
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

