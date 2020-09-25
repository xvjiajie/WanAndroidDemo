package com.zhouyou.http.utils.http;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface MyPutApiService {

    @FormUrlEncoded
    @PUT()
    Observable<ResponseBody> put(@Url String url, @FieldMap Map<String, String> maps);

    @PUT()
    Observable<ResponseBody> putBody(@Url String url, @Body Object object);

    @PUT()
    Observable<ResponseBody> putBody(@Url String url, @Body RequestBody body);

    @PUT()
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ResponseBody> putJson(@Url String url, @Body RequestBody jsonBody);
}
