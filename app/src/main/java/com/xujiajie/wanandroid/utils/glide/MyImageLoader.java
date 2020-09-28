package com.xujiajie.wanandroid.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xujiajie.wanandroid.utils.DisplayUtil;

public class MyImageLoader {
    private static String TAG = "MyImageLoader";

    public MyImageLoader() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder<T extends Context> {
        private T mContext;
        private Object mPath;
        private int mError;
        private int mPlaceholders;
        private int mRadius = 0;
        private ImageView mImageView;
        private DiskCacheStrategy mDiskCacheStrategy;
        private boolean isRoundImg;
        private boolean asBitmap;

        private Builder Builder(T t) {
            mContext = t;
            return this;
        }

        public Builder setRoundImg(boolean roundImg) {
            isRoundImg = roundImg;
            return this;
        }
        public Builder with(T context) {
            mContext = context;
            return this;
        }
        public Builder asBitmap(boolean isBitmap) {
            asBitmap = isBitmap;
            return this;
        }

        public Builder load(Object path) {
            mPath = path;
            return this;
        }

        public Builder setRadius(int radius) {
            mRadius = radius;
            return this;
        }

        public Builder setDiskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
            mDiskCacheStrategy = diskCacheStrategy;
            return this;
        }

        public Builder error(int error) {
            mError = error;
            return this;
        }

        public Builder placeholders(int placeholders) {
            mPlaceholders = placeholders;
            return this;
        }

        public Builder into(ImageView imageView) {
            mImageView = imageView;
            return this;
        }

        public void show() {

            RequestManager requestManager = Glide.with(mContext);
            RequestOptions options;
            if (isRoundImg){
                options = RequestOptions.bitmapTransform(new CircleCrop());
            }else {
                if (mRadius != 0){
                    options = new RequestOptions().fitCenter();
                    options.transform(new CenterCropRoundCornerTransform(DisplayUtil.dp2px(mContext,mRadius)));
                }else {
                    options = new RequestOptions();
                }

            }
            if (mPlaceholders!= 0){
                options.placeholder(mPlaceholders);
            }

            if (mError != 0){
                options.error(mError);
            }
            if (mDiskCacheStrategy!=null){
                options.skipMemoryCache(true);
                options.diskCacheStrategy(mDiskCacheStrategy);
            }
            if (mImageView != null) {
                if (asBitmap){
                    requestManager.asBitmap().load(mPath).apply(options).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            mImageView.setImageBitmap(resource);
                        }
                    });
                }else {
                    requestManager.load(mPath)
                            .apply(options).into(mImageView);
                }

            }
        }

    }


}
