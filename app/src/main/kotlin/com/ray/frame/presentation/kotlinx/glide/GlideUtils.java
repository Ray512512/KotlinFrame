package com.ray.frame.presentation.kotlinx.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ray.frame.App;
import com.ray.frame.R;
import com.ray.frame.data.api.ApiConstants;
import com.ray.frame.presentation.utils.BitmapUtil;
import com.ray.frame.presentation.utils.SizeUtils;

import java.io.File;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by Ray on 2018/8/15.
 */
public class GlideUtils {

    public static boolean check(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 || context != null && (!(context instanceof Activity) || !((Activity) context).isDestroyed());
    }

    public static String checkUrl(String url){
        if(TextUtils.isEmpty(url))return "";
        String r=url;
       /* if(!url.startsWith("/")&&!url.startsWith("http")){
            if(!url.contains(ApiConstants.SHOW_IMG_HEAD)){
                r=ApiConstants.SHOW_IMG_HEAD+r;
            }
        }*/
        return r;
    }
    public static void loadRoundImg(ImageView imageView, Bitmap bitmap,int corner){
        if(!check(imageView.getContext()))return;
        if(bitmap==null)return;
        imageView.setImageBitmap(BitmapUtil.bimapRound(bitmap, SizeUtils.dp2px(imageView.getContext(),corner)));
    }

    public static void loadCircleImg(ImageView imageView, String  url){
        if(!check(imageView.getContext()))return;
        url=checkUrl(url);
        GlideApp.with(App.instance).load(url).transform
                (new CircleCrop()).
                placeholder(R.mipmap.avatar_default).
                into(imageView);
    }
    public static void loadCircleImg(Context context,ImageView imageView, String  url){
        if(!check(context))return;
        url=checkUrl(url);
        GlideApp.with(context).load(url).transform
                (new CircleCrop()).
                placeholder(R.mipmap.avatar_default).
                into(imageView);
    }

    public static void loadCircleImg(Context context,ImageView imageView, String  url,int defaultId){
        if(!check(context))return;
        if(TextUtils.isEmpty(url)){
            GlideApp.with(context).load(defaultId).transform
                    (new CircleCrop()).
                    placeholder(defaultId).
                    into(imageView);
            return;
        }
        url=checkUrl(url);
        GlideApp.with(context).load(url).transform
                (new CircleCrop()).
                placeholder(defaultId).
                into(imageView);
    }

    public static void loadCircleImg(Context context, String  url,ImageView imageView){
        if(!check(context))return;
        url=checkUrl(url);
        GlideApp.with(context).load(url).transform
                (new CircleCrop()).
                placeholder(R.mipmap.avatar_default).
                into(imageView);
    }

    public static void loadCircleImgByDisallowHardwareConfig(Context context, String  url,ImageView imageView){
        if(!check(context))return;
        url=checkUrl(url);
        GlideApp.with(context).load(url).transform
                (new CircleCrop()).
                placeholder(R.mipmap.avatar_default).
                disallowHardwareConfig().
                into(imageView);
    }

    public static void load(Context mContext, String url, ImageView view) {
        if(!check(mContext))return;
        url=checkUrl(url);
        GlideApp.with(mContext).load(url).centerCrop().placeholder(R.mipmap.placeholder_gray).into(view);
    }

    public static void load(Context mContext, int url, ImageView view) {
        if(!check(mContext))return;
        GlideApp.with(mContext).load(url).centerCrop().placeholder(R.mipmap.placeholder_gray).into(view);
    }
    public static void loadDefault(Context mContext, String url, ImageView view) {
        if(!check(mContext))return;
        url=checkUrl(url);
        GlideApp.with(mContext).load(url).placeholder(R.mipmap.placeholder_gray).into(view);
    }

    public static void loadRound(Context mContext, String url, ImageView view,int radius) {
        if(!check(mContext))return;
        url=checkUrl(url);
        view.setTag(null);
        Glide.with(mContext)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.placeholder_gray)
                        .transforms(new CenterCrop(), new RoundedCorners(radius)
                        ))
                .into(view);
//        GlideApp.with(mContext).load(url).placeholder(R.mipmap.placeholder_gray).centerCrop().transform(new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL)).into(view);
    }

    public static void loadRound(Context mContext, File file, ImageView view, int radius) {
        if(!check(mContext))return;
        Glide.with(mContext)
                .load(file)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.placeholder_gray)
                        .transforms(new CenterCrop(), new RoundedCorners(radius)
                        ))
                .into(view);
//        GlideApp.with(mContext).load(file).placeholder(R.mipmap.placeholder_gray).centerCrop().transform(new CenterCrop(),new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL)).into(view);
    }
    public static void load(Context mContext, String url, ImageView view, int defaultRes) {
        if(!check(mContext))return;
        url=checkUrl(url);
        GlideApp.with(mContext).load(url).placeholder(defaultRes).into(view);
    }
}
