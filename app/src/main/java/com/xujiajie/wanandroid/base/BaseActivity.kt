package com.xujiajie.wanandroid.base

import android.app.ProgressDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.xujiajie.wanandroid.R
import com.xujiajie.wanandroid.ext.getAppTheme
import com.xujiajie.wanandroid.ext.resourceId
import com.xujiajie.wanandroid.ui.MyProgressDialog
import com.xujiajie.wanandroid.view.GrayFrameLayout

/**
 * 创建日期 2020/9/24
 * 描述：
 */
open abstract class BaseActivity : AppCompatActivity() {
    val TAG: String = this.javaClass.simpleName
    protected abstract fun getContentLayout(): Int
    protected abstract fun initView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(getAppTheme())
        super.onCreate(savedInstanceState)
        mContext = this

    }

    open fun initImmersionBar() {
        immersionBar {
            statusBarColor(
                TypedValue().resourceId(
                    R.attr.colorPrimary,
                    theme
                )
            ).autoStatusBarDarkModeEnable(true, 0.1f)
        }
    }
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        if ("FrameLayout" == name) {
            val count = attrs.attributeCount
            for (i in 0 until count) {
                val attributeName = attrs.getAttributeName(i)
                val attributeValue = attrs.getAttributeValue(i)
                if (attributeName == "id") {
                    val id = attributeValue.substring(1).toInt()
                    val idVal = resources?.getResourceName(id)
                    if ("android:id/content" == idVal) {
                        return GrayFrameLayout(context, attrs)
                    }
                }
            }
        }
        return super.onCreateView(name, context!!, attrs)
    }

    override fun finish() {
        closeKeyboard()
        super.finish()
    }


    open fun closeKeyboard() {
        val view = window.peekDecorView()
        if (view != null) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun getResources(): Resources? {
        val res = super.getResources()
        val config = res.configuration
        config.fontScale = 1.0f //1 设置正常字体大小的倍数
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }

    protected open fun showProgressDialog(msg: String = "") {
        mProgressDialog.setMessage(if (msg == null || msg.isEmpty()) "加载中..." else msg)
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


    private val mProgressDialog: ProgressDialog by lazy {
        MyProgressDialog(this)
    }
    private var mToolbar: Toolbar? = null
    private var mTvTitle: TextView? = null
    protected var mContext: Context? = null

    open fun setToolBar(toolbar: Toolbar, title: TextView) {
        mToolbar = toolbar
        mTvTitle = title
        setSupportActionBar(mToolbar)
        ImmersionBar.with(this).titleBar(mToolbar)
            .init()
        //设置actionBar的标题是否显示，对应ActionBar.DISPLAY_SHOW_TITLE。
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        setBackArrow()
    }

    protected open fun setTitleString(titles: String?) {
        mTvTitle?.let {
            it.text = titles
            it.visibility = View.VISIBLE
        }
    }

    protected open fun setTitleString(titles: CharSequence?) {
        mTvTitle?.let {
            it.text = titles
            it.visibility = View.VISIBLE
        }
    }

    /**
     * 设置左上角back按钮
     */
    open fun setBackArrow() {
        setBackArrow(R.drawable.ic_baseline_arrow_back_24)
    }

    /**
     * 设置左上角back按钮
     */
    open fun setBackArrow(drawable: Int) {
        val upArrow = resources!!.getDrawable(drawable)
        //给ToolBar设置左侧的图标
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
        // 给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //设置返回按钮的点击事件
        setNavigationOnClickListener()
    }

    protected open fun setNavigationOnClickListener() {
        if (mToolbar == null) {
            return
        }
        mToolbar!!.setNavigationOnClickListener { MyLeftClick(false) }
    }

    protected open fun MyLeftClick(b: Boolean) {
        finish()
    }

}