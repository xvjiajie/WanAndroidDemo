package com.xujiajie.wanandroid.utils.glide

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

class MyImageLoader {
    class Builder internal constructor() {
        private var requestManager: RequestManager? = null
        private var mPath: Any? = null
        private var mError = 0
        private var mPlaceholders = 0
        private var mRadius = 0
        private var mImageView: ImageView? = null
        private var mDiskCacheStrategy: DiskCacheStrategy? = null
        private var isRoundImg = false
        private var asBitmap = false
        fun setRoundImg(roundImg: Boolean): Builder {
            isRoundImg = roundImg
            return this
        }

        fun with(context: Context?): Builder {
            requestManager = Glide.with(context!!)
            return this
        }

        fun with(activity: Activity?): Builder {
            requestManager = Glide.with(activity!!)
            return this
        }

        fun with(fragment: Fragment?): Builder {
            requestManager = Glide.with(fragment!!)
            return this
        }

        fun with(view: View?): Builder {
            requestManager = Glide.with(view!!)
            return this
        }

        fun with(fragmentActivity: FragmentActivity?): Builder {
            requestManager = Glide.with(fragmentActivity!!)
            return this
        }

        fun with(fragment: android.app.Fragment?): Builder {
            requestManager = Glide.with(fragment!!)
            return this
        }

        fun asBitmap(isBitmap: Boolean): Builder {
            asBitmap = isBitmap
            return this
        }

        fun load(path: Any?): Builder {
            mPath = path
            return this
        }

        fun setRadius(radius: Int): Builder {
            mRadius = radius
            return this
        }

        fun setDiskCacheStrategy(diskCacheStrategy: DiskCacheStrategy?): Builder {
            mDiskCacheStrategy = diskCacheStrategy
            return this
        }

        fun error(error: Int): Builder {
            mError = error
            return this
        }

        fun placeholders(placeholders: Int): Builder {
            mPlaceholders = placeholders
            return this
        }

        fun into(imageView: ImageView?): Builder {
            mImageView = imageView
            return this
        }

        fun show() {
            val options: RequestOptions
            if (isRoundImg) {
                options = RequestOptions.bitmapTransform(CircleCrop())
            } else {
                if (mRadius != 0) {
                    options = RequestOptions().fitCenter()
                    options.transform(CenterCropRoundCornerTransform(mRadius))
                } else {
                    options = RequestOptions()
                }
            }
            if (mPlaceholders != 0) {
                options.placeholder(mPlaceholders)
            }
            if (mError != 0) {
                options.error(mError)
            }
            if (mDiskCacheStrategy != null) {
                options.skipMemoryCache(true)
                options.diskCacheStrategy(mDiskCacheStrategy!!)
            }
            mImageView?.let {
                if (requestManager==null){
                    requestManager = Glide.with(it)
                }
                if (asBitmap) {
                    requestManager!!.asBitmap().load(mPath).apply(options)
                        .into(object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?
                            ) {
                                mImageView!!.setImageBitmap(resource)
                            }
                        })
                } else {
                    requestManager!!.load(mPath)
                        .apply(options).into(mImageView!!)
                }
            }
        }

    }

    companion object {
        private const val TAG = "MyImageLoader"
        val builder: Builder
            get() = Builder()
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}