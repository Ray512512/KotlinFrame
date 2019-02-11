/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ray.frame.presentation.rxutil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ray.frame.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.ray.frame.presentation.widget.impl.ChoicePicTool.FILE_HEAD;

/**
 * 简单重构了下，并且修复了重复插入图片问题
 * Created by drakeet on 8/10/15.
 */
public class RxImgSave {

    public static void saveImageToGallery(Context context,String mImageUrl) {
        // @formatter:off
        Log.v("saveImageToGallery",mImageUrl);
        String mImageTitle;
        try {
            mImageTitle = mImageUrl.substring(mImageUrl.lastIndexOf("/") + 1, mImageUrl.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            mImageTitle = mImageUrl.substring(mImageUrl.lastIndexOf("/") + 1, mImageUrl.length());
        }
        Subscription s = saveImageAndGetPathObservable(context, mImageUrl, mImageTitle)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                        Toast.makeText(context, String.format(context.getString(R.string.file_save_to), uri.getPath()), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context,context.getString(R.string.save_succ), Toast.LENGTH_SHORT).show();
                    }
                }, error -> Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show());
        // @formatter:on
//        addSubscription(s);
    }

    private static Observable<Uri> saveImageAndGetPathObservable(Context context, String url, String title) {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                try {
                    bitmap = Picasso.with(context).load(url).get();
                } catch (IOException e) {
                    subscriber.onError(e);
                }catch (IllegalStateException e2) {
                    if (url.startsWith("/")) {
                        String local = FILE_HEAD + url;
                        try {
                            bitmap = Picasso.with(context).load(local).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (bitmap == null) {
                    subscriber.onError(new Exception("无法下载到图片"));
                }
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        }).flatMap(bitmap -> {
            File appDir = new File(Environment.getExternalStorageDirectory(), context.getString(R.string.app_tag_name));
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = title.replace('/', '-') + ".jpg";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                assert bitmap != null;
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Uri uri = Uri.fromFile(file);
            // 通知图库更新
            Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            context.sendBroadcast(scannerIntent);
            return Observable.just(uri);
        }).subscribeOn(Schedulers.io());
    }
}
