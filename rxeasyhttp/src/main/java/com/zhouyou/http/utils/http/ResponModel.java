package com.zhouyou.http.utils.http;

import java.io.Serializable;

/**
 * 这个类是泛型类，可根据后端的返回字段修改
 */
public class ResponModel<T> implements Serializable {
    public static final int RESULT_SUCCESS = 0;

    private T data;
    private int errorCode;
    private String errorMsg;

    public ResponModel() {
    }

    public ResponModel(T data) {
        this.data = data;
    }

    public ResponModel(T data, int errorCode, String errorMsg) {
        this.data = data;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return RESULT_SUCCESS == errorCode;
    }
}
