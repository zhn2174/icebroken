package com.icebroken.utils;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;

import java.io.File;
import java.math.BigDecimal;

/**
 * Synopsis     Glide缓存操作工具类
 * Author		Mosr
 * version		${VERSION}
 * Create 	    2017/5/4 21:08
 * Email  		intimatestranger@sina.cn
 */
public class GlideCacheUtil {
    private static GlideCacheUtil mInstance;

    private boolean mIsDiskClearEnd;
    private boolean mIsMemoryClearEnd;

    private ClearListener mClearListener;

    public GlideCacheUtil setmClearListener(ClearListener mClearListener) {
        this.mClearListener = mClearListener;
        return mInstance;
    }

    public static GlideCacheUtil getInstance() {
        if (mInstance == null)
            synchronized (GlideCacheUtil.class) {
                if (mInstance == null)
                    mInstance = new GlideCacheUtil();
            }
        return mInstance;
    }

    /**
     * 清除图片磁盘缓存
     */
    public GlideCacheUtil clearImageDiskCache(final Context context) {
        mIsDiskClearEnd = false;
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                        mIsDiskClearEnd = true;
                        if (mIsDiskClearEnd && mIsMemoryClearEnd && mClearListener!=null)
                            mClearListener.onClearFinish();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mInstance;
    }

    /**
     * 清除图片内存缓存
     */
    public GlideCacheUtil clearImageMemoryCache(Context context) {
        mIsMemoryClearEnd = false;
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
                mIsMemoryClearEnd = true;
                if (mIsDiskClearEnd && mIsMemoryClearEnd)
                    mClearListener.onClearFinish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mInstance;
    }

    /**
     * 清除图片所有缓存
     */
    public GlideCacheUtil clearImageAllCache(Context context) {
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
        String ImageExternalCatchDir = context.getExternalCacheDir() + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        deleteFolderFile(ImageExternalCatchDir, true);
        return mInstance;
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     *
     * @return size
     *
     * @throws Exception
     */
    private long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    private void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size size
     *
     * @return size
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
//        if (kiloByte < 1) {
//            return size + "Byte";
//        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    public interface ClearListener {
        void onClearFinish();
    }
}
