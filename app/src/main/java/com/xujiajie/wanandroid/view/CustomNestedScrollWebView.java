package com.xujiajie.wanandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * 创建日期 2020/12/21
 * 描述：
 */
public class CustomNestedScrollWebView extends WebView {
    public CustomNestedScrollWebView(Context context) {
        super(context);
    }

    public CustomNestedScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNestedScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomNestedScrollWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //根据手机屏幕重新计算高度
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }

}
