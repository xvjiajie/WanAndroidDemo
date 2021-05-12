package com.xujiajie.wanandroid

import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.xujiajie.wanandroid.utils.ToastUtils

/**
 * 创建日期 2020/10/31
 * 描述：
 */
class MyLifecycleObserver(private val registry : ActivityResultRegistry)
    : LifecycleObserver {
    lateinit var getContent : ActivityResultLauncher<String>

    fun onCreate(owner: LifecycleOwner) {
        getContent = registry.register("key", owner,ActivityResultContracts.GetContent(),
            ActivityResultCallback<Uri> {
                Log.d(Companion.TAG, "onCreate: ")
            })
    }

    fun selectImage() {
        Log.d(Companion.TAG, "selectImage: ")
    }

    companion object {
        private const val TAG = "MyLifecycleObserver"
    }
}
