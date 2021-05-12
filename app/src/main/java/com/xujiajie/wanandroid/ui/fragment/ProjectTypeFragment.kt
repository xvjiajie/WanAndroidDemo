package com.xujiajie.wanandroid.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xujiajie.wanandroid.R
import com.xujiajie.wanandroid.adapter.ProjectTypeAdapter
import com.xujiajie.wanandroid.base.BaseLiveData
import com.xujiajie.wanandroid.base.BaseMFragment
import com.xujiajie.wanandroid.data.ProjectTypeData
import com.xujiajie.wanandroid.databinding.FragmentProjectTypeBinding
import com.xujiajie.wanandroid.utils.Resource
import com.xujiajie.wanandroid.vm.ProjectTypeViewModel
import kotlinx.coroutines.delay

/**
 * 创建日期 2020/11/26
 * 描述：
 */
class ProjectTypeFragment : BaseMFragment<ProjectTypeViewModel, FragmentProjectTypeBinding>() {
    companion object {
        private const val KEY_ID = "id"
        fun newInstance(id: Int): ProjectTypeFragment {

            val args = Bundle()
            args.putInt(KEY_ID, id)
            val fragment = ProjectTypeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val id by lazy {
        arguments?.getInt(KEY_ID)
    }

    override fun getContentLayout(): Int {
        return R.layout.fragment_project_type
    }

    private val mLiveData by lazy {
        BaseLiveData<Resource<ProjectTypeData>>()
    }

    private val mProjectTypeAdapter by lazy {
        ProjectTypeAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.recyclerView?.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mProjectTypeAdapter
        }
        mProjectTypeAdapter.apply {
            loadMoreModule.isAutoLoadMore = true
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.mPageInfo.nextPage()
                id?.let { mViewModel.getData(mLiveData,cid = it) }
            }
            setOnItemClickListener { _, _, position ->

            }
        }
        mLiveData.observe(viewLifecycleOwner,
            { t ->
                t?.handler(object : Resource.OnHandleCallback<ProjectTypeData> {
                    override fun onLoading(showMessage: String?) {
                        TODO("Not yet implemented")
                    }

                    override fun onSuccess(data: ProjectTypeData) {
                        if (mViewModel.mPageInfo.isFirstPage) {
                            if (data.datas!!.isNotEmpty()) {
                                mProjectTypeAdapter.setList(data.datas)
                            }
                        } else {
                            mProjectTypeAdapter.addData(data.datas!!)
                        }
                        if (data.isHasNext) {
                            mProjectTypeAdapter.loadMoreModule.loadMoreComplete()
                        } else {
                            mProjectTypeAdapter.loadMoreModule.loadMoreEnd()
                        }
                    }

                    override fun onFailure(code: Int, msg: String?) {
                        TODO("Not yet implemented")
                    }

                    override fun onError(error: Throwable?) {
                        TODO("Not yet implemented")
                    }

                    override fun onCompleted() {
                        dismissProgressDialog()
                    }
                })
            })

        id?.let {
            mViewModel.getData(mLiveData, cid = it)
        }
    }

    suspend fun fetchData(): String {
        delay(2000)
        return "content"
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: $id")
    }
}