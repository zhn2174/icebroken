package com.mocuz.common.compressorutils;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUpload7NiuUtil {
    public static String MEDIA_SUB_URL = "http://pnl1g24kx.bkt.clouddn.com/";

    public interface OnUpLoadCompleteListener {
        abstract void onUpLoadComplete(String key);
    }

    /**
     * 上传图片到七牛
     *
     * @param filePath 要上传的图片路径
     * @param token    在七牛官网上注册的token
     */
    public static void uploadImageToQiniu(String filePath, String token, final OnUpLoadCompleteListener listener) {
        UploadManager uploadManager = new UploadManager();
        // 设置图片名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = "icon_" + sdf.format(new Date());
        uploadManager.put(filePath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
                listener.onUpLoadComplete(key);
            }
        }, null);
    }
}
