package com.mocuz.common.commonutils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mocuz.common.baseapp.BaseApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 */
@SuppressLint("DefaultLocale")
@TargetApi(Build.VERSION_CODES.DONUT)
public class ContentData {
    public static final String NetErroMsg = "服务器忙,请重试";
    public static final String TIMEOUTERR = "timeouterr";
    public static String link = "";
    public static Map<String, SoftReference<Bitmap>> superIndexImageCache = new HashMap<String, SoftReference<Bitmap>>(
            1);

    /****
     * 下载图片
     **/
    public static Bitmap getPicOneDown(String path) {
        URL m;
        InputStream input = null;
        byte[] imgdata = null;
        try {
            m = new URL(path);
            input = (InputStream) m.getContent();
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch = 0;
            int num = 0;
            while ((ch = input.read()) != -1) {
                bytestream.write(ch);
                num = num + ch;
            }
            imgdata = bytestream.toByteArray();
            bytestream.close();
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return BitmapFactory.decodeByteArray(imgdata, 0, imgdata.length);
    }

    /***
     * 四舍五入
     *
     * @param msg
     * @return
     */
    public static int siSheWuRu(double msg) {
        return (int) Math.round(msg);
    }

    /***
     * 程序应用版本
     */
    public static int myAppVerCode = 0;

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static void bmpIsNullDel(Bitmap btp, String fileName) {

        if (null == btp) {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();

            }
        }
    }

    public static final String baseShared = BaseApplication.getAppContext().getPackageName().replace("com.mocuz.", "").replace(".", "");
    /**
     * XM基本目录
     */
    public static final String SHARED_BASE = baseShared;
    /**
     * XML商品兑换之手机号
     */
    public static final String SHARED_SPDH_TELNUM = baseShared + "_spdhtelnum";

    /**
     * XML商品兑换之地址
     */
    public static final String SHARED_SPDH_ADRESS = baseShared + "_spdhadress";

    /**
     * XML天气缓冲
     */
    public static final String SHARED_SPDH_WATHERS = baseShared
            + "_spdhwathers";
    /**
     * XML论坛版块密码
     */
    public static final String SHARED_BBS_PASSWORD = baseShared
            + "_bbs_password_";
    /**
     * XML首页返回
     */
    public static final String SHARED_BBS_INDEX = baseShared + "index";
    /**
     * 客户端是否关闭用户名注册--0=开启注册 1=关闭注册
     */
    public static final String SHARED_Reg = baseShared + "_reg";
    /**
     * 梦游开关请求-- 0=关闭梦游 1=开启梦游
     */
    public static final String SHARED_MengYou = baseShared + "_mengyou";

    /**
     * 梦游开关请求-- 1不掉接口
     */
    public static final String SHARED_MengYou_Sun = baseShared + "_mengyousun";
    public static final long minMusicTime = 10000;
    public static final long maxMusicTime = 46000;
    public static final long minMusicTimeSS = 10;
    public static final long maxMusicTimeSS = 46;

    /***
     * 判断所选音乐是否符合标准
     *
     * @param nowTimes
     * @return true 不符合 false 符合
     */
    public static boolean checkMusicTime(String nowTimes) {
        if (TextUtils.isEmpty(nowTimes)) {
            return true;
        }
        if (Long.parseLong(nowTimes) < minMusicTime) {
            return true;
        }
        return false;
    }

    /**
     * 根据手机的分辨率从dp 的单位 转成为px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从px(像素) 的单位 转成为dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /***
     * 选择的联系人
     */
    public static String phoneNum = "";

    public static boolean iscomplete = false;

    public static Object getJson(String js, Class<?> classNames) {
        Gson gson = new Gson();
        return gson.fromJson(js, classNames);
    }

    public static String testdata = "";

    /****
     * 判断List是否为空
     *
     * @param dataList
     * @return true list为空或没有值；false list不为空，并有值
     */
    public static boolean isListNull(List<?> dataList) {
        if (null != dataList && dataList.size() > 0) {
            return false;
        } else {
            return true;
        }

    }

    public static String commonetishi = "首次安装就送3000魔币，可以兑换QB、话费,小伙伴们赶紧快来跟我一起赚钱吧，魔币地址http://url.cn/QIPsVN 别忘了填我为推荐人（我的id:#userId#）";

