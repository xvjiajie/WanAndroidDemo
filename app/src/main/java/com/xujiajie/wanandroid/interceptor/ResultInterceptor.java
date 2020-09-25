package com.xujiajie.wanandroid.interceptor;

import android.util.Log;

import com.google.gson.Gson;
import com.xujiajie.wanandroid.base.BaseBean;
import com.zhouyou.http.interceptor.BaseExpiredInterceptor;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/9.
 * 返回数据拦截器
 */

public class ResultInterceptor extends BaseExpiredInterceptor {
    private static final String TAG = "ResultInterceptor";

    @Override
    public boolean isResponseExpired(Response response, String bodyString) {
        Log.d(TAG, "isResponseExpired: " + bodyString);

        CodeBean resultBean = new Gson().fromJson(bodyString,CodeBean.class);

        return false;
    }

    @Override
    public Response responseExpired(Chain chain, String bodyString) {

        Log.d(TAG, "responseExpired: ");
        return null;
    }


    private class CodeBean extends BaseBean {
        private String code = "-1";
        private String msg;
    }

}
