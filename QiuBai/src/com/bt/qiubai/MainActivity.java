package com.bt.qiubai;

import java.util.ArrayList;
import java.util.List;













import com.qiubai.fragment.HotFragment;
import com.qiubai.fragment.CharacterFragment;
import com.qiubai.fragment.PictureFragment;
import com.qiubai.thread.MainBackThread;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,
		OnGestureListener {

	
	private RelativeLayout rel_main_left = null;
	private RelativeLayout rel_main_right = null;

	// 用于展示消息的fragment
	private CharacterFragment characterFragment = null;
	private HotFragment hotFragment = null;
	private PictureFragment pictureFragment = null;

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

	// 定义手势检测实例
	private static GestureDetector detector;

	// 做标签，记录当前是哪个fragment
	private int MARK = 0;

	// 定义手势两点之间的最小距离 
	final int DISTANT = 50;
	
	private static final String EXIT_TAG = "退出程序";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.main_title_bar);
		// 加载titleBar的自定义控件
		initTitleBar();

		// 加载fragment的标题栏
		initScrollTitle();

		fragmentManager = getFragmentManager();
		beginTransaction();
		setTabSelection(0);

		// 创建手势检测器
		detector = new GestureDetector(getApplicationContext(), this);


	}
	
	
	//定义变量，判断是否退出	
	private static boolean isExit = false;
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			if(msg.what==2){
				isExit=false;
			}
			
		};
	};
	

	//连续按键退出程序
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			exitApplication();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	};


	private void exitApplication() {
		if(!isExit){
			isExit=true;
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			//利用Handler延迟2秒发送更改消息
			mHandler.sendEmptyMessageDelayed(2, 2000);
			
		}else{
			Log.e(EXIT_TAG, "exit application");
			finish();
		}
	}


	/**
	 * 根据传入的参数来选择每个tab对应的下标
	 * 
	 * @param i
	 */
	
	FragmentTransaction transaction ;
	
	private void beginTransaction(){
		color_orange = getResources().getColor(R.color.activity_main_orange);

		clearSelection();// 每次选中之前先清理掉上次的选中的状态
		transaction = fragmentManager.beginTransaction();// 开启一个Fragment事务
		hideFragments(transaction);// 先隐藏所有的Fragment,以防止有多个Fragment显示在界面上的情况
	}
	
	private void setTabSelection(int i) {
		
		switch (i) {

		case 0:
			// 当点击了热门tab时，改变文字颜色和图片
			rel_main_hot_image.setImageResource(R.drawable.tab_red_rectangle);
			rel_main_hot_text.setTextColor(color_orange);
			
			if (hotFragment == null) {
				// 如果hotFragment为空，则创建一个并添加到界面上
				hotFragment = new HotFragment();
				transaction.add(R.id.content, hotFragment);
			} else {
				// 如果hotFragment不为空，则直接将他显示出来
				transaction.show(hotFragment);
			}
			
			

			break;
		case 1:
			rel_main_character_image
					.setImageResource(R.drawable.tab_red_rectangle);
			rel_main_character_text.setTextColor(color_orange);
			if (characterFragment == null) {
				// 如果characterFragment为空，则创建一个并添加到界面上
				characterFragment = new CharacterFragment();

				transaction.add(R.id.content, characterFragment);
			} else {
				// 如果characterFragment不为空，则直接将他显示出来
				transaction.show(characterFragment);
			}
			break;
		case 2:

			rel_main_picture_image
					.setImageResource(R.drawable.tab_red_rectangle);
			rel_main_picture_text.setTextColor(color_orange);
			
			if (pictureFragment == null) {
				// 如果pictureFragment为空，则创建一个并添加到界面上
				pictureFragment = new PictureFragment();
				transaction.add(R.id.content, pictureFragment);
			} else {
				// 如果pictureFragment不为空，则直接将他显示出来
				transaction.show(pictureFragment);
			}
			break;

		}
		MARK = i;
		
		
		transaction.commit();

	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (hotFragment != null) {
			transaction.hide(hotFragment);
		}
		if (characterFragment != null) {
			transaction.hide(characterFragment);
		}
		if (pictureFragment != null) {
			transaction.hide(pictureFragment);
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

			// 跳转到detail activity
			Intent intent = new Intent(MainActivity.this, DetailActivity.class);
			startActivity(intent);

			break;

		case R.id.rel_main_hot_layout:
			// 点击热门响应事件
			beginTransaction();

			
			setTabSelection(0);
			// mPager.setCurrentItem(0);
			break;
		case R.id.rel_main_character_layout:
			// 点击文字响应事件
			beginTransaction();
			setTabSelection(1);
			// mPager.setCurrentItem(1);
			break;
		case R.id.rel_main_picture_layout:
			// 点击图片响应事件
			beginTransaction();
			setTabSelection(2);
			// mPager.setCurrentItem(2);
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// 将该activity中的触碰事件交给GestureDetector来处理
		return detector.onTouchEvent(event);
	}

	/*
	 * GesureDetector手势 (non-Javadoc)
	 * 
	 * @see android.view.GestureDetector.OnGestureListener#onDown(android.view.
	 * MotionEvent)
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

	/*
	 * 滑动效果的实现 (non-Javadoc)
	 * 
	 * @see android.view.GestureDetector.OnGestureListener#onFling(android.view.
	 * MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		clearSelection();
		beginTransaction();
//		System.out.println("e1.getX():"+e1.getX());
//		System.out.println("e2.getX():"+e2.getX());
		// 当是Fragment0的时候
		if (MARK == 0) {
			if (e2.getX() > e1.getX() + DISTANT) {
				
				transaction.setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
				setTabSelection(1);
				MARK = 1;
			} else {
				setTabSelection(0);
			}
		}

		else if (MARK == 1) {
			if (e2.getX() > e1.getX() + DISTANT) {
				transaction.setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
				setTabSelection(2);
				MARK = 2;
			} else if (e1.getX() > e2.getX() + DISTANT) {
				transaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit);
				setTabSelection(0);
				MARK = 0;
			} else {
				setTabSelection(1);
			}
		}

		else if (MARK == 2) {
			if (e1.getX() > e2.getX() + DISTANT) {
				transaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit);
				setTabSelection(1);
				MARK = 1;
			} else {
				setTabSelection(2);
			}
		}

		return false;
	}
}
