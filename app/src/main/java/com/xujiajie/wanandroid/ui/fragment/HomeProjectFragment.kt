package com.xujiajie.wanandroid.ui.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.angcyo.tablayout.ViewPagerDelegate
import com.gyf.immersionbar.ktx.immersionBar
import com.xujiajie.wanandroid.R
import com.xujiajie.wanandroid.adapter.HomeProjectFragmentAdapter
import com.xujiajie.wanandroid.base.BaseMFragment
import com.xujiajie.wanandroid.data.bean.HomeProjectTabBean
import com.xujiajie.wanandroid.databinding.FragmentHomeProjectBinding
import com.xujiajie.wanandroid.ext.inflate
import com.xujiajie.wanandroid.ext.resourceId
import com.xujiajie.wanandroid.utils.Resource
import com.xujiajie.wanandroid.utils.ToastUtils
import com.xujiajie.wanandroid.vm.VMHomeProjectFragment
import kotlin.math.absoluteValue

/**
 * 创建日期 2020/9/24
 * 描述：项目
 */
class HomeProjectFragment : BaseMFragment<VMHomeProjectFragment, FragmentHomeProjectBinding>() {
    override fun getContentLayout(): Int {
        return R.layout.fragment_home_project
    }

    private val mFragmentList = mutableListOf<Fragment>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        mViewModel.getTabData()
        mBinding.viewPage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                mBinding.tlMainProject.onPageSelected(position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                mBinding.tlMainProject.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                mBinding.tlMainProject.onPageScrollStateChanged(state)
            }
        })
        mBinding.tlMainProject.setupViewPager(object : ViewPagerDelegate{
            override fun onGetCurrentItem(): Int {
                return mBinding.viewPage.currentItem
            }

            override fun onSetCurrentItem(fromIndex: Int, toIndex: Int) {
                mBinding.viewPage.setCurrentItem(toIndex, (toIndex - fromIndex).absoluteValue <= 1)
            }
        })
        mViewModel.apply {
            mLiveData.observe(viewLifecycleOwner, {
                it.handler(object : Resource.OnHandleCallback<List<HomeProjectTabBean>> {
                    override fun onLoading(showMessage: String?) {
                        showProgressDialog(showMessage)
                    }

                    override fun onSuccess(data: List<HomeProjectTabBean>) {
                        mBinding.tlMainProject.removeAllViews()
                        data.apply {
                            forEach { bean ->
                                mBinding.tlMainProject?.let { tl ->
                                    tl.inflate(R.layout.layout_project_tab, false).apply {
                                        findViewById<TextView>(R.id.tvTabLayoutTitle)?.text =
                                            bean.name
                                        tl.addView(this)
                                    }
                                }
                                mFragmentList.add(ProjectTypeFragment.newInstance(bean.id))
                            }
                            mBinding.viewPage.apply {
                                adapter = HomeProjectFragmentAdapter(this@HomeProjectFragment,mFragmentList)
                            }
                        }
                    }

                    override fun onFailure(code: Int, msg: String?) {
                        ToastUtils.showToast(mContext, msg)
                    }

                    override fun onError(error: Throwable?) {
                        ToastUtils.showToast(mContext, error?.message)
                    }

                    override fun onCompleted() {
                        dismissProgressDialog()
                    }
                })
            })
            getTabData()
        }
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        mContext?.let {
            ContextCompat.getColor(
                it, TypedValue().resourceId(
                    R.attr.colorPrimary,
                    requireActivity().theme
                )
            )
        }?.let {
            mBinding?.tb?.toolbar?.setBackgroundColor(
                it
            )
        }
        immersionBar {
            titleBar(mBinding?.tb?.toolbar)
//            statusBarView(mBinding?.tb?.toolbar)
        }
    }
}