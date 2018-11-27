package com.maning.imagebrowserlibrary.model;

import android.support.v4.view.ViewPager;

import com.maning.imagebrowserlibrary.ImageEngine;
import com.maning.imagebrowserlibrary.listeners.OnClickListener;
import com.maning.imagebrowserlibrary.listeners.OnLongClickListener;

import java.util.ArrayList;

/**
 * <pre>
 *     author : maning
 *     e-mail : xxx@xx
 *     time   : 2018/04/10
 *     desc   : 相关配置信息
 *     version: 1.0
 * </pre>
 */
public class ImageBrowserConfig {

    //枚举类型：切换动画类型
    public enum TransformType {
        Transform_Default,
        Transform_DepthPage,
        Transform_RotateDown,
        Transform_RotateUp,
        Transform_ZoomIn,
        Transform_ZoomOutSlide,
        Transform_ZoomOut,
    }

    //枚举类型：指示器类型
    public enum IndicatorType {
        Indicator_Circle,
        Indicator_Number,
    }

    //枚举类型：屏幕方向
    public enum ScreenOrientationType {
        //默认：横竖屏全部支持
        Screenorientation_Default,
        //竖屏
        ScreenOrientation_Portrait,
        //横屏
        Screenorientation_Landscape,
    }


    //当前位置
    private int position;
    //切换效果
    private TransformType transformType = TransformType.Transform_Default;
    //指示器类型
    private IndicatorType indicatorType = IndicatorType.Indicator_Number;
    //图片源
    private ArrayList<String> imageList;
    //图片加载引擎
    private ImageEngine imageEngine;
    //单击监听
    private OnClickListener onClickListener;
    //长按监听
    private OnLongClickListener onLongClickListener;
    //设置屏幕的方向
    private ScreenOrientationType screenOrientationType = ScreenOrientationType.Screenorientation_Default;

    private ViewPager.SimpleOnPageChangeListener onPageChangeListener;

    private String title;

    private boolean isPayCode=false;

    public boolean isPayCode() {
        return isPayCode;
    }

    public void setPayCode(boolean payCode) {
        isPayCode = payCode;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ScreenOrientationType getScreenOrientationType() {
        return screenOrientationType;
    }

    public void setScreenOrientationType(ScreenOrientationType screenOrientationType) {
        this.screenOrientationType = screenOrientationType;
    }

    public IndicatorType getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(IndicatorType indicatorType) {
        this.indicatorType = indicatorType;
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public ViewPager.SimpleOnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    public void setOnPageChangeListener(ViewPager.SimpleOnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OnLongClickListener getOnLongClickListener() {
        return onLongClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ImageEngine getImageEngine() {
        return imageEngine;
    }

    public void setImageEngine(ImageEngine imageEngine) {
        this.imageEngine = imageEngine;
    }

    public TransformType getTransformType() {
        return transformType;
    }

    public void setTransformType(TransformType transformType) {
        this.transformType = transformType;
    }
}
