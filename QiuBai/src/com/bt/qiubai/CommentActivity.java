package com.bt.qiubai;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CommentActivity extends Activity implements OnClickListener{
	
	private RelativeLayout comment_title_back;
	private ListView comment_listview;
	private EditText comment_edittext_comment;
	private TextView comment_send;
	private View comment_listview_footer;
	
	private CommentBaseAdapter commentBaseAdapter;
	private GestureDetector gestureDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 下面三行代码顺序不能颠倒
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 声明使用自定义标题
		setContentView(R.layout.comment_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.comment_title);
		
		comment_title_back = (RelativeLayout) findViewById(R.id.comment_title_back);
		comment_title_back.setOnClickListener(this);
		
		comment_listview = (ListView) findViewById(R.id.comment_listview);
		comment_listview_footer = getLayoutInflater().inflate(R.layout.comment_listview_footer, null);
		comment_listview.addFooterView(comment_listview_footer);
		commentBaseAdapter = new CommentBaseAdapter(this);
		comment_listview.setAdapter(commentBaseAdapter);
		
		gestureDetector = new GestureDetector(CommentActivity.this,onGestureListener);
		comment_listview.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		});
		
		comment_listview.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
		});
		
		comment_edittext_comment = (EditText) findViewById(R.id.comment_edittext_comment);
		comment_edittext_comment.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(!"".equals(s.toString().trim())){
					comment_send.setTextColor(getResources().getColor(R.color.comment_send_enable_text_color));
				} else {
					comment_send.setTextColor(getResources().getColor(R.color.comment_send_disable_text_color));
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		comment_send = (TextView) findViewById(R.id.comment_send);
		comment_send.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.comment_title_back:
			CommentActivity.this.finish();
			overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
			break;
		case R.id.comment_send:
			
			break;
		}
	}
	
	@SuppressLint("ViewHolder")
	private class CommentBaseAdapter extends BaseAdapter {
		private LayoutInflater inflater;// 得到一个LayoutInfalter对象用来导入布局
		
		public CommentBaseAdapter(Context context) {
			this.inflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return 20;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = inflater.inflate(R.layout.comment_listview_item, null);
			return convertView;
		}
		
	}

	private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float x = Math.abs(e2.getX() - e1.getX());
			float y = Math.abs(e2.getY() - e1.getY());
			if(y < x){
				if(e2.getX() - e1.getX() > 200){
					CommentActivity.this.finish();
					overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
					return true;
				}else if(e2.getX() - e1.getX() < -200){
					return false;
				}
			}else {
				return false;
			}
			
			return false;
		}
	};
}
