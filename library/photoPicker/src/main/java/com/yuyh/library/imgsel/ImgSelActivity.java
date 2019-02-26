package com.yuyh.library.imgsel;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mocuz.common.commonutils.RomUtils;
import com.mocuz.common.flyn.Eyes;
import com.yuyh.library.imgsel.common.Callback;
import com.yuyh.library.imgsel.common.Constant;
import com.yuyh.library.imgsel.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ImgSelActivity extends SwipeBackActivity implements View.OnClickListener, Callback {

    private static final String KEY_CONFIG = "KEY_CONFIG";
    public static final String INTENT_RESULT = "result";
    private static final int IMAGE_CROP_CODE = 1;
    private static final int STORAGE_REQUEST_CODE = 1;

    private ImgSelConfig config;

    private RelativeLayout rel_titleBar;
    private RelativeLayout rlTitleBar;
    private LinearLayout lin_confirm;
    private TextView tvTitle;
    private TextView btnConfirm;
    private TextView tv_number;
    private ImageView ivBack;
    private RelativeLayout lay_image_back;
    private String cropImagePath;
    private SwipeBackLayout mSwipeBackLayout;
    private ArrayList<String> result = new ArrayList<>();
    private List<String> mPermissionList;

    public static void startActivity(Activity activity, ImgSelConfig config, int RequestCode) {
        Intent intent = new Intent(activity, ImgSelActivity.class);
        Constant.config = config;
        activity.startActivityForResult(intent, RequestCode);
    }

    public static void startActivity1(Fragment fragment, ImgSelConfig config, int RequestCode) {
        Intent intent = new Intent(fragment.getActivity(), ImgSelActivity.class);
        Constant.config = config;
        fragment.startActivityForResult(intent, RequestCode);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState)
            Constant.config = savedInstanceState.getParcelable(KEY_CONFIG);
        setContentView(R.layout.activity_img_sel);
        Constant.imageList.clear();
        config = Constant.config;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            if (null != config) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fmImageList, ImgSelFragment.instance(config), null)
                        .commit();
            }
        } else {
            mPermissionList = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //添加需要申请的权限
                mPermissionList.add(Manifest.permission.CAMERA);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //添加需要申请的权限
                mPermissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (null != mPermissionList && mPermissionList.size() > 0)
                //申请权限
                ActivityCompat.requestPermissions(this, mPermissionList.toArray(new String[mPermissionList.size()]),
                        STORAGE_REQUEST_CODE);
        }
        initView();
        if (!FileUtils.isSdCardAvailable()) {
            Toast.makeText(this, "SD卡不可用", Toast.LENGTH_SHORT).show();
        }
        setmSwipeBackLayout();
    }

    private void setmSwipeBackLayout() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {

            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
//                vibrate(VIBRATE_DURATION);
            }

            @Override
            public void onScrollOverThreshold() {
//                vibrate(VIBRATE_DURATION);
            }
        });
    }

    private void initView() {
        rel_titleBar = (RelativeLayout) findViewById(R.id.rel_titleBar);
        rlTitleBar = (RelativeLayout) findViewById(R.id.rlTitleBar);
        lin_confirm = (LinearLayout) findViewById(R.id.lin_confirm);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        btnConfirm = (TextView) findViewById(R.id.btnConfirm);
        tv_number = (TextView) findViewById(R.id.tv_imgNumber);
        lin_confirm.setOnClickListener(this);
        ivBack = (ImageView) findViewById(R.id.image_back);
        lay_image_back = (RelativeLayout) findViewById(R.id.lay_image_back);
        lay_image_back.setOnClickListener(this);
        if (config != null) {
            if (config.themeColor != 0)
                ivBack.setBackgroundColor(config.themeColor);
            if (config.themeColor != 0)
                btnConfirm.setTextColor(config.themeColor);
            tv_number.setText(Constant.imageList.size() + "/" + config.maxNum);
            if (config.backResId != -1) {
                ivBack.setImageResource(config.backResId);
            }

//            if (config.statusBarColor != -1) {
//                StatusBarCompat.compat(this, config.statusBarColor);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
//                        && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                    //透明状态栏
//                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                }
//            }
            if (null != config.titleBgDrawable) {
                rel_titleBar.setBackgroundDrawable(config.titleBgDrawable);
            }
            int mTitleBarHeight = com.mocuz.common.commonwidget.StatusBarCompat.getStatusBarHeight(this);
            // 默认着色状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || RomUtils.getMiuiVersion() >= 6 || RomUtils.checkIsMeizuRom() == true) {
                Eyes.translucentStatusBar(this, true);
                Eyes.setStatusTextBlack(this);
            } else {
                Eyes.translucentStatusBar(this, false);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                rel_titleBar.setPadding(0, mTitleBarHeight, 0, 0);
            }
            if (config.statusBarColor != -1)
                rlTitleBar.setBackgroundColor(config.titleBgColor);
//            tvTitle.setTextColor(config.titleColor);
            tvTitle.setText(config.title);
//            btnConfirm.setBackgroundColor(config.btnBgColor);
//            btnConfirm.setTextColor(config.btnTextColor);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.lin_confirm) {
            if (Constant.imageList != null && !Constant.imageList.isEmpty()) {
                exit();
            }
        } else if (id == R.id.lay_image_back) {
            finish();
        }
    }

    @Override
    public void onSingleImageSelected(String path) {
        if (config.needCrop) {
            crop(path);
        } else {
            Constant.imageList.add(path);
            exit();
        }
    }

    @Override
    public void onImageSelected(String path) {
        tv_number.setText(Constant.imageList.size() + "/" + config.maxNum);

    }

    @Override
    public void onImageUnselected(String path) {
        tv_number.setText(Constant.imageList.size() + "/" + config.maxNum);
    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            if (config.needCrop) {
                crop(imageFile.getAbsolutePath());
            } else {
                Constant.imageList.add(imageFile.getAbsolutePath());
                exit();
            }
        }
    }

    private void crop(String imagePath) {
        File file = new File(FileUtils.createRootPath(this) + "/" + System.currentTimeMillis() + ".temp");
        cropImagePath = file.getAbsolutePath();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(imagePath)), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", config.aspectX);
        intent.putExtra("aspectY", config.aspectY);
        intent.putExtra("outputX", config.outputX);
        intent.putExtra("outputY", config.outputY);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, IMAGE_CROP_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CROP_CODE && resultCode == RESULT_OK) {
            Constant.imageList.add(cropImagePath);
            exit();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void exit() {
        Intent intent = new Intent();
        result.clear();
        result.addAll(Constant.imageList);
        intent.putStringArrayListExtra(INTENT_RESULT, result);
        setResult(RESULT_OK, intent);
        Constant.imageList.clear();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_REQUEST_CODE:
                if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (null != config) {
                            getSupportFragmentManager().beginTransaction()
                                    .add(R.id.fmImageList, ImgSelFragment.instance(config), null)
                                    .commit();
                        }
                    } else if (null != mPermissionList && mPermissionList.size() > 0) {
                        Toast.makeText(this, "请允许" + getResources().getString(R.string.app_name) + "获取相关权限", Toast.LENGTH_SHORT).show();
                        //申请权限
                        ActivityCompat.requestPermissions(this, mPermissionList.toArray(new String[mPermissionList.size()]),
                                STORAGE_REQUEST_CODE);
                    }
                } else {
                    Toast.makeText(this, "请允许" + getResources().getString(R.string.app_name) + "获取相关权限", Toast.LENGTH_SHORT).show();
                    if (null != mPermissionList && mPermissionList.size() > 0)
                        //申请权限
                        ActivityCompat.requestPermissions(this, mPermissionList.toArray(new String[mPermissionList.size()]),
                                STORAGE_REQUEST_CODE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("KEY_CONFIG", config);
    }
}
