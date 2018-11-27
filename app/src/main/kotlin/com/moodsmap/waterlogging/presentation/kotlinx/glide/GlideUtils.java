package com.moodsmap.waterlogging.presentation.kotlinx.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.moodsmap.waterlogging.App;
import com.moodsmap.waterlogging.R;
import com.moodsmap.waterlogging.presentation.utils.BitmapUtil;
import com.moodsmap.waterlogging.presentation.utils.SizeUtils;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by Ray on 2018/8/15.
 */
public class GlideUtils {

    public static void loadRoundImg(ImageView imageView, Bitmap bitmap,int corner){
        imageView.setImageBitmap(BitmapUtil.bimapRound(bitmap, SizeUtils.dp2px(imageView.getContext(),corner)));
        /*GlideApp.with(App.instance).load(bitmap).transform
                (new RoundedCornersTransformation(corner, 0, RoundedCornersTransformation.CornerType.ALL)).
                into(imageView);*/
    }

    public static void loadCircleImg(ImageView imageView, String  url){
        GlideApp.with(App.instance).load(url).transform
                (new CircleCrop()).
                into(imageView);
    }
    public static void loadCircleImg(Context context,ImageView imageView, String  url){
        GlideApp.with(context).load(url).transform
                (new CircleCrop()).
                placeholder(R.mipmap.ic_launcher).
                into(imageView);
    }

    public static void loadCircleImg(Context context, String  url,ImageView imageView){
        GlideApp.with(context).load(url).transform
                (new CircleCrop()).
                placeholder(R.mipmap.ic_launcher).
                into(imageView);
    }

    public static void loadCircleImgByDisallowHardwareConfig(Context context, String  url,ImageView imageView){
        GlideApp.with(context).load(url).transform
                (new CircleCrop()).
                placeholder(R.mipmap.ic_launcher).
                disallowHardwareConfig().
                into(imageView);
    }
    public static void load(Context mContext, String url, ImageView view) {
        GlideApp.with(mContext).load(url).centerCrop().placeholder(R.mipmap.ic_launcher).into(view);
    }

    public static void loadRound(Context mContext, String url, ImageView view,int radius) {
        GlideApp.with(mContext).load(url).centerCrop().placeholder(R.mipmap.ic_launcher).transform(new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL)).into(view);
    }
    public static void load(Context mContext, String url, ImageView view, int defaultRes) {
        GlideApp.with(mContext).load(url).placeholder(defaultRes).into(view);
    }
}
