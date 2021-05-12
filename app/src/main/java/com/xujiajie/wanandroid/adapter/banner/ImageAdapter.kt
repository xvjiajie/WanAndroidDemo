package com.xujiajie.wanandroid.adapter.banner

import android.view.ViewGroup
import android.widget.ImageView
import com.xujiajie.wanandroid.utils.glide.MyImageLoader
import com.youth.banner.adapter.BannerAdapter

/**
 * 自定义布局，图片
 */
class ImageAdapter<T>(mDatas: List<T>?) : BannerAdapter<T, ImageHolder>(mDatas) {
    //更新数据
    fun updateData(data: List<T>?) {
        //这里的代码自己发挥，比如如下的写法等等
        mDatas.clear()
        data?.let { mDatas.addAll(it) }
        notifyDataSetChanged()
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val imageView = ImageView(parent.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        return ImageHolder(imageView)
    }

    override fun onBindView(holder: ImageHolder, data: T, position: Int, size: Int) {
        MyImageLoader.builder.with(holder.imageView.context).into(holder.imageView).load(data)
            .show()
    }
}