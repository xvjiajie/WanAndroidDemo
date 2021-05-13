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
import com.xujiajie.wanandroid.databinding.FragmentHomeSetUpBinding
import com.xujiajie.wanandroid.ext.inflate
import com.xujiajie.wanandroid.ext.resourceId
import com.xujiajie.wanandroid.utils.Resource
import com.xujiajie.wanandroid.utils.ToastUtils
import com.xujiajie.wanandroid.vm.HomeSetUpFragmentViewModel
import kotlin.math.absoluteValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeSetUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeSetUpFragment : BaseMFragment<HomeSetUpFragmentViewModel,FragmentHomeSetUpBinding>() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.fragment_home_set_up
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
        mBinding.tlMainProject.setupViewPager(object : ViewPagerDelegate {
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
                                mFragmentList.add(ProjectTypeFragment.newInstance(bean.id,1))
                            }
                            mBinding.viewPage.apply {
                                adapter = HomeProjectFragmentAdapter(this@HomeSetUpFragment,mFragmentList)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeSetUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HomeSetUpFragment().apply {
                /*arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }*/
            }
    }
}