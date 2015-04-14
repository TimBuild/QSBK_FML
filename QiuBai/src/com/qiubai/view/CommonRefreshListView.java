package com.qiubai.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.bt.qiubai.R;
import com.qiubai.util.BitmapUtil;
import com.qiubai.util.DensityUtil;

public class CommonRefreshListView extends ListView implements OnScrollListener{
	
	private int firstVisibleItemPosition; //屏幕显示在第一个的 item 的索引
	private View hiddenView, headerView, footerView;
	private int headerViewHeight, footerViewHeight;
	//private TextView tv_state;
	
	private int pressDownY;
	private boolean isScrollToBottom, isLoadingMore;
	private OnRefreshListener mOnRefreshListener;
	
	private final static int DOWN_PULL_REFRESH = 0; // 下拉刷新状态
	private final static int RELEASE_REFRESH = 1; // 松开刷新
	private final static int REFRESHING = 2; // 正在刷新中
	private int currentState = DOWN_PULL_REFRESH; // 头布局的状态: 默认为下拉刷新状态
	
	public CommonRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView();
		initFooterView();
		this.setOnScrollListener(this);
	}
	
	public void setHiddenView(View view){
		this.hiddenView = view;
	}
	
	public interface OnRefreshListener{
		public void onDownPullRefresh();
		public void onLoadingMore();
	}
	
	private void initFooterView(){
		footerView = View.inflate(getContext(), R.layout.common_refresh_listview_footer, null);
		//footerView.measure(0, 0);
		//footerViewHeight = footerView.getMeasuredHeight();
		//footerView.setPadding(0, -footerViewHeight, 0, 0);
		this.addFooterView(footerView);
	}
	
	public void test(float degree){
		ImageView crl_min = (ImageView) hiddenView.findViewById(R.id.crl_min);
		Bitmap bitmap_min = BitmapFactory.decodeResource(getResources(), R.drawable.common_refresh_listview_line_min);
		Bitmap alterBitmap_min = BitmapUtil.resizeSquareBitmap(DensityUtil.dip2px(getContext(), 35), bitmap_min);
		Bitmap newBitmap = BitmapUtil.rotateBitmap(degree, alterBitmap_min);
		crl_min.setImageBitmap(newBitmap);
	}
	
	
	private void initHeaderView(){
		headerView = View.inflate(getContext(), R.layout.common_refresh_listview_header, null);
		headerView.measure(0, 0);
		headerViewHeight = headerView.getMeasuredHeight();
		System.out.println("headerViewHeight:  " + headerViewHeight);
		headerView.setPadding(0, -headerViewHeight, 0, 0);
		this.addHeaderView(headerView);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	
	/*private String getLastUpdateTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(System.currentTimeMillis());
	}*/
	
	public void setOnRefreshListener(OnRefreshListener listener) {
		this.mOnRefreshListener = listener;
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			pressDownY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int touchMoveY = (int) ev.getY();
			System.out.println("pressDownY:" + pressDownY + "---->touchMoveY:" + touchMoveY + "---->diff" + (touchMoveY - pressDownY));
			//int diff  = (touchMoveY - pressDownY)/3;
			//System.out.println("diff" + diff);
			int paddingTop = -headerViewHeight + (int)(touchMoveY - pressDownY)/3;
			System.out.println("paddingTop" + paddingTop);
			test(paddingTop);
			if(firstVisibleItemPosition == 0 && -headerViewHeight < paddingTop){
				if(paddingTop >= 0 && currentState == DOWN_PULL_REFRESH){
					System.out.println("松开刷新");
					currentState = RELEASE_REFRESH;
					//refreshHeaderView();
				} else if (paddingTop < 0 && currentState == RELEASE_REFRESH){
					currentState = DOWN_PULL_REFRESH;
		           // refreshHeaderView();
				}
				headerView.setPadding(0, paddingTop, 0, 0);
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (currentState == RELEASE_REFRESH) {
				System.out.println("刷新数据.");
				//把头布局设置为完全显示状态
				headerView.setPadding(0, 0, 0, 0);
				//进入到正在刷新中状态
				currentState = REFRESHING;
				if(mOnRefreshListener != null){
					mOnRefreshListener.onDownPullRefresh();
				}
			} else if(currentState == DOWN_PULL_REFRESH){
				headerView.setPadding(0, -headerViewHeight, 0, 0);
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

/*	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING){
			if(isScrollToBottom && !isLoadingMore) {
				isLoadingMore = true;
				System.out.println("加载更多数据");
				footerView.setPadding(0, 0, 0, 0);
				this.setSelection(this.getCount());
				if(mOnRefreshListener != null){
					mOnRefreshListener.onLoadingMore();
				}
			}
		}
	}*/

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		firstVisibleItemPosition = firstVisibleItem;
		if (getLastVisiblePosition() == (totalItemCount - 1)) {
			isScrollToBottom = true;
		} else {
			isScrollToBottom = false;
		}
	}
	
	/**
	 * 根据currentState刷新头布局的状态
	 */
	/*private void refreshHeaderView(){
		switch (currentState) {
		case DOWN_PULL_REFRESH:
			//tv_state.setText("下拉刷新");
			break;
		case RELEASE_REFRESH:
			//tv_state.setText("松开刷新");
			break;
		case REFRESHING:
			//tv_state.setText("正在刷新中...");
			break;
		}
	}*/
	

}
