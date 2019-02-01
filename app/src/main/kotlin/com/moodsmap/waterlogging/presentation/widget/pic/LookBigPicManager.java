package com.moodsmap.waterlogging.presentation.widget.pic;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.maning.imagebrowserlibrary.ImageEngine;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.maning.imagebrowserlibrary.model.ImageBrowserConfig;

import java.util.ArrayList;

/**
 * Created by Ray on 2018/5/29.
 */

public class LookBigPicManager {
    private static final String TAG = "LookBigPicManager";
    private ImageBrowserConfig.TransformType transformType = ImageBrowserConfig.TransformType.Transform_ZoomOut;
    private ImageBrowserConfig.IndicatorType indicatorType = ImageBrowserConfig.IndicatorType.Indicator_Number;
    private ImageBrowserConfig.ScreenOrientationType screenOrientationType = ImageBrowserConfig.ScreenOrientationType.Screenorientation_Default;

    private static LookBigPicManager mLookBigPicManager = null ;
    private LookBigPicManager(){}
    public static LookBigPicManager getInstance(){
        if(mLookBigPicManager==null)
         synchronized (LookBigPicManager.class){
              if(mLookBigPicManager==null){
                  mLookBigPicManager=new LookBigPicManager();
              }
         }
         return mLookBigPicManager;
    }

    int testNum=0;
    private MNImageBrowser init(Context context, int position){
       /* if(testNum>=7)testNum=0;
        transformType=ImageBrowserConfig.TransformType.values()[testNum++];*/
//        L.v(TAG,transformType.nickName());
        return MNImageBrowser.with(context)
                .setCurrentPosition(position)
                .setImageEngine(new GlideImageEngine())
                .setTransformType(transformType)
                .setIndicatorType(indicatorType)
                .setScreenOrientationType(screenOrientationType);
    }

    public void lookBigPic(Context context, int position, ArrayList<String> gankData, View view){
        init(context,position).setImageList(gankData).show(view);
    }

    public void lookBigPic(Context context, String single, View view){
        MNImageBrowser imageBrowser=init(context,0);
        imageBrowser.setImageUrl(single).show(view);
    }

    public void lookQrCode(Activity context, String url, View view){
//        context.overridePendingTransition(R.anim.fragment_in,R.anim.anim_stay);
        MNImageBrowser imageBrowser=init(context,0);
        imageBrowser.setPayCode(true);
        imageBrowser.setImageUrl(url).show(view);
    }

    public class GlideImageEngine implements ImageEngine {
        @Override
        public void loadImage(Context context, String url, ImageView imageView) {
            Glide.with(context).load(url).into(imageView);
        }
    }

}
