package com.mocuz.common.basebean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * des:封装服务器返回数据
 * Created by xsf
 * on 2016.09.9:47
 */
public class BaseRespose<T> implements Serializable {
    public int code;
    public String message;
    public T result;

    public T getData() {
        return result;
    }

    public void setData(T result) {
        this.result = result;
    }

    public boolean success() {
        return code==200;
    }

    public String getErrmsg() {
        return message;
    }


    @Override
    public String toString() {
        return "BaseRespose{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
