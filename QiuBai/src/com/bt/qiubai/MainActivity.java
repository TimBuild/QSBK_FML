package com.bt.qiubai;

import com.qiubai.fragment.ContactsFragment;
import com.qiubai.fragment.MessageFragment;
import com.qiubai.fragment.NewsFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,OnGestureListener {

	private RelativeLayout rel_main_left = null;
	private RelativeLayout rel_main_right = null;

	// 用于展示消息的fragment
	private MessageFragment messageFragment = null;
	private ContactsFragment contactsFragment = null;
	private NewsFragment newsFragment = null;

	// 在tab布局上显示上View的控件
	private View rel_main_hot_layout = null;
	private View rel_main_character_layout = null;
	private View rel_main_picture_layout = null;

	// 在tab布局上显示上图标的控件
	private ImageView rel_main_hot_image = null;
	private ImageView rel_main_character_image = null;
	private ImageView rel_main_picture_image = null;

	// 在tab布局上显示上文字的控件
	private TextView rel_main_hot_text = null;
	private TextView rel_main_character_text = null;
	private TextView rel_main_picture_text = null;

	// 用于对Fragment的管理
	private FragmentManager fragmentManager = null;

	private int color_orange;
	private int color_gray;
	
	/** 定义手势检测实例 */
	public static GestureDetector detector;

	/** 做标签，记录当前是哪个fragment */
	public int MARK = 0;

	/** 定义手势两点之间的最小距离 */
	final int DISTANT = 50;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.main_title_bar);
		// 加载titleBar的自定义控件
		initTitleBar();
		// 加载fragment的标题栏
		initScrollTitle();

		fragmentManager = getFragmentManager();
		setTabSelection(0);
		
		//创建手势检测器
		detector = new GestureDetector(getApplicationContext(), this);

	}

	/**
	 * 根据传入的参数来选择每个tab对应的下标
	 * 
	 * @param i
	 */
	private void setTabSelection(int i) {
		color_orange = getResources().getColor(R.color.activity_main_orange);

		clearSelection();// 每次选中之前先清理掉上次的选中的状态
		FragmentTransaction transaction = fragmentManager.beginTransaction();// 开启一个Fragment事务
		hideFragments(transaction);// 先隐藏所有的Fragment,以防止有多个Fragment显示在界面上的情况

		switch (i) {

		case 0:
			// 当点击了热门tab时，改变文字颜色和图片
			rel_main_hot_image.setImageResource(R.drawable.tab_red_rectangle);
			rel_main_hot_text.setTextColor(color_orange);
			if (messageFragment == null) {
				// 如果messageFragment为空，则创建一个并添加到界面上
				messageFragment = new MessageFragment();
				transaction.add(R.id.content, messageFragment);
			} else {
				// 如果messageFragment不为空，则直接将他显示出来
				transaction.show(messageFragment);
			}
			

			break;
		case 1:
			rel_main_character_image
					.setImageResource(R.drawable.tab_red_rectangle);
			rel_main_character_text.setTextColor(color_orange);
			if (contactsFragment == null) {
				// 如果messageFragment为空，则创建一个并添加到界面上
				contactsFragment = new ContactsFragment();
				transaction.add(R.id.content, contactsFragment);
			} else {
				// 如果messageFragment不为空，则直接将他显示出来
				transaction.show(contactsFragment);
			}
			break;
		case 2:

			rel_main_picture_image
					.setImageResource(R.drawable.tab_red_rectangle);
			rel_main_picture_text.setTextColor(color_orange);
			if (newsFragment == null) {
				// 如果messageFragment为空，则创建一个并添加到界面上
				newsFragment = new NewsFragment();
				transaction.add(R.id.content, newsFragment);
			} else {
				// 如果messageFragment不为空，则直接将他显示出来
				transaction.show(newsFragment);
			}
			break;

		}
		MARK = i;
//		System.out.println("MARK:"+MARK);

		transaction.commit();

	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (messageFragment != null) {
			transaction.hide(messageFragment);
		}
		if (contactsFragment != null) {
			transaction.hide(contactsFragment);
		}
		if (newsFragment != null) {
			transaction.hide(newsFragment);
		}

	}

	/**
	 * 清除掉所有的选中状态
	 */
	private void clearSelection() {
		color_gray = getResources().getColor(R.color.activity_main_gray);
		rel_main_hot_image.setImageResource(R.drawable.tab_bg);
		rel_main_hot_text.setTextColor(color_gray);

		rel_main_character_image.setImageResource(R.drawable.tab_bg);
		rel_main_character_text.setTextColor(color_gray);

		rel_main_picture_image.setImageResource(R.drawable.tab_bg);
		rel_main_picture_text.setTextColor(color_gray);
	}

	private void initScrollTitle() {
		rel_main_hot_layout = findViewById(R.id.rel_main_hot_layout);
		rel_main_character_layout = findViewById(R.id.rel_main_character_layout);
		rel_main_picture_layout = findViewById(R.id.rel_main_picture_layout);

		rel_main_hot_image = (ImageView) findViewById(R.id.rel_main_hot_image);
		rel_main_character_image = (ImageView) findViewById(R.id.rel_main_character_image);
		rel_main_picture_image = (ImageView) findViewById(R.id.rel_main_picture_image);

		rel_main_hot_text = (TextView) findViewById(R.id.rel_main_hot_text);
		rel_main_character_text = (TextView) findViewById(R.id.rel_main_character_text);
		rel_main_picture_text = (TextView) findViewById(R.id.rel_main_picture_text);

		rel_main_hot_layout.setOnClickListener(this);
		rel_main_character_layout.setOnClickListener(this);
		rel_main_picture_layout.setOnClickListener(this);

	}

	/**
	 * 加载titleBar的自定义控件
	 */
	private void initTitleBar() {
		rel_main_left = (RelativeLayout) findViewById(R.id.rel_main_title_left);
		rel_main_right = (RelativeLayout) findViewById(R.id.rel_main_title_right);

		rel_main_left.setOnClickListener(this);
		rel_main_right.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 点击事件
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rel_main_title_left:
			// 点击左边的按钮响应事件
			break;
		case R.id.rel_main_title_right:
			// 点击右边的按钮响应事件
			break;

		case R.id.rel_main_hot_layout:
			// 点击热门响应事件
			setTabSelection(0);
			break;
		case R.id.rel_main_character_layout:
			// 点击文字响应事件
			setTabSelection(1);
			break;
		case R.id.rel_main_picture_layout:
			// 点击图片响应事件
			setTabSelection(2);
			break;

		default:
			break;
		}
	}

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onDown(android.view.MotionEvent)
	 */
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}
}
