package com.xujiajie.wanandroid;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.tencent.mmkv.MMKV;
import com.xujiajie.wanandroid.help.UrlHelp;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.RxHttp;

/**
 * 创建日期 2020/12/3
 * 描述：
 */
public class MyReferrerReceiver extends BroadcastReceiver {
    private static String TAG = "MyReferrerReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        MMKV.initialize(context);
        String rawReferrer = intent.getStringExtra("referrer");

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("key", rawReferrer).apply();
        Log.d(TAG, "onReceive: " + rawReferrer);
        if (null == rawReferrer) {
            return;
        }else {
            MMKV.defaultMMKV().putString("referrer", rawReferrer);
        }
        Intent intent1 = new  Intent();
        intent1.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        //设置intent的动作为com.example.broadcast，可以任意定义
        intent1.setAction("com.xxx.broadcast");
        intent1.putExtra("referrer",rawReferrer);
        //发送无序广播
                /*intent.setComponent(new ComponentName(getPackageName(),
                        "com.xujiajie.wanandroid.MyReferrerReceiver"));*/
        intent1.setComponent(new ComponentName("ai.advance.liveness.demo",
                "ai.advance.liveness.demo.MyReferrerReceiver"));
        context.sendBroadcast(intent1);

//        RxHttp.postJson(UrlHelp.App.domain)

    }
}
