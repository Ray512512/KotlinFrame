package com.ray.frame.common.adapter.expandadapter;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

public abstract class ParentHolder<T> {
    private View convertView;
    public Context mContext;

    public ParentHolder(Context context) {
        mContext=context;
        convertView = initView();
        convertView.setTag(this);
    }

    public View getConvertView() {
        return convertView;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findID(View v, int id) {
        return (T) v.findViewById(id);
    }

    public abstract void refreshView(ArrayList<T> list, int position, boolean isExpanded);//初始化页面数据

    public abstract View initView();//加载页面ui
}
