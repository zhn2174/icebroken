package com.icebroken.api;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.mocuz.common.commonutils.LogUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
        * 自定义响应ResponseBody
        */
public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson mGson;//gson对象
    private final TypeAdapter<T> adapter;
    private  Type type;
    private  boolean needSC;//是否加密
    /**
     * 构造器
     */
    public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter,Type type,boolean needSC) {
        this.mGson = gson;
        this.adapter = adapter;

        this.type = type;
        this.needSC = needSC;
    }

    /**
     * 转换
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody responseBody) throws IOException {

        String response = responseBody.string();

//        String strResult = response.substring(1, response.length() - 1);
        String result=response.toString();
//        if (needSC){
//            result =DES.decrypt3DesNOIps(response.toString(), "");//解密
//        }
        LogUtils.loge("解密的服务器数据: " + result);
        LogUtils.loge("result==: " + result);

//        LogUtils.logd("解密的服务器数据: " + jsonObject.toString());
        T pageBean = mGson.fromJson(result, type);
        return (T) pageBean;


    }

}
