package com.ray.frame.presentation.widget.work;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Ray on 2018/9/18.
 */
public class CountDownTimeWorker {
    private static final String TAG = "CountDownTimeWorker";
    private static final int MSG_TAG = 6;
    //    private SimpleDateFormat dataformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private long day, hour, min, mSecond;
    private boolean isRun = true;
    private TextView showTv;

    public interface TimeOverCallback {
        void onTimeFinish();
    }

    private TimeOverCallback callback;

    public CountDownTimeWorker(TextView tv, long validTime, TimeOverCallback callback) {
        this.showTv = tv;
        this.callback = callback;
        long timeDQ = System.currentTimeMillis();
        long date = validTime - timeDQ;
        if (date < 0) {
            Log.v(TAG, "截止时间已过");
            if(callback!=null)
            callback.onTimeFinish();
            isRun = false;
        }
        day = date / (1000 * 60 * 60 * 24);
        hour = (date / (1000 * 60 * 60) - day * 24);
        min = ((date / (60 * 1000)) - day * 24 * 60 - hour * 60);
        mSecond = (date / 1000) - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;
    }

    //开启线程
    @SuppressLint("NewApi")
    public void startRun() {
        new Thread(() -> {
            while (isRun) {
                try {
                    if (((Activity) showTv.getContext()).isDestroyed()) {
                        reset();
                        return;
                    }
                    Thread.sleep(1000); // sleep 1000ms
                    Message message = Message.obtain();
                    message.what = 6;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stop(){
        reset();
    }

    private void reset(){
        isRun = false;
        showTv = null;
        callback=null;
        handler.removeMessages(MSG_TAG);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_TAG:
                    computeTime();
                    if (day <= 0 && hour <= 0 && min <= 0 && mSecond <= 0) {
                        if(showTv!=null)
                        showTv.setText("已截止");
                        isRun = false;
                        if(callback!=null)
                        callback.onTimeFinish();
                        return true;
                    }
                    String s = "";
                    if (day > 0) {
                        s += day + "天";
                    }
                    if (hour > 0) {
                        s += hour + "小时";
                    }
                    if (min > 0) {
                        s += min + "分";
                    }
                    if (mSecond > 0) {
                        s += mSecond + "秒";
                    }
                    if(showTv!=null)
                        showTv.setText(s);
                    break;
            }
            return false;
        }
    });

    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            min--;
            mSecond = 59;
            if (min < 0) {
                min = 59;
                hour--;
                if (hour < 0) {
                    // 倒计时结束
                    hour = 23;
                    day--;
                }
            }
        }
    }

}
