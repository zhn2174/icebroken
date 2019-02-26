package com.icebroken.api;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Dev on 2017/1/24.
* 自定义 json转换器
        */
public class JsonConverterFactory extends Converter.Factory {
    private final  boolean needSC;
    public static JsonConverterFactory create() {
        return create(new Gson(),true);
    }

    public static JsonConverterFactory create(Gson gson,boolean needSC) {
        return new JsonConverterFactory(gson,needSC);


    }

    private final Gson gson;

    private JsonConverterFactory(Gson gson,boolean needSC) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
        this.needSC=needSC;
    }


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {

        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new JsonResponseBodyConverter<>(gson, adapter,type,needSC); //响应
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {

        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new JsonRequestBodyConverter<>(gson, adapter,needSC); //请求
    }


}
