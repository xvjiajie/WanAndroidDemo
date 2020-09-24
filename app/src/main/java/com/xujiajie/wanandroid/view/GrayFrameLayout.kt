package com.xujiajie.wanandroid.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * 创建日期 2020/4/5
 * 描述：灰色版实现
 */
class GrayFrameLayout(context: Context?, attrs: AttributeSet?) : FrameLayout(
    context!!, attrs
) {
    private val mPaint = Paint()
    override fun dispatchDraw(canvas: Canvas) {
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG)
        super.dispatchDraw(canvas)
        canvas.restore()
    }

    override fun draw(canvas: Canvas) {
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG)
        super.draw(canvas)
        canvas.restore()
    }

    init {
        val cm = ColorMatrix()
        cm.setSaturation(1f)
        mPaint.colorFilter = ColorMatrixColorFilter(cm)
    }
}