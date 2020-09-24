package com.zhouyou.http.func;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONObject;

import java.lang.reflect.Type;
/**
 * 创建日期 2020/4/13
 * 描述：处理String 类型后台返回null
 */
public class StringConverter implements JsonSerializer<String>,
        JsonDeserializer<String> {
    private static String TAG = "StringConverter";
    public JsonElement serialize(String src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        Log.e(TAG, "serialize: "+src+"\n");
        if (typeOfSrc instanceof JSONObject){
            Log.e(TAG, "serialize: "+"ssssssssssssssssssssssssssssss");
        }
        if (src == null) {
            return new JsonPrimitive("");
        } else {
            return new JsonPrimitive(src.toString());
        }
    }



    public String deserialize(JsonElement json, Type typeOfT,
                              JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonObject()){

        }
        return json.getAsJsonPrimitive().getAsString();
    }

}

