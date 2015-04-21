package com.bt.qiubai;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.qiubai.adapter.MainTabAdapter;
import com.qiubai.db.DBManager;
import com.qiubai.db.DbOpenHelper;
import com.qiubai.fragment.HotFragment;
import com.qiubai.fragment.CharacterFragment;
import com.qiubai.fragment.PictureFragment;
import com.qiubai.service.CityService;
import com.qiubai.service.WeatherService;
import com.qiubai.util.HttpUtil;
import com.viewpagerindicator.TabPageIndicator;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DialerFilter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
	
	private Dialog rightDialog;
	private Dialog mainPersonDialog;
	
	private LinearLayout lin_weather;
	private LinearLayout lin_setting;
	
	private TextView text_weather;
	
	private final static int WEATHER = 1; 
	private final static int EXIT = 2; 
	
	private WeatherService weatherService;
	private CityService cityService;
	// 定义变量，判断是否退出
	private static boolean isExit = false;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.what == EXIT) {
				// 退出程序
				isExit = false;
			} else if (msg.what == WEATHER) {
				String temp = (String) msg.obj;
				System.out.println("天气：" + temp);
				text_weather.setText(temp);
			}

		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		mainPersonDialog = new Dialog(MainActivity.this, R.style.CommonDialog);
		mainPersonDialog.setContentView(R.layout.main_dialog_person);
		Window window_mainPerson = mainPersonDialog.getWindow();
		window_mainPerson.setGravity(Gravity.RIGHT);
		window_mainPerson.setWindowAnimations(R.style.MainPersonDialogAnimationStyle);
		
		initTitleBar(); // 加载titleBar的自定义控件
		initTitleDialog(); //加载titlebar的dialog控件
		// 加载ViewPager
		mViewPager = (ViewPager) findViewById(R.id.main_tab_viewpager);
		initFragment();
		mTabPageIndicator = (TabPageIndicator) findViewById(R.id.main_indicator);
		// 加载适配器
		mAdapter = new MainTabAdapter(getSupportFragmentManager(), mFragments);
		mViewPager.setAdapter(mAdapter);
		mTabPageIndicator.setViewPager(mViewPager, 1);

	}
	
	private void initWeather(final String cityName) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				DbOpenHelper dbHelper = new DbOpenHelper(getApplicationContext());
				DBManager dbManager = new DBManager(getApplicationContext());
				dbManager.copyDatabase();
				
				
				
				weatherService = new WeatherService();
				
				String city = weatherService.getCityByName(cityName, getApplicationContext());
				
				
				String weatherUrl = "http://www.weather.com.cn/data/cityinfo/"+city+".html";
				/*
				 * {"weatherinfo":{"city":"常州","cityid":"101191101","temp1":"15℃"
				 * ,"temp2":"9℃","weather":"阵雨","img1":"d3.gif","img2":"n3.gif",
				 * "ptime":"08:00"}}
				 */
				String weatherJson = HttpUtil.doGet(weatherUrl);
//				System.out.println("天气："+result);
				try {
					JSONObject jsonObject = new JSONObject(weatherJson);
					JSONObject weatherObject = jsonObject.getJSONObject("weatherinfo");
					
					String temp1 = weatherObject.getString("temp1");
					String temp2 = weatherObject.getString("temp2");
					String temp = temp1+"/"+temp2;
					Message msg = new Message();
					msg.what = WEATHER;
					msg.obj = temp;
					mHandler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	private void initTitleDialog() {
		rightDialog = new Dialog(MainActivity.this, R.style.CommonActionDialog);
		rightDialog.setContentView(R.layout.main_menu_action_bar);
		rightDialog.getWindow().setGravity(Gravity.RIGHT | Gravity.TOP);
		
		lin_weather = (LinearLayout) rightDialog.findViewById(R.id.main_menu_action_weather_lin);
		lin_setting = (LinearLayout) rightDialog.findViewById(R.id.main_menu_action_setting_lin);
		
		text_weather = (TextView) rightDialog.findViewById(R.id.main_menu_action_weather);
		
		lin_weather.setOnClickListener(this);
		lin_setting.setOnClickListener(this);
	}

	private void initFragment() {
		hotFragment = new HotFragment();
		characterFragment = new CharacterFragment();
		pictureFragment = new PictureFragment();

		mFragments.add(hotFragment);
		mFragments.add(characterFragment);
		mFragments.add(pictureFragment);
	}

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
			mHandler.sendEmptyMessageDelayed(EXIT, 2000);

		} else {
//			Log.e(EXIT_TAG, "exit application");
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
			//Intent intent_login = new Intent(MainActivity.this, LoginActivity.class);
			//startActivity(intent_login);
			break;
		case R.id.rel_main_title_right:
			rightDialog.show();
			
			SharedPreferences share = getSharedPreferences(CityActivity.SHAREDPREFERENCES_FIRSTENTER, MODE_PRIVATE);
			String city = share.getString(CityActivity.CityActivity_CityTown, "常州");
			initWeather(city);
			// 点击右边的按钮响应事件
			// 跳转到detail activity
			//Intent intent = new Intent(MainActivity.this, CharacterDetailActivity.class);
			//startActivity(intent);
			break;

		case R.id.rel_main_title_avator:
			//Intent intent_login = new Intent(MainActivity.this, LoginActivity.class);
			//startActivity(intent_login);
			//overridePendingTransition(R.anim.in_from_right, R.anim.stay_in_place);
			mainPersonDialog.show();
			break;
		case R.id.main_menu_action_weather_lin:
			//点击天气
			rightDialog.dismiss();
			
			Intent intent_weather = new Intent(MainActivity.this, WeatherActivity.class);
			startActivity(intent_weather);
			
//			Toast.makeText(MainActivity.this, "今天天气晴朗", Toast.LENGTH_SHORT).show();
			break;
		case R.id.main_menu_action_setting_lin:
			//点击设置
			rightDialog.dismiss();
			
			Toast.makeText(MainActivity.this, "点击设置，准备跳转", Toast.LENGTH_SHORT).show();
			break;
		}
	}

}
