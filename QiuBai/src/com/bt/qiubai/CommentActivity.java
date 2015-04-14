package com.bt.qiubai;

import com.qiubai.service.UserService;
import com.qiubai.util.BitmapUtil;
import com.qiubai.util.DensityUtil;
import com.qiubai.util.NetworkUtil;
import com.qiubai.util.SharedPreferencesUtil;
import com.qiubai.view.CommonRefreshListView;
import com.qiubai.view.CommonRefreshListView.OnRefreshListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends Activity implements OnClickListener, OnTouchListener, OnRefreshListener{
	
	private RelativeLayout comment_title_back;
	private EditText comment_edittext_comment;
	private TextView comment_send;
	private CommonRefreshListView commentListView;
	private LinearLayout crl_header_hidden;
	
	private CommentBaseAdapter commentBaseAdapter;
	private GestureDetector gestureDetector;
	
	private UserService userService = new UserService();
	private SharedPreferencesUtil spUtil = new SharedPreferencesUtil(CommentActivity.this);
	
	private final static int COMMENT_SUCCESS = 1;
	private final static int COMMENT_FAIL = 2;
	private final static int COMMENT_ERROR = 3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.comment_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.comment_title);
		
		if(!NetworkUtil.isConnectInternet(this)){
			Toast.makeText(this, "您没有连接网络，请连接网络", Toast.LENGTH_SHORT).show();
		}
		crl_header_hidden = (LinearLayout) findViewById(R.id.crl_header_hidden);
		comment_title_back = (RelativeLayout) findViewById(R.id.comment_title_back);
		comment_title_back.setOnClickListener(this);
		
		commentListView = (CommonRefreshListView) findViewById(R.id.comment_listview);
		commentBaseAdapter = new CommentBaseAdapter(this);
		commentListView.setAdapter(commentBaseAdapter);
		commentListView.setHiddenView(crl_header_hidden);
		commentListView.setOnRefreshListener(this);
		
		
		gestureDetector = new GestureDetector(CommentActivity.this,onGestureListener );
		/*comment_listview.setOnTouchListener(this);
		comment_listview.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				System.out.println(firstVisibleItem);
			}
		});*/
		
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
		test();
	}
	
	public void test(){
		ImageView crl_min = (ImageView) findViewById(R.id.crl_min);
		ImageView crl_hour = (ImageView) findViewById(R.id.crl_hour);
		ImageView crl_clock_bg = (ImageView) findViewById(R.id.crl_clock_bg);
		Bitmap bitmap_min = BitmapFactory.decodeResource(getResources(), R.drawable.common_refresh_listview_line_min);
		Bitmap alterBitmap_min = BitmapUtil.resizeSquareBitmap(DensityUtil.dip2px(this, 35), bitmap_min);
		Bitmap newBitmap_min = BitmapUtil.rotateBitmap(0, alterBitmap_min);
		
		Bitmap bitmap_hour = BitmapFactory.decodeResource(getResources(), R.drawable.common_refresh_listview_line_hour);
		Bitmap alterBitmap_hour = BitmapUtil.resizeSquareBitmap(DensityUtil.dip2px(this, 35), bitmap_hour);
		
		Bitmap bitmap_clock_bg = BitmapFactory.decodeResource(getResources(), R.drawable.common_refresh_listview_disk);
		Bitmap alterBitmap_clock_bg = BitmapUtil.resizeSquareBitmap(DensityUtil.dip2px(this, 20), bitmap_clock_bg);
		crl_hour.setImageBitmap(alterBitmap_hour);
		crl_min.setImageBitmap(newBitmap_min);
		crl_clock_bg.setImageBitmap(alterBitmap_clock_bg);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.comment_title_back:
			CommentActivity.this.finish();
			overridePendingTransition(R.anim.stay_in_place, R.anim.out_to_right);
			break;
		case R.id.comment_send:
			if(verifyInformation()){
				if (checkUserLogin()) {
					sendComment();
				} else {
					Toast.makeText(CommentActivity.this, "您还没有登录，请登录", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(CommentActivity.this, LoginActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.in_from_right, R.anim.stay_in_place);
				}
			}
			break;
		}
	}
	
	/**
	 * verify information and network
	 * @return true: success; false: fail
	 */
	public boolean verifyInformation(){
		if("".equals(comment_edittext_comment.getText().toString().trim())){
			return false;
		} else if(!NetworkUtil.isConnectInternet(this)){
			Toast.makeText(this, "您没有连接网络，请连接网络", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * check user login
	 * @return true: user login; false: user doesn't login
	 */
	public boolean checkUserLogin(){
		if("true".equals(spUtil.getUserLoginFlag())){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * publish comment
	 */
	public void sendComment(){
		new Thread(){
			public void run() {
				String result = userService.publishComment(spUtil.getToken(), spUtil.getEmail(), comment_edittext_comment.getText().toString().trim());
				if("success".equals(result)){
					Message msg = commentHandle.obtainMessage(COMMENT_SUCCESS);
					commentHandle.sendMessage(msg);
				} else if("fail".equals(result)){
					Message msg = commentHandle.obtainMessage(COMMENT_FAIL);
					commentHandle.sendMessage(msg);
				} else if("error".equals(result)){
					Message msg = commentHandle.obtainMessage(COMMENT_ERROR);
					commentHandle.sendMessage(msg);
				}
			};
		}.start();
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

	@SuppressLint("HandlerLeak")
	private Handler commentHandle = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case COMMENT_SUCCESS:
				Toast.makeText(CommentActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
				break;
			case COMMENT_FAIL:
				Toast.makeText(CommentActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
				break;
			case COMMENT_ERROR:
				Toast.makeText(CommentActivity.this, "发布异常", Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};
	
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

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	@Override
	public void onDownPullRefresh() {
		
	}

	@Override
	public void onLoadingMore() {
		// TODO Auto-generated method stub
		
	}
}
