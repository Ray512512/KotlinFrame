package com.moodsmap.waterlogging.presentation.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by Ray on 2017/5/18.
 * email：1452011874@qq.com
 * sign:每当你在感叹，如果有这样一个东西就好了的时候，请注意，其实这是你的机会. —— 郭霖
 */

public class ViewUtils {

    public static Bitmap getViewBitmap(View addViewContent) {

        addViewContent.setDrawingCacheEnabled(true);

        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());

        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        return bitmap;
    }


    /**
     * 获取视图宽度
     */
    public static int getViewWidth(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        width = view.getMeasuredWidth();
        return width;
    }

    /**
     * 获取视图高度
     */
    public static int getViewHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        height = view.getMeasuredHeight();
        return height;
    }

    public static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    public static void EditloseInput(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void EditloseInput(Context context, Dialog dialog) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void EditshowInput(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        //请求获得焦点
        editText.requestFocus();
        //调用系统输入法
        InputMethodManager inputManager = (InputMethodManager) editText
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

    public static void EditloseFocus(Context context, EditText editText) {
        editText.setFocusable(false);//设置输入框不可聚焦，即失去焦点和光标
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void EditFindFocus(Context context, EditText editText) {
        editText.setFocusable(true);//设置输入框可聚集
        editText.setFocusableInTouchMode(true);//设置触摸聚焦
        editText.requestFocus();//请求焦点
        editText.findFocus();//获取焦点
//        InputMethodManager imm = (InputMethodManager)  context.getSystemService( Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
    }

    /**
     * 阻止系统输入法弹出并保留光标
     */
    public static void EditStopNativeInput(final EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return true;
                }
                int inType = editText.getInputType();
                editText.setInputType(InputType.TYPE_NULL);
                editText.onTouchEvent(event);
                editText.setInputType(inType);
                editText.setSelection(editText.getText().length());
                return true;
            }
        });
    }

    /**
     * 清除edittext焦点以及输入框
     */
    public static void EditloseInputAndFocus(Context context, EditText editText) {
        editText.clearFocus();//取消焦点
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//        editText.clearFocus();
    }

    public static void ViewFixVisibility(View view, boolean isVisivility) {
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) view.getLayoutParams();
        if (isVisivility) {
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            view.setVisibility(View.VISIBLE);
        } else {
            param.height = 0;
            param.width = 0;
            view.setVisibility(View.GONE);
        }
        view.setLayoutParams(param);
    }

    public static void EditLoseInputAndFocus(Context context, View other) {
        //让other获取焦点
        other.setFocusable(true);
        other.setFocusableInTouchMode(true);
        other.requestFocus();
        //隐藏输入法
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(other.getWindowToken(), 0);
    }


    /**
     * 根据传入控件的坐标和用户的焦点坐标，判断是否隐藏键盘，如果点击的位置在控件内，则不隐藏键盘
     *
     * @param view  控件view
     * @param event 焦点位置
     * @return 是否隐藏
     */
    public static void hideKeyboard(MotionEvent event, View view,
                                    Activity activity) {
        try {
            if (view != null/* && view instanceof EditText
                    || view instanceof VoiceEditText
                    || view instanceof VoiceDescriptionEditText*/) {
                int[] location = {0, 0};
                view.getLocationInWindow(location);
                int left = location[0], top = location[1], right = left
                        + view.getWidth(), bootom = top + view.getHeight();
                // 判断焦点位置坐标是否在空间内，如果位置在控件外，则隐藏键盘
                if (event.getRawX() < left || event.getRawX() > right
                        || event.getY() < top || event.getRawY() > bootom) {
                    // 隐藏键盘
                    EditLoseInputAndFocus(activity, view);
                    /*IBinder token = view.getWindowToken();
                    InputMethodManager inputMethodManager = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(token,
                            InputMethodManager.HIDE_NOT_ALWAYS);*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTextColor(TextView tv,  String content, int keyColor) {
        if (TextUtils.isEmpty(content)) return;
        SpannableStringBuilder spanable = new SpannableStringBuilder(content);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), keyColor)), 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spanable);
    }
    /**
     * 设置开头文本颜色处理
     *
     * @param tv
     * @param key
     * @param content
     * @param keyColor
     * @param nomalColor
     */
    public static void setStartMultText(TextView tv, String key, String content, int keyColor, int nomalColor) {
        if (TextUtils.isEmpty(content)) return;
        SpannableStringBuilder spanable = new SpannableStringBuilder(content);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        if (content.startsWith(key)) {
            spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), keyColor)), 0, key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), nomalColor)), key.length(), content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), nomalColor)), 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spanable);
    }

    /**
     * 设置文本某段文字颜色处理
     * 只能处理第一次
     *
     * @param tv
     * @param key
     * @param content
     * @param keyColor
     * @param nomalColor
     */
    public static void setSomeMultText(TextView tv, String key, String content, int keyColor, int nomalColor) {
        if (TextUtils.isEmpty(content)) return;
        SpannableStringBuilder spanable = new SpannableStringBuilder(content);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        if (content.contains(key)) {
            spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), nomalColor)), 0, content.indexOf(key), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), keyColor)), content.indexOf(key), content.indexOf(key) + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), nomalColor)), content.indexOf(key) + key.length(), content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), nomalColor)), 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spanable);
    }

    public static void setSomeMultTextWithClick(TextView tv, String key, String content, int keyColor, int nomalColor,ClickableSpan clickableSpan) {
        if (TextUtils.isEmpty(content)) return;
        SpannableStringBuilder spanable = new SpannableStringBuilder(content);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        if (content.contains(key)) {
            spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), nomalColor)), 0, content.indexOf(key), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), keyColor)), content.indexOf(key), content.indexOf(key) + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanable.setSpan(clickableSpan, content.indexOf(key),content.indexOf(key) + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), nomalColor)), content.indexOf(key) + key.length(), content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), nomalColor)), 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spanable);
        tv.setHighlightColor(Color.parseColor("#00ffffff"));
    }

    public static void setEndMultTextWithClick(TextView tv, int keyIndex, int keyColor, int nomalColor,ClickableSpan clickableSpan) {
        String content=tv.getText().toString();
        if (TextUtils.isEmpty(content)) return;
        SpannableStringBuilder spanable = new SpannableStringBuilder(content);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        if (keyIndex<content.length()) {
            if(clickableSpan!=null)
            spanable.setSpan(clickableSpan,keyIndex, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), nomalColor)), 0, keyIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), keyColor)), keyIndex, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spanable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(tv.getContext(), nomalColor)), 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spanable);
        tv.setHighlightColor(Color.parseColor("#00ffffff"));
    }

    /**
     * 旋转动画只执行一次
     * @param image
     */
    public static void roteView(ImageView image) {
        image.setPivotX(image.getWidth() / 2);
        image.setPivotY(image.getHeight() / 2);
        image.setRotation(180);
    }


    /**
     * 获取ExpandableListView显示的第一个子view
     * @param pos
     * @param listView
     * @return
     */
    public View getViewByPosition(int pos, ExpandableListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;
        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }


    public static void setNumInputLimitEt(EditText et){
        et.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                if(et.getText().toString().equals("")){
                    et.setText("1");
                }
            }
        });
    }
}
