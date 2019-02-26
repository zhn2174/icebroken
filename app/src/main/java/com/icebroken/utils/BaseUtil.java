package com.icebroken.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.icebroken.BuildConfig;
import com.icebroken.R;
import com.icebroken.app.AppApplication;
import com.mocuz.common.commonutils.ImageLoaderUtils;
import com.mocuz.common.commonutils.ToastUitl;
import com.mocuz.common.compressorutils.Compressor;
import com.umeng.message.PushAgent;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dev on 2017/3/8.
 */

public class BaseUtil {
    public static final String baseShared = AppApplication.getAppContext().getPackageName().replace("com.mocuz.", "").replace(".", "");
    /* 默认主题色 */
    public static final String baseColor = "#78BD50";

    /* 动画时间 */
    public static int TIMES = 200;
    /* 位移动画 出现和消失 */
    public static TranslateAnimation transAnim_show, transAnim_dismiss;
    /* 渐变动画 出现和消失 */
    public static AlphaAnimation alphaAnim_show, alphaAnim_dismiss;
    /* 旋转动画 */
    public static RotateAnimation rotateAnim_anti, rotateAnim_wise;

    public static final String OPEN_PUSH = "OPEN_PUSH";
    public static final String OPEN_LAUD_PUSH = "OPEN_LAUD_PUSH";

