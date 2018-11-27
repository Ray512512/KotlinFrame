package com.moodsmap.waterlogging.view.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.moodsmap.waterlogging.R;
import com.moodsmap.waterlogging.presentation.utils.SizeUtils;
import com.moodsmap.waterlogging.presentation.utils.Utils;


/**
 * 设置等页面条状控制或显示信息的控件
 */
public class AlineLinerlayoutView extends LinearLayout {

    private String name;
    private boolean isBottom,isSpacing;
    private String content;
    private boolean canNav;
    private boolean isSwitch;
    private boolean isAllLine;
    private int image;
    private LinearLayout contentPanel;
    public Switch switchPanel;

    public AlineLinerlayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.a_line_layout, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ALineLinerLayout, 0, 0);
        try {
            name = ta.getString(R.styleable.ALineLinerLayout_name);
            content = ta.getString(R.styleable.ALineLinerLayout_lv_content);
            isBottom = ta.getBoolean(R.styleable.ALineLinerLayout_isBottom, false);
            isSpacing = ta.getBoolean(R.styleable.ALineLinerLayout_isSpaceing, false);
            canNav = ta.getBoolean(R.styleable.ALineLinerLayout_canNav,false);
            isSwitch = ta.getBoolean(R.styleable.ALineLinerLayout_isSwitch,false);
            image = ta.getResourceId(R.styleable.ALineLinerLayout_image,0);
            isAllLine = ta.getBoolean(R.styleable.ALineLinerLayout_isAllLine,false);
            setUpView();
        } finally {
            ta.recycle();
        }
    }


    public void setContentTranceName(String name){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tv_content.setTransitionName(name);
        }
    }

    private void setUpView(){
        tv_Name = (TextView) findViewById(R.id.name);
        tv_Name.setText(name);
        tv_content = (TextView) findViewById(R.id.content);
        tv_content.setText(content);
        View bottomLine = findViewById(R.id.bottomLine);
        View spacing_line = findViewById(R.id.spacing_line);
        View all_line = findViewById(R.id.line_isAll);
        all_line.setVisibility(isAllLine ? VISIBLE : GONE);
        bottomLine.setVisibility(isBottom ? VISIBLE : GONE);
        spacing_line.setVisibility(isSpacing ? VISIBLE : GONE);
        ImageView navArrow = (ImageView) findViewById(R.id.rightArrow);
        navArrow.setVisibility(canNav ? VISIBLE : GONE);
        contentPanel= (LinearLayout) findViewById(R.id.contentText);
        contentPanel.setVisibility(isSwitch ? GONE : VISIBLE);
        switchPanel = (Switch) findViewById(R.id.btnSwitch);
        switchPanel.setVisibility(isSwitch?VISIBLE:GONE);
        ImageView img = (ImageView) findViewById(R.id.image);
        img.setVisibility(image==0?GONE:VISIBLE);

        if(image!=0){
            img.setImageResource(image);
        }else
            img.setVisibility(GONE);
        View v=findViewById(R.id.aline_root);
        v.setTag(getTag());
        Utils.onClick(v, () -> {
            if(callBack!=null)callBack.click();
            return null;
        });
    }

    private onClickCallBack callBack;
    public interface onClickCallBack{
        void click();
     }

    public AlineLinerlayoutView setClick(onClickCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public TextView tv_content;
    public TextView tv_Name;

    public TextView getTvContent() {
        if(tv_content==null)
            tv_content = (TextView) findViewById(R.id.content);
        return tv_content;
    }

    /**
     * 设置文字内容
     *
     * @param content 内容
     */
    public void setContent(String content){
        this.content = content;
        if(tv_content==null)
            tv_content = (TextView) findViewById(R.id.content);
        tv_content.setText(content);
    }

    public void initRedPoint(){
        LayoutParams line=new LayoutParams(0,1);
        line.weight=1;
        View v=new View(getContext());
        v.setLayoutParams(line);
        contentPanel.addView(v,0);

        LayoutParams param=new LayoutParams(SizeUtils.dp2px(getContext(),18F),SizeUtils.dp2px(getContext(),18F));
        param.gravity= Gravity.CENTER;
        tv_content.setVisibility(GONE);
        tv_content.setGravity( Gravity.CENTER);
        tv_content.setLayoutParams(param);
        tv_content.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
        tv_content.setTextSize(12);
        tv_content.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.point1));
    }

    public void setRedContent(int content){
        if(content<=0){
            tv_content.setVisibility(GONE);
            return;
        }else {
            tv_content.setVisibility(VISIBLE);
        }
        setContent(String.valueOf(content));
    }

 public void setName(String content){
        this.name = content;
        if(tv_Name==null)
            tv_Name = (TextView) findViewById(R.id.name);
     tv_Name.setText(content);
    }


    /**
     * 获取内容
     *
     */
    public String getContent(){
        if(tv_content==null)
            tv_content = (TextView) findViewById(R.id.content);
        return tv_content.getText().toString();
    }


    /**
     * 设置是否可以跳转
     *
     * @param canNav 是否可以跳转
     */
    public void setCanNav(boolean canNav){
        this.canNav = canNav;
        ImageView navArrow = (ImageView) findViewById(R.id.rightArrow);
        navArrow.setVisibility(canNav ? VISIBLE : GONE);
    }


    /**
     * 设置开关状态
     *
     * @param on 开关
     */
    public void setSwitch(boolean on){
        Switch mSwitch = (Switch) findViewById(R.id.btnSwitch);
        mSwitch.setChecked(on);
    }


    /**
     * 设置开关监听
     *
     * @param listener 监听
     */
    public void setCheckListener(CompoundButton.OnCheckedChangeListener listener){
        Switch mSwitch = (Switch) findViewById(R.id.btnSwitch);
        mSwitch.setOnCheckedChangeListener(listener);
    }


}
