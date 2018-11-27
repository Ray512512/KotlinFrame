package util;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;

import java.io.File;

import teprinciple.updateapputils.R;


/**
 * Created by Teprinciple on 2017/11/3.
 */
 public class UpdateAppReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int notifyId = 1;
        int progress = intent.getIntExtra("progress", 0);
        String title = intent.getStringExtra("title");

        NotificationManager nm = null;
        if (UpdateAppUtils.showNotification) {
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationCompat.Builder builder = get8NotifacationBuild(context);
                if (builder != null) {
                    builder.setContentTitle("正在下载 " + title);
                    builder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
                    builder.setProgress(100, progress, false);
                    builder.setSound(null);
                    builder.setVibrate(null);
                    Notification notification = builder.build();
                    nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    nm.notify(notifyId, notification);
                }
            } else {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setContentTitle("正在下载 " + title);
                builder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
                builder.setProgress(100, progress, false);
                builder.setSound(null);
                builder.setVibrate(null);

                Notification notification = builder.build();
                nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                nm.notify(notifyId, notification);
            }
        }


        if (progress == 100) {
            if (nm != null) {
                nm.cancel(notifyId);
            }

            if (DownloadAppUtils.downloadUpdateApkFilePath != null) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                File apkFile = new File(DownloadAppUtils.downloadUpdateApkFilePath);
                if (UpdateAppUtils.needFitAndroidN && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(
                            context, context.getPackageName() + ".fileprovider", apkFile);
                    i.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    i.setDataAndType(Uri.fromFile(apkFile),
                            "application/vnd.android.package-archive");
                }
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }


    public static NotificationCompat.Builder get8NotifacationBuild(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //分组（可选）
            //groupId要唯一
            String groupId = "down";
            NotificationChannelGroup group = new NotificationChannelGroup(groupId, "下载通知");
            //创建group
            notificationManager.createNotificationChannelGroup(group);
            //channelId要唯一
            String channelId = "channel";
            @SuppressLint("WrongConstant")
            NotificationChannel adChannel = new NotificationChannel(channelId, "下载进度", NotificationManager.IMPORTANCE_DEFAULT);
            //补充channel的含义（可选）
            adChannel.setDescription("进度信息");
            //将渠道添加进组（先创建组才能添加）
            adChannel.setGroup(groupId);
            adChannel.setSound(null, null);
            adChannel.enableVibration(false);
            //创建channel
            notificationManager.createNotificationChannel(adChannel);
            //创建通知时，标记你的渠道id
            return new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher))
                    ;
        }
        return null;
    }
}
