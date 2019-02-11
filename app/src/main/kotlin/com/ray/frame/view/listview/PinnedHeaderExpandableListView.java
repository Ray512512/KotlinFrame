package com.ray.frame.view.listview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.ray.frame.presentation.utils.SizeUtils;
import com.ray.frame.presentation.utils.StatusBarUtils;


public class PinnedHeaderExpandableListView extends ExpandableListView
		implements OnScrollListener, OnGroupClickListener {
    private static final String TAG = "PinnedHeaderExpandableL";
	public PinnedHeaderExpandableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		registerListener();
	}

	public PinnedHeaderExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		registerListener();
	}

	public PinnedHeaderExpandableListView(Context context) {
		super(context);
		registerListener();
	}

	/**
	 * Adapter 接口 . 列表必须实现此接口 .
	 */
	public interface HeaderAdapter {
		public static final int PINNED_HEADER_GONE = 0;
		public static final int PINNED_HEADER_VISIBLE = 1;
		public static final int PINNED_HEADER_PUSHED_UP = 2;

		/**
		 * 获取 Header 的状态
		 * 
		 * @param groupPosition
		 * @param childPosition
		 * @return PINNED_HEADER_GONE,PINNED_HEADER_VISIBLE,
		 *         PINNED_HEADER_PUSHED_UP 其中之一
		 */
		int getHeaderState(int groupPosition, int childPosition);

		/**
		 * 配置 Header, 让 Header 知道显示的内容
		 * 
		 * @param header
		 * @param groupPosition
		 * @param childPosition
		 * @param alpha
		 */
		void configureHeader(View header, int groupPosition, int childPosition, int alpha);

		/**
		 * 设置组按下的状态
		 * 
		 * @param groupPosition
		 * @param status
		 */
		void setGroupClickStatus(int groupPosition, int status);

		/**
		 * 获取组按下的状态
		 * 
		 * @param groupPosition
		 * @return
		 */
		int getGroupClickStatus(int groupPosition);

	}

	private static final int MAX_ALPHA = 255;

	private HeaderAdapter mAdapter;

	/**
	 * 用于在列表头显示的 View,mHeaderViewVisible 为 true 才可见
	 */
	private View mHeaderView;

	/**
	 * 列表头是否可见
	 */
	private boolean mHeaderViewVisible;

	private int mHeaderViewWidth;

	private int mHeaderViewHeight;

	public void setHeaderView(View view) {
		mHeaderView = view;
		LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(lp);

		if (mHeaderView != null) {
			setFadingEdgeLength(0);
		}

		requestLayout();
	}

	private void registerListener() {
		fixH= SizeUtils.dp2px(getContext(),48)+ StatusBarUtils.getStatusBarHeight(getContext());
		setOnScrollListener(this);
		setOnGroupClickListener(this);
	}

	/**
	 * 点击 HeaderView 触发的事件
	 */
	private void headerViewClick() {
		long packedPosition = getExpandableListPosition(this.getFirstVisiblePosition());

		int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);

		dealClickStatus(groupPosition);

		this.setSelectedGroup(groupPosition);
	}

	public boolean isCanClick=true;
	private void dealClickStatus(int groupPosition){
		if(!isCanClick)return;
		if (mAdapter.getGroupClickStatus(groupPosition) == 1) {
			this.collapseGroup(groupPosition);
			mAdapter.setGroupClickStatus(groupPosition, 0);
		} else {
			this.expandGroup(groupPosition);
			mAdapter.setGroupClickStatus(groupPosition, 1);
		}
	}

	public void openGroup(int groupPosition){
		this.expandGroup(groupPosition);
		mAdapter.setGroupClickStatus(groupPosition, 1);
	}

	private float mDownX;
	private float mDownY;
	private float mDownX2;
	private float mDownY2;

	/**
	 * 如果 HeaderView 是可见的 , 此函数用于判断是否点击了 HeaderView, 并对做相应的处理 , 因为 HeaderView
	 * 是画上去的 , 所以设置事件监听是无效的 , 只有自行控制 .
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mHeaderViewVisible) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mDownX = ev.getX();
				mDownY = ev.getY();
				if (mDownX <= mHeaderViewWidth && mDownY <= mHeaderViewHeight) {
					return true;
				}
				break;
			case MotionEvent.ACTION_UP:
				float x = ev.getX();
				float y = ev.getY();
				float offsetX = Math.abs(x - mDownX);
				float offsetY = Math.abs(y - mDownY);
				// 如果 HeaderView 是可见的 , 点击在 HeaderView 内 , 那么触发 headerClick()
				if (x <= mHeaderViewWidth && y <= mHeaderViewHeight && offsetX <= mHeaderViewWidth
						&& offsetY <= mHeaderViewHeight) {
                    if (mHeaderView != null) {
						headerViewClick();
					}
					return true;
				}
			default:
				break;
			}
		}

		return super.onTouchEvent(ev);

	}

	private int fixH=0;
	public boolean isHeadClick(MotionEvent ev){
		if (mHeaderViewVisible) {
			switch (ev.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mDownX2 = ev.getRawX();
					mDownY2 = ev.getRawY();
					if (mDownX2 <= mHeaderViewWidth && mDownY2 <= mHeaderViewHeight) {
						return true;
					}
					break;
				case MotionEvent.ACTION_UP:
					float x = ev.getRawX();
					float y = ev.getRawY();
					float offsetX = Math.abs(x - mDownX2);
					float offsetY = Math.abs(y - mDownY2);
					int[] headLoc=new int[2];
					headLoc[0]=mHeaderViewWidth;
					headLoc[1]=fixH+mHeaderViewHeight;
					// 如果 HeaderView 是可见的 , 点击在 HeaderView 内 , 那么触发 headerClick()
					if (x <= headLoc[0] && y <= headLoc[1] && offsetX <= headLoc[0]
							&& offsetY <= headLoc[1]) {
						if (mHeaderView != null) {
							headerViewClick();
							return true;
						}
					}
					return false;
				default:
					break;
			}
		}
		return false;
	}

	@Override
	public void setAdapter(ExpandableListAdapter adapter) {
		super.setAdapter(adapter);
		mAdapter = (HeaderAdapter) adapter;
	}

	/**
	 * 
	 * 点击了 Group 触发的事件 , 要根据根据当前点击 Group 的状态来
	 */
	@Override
	public boolean onGroupClick(final ExpandableListView parent, View v, int groupPosition, long id) {
		if(!isCanClick)return true;
		if (mAdapter.getGroupClickStatus(groupPosition) == 0) {// 点击需展开
			mAdapter.setGroupClickStatus(groupPosition, 1);
			parent.expandGroup(groupPosition);

			// Header自动置顶
			// parent.setSelectedGroup(groupPosition);

		} else if (mAdapter.getGroupClickStatus(groupPosition) == 1) {// 点击合上
			mAdapter.setGroupClickStatus(groupPosition, 0);
			parent.collapseGroup(groupPosition);
		}

		// 返回 true 才可以弹回第一行 , 不知道为什么
		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (mHeaderView != null) {
			measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
			mHeaderViewWidth = mHeaderView.getMeasuredWidth();
			mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		}
	}

	private int mOldState = -1;

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		final long flatPostion = getExpandableListPosition(getFirstVisiblePosition());
		final int groupPos = ExpandableListView.getPackedPositionGroup(flatPostion);
		final int childPos = ExpandableListView.getPackedPositionChild(flatPostion);
		if(mAdapter!=null&&groupPos!=-1) {
			int	state = mAdapter.getHeaderState(groupPos, childPos);
			if (mHeaderView != null && mAdapter != null && state != mOldState) {
				mOldState = state;
				mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
			}
		}
		configureHeaderView(groupPos, childPos);
	}

	public void configureHeaderView(int groupPosition, int childPosition) {
		if (mHeaderView == null || mAdapter == null || ((ExpandableListAdapter) mAdapter).getGroupCount() == 0) {
			return;
		}

		int state = mAdapter.getHeaderState(groupPosition, childPosition);

		switch (state) {
		case HeaderAdapter.PINNED_HEADER_GONE: {
			mHeaderViewVisible = false;
			break;
		}

		case HeaderAdapter.PINNED_HEADER_VISIBLE: {
			mAdapter.configureHeader(mHeaderView, groupPosition, childPosition, MAX_ALPHA);

			if (mHeaderView.getTop() != 0) {
				mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
			}

			mHeaderViewVisible = true;

			break;
		}

		case HeaderAdapter.PINNED_HEADER_PUSHED_UP: {
			View firstView = getChildAt(0);
			int bottom = firstView.getBottom();

			// intitemHeight = firstView.getHeight();
			int headerHeight = mHeaderView.getHeight();

			int y;

			int alpha;

			if (bottom < headerHeight) {
				y = (bottom - headerHeight);
				alpha = MAX_ALPHA * (headerHeight + y) / headerHeight;
			} else {
				y = 0;
				alpha = MAX_ALPHA;
			}

			mAdapter.configureHeader(mHeaderView, groupPosition, childPosition, alpha);

			if (mHeaderView.getTop() != y) {
				mHeaderView.layout(0, y, mHeaderViewWidth, mHeaderViewHeight + y);
			}

			mHeaderViewVisible = true;
			break;
		}
		}
	}

	@Override
	/**
	 * 列表界面更新时调用该方法(如滚动时)
	 */
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (mHeaderViewVisible) {
			// 分组栏是直接绘制到界面中，而不是加入到ViewGroup中
			drawChild(canvas, mHeaderView, getDrawingTime());
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		final long flatPos = getExpandableListPosition(firstVisibleItem);
		int groupPosition = ExpandableListView.getPackedPositionGroup(flatPos);
		int childPosition = ExpandableListView.getPackedPositionChild(flatPos);

		configureHeaderView(groupPosition, childPosition);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}
	private int expandFlag = -1;// 控制列表的展开
	class GroupClickListener implements OnGroupClickListener {
		private ExpandableListView explistview;
		public GroupClickListener(ExpandableListView explistview) {
			this.explistview = explistview;
		}

		@Override
		public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
			if(!isCanClick)return true;
			if (expandFlag == -1) {
				// 展开被选的group
				explistview.expandGroup(groupPosition);
				// 设置被选中的group置于顶端
				explistview.setSelectedGroup(groupPosition);
				expandFlag = groupPosition;
			} else if (expandFlag == groupPosition) {
				explistview.collapseGroup(expandFlag);
				expandFlag = -1;
			} else {
				explistview.collapseGroup(expandFlag);
				// 展开被选的group
				explistview.expandGroup(groupPosition);
				// 设置被选中的group置于顶端
				explistview.setSelectedGroup(groupPosition);
				expandFlag = groupPosition;
			}
			return true;
		}
	}
}
