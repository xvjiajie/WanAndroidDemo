package com.xujiajie.wanandroid.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gyf.immersionbar.components.ImmersionFragment
import com.gyf.immersionbar.ktx.immersionBar
import com.xujiajie.wanandroid.R
import com.xujiajie.wanandroid.ext.resourceId
import com.xujiajie.wanandroid.ui.MyProgressDialog

/**
 * 创建日期 2020/9/24
 * 描述：
 */
open abstract class BaseFragment : ImmersionFragment(){
    val TAG = this.javaClass.simpleName
    protected var mContext: Context ?= null
    protected open fun finish() {
        activity?.let {
            it.finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext();
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getContentLayout(), container, false)
    }

    override fun initImmersionBar() {
        immersionBar {
            statusBarColor(
                TypedValue().resourceId(
                    R.attr.colorPrimary,
                    activity!!.theme
                )
            ).autoStatusBarDarkModeEnable(true, 0.1f)
        }
    }
    protected abstract fun getContentLayout(): Int

    private val mProgressDialog: ProgressDialog by lazy {
        MyProgressDialog(context)
    }
    protected open fun showProgressDialog(msg: String ?= "") {
//        mProgressDialog.setMessage(if (msg == null || msg.isEmpty()) "加载中..." else msg)
        mProgressDialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val lp = attributes
            lp.alpha = 0.9f // 透明度
            lp.dimAmount = 0.0f // 黑暗度
            lp.gravity = Gravity.CENTER
            attributes = lp
            if (!mProgressDialog.isShowing){
                mProgressDialog.show()
            }
        }
    }

    protected open fun dismissProgressDialog() {
        mProgressDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
    }
}