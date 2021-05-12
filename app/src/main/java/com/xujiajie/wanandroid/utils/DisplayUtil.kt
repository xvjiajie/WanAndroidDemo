package com.xujiajie.wanandroid.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

/**
 * dp、sp 转换为 px 的工具类
 *
 * Created by zxb 16/5/31.
 */
object DisplayUtil {
    // 将px值转换为dip或dp值，保证尺寸大小不变
    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    // 将dip或dp值转换为px值，保证尺寸大小不变
    fun dp2px(context: Context?, dipValue: Float): Int {
        val scale = context?.resources?.displayMetrics?.density
        return (dipValue * scale!! + 0.5f).toInt()
    }

    // 将px值转换为sp值，保证文字大小不变
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    // 将sp值转换为px值，保证文字大小不变
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    // 屏幕宽度（像素）
    fun getWindowWidth(context: Activity): Int {
        val metric = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metric)
        return metric.widthPixels
    }

    // 屏幕高度（像素）
    fun getWindowHeight(context: Activity): Int {
        val metric = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metric)
        return metric.heightPixels
    }
}

