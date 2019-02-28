package com.mocuz.common.commonutils;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.mocuz.common.R;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class ImageLoaderUtils {

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url).placeholder(placeholder)
                .error(error).crossFade().into(imageView);
    }

    public static void display_block(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .crossFade().into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loading)
                .into(imageView);
    }

    public static void displayNoPlaceholder(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .crossFade().into(imageView);
    }

    public static void display(Context context, ImageView imageView, File url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loading)
                .crossFade().into(imageView);
    }

    public static void displaySmallPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loading)
                .thumbnail(0.5f)
                .into(imageView);
    }

    public static void displayBigPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url).asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loading)
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, int url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loading)
                .crossFade().into(imageView);
    }

    public static void displayCircle(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.toux2)
                .error(R.drawable.toux2)
                .transform(new CenterCrop(context), new GlideCircleTransfromUtil(context)).into(imageView);
    }
    public static void displayCircleIcon(Context context, ImageView imageView, int id) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load("")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(id)
                .error(id)
                .centerCrop().
                bitmapTransform(new RoundedCornersTransformation(context, 12, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void displayRoundedCornersHead(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.toux2)
                .error(R.drawable.toux2)
                .centerCrop().
                bitmapTransform(new RoundedCornersTransformation(context, 12, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void displayRoundedCorners(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loading)
                .centerCrop().
                bitmapTransform(new RoundedCornersTransformation(context, 12, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void displayRoundedCornersno(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_image_loading)
                .centerCrop().
                bitmapTransform(new RoundedCornersTransformation(context, 12, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void displayRoundedCorners2(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_image_loading)
                .placeholder(R.drawable.ic_image_loading)
                .bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 12, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void displayRoundedCorners2adv(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.load_adv)
                .bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 12, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void displayVideoImage(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.load_adv)
                .bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 12, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void displayVideoImage2(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.load_adv)
                .bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 0, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void displayBlurImage(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loading)
                .crossFade()
                .bitmapTransform(new BlurTransformation(context, 10, 1))
                .into(imageView);
    }

    public static void displayCircleHead(Context context, ImageView imageView, int url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.toux2)
                .error(R.drawable.toux2)
                .transform(new CenterCrop(context), new GlideCircleTransfromUtil(context)).into(imageView);
    }

    public static void displayBlurImageHead(Context context, ImageView imageView, int url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (isFinished(context))
            return;
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loading)
                .crossFade()
                .bitmapTransform(new BlurTransformation(context, 10, 1))
                .into(imageView);
    }

    public static boolean isFinished(Context mContext) {
        if (null == mContext)
            return true;
        return ((Activity) mContext).isFinishing();
    }
}
