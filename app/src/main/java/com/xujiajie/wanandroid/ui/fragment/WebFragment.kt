package com.xujiajie.wanandroid.ui.fragment

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import com.just.agentweb.*
import com.xujiajie.wanandroid.R
import com.xujiajie.wanandroid.base.BaseMFragment
import com.xujiajie.wanandroid.databinding.FragmentHome2Binding
import com.xujiajie.wanandroid.vm.VMHomeFragment1


/**
 * 创建日期 2020/9/24
 * 描述：
 */
class WebFragment : BaseMFragment<VMHomeFragment1, FragmentHome2Binding>() {
    private var agentWeb: AgentWeb? = null
    override fun getContentLayout(): Int {
        return R.layout.fragment_home_2
    }///project/tree/json

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.web?.loadData("加载中...", "text/html", "UTF-8");
        mBinding.web.setWebViewClient(object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                mBinding.web.isNestedScrollingEnabled = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                mBinding.web.visibility = View.VISIBLE

            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                /*Toast.makeText(
                    this@MainActivity,
                    "shouldOverrideUrlLoading $url",
                    Toast.LENGTH_SHORT
                ).show()*/
                return false // false 显示frameset, true 不显示Frameset
            }
            /*@Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mBinding.xwv.measure(0, 0);//重新测量
                ViewGroup.LayoutParams params = mBinding.xwv.getLayoutParams();
                params.width = getResources().getDisplayMetrics().widthPixels;
                params.height = mBinding.xwv.getHeight() - mBinding.nsv.getHeight();
                mBinding.xwv.setLayoutParams(params);
            }*/
        });
//        mBinding.nsv.isNestedScrollingEnabled = true
       /* agentWeb = mContext?.let {
            AgentWeb.with(this)
                .setAgentWebParent(mBinding.webContainer, ViewGroup.LayoutParams(-1, -1))
                .useDefaultIndicator(it.color(R.color.colorAccent), 2)
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
        }
        agentWeb?.webCreator?.webView?.run {
            overScrollMode = WebView.OVER_SCROLL_NEVER
            settings.run {
                javaScriptCanOpenWindowsAutomatically = false
                loadsImagesAutomatically = true
                useWideViewPort = true
                loadWithOverviewMode = true
//                textZoom = SettingsStore.getWebTextZoom()
            }
        }*/

        Log.d(TAG, "onViewCreated: ${Uri.decode("http:/\\/tapi.loanunion.in\\/h5\\/indexPage")} " +
                "${Uri.parse("http:/\\/tapi.loanunion.in\\/h5\\/indexPage")} " +
                "${Uri.encode("http:/\\/tapi.loanunion.in\\/h5\\/indexPage")}")

        mBinding.web?.loadUrl("http://tapi.loanunion.in/h5/indexPage")

    }
}