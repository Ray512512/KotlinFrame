package com.moodsmap.waterlogging.presentation.widget.impl;

import android.view.ViewGroup;

import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.sort.LinearSort;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by Ray on 2018/1/16.
 * 每个布局默认只显示一次动画
 */
public class LayoutAnim {
    private static final String TAG = "LayoutAnim";
    private HashMap<ViewGroup,Boolean> hasAnim=new HashMap<>();
    private ViewGroup viewGroup; //默认每个界面一个

    @Inject
    public LayoutAnim() {
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    public void start(ViewGroup viewGroup){
        if(viewGroup==null||hasAnim.containsKey(viewGroup))return;
        Animator[] animator=new Animator[]{
                Glider.glide(Skill.QuintEaseOut, 800, ObjectAnimator.ofFloat(viewGroup, "scaleX", 1.5f, 1f)),
                Glider.glide(Skill.QuintEaseOut, 800, ObjectAnimator.ofFloat(viewGroup, "scaleY", 1.5f, 1f)),
                Glider.glide(Skill.QuintEaseOut, 800, ObjectAnimator.ofFloat(viewGroup, "alpha", 0, 1f))
        };
        viewGroup.postDelayed(() -> {
            try {
                new Spruce.SpruceBuilder(viewGroup)
                        .sortWith(new LinearSort(50L, false, LinearSort.Direction.TOP_TO_BOTTOM))
                        .animateWith(animator)
                        .start();
                hasAnim.put(viewGroup, true);
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }, 0);
    }

    public void start(){
        start(viewGroup);
    }

    public void clear(){
        viewGroup=null;
        hasAnim.clear();
    }

}
