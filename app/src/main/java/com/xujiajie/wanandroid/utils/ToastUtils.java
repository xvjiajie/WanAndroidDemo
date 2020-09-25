package com.xujiajie.wanandroid.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.xujiajie.wanandroid.R;


/**
 * Toast工具
 *  Created by wanghongchuang
 *  on 2016/8/25.
 *  email:844285775@qq.com
 */
public class ToastUtils {

    private static Toast mToast;

    private static Handler mhandler = new Handler(Looper.getMainLooper());
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public static void showToast(Context context, @StringRes int strId) {
        showToast(context, context.getString(strId), false);
    }

    public static void showToast(Context context, String text) {
        showToast(context, text, true);
    }

    public static void showToast(Context context, int strId, boolean lengthLong) {
        showToast(context, context.getString(strId), lengthLong);
    }

    public static void showNotEmptyToast(Context context, int keyStrId) {
        showNotEmptyToast(context, context.getString(keyStrId));
    }

    public static void showNotEmptyToast(Context context, String keyText) {
        showToast(context, keyText + context.getString(R.string.cannot_be_empty));
    }

    public static void showToast(Context context, String text, boolean lengthLong) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        try {
            mhandler.removeCallbacks(r);
            if (null != mToast) {
                mToast.setText(text);
            } else {
                mToast = Toast.makeText(context, null, Toast.LENGTH_LONG);
                mToast.setText(text);
            }
            if (text.length() > 5) {
                lengthLong = true;
            }
            mhandler.postDelayed(r, lengthLong ? 1500 : 1000);

       /* if (Looper.myLooper() == Looper.getMainLooper()) {
        }*/
            mToast.show();
        }catch (Exception e){

        }

    }

    /**
     * 取消
     */
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

}