    /***
     * 判断当前Activity是否关闭
     * <p/>
     * true 关闭 false 没有关闭
     *
     * @return
     */
    public static boolean isActivityISNull(Activity context) {
        if (null != context && !context.isFinishing()) {
            return false;
        }
        return true;
    }

    /***
     * 判断是否存在SD卡 true：存在 false：不存在
     *
     * @return
     */
    public static boolean isSD() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static String uuid32len() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }



    /***
     * 获得APK下载的sd卡路径
     *
     * @param pid
     * @param name
     * @return
     */
    public static String getApkFileName(String pid, String name) {
        if (TextUtils.isEmpty(pid)) {
            return "";
        }
        if (TextUtils.isEmpty(name)) {
            return "";
        }
        return ContentData.BASE_APKS + "/" + pid + "_" + name + ".apk";
    }


    /**
     * SD卡--总目录
     */
    public static final String BASE_DIR = Environment
            .getExternalStorageDirectory() + "/" + baseShared;
    /**
     * SD卡--日志目录
     */
    public static final String BASE_LOG = BASE_DIR + "/logs";
    /**
     * SD卡--Main日志目录
     */
    public static final String BASE_MAIN_LOG = BASE_DIR + "/mainlogs";
    /**
     * SD卡--图片裁剪后的下载目录
     */
    public static final String BASE_CUT_PICS = BASE_DIR + "/cutpics";
    /**
     * SD卡--图片下载目录
     */
    public static final String BASE_PICS = BASE_DIR + "/pics";
    /**
     * SD卡--拍照
     */
    public static final String BASE_CAMER_PICS = BASE_DIR + "/cameropic";
    /**
     * SD卡--录音缓存文件
     */
    public static final String BASE_LUYINCASE = BASE_DIR + "/lunyincase";
    /**
     * SD卡--APK下载目录
     */
    public static final String BASE_APKS = BASE_DIR + "/apks";

    /**
     * 创建所有文件夹
     */
    public static void createMKDir() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File f = new File(BASE_DIR);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_LOG);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_MAIN_LOG);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_PICS);
            if (!f.exists()) {
                f.mkdirs();

            }
            f = new File(BASE_CUT_PICS);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_LUYINCASE);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_CAMER_PICS);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_APKS);
            if (!f.exists()) {
                f.mkdirs();
            }
        }

    }

    /***
     * 将px转换成dp
     */
    public static int px_TO_Dip(Context context, float pxValue) {
        return (int) (pxValue
                / context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /***
     * 将dp转换成xp
     */
    public static int Dp_To_XP(Context context, int paramFloat) {
        return (int) (paramFloat * (context.getResources().getDisplayMetrics().densityDpi / 160.0F));
    }

    /***
     * 检查版本 返回所有信息
     */
    public static String versonResultAll = "";
    /***
     * 升级包名称
     */
    public static String filename = "mymoney.apk";

    /***
     * 升级包路径
     */
    public static String path = Environment.getExternalStorageDirectory() + "/"
            + filename;

    /***
     * 如果存在升级包 就删除
     *
     * @param context
     */
    public static void checkApk(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory() + "/" + filename;
            // 如果存在升级包 就删除
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
        } else {
            try {
                path = context.getFilesDir().getPath() + "/" + filename;
                File file_apk = new File(path);
                if (file_apk.exists()) {
                    file_apk.delete();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    /***
     * 安装包
     *
     * @param ctx
     * @param file
     */
    public static void installApk(Context ctx, File file) {
        Intent intent = new Intent();

        intent.addFlags(268435456);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file), getMIMEType(file));
        ctx.startActivity(intent);
    }

    public static String getMIMEType(File file) {
        String type = "";
        String fName = file.getName();

        String end = fName
                .substring(fName.lastIndexOf(".") + 1, fName.length())
                .toLowerCase();

        if ((end.equals("m4a")) || (end.equals("mp3")) || (end.equals("mid"))
                || (end.equals("xmf")) || (end.equals("ogg"))
                || (end.equals("wav"))) {
            type = "audio";
        } else if ((end.equals("3gp")) || (end.equals("mp4"))) {
            type = "video";
        } else if ((end.equals("jpg")) || (end.equals("gif"))
                || (end.equals("png")) || (end.equals("jpeg"))
                || (end.equals("bmp"))) {
            type = "image";
        } else if (end.equals("apk")) {
            type = "application/vnd.android.package-archive";
        } else {
            type = "*";
        }

        if (!end.equals("apk")) {
            type = type + "/*";
        }
        return type;
    }

    /***
     * 数据加载通用提示语
     */
    public static String pdShowMsg = "数据加载中,请稍后... ...";

    /***
     * 获得MAC
     */
    public static String getLocalMacAddress2(Context context) {

        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();

        return info.getMacAddress();

    }

    /**
     * 判断输入是否是手机号码
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isMobileNumber(String phoneNumber) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

        Matcher m = p.matcher(phoneNumber);

        return m.matches();
    }

    public static String getSmallUrl(String file_Url) {
        String result = "";
        try {
            int lens = file_Url.length();
            result = file_Url.substring(0, file_Url.lastIndexOf(".")) + "_s"
                    + file_Url.substring(file_Url.lastIndexOf("."), lens);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public static int hours = 3600000;
    public static int mins = 60000;
    public static int mm = 1000;

    public static String showTime(String times) {
        if (null == times || "".equals(times)) {
            return "";
        }
        int timeNum = Integer.parseInt(times);
        int temp = 0;
        String result = "";
        if (timeNum >= hours) {
            temp = timeNum / hours;
            result = result + (temp < 10 ? ("0" + temp) : temp) + ":";
            timeNum = timeNum - hours * temp;
            if (timeNum >= mins) {
                temp = timeNum / mins;
                result = result + temp + ":";
                timeNum = timeNum - mins * temp;
                if (timeNum >= mm) {
                    temp = timeNum / mm;
                    result = result + (temp < 10 ? ("0" + temp) : temp);
                } else {
                    result = "00:00";
                }
            } else {
                if (timeNum >= mm) {
                    result = result + ":00:";
                    temp = timeNum / mm;
                    result = result + (temp < 10 ? ("0" + temp) : temp);
                } else {
                    result = "00:00";
                }
            }
        } else {
            if (timeNum >= mins) {
                temp = timeNum / mins;
                result = result + (temp < 10 ? ("0" + temp) : temp) + ":";
                timeNum = timeNum - mins * temp;
                if (timeNum >= mm) {
                    temp = timeNum / mm;
                    result = result + (temp < 10 ? ("0" + temp) : temp);
                } else {
                    result = result + "00";
                }
            } else {
                if (timeNum >= mm) {
                    result = "00:";
                    temp = timeNum / mm;
                    result = result + (temp < 10 ? ("0" + temp) : temp);
                } else {
                    result = "00:00";
                }
            }
        }
        return result;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sptopx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static String getTimeFormatValue(long time) {
        return MessageFormat.format(
                "{0,number,00}:{1,number,00}:{2,number,00}",
                time / 1000 / 60 / 60, time / 1000 / 60 % 60, time / 1000 % 60);
    }

    /***
     * 截取40个字节
     *
     * @param str
     * @return
     */
    public static String subStringByByte(String str) {
        int len = 40;
        String result = null;
        if (str != null) {
            byte[] a = str.getBytes();
            if (a.length <= len) {
                result = str;
            } else if (len > 0) {
                result = new String(a, 0, len);
                int length = result.length();
                if (str.charAt(length - 1) != result.charAt(length - 1)) {
                    if (length < 2) {
                        result = null;
                    } else {
                        result = result.substring(0, length - 1);
                    }
                }
            }
        }
        return result;
    }

    /***
     * 截取N个字节
     *
     * @param str
     * @param len 字节数字
     * @return
     */
    public static String subStringByByte(String str, int len) {
        String result = null;
        if (str != null) {
            byte[] a = str.getBytes();
            if (a.length <= len) {
                result = str;
            } else if (len > 0) {
                result = new String(a, 0, len);
                int length = result.length();
                if (str.charAt(length - 1) != result.charAt(length - 1)) {
                    if (length < 2) {
                        result = null;
                    } else {
                        result = result.substring(0, length - 1);
                    }
                }
            }
        }
        return result;
    }

    /***
     * 截取N个字符
     *
     * @return
     */
    public static String subString(String values, int nums) {
        try {
            if (values.length() > nums) {
                return values.substring(0, nums);
            }
            return values;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }





}
