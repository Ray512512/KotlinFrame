package com.ray.frame.common.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.ray.frame.R;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * Created by Ray on 2017/12/13.
 */

public class IndicatorAdapter extends CommonNavigatorAdapter {
    private String [] mTitleDataList;
    private ViewPager mViewPager;
    public enum IndicatorType{
       MATCH_H_1,WRAP_H_1
    }
    private IndicatorType mIndicatorType = IndicatorType.MATCH_H_1;
    private int pading=10;
    private int normalTvColor= R.color.text_3;
    private int selectTvColor=R.color.text_1;
    private int normalTvTextSize= 16;
    private int selectTvTextSize=16;

    public void setPading(int pading) {
        this.pading = pading;
    }

    public void setTvColor(int normalTvColor, int selectTvColor) {
        this.normalTvColor = normalTvColor;
        this.selectTvColor = selectTvColor;
    }

    public void setTvTextSize(int normalTvSize,int selectTvSize) {
        this.normalTvTextSize = normalTvSize;
        this.selectTvTextSize = selectTvSize;
    }

    public void setmTitleDataList(String[] mTitleDataList) {
        this.mTitleDataList = mTitleDataList;
        notifyDataSetChanged();
    }

    public IndicatorAdapter(String[] mTitleDataList, ViewPager mViewPager, IndicatorType indicatorType) {
        this.mTitleDataList = mTitleDataList;
        this.mViewPager = mViewPager;
        mIndicatorType=indicatorType;
    }

    public IndicatorAdapter(String[] mTitleDataList, ViewPager mViewPager, IndicatorType indicatorType, int padding) {
       this(mTitleDataList,mViewPager,indicatorType);
        pading=padding;
    }


    @Override
    public int getCount() {
        return mTitleDataList == null ? 0 : mTitleDataList.length;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int i) {
        ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context,pading);
        colorTransitionPagerTitleView.setNormalColor(ContextCompat.getColor(context,normalTvColor));
        colorTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(context,selectTvColor));
        colorTransitionPagerTitleView.setText(mTitleDataList[i]);
        colorTransitionPagerTitleView.setTextSize(16);
//        colorTransitionPagerTitleView.setNormalSize(normalTvTextSize);
//        colorTransitionPagerTitleView.setSelectedSize(selectTvTextSize);
        colorTransitionPagerTitleView.setOnClickListener(view -> mViewPager.setCurrentItem(i));
        return colorTransitionPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        switch (mIndicatorType){
            case MATCH_H_1:
                indicator.setLineHeight(UIUtil.dip2px(context, 1));
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                indicator.setColors(context.getResources().getColor(R.color.mainColor));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                break;
            case WRAP_H_1:
                indicator.setLineHeight(UIUtil.dip2px(context, 1));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(context.getResources().getColor(R.color.mainColor));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                return indicator;
        }
        return  indicator;
    }
}
