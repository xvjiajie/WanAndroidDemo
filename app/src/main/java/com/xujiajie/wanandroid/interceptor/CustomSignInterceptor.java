/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xujiajie.wanandroid.interceptor;

import android.util.Log;

import com.xujiajie.wanandroid.utils.MyUtils;
import com.zhouyou.http.interceptor.BaseDynamicInterceptor;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>描述：对参数进行签名、添加token、时间戳处理的拦截器</p>
 * 主要功能说明：<br>
 * 因为参数签名没办法统一，签名的规则不一样，签名加密的方式也不同有MD5、BASE64等等，只提供自己能够扩展的能力。<br>
 * 作者： zhouyou<br>
 * 日期： 2017/5/4 15:21 <br>
 * 版本： v1.0<br>
 */
public class CustomSignInterceptor extends BaseDynamicInterceptor<CustomSignInterceptor> {
    private static final String TAG = "CustomSignInterceptor";

    @Override
    public TreeMap<String, String> dynamic(TreeMap<String, String> dynamicMap) {
        //dynamicMap:是原有的全局参数+局部参数
        if (isTimeStamp()) {//是否添加时间戳，因为你的字段key可能不是timestamp,这种动态的自己处理
            dynamicMap.put("time",System.currentTimeMillis()+"");
        }
        if (isAccessToken()){

        }

        Iterator iterator = dynamicMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            String dc;
            try {
                if (value instanceof String){
                    dc = java.net.URLDecoder.decode((String) value,   "utf-8");
                }else if (value instanceof Double){
                    dc = MyUtils.getDoubleString((Double) value);
                }else {
                    dc = value + "";
                }

                dynamicMap.put(key,dc);
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "dynamic: ",e );
                e.printStackTrace();
            }
        }
        if (isSign()) {//是否签名,因为你的字段key可能不是sign，这种动态的自己处理

        }

        return dynamicMap;//dynamicMap:是原有的全局参数+局部参数+新增的动态参数
    }




}
