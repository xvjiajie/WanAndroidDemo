package com.xujiajie.wanandroid.ui

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import com.xujiajie.wanandroid.R

/**
 * 创建日期 2020/5/9
 * 描述：
 */
class MyProgressDialog(context: Context?) : ProgressDialog(context) {
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_progress_dialog)
    }
    init {
        setCancelable(true)
        setCanceledOnTouchOutside(false)
    }
}