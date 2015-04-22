package com.bt.qiubai;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.qiubai.adapter.MainTabAdapter;
import com.qiubai.db.DBManager;
import com.qiubai.db.DbOpenHelper;
import com.qiubai.fragment.CharacterFragment;
import com.qiubai.fragment.HotFragment;
import com.qiubai.fragment.PictureFragment;
import com.qiubai.service.CityService;
import com.qiubai.service.WeatherService;
import com.qiubai.util.HttpUtil;
import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private RelativeLayout main_rel_menu, rel_main_right, main_rel_avator;
	private LinearLayout lin_weather, lin_setting;
	private TextView text_weather;
	private ViewPager main_viewpager;
	
	private Dialog rightDialog;
	private CharacterFragment characterFragment; // 用于展示消息的fragment
	private HotFragment hotFragment;
	private PictureFragment pictureFragment;
	private List<Fragment> mFragments = new ArrayList<Fragment>();

	private TabPageIndicator mTabPageIndicator;
	private MainTabAdapter mAdapter;
	
	private WeatherService weatherService;
	private CityService cityService;
	
	private final static int WEATHER = 1; 
	private final static int EXIT = 2; 
	private static boolean isExit = false; // 定义变量，判断是否退出
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.main_title);
		
		main_rel_menu = (RelativeLayout) findViewById(R.id.rel_main_title_left);
		main_rel_menu.setOnClickListener(this);
		rel_main_right = (RelativeLayout) findViewById(R.id.rel_main_title_right);
		rel_main_right.setOnClickListener(this);
		main_rel_avator = (RelativeLayout) findViewById(R.id.rel_main_title_avator);
		main_rel_avator.setOnClickListener(this);
		
		initTitleDialog(); //加载titlebar的dialog控件
		
		main_viewpager = (ViewPager) findViewById(R.id.main_viewpager);
		initFragment();
		//mTabPageIndicator = (TabPageIndicator) findViewById(R.id.main_indicator);
		mAdapter = new MainTabAdapter(getSupportFragmentManager(), mFragments); // 加载适配器
		main_viewpager.setAdapter(mAdapter);
		mTabPageIndicator.setViewPager(main_viewpager, 1);
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
					mainHandler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}).start();
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
			mainHandler.sendEmptyMessageDelayed(EXIT, 2000);
		} else {
			finish();
		}
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
	
	@SuppressLint("HandlerLeak")
	private Handler mainHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == EXIT) {
				isExit = false; // 退出程序
			} else if (msg.what == WEATHER) {
				String temp = (String) msg.obj;
				System.out.println("天气：" + temp);
				text_weather.setText(temp);
			}
		}
	};

}
