package com.bt.qiubai;

import java.util.ArrayList;
import java.util.List;

import com.qiubai.adapter.MainTabAdapter;
import com.qiubai.fragment.HotFragment;
import com.qiubai.fragment.CharacterFragment;
import com.qiubai.fragment.PictureFragment;
import com.viewpagerindicator.TabPageIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private RelativeLayout rel_main_left = null;
	private RelativeLayout rel_main_right = null;
	private RelativeLayout rel_main_avator = null;

	// 用于展示消息的fragment
	private CharacterFragment characterFragment = null;
	private HotFragment hotFragment = null;
	private PictureFragment pictureFragment = null;

	private static final String EXIT_TAG = "退出程序";

	private ViewPager mViewPager;
	private List<Fragment> mFragments = new ArrayList<Fragment>();

	private TabPageIndicator mTabPageIndicator;
	private MainTabAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity);

		// 加载titleBar的自定义控件
		initTitleBar();

		// 加载ViewPager
		mViewPager = (ViewPager) findViewById(R.id.main_tab_viewpager);

		initFragment();
		mTabPageIndicator = (TabPageIndicator) findViewById(R.id.main_indicator);

		// 加载适配器
		mAdapter = new MainTabAdapter(getSupportFragmentManager(), mFragments);

		mViewPager.setAdapter(mAdapter);

		mTabPageIndicator.setViewPager(mViewPager, 1);

	}

	private void initFragment() {
		hotFragment = new HotFragment();
		characterFragment = new CharacterFragment();
		pictureFragment = new PictureFragment();

		mFragments.add(hotFragment);
		mFragments.add(characterFragment);
		mFragments.add(pictureFragment);
	}

	// 定义变量，判断是否退出
	private static boolean isExit = false;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.what == 2) {
				isExit = false;
			}

		};
	};

	// 连续按键退出程序
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitApplication();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	};

	private void exitApplication() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			// 利用Handler延迟2秒发送更改消息
			mHandler.sendEmptyMessageDelayed(2, 2000);

		} else {
			Log.e(EXIT_TAG, "exit application");
			finish();
		}
	}

	/**
	 * 加载titleBar的自定义控件
	 */
	private void initTitleBar() {
		rel_main_left = (RelativeLayout) findViewById(R.id.rel_main_title_left);
		rel_main_right = (RelativeLayout) findViewById(R.id.rel_main_title_right);
		rel_main_avator = (RelativeLayout) findViewById(R.id.rel_main_title_avator);

		rel_main_left.setOnClickListener(this);
		rel_main_right.setOnClickListener(this);
		rel_main_avator.setOnClickListener(this);
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

			// 跳转到Login activity
			Intent intent_login = new Intent(MainActivity.this,
					LoginActivity.class);
			startActivity(intent_login);
			break;
		case R.id.rel_main_title_right:
			// 点击右边的按钮响应事件

			// 跳转到detail activity
			Intent intent = new Intent(MainActivity.this, DetailActivity.class);
			startActivity(intent);

			break;

		case R.id.rel_main_title_avator:
			// 点击头像按钮响应事件
			break;

		}
	}

}
