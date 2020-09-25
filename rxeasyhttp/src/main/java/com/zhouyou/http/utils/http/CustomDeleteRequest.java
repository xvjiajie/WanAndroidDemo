package com.zhouyou.http.utils.http;

import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.request.DeleteRequest;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class CustomDeleteRequest extends DeleteRequest {


    public CustomDeleteRequest(String url) {
        super(url);
    }

    @Override
    public <T> Disposable execute(CallBack<T> callBack) {
        return super.execute(new CallBackProxy<MyResult<T>, T>(callBack) {
        });
    }

    private MyDeleteApiService apiManager;
    @Override
    protected Observable<ResponseBody> generateRequest() {
        apiManager = retrofit.create(MyDeleteApiService.class);
        if (this.requestBody != null) { //自定义的请求体
            return apiManager.deleteBody(url, this.requestBody);
        } else if (this.json != null) {//Json
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), this.json);
            return apiManager.deleteJson(url, body);
        }  else if (this.object != null) {//自定义的请求object
            return apiManager.deleteBody(url, object);
        } else if (this.string != null) {//文本内容
            RequestBody body = RequestBody.create(mediaType, this.string);
            return apiManager.deleteBody(url, body);
        } else {
            return apiManager.delete(url, params.urlParamsMap);
        }
    }
}
