package com.icebroken.api;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import com.icebroken.utils.StringUtils;
import com.mocuz.common.commonutils.LogUtils;
import org.json.JSONObject;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * <p>
 * 自定义请求RequestBody
 */
public class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private  boolean needSC;

    /**
     * 构造器
     */

    public JsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter,boolean needSC) {
        this.gson = gson;
        this.adapter = adapter;
        this.needSC = needSC;
    }


    @Override
    public RequestBody convert(T value) throws IOException {
        //加密
        String postBody = null;
//        if (needSC){
//            try {
//                postBody = DES.encryptDesNoIps(getStatisticalData(value.toString()));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }else {
            postBody=getStatisticalData(value.toString());
//        }

        //对象转化成json
        LogUtils.logd("request中传递的json数据： : " + getStatisticalData(value.toString()));
//        LogUtils.logd("转化后的数据: " + postBody);
        return RequestBody.create(MEDIA_TYPE, postBody);
    }

    private String getStatisticalData(String str) {
        JSONObject data = null;
        try {
            if (StringUtils.isEmpty(str)) {
                data = new JSONObject();
            } else {
                data = new JSONObject(str);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.toString();
    }


}
