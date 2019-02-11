package com.ray.frame.view.layout;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.ray.frame.R;
import com.ray.frame.presentation.utils.Lg;

public class SlidingButtonView extends HorizontalScrollView {
    private static final String TAG = "SlidingButtonView";

    //删除按钮
    private View mTextView_Delete;

    //左侧控件
    private RelativeLayout rbtn;

    private TextView text;

    private int leftWidth;
    private int fristLeftWidth;
    private int leftMartin;

    //记录滚动条滚动的距离
    private int mScrollWidth;

    public int getLeftWidth() {
        return leftWidth;
    }

    public void setFristLeftWidth(int fristLeftWidth) {
        this.fristLeftWidth = fristLeftWidth;
    }

    //自定义的接口，用于传达滑动事件
    private IonSlidingButtonListener mIonSlidingButtonListener;

    //记录按钮菜单是否打开，默认关闭false
    private Boolean isOpen = false;

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    //在onMeasure中只执行一次的判断
    private Boolean once = false;
    private Boolean isGetLeftW = false;

    public SlidingButtonView(Context context) {
        this(context, null);
    }

    public SlidingButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public SlidingButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!once) {
            //只需要执行一次
            mTextView_Delete = findViewById(R.id.fra_delete);
//            if(mTextView_Delete.getLayoutParams() instanceof  RelativeLayout.LayoutParams)
//            leftMartin=((RelativeLayout.LayoutParams)mTextView_Delete.getLayoutParams()).leftMargin;
            rbtn = (RelativeLayout) findViewById(R.id.rl_left);
            once = true;
        }
    }

    //使Item在每次变更布局大小时回到初始位置，并且获取滚动条的可移动距离
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            //获取水平滚动条可以滑动的范围，即右侧按钮的宽度
            mScrollWidth = mTextView_Delete.getWidth();
            leftWidth = rbtn.getWidth();
            if (fristLeftWidth==0&&!isGetLeftW) {
                fristLeftWidth = leftWidth;
                isGetLeftW = true;
            }
            if (!isAllOpen) {
                this.scrollTo(leftWidth, 0);
                Lg.v(TAG, "1-->" + leftWidth + "  tag--->" + getTag());
            }
            // LogUtils.d("可以滑动的范围:" + mScrollWidth);

        }
    }

    private boolean canTouch = true;
    private boolean canScroll = true;
    public boolean isAllOpen = false;

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    public boolean isCanTouch() {
        return canTouch;
    }

    public void setCanTouch(boolean canTouch) {
        this.canTouch = canTouch;
    }


 /*   @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!canTouch) {
            if(parentViewpager!=null){
                parentViewpager.onInterceptTouchEvent(ev);
            }
            return false;
        }else {
            return super.onInterceptTouchEvent(ev);
        }
    }
*/
    /**
     * 拦截右划事件
     */
    private int downX;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!canTouch) {
            return false;
        }
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getRawX();
               /* Log.v("onTouchEvent1",(moveX - downX)+"");
                Log.v("onTouchEvent2",(getScrollX() - leftWidth)+"");
                if(getScrollX()-leftWidth<=0)
                    return true;*/
                mIonSlidingButtonListener.onDownOrMove(this);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                changeScrollx();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }


    private Scroller mScroller;

    //调用此方法滚动到目标位置  duration滚动时间
    public void smoothScrollToSlow(int fx, int fy, int duration) {
        int dx = fx - getScrollX();//mScroller.getFinalX();  普通view使用这种方法
        int dy = fy - getScrollY();  //mScroller.getFinalY();
        smoothScrollBySlow(dx, dy, duration);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBySlow(int dx, int dy, int duration) {
        //设置mScroller的滚动偏移量
        if (mScroller != null)
            mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, duration);//scrollView使用的方法（因为可以触摸拖动）
//        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, duration);  //普通view使用的方法
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (mScroller != null && mScroller.computeScrollOffset() && canScroll) {
            //这里调用View的scrollTo()完成实际的滚动
            this.smoothScrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }


    //滚动监听，为了让删除按钮显示在项的背后的效果
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.v("onScrollChanged", l + "\t" + t + "\t" + oldl + "\t" + oldt);
        //  mTextView_Delete.setTranslationX(l - mScrollWidth -100);
        // mTextView_Delete.setTranslationX(l - mScrollWidth  );
        //this.setX(l);
    }

    public void scrollAnimHint() {
        mScroller = new Scroller(getContext());
        scrollHandler.postDelayed(() -> {
            smoothScrollToSlow((mScrollWidth + leftWidth) / 2, 0, 500);
//             this.smoothScrollTo((mScrollWidth + leftWidth)/2, 0);
            isOpen = true;
            mIonSlidingButtonListener.onMenuIsOpen(this);
        }, 800);
        scrollHandler.postDelayed(() -> {
            smoothScrollToSlow(leftWidth, 0, 500);
//             this.smoothScrollTo(leftWidth, 0);
            isOpen = false;
        }, 2000);
    }

    public void changeScrollx() {
//        Lg.d(TAG,"getScrollX(): " + getScrollX());
//        LogUtils.d("mScrollWidth: " + mScrollWidth);
//        LogUtils.d("leftWidth: " + leftWidth);
        if (getScrollX() - leftWidth >= (mScrollWidth / 2)) {
            this.smoothScrollTo(mScrollWidth + leftWidth, 0);
            isOpen = true;
            mIonSlidingButtonListener.onMenuIsOpen(this);
        }/* else if (getScrollX()==0){
            this.smoothScrollTo(0, 0);
        }*/ else {
            this.smoothScrollTo(leftWidth, 0);
            isOpen = false;
        }
    }


    Handler scrollHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                Lg.v(TAG, "2-->" + leftWidth+"\t"+fristLeftWidth);
                if(leftWidth!=fristLeftWidth){
                    scrollHandler.postDelayed(() -> closeMenu(),20);
                }else
                SlidingButtonView.this.smoothScrollTo(leftWidth, 0);
            } else if (msg.what == 1) {
                Lg.v(TAG, "3-->" + 0);
                SlidingButtonView.this.smoothScrollTo(0, 0);
            }
        }
    };

    public void openMenu() {
//        if (isOpen) {
//            return;
//        }
        Message msg = new Message();
        msg.what = 1;
        scrollHandler.sendMessage(msg);

        isOpen = true;
        mIonSlidingButtonListener.onMenuIsOpen(this);
    }

    public void closeMenu() {
//        if (!isOpen) {
//            return;
//        }
        Message msg = new Message();
        msg.what = 2;
        scrollHandler.sendMessage(msg);

        isOpen = false;
    }

    public void setSlidingButtonListener(IonSlidingButtonListener listener) {
        mIonSlidingButtonListener = listener;
    }

    public interface IonSlidingButtonListener {
        void onMenuIsOpen(View view);

        void onDownOrMove(SlidingButtonView slidingButtonView);
    }

}
