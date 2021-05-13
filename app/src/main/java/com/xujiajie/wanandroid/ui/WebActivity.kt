package com.xujiajie.wanandroid.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.core.content.ContextCompat
import com.gyf.immersionbar.ktx.immersionBar
import com.just.agentweb.*
import com.xujiajie.wanandroid.R
import com.xujiajie.wanandroid.base.BaseActivity
import com.xujiajie.wanandroid.base.BaseDActivity
import com.xujiajie.wanandroid.data.bean.HomeFragment1BannerBean
import com.xujiajie.wanandroid.databinding.ActivityWebBinding
import com.xujiajie.wanandroid.ext.color
import com.xujiajie.wanandroid.ext.resourceId
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseDActivity<ActivityWebBinding>() {
    private var agentWeb: AgentWeb? = null
    var url:String?=null
    companion object {
        fun start(context: Context, url: String) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("url",url)
            context.startActivity(intent)
        }
    }
    override fun getContentLayout(): Int {
        return R.layout.activity_web
    }

    override fun initImmersionBar() {
//        super.initImmersionBar()
        mContext?.let {
            ContextCompat.getColor(
                it, TypedValue().resourceId(
                    R.attr.colorPrimaryDark,
                    theme
                )
            )
        }?.let {
            mBinding?.tb?.toolbar?.setBackgroundColor(
                it
            )
        }
        immersionBar {
            titleBar(mBinding?.tb?.toolbar)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(mBinding!!.root)
        intent?.apply {
            url = getStringExtra("url")
        }
        mBinding?.tb?.toolbar?.apply {
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            setNavigationOnClickListener { finish() }
        }
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(webContainer, ViewGroup.LayoutParams(-1, -1))
            .useDefaultIndicator(color(R.color.colorAccent), 2)
            .interceptUnkownUrl()
            .setAgentWebWebSettings(AgentWebSettingsImpl.getInstance())
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
//            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
            .setWebChromeClient(object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
//            setTitle(title)
                    super.onReceivedTitle(view, title)
                }

                override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                    return super.onConsoleMessage(consoleMessage)
                }
            })
            .setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
//            view?.loadUrl(customJs(url))
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)

                }
            })
            .createAgentWeb()
            .ready()
            .get()
        agentWeb?.webCreator?.webView?.run {
            overScrollMode = WebView.OVER_SCROLL_NEVER
            settings.run {
                javaScriptCanOpenWindowsAutomatically = false
                loadsImagesAutomatically = true
                useWideViewPort = true
                loadWithOverviewMode = true
//                textZoom = SettingsStore.getWebTextZoom()
            }
        }

        agentWeb?.urlLoader?.loadUrl(url)
    }

}