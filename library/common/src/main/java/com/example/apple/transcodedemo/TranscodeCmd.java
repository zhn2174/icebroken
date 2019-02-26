package com.example.apple.transcodedemo;

/**
 * Created by apple on 2017/5/25.
 */

public class TranscodeCmd {
    static {
        System.loadLibrary("ffmpeg");
    }

    private static OnExecListener listener;

    /**
     * 调用底层执行，是在异步线程中执行的
     * @param argc
     * @return
     */
    public static native int exec(TranscodeParam param);


    public static void onExecuted(int ret) {
        if (listener != null) {
            listener.onExecuted(ret);
        }
    }

    /**
     * 执行转码命令
     * @param param 转码参数
     * @param listener 底层消息回调
     *
     * @return 默认都会返回0
     */
    public static int exec(TranscodeParam param, OnExecListener listener) {
        TranscodeCmd.listener = listener;
        return exec(param);
    }

    /**
     * 执行完成/错误 时的回调接口
     */
    public interface OnExecListener {
        /**
         * 回调接口，ret返回0，表示执行任务成功
         * @param ret
         */
        void onExecuted(int ret);
    }
}
