package com.xujiajie.wanandroid.adapter.banner

import android.text.Html
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.youth.banner.adapter.BannerAdapter

/**
 * 自定义布局，图片
 */
class StringAdapter : BannerAdapter<CharSequence, StringHolder> {
    private var textSize = 0f
    private var textColor = 0

    constructor(datas: List<CharSequence>?, textSize: Float, textColor: Int) : super(datas) {
        this.textSize = textSize
        this.textColor = textColor
    }

    constructor(mDatas: List<CharSequence>?) : super(mDatas) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
    }

    //更新数据
    fun updateData(data: List<CharSequence>?) {
        //这里的代码自己发挥，比如如下的写法等等
        mDatas.clear()
        data?.let { mDatas.addAll(it) }
        notifyDataSetChanged()
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): StringHolder {
        val imageView = TextView(parent.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        imageView.ellipsize = TextUtils.TruncateAt.END
        imageView.maxLines = 1
        imageView.gravity = Gravity.CENTER_VERTICAL
        return StringHolder(imageView)
    }

    override fun onBindView(holder: StringHolder, data: CharSequence, position: Int, size: Int) {
        if (data is String) {
            holder.mTextView.text = Html.fromHtml(data)
        } else {
            holder.mTextView.text = data
        }
        if (textSize != 0f) {
            holder.mTextView.textSize = textSize
        }
        if (textColor != 0) {
            holder.mTextView.setTextColor(textColor)
        }
    }
}