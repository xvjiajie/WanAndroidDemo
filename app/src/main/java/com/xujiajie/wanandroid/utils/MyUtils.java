package com.xujiajie.wanandroid.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import androidx.core.app.NotificationManagerCompat;


import com.jakewharton.rxbinding2.view.RxView;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 创建日期 2019/11/19
 * 描述：简单方法
 */
public class MyUtils {

    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][0123456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return mobiles.matches(telRegex);
    }

    public static void viewClicks(View view, io.reactivex.functions.Consumer<Object> consumer) {
        RxView.clicks(view).throttleFirst(500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);
    }



    public static String getBase64ImgPath(String base64) {
        return "data:image/jpeg;base64," + base64;
    }


    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 设置状态栏文字颜色
     *
     * @param activity
     * @param dark
     */
    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    /**
     * 判断字符串是否不为空
     *
     * @param string
     * @return
     */
    public static boolean isNotEmpty(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        if (string == null || string.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 禁止Edittext弹出软件盘，光标依然正常显示。
     */
    public static void disableShowSoftInput(EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
            }
        }
    }


    /**
     * 如果是小数，保留两位，非小数，保留整数
     *
     * @param number
     */
    public static String getDoubleString(double number) {
        return getDoubleString(number,false);
    }

    /**
     * @param number
     * @param isNegative 是否可为负 true 可
     * @return
     */
    public static String getDoubleString(double number, boolean isNegative) {
        if (!isNegative && number < 0) {
            number = 0;
        }
        String numberStr;
        if (((int) number * 1000) == (int) (number * 1000)) {
            //如果是一个整数
            numberStr = String.valueOf((int) number);
        } else {
            DecimalFormat df = new DecimalFormat("######0.00");
            numberStr = df.format(number);
        }
        return numberStr;
    }

    public static String getDoubleString(String number) {
        if (number.isEmpty()) {
            return "0";
        }
        return getDoubleString(Double.valueOf(number));
//        return number;
    }
    public static String getDoubleString(String number,boolean isNegative) {
        if (number.isEmpty()) {
            return "0";
        }
        return getDoubleString(Double.valueOf(number),isNegative);
//        return number;
    }

    public static String getNumber(int number) {
        return number > 99 ? "99+" : String.valueOf(number);
    }

    /**
     * 规范数量必须是整数
     *
     * @param barcodeDesc
     * @return
     */
    public static String onPatternNumber(String barcodeDesc) {
        Pattern p;
        p = Pattern.compile("[0-9]");//在这里，编译 成一个正则。
        Matcher m;
        m = p.matcher(barcodeDesc);//获得匹配
        String res = "";

        while (m.find()) { //注意这里，是while不是if
            String xxx = m.group();
            res += xxx;
        }
        if (res.isEmpty()) {
            return barcodeDesc.replaceAll("-", "").replaceAll("\\+", "").replaceAll(".", "");
        }
        return res;
    }

    /**
     * 通知是否打开
     *
     * @param context
     * @return true打开
     */
    public static boolean isNotificationEnabled(Context context) {
        boolean isOpened = false;
        try {
            isOpened = NotificationManagerCompat.from(context).areNotificationsEnabled();
        } catch (Exception e) {
            e.printStackTrace();
            isOpened = false;
        }
        return isOpened;

    }
}
