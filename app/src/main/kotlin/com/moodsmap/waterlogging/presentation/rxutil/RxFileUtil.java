package com.moodsmap.waterlogging.presentation.rxutil;

import com.luseen.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ray on 2018/8/30.
 */
public class RxFileUtil {

    public static Disposable UnZipFolder(final File zipFile, final String outPathString, final OnUnzipProgress onUnzipProgress) {

        onUnzipProgress.onStart();
        return Observable.just(zipFile)
                .map(new Function<File, ZipInputStream>() {
                    @Override
                    public ZipInputStream apply(@NonNull File file) throws Exception {

                        FileInputStream fileInputStream = new FileInputStream(file);
                        return new ZipInputStream(fileInputStream);
                    }
                })
                .doOnNext(new Consumer<ZipInputStream>() {
                    @Override
                    public void accept(@NonNull ZipInputStream zipInputStream) throws Exception {
                        ZipEntry zipEntry;
                        String szName = "";

                        while ((zipEntry = zipInputStream.getNextEntry()) != null) {

                            szName = zipEntry.getName();
                            if (zipEntry.isDirectory()) {
                                //获取部件的文件夹名
                                szName = szName.substring(0, szName.length() - 1);
                                File folder = new File(outPathString + File.separator + szName);
                                folder.mkdirs();
                            } else {
                                File file = new File(outPathString + File.separator + szName);
                                if (!file.exists()) {
                                    file.getParentFile().mkdirs();
                                    file.createNewFile();
                                }
                                // 获取文件的输出流
                                FileOutputStream out = new FileOutputStream(file);

                                int len;
                                byte[] buffer = new byte[1024];
                                // 读取（字节）字节到缓冲区
                                while ((len = zipInputStream.read(buffer)) != -1) {
                                    // 从缓冲区（0）位置写入（字节）字节
                                    out.write(buffer, 0, len);
                                    out.flush();
                                }
                                out.close();
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ZipInputStream>() {
                    @Override
                    public void accept(@NonNull ZipInputStream zipInputStream) throws Exception {
                        zipInputStream.close();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Logger.e(throwable.getMessage());
                        onUnzipProgress.onError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        onUnzipProgress.onFinish();
                    }
                });

    }

    public interface OnUnzipProgress {

        void onStart();

        void onFinish();

        void onError(Throwable throwable);
    }

}
