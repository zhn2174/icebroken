package com.yuyh.library.imgsel;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;

import com.yuyh.library.imgsel.utils.FileUtils;

import java.io.Serializable;

/**
 * @author yuyh.
 * @date 2016/8/5.
 */
public class ImgSelConfig implements Parcelable{

    /**
     * 是否需要裁剪
     */
    public boolean needCrop;

    /**
     * 是否多选
     */
    public boolean multiSelect;

    /**
     * 最多选择图片数
     */
    public int maxNum = 9;

    /**
     * 第一个item是否显示相机
     */
    public boolean needCamera;

    public int statusBarColor = -1;

    /**
     * 返回图标资源
     */
    public int backResId = -1;

    /**
     * 标题
     */
    public String title;

    /**
     * 标题颜色
     */
    public int titleColor;

    /**
     * titlebar背景色
     */
    public int titleBgColor;

    /**
     * 确定按钮文字颜色
     */
    public int btnTextColor;

    /**
     * 确定按钮背景色
     */
    public int btnBgColor;

    /**
     * 拍照存储路径
     */
    public String filePath;

    /**
     * 自定义图片加载器
     */
    public ImageLoader loader;
    /**
     * 状态栏背景
     */
    public Drawable titleBgDrawable;
    /**
     * 状态栏背景
     */
    public int themeColor;


    /**
     * 裁剪输出大小
     */
    public int aspectX = 1;
    public int aspectY = 1;
    public int outputX = 500;
    public int outputY = 500;

    public ImgSelConfig(Builder builder) {
        this.needCrop = builder.needCrop;
        this.multiSelect = builder.multiSelect;
        this.maxNum = builder.maxNum;
        this.needCamera = builder.needCamera;
        this.statusBarColor = builder.statusBarColor;
        this.backResId = builder.backResId;
        this.title = builder.title;
        this.titleBgColor = builder.titleBgColor;
        this.titleColor = builder.titleColor;
        this.btnBgColor = builder.btnBgColor;
        this.btnTextColor = builder.btnTextColor;
        this.filePath = builder.filePath;
        this.titleBgDrawable = builder.titleBgDrawable;
        this.loader = builder.loader;
        this.aspectX = builder.aspectX;
        this.aspectY = builder.aspectY;
        this.outputX = builder.outputX;
        this.outputY = builder.outputY;
        this.themeColor=builder.themeColor;
    }

    public static class Builder implements Serializable {

        private boolean needCrop = false;
        private boolean multiSelect = true;
        private int maxNum = 9;
        private boolean needCamera = true;
        public int statusBarColor = -1;
        private int backResId = -1;
        private String title = "图片";
        private int titleColor;
        private int titleBgColor;
        private int btnTextColor;
        private int btnBgColor;
        private String filePath;
        private ImageLoader loader;
        private Drawable titleBgDrawable;
        private int themeColor;

        private int aspectX = 1;
        private int aspectY = 1;
        private int outputX = 400;
        private int outputY = 400;

        public Builder(ImageLoader loader) {
            this.loader = loader;

            if (FileUtils.isSdCardAvailable())
                filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Camera";
            else
                filePath = Environment.getRootDirectory().getAbsolutePath() + "/Camera";

//            titleBgColor = Color.BLACK;
//            titleColor = Color.WHITE;
//
//            btnBgColor = Color.TRANSPARENT;
//            btnTextColor = Color.WHITE;

            FileUtils.createDir(filePath);
        }

        public Builder needCrop(boolean needCrop) {
            this.needCrop = needCrop;
            return this;
        }


        public Builder titleBgDrawable(Drawable titleBgDrawable) {
            this.titleBgDrawable = titleBgDrawable;
            return this;
        }

        public Builder multiSelect(boolean multiSelect) {

            this.multiSelect = multiSelect;
            return this;
        }

        public Builder maxNum(int maxNum) {
            this.maxNum = maxNum;
            return this;
        }

        public Builder needCamera(boolean needCamera) {
            this.needCamera = needCamera;
            return this;
        }

        public Builder statusBarColor(int statusBarColor) {
            this.statusBarColor = statusBarColor;
            return this;
        }

        public Builder backResId(int backResId) {
            this.backResId = backResId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder themeColor(int themeColor) {
            this.themeColor = themeColor;
            return this;
        }


        public Builder titleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder titleBgColor(int titleBgColor) {
            this.titleBgColor = titleBgColor;
            return this;
        }

        public Builder btnTextColor(int btnTextColor) {
            this.btnTextColor = btnTextColor;
            return this;
        }

        public Builder btnBgColor(int btnBgColor) {
            this.btnBgColor = btnBgColor;
            return this;
        }

        private Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder cropSize(int aspectX, int aspectY, int outputX, int outputY) {
            this.aspectX = aspectX;
            this.aspectY = aspectY;
            this.outputX = outputX;
            this.outputY = outputY;
            return this;
        }

        public ImgSelConfig build() {
            return new ImgSelConfig(this);
        }
    }

    protected ImgSelConfig(Parcel in) {
        needCrop = in.readByte() != 0;
        multiSelect = in.readByte() != 0;
        maxNum = in.readInt();
        needCamera = in.readByte() != 0;
        statusBarColor = in.readInt();
        backResId = in.readInt();
        title = in.readString();
        titleColor = in.readInt();
        titleBgColor = in.readInt();
        btnTextColor = in.readInt();
        btnBgColor = in.readInt();
        filePath = in.readString();
        aspectX = in.readInt();
        aspectY = in.readInt();
        outputX = in.readInt();
        outputY = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (needCrop ? 1 : 0));
        dest.writeByte((byte) (multiSelect ? 1 : 0));
        dest.writeInt(maxNum);
        dest.writeByte((byte) (needCamera ? 1 : 0));
        dest.writeInt(statusBarColor);
        dest.writeInt(backResId);
        dest.writeString(title);
        dest.writeInt(titleColor);
        dest.writeInt(titleBgColor);
        dest.writeInt(btnTextColor);
        dest.writeInt(btnBgColor);
        dest.writeString(filePath);
        dest.writeInt(aspectX);
        dest.writeInt(aspectY);
        dest.writeInt(outputX);
        dest.writeInt(outputY);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImgSelConfig> CREATOR = new Creator<ImgSelConfig>() {
        @Override
        public ImgSelConfig createFromParcel(Parcel in) {
            return new ImgSelConfig(in);
        }

        @Override
        public ImgSelConfig[] newArray(int size) {
            return new ImgSelConfig[size];
        }
    };
}