    public static ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            ImageLoaderUtils.display(context, imageView, path);
        }
    };
    private static Compressor mCompressor;
    private static ProgressDialog mProgressDialog;


    public static boolean getDebug() {
        return BuildConfig.LOG_DEBUG;
    }

    public static String bitmaptoString(String path) {

        // 将Bitmap转换成字符串
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);

        byte[] bytes = bStream.toByteArray();

        string = Base64.encodeToString(bytes, Base64.DEFAULT);

        return "data:image/png;base64," + string;
    }

    /**
     * 初始化弹框动画
     */
    public static void initAnimation() {
        transAnim_show = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        transAnim_show.setDuration(TIMES);

        transAnim_dismiss = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        transAnim_dismiss.setDuration(TIMES);

        alphaAnim_show = new AlphaAnimation(0.1f, 1.0f);
        alphaAnim_show.setDuration(TIMES);

        alphaAnim_dismiss = new AlphaAnimation(1.0f, 0.1f);
        alphaAnim_dismiss.setDuration(TIMES);

        rotateAnim_anti = new RotateAnimation(-180f, 0f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim_anti.setDuration(TIMES);

        rotateAnim_wise = new RotateAnimation(-180f, 0f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim_wise.setDuration(TIMES);
    }

    public static SharedPreferences getSp() {
        return AppApplication.getAppContext().getSharedPreferences(baseShared,
                Context.MODE_PRIVATE);
    }

    /***
     * 获得友盟
     */
    public static String getUmengDeToken() {
//        String device_token = getSp().getString(SHARED_UMENGDEVIECETOKEN, "");
//        if (StringUtils.isEmpty(device_token)) {
//            device_token = UmengRegistrar.getRegistrationId();
//            device_token = PushAgent.getInstance(AppApplication.getAppContext()).getRegistrationId();
//            if (!StringUtils.isEmpty(device_token)) {
//                getSp().edit().putString(SHARED_UMENGDEVIECETOKEN, device_token).commit();
//            }
//        }
        return PushAgent.getInstance(AppApplication.getAppContext()).getRegistrationId();
    }

    /***
     * 获得推送开关
     *
     * @return
     */
    public static boolean isPushenable() {
        return getSp().getBoolean(OPEN_PUSH, true);
    }

    /***
     * 判断主题是否为白色
     *
     * @return true为白色主题 false 自选色主题
     */
    public static boolean isWhite() {
//        String col = getSp().getString(SHARED_ZHUTICOLOR, baseColor);
//        col = "white";
//        if ("white".equals(col)) {
//            return_icon true;
//        } else {
        return false;
//        }
    }

    /**
     * 系统状态栏着色
     *
     * @param mActivity
     * @param StartColor
     * @param EndColor
     */
    public static Drawable drawSystemBar(@NonNull Activity mActivity, @NonNull String StartColor, @NonNull String EndColor) {
        GradientDrawable mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[]{Color.parseColor(StartColor),
                        Color.parseColor(EndColor)});
        mGradientDrawable.setGradientCenter(0, 0);
        mGradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        mGradientDrawable.setShape(GradientDrawable.RECTANGLE);
        mGradientDrawable.setGradientRadius(mActivity.getWindowManager().getDefaultDisplay().getWidth() / 2);
        return mGradientDrawable;
    }

    /**
     * 左→右渐变色
     *
     * @param StartColor
     * @param EndColor
     * @param mRadius
     * @return
     */
    public static Drawable leftToRightDrawable(@NonNull String StartColor, @NonNull String EndColor, float mRadius) {
        GradientDrawable mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{Color.parseColor(StartColor),
                        Color.parseColor(EndColor)});
        mGradientDrawable.setCornerRadius(mRadius);
        return mGradientDrawable;
    }


    /***
     * 对TextView设置不同状态时其文字颜色。
     *
     * @param normal 默认颜色
     * @param unable 选中颜色
     *
     * @return
     */
    public static ColorStateList createColorStateList(int normal, int unable) {
        int[] colors = new int[]{unable, normal};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_checked};
        states[1] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }


    // 首页广告图片尺寸
    public static int BBS_Index_Width = 1080;
    public static int BBS_Index_Height = 480;

    /***
     * 获得首页广告的高度
     *
     * @return
     */
    public static int getIndex_HH(int ww) {
        return ww * BBS_Index_Height / BBS_Index_Width;
    }

    // ListView中广告图片尺寸
    public static int List_GG_Width = 700;
    public static int List_GG_Height = 390;

    /***
     * 获得listview中广告的高度
     *
     * @return
     */
    public static int getListGG_HH(int ww) {
        return ww * List_GG_Height / List_GG_Width;
    }

    // listview中帖子多个图片的比例
    public static int List_Img_Width = 312;
    public static int List_Img_Height = 250;

    /***
     * 获得listview中帖子多个图片的高度
     *
     * @return
     */
    public static int getListImg_HH(int ww) {
        return ww * List_Img_Height / List_Img_Width;
    }

    // listview中帖子一张图片的比例
    public static int List_Img_One_Width = 280;
    public static int List_Img_One_Height = 184;

    /***
     * 获得listview中帖子一张图片的宽度
     *
     * @return
     */
    public static int getListImgOne_WW(int ww) {
        return ww * List_Img_One_Width / List_Img_One_Height;
    }

    /***
     * 获得listview中帖子一张图片的高度
     *
     * @return
     */
    public static int getListImgOne_HH(int ww) {
        return ww * List_Img_One_Height / List_Img_One_Width;
    }

    // 活动图片尺寸
    public static int HuoDong_Width = 700;
    public static int HuoDong_Height = 340;

    /***
     * 获得活动图片尺寸的高度
     *
     * @return
     */
    public static int getHuoDong_HH(int ww) {
        return ww * HuoDong_Height / HuoDong_Width;
    }

    /**
     * 设置边框,和背景
     */
    public static GradientDrawable createShape(int strokeWidth, int roundRadius, int strokeColor, int fillColor) {
        GradientDrawable gd = new GradientDrawable();// 创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);

        return gd;
    }


    /**
     * 对TextView设置不同状态时其文字颜色。
     */
    public static ColorStateList createColorStateList(int normal, int pressed,
                                                      int focused, int unable) {
        int[] colors = new int[]{pressed, focused, normal, focused, unable,
                normal, pressed};
        int[][] states = new int[7][];
        states[0] = new int[]{android.R.attr.state_pressed,
                android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled,
                android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_checked};
        states[4] = new int[]{android.R.attr.state_checked};
        states[5] = new int[]{};
        states[6] = new int[]{android.R.attr.state_checked};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;

    }

    /**
     * 对View设置不同状态时其背景颜色。
     */
    public static StateListDrawable addStateDrawable(Context context, GradientDrawable idNormal,
                                                     GradientDrawable idPressed, GradientDrawable idFocused) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normal = idNormal;
        Drawable pressed = idPressed;
        Drawable focus = idFocused;
        // 注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        // 所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
        sd.addState(new int[]{android.R.attr.state_enabled,
                android.R.attr.state_focused}, focus);
        sd.addState(new int[]{android.R.attr.state_pressed,
                android.R.attr.state_enabled}, pressed);
        sd.addState(new int[]{android.R.attr.state_focused}, focus);
        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);
        sd.addState(new int[]{android.R.attr.state_checked}, idFocused);
        sd.addState(new int[]{android.R.attr.state_enabled}, normal);
        sd.addState(new int[]{}, normal);
        return sd;
    }


    /**
     * 将多个参数合成一个以按照特定分隔符分割的参数
     *
     * @param spriter 参数之间的分隔符, eg: _&
     * @return eg: a_&b
     */
    public static String getUnderLindAndParam(String spriter, String... values) {
        if (values == null || values.length == 0)
            return null;

        StringBuffer sb = new StringBuffer();
        int len = values.length;
        String str;
        for (int i = 0, end = len - 1; i < len; i++) {
            str = values[i];
            if (str != null && str.length() != 0)
                sb.append(str);

            if (i != end)
                sb.append(spriter);
        }

        return sb.toString();
    }

    public static void singleCheck(final View v) {
        v.setClickable(false);
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setClickable(true);
            }
        }, 1000);
    }


    /**
     * 判断字符是否是中文
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }


    /**
     * 获取手机的mac地址
     *
     * @return
     */
    public static String getMacAddress() {
        @SuppressLint("WifiManagerLeak") WifiManager wifi = (WifiManager) AppApplication.getAppContext()
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (null == info || StringUtils.isEmpty(info.getMacAddress())) {
            return "";
        }
        return info.getMacAddress();
    }

    /**
     * 获取手机的IMEI
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) AppApplication.getAppContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (null == tm || StringUtils.isEmpty(tm.getDeviceId())) {
            return "";
        }
        return tm.getDeviceId();
    }

    public static float AutoPX(int px, Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return px * dm.heightPixels / 1920;
    }


    /***
     * 判断List是否为空
     */
    public static boolean isList(List<?> s) {
        if (s == null) {
            return true;
        }
        if (s.size() <= 0) {
            return true;
        }
        return false;
    }

    /***
     * 判断String是否为空
     */

    public static boolean isEmpty(String str) {
        return str == null || str.length() <= 0;
    }

    /**
     * 显示软键盘
     */
    public static void showInput(final Context con) {
        InputMethodManager imm = (InputMethodManager) con
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /*
     * 隐藏软键盘
     * */
    public static void hideInput(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);
        }
    }

    /*
     * 隐藏软键盘
     * */
    public static void hideInput(Context mContext) {
        if (null == mContext)
            return;

        try {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(((Activity) mContext).getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        long lt = new Long(s);
        lt = lt * 1000;
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 跳转图片选择器
     *
     * @param mActivity
     * @param title             //标题
     * @param maxNum            //最大选择数量
     * @param requestCode
     * @param needCrop//是否需要裁剪  当 multiSelect false生效
     * @param multiSelect//是否多选
     */
//    public static void skipImageSelector(Activity mActivity, String title, int maxNum, int requestCode, boolean needCrop, boolean multiSelect) {
//
//        ImgSelConfig config = new ImgSelConfig.Builder(loader)
//                // 是否多选
//                .multiSelect(true)
//                //主题色
//                .themeColor(BaseUtil.getEndColor_int())
//                // 使用沉浸式状态栏
//                .titleBgDrawable(ContextCompat.getDrawable(mActivity, R.color.white))
//                //是否需要裁剪
//                .needCrop(needCrop)
//                //标题
//                .title(title)
//                //是否需要相机
//                .needCamera(true)
//                //是否多选
//                .multiSelect(multiSelect)
//                // 最大选择图片数量
//                .maxNum(maxNum)
//                .build();
//        ImgSelActivity.startActivity(mActivity, config, requestCode);
//    }

//    public static void skipImageSelectorFragment(android.support.v4.app.Fragment mFragment, String title, int maxNum, int requestCode, boolean needCrop, boolean multiSelect) {
//        ImgSelConfig config = new ImgSelConfig.Builder(loader)
//                // 是否多选
//                .multiSelect(true)
//                //主题色
//                .themeColor(BaseUtil.getEndColor_int())
//                // 使用沉浸式状态栏
//                .titleBgDrawable(ContextCompat.getDrawable(mFragment.getActivity(), R.color.white))
//                //是否需要裁剪
//                .needCrop(needCrop)
//                //标题
//                .title(title)
//                //是否需要相机
//                .needCamera(true)
//                //是否多选
//                .multiSelect(multiSelect)
//                // 最大选择图片数量
//                .maxNum(maxNum)
//                .build();
//        ImgSelActivity.startActivity1(mFragment, config, requestCode);
//    }

    /**
     * 压缩图片
     *
     * @param url
     * @return url
     */
    public static String compressFile(Context mContext, String url) {
        if (null == mCompressor)
            mCompressor = new Compressor.Builder(mContext.getApplicationContext())
                    .setMaxWidth(800)
                    .setMaxHeight(1333)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .build();

        File mFile = mCompressor.compressToFile(new File(url));
        if (null == mFile && mContext instanceof Activity) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUitl.showError("图片格式不支持!");
                }
            });
            return "";
        }
        return mFile.getAbsolutePath();
    }

    /**
     * 获取当前应用的版本名称
     */
    public static String getVerName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return "";
    }

    /**
     * 压缩图片
     *
     * @param url
     * @return url
     */
    public static String compressFile(Context mContext, String url, int maxWidth, int maxHeight) {
        if (null == mCompressor)
            mCompressor = new Compressor.Builder(mContext)
                    .setMaxWidth(maxWidth == 0 ? 800 : maxWidth)
                    .setMaxHeight(maxHeight == 0 ? 1333 : maxHeight)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .build();
        File mFile = null;
        try {
            mFile = mCompressor.compressToFile(new File(url));
        } catch (OutOfMemoryError e) {

        }
        if (null == mFile && mContext instanceof Activity) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUitl.showError("图片格式不支持!");
                }
            });
            return "";
        }
        return mFile.getAbsolutePath();
    }

    /**
     * 压缩图片
     *
     * @param file
     * @return url
     */
    public static String compressFile(Context mContext, File file) {
        if (null == mCompressor)
            mCompressor = new Compressor.Builder(mContext)
                    .setMaxWidth(800)
                    .setMaxHeight(1333)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .build();

        File mFile = mCompressor.compressToFile(file);
        if (null == mFile && mContext instanceof Activity) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUitl.showError("图片格式不支持!");
                }
            });
            return "";
        }
        return mFile.getAbsolutePath();
    }

    /**
     * 压缩图片
     *
     * @param mContext
     * @param url
     * @param t
     * @param <T>      (String表示返回Url File表示返回文件)
     * @return
     */
    public static <T> T compressFile(Context mContext, String url, T t) {
        if (null == mCompressor)
            mCompressor = new Compressor.Builder(mContext)
                    .setMaxWidth(800)
                    .setMaxHeight(1333)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .build();

        File mFile = mCompressor.compressToFile(new File(url));
        T retureT = null;
        if (null == mFile && mContext instanceof Activity) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUitl.showError("图片格式不支持!");
                }
            });
            return retureT;
        }
        if (t instanceof String)
            retureT = (T) mFile.getAbsolutePath();
        if (t instanceof File)
            retureT = (T) mFile;
        return retureT;
    }

    /**
     * 将uri转化为BitMap
     *
     * @param uri
     * @param mContext
     * @return
     */
    public static Bitmap decodeUriAsBitmap(Uri uri, Context mContext) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(mContext.getContentResolver()
                    .openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public static String endcodeBase64File(String path) {
        byte[] data = null;
        try {
            InputStream in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(data, 0);
    }

    public static String getBase64Str(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
        byte[] b = stream.toByteArray();
        try {
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    /**
     * 匹配号码是否是11为数字
     *
     * @param phoneNum
     * @return 不是11位数字，返回false
     */
    public static boolean checkPhoneNum(String phoneNum) {
        String format = "^\\d{11}$";
        Pattern p = Pattern.compile(format);
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }


    public static boolean IsTelephone(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        if (!b) {
            ToastUitl.showShort("请输入正确的手机号码");
        }
        return b;
    }

    public static void doubleClick(final View view) {
        view.setEnabled(false);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, 1000);
    }

    /**
     * 显示进度条
     *
     * @param msg
     */
    public static void showProgress(Context mContext, String msg) {
        if (null == mProgressDialog && null != mContext) {
            mProgressDialog = new ProgressDialog(mContext, AlertDialog.THEME_HOLO_LIGHT);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        if (null != mProgressDialog && !mProgressDialog.isShowing())
            mProgressDialog.show();
        if (null != mProgressDialog)
            mProgressDialog.setMessage(msg);
    }

    /**
     * 显示进度条
     *
     * @param msg
     */
    public static void showProgress(Context mContext, String msg, boolean CanceledOnTouch) {
        if (null == mProgressDialog && null != mContext) {
            mProgressDialog = new ProgressDialog(mContext, AlertDialog.THEME_HOLO_LIGHT);
            mProgressDialog.setCanceledOnTouchOutside(CanceledOnTouch);
        }
        if (null != mProgressDialog && !mProgressDialog.isShowing())
            mProgressDialog.show();
        if (null != mProgressDialog)
            mProgressDialog.setMessage(msg);
    }

    /**
     * 关闭进度条
     */
    public static void dismissProgress() {
        if (null != mProgressDialog && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        mProgressDialog = null;
    }

    public static boolean isFinished(Context mContext) {
        if (null == mContext)
            return true;
        return ((Activity) mContext).isFinishing();
    }

    /**
     * 跳转图片选择器
     *
     * @param mActivity
     * @param title             //标题
     * @param maxNum            //最大选择数量
     * @param requestCode
     * @param needCrop//是否需要裁剪  当 multiSelect false生效
     * @param multiSelect//是否多选
     */
    public static void skipImageSelector(Activity mActivity, String title, int maxNum, int requestCode, boolean needCrop, boolean multiSelect) {

        ImgSelConfig config = new ImgSelConfig.Builder(loader)
                // 是否多选
                .multiSelect(true)
//                //主题色
//                .themeColor(mActivity)
                // 使用沉浸式状态栏
                .titleBgDrawable(ContextCompat.getDrawable(mActivity, R.color.white))
                //是否需要裁剪
                .needCrop(needCrop)
                //标题
                .title(title)
                //是否需要相机
                .needCamera(true)
                //是否多选
                .multiSelect(multiSelect)
                // 最大选择图片数量
                .maxNum(maxNum)
                .build();
        ImgSelActivity.startActivity(mActivity, config, requestCode);
    }

    public static int getColor(Context context, int res) {
        Resources r = context.getResources();
        return r.getColor(res);
    }

    public static float getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }


    public static int px(float dipValue) {
        Resources r = Resources.getSystem();
        final float scale = r.getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    //获取显示版本
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //获取版本信息
    public static int getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static StateListDrawable getRoundSelectorDrawable(int alpha, int color, int radir) {
        Drawable pressDrawable = getRoundDrawalbe(alpha, color, radir);
        Drawable normalDrawable = getRoundDrawalbe(color, radir);
        return getStateListDrawable(pressDrawable, normalDrawable);
    }

    //获取带透明度的圆角矩形
    public static Drawable getRoundDrawalbe(int alpha, int color, int radir) {
        int normalColor = Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
        Drawable normalDrawable = getRoundDrawalbe(normalColor, radir);
        return normalDrawable;
    }


    //根据颜色获取圆角矩形
    public static Drawable getRoundDrawalbe(int color, int radir) {
        radir = px(radir);
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{color, color});
        drawable.setCornerRadius(radir);
        return drawable;
    }

    public static StateListDrawable getStateListDrawable(Drawable pressDrawable, Drawable normalDrawable) {
        int pressed = android.R.attr.state_pressed;
        int select = android.R.attr.state_selected;
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{pressed}, pressDrawable);
        drawable.addState(new int[]{select}, pressDrawable);
        drawable.addState(new int[]{}, normalDrawable);
        return drawable;
    }

    public static StateListDrawable getSelectDrawable(int color) {
        int select = android.R.attr.state_selected;
        StateListDrawable drawable = new StateListDrawable();

        GradientDrawable drawable2 = new GradientDrawable();
        drawable2.setShape(GradientDrawable.OVAL);
        drawable2.setColor(color);

        drawable.addState(new int[]{select}, drawable2);
        drawable.addState(new int[]{}, null);

        return drawable;
    }
}
