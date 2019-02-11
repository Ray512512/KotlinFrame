package com.ray.frame.common.adapter.expandadapter;

import android.content.Context;
import android.view.View;

import java.util.List;

public abstract class ChildHolder<T,V> {
    private View convertView;
    public Context mContext;

    public ChildHolder(Context context, V t) {
        mContext=context;
        convertView = initView(t);
        convertView.setTag(this);
    }

    public View getConvertView() {
        return convertView;
    }

    @SuppressWarnings("unchecked")
    public  <T extends View> T findID(View v, int id) {
        return (T) v.findViewById(id);
    }

    public abstract void refreshView(List<T> list, int position);//初始化页面数据

    public abstract View initView(V t);//加载页面ui
}
